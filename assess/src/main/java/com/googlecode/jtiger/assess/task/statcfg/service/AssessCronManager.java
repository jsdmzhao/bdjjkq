package com.googlecode.jtiger.assess.task.statcfg.service;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.googlecode.jtiger.assess.evaluate.service.EvaluateRecordManager;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.core.util.DateUtil;
import com.googlecode.jtiger.modules.quartz.model.Cron;
@Service
public class AssessCronManager extends BaseGenericsManager<Cron> {
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private EvaluateRecordManager evaluateRecordManager; 

	/**
	 * 添加新Cron实例
	 * 
	 * @param cron
	 */
	public void addCron(Cron cron) {
		Assert.notNull(cron);
		Assert.hasText(cron.getMarker());
		Assert.hasText(cron.getName());

		Cron c = getCronByMarker(cron.getMarker());

		if (c == null) {
			logger.info("添加一个新的Cron对象");
			getDao().save(cron);
		}
	}

	/**
	 * 根据marker这个唯一标识,得到Cron实例
	 * 
	 * @param marker
	 *            唯一标识依据
	 * @return Cron实例
	 */
	public Cron getCronByMarker(String marker) {
		return (Cron) getDao().findObject("from Cron c where c.marker = ?",
				marker);
	}

	/**
	 * 跟新选定的Cron实例
	 * 
	 * @param cron
	 *            选定并作了修改的Cron实例
	 */
	public void update(Cron cron) {
		Assert.notNull(cron);
		Assert.hasText(cron.getId());

		Cron cronOriginal = getCronById(cron.getId());
		if (!StringUtils.equals(cron.getCron(), cronOriginal.getCron())) {
			cronOriginal.setName(cron.getName());
			cronOriginal.setCron(cron.getCron());

			getDao().merge(cronOriginal);
			restartJob(cronOriginal.getMarker());
		}
	}

	/**
	 * 根据id得到Cron实例
	 * 
	 * @param id
	 * @return
	 */
	private Cron getCronById(String id) {
		return (Cron) getDao().findObject("from Cron c where c.id = ?", id);
	}

	/**
	 * 如果cron改变，则重新启动Quartz任务。
	 * 
	 * @param Cron的marker字段
	 *            ，对应的trigger 的beanId为markerTrigger
	 */
	private void restartJob(String marker) {
		if (StringUtils.isNotBlank(marker)) {
			logger.warn("CRON未设定。");
			return;
		}

		Cron cron = getCronByMarker(marker);
		// 得到trigger
		logger.info("重启Job {}", marker);
		CronTrigger cronTrigger = (CronTrigger) ctx.getBean(marker + "Trigger",
				CronTrigger.class);
		if (cron == null || cronTrigger == null) {
			logger.info("定时任务不存在 {}", marker);
			return;
		}
		// 如果频率都有变，则不必重新启动.
		if (StringUtils.equals(cron.getCron(), cronTrigger.getCronExpression())) {
			logger.info("Cron未曾改变，Quartz不必重新启动.{}", marker);
			return;
		}
		// [b]下面是关键[/b]
		// 得到SchedulerFactoryBean的实例，注意beanName前面的&符号
		SchedulerFactoryBean schedulerFactory = (SchedulerFactoryBean) ctx
				.getBean("&" + marker + "SchedulerFactory");
		if (schedulerFactory == null) {
			logger.info("定时任务SchedulerFactory不存在 {}", marker);
			return;
		}
		// 重新设定trigger
		try {
			cronTrigger.setCronExpression(cron.getCron());
			schedulerFactory.destroy(); // 关闭原来的任务
			schedulerFactory.afterPropertiesSet(); // 启动新的任务
			logger.info("{}任务启动成功.", marker);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行考核作业任务
	 */
	public void assess() {
		logger.info("Assess任务执行时间 " + DateUtil.getDateTime("hh:mm:ss", new Date()));
		
	}
}

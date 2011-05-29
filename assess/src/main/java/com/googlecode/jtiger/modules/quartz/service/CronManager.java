package com.googlecode.jtiger.modules.quartz.service;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.core.util.DateUtil;
import com.googlecode.jtiger.modules.quartz.model.Cron;

/**
 * 管理动态的Quartz任务。{@link Cron}对象保存Cron表达式和一个唯一的标示marker。
 * 改Quartz任务的Trigger对象的名称为该markerTrigger。该任务的
 * SchedulerFactoryBean的id为markerSchedulerFactory。例如：
 * 
 * 
 * @author Catstiger
 *
 */

@Service
public class CronManager extends BaseGenericsManager<Cron> {
  @Autowired
  private ApplicationContext ctx;
  /**
   * 添加一个Cron对象，调用者在“init-method”中添加属于自己的cron
   * @param cron
   */
  @Transactional
  public void addCron(Cron cron) {
    Assert.notNull(cron);
    Assert.hasText(cron.getMarker());
    Assert.hasText(cron.getName());
    
    Cron c = getCronByMarker(cron.getMarker());
    if(c == null) {
      logger.info("添加一个新的Cron对象");
      getDao().save(cron);
    }
  }
  
  /**
   * 根据Marker获得Cron对象
   */
  public Cron getCronByMarker(String marker) {
    return (Cron) getDao().findObject("from Cron c where c.marker=?", marker);
  }

  /**
   * 保存Cron表达式，并重启对应的Quartz任务
   */
  @Transactional
  public void save(Cron entity) {
    Assert.notNull(entity);
    Assert.hasText(entity.getId());
    
    Cron cron = get(entity.getId());
    if(!StringUtils.equals(cron.getCron(), entity.getCron())) {
      cron.setName(entity.getName());
      cron.setCron(entity.getCron());
      
      getDao().merge(cron);
      restartJob(cron.getMarker()); //重启对应的任务
    }
  }

  /**
   * 如果cron改变，则重新启动Quartz任务。
   * 
   * @param Cron的marker字段，对应的trigger 的beanId为markerTrigger
   */

  private void restartJob(String marker) {
    if (StringUtils.isBlank(marker)) {
      logger.warn("CRON未设定。");
      return;
    }
    
    Cron cron = getCronByMarker(marker);
    // 得到trigger
    logger.info("重启Job {}", marker);
    CronTrigger cronTrigger = (CronTrigger) ctx.getBean(
        marker + "Trigger", CronTrigger.class);
    if(cron == null || cronTrigger == null) {
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
    if(schedulerFactory == null) {
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
   * 测试任务
   */
  public void testMethod() {
    System.out.println("测试任务执行时间 " + DateUtil.getDateTime("hh:mm:ss", new Date()));
  }
}

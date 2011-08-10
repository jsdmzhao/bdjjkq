package com.googlecode.jtiger.assess.task.statcfg.service;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.modules.quartz.model.Cron;

public class AssessCronExpressionFactory implements FactoryBean<CronExpression> {
	private static Logger logger = LoggerFactory
			.getLogger(AssessCronExpressionFactory.class);
	private String cronMarker;
	@Autowired
	private AssessCronManager assessCronManager;

	@Override
	public CronExpression getObject() throws Exception {

		Cron cron = assessCronManager.getCronByMarker(cronMarker);
		if (cron == null) {
			// cron = new Cron(cronMarker, cronMarker, "0 0 0 1 * ?");
			if (AssessConstants.CRON_ASSESS.equals(cronMarker)) {
				cron = new Cron(cronMarker, cronMarker,
						AssessConstants.CRON_ASSESS_DEFAULT);
			} else {
				cron = new Cron(cronMarker, cronMarker,
						AssessConstants.CRON_ASSESS_DAILY_DEFAULT);
			}
			assessCronManager.addCron(cron);
		}
		logger.info("Get cron express from db {}", cron.getCron());
		return new CronExpression(cron.getCron());

	}

	@Override
	public Class<?> getObjectType() {
		return CronExpression.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getCronMarker() {
		return cronMarker;
	}

	public void setCronMarker(String cronMarker) {
		this.cronMarker = cronMarker;
	}

}

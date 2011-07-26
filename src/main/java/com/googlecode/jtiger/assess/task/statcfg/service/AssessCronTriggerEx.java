package com.googlecode.jtiger.assess.task.statcfg.service;

import org.quartz.CronExpression;
import org.springframework.scheduling.quartz.CronTriggerBean;

@SuppressWarnings("serial")
public class AssessCronTriggerEx extends CronTriggerBean {
	public void setCron(CronExpression cronExpression) {
		this.setCronExpression(cronExpression);
	}
}

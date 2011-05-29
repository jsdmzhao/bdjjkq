package com.googlecode.jtiger.modules.quartz.service;

import org.quartz.CronExpression;
import org.springframework.scheduling.quartz.CronTriggerBean;

/**
 * 因为CronTriggerBeanEx的#setCronExpression()方法是重载的，Spring
 * 无法注入，所以提供一个{@link #setCron(CronExpression)}方法用于注入。
 * @author catstiger@gmail.com
 *
 */
public class CronTriggerBeanEx extends CronTriggerBean {
  /**
   * 调用父类的{@link CronTriggerBean#setCronExpression(CronExpression)} 方法.
   */
  public void setCron(CronExpression cronExpression) {
    this.setCronExpression(cronExpression);
  }

}

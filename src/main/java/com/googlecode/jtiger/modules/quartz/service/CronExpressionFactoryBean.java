package com.googlecode.jtiger.modules.quartz.service;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.jtiger.modules.quartz.model.Cron;

public class CronExpressionFactoryBean implements FactoryBean<CronExpression> {
  private static Logger logger = LoggerFactory.getLogger(CronExpressionFactoryBean.class);
  /**
   * Cron对象的marker
   */
  private String cronMarker;
  
  @Autowired
  private CronManager cronManager;

  @Override
  public CronExpression getObject() throws Exception {
    Cron cron = cronManager.getCronByMarker(cronMarker);
    if(cron == null) {
      cron = new Cron(cronMarker, cronMarker, "0 * * * * ?");    
      cronManager.addCron(cron);
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

  /**
   * @return the cronMarker
   */
  public String getCronMarker() {
    return cronMarker;
  }

  /**
   * @param cronMarker the cronMarker to set
   */
  public void setCronMarker(String cronMarker) {
    this.cronMarker = cronMarker;
  }

}

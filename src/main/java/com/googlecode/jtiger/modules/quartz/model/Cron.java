package com.googlecode.jtiger.modules.quartz.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

/**
 * Cron表达式实体，用于数据库方式管理Quartz
 * @author catstiger@gmail.com
 *
 */
@Entity
@Table(name = "assess_crons")
public class Cron extends BaseIdModel {
  
  
  private String name;
  /**
   * 唯一标示，一个易于记忆的ID，用于quartz配置。
   * 对应的Trigger的bean id必须为xxxxTrigger
   */
  private String marker;
  private String cron;
  
  public Cron() {
    
  }
  
  public Cron(String name, String marker, String cron) {
    this.name = name;
    this.marker = marker;
    this.cron = cron;
  }
  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return the marker
   */
  public String getMarker() {
    return marker;
  }
  /**
   * @param marker the marker to set
   */
  public void setMarker(String marker) {
    this.marker = marker;
  }
  /**
   * @return the cron
   */
  public String getCron() {
    return cron;
  }
  /**
   * @param cron the cron to set
   */
  public void setCron(String cron) {
    this.cron = cron;
  }
}

package com.googlecode.jtiger.core.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.googlecode.jtiger.core.dao.support.DynaDataSourceFactory;

/**
 * 使用JdbcTemplate访问数据的基类。数据源配置在applicationContext.xml中
 * 可以配置多个数据源，用dsName定义。子类必须实现{@link #getDataSourceName()}
 * 方法，以定义访问哪个数据源。子类调用{@link #getJdbcTemplate()}以获得
 * JdbcTemplate的实例。
 * 
 * @see com.googlecode.jtiger.core.dao.support.DynaDataSource#getDsName()
 * 
 * @author catstiger@gamil.com
 *
 */
public abstract class BaseJdbcDao {
  @Autowired
  private DynaDataSourceFactory dataSourceFactory;
  
  /**
   * 根据{@link #getDataSourceName()}的定义，返回对应的JdbcTemplate的实例
   * @return
   */
  protected JdbcTemplate getJdbcTemplate() {
    return new JdbcTemplate(dataSourceFactory.getDataSource(getDataSourceName()));
  }
  /**
   * 返回这个类访问的数据源名称，数据源名称定义在applicationContext.xml中dynaDataSourceFactory
   * 配置
   * @return
   */
  protected abstract String getDataSourceName();
}

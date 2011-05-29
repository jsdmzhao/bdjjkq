package com.googlecode.jtiger.core.dao.support;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jtiger.core.ApplicationException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 动态设置数据源
 * @author catstiger@gmail.com
 *
 */
public class DynaDataSourceFactory {
  private static Logger logger = LoggerFactory.getLogger(DynaDataSourceFactory.class);
  /**
   * 动态数据源设置，根据项目情况可以多注入几个
   */
  private List<DynaDataSource> dynaDataSources = new ArrayList<DynaDataSource>(5);
  
  private Map<String, DataSource> dataSources = new HashMap<String, DataSource>(5);
  
  /**
   * 根据数据源名称获得数据源
   * @param dsName
   * @return
   */
  public DataSource getDataSource(String dataSourceName) {
    if(dataSources.containsKey(dataSourceName)) {
      return dataSources.get(dataSourceName);
    }
    
    DynaDataSource dynaDs = null;
    //从数据源定义中找到相应的配置
    for(DynaDataSource dds : dynaDataSources) {
      if(StringUtils.equalsIgnoreCase(dds.getDsName(), dataSourceName)) {
        dynaDs = dds;
        break;
      }
    }
    if(dynaDs == null) {
      throw new ApplicationException("没有找到相应的数据源配置，请检查applicationContext.xml" + dataSourceName);
    }
    ComboPooledDataSource ds = new ComboPooledDataSource();
    try {
      ds.setDriverClass(dynaDs.getDriver());
      ds.setJdbcUrl(dynaDs.getUrl());
      ds.setUser(dynaDs.getUser());
      ds.setPassword(dynaDs.getPassword());
      ds.setInitialPoolSize(dynaDs.getInitialPoolSize());
      ds.setMaxPoolSize(dynaDs.getMaxPoolSize());
      ds.setIdleConnectionTestPeriod(300);
      ds.setPreferredTestQuery(dynaDs.getPingQuery());
      dataSources.put(dataSourceName, ds);
      return ds;
    } catch (PropertyVetoException e) {
      e.printStackTrace();
    }
    
    throw new ApplicationException("数据源配置错误，请检查applicationContext.xml" + dataSourceName);
  }
  
  @PreDestroy
  public void close() {
    Collection<DataSource> allDs = dataSources.values();
    if(CollectionUtils.isEmpty(allDs)) {
      return;
    }
    
    for(DataSource ds : allDs) {
      ComboPooledDataSource c3p0Ds = (ComboPooledDataSource) ds;
      logger.info("销毁数据源 {}", c3p0Ds.getJdbcUrl());
      c3p0Ds.resetPoolManager(true);
      c3p0Ds.close();
      c3p0Ds = null;    
    }
    dataSources.clear();
    logger.info("All seismic datasouces were closed.");
  }

  /**
   * @return the dynaDataSources
   */
  public List<DynaDataSource> getDynaDataSources() {
    return dynaDataSources;
  }

  /**
   * @param dynaDataSources the dynaDataSources to set
   */
  public void setDynaDataSources(List<DynaDataSource> dynaDataSources) {
    this.dynaDataSources = dynaDataSources;
  }
}

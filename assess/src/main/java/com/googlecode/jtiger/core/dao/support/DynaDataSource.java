package com.googlecode.jtiger.core.dao.support;

/**
 * 数据源设置信息
 * @author catstiger@gmail.com
 *
 */
public class DynaDataSource {
  /**
   * 数据源名称，全局唯一
   */
   private String dsName;
   private String driver;
   private String url;
   private String user;
   private String password;
   private int initialPoolSize = 1;
   private int maxPoolSize = 10;
   private String pingQuery = "select sysdate from dual";
   private String testTable = "USER_TABLES";
  /**
   * @return the driver
   */
  public String getDriver() {
    return driver;
  }
  /**
   * @param driver the driver to set
   */
  public void setDriver(String driver) {
    this.driver = driver;
  }
  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }
  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }
  /**
   * @return the user
   */
  public String getUser() {
    return user;
  }
  /**
   * @param user the user to set
   */
  public void setUser(String user) {
    this.user = user;
  }
  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }
  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }
  /**
   * @return the initialPoolSize
   */
  public int getInitialPoolSize() {
    return initialPoolSize;
  }
  /**
   * @param initialPoolSize the initialPoolSize to set
   */
  public void setInitialPoolSize(int initialPoolSize) {
    this.initialPoolSize = initialPoolSize;
  }
  /**
   * @return the maxPoolSize
   */
  public int getMaxPoolSize() {
    return maxPoolSize;
  }
  /**
   * @param maxPoolSize the maxPoolSize to set
   */
  public void setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }
  /**
   * @return the dsName
   */
  public String getDsName() {
    return dsName;
  }
  /**
   * @param dsName the dsName to set
   */
  public void setDsName(String dsName) {
    this.dsName = dsName;
  }
  /**
   * @return the pingQuery
   */
  public String getPingQuery() {
    return pingQuery;
  }
  /**
   * @param pingQuery the pingQuery to set
   */
  public void setPingQuery(String pingQuery) {
    this.pingQuery = pingQuery;
  }
  /**
   * @return the testTable
   */
  public String getTestTable() {
    return testTable;
  }
  /**
   * @param testTable the testTable to set
   */
  public void setTestTable(String testTable) {
    this.testTable = testTable;
  }
}

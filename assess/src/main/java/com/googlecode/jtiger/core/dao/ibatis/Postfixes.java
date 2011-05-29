package com.googlecode.jtiger.core.dao.ibatis;

/**
 * 定义SqlMap中statement id的名称规范。
 * @author Sam Lee
 *
 */
public final class Postfixes {
  /**
   * Constructor
   */
  private Postfixes() {
    
  }
  /**
   * The name of insert statement.
   */
  public static final String INSERT = ".insert";
  /**
   * The name of update statement.
   */
  public static final String UPDATE = ".update";
  /**
   * The name of delete statement.
   */
  public static final String DELETE = ".delete";
  /**
   * The name of delete by primary key statement.
   */
  public static final String DELETE_BY_ID = ".deleteById";
  /**
   * The name of select statement.
   */
  public static final String SELECT = ".select";
  /**
   * The name of select all statement.
   */
  public static final String SELECT_ALL = ".selectAll";
  /**
   * The name of select by map statement.
   */
  public static final String SELECT_MAP = ".selectByMap";
  /**
   * The name of select by SQL statement.
   */
  public static final String SELECT_SQL = ".selectBySql";
  /**
   * The name of count statement.
   */
  public static final String COUNT = ".count";
  /**
   * The name of select by primary key statement.
   */
  public static final String SELECT_BY_ID = ".selectById";
}

package com.googlecode.jtiger.core.dao.ibatis;

import org.hibernate.id.UUIDHexGenerator;

/**
 * GenericIBatisDAO 工具类
 * @author Sam
 */
@SuppressWarnings("rawtypes")
public final class IBatisHelper {
  /**
   * @see {@link UUIDHex}
   */
  private static UUIDHexGenerator uuid = new UUIDHexGenerator();

  /**
   * 生成UUID，可以作为主键。对应字段应为varchar(32).
   */
  public static String generateUUID() {
    if (uuid == null) {
      uuid = new UUIDHexGenerator();
    }
    return (String) uuid.generate(null, "");
  }

  /**
   * 构造SqlMap statement id
   */
  public static String stmtId(Object object, String postfix) {
    if (object == null) {
      return postfix;
    }

    String namespace = (object instanceof Class) ? ((Class) object).getName()
        : object.getClass().getName();
    return (!postfix.startsWith(".")) ? namespace + "." + postfix : namespace
        + postfix;
  }

  /**
   * 根据后缀为.select的Statement Id，构造对应的，用于计算总记录数的
   * Statement。计算规则是将.select替换为.count,如果没有namespace， 
   * 则直接在原statement id之后加.count.<br>
   * Example:
   * <br>
   * 
   * <pre>
   * String statementId = &quot;mynamespace.selectUsers&quot;;
   * IBatisHelper.getCountStatementId(statementId);
   * //returns mynamespace.countUsers
   * statementId = &quot;selectUsers&quot;;
   * IBatisHelper.getCountStatementId(statementId);//returns selectUsers.count
   * </pre>
   * 
   * @param statementId 原statement id
   * @return 用于count的statement
   */
  static String getCountStatementId(String statementId) {
    if (statementId.indexOf(Postfixes.SELECT) < 0) {
      return statementId + Postfixes.COUNT;
    } else {
      return statementId.replaceAll(Postfixes.SELECT, Postfixes.COUNT);
    }
  }

  /**
   * Private constructor
   */
  private IBatisHelper() {

  }  
}

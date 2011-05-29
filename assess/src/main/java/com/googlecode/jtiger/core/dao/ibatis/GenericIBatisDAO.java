package com.googlecode.jtiger.core.dao.ibatis;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.googlecode.jtiger.core.dao.support.Page;

/**
 * IBatis Dao的泛型基类. <p/> 
 * 继承于Spring的SqlMapClientDaoSupport,提供分页函数和若干便捷查询方法，
 * 并对返回值作了泛型类型转换.<p/>
 * 使用这些范型方法，要求SqlMap配置文件中 statement id符合命名标准，
 * 命名空间采用对应的full quality类名。如果没有 采用标准命名，则直接调用
 * <code>{@link #getSqlMapClientTemplate()}</code>。
 * <p/> example:<br>
 * 
 * <pre>
 * &lt;sqlMap namespace=&quot;com.systop.examples.User&quot;&gt;
 *     &lt;select id=&quot;selectByPrimaryKey&quot; 
 *     parameterClass=&quot;java.lang.Integer&quot;
 *             resultClass=&quot;com.systop.examples.User&quot;&gt;
 *             &lt;![CDATA[select id,name,pwd from users where id=#id#]]&gt;
 *     &lt;/select&gt;
 * &lt;/sqlMap&gt;
 * </pre>
 * <br>
 * 当使用分页方法（如:{@link #query(Class, Object, int, int)}）的时候，应使用
 * select作为statement id，而且需要一个计算总记录数的count statement，这个statement
 * 以count作为id。
 * 当一个模块多于一个分页查询的时候，可以直接使用{@link #query(String, Object, int, int)}
 * ,statement id使用selectXXX的形式，对应的count statement id 为countXXX
 * @see {@link Postfixes}
 * @author suwei
 * @author sam lee
 * @see SqlMapClientDaoSupport
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class GenericIBatisDAO extends SqlMapClientDaoSupport {
  
  /**
   * 返回<code>SqlMapClientTemplate</code>对象。替代
   * {@link SqlMapClientDaoSupport#getSqlMapClientTemplate()}方法
   * 一方面可以简化代码^_^,另一方方面，如果对BaseIBatisDAO使用了Spring的
   * AspactJ风格的事务代理之后，会导致SqlMapClientDaoSupport中的
   * <code>final</code>方法出现问题（总是返回<code>Null</code>）。
   * @return
   */
  public SqlMapClientTemplate getTemplate() {
    return getSqlMapClientTemplate();
  }

  /**
   * 根据Id（primary key），返回指定类型的对象.
   * 
   * @param <T> 指定返回类型
   * @param entityClass 指定类型和namespace
   * @param id primary key
   * @return Entity object or <code>null</code>.
   */
  public <T> T get(Class<T> entityClass, Serializable id) {
    String statementId = IBatisHelper.stmtId(entityClass, 
        Postfixes.SELECT_BY_ID);
    return (T) getTemplate().queryForObject(statementId, id);
  }

  /**
   * 获取全部对象
   */
  public <T> List<T> get(Class<T> entityClass) {
    return getTemplate().queryForList(
        IBatisHelper.stmtId(entityClass, Postfixes.SELECT_ALL), null);
  }

  /**
   * 新增对象
   */
  public void insert(Object object) {
    getTemplate().insert(IBatisHelper.stmtId(object, Postfixes.INSERT),
        object);
  }

  /**
   * 保存对象
   */
  public void update(Object object) {
    getTemplate().update(IBatisHelper.stmtId(object, Postfixes.UPDATE), 
        object);
  }

  /**
   * 删除对象
   */
  public void delete(Object object) {
    getTemplate().delete(IBatisHelper.stmtId(object, Postfixes.DELETE),
        object);
  }

  /**
   * 根据ID删除对象
   */
  public <T> void delete(Class<T> entityClass, Serializable id) {
    getTemplate().delete(
        IBatisHelper.stmtId(entityClass, Postfixes.DELETE_BY_ID), id);
  }

  /**
   * Map查询.
   * 
   * @param map 包含各种属性的查询参数
   */
  public <T> List<T> queryByMap(Class<T> entityClass, Map map) {
    String statementId = IBatisHelper.stmtId(entityClass, Postfixes.SELECT);
    return getTemplate().queryForList(statementId, map);
  }
  
  /**
   * Map查询.带分页。
   * @param pageNo 页码，first is 1
   * @param pageSize Page size
   * @return Instanse of Page or {@link Page#EMPTY_PAGE}
   * @param map 包含各种属性的查询参数
   */
  public Page queryByMap(Class entityClass, 
      Map map, int pageNo, int pageSize) {
    return query(entityClass, map, pageNo, pageSize);
  }
  /**
   * 执行查询
   * @param entityClass 查询的实体的class，用于指出SqlMap namespace
   * @param parameterObject 查询参数
   * @return List of entity objects or empty list
   */
  public List query(Class entityClass, Object parameterObject) {
    String statementId = IBatisHelper.stmtId(entityClass, Postfixes.SELECT);
    return getTemplate().queryForList(statementId, parameterObject);
  }
  
  /**
   * 根据指定的类别执行分页查询
   * @param entityClass 查询的实体的class，用于指出SqlMap namespace
   * @param parameterObject 查询参数
   * @param pageNo 页码，first is 1
   * @param pageSize Page size
   * @return Instanse of Page or {@link Page#EMPTY_PAGE}
   * @see {@link Page}
   * @see {@link GenericIBatisDAO#query(String, Object, int, int)}
   */
  public Page query(Class entityClass, Object parameterObject, int pageNo,
      int pageSize) {
    String statementId = IBatisHelper.stmtId(entityClass, Postfixes.SELECT);
    return this.query(statementId, parameterObject, pageNo, pageSize);
  }
  /**
   * 根据指定的Sql Map Statement Id，执行分页查询。要求statement id
   * 以select开头，对应的count statement id以count开头。并且两个id的后缀
   * 必须相同。比如:<br>
   * statement id 是 selectUsersByName
   * 则count statement id 是 countUsersByName.
   * @param statementId SqlMap statement id
   * @param parameterObject 查询参数
   * @param pageNo 页码，first is 1
   * @param pageSize Page size
   * @return Instanse of Page or {@link Page#EMPTY_PAGE}
   * @see {@link Page}
   */
  public Page query(String statementId, Object parameterObject, int pageNo,
      int pageSize) {
    assert (StringUtils.isNotBlank(statementId));
    //得到对应的计算总记录数的statementId
    String countStmtId = IBatisHelper.getCountStatementId(statementId);
    Integer totalCount = (Integer) getTemplate().queryForObject(countStmtId,
        parameterObject);
    if (totalCount == null || totalCount == 0) {
      logger.debug("Can't find any rows.");
      return Page.EMPTY_PAGE;
    }
    //首行记录在总结果中的index
    int startIndex = Page.start(pageNo, pageSize);
    List list = Collections.EMPTY_LIST;
    if (pageSize > 0 && pageNo > 0) {
      //分页查询
      list = getTemplate().queryForList(statementId, parameterObject,
          startIndex, pageSize);
    } else {
      list = getTemplate().queryForList(statementId, null);
    }
    return new Page(startIndex, totalCount, pageSize, list);
  }
}

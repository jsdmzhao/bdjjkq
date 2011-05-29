package com.googlecode.jtiger.core.webapp.struts2.action;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.dao.support.Page;
import com.googlecode.jtiger.core.model.BaseModel;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.core.util.ReflectUtil;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.table.limit.Limit;
import com.googlecode.jtiger.modules.ecside.table.limit.Sort;
import com.googlecode.jtiger.modules.ecside.util.RequestUtils;

/**
 * 使用ECSide的limit方式查询的基类。<code>DefaultCrudAction</code>:<br>
 * <ul>
 * <li>子类可以通过调用{@link #getPageNo()}方法,获得当前页码.</li>
 * <li>子类可以通过调用{@link #getPageSize()}方法,获得分页容量.</li>
 * <li>子类可以通过调用{@link #getSort()}方法,获得排序条件.</li>
 * <li>子类可以通过调用{@link #setupModelSort()}方法,为DetachedCriteria对象设置排序条件.</li>
 * <li>实现了{@link #restorePageData(Page)}方法.将查询结果以"items"作为名称返回HttpServletRequest.</li>
 * <li>使用{@link java.lang.Integer}作为ID的类型.</li>
 * </ul>
 * 
 * 
 * @author Sam Lee
 * 
 * @param <T> Action所管理的实体类型
 * @param <M> Action所使用的Manager的类型
 */
@SuppressWarnings( { "serial" })
public class DefaultCrudAction<T extends BaseModel, M extends BaseGenericsManager<T>>
    extends AbstractCrudAction<T, M> {
  /**
   * ECSide读取的数据在Request中的name
   */
  public static final String EC_DATA_NAME = "items";

  /**
   * 最大行数，用于设置limit
   */
  public static final int MAX_ROWS = 10000000;


  /**
   * 覆盖父类的save方法。在AbstractCrudAction类中save方法根据
   * model的id是否为null判断调用create或update。在DefaultCrudAction
   * 类中改为利用Hibernate的持久化机制自动判断更新或创建。
   * @see {@link com.systop.common.core.dao.hibernate.HibernateDao#saveOrUpdate(Object)}
   */
  @Override
  public String save() {
    Assert.notNull(getModel());
    try {
      if (extractId(getModel()) != null) {
        getManager().getDao().evict(getModel());
      }
      getManager().save(getModel());
      return SUCCESS;
    } catch (ApplicationException e) {
      addActionError(e.getMessage());
      return INPUT;
    }
  }
  
  /**
   * 得到由EcSide生成的page No.
   */
  protected int getPageNo() {
    int pageNo = RequestUtils.getPageNo(getRequest());

    return pageNo;
  }

  /**
   * 得到由EcSide生成的page size
   */
  
  protected int getPageSize() {
    int pageSize = RequestUtils.getCurrentRowsDisplayed(getRequest());
    int[] rowStartEnd = RequestUtils.getRowStartEnd(getRequest(), MAX_ROWS,
        Constants.DEFAULT_PAGE_SIZE);
    pageSize = rowStartEnd[1] - rowStartEnd[0];

    return pageSize;
  }

  /**
   * 从ec的Order中取得排序信息，并用于设置DetachedCriteria对象
   * @param criteria 被设置的DetachedCriteria对象.
   * @return 设置之后的DetachedCriteria对象.
   */
  protected DetachedCriteria setupSort(DetachedCriteria criteria) {    
    if (StringUtils.isNotBlank(getSortProperty())) {
      if (getSortDir().toLowerCase().equals("asc")) {
        criteria.addOrder(Property.forName(getSortProperty()).asc());
      } else {
        criteria.addOrder(Property.forName(getSortProperty()).desc());
      }
    }

    return criteria;
  }
  
  /**
   * 从ec的Order中取得排序信息，并用于设置hql之后
   * @param hql        hql
   * @param sortName   默认排序字段
   * @param sortDir    默认排序方向
   * @return 
   */
  protected String setupSort(String hql, String sortName, String sortDir) {    
  	hql = hql + " order by ";
    if (StringUtils.isNotBlank(getSortProperty())) {
      if (getSortDir().toLowerCase().equals("asc")) {
      	hql = hql + " " + getSortProperty() + " asc";
      } else {
      	hql = hql + " " + getSortProperty() + " desc";
      }
    } else {
    	if (StringUtils.isNotBlank(sortName) && StringUtils.isNotBlank(sortDir)) {
    		hql = hql + " " + sortName + " " + sortDir;
    	} else {
    		hql = hql + " " + sortName + " asc";
    	}
    }
    return hql;
  }
  
  /**
   * @return 返回排序字段名,如果不需要排序，则返回零长度String.
   */
  protected String getSortProperty() {
    Sort sort = getSort();
    return (sort == null) ? StringUtils.EMPTY : sort.getProperty();    
  }
  /**
   * @return 返回"asc"或"desc",如果不需要排序，则返回零长度String.
   */
  protected String getSortDir() {
    Sort sort = getSort();
    return (sort == null) ? StringUtils.EMPTY : sort.getSortOrder();    
  }
  /**
   * @return <code>Sort</code> of ec.
   */
  private Sort getSort() {
    Limit limit = RequestUtils.getLimit(getRequest(),
        TableConstants.EXTREME_COMPONENTS, MAX_ROWS,
        Constants.DEFAULT_PAGE_SIZE);

    return limit.getSort();
  }


  /**
   * 将按照ECSide的要求，将分页数据放到Request中.
   * <B>Note:</B>如果RequestHeader的Accept属性包含"x-json"，restorePageData方法不做任何操作.
   * @see {@link AbstractCrudAction#restorePageData(Page)}
   */
  @Override
  protected void restorePageData(Page page) {
    RequestUtils.initLimit(getRequest(), TableConstants.EXTREME_COMPONENTS,
        page.getRows(), Constants.DEFAULT_PAGE_SIZE);
    getRequest().setAttribute(EC_DATA_NAME, page.getData());
  }

  /**
   * @see {@link #AbstractCrudAction#convertId(Serializable)}
   */
  @Override
  protected Serializable convertId(Serializable id) {
    return (id != null) ? id.toString() : null; 
  }
  /**
   * @see {@link #AbstractCrudAction#extractId(BaseModel)}
   */
  @Override
  protected Serializable extractId(T model) {
    return (Serializable) ReflectUtil.get(model, "id");
  }
  /**
   * 缺省的pageQuery的实现,查询所有数据，不分页，不排序
   */
  @Override
  protected Page pageQuery() {
    DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
    return getManager().pageQuery(new Page(0, Constants.DEFAULT_PAGE_SIZE), dc);
  }
  
  /**
   * 返回一个根据当前分页条件而创建的Page对象，该对象常用于pageQuery的参数
   */
  protected Page pageOfBlock() {
    return new Page(Page.start(getPageNo(), getPageSize()), getPageSize(), true);
  }
}

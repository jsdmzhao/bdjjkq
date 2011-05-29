package com.googlecode.jtiger.core.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.core.dao.hibernate.BaseHibernateDao;
import com.googlecode.jtiger.core.dao.support.Page;
import com.googlecode.jtiger.core.util.GenericsUtil;

/**
 * 使用Hibernate作为持久化框架的实现的Manager基类.
 * 
 * @author Sam Lee
 * @version 3.0
 */
@SuppressWarnings("unchecked")
public class BaseGenericsManager<T> implements Manager<T> {
	/**
	 * Log,子类可以直接使用，不必重新声明
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Manager所管理的实体对象类型.
	 */
	protected Class<T> entityClass;

	/**
	 * Manager所使用的Dao对象.
	 */
	private BaseHibernateDao dao;

	/**
	 * @see Manager#getDao()
	 */
	public BaseHibernateDao getDao() {
		return dao;
	}

	@Autowired
	public void setBaseHibernateDao(BaseHibernateDao dao) {
		this.dao = dao;
	}

	/**
	 * @see Manager#create(java.lang.Object)
	 */
	@Transactional
	public void create(T entity) {
		getDao().save(entity);
	}

	/**
	 * @see Manager#get(java.io.Serializable)
	 */
	@Transactional(readOnly = true)
	public T get(Serializable id) {
		return getDao().get(getEntityClass(), id);
	}

	/**
	 * @see Manager#get()
	 */
	@Transactional(readOnly = true)
	public List<T> get() {
		return getDao().get(getEntityClass());
	}

	/**
	 * @see Manager#remove(java.lang.Object)
	 */
	@Transactional
	public void remove(T entity) {
		getDao().delete(entity);
	}

	/**
	 * @see Manager#save(java.lang.Object)
	 * @see BaseHibernateDao#saveOrUpdate(Object).
	 */
	@Transactional
	public void save(T entity) {
		getDao().saveOrUpdate(entity);
	}

	/**
	 * @see Manager#update(java.lang.Object)
	 */
	@Transactional
	public void update(T entity) {
		getDao().update(entity);
	}
	
	/**
   * @see Manager#merge(java.lang.Object)
   */
  @Transactional
  public T merge(T entity) {
    return getDao().merge(entity);
  }

	/**
	 * @see {@link BaseHibernateDao#findObject(String, Object...)}
	 */
	@Transactional(readOnly = true)
	public T findObject(String hql, Object... args) {
		return (T) getDao().findObject(hql, args);
	}

	/**
	 * @see {@link BaseHibernateDao#query(String, Object...)}
	 */
	@Transactional(readOnly = true)
	public List<T> query(String hql, Object... args) {
		return getDao().query(hql, args);
	}

	/**
	 * @see {@link BaseHibernateDao#pagedQuery(Page, DetachedCriteria)}
	 */
	@Transactional(readOnly = true)
	public Page pageQuery(Page page, DetachedCriteria criteria) {
		return getDao().pagedQuery(page, criteria);
	}

	/**
	 * @see {@link BaseHibernateDao#pagedQuery(Page, Criteria)}
	 */
	@Transactional(readOnly = true)
	public Page pageQuery(Page page, Criteria criteria) {
		return getDao().pagedQuery(page, criteria);
	}

	/**
	 * @see {@link BaseHibernateDao#pagedQuery(Page, String, Object...)}
	 */
	@Transactional(readOnly = true)
	public Page pageQuery(Page page, String hql, Object... args) {
		return getDao().pagedQuery(page, hql, args);
	}

	/**
	 * 取得entityClass的函数. JDK1.4不支持泛型的子类可以抛开Class entityClass,重载此函数达到相同效果。
	 */
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = GenericsUtil.getGenericClass(getClass());
		}
		return entityClass;
	}
}

package com.googlecode.jtiger.modules.cms.catalog.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.core.util.ResBundleUtil;
import com.googlecode.jtiger.modules.cms.catalog.model.Catalog;

@Service
public class CatalogManager extends BaseGenericsManager<Catalog> {
  
  /**
   * 返回首页栏目
   */
  @Transactional
  public Catalog getHome() {
    Catalog c = findObject("from Catalog c where c.isHome=true"); 
    if(c == null || StringUtils.isBlank(c.getId())) {
      logger.debug("New Site , create a home catalog.");
      c = createHome();
    }
    return c;
  }
  
  
  /**
   * 创建首页栏目
   */
  @Transactional
  public Catalog createHome() {
    String appName = ResBundleUtil.getString(Constants.RESOURCE_BUNDLE, "app.name", "");
    Catalog c = new Catalog();
    c.setName(appName + "首页");
    c.setIsHome(true);
    getDao().save(c);
    return c;
  }
  
  
  


  /**
   * @see BaseGenericsManager#save(java.lang.Object)
   */
  @Override @Transactional
  public void save(Catalog entity) {
    Assert.notNull(entity);
    if(entity.getParent() == null || StringUtils.isBlank(entity.getParent().getId())) {
      entity.setParent(getHome());
    } else {
      entity.setParent(get(entity.getParent().getId()));
    }
    entity.setLvl(entity.getParent().getLvl() + 1);
    entity.setIsHome(false);
    if(StringUtils.isNotBlank(entity.getId())) {
      if(StringUtils.equals(entity.getId(), entity.getParent().getId())) {
        throw new ApplicationException("上级栏目和本栏目重复");
      }
    }
    getDao().clear();
    getDao().merge(entity);
  }


  /**
   * 删除一个空的栏目
   */
  @Override  @Transactional
  public void remove(Catalog entity) {
    Assert.notNull(entity);
    Assert.hasLength(entity.getId());
    entity = getDao().load(Catalog.class, entity.getId());
    if(CollectionUtils.isNotEmpty(entity.getChildren())) {
      throw new ApplicationException("栏目下已经包含子栏目，不可删除，您可以将栏目设置为不可见。");
    }
    if(CollectionUtils.isNotEmpty(entity.getArticles())) {
      throw new ApplicationException("栏目下已经包含文章，不可删除，您可以将栏目设置为不可见。");
    }
    super.remove(entity);
  }

  /**
   * 栏目排序
   * @param sortedIds
   */
  @Transactional
  public void sort(String sortedIds) {
    if(StringUtils.isBlank(sortedIds)) {
      return;
    }    
    String[]ids = sortedIds.split(",");
    Session session = getDao().getSessionFactory().getCurrentSession();
    for(int i = 1; i <= ids.length; i++) {
      session.createQuery("update Catalog c set c.sortOrder=? where c.id=?")
      .setInteger(0, i).setString(1, ids[i - 1]).executeUpdate();
    }
  }
  

}

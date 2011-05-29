package com.googlecode.jtiger.modules.cms.catalog.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.core.webapp.struts2.action.AbstractCrudAction;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.googlecode.jtiger.modules.cms.catalog.model.Catalog;
import com.googlecode.jtiger.modules.cms.catalog.service.CatalogManager;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CatalogAction extends DefaultCrudAction<Catalog, CatalogManager> {
  private Catalog parent = new Catalog();
  /**
   * 排序后的id，用comma分隔
   */
  private String sortedIds;
  /**
   * 栏目列表，只列出当前栏目下的子栏目
   */
  public String index() {
    page = getManager().pageQuery(pageOfBlock(), 
        "from Catalog c where c.parent.id=? order by c.sortOrder", parent.getId());
    restorePageData(page);
        
    return "index";
  }
  /**
   * 新建一个栏目
   */
  public String editNew() {
    initParent();
    return "edit";
  }
  
  /**
   * 编辑一个栏目
   */
  public String edit() {
    setModel(getManager().get(getModel().getId()));
    parent = getManager().get(getModel().getParent().getId());
    getRequest().setAttribute("parent", parent);
    return "edit";
  }
  
  /**
   * 返回JSON格式的栏目树
   */
  public String catalogTree() {
    Map<String, Object> tree = getCatalogTree(null, true);
    String json = toJson(tree);
    logger.debug(json);
    renderJson("[" + json + "]");
    
    return  null;
  }
  /**
   * 将栏目（Catalog）转换为Map格式，并构建栏目树
   * @param parent 副栏目
   * @param nested 是否递归构建栏目树
   * @return Root栏目，children entry中包含子栏目
   */
  public Map<String, Object> getCatalogTree(Map<String, Object> parent, boolean nested) {
    if (parent == null || parent.isEmpty() || parent.get("id") == null) {
      parent = new HashMap<String, Object>();
      Catalog home = getHome();
      parent.put("text", home.getName());
      parent.put("id", home.getId());
      parent.put("expanded", true);
      //parent.put("cls", "folder");
    }
    // 得到子部门
    List<Catalog> catalogs = getManager().query(
        "from Catalog c where c.parent.id=? order by sortOrder", parent.get("id"));

    logger.debug("Catalog {} has {} children." , parent.get("text"), catalogs.size());
    // 转换所有子部门为Map对象，一来防止json造成延迟加载，
    // 二来可以符合Ext的数据要求.
    List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
    for (Iterator<Catalog> itr = catalogs.iterator(); itr.hasNext();) {
      Catalog c = itr.next();
      Map<String, Object> child = new HashMap<String, Object>();
      child.put("id", c.getId());
      child.put("text", c.getName());
      child.put("descn", c.getDescn());
     // child.put("cls", "folder");
      if (nested) { // 递归查询子栏目
        child = this.getCatalogTree(child, nested);
      }
      children.add(child);
    }
    if (children.isEmpty()) {
      parent.put("leaf", true);      
    } else {
      parent.put("children", children);
      //parent.put("childNodes", children);
      parent.put("leaf", false);
    }

    return parent;
  }
  /**
   * 栏目排序
   * @return
   */
  public String sort() {
    try {
      getManager().sort(sortedIds);
      render("success", "text/plain");
    } catch(Exception e) {
      render(e.getMessage(), "text/plain");
    }
    return null;
  }
  
  /**
   * 删除一个空栏目  
   */
  public String remove() {
    try {
      getManager().remove(getModel());
      render("success", "text/plain");
    } catch(Exception e) {
      render(e.getMessage(), "text/plain");
    }
    return null;
  }
  /**
   * 返回首页栏目
   */
  public Catalog getHome() {
    return getManager().getHome();
  }
  /**
   * @return the parent
   */
  public Catalog getParent() {
    return parent;
  }
  /**
   * @param parent the parent to set
   */
  public void setParent(Catalog parent) {
    this.parent = parent;
  }
  
  private void initParent() {
    if(parent == null || StringUtils.isBlank(parent.getId())) {
      parent = getManager().getHome();
    } else {
      parent = getManager().get(parent.getId());
    }
    getRequest().setAttribute("parent", parent);
    logger.debug("current parent catalog {}", parent.getName());
  }

  /**
   * @see AbstractCrudAction#prepare()
   */
  @Override
  public void prepare() {
    super.prepare();
    initParent();
  }
  /**
   * @return the sortedIds
   */
  public String getSortedIds() {
    return sortedIds;
  }
  /**
   * @param sortedIds the sortedIds to set
   */
  public void setSortedIds(String sortedIds) {
    this.sortedIds = sortedIds;
  }
}

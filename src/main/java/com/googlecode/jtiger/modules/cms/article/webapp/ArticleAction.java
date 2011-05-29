package com.googlecode.jtiger.modules.cms.article.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.googlecode.jtiger.modules.cms.article.model.Article;
import com.googlecode.jtiger.modules.cms.article.service.ArticleManager;
import com.googlecode.jtiger.modules.cms.catalog.model.Catalog;
import com.googlecode.jtiger.modules.security.user.model.User;
import com.googlecode.jtiger.modules.upload.webapp.FileAccessServlet;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ArticleAction extends DefaultCrudAction<Article, ArticleManager> {
  private Catalog catalog = new Catalog();
  private File cPic;
  private File tPic;
  private String cPicFileName;
  private String tPicFileName;
  private String urls; //附件URL，用|分隔
  private String sortIds; //排序文章ID用comma分隔
  
  /**
   * 文章列表
   */
  public String index() {
    String hql = "from Article a where 1=1";
    List<Object> args = new ArrayList<Object>(5);
    
    if(getModel() != null && getModel().getCatalog() != null 
        && StringUtils.isNotBlank(getModel().getCatalog().getId())) {
      hql += " and a.catalog.id=?";
      args.add(getModel().getCatalog().getId());
      //加载栏目数据
      catalog = getManager().getDao().get(Catalog.class, getModel().getCatalog().getId());
      getRequest().setAttribute("catalog", catalog);
      //查询当前栏目下置顶文章
      String topQL = "from Article a where a.catalog.id=? and a.isOnTop=true order by a.sortOrder";
      List<Article> tops = getManager().query(topQL, catalog.getId());
      getRequest().setAttribute("tops", tops);
    }
    
    if(StringUtils.isNotBlank(getModel().getTitle())) {
      hql += " and a.title like ?";
      args.add(MatchMode.ANYWHERE.toMatchString(getModel().getTitle()));
    }
    
    hql += " order by a.isChecked, a.isOnTop desc, a.updateTime desc";
    
    page = getManager().pageQuery(pageOfBlock(), hql, args.toArray());
    restorePageData(page);
        
    return "index";
  }
  
  /**
   * 编辑新文章
   */
  public String editNew() {
    if(getModel() != null && getModel().getCatalog() != null 
        && StringUtils.isNotBlank(getModel().getCatalog().getId())) {
      catalog = getManager().getDao().get(Catalog.class, getModel().getCatalog().getId());
      getModel().setCatalog(catalog);
      getRequest().setAttribute("catalog", catalog);
    }
    return "edit";
  }
  
  /**
   * 置顶
   */
  public String top() {
    getManager().top(getModel());
    return null;
  }
  
  /**
   * 编辑文章
   */
  public String edit() {
    editNew();
    setModel(getManager().get(getModel().getId()));
    return "edit";
  }
  /**
   * 置顶文章排序
   */
  public String sort() {
    try {
      getManager().sort(sortIds);
      render("success", "text/plain");
    } catch (Exception e) {
      render(e.getMessage(), "text/plain");
    }
    
    return null;
  }
  
  /**
   * 保存文章
   */
  public String save() {
    User user = getUser();
    if(StringUtils.isNotBlank(cPicFileName) && !FileAccessServlet.isPic(cPicFileName)) {
      addActionError("内容图片必须是jpg、png或gif格式。");
    }
    if(StringUtils.isNotBlank(tPicFileName) && !FileAccessServlet.isPic(tPicFileName)) {
      addActionError("标题图片必须是jpg、png或gif格式。");
    }
    try {
      getManager().save(getModel(), user, cPic, cPicFileName, tPic, tPicFileName, urls);      
    } catch (Exception e) {
      addActionError(e.getMessage());
      return INPUT;
    }
    return SUCCESS;
  }
  
  /**
   * 文章审核
   */
  public String check() {
    User user = getUser();
    if(user == null) {
      render("请登录！", "text/plain");
    }
    getModel().setCheckUser(user.getLoginId());
    try {
      getManager().check(getModel());
      render("success", "text/plain");
    } catch(Exception e) {
      e.printStackTrace();
      render(e.getMessage(), "text/plain");
    }
    
    return null;
  }
  /**
   * @return the catalog
   */
  public Catalog getCatalog() {
    return catalog;
  }

  /**
   * @param catalog the catalog to set
   */
  public void setCatalog(Catalog catalog) {
    this.catalog = catalog;
  }

  /**
   * @return the cPic
   */
  public File getCPic() {
    return cPic;
  }

  /**
   * @param cPic the cPic to set
   */
  public void setCPic(File cPic) {
    this.cPic = cPic;
  }

  /**
   * @return the tPic
   */
  public File getTPic() {
    return tPic;
  }

  /**
   * @param tPic the tPic to set
   */
  public void setTPic(File tPic) {
    this.tPic = tPic;
  }

  /**
   * @return the cPicFileName
   */
  public String getCPicFileName() {
    return cPicFileName;
  }

  /**
   * @param cPicFileName the cPicFileName to set
   */
  public void setCPicFileName(String cPicFileName) {
    this.cPicFileName = cPicFileName;
  }

  /**
   * @return the tPicFileName
   */
  public String getTPicFileName() {
    return tPicFileName;
  }

  /**
   * @param tPicFileName the tPicFileName to set
   */
  public void setTPicFileName(String tPicFileName) {
    this.tPicFileName = tPicFileName;
  }

  /**
   * @return the urls
   */
  public String getUrls() {
    return urls;
  }

  /**
   * @param urls the urls to set
   */
  public void setUrls(String urls) {
    this.urls = urls;
  }

  /**
   * @return the sortIds
   */
  public String getSortIds() {
    return sortIds;
  }

  /**
   * @param sortIds the sortIds to set
   */
  public void setSortIds(String sortIds) {
    this.sortIds = sortIds;
  }

}

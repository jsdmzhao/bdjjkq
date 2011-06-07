package com.googlecode.jtiger.modules.cms.catalog.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.cms.article.model.Article;

/**
 * 栏目
 * @author catstiger@gmail.com
 *
 */
@Entity
@Table(name = "assess_cms_catalogs")
public class Catalog extends BaseIdModel {
  /**
   * 名称
   */
  private String name;
  /**
   * 描述
   */
  private String descn;
  
  /**
   * 访问该栏目的URL
   */
  private String url;
  /**
   * 模板
   */
  private String tpl;
  
  /**
   * 是否可用
   */
  private Boolean isEnabled = true;
  
  /**
   * 是否可见
   */
  private Boolean isVisible = true;
  
  /**
   * 是否外部栏目
   */
  private Boolean isExternal = false;
  
  /**
   * 是否首页
   */
  private Boolean isHome = true;
  
  /**
   * 是否在导航条显示
   */
  private Boolean isListInNav = true;
  
  /**
   * 栏目顺序
   */
  private Integer sortOrder = 10000;
  
  /**
   * 栏目级别（深度）
   */
  private Integer lvl = 0;
  
  /**
   * 上级栏目
   */
  private Catalog parent;
  
  /**
   * 子栏目
   */
  private Set<Catalog> children = new HashSet<Catalog>(0);
  
  /**
   * 栏目下的文章
   */
  private Set<Article> articles = new HashSet<Article>(0);
  
  /**
   * 栏目显示在option总的附加文本
   */
  private transient String lvlText;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the descn
   */
  @Column(columnDefinition = "varchar2(2000)")
  public String getDescn() {
    return descn;
  }

  /**
   * @param descn the descn to set
   */
  public void setDescn(String descn) {
    this.descn = descn;
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
   * @return the tpl
   */
  public String getTpl() {
    return tpl;
  }

  /**
   * @param tpl the tpl to set
   */
  public void setTpl(String tpl) {
    this.tpl = tpl;
  }

  /**
   * @return the isEnabled
   */
  public Boolean getIsEnabled() {
    return isEnabled;
  }

  /**
   * @param isEnabled the isEnabled to set
   */
  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  /**
   * @return the isVisible
   */
  public Boolean getIsVisible() {
    return isVisible;
  }

  /**
   * @param isVisible the isVisible to set
   */
  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  /**
   * @return the isExternal
   */
  public Boolean getIsExternal() {
    return isExternal;
  }

  /**
   * @param isExternal the isExternal to set
   */
  public void setIsExternal(Boolean isExternal) {
    this.isExternal = isExternal;
  }

  /**
   * @return the isHome
   */
  public Boolean getIsHome() {
    return isHome;
  }

  /**
   * @param isHome the isHome to set
   */
  public void setIsHome(Boolean isHome) {
    this.isHome = isHome;
  }

  /**
   * @return the isListInNav
   */
  public Boolean getIsListInNav() {
    return isListInNav;
  }

  /**
   * @param isListInNav the isListInNav to set
   */
  public void setIsListInNav(Boolean isListInNav) {
    this.isListInNav = isListInNav;
  }

  /**
   * @return the parent
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  public Catalog getParent() {
    return parent;
  }

  /**
   * @param parent the parent to set
   */
  public void setParent(Catalog parent) {
    this.parent = parent;
  }

  /**
   * @return the children
   */
  @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "parent")
  public Set<Catalog> getChildren() {
    return children;
  }

  /**
   * @param children the children to set
   */
  public void setChildren(Set<Catalog> children) {
    this.children = children;
  }

  /**
   * @return the sortOrder
   */
  @Column(columnDefinition = "integer default 10000")
  public Integer getSortOrder() {
    return sortOrder;
  }

  /**
   * @param sortOrder the sortOrder to set
   */
  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  /**
   * @return the articles
   */
  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "catalog", fetch = FetchType.LAZY)
  public Set<Article> getArticles() {
    return articles;
  }

  /**
   * @param articles the articles to set
   */
  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }

  /**
   * @return the lvl
   */
  @Column(columnDefinition = "integer default 0")
  public Integer getLvl() {
    return lvl;
  }

  /**
   * @param lvl the lvl to set
   */
  public void setLvl(Integer lvl) {
    this.lvl = lvl;
  }

  /**
   * @return the lvlText
   */
  @Transient
  public String getLvlText() {
    return lvlText;
  }

  /**
   * @param lvlText the lvlText to set
   */
  public void setLvlText(String lvlText) {
    this.lvlText = lvlText;
  }
  
  
}

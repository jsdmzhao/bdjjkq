package com.googlecode.jtiger.modules.cms.article.model;

import java.util.Date;
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

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.cms.attachment.model.Attachment;
import com.googlecode.jtiger.modules.cms.catalog.model.Catalog;

@Entity
@Table(name = "assess_cms_articles")
public class Article extends BaseIdModel {
  /**
   * 标题
   */
  private String title;
  
  /**
   * 副标题
   */
  private String subhead;
  
  /**
   * 短标题
   */
  private String shortTitle;
  
  /**
   * 内容图片
   */
  private String contentPic;
  
  /**
   * 标题图片
   */
  private String titlePic;
  
  /**
   * 内容
   */
  private String content;
  
  /**
   * 摘要
   */
  private String abstraction;
  
  /**
   * 关键字
   */
  private String keyWord;
  
  /**
   * 创建时间
   */
  private Date createTime;
  
  /**
   * 更新时间
   */
  private Date updateTime;
  
  /**
   * 创建者
   */
  private String createUser;
  
  /**
   * 更新者
   */
  private String updateUser;
  
  /**
   * 审核者
   */
  private String checkUser;
  
  /**
   * 是否审核通过
   */
  private Boolean isChecked = false;
  
  /**
   * 文章顺序
   */
  private Integer sortOrder;
  
  /**
   * 所属栏目
   */
  private Catalog catalog;
  
  /**
   * 是否置顶
   */
  private Boolean isOnTop = false;
  
  /**
   * 附件
   */
  private Set<Attachment> attachments = new HashSet<Attachment>(0);

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the subhead
   */
  public String getSubhead() {
    return subhead;
  }

  /**
   * @param subhead the subhead to set
   */
  public void setSubhead(String subhead) {
    this.subhead = subhead;
  }

  /**
   * @return the shortTitle
   */
  public String getShortTitle() {
    return shortTitle;
  }

  /**
   * @param shortTitle the shortTitle to set
   */
  public void setShortTitle(String shortTitle) {
    this.shortTitle = shortTitle;
  }

  /**
   * @return the contentPic
   */
  public String getContentPic() {
    return contentPic;
  }

  /**
   * @param contentPic the contentPic to set
   */
  public void setContentPic(String contentPic) {
    this.contentPic = contentPic;
  }

  /**
   * @return the titlePic
   */
  public String getTitlePic() {
    return titlePic;
  }

  /**
   * @param titlePic the titlePic to set
   */
  public void setTitlePic(String titlePic) {
    this.titlePic = titlePic;
  }

  /**
   * @return the content
   */
  @Column(columnDefinition = "varchar2(2000)")
  public String getContent() {
    return content;
  }

  /**
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * @return the createTime
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * @param createTime the createTime to set
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * @return the updateTime
   */
  public Date getUpdateTime() {
    return updateTime;
  }

  /**
   * @param updateTime the updateTime to set
   */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  /**
   * @return the createUser
   */
  public String getCreateUser() {
    return createUser;
  }

  /**
   * @param createUser the createUser to set
   */
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  /**
   * @return the updateUser
   */
  public String getUpdateUser() {
    return updateUser;
  }

  /**
   * @param updateUser the updateUser to set
   */
  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  /**
   * @return the isChecked
   */
  public Boolean getIsChecked() {
    return isChecked;
  }

  /**
   * @param isChecked the isChecked to set
   */
  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
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
   * @return the abstraction
   */
  @Column(columnDefinition = "varchar2(2000)")
  public String getAbstraction() {
    return abstraction;
  }

  /**
   * @param abstraction the abstraction to set
   */
  public void setAbstraction(String abstraction) {
    this.abstraction = abstraction;
  }

  /**
   * @return the keyWord
   */
  public String getKeyWord() {
    return keyWord;
  }

  /**
   * @param keyWord the keyWord to set
   */
  public void setKeyWord(String keyWord) {
    this.keyWord = keyWord;
  }

  /**
   * @return the catalog
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cms_catalog_id")
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
   * @return the attatchments
   */
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = {CascadeType.MERGE})
  public Set<Attachment> getAttachments() {
    return attachments;
  }

  /**
   * @param attatchments the attatchments to set
   */
  public void setAttachments(Set<Attachment> attatchments) {
    this.attachments = attatchments;
  }

  /**
   * @return the checkUser
   */
  public String getCheckUser() {
    return checkUser;
  }

  /**
   * @param checkUser the checkUser to set
   */
  public void setCheckUser(String checkUser) {
    this.checkUser = checkUser;
  }

  /**
   * @return the isOnTop
   */
  @Column(columnDefinition = "SMALLINT default 0")
  public Boolean getIsOnTop() {
    return isOnTop;
  }

  /**
   * @param isOnTop the isOnTop to set
   */
  public void setIsOnTop(Boolean isOnTop) {
    this.isOnTop = isOnTop;
  }
  
}

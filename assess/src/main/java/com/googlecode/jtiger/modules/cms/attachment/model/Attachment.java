package com.googlecode.jtiger.modules.cms.attachment.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.cms.article.model.Article;

@Entity
@Table(name = "cms_attachments")
public class Attachment extends BaseIdModel {
  /**
   * 访问附件的URL
   */
  private String url;
  /**
   * 原始文件名
   */
  private String name;
  /**
   * 扩展名
   */
  private String extName;
  /**
   * 简介
   */
  private String descn;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 创建者
   */
  private String createUser;
  
  /**
   * 所属文章
   */
  private Article article;

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
   * @return the extName
   */
  public String getExtName() {
    return extName;
  }

  /**
   * @param extName the extName to set
   */
  public void setExtName(String extName) {
    this.extName = extName;
  }

  /**
   * @return the descn
   */
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
   * @return the article
   */
  @ManyToOne
  @JoinColumn(name = "cms_article_id")
  public Article getArticle() {
    return article;
  }

  /**
   * @param article the article to set
   */
  public void setArticle(Article article) {
    this.article = article;
  }
}

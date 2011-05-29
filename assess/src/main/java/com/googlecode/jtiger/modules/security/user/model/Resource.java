package com.googlecode.jtiger.modules.security.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.googlecode.jtiger.core.model.BaseIdModel;

@Entity
@Table(name = "resources")
public class Resource extends BaseIdModel {
  /**
   * 资源名称
   */
  private String name;
  
  /**
   * 资源URL
   */
  private String url;
  
  /**
   * 排列顺序
   */
  private Integer sortNo;
  

  /**
   * 父资源，通常是一个模块，例如/senior/*表示一个模块，
   * /senior/index.htm表示模块里面的一个资源
   */
  private Resource parent;
  
  private Set<Resource> children = new HashSet<Resource>(0);
  
  /**
   * 能够访问此资源的角色
   */
  private Set<Role> roles = new HashSet<Role>(0);

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  @Column(columnDefinition = "varchar(500)")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  


  public Integer getSortNo() {
    return sortNo;
  }

  public void setSortNo(Integer sortNo) {
    this.sortNo = sortNo;
  }


  
  @ManyToOne
  @JoinColumn(name = "parent_id")
  public Resource getParent() {
    return parent;
  }

  public void setParent(Resource parent) {
    this.parent = parent;
  }
  
  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
  public Set<Resource> getChildren() {
    return children;
  }

  public void setChildren(Set<Resource> children) {
    this.children = children;
  }

  /**
   * @return the roles
   */
  @ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "resources", targetEntity = Role.class)
  public Set<Role> getRoles() {
    return roles;
  }

  /**
   * @param roles the roles to set
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
}

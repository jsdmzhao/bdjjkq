package com.googlecode.jtiger.modules.security.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.googlecode.jtiger.core.model.BaseModel;


/**
 * The persistent class for the roles database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "roles", uniqueConstraints = {})
public class Role extends BaseModel implements Serializable {

  /**
   * id
   */
  private String id;

  /**
   * 角色描述
   */
  private String descn;

  /**
   * 角色名称
   */
  private String name;
  
  /**
   * 是否系统角色
   */
  private String isSys;

   
  
  /**
   * 具有此角色的部门
  private Set<Dept> depts = new HashSet<Dept>(0);
  */

  /**
   * 父角色
   */
  private Role parentRole;

  /**
   * 子角色
   */
  private Set<Role> childRoles = new HashSet<Role>(0);

  /**
   * 具有此角色的用户
   */
  private Set<User> users = new HashSet<User>(0);
  
  /**
   * 此角色能够访问的资源
   */
  private Set<Resource> resources = new HashSet<Resource>(0);

  /**
   * 页面checkboxList提交数据保存
   */
  private String[] methodIds = new String[0];

  /**
   * 缺省构造器
   */
  public Role() {
  }

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @Column(name = "ID",  nullable = false, length = 32)//unique = true,
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "descn")
  public String getDescn() {
    return this.descn;
  }

  public void setDescn(String descn) {
    this.descn = descn;
  }

  @Column(name = "name")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @ManyToOne(cascade = {}, fetch = FetchType.LAZY)
  @JoinColumn(name = "parent")
  public Role getParentRole() {
    return this.parentRole;
  }

  public void setParentRole(Role parentRole) {
    this.parentRole = parentRole;
  }

  @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "parentRole")
  public Set<Role> getChildRoles() {
    return this.childRoles;
  }

  public void setChildRoles(Set<Role> childRoles) {
    this.childRoles = childRoles;
  }

  @ManyToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "roles", targetEntity = User.class)
  public Set<User> getUsers() {
    return this.users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  /**
   * 得到页面提交的测项'id'数组
   * @return
   */
  @Transient
  public String[] getMethodIdArray() {
    return methodIds;
  }

  /**
   * @see Object#equals(Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Role)) {
      return false;
    }
    Role castOther = (Role) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId())
        .isEquals();
  }

  /**
   * @see Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }

  /**
   * @return the isSys
   */
  @Column(name = "is_sys", columnDefinition = "char(1) default '0'")
  public String getIsSys() {
    return isSys;
  }

  /**
   * @param isSys the isSys to set
   */
  public void setIsSys(String isSys) {
    this.isSys = isSys;
  }

  /**
   * @return the resources
   */
  @ManyToMany(targetEntity = Resource.class, cascade = {}, fetch = FetchType.LAZY)
  @JoinTable(name = "role_res", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "res_id") })
  public Set<Resource> getResources() {
    return resources;
  }

  /**
   * @param resources the resources to set
   */
  public void setResources(Set<Resource> resources) {
    this.resources = resources;
  }
}

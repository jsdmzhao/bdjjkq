package com.googlecode.jtiger.modules.hr.employee.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.modules.hr.HrConstants;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * 员工表 The persistent class for the employees database table.
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_employees")
public class Employee extends BaseIdModel implements Serializable {

  /**
   * 地址
   */
  private String address;

  /**
   * 出身日期
   */
  private Date birthday;

  /**
   * 证件号码
   */
  private String credentialNo;
  /**
   * 警号
   */
  private String policeNo;

  /**
   * 学历
   */
  private String degree;

  /**
   * 传真
   */
  private String fax;

  /**
   * 民族
   */
  private String folk;

  /**
   * 毕业时间
   */
  private Date graduateDate;

  /**
   * 宅电
   */
  private String hTel;

  /**
   * 参加工作时间
   */
  private Date jobDate;

  /**
   * 婚姻状况
   */
  private String married = HrConstants.MARRIED_N;

  /**
   * 手机
   */
  private String mobil;

  /**
   * msn
   */
  private String msn;

  /**
   * 姓名或昵称
   */
  private String name;

  /**
   * 照片
   */
  private String photo;

  /**
   * 职位
   */
  private String place;

  /**
   * 政治面貌
   */
  private String political;

  /**
   * qq
   */
  private String qq;

  /**
   * 户口所在地
   */
  private String registeredPos;

  /**
   * 毕业学校
   */
  private String school;

  /**
   * 性别
   */
  private String sex = HrConstants.GENT;

  /**
   * 专业
   */
  private String speciality;

  /**
   * 邮编
   */
  private String zip;

  /**
   * 对应的User
   */
  private User user = new User();

  /**
   * 对应的Dept
   */
  private Dept dept;
  
  /**
   * 对应的部门下的主管
   */
  private Set<Dept> leaders = new HashSet<Dept>(0);
  
  /**
   * 对应的部门下的上级主管
   */
  private Set<Dept> superior = new HashSet<Dept>(0);
  
  /**
   * 对应的部门下的上级分管领导
   */
  private Set<Dept> subSuperior = new HashSet<Dept>(0);
  
  /**
   * 缺省构造
   */
  public Employee() {
  }
  
  @OneToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "leader")
  public Set<Dept> getLeaders() {
    return leaders;
  }

  public void setLeaders(Set<Dept> leaders) {
    this.leaders = leaders;
  }

  @OneToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "superior")
  public Set<Dept> getSuperior() {
    return superior;
  }

  public void setSuperior(Set<Dept> superior) {
    this.superior = superior;
  }

  @OneToMany(cascade = { }, fetch = FetchType.LAZY, mappedBy = "subSuperior")
  public Set<Dept> getSubSuperior() {
    return subSuperior;
  }

  public void setSubSuperior(Set<Dept> subSuperior) {
    this.subSuperior = subSuperior;
  }


  @Column(name = "address")
  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "birthday")
  public Date getBirthday() {
    return this.birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  @Column(name = "credential_no")
  public String getCredentialNo() {
    return this.credentialNo;
  }

  public void setCredentialNo(String credentialNo) {
    this.credentialNo = credentialNo;
  }

  @Column(name = "degrees")
  public String getDegree() {
    return this.degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  @Column(name = "fax")
  public String getFax() {
    return this.fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  @Column(name = "folk")
  public String getFolk() {
    return this.folk;
  }

  public void setFolk(String folk) {
    this.folk = folk;
  }

  @Column(name = "graduate_date")
  public Date getGraduateDate() {
    return this.graduateDate;
  }

  public void setGraduateDate(Date graduateDate) {
    this.graduateDate = graduateDate;
  }

  @Column(name = "H_tel")
  public String getHTel() {
    return this.hTel;
  }

  public void setHTel(String hTel) {
    this.hTel = hTel;
  }

  @Column(name = "job_date")
  public Date getJobDate() {
    return this.jobDate;
  }

  public void setJobDate(Date jobDate) {
    this.jobDate = jobDate;
  }

  @Column(name = "married")
  public String getMarried() {
    return this.married;
  }

  public void setMarried(String married) {
    this.married = married;
  }

  @Column(name = "mobil")
  public String getMobil() {
    return this.mobil;
  }

  public void setMobil(String mobil) {
    this.mobil = mobil;
  }

  @Column(name = "msn")
  public String getMsn() {
    return this.msn;
  }

  public void setMsn(String msn) {
    this.msn = msn;
  }

  @Column(name = "name")
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name = "photo")
  public String getPhoto() {
    return this.photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  @Column(name = "place")
  public String getPlace() {
    return this.place;
  }

  public void setPlace(String place) {
    this.place = place;
  }

  @Column(name = "political")
  public String getPolitical() {
    return this.political;
  }

  public void setPolitical(String political) {
    this.political = political;
  }

  @Column(name = "qq")
  public String getQq() {
    return this.qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  @Column(name = "registered_pos")
  public String getRegisteredPos() {
    return this.registeredPos;
  }

  public void setRegisteredPos(String registeredPos) {
    this.registeredPos = registeredPos;
  }

  @Column(name = "school")
  public String getSchool() {
    return this.school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  @Column(name = "sex")
  public String getSex() {
    return this.sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  @Column(name = "speciality")
  public String getSpeciality() {
    return this.speciality;
  }

  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }

  @Column(name = "zip")
  public String getZip() {
    return this.zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @ManyToOne(cascade = { }, fetch = FetchType.LAZY)
  @JoinColumn(name = "dept_id")
  public Dept getDept() {
    return this.dept;
  }

  public void setDept(Dept dept) {
    this.dept = dept;
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Employee)) {
      return false;
    }
    Employee castOther = (Employee) other;
    return new EqualsBuilder().append(this.getId(), castOther.getId())
        .isEquals();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return new HashCodeBuilder().append(getId()).toHashCode();
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return new ToStringBuilder(this).append("id", getId()).toString();
  }

public String getPoliceNo() {
	return policeNo;
}

public void setPoliceNo(String policeNo) {
	this.policeNo = policeNo;
}
}
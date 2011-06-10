package com.googlecode.jtiger.modules.hr.dept.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.googlecode.jtiger.core.model.BaseModel;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;

/**
 * 部门表 The persistent class for the depts database table.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "assess_depts", uniqueConstraints = {})
public class Dept extends BaseModel implements Serializable {
	/**
	 * id
	 */
	private Integer id;

	/**
	 * 部门描述
	 */
	private String descn;

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 部门编号规则：上级部门编号+两位数字，从1自动排；
	 */
	private String serialNo;
	/**
	 * 大队/中队编号
	 */
	private String deptCode;

	/**
	 * 部门类别
	 */
	private String deptSort = "0";
	/**
	 * 机构类别
	 */
	private String deptType = "0";

	/**
	 * 上级部门
	 */
	private Dept parentDept;
	/**
	 * 部门主管
	 */
	private Employee leader;
	/**
	 * 上级主管
	 */
	private Employee superior;
	/**
	 * 上级分管领导
	 */
	private Employee subSuperior;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 邮编
	 */
	private String zip;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 网站
	 */
	private String homePage;
	/**
	 * 电子信箱
	 */
	private String email;
	/**
	 * 开户行
	 */
	private String bank;
	/**
	 * 账号
	 */
	private String bankAccount;

	/**
	 * 部门记录
	 */
	private Set<Dept> childDepts = new HashSet<Dept>(0);

	/**
	 * 部门下的员工
	 */
	private Set<Employee> employees = new HashSet<Employee>(0);

	/**
	 * 缺省构造
	 */
	public Dept() {
	}

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", /* unique = true, */nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	@Column(name = "serial_no")
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "dept_sort")
	public String getDeptSort() {
		return deptSort;
	}

	public void setDeptSort(String deptSort) {
		this.deptSort = deptSort;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "leader")
	public Employee getLeader() {
		return leader;
	}

	public void setLeader(Employee leader) {
		this.leader = leader;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "superior")
	public Employee getSuperior() {
		return superior;
	}

	public void setSuperior(Employee superior) {
		this.superior = superior;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_superior")
	public Employee getSubSuperior() {
		return subSuperior;
	}

	public void setSubSuperior(Employee subSuperior) {
		this.subSuperior = subSuperior;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "fax")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "home_page")
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "bank")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "bank_account")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public Dept getParentDept() {
		return this.parentDept;
	}

	public void setParentDept(Dept parentDept) {
		this.parentDept = parentDept;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "parentDept")
	public Set<Dept> getChildDepts() {
		return this.childDepts;
	}

	public void setChildDepts(Set<Dept> childDepts) {
		this.childDepts = childDepts;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "dept")
	public Set<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Transient
	public boolean getHasChild() {
		return this.getChildDepts().size() > 0;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Dept)) {
			return false;
		}
		Dept castOther = (Dept) other;
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

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
}
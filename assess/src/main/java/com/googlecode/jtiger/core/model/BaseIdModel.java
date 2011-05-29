package com.googlecode.jtiger.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.StringUtils;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author calvin
 */
//JPA Entity基类的标识
@MappedSuperclass
public abstract class BaseIdModel extends BaseModel {

	protected String id;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", length = 32)
	public String getId() {
	  return (!StringUtils.hasText(id)) ? null : id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 是否是新对象
	 * @return
	 */
	@Transient
	public boolean isNew() {
	  return !StringUtils.hasText(id);
	}
}

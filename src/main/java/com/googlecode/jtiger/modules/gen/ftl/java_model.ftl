package ${packageName ? default('')}.${className?lower_case}.model;

import com.googlecode.jtiger.core.model.BaseIdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

<#list javaImports as imp>
import ${imp?default('')};
</#list>

/**
 * Hibernate Mapping Class for ${tableName ? default('')}
 */
@Entity
@Table(name = "${tableName ? default('')}")
public class ${className ? default('')} extends BaseIdModel {
  <#list table.columns as column>
 
  <#if !column.primaryKey>
    <#if column.description?exists && column.description != ''>    
  /**
   * ${column.description ?default('')}
   */
    </#if>
    <#if column.required>
  @NotNull
    </#if>
  private ${javaTypes[column_index]} ${properties[column_index]};
  </#if>
  </#list>
  
  <#list properties as prop>
  <#if !table.columns[prop_index].primaryKey>
  
  @Column(name = "${table.columns[prop_index].name}")
  public ${javaTypes[prop_index]} get${prop[0]?upper_case}${prop[1..]}() {
    return ${prop};
  }
  
  public void set${prop[0]?upper_case}${prop[1..]}(${javaTypes[prop_index]} ${prop}) {
    this.${prop} = ${prop};
  }
  </#if>
  </#list>
}
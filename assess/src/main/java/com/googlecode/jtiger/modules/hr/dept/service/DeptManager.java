package com.googlecode.jtiger.modules.hr.dept.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.hr.company.service.CompanyManager;
import com.googlecode.jtiger.modules.hr.dept.DeptConstants;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 部门管理类
 * @author WBB
 * @version 3.0
 */
@Service()
public class DeptManager extends BaseGenericsManager<Dept> {
  /**
   * 用于计算部门编号
   */
  private DeptSerialNoManager serialNoManager;

  @Autowired
  public void setSerialNoManager(DeptSerialNoManager serialNoManager) {
    this.serialNoManager = serialNoManager;
  }

  /**
   * 公司管理
   */
  private CompanyManager companyManager;

  @Autowired
  public void setCompanyManager(CompanyManager companyManager) {
    this.companyManager = companyManager;
  }

  public CompanyManager getCompanyManager() {
    return companyManager;
  }

  /**
   * 保存部门信息
   */
  @Override
  @Transactional
  public void save(Dept dept) {
    Assert.notNull(dept);
    logger.debug("Parent dept {}", dept.getParentDept());
    // 查询上级部门并建立双向关联
    Dept parent = dept.getParentDept();
    getDao().evict(parent);

    logger.debug("Parent dept Id {}", dept.getParentDept().getId());
    parent = get(dept.getParentDept().getId());
    if (parent != null) {
      parent.getChildDepts().add(dept);
      dept.setParentDept(parent);
    } else {
      // 上级部门在数据库中不存在，可能选择上级部门后，此上级部门被删除
      throw new ApplicationException("上级部门不存在");
    }

    if (dept.getId() == null) {// 新建的部门设置部门编号
      dept.setSerialNo(serialNoManager.getSerialNo(dept));
      // 设置部门类型为普通部门
      dept.setDeptSort(DeptConstants.DEPT_SORT_DEPT);
    }
    // FIXME: Clear the session or it will throw an exception with the message:
    // "dentifier of an instance was altered from xxx to xxx"
    getDao().getHibernateTemplate().clear();
    getDao().saveOrUpdate(dept);
  }

  /**
   * 返回部门树形列表
   * @return
   */
  public Map<String, Object> getDeptTree() {
    return getDeptTree(null);
  }

  /**
   * 返回部门树形列表
   * @param deptId 要排除的部门ID
   * @return
   */
  public Map<String, Object> getDeptTree(Integer deptId) {
    Dept comPany = companyManager.getCompany();
    // 判断是否设置了公司
    if (comPany == null) {
      return Collections.emptyMap();
    }
    // 设置公司信息
    Map<String, Object> parMap = new HashMap<String, Object>();
    parMap.put("id", comPany.getId());
    parMap.put("text", comPany.getName());
    return getDeptTree(parMap, true, deptId);
  }

  /**
   * 返回部门树形列表，每一个部门用一个<code>java.util.Map</code>表示，子部门 用Map的“childNodes”key挂接一个
   * <code>java.util.List</code>.<br>
   * 本方法供DWR调用，Map中key符合jsam dojo Tree的要求。
   * @param parent 父部门
   * @param nested 是否递归查询子部门，true表示递归查询子部门
   * @param deptId 要排除的部门
   * @return
   */
  public Map<String, Object> getDeptTree(Map<String, Object> parent, boolean nested, Integer deptId) {
    // 得到子部门
    List<Dept> depts = getByParentId((Integer) parent.get("id"));

    logger.debug("Dept {} has {} children.", parent.get("text"), depts.size());
    // 转换所有子部门为Map对象，一来防止dwr造成延迟加载，
    // 二来可以符合Ext的数据要求.
    List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
    for (Iterator<Dept> itr = depts.iterator(); itr.hasNext();) {
      Dept dept = itr.next();
      // 修改部门信息时当前部门与所有子部门不加入到编辑部门树中，防止当前部门选其子部门与本部门为上级部门
      if (deptId != null && deptId.equals(dept.getId())) {
        continue;
      }
      Map<String, Object> child = new HashMap<String, Object>();
      child.put("id", dept.getId());
      child.put("text", dept.getName());
      if (nested) { // 递归查询子部门
        child = this.getDeptTree(child, nested, deptId);
      }
      children.add(child);
    }
    if (!children.isEmpty()) {
      parent.put("children", children);
      parent.put("childNodes", children);
      parent.put("leaf", false);
    } else {
      parent.put("leaf", true);
    }

    return parent;
  }

  /**
   * 根据指定的父部门id查询子部门
   */
  @SuppressWarnings( { "unchecked" })
  public List<Dept> getByParentId(Integer parentDeptId) {
    if (parentDeptId == null) {
      return Collections.EMPTY_LIST;
    }
    return query("from Dept d where d.parentDept.id = ?", parentDeptId);
  }
}

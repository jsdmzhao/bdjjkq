package com.googlecode.jtiger.modules.hr.company.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.hr.dept.DeptConstants;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 公司管理类
 * @author WBB
 * @version 3.0
 */
@Service()
public class CompanyManager extends BaseGenericsManager<Dept> {
  /**
   * 得到公司对象
   * @return
   */
  public Dept getCompany() {
    String hql = "from Dept where deptSort = ?";
    return findObject(hql, DeptConstants.DEPT_SORT_COMPANY);
  }
  
  /** 
   * 保存公司对象
   * @see com.systop.core.service.BaseGenericsManager#save(java.lang.Object)
   */
  @Override
  @Transactional
  public void save(Dept dept) {
    if (dept.getId() == null) {
      //新增公司设置部门类别为公司对象
      dept.setDeptSort(DeptConstants.DEPT_SORT_COMPANY);
    }
    getDao().saveOrUpdate(dept);
  }
}

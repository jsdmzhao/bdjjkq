package com.googlecode.jtiger.modules.hr.dept.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.core.util.StringUtil;
import com.googlecode.jtiger.modules.hr.dept.DeptConstants;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;

/**
 * 计算部门编号<br>
 * <ul>
 * <li>同级部门，从01开始，最多99</li>
 * <li>部门编号等于"上级部门编号,部门编号",例如， 行政部编号01，下属小车组编号为01,02</li>
 * </ul>
 * @author Sam Lee Create time:2008-1-24 上午09:59:00
 */
@SuppressWarnings("unchecked")
@Service()
public class DeptSerialNoManager extends BaseGenericsManager<Dept> {
  /**
   * 返回给定部门的部门编号
   * @param parentDept 给定部门
   * 
   */
  public String getSerialNo(final Dept dept) {
    Assert.notNull(dept, "The given Dept must not be null.");
    String serialNo = null;
    List<String> serialNos = getDao().query(
        "select d.serialNo from Dept d "
            + "where d.parentDept = ? order by d.serialNo desc",
        dept.getParentDept());
    if (serialNos == null || serialNos.isEmpty()) {
      serialNo = buildFirstSerialNo(dept.getParentDept());
    } else { // 有同级部门
      String maxSerialNo = (String) serialNos.get(0); // 同级部门最大编号
      // 为公司下的部门部门编号如:01 02 03
      if (DeptConstants.DEPT_SORT_COMPANY.equals(dept.getParentDept()
          .getDeptSort())) {
        if (StringUtils.isBlank(maxSerialNo)) {
          serialNo = buildFirstSerialNo(dept.getParentDept());
        } else {
          Integer serial = StringUtil.getNumFromSerial(maxSerialNo);
          serialNo = buildSerialNo(dept.getParentDept(), (serial + 1));
        }

      } else {
        final String[] splited = org.springframework.util.StringUtils
            .commaDelimitedListToStringArray(maxSerialNo);
        if (splited == null || splited.length == 0) {
          serialNo = buildFirstSerialNo(dept.getParentDept());
        } else {// 计算当前部门编号
          Integer serial = StringUtil
              .getNumFromSerial(splited[splited.length - 1]);
          serialNo = buildSerialNo(dept.getParentDept(), (serial + 1));
        }
      }
    }
    logger.debug("Create serial No." + serialNo);
    return serialNo;
  }
  
  /**
   * 重设所有部门编号。
   */
  public void updateAllSerialNo() {
    List<Dept> tops = getDao().query(
        "from Dept d where d.parentDept.deptSort = ?",
        DeptConstants.DEPT_SORT_COMPANY);
    Map<Integer, String> serialMap = new HashMap<Integer, String>(100); // 用于存放部门ID-SerialNo
    // 计算所有部门编号，并将部门ID-部门编号的对应关系存入serialMap
    Integer serial = 1;
    for (Dept top : tops) {
      top.setSerialNo(buildSerialNo(top, serial));
      serialMap.put(top.getId(), buildSerialNo(top, serial));
      if (top.getChildDepts().size() > 0) {
        this.updateChildrenSerialNo(top, serialMap);
      }
      serial = serial + 1;
    }
    // 批量更新部门编号
    Set<Integer> ids = serialMap.keySet();
    for (Integer id : ids) {
      Dept dept = get(id);
      getDao().evict(dept);
      dept.setSerialNo((String) serialMap.get(id));
      getDao().getHibernateTemplate().update(dept);
    }
  }

  /**
   * 更新所有子部门的SerialNo
   */
  public void updateChildrenSerialNo(Dept parent, Map<Integer, String> serialMap) {
    logger.debug("Update " + parent.getName() + "'s children serial No.");
    Set<Dept> children = parent.getChildDepts();
    Integer serial = 1;
    for (Dept child : children) {
      child.setSerialNo(buildSerialNo(parent, serial));
      serialMap.put(child.getId(), buildSerialNo(parent, serial));
      if (child.getChildDepts().size() > 0) {
        updateChildrenSerialNo(child, serialMap);
      }
      serial = serial + 1;
    }
  }
  
  /**
   * 构建第一个部门的编号
   */
  private static String buildFirstSerialNo(Dept parent) {
    // 上级部门为公司
    if (DeptConstants.DEPT_SORT_COMPANY.equals(parent.getDeptSort())) {
      return DeptConstants.FIRST_SERIAL_NO;
    } else {
      return new StringBuffer(100).append(parent.getSerialNo()).append(",")
          .append(DeptConstants.FIRST_SERIAL_NO).toString();
    }
  }

  /**
   * 构建部门编号
   */
  private static String buildSerialNo(Dept parent, Integer serial) {
    // 上级部门为公司
    if (DeptConstants.DEPT_SORT_COMPANY.equals(parent.getDeptSort())) {
      return StringUtil.zeroPadding(serial, 2);
    } else {
      return new StringBuffer(100).append(parent.getSerialNo()).append(",")
          .append(StringUtil.zeroPadding(serial, 2)).toString();
    }
  }
  
  /** 
   * 2009-02-20注释
   * 根据上级部门和当前部门的前一个编号，获得当前部门编号的数字表示 ， 用于重置所有部门编号:<br>
   * 首先根据上级部门编号，计算同级部门的第一个编号，以后便再此基础上 +1即可。

  private Integer getSerial(Dept dept, final Integer preSerial) {
    Integer serial = null;
    if (preSerial == null) {
      String serialNo = getSerialNo(dept);
      if (StringUtils.isNotBlank(serialNo)) {
        String[] splits = org.springframework.util.StringUtils
            .commaDelimitedListToStringArray(serialNo);
        if (splits != null && splits.length > 0) {
          serial = StringUtil.getNumFromSerial(splits[splits.length - 1]);
        } else {
          serial = 0;
        }
      }
    } else {
      serial = preSerial + 1;
    }

    return serial;
  }

  /**
   * 重设所有部门编号。

  public void updateAllSerialNo() {
    List<Dept> tops = getDao().query(
        "from Dept d where d.parentDept.deptSort = ?",
        DeptConstants.DEPT_SORT_COMPANY);
    Map serialMap = new HashMap(100); // 用于存放部门ID-SerialNo
    // 计算所有部门编号，并将部门ID-部门编号的对应关系存入serialMap
    Integer serial = null;
    for (Dept top : tops) {
      serial = this.getSerial(top, serial);
      // String serialNo = getSerialNo(top);
      top.setSerialNo(buildSerialNo(null, serial));
      serialMap.put(top.getId(), buildSerialNo(null, serial));
      // getDao().saveObject(top);
      if (top.getChildDepts().size() > 0) {
        this.updateChildrenSerialNo(top, serialMap);
      }
    }
    // 批量更新部门编号
    Set<Integer> ids = serialMap.keySet();
    for (Integer id : ids) {
      Dept dept = get(id);
      getDao().evict(dept);
      dept.setSerialNo((String) serialMap.get(id));
      getDao().getHibernateTemplate().update(dept);
    }
  }

  /**
   * 更新所有子部门的SerialNo

  public void updateChildrenSerialNo(Dept parent, Map serialMap) {
    logger.debug("Update " + parent.getName() + "'s children serial No.");
    Set<Dept> children = parent.getChildDepts();
    Integer serial = null;
    for (Dept child : children) {
      serial = this.getSerial(child, serial);
      child.setSerialNo(buildSerialNo(parent, serial));
      serialMap.put(child.getId(), buildSerialNo(parent, serial));
      // getDao().saveObject(child);
      if (child.getChildDepts().size() > 0) {
        updateChildrenSerialNo(child, serialMap);
      }
    }
  }
  */
}

package com.googlecode.jtiger.assess.core.webapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.core.model.BaseIdModel;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.employee.model.Employee;
import com.googlecode.jtiger.modules.security.user.model.User;

/**
 * <pre>
 * 考核系统中,
 * 各个Action类都需要用到的根据当前用户得到部门和列出部门方法, 故提取出公共父类.
 * 
 * @author DELPHI
 * 
 * @param <T>
 * @param <M>
 * </pre>
 */
@SuppressWarnings("serial")
public abstract class AssessBaseAction<T extends BaseIdModel, M extends BaseGenericsManager<T>>
		extends DefaultCrudAction<T, M> {
	/**
	 * 得到当前用户所在部门
	 * 
	 * @return
	 */
	protected Dept getUserDept() {
		Dept dept = null;
		User user = getUser();
		Set<Employee> emps = user.getEmployees();
		Iterator<Employee> itr = emps.iterator();
		while (itr.hasNext()) {
			dept = itr.next().getDept();
			break;
		}

		return dept;
	}

	/**
	 * <pre>
	 * 得到当前用户所在部门下的所有子部门,得到部门代码(code)和部门名称的键值对集合
	 * 主要用途是传递给界面中的部门下拉选择框.
	 * 
	 * @return
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getDeptCodeList() {
		Map<String, String> map = new HashMap<String, String>(0);
		StringBuffer buf = new StringBuffer(
				"from Dept d where d.parentDept.id = ? and d.deptType = ? ");
		/**
		 * <pre>
		 * 由于考核范围目前只是涉及到了市级大队,而以后的需求还可能涉及县级大队还有各个大队中队,]
		 * 这个地方的变数比较大,就在properties配置文件中写出了,
		 * 如果这个值是"all",则列出所有当前人员所在部门的下级部门
		 * 如果这个值不是"all"则应该是以英文逗号分隔开的各个部门的代码(code)值,以限定范围
		 * </pre>
		 */
		// 涉及部分部门
		if (!"all".equalsIgnoreCase(AssessConstants.ASSESS_DEPT_CODES)) {
			String[] codes = AssessConstants.ASSESS_DEPT_CODES.split(",");
			buf.append("and (");
			for (int i = 0; i < codes.length - 1; i++) {
				buf.append("d.deptCode = '").append(codes[i]).append("' or ");
			}
			buf.append("d.deptCode = '").append(codes[codes.length - 1])
					.append("')");
			// 涉及全部部门
		} else {
			// ----------此处将会添加代码以满足新需求...
		}
		buf.append("order by d.orderNo");
		logger.debug(buf.toString());
		List list = getManager().query(buf.toString(), getUserDept().getId(),
				AssessConstants.DEPT_TYPE_1);

		for (Object o : list) {
			Dept d = (Dept) o;
			map.put(d.getDeptCode(), d.getName());
		}

		return map;
	}
}

package com.googlecode.jtiger.assess.evaluate.stat.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.googlecode.jtiger.assess.core.webapp.AssessBaseAction;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecord;
import com.googlecode.jtiger.assess.evaluate.stat.service.EvaluateRecordStatManager;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.dept.service.DeptManager;
import com.ibm.icu.util.Calendar;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EvaluateRecordStatAction extends
		AssessBaseAction<EvaluateRecord, EvaluateRecordStatManager> {
	/**考核部门*/
	private Dept dept;
	/**部门编号*/
	private String deptCode;
	@Autowired
	private DeptManager deptManager;
	/**
	 * 环比
	 * 
	 * @return
	 */
	public String linkRelatie() {		
		Assert.notNull(deptCode);
		dept = deptManager.getDeptByCode(deptCode);
		StringBuffer buf = new StringBuffer();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		List<EvaluateRecord> list = getEvaluateRecords(year, dept);
		for (EvaluateRecord er : list) {
			buf.append(year + "年-" + er.getMonth() + "月;").append(
					er.getTotal() + "\\n");
		}

		getRequest().setAttribute("csvData", buf.toString());

		return "linkRelatie";
	}

	public String index() {
		return "index";
	}

	/**
	 * 得到同年所有数据
	 * 
	 * @param year
	 * @param dept
	 * @return
	 */
	private List<EvaluateRecord> getEvaluateRecords(int year, Dept dept) {
		String hql = "from EvaluateRecord er where er.year = ? and er.dept.id = ? order by er.month";

		return getManager().query(hql, new Object[] { year, dept.getId() });
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}

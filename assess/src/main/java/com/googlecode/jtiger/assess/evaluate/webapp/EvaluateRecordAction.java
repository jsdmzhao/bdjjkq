package com.googlecode.jtiger.assess.evaluate.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.core.webapp.AssessBaseAction;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecord;
import com.googlecode.jtiger.assess.evaluate.service.EvaluateRecordManager;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskType;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskTypeManager;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.dept.service.DeptManager;
import com.ibm.icu.util.Calendar;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EvaluateRecordAction extends
		AssessBaseAction<EvaluateRecord, EvaluateRecordManager> {
	/** 考核日期 */
	private Date evalDate;
	/** 部门Manager */
	@Autowired
	private DeptManager deptManager;
	/** 考核标准类别Manager */
	@Autowired
	private TaskTypeManager taskTypeManager;
	/** 考核年份 */
	private int year;
	/** 考核月份 */
	private int month;
	/***/
	private List<EvaluateRecord> items = new ArrayList<EvaluateRecord>(0);
	/** 查询排名条件,考核标准类型 */
	private String taskTypeId;

	private String deptCode;

	/**
	 * 评分
	 * 
	 * @return
	 */
	public String index() {

		Map<String, String> map = getDeptCodeList();
		getRequest().setAttribute("depts", map);
		page = getManager().pageQuery(pageOfBlock(), "from EvaluateRecord er order by er.year desc er.month desc er.dept.deptCode");
		restorePageData(page);

		return INDEX;
	}

	/**
	 * 前台展示页面
	 * 
	 * @return
	 */
	public String front() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		int date = c.get(Calendar.DATE);
		int year = c.get(Calendar.YEAR);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		String hql = "from EvaluateRecord er where er.recordType = 'daily' and er.recordTime >= ? order by er.total desc";
		List<EvaluateRecord> list = getManager().query(hql,
				new Object[] { c.getTime() });
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("month", month);
		getRequest().setAttribute("date", date);
		getRequest().setAttribute("list", list);

		return "front";
	}

	/**
	 * 考核查询
	 * 
	 * @return
	 */
	public String query() {
		deptCode = getRequest().getParameter("deptCode");
		if (StringUtils.isNotBlank(deptCode)) {
			Dept dept = deptManager.getDeptByCode(deptCode);
			queryByDept(dept);
		} else {
			String hql = "from EvaluateRecord er where er.year = ? and er.month = ?"
					+ buildDeptParamStr(getDeptCodeList().keySet())+"order by er.dept.deptCode";

			page = getManager().pageQuery(pageOfBlock(), hql,
					new Object[] { year, month });
			restorePageData(page);

		}

		return INDEX;
	}

	/**
	 * 转到查询页面
	 * 
	 * @return
	 */
	public String toRank() {
		return "toRank";
	}

	/**
	 * 排名
	 * 
	 * @return
	 */
	public String rank() {
		StringBuffer buf = new StringBuffer(
				"from EvaluateRecordDetail erd  where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		// 年份
		if (year != 0) {
			buf.append("and erd.evaluateRecord.year = ? ");
			args.add(year);
		}
		// 月份
		if (month != 0) {
			buf.append("and erd.evaluateRecord.month = ? ");
			args.add(month);
		}
		// 考核标准类别
		if (StringUtils.isNotBlank(taskTypeId)) {
			buf.append("and erd.taskType.id = ? ");
			args.add(taskTypeId);
		}
		buf.append("order by erd.total desc");

		page = getManager().pageQuery(pageOfBlock(), buf.toString(),
				args.toArray());
		restorePageData(page);

		return "rank";
	}

	/**
	 * 根据部门查询
	 * 
	 * @param dept
	 * @return
	 */
	private void queryByDept(Dept dept) {

		String hql = "from EvaluateRecord er where er.dept.id = ? and er.year = ? and er.month = ?";

		page = getManager().pageQuery(pageOfBlock(), hql,
				new Object[] { dept.getId(), year, month });
		restorePageData(page);

	}

	private String buildDeptParamStr(Set<String> deptCodeSet) {
		StringBuffer buf = new StringBuffer(" and er.dept.deptCode in (");
		Object[] codes = deptCodeSet.toArray();

		for (int i = 0; i < codes.length - 1; i++) {
			buf.append("'").append(codes[i]).append("'").append(",");
		}
		buf.append("'").append(codes[codes.length - 1]).append("')");

		return buf.toString();
	}

	public String evalAll() {
		return INDEX;
	}

	public Date getEvalDate() {
		return evalDate;
	}

	public void setEvalDate(Date evalDate) {
		this.evalDate = evalDate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<EvaluateRecord> getItems() {
		return items;
	}

	public void setItems(List<EvaluateRecord> items) {
		this.items = items;
	}

	public String getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public List<TaskType> getTaskTypes() {
		String hql = "from TaskType tt where tt.type = '0'";

		return taskTypeManager.query(hql);
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

}

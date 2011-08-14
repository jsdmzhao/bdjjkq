package com.googlecode.jtiger.assess.evaluate.webapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	/**
	 * 评分
	 * 
	 * @return
	 */
	public String index() {

		Map<String, String> map = getDeptCodeList();
		getRequest().setAttribute("depts", map);
		page = getManager().pageQuery(pageOfBlock(), "from EvaluateRecord er");
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
		String deptCode = getRequest().getParameter("deptCode");
		if (StringUtils.isNotBlank(deptCode)) {
			Dept dept = deptManager.query("from Dept d where d.deptCode = ?",
					deptCode).get(0);
			List<EvaluateRecord> list = queryByDept(dept);
			if (CollectionUtils.isEmpty(list)) {
				getManager().eval(dept, year, month);
				list = queryByDept(dept);
			}
			items = list;
		} else {
			for (String code : getDeptCodeList().keySet()) {
				Dept dept = deptManager.query(
						"from Dept d where d.deptCode = ?", code).get(0);
				List<EvaluateRecord> list = queryByDept(dept);
				if (CollectionUtils.isEmpty(list)) {
					getManager().eval(dept, year, month);
					list = queryByDept(dept);
				}

				items.addAll(list);
			}
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
			buf.append("and erd.type.id = ? ");
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
	private List<EvaluateRecord> queryByDept(Dept dept) {

		String hql = "from EvaluateRecord er where er.dept.id = ? and er.year = ? and er.month = ?";
		List<EvaluateRecord> list = getManager().query(hql,
				new Object[] { dept.getId(), year, month });
		return list;
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

}

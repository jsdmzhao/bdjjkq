package com.googlecode.jtiger.assess.evaluate.webapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecord;
import com.googlecode.jtiger.assess.evaluate.service.EvaluateRecordManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.dept.service.DeptManager;
import com.ibm.icu.util.Calendar;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EvaluateRecordAction extends
		DefaultCrudAction<EvaluateRecord, EvaluateRecordManager> {
	/** 考核日期 */
	private Date evalDate;
	/***/
	@Autowired
	private DeptManager deptManager;

	private int year;
	private int month;
	private List<EvaluateRecord> items = new ArrayList<EvaluateRecord>(0);

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
	 * @return
	 */
	public String front(){
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		
		String hql = "from EvaluateRecord er where er.year = ? and er.month = ? order by er.total desc";
		List<EvaluateRecord> list = getManager().query(hql, new Object[]{year,month});
		getRequest().setAttribute("year", year);
		getRequest().setAttribute("month", month);
		getRequest().setAttribute("list",list);
		
		return "front";
	}

	public String query() {
		String deptCode = getRequest().getParameter("deptCode");
		if (StringUtils.isNotBlank(deptCode)) {
			Dept dept = deptManager.query("from Dept d where d.deptCode = ?",
					deptCode).get(0);
			List<EvaluateRecord> list = queryByDept(dept);
			if (CollectionUtils.isEmpty(list)) {
				eval(dept, year, month);
				list = queryByDept(dept);
			}
			items = list;
		} else {
			for (String code : getDeptCodeList().keySet()) {
				Dept dept = deptManager.query(
						"from Dept d where d.deptCode = ?", code).get(0);
				List<EvaluateRecord> list = queryByDept(dept);
				if (CollectionUtils.isEmpty(list)) {
					eval(dept, year, month);
					list = queryByDept(dept);
				}

				items.addAll(list);
			}
		}

		return INDEX;
	}

	private List<EvaluateRecord> queryByDept(Dept dept) {

		String hql = "from EvaluateRecord er where er.dept.id = ? and er.year = ? and er.month = ?";
		List<EvaluateRecord> list = getManager().query(hql,
				new Object[] { dept.getId(), year, month });
		return list;
	}

	private void eval(Dept dept, int year, int month) {

		EvaluateRecord evaluateRecord = new EvaluateRecord();
		evaluateRecord.setDept(dept);
		evaluateRecord.setRecordTime(new java.util.Date());
		evaluateRecord.setYear(year);
		evaluateRecord.setMonth(month);

		getManager().save(evaluateRecord);

		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DATE, 0);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		Date beginDate = c.getTime();

		c.set(Calendar.MONTH, month);

		Date endDate = c.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		logger.debug(sf.format(beginDate));
		logger.debug(sf.format(endDate));

		getManager().evalByDept(dept, beginDate, endDate, year, month,
				evaluateRecord);

	}

	public String evalAll() {
		return INDEX;
	}

	private Map<String, String> getDeptCodeList() {
		Map<String, String> map = new HashMap<String, String>(0);

		map.put("130604", "一大队");
		map.put("130603", "二大队");
		map.put("130602", "三大队");
		map.put("130641", "四大队");
		map.put("130642", "五大队");
		map.put("130643", "六大队");

		return map;
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

}

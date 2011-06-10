package com.googlecode.jtiger.assess.transgress.stat.webapp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.transgress.stat.StatCondition;
import com.googlecode.jtiger.assess.transgress.stat.model.TransgressStat;
import com.googlecode.jtiger.assess.transgress.stat.service.TransgressStatManager;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressCustomStatCondition;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressType;
import com.googlecode.jtiger.assess.transgress.statcfg.model.VehicleUseCode;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressCustomStatConditionManager;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressTypeManager;
import com.googlecode.jtiger.assess.transgress.statcfg.service.VehicleUseCodeManager;
import com.googlecode.jtiger.assess.transgress.statproperties.service.TransgressStatPropertiesManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.googlecode.jtiger.modules.hr.dept.model.Dept;
import com.googlecode.jtiger.modules.hr.dept.service.DeptManager;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SimpleTransgressStatAction extends
		DefaultCrudAction<TransgressStat, TransgressStatManager> {
	/** 条件1--部门,指定了则查指定部门,未指定,查询全部部门 */
	/** 指定部门 */
	private String deptId;
	/** 条件2--时间区间,指定了,则查指定时间区间,未指定,则查当天(系统时间前一天00:00至当天00:00) */
	/** 选定时间区间查询--起始时间 */
	private Date beginTime;
	/** 选定时间区间查询--截至时间 */
	private Date endTime;
	/** 条件3--违法行为范围,必须选择,以免全库查询 */
	/** 用户选择的违法行为代码 */
	private String[] transgressActionCodes;
	/** 条件4--车辆使用性质, */
	/** 界面传递过来的车辆使用性质代码 */
	private String[] vehicleUseCodes;
	/** 发现时间/处理时间 */
	private String findOrDealWith;
	/** 查询结果表头 */
	@SuppressWarnings("unchecked")
	private List title = new ArrayList();
	/** 是否union VIO_FORCE表 */
	private String unionForce;
	@Autowired
	private DeptManager deptManager;
	/** 统计条件manger */
	@Autowired
	private TransgressCustomStatConditionManager conditionManager;
	@Autowired
	private TransgressStatPropertiesManager tspm;
	@Autowired
	private TransgressTypeManager ttManager;
	@Autowired
	private VehicleUseCodeManager vehicleUseCodeManager;

	public String index() {
		List<Dept> depts = deptManager
				.query("from Dept d where d.deptType = '1'");
		getRequest().setAttribute("depts", depts);
		List<TransgressType> firstLevelTypes = ttManager.getFirstLevelTypes();
		getRequest().setAttribute("firstLevelTypes", firstLevelTypes);

		// 列出所有机动车使用性质实体
		List<VehicleUseCode> allVehicleUseCodes = vehicleUseCodeManager
				.getAllVehicleUseCodes();
		getRequest().setAttribute("allVehicleUseCodes", allVehicleUseCodes);

		return INDEX;
	}

	@SuppressWarnings("unchecked")
	public String simpleStat() {
		List items = new ArrayList();
		StatCondition statCondition = new StatCondition();
		// 设置统计类型是否是每天定制查询
		if (beginTime == null && endTime == null) {
			statCondition.setEachDayStat(true);
		} else {
			statCondition.setEachDayStat(false);
		}
		// 得到定制的统计条件
		TransgressCustomStatCondition customCondition = conditionManager.get()
				.get(0);
		// 如果是每日定制统计
		if (statCondition.isEachDayStat()) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			String todayStr = sf.format(c.getTime());
			statCondition.setEndHourMinute(todayStr + " "
					+ customCondition.getEndHourMinute());

			// 得到前一天
			c.add(Calendar.DAY_OF_MONTH, -1);
			// c.add(Calendar.YEAR, -10);
			String yestDayStr = sf.format(c.getTime());

			statCondition.setBeginHourMinute(yestDayStr + " "
					+ customCondition.getBeginHourMinute());
		} else {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statCondition.setBeginHourMinute(sf.format(beginTime));
			statCondition.setEndHourMinute(sf.format(endTime));
		}
		if (StringUtils.isNotBlank(unionForce)) {
			statCondition.setUnionForce(true);
		} else {
			statCondition.setUnionForce(false);
		}
		// title.add("");
		title.add("合计");
		// 违法行为代码
		statCondition
				.setTransgressActionCodesStr(buildTransgressActionCodesStr());
		// 机动车使用性质
		statCondition.setVehicleUseCodes(buildVehicleUseCodeSStr());
		statCondition.setTimeCondition(getRequest().getParameter(
				"timeCondition"));
		// 得到当前登录用户所在部门的子部门代码编号,名称集合
		Map<String, String> deptCodeMap = getDeptCodeList();
		Iterator<String> itr = deptCodeMap.keySet().iterator();
		BigDecimal total = new BigDecimal(0);
		List itemResult = new ArrayList();
		while (itr.hasNext()) {
			String code = itr.next();
			// String name = deptCodeMap.get(code);

			statCondition.setDeptCode(code);

			List<Map<String, Object>> list = getManager().stat(statCondition);
			BigDecimal current = (BigDecimal) list.get(0).get("TOTAL_COUNT");
			itemResult.add(current);
			total = total.add(current);
			title.add(deptCodeMap.get(code));
		}
		itemResult.add(total);
		items.add(itemResult);

		getRequest().setAttribute("items", items);
		getRequest().setAttribute("statDate",
				new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		getRequest().setAttribute("statBeginTime",
				statCondition.getBeginHourMinute());
		getRequest().setAttribute("statEndTime",
				statCondition.getEndHourMinute());
		getRequest().setAttribute("transgressStatProperties", tspm.getIt());
		getRequest().setAttribute("title", title);

		return "stat";
	}

	/**
	 * 构建机动车使用性质字符串
	 * 
	 * @return
	 */
	private String buildVehicleUseCodeSStr() {
		StringBuffer buf = new StringBuffer();
		if (vehicleUseCodes != null && vehicleUseCodes.length > 0) {
			for (int i = 0; i < vehicleUseCodes.length - 1; i++) {
				buf.append("'").append(vehicleUseCodes[i]).append("',");
			}
			buf.append("'").append(vehicleUseCodes[vehicleUseCodes.length - 1])
					.append("'");
		}
		return buf.toString();
	}

	/**
	 * 构建违法行为代码字符串
	 * 
	 * @return
	 */
	private String buildTransgressActionCodesStr() {
		StringBuffer buf = new StringBuffer();
		if (transgressActionCodes != null && transgressActionCodes.length > 0) {
			for (int i = 0; i < transgressActionCodes.length - 1; i++) {
				buf.append("'").append(transgressActionCodes[i]).append("',");
			}
			buf.append("'").append(
					transgressActionCodes[transgressActionCodes.length - 1])
					.append("'");
		}
		return buf.toString();
	}

	/**
	 * 得到当前登录用户所在部门的子部门编号集合 支队,得到其下所有大队, 大队,得到其下所有中队
	 * 
	 * @FixMe由于部门没有完善,此方法仅仅适于支队----------待完善 
	 *                                       130604,130603,130602,130641,130642,130643
	 * @return
	 */
	private Map<String, String> getDeptCodeList() {
		Map<String, String> map = new HashMap<String, String>(0);
		if (StringUtils.isNotBlank(deptId)) {
			Dept dept = deptManager.get(Integer.valueOf(deptId));
			map.put(dept.getDeptCode(), dept.getName());
		} else {
			map.put("130604", "一大队");
			map.put("130603", "二大队");
			map.put("130602", "三大队");
			map.put("130641", "四大队");
			map.put("130642", "五大队");
			map.put("130643", "六大队");
		}

		return map;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String[] getTransgressActionCodes() {
		return transgressActionCodes;
	}

	public void setTransgressActionCodes(String[] transgressActionCodes) {
		this.transgressActionCodes = transgressActionCodes;
	}

	public String[] getVehicleUseCodes() {
		return vehicleUseCodes;
	}

	public void setVehicleUseCodes(String[] vehicleUseCodes) {
		this.vehicleUseCodes = vehicleUseCodes;
	}

	public String getFindOrDealWith() {
		return findOrDealWith;
	}

	public void setFindOrDealWith(String findOrDealWith) {
		this.findOrDealWith = findOrDealWith;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getUnionForce() {
		return unionForce;
	}

	public void setUnionForce(String unionForce) {
		this.unionForce = unionForce;
	}
}

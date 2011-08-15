package com.googlecode.jtiger.assess.transgress.stat.webapp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.core.webapp.AssessBaseAction;
import com.googlecode.jtiger.assess.transgress.stat.StatCondition;
import com.googlecode.jtiger.assess.transgress.stat.model.TransgressStat;
import com.googlecode.jtiger.assess.transgress.stat.service.TransgressStatManager;
import com.googlecode.jtiger.assess.transgress.statcfg.StatCfgConstants;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressCustomStatCondition;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressCustomStatConditionManager;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressStatItemManager;
import com.googlecode.jtiger.assess.transgress.statproperties.service.TransgressStatPropertiesManager;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransgressStatAction extends
		AssessBaseAction<TransgressStat, TransgressStatManager> {

	/** 统计项mangaer */
	@Autowired
	private TransgressStatItemManager itemManager;
	/** 统计条件manger */
	@Autowired
	private TransgressCustomStatConditionManager conditionManager;
	/** 选定时间区间查询--起始时间 */
	private Date beginTime;
	/** 选定时间区间查询--截至时间 */
	private Date endTime;

	@SuppressWarnings("unchecked")
	private List items = new ArrayList();
	@SuppressWarnings("unchecked")
	private List title = new ArrayList();
	@Autowired
	private TransgressStatPropertiesManager tspm;

	@Override
	public String index() {
		getRequest().setAttribute("transgressStatProperties", tspm.getIt());
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND,0);
		
		endTime = c.getTime();
		c.add(Calendar.DAY_OF_MONTH, -1);
		beginTime = c.getTime();
		
		getRequest().setAttribute("beginTime", beginTime);
		getRequest().setAttribute("endTime",  endTime);
		return INDEX;
	}

	/**
	 * 统计方法
	 * 
	 * <pre>
	 * 注:1累计不要了
	 *    2,好像发现部门不仅仅是六个大队,还有其他编码,所以不能group by 来统计
	 * 1.从统计项目中获得各个项目,遍历各个项目,每个项目一个统计,--迭代 
	 * 1.1从统计条件中获得起至时间作为查询时间段条件
	 * 1.2从统计条件中获得累计起始时间作为累计合计起始时间条件 
	 * 1.3根据当前用户部门,获得其下所有部门,迭代个各个部门 --迭代
	 * 1.3.1根据前面所列条件,统计
	 * </pre>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String stat() {
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
		// 表头

		title.add("");// 第一行第一列
		title.add("合计");
		for (String str : getDeptCodeList().values()) {
			title.add(str);
		}
		/*
		 * title.add("一大队"); title.add("二大队"); title.add("三大队");
		 * title.add("四大队"); title.add("五大队"); title.add("六大队");
		 */

		// result.add(title);

		// 1.得到所有统计项
		List<TransgressStatItem> tsItems = itemManager
				.getStatItemByType(StatCfgConstants.STAT_ITEM_TYPE_COMMON);
		if (CollectionUtils.isNotEmpty(tsItems)) {
			// 迭代各个统计项
			for (TransgressStatItem itm : tsItems) {
				List itemResult = new ArrayList();
				itemResult.add(itm.getName());
				// Map<String,Object>itemResult = new HashMap<String,Object>();
				// itemResult.put("itemName", itm.getName());
				/*
				 * // 根据统计项,列出关联的违法行为编号,组成违法行为字符串 StringBuffer buf = new
				 * StringBuffer(); // 迭代对应违法行为,拼接违法行为代码字符串 for (TransgressAction
				 * ta : itm.getTransgressActions()) {
				 * buf.append(ta.getCode()).append(","); } // 得到违法行为代码字符串 String
				 * actionCodeStr = buf.toString(); // 去掉最后的逗号后,赋值给查询条件
				 * statCondition.setTransgressActionCodesStr(actionCodeStr
				 * .substring(0, actionCodeStr.length() - 1));
				 */
				// 违法行为代码
				statCondition.setTransgressActionCodesStr(itm
						.getTransgressActionCodes());
				// 机动车使用性质
				statCondition.setVehicleUseCodes(itm.getVehicleUseCodes());
				// 是否关联Force表
				statCondition.setUnionForce(itm.getUnionForce());
				// 发现时间/处理时间
				statCondition.setTimeCondition(itm.getFindOrDealWith());

				// 得到当前登录用户所在部门的子部门代码编号,名称集合
				Map<String, String> deptCodeMap = getDeptCodeList();
				Iterator<String> itr = deptCodeMap.keySet().iterator();
				BigDecimal total = new BigDecimal(0);
				while (itr.hasNext()) {
					String code = itr.next();
					// String name = deptCodeMap.get(code);

					statCondition.setDeptCode(code);

					List<Map<String, Object>> list = getManager().stat(
							statCondition);
					BigDecimal current = (BigDecimal) list.get(0).get(
							"TOTAL_COUNT");
					itemResult.add(current);
					total = total.add(current);

				}
				itemResult.add(total);
				items.add(itemResult);
			}
		}
		getRequest().setAttribute("items", items);
		getRequest().setAttribute("statDate",
				new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		getRequest().setAttribute("statBeginTime",
				statCondition.getBeginHourMinute());
		getRequest().setAttribute("statEndTime",
				statCondition.getEndHourMinute());

		getRequest().setAttribute("transgressStatProperties", tspm.getIt());
		
		getRequest().setAttribute("beginTime", beginTime);
		getRequest().setAttribute("endTime",  endTime);
		
		return INDEX;
	}

	/**
	 * 构建统计条件
	 * 
	 * @param isEachDayStat
	 *            是否是定制的每天日常统计
	 * @param beginTime
	 *            时间区间统计开始时间
	 * @param endTime
	 *            时间区间统计截至时间
	 * @return
	 */
	@SuppressWarnings("unused")
	private StatCondition buildStatCondition(boolean isEachDayStat,
			Date beginTime, Date endTime) {

		return null;
	}

	public String reportExcel() {
		return "reportExcel";
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

	@SuppressWarnings("unchecked")
	public List getTitle() {
		return title;
	}

	@SuppressWarnings("unchecked")
	public void setTitle(List title) {
		this.title = title;
	}

	@SuppressWarnings("unchecked")
	public List getItems() {
		return items;
	}

	@SuppressWarnings("unchecked")
	public void setItems(List items) {
		this.items = items;
	}

}

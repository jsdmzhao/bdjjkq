package com.googlecode.jtiger.assess.transgress.stat.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Repository;

import com.googlecode.jtiger.assess.transgress.stat.StatCondition;
import com.googlecode.jtiger.core.dao.jdbc.BaseJdbcDao;

/**
 * StatDao<br>
 * 负责从违法表中
 * 
 * @author DELPHI
 * 
 */
@Repository
public class StatDao extends BaseJdbcDao {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	// private static final String SQL_STAT1 =
	// "SELECT COUNT(*) ROWS_COUNT FROM VIO_VIOLATION VV WHERE VV.WFXW IN (";
	//private static final String SQL_STAT1 = "SELECT COUNT(*) ROWS_COUNT FROM vio_admin.vio_violation  VV WHERE VV.WFXW IN (";
	private static final String SQL_STAT1 = "SELECT COUNT(*) ROWS_COUNT FROM vio_violation  VV WHERE VV.WFXW IN (";
	private static final String SQL_STAT2 = ") ";
	private static final String SQL_STAT_BEGIN_TIME1 = "and VV.WFSJ >= to_date('";
	private static final String SQL_STAT_BEGIN_TIME2 = "','yyyy-mm-dd hh24:mi:ss') ";
	private static final String SQL_STAT_END_TIME1 = "and VV.WFSJ <= to_date('";
	private static final String SQL_STAT_END_TIME2 = "','yyyy-mm-dd hh24:mi:ss') and trim(VV.FXJG) like ";
	private static final String SQL_STAT_VEHICLE1 = " and vv.SYXZ IN(";
	private static final String SQL_STAT_VEHICLE2 = ")";

	@Override
	protected String getDataSourceName() {
		return "weiFaDs";
	}

	public List<Map<String, Object>> stat(StatCondition condition) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String actionCodesStr = condition.getTransgressActionCodesStr() == null ? "''"
				: condition.getTransgressActionCodesStr();

		StringBuffer buf = new StringBuffer(SQL_STAT1);
		// 设定违法行为字符串
		buf.append(actionCodesStr).append(SQL_STAT2);
		// 定制每日查询
		if (condition.isEachDayStat()) {
			buf.append(SQL_STAT_BEGIN_TIME1).append(
					condition.getBeginHourMinute())
					.append(SQL_STAT_BEGIN_TIME2).append(SQL_STAT_END_TIME1)
					.append(condition.getEndHourMinute()).append(
							SQL_STAT_END_TIME2);
		} else {// 时间区间查询
			// 如果起始时间不为空,则赋值起始时间
			if (StringUtils.isNotBlank(condition.getBeginHourMinute())) {
				buf.append(SQL_STAT_BEGIN_TIME1).append(
						condition.getBeginHourMinute()).append(
						SQL_STAT_BEGIN_TIME2);
			}
			// 如果截至时间不为空,则赋值截至时间
			if (StringUtils.isNotBlank(condition.getEndHourMinute())) {
				buf.append(SQL_STAT_END_TIME1).append(
						condition.getEndHourMinute())
						.append(SQL_STAT_END_TIME2);
			}
		}
		// 设定要部门条件
		buf.append("'").append(condition.getDeptCode()).append("%'");

		
		//如果车辆使用性质信息不为空,则加上使用性质条件
		if(StringUtils.isNotBlank(condition.getVehicleUseCodes())){
			buf.append(SQL_STAT_VEHICLE1).append(condition.getVehicleUseCodes()).append(SQL_STAT_VEHICLE2);
		}
		
		logger.debug(buf.toString());
		list = getJdbcTemplate()
				.query(buf.toString(), new ColumnMapRowMapper());

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> stat1() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 得到当前日期
		Calendar c = Calendar.getInstance();

		String today = format.format(c.getTime());
		// 得到当前日期的前一天
		c.add(Calendar.DAY_OF_MONTH, -1);
		String yestDayStr = format.format(c.getTime());
		yestDayStr = yestDayStr + " 17:00:00";
		String todayStr = today + " 17:00:00";

		List args = new ArrayList();
		args.add(yestDayStr);
		args.add(todayStr);
		list = getJdbcTemplate().query(
				"select count(*) rows_count from VIO_VIOLATION vv",
				new ColumnMapRowMapper());
		return list;
	}

}

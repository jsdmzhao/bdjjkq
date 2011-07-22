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

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.transgress.TransgressConstants;
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
	// private static final String SQL_STAT1 =
	// "SELECT COUNT(*) ROWS_COUNT FROM vio_admin.vio_violation  VV WHERE VV.WFXW IN (";
	// private static final String SQL_STAT1 =
	// "SELECT SUM (ROWS_COUNT) AS TOTAL_COUNT FROM( SELECT COUNT(*) ROWS_COUNT FROM "
	// + AssessConstants.VIO_VIOLATION + "  VV WHERE VV.WFXW IN (";
	private static final String SQL_STAT1 = "SELECT ROWS_COUNT1 TOTAL_COUNT FROM( SELECT COUNT(*) ROWS_COUNT1 FROM "
			+ AssessConstants.VIO_VIOLATION + "  VV WHERE 1=1 ";
	private static final String SQL_STAT101 = "AND VV.WFXW IN (";
	private static final String SQL_STAT11 = "SELECT (ROWS_COUNT1+ROWS_COUNT2) TOTAL_COUNT FROM( SELECT COUNT(*) ROWS_COUNT1 FROM "
			+ AssessConstants.VIO_VIOLATION + "  VV  WHERE 1=1 ";
	// private static final String SQL_STAT102 ="AND VV.WFXW IN (";
	private static final String SQL_STAT2 = ") ";
	private static final String SQL_STAT_BEGIN_TIME1 = "and VV.WFSJ >= to_date('";
	private static final String SQL_STAT_BEGIN_TIME11 = "and VV.CLSJ >= to_date('";
	private static final String SQL_STAT_BEGIN_TIME2 = "','yyyy-mm-dd hh24:mi:ss') ";
	private static final String SQL_STAT_END_TIME1 = "and VV.WFSJ <= to_date('";
	private static final String SQL_STAT_END_TIME11 = "and VV.CLSJ <= to_date('";
	private static final String SQL_STAT_END_TIME2 = "','yyyy-mm-dd hh24:mi:ss') and trim(VV.FXJG) like ";
	private static final String SQL_STAT_VEHICLE1 = " and vv.SYXZ IN(";
	private static final String SQL_STAT_VEHICLE2 = ")";
	private static final String SQL_STAT_FLAPPER1 = " and vv.HPZL IN(";
	private static final String SQL_STAT_FLAPPER2 = ")";

	private static final String SQL_UNION_STAT1 = " ),( SELECT COUNT(*) ROWS_COUNT2 FROM "
			+ AssessConstants.VIO_FORCE + "  VF WHERE 1 = 1 ";
	private static final String SQL_UNION_STAT11 = "AND VF.WFXW1 IN (";
	private static final String SQL_UNION_STAT12 = ") ";
	private static final String SQL_UNION_STAT_BEGIN_TIME1 = "and VF.WFSJ >= to_date('";
	private static final String SQL_UNION_STAT_BEGIN_TIME11 = "and VF.JSCJSJ >= to_date('";
	private static final String SQL_UNION_STAT_BEGIN_TIME2 = "','yyyy-mm-dd hh24:mi:ss') ";
	private static final String SQL_UNION_STAT_END_TIME1 = "and VF.WFSJ <= to_date('";
	private static final String SQL_UNION_STAT_END_TIME11 = "and VF.JSCJSJ <= to_date('";
	private static final String SQL_UNION_STAT_END_TIME2 = "','yyyy-mm-dd hh24:mi:ss')  and VF.cjbj = '0' and trim(VF.FXJG) like ";
	private static final String SQL_UNION_STAT_VEHICLE1 = " and VF.SYXZ IN(";
	private static final String SQL_UNION_STAT_VEHICLE2 = ")";
	private static final String SQL_UNION_STAT_FLAPPER1 = " and VF.HPZL IN(";
	private static final String SQL_UNION_STAT_FLAPPER2 = ")";

	private static final String SQL_END = ")";

	@Override
	protected String getDataSourceName() {
		return AssessConstants.ASSESS_DS;
	}
	

	public List<Map<String, Object>> stat(StatCondition condition) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String actionCodesStr = condition.getTransgressActionCodesStr() == null ? "''"
				: condition.getTransgressActionCodesStr();
		StringBuffer buf = null;
		// 不关联
		if (TransgressConstants.UNION_FORCE_FALSE.equals(condition
				.getUnionForce())) {
			buf = new StringBuffer(SQL_STAT1);
			// 关联
		} else if (TransgressConstants.UNION_FORCE_TRUE.equals(condition
				.getUnionForce())) {
			buf = new StringBuffer(SQL_STAT11);
			// 仅统计强制表
		} else {

		}

		// 设定违法行为字符串
		if (StringUtils.isNotBlank(actionCodesStr)) {
			buf.append(SQL_STAT101).append(actionCodesStr).append(SQL_STAT2);
		}
		// 定制每日查询
		if (condition.isEachDayStat()) {
			if ("CLSJ".equals(condition.getTimeCondition())) {
				buf.append(SQL_STAT_BEGIN_TIME11).append(
						condition.getBeginHourMinute()).append(
						SQL_STAT_BEGIN_TIME2).append(SQL_STAT_END_TIME11)
						.append(condition.getEndHourMinute()).append(
								SQL_STAT_END_TIME2);
			} else {
				buf.append(SQL_STAT_BEGIN_TIME1).append(
						condition.getBeginHourMinute()).append(
						SQL_STAT_BEGIN_TIME2).append(SQL_STAT_END_TIME1)
						.append(condition.getEndHourMinute()).append(
								SQL_STAT_END_TIME2);
			}
		} else {// 时间区间查询
			// 如果起始时间不为空,则赋值起始时间
			if ("CLSJ".equals(condition.getTimeCondition())) {
				if (StringUtils.isNotBlank(condition.getBeginHourMinute())) {
					buf.append(SQL_STAT_BEGIN_TIME11).append(
							condition.getBeginHourMinute()).append(
							SQL_STAT_BEGIN_TIME2);
				}
				// 如果截至时间不为空,则赋值截至时间
				if (StringUtils.isNotBlank(condition.getEndHourMinute())) {
					buf.append(SQL_STAT_END_TIME11).append(
							condition.getEndHourMinute()).append(
							SQL_STAT_END_TIME2);
				}
			} else {
				if (StringUtils.isNotBlank(condition.getBeginHourMinute())) {
					buf.append(SQL_STAT_BEGIN_TIME1).append(
							condition.getBeginHourMinute()).append(
							SQL_STAT_BEGIN_TIME2);
				}
				// 如果截至时间不为空,则赋值截至时间
				if (StringUtils.isNotBlank(condition.getEndHourMinute())) {
					buf.append(SQL_STAT_END_TIME1).append(
							condition.getEndHourMinute()).append(
							SQL_STAT_END_TIME2);
				}
			}

		}
		// 设定要部门条件
		buf.append("'").append(condition.getDeptCode()).append("%'");

		// 如果车辆使用性质信息不为空,则加上使用性质条件
		if (StringUtils.isNotBlank(condition.getVehicleUseCodes())) {
			buf.append(SQL_STAT_VEHICLE1)
					.append(condition.getVehicleUseCodes()).append(
							SQL_STAT_VEHICLE2);
		}
		// 如果号牌种类不为空，则加上号牌种类条件
		if (StringUtils.isNotBlank(condition.getFlapperTyps())) {
			buf.append(SQL_STAT_FLAPPER1).append(condition.getFlapperTyps())
					.append(SQL_STAT_FLAPPER2);
		}
		// 如果需要union VIO_FORCE表,则需要另拼接sql
		if (TransgressConstants.UNION_FORCE_TRUE.equals(condition
				.getUnionForce())) {
			StringBuffer bufUnion = new StringBuffer(SQL_UNION_STAT1);
			// 设定违法行为字符串
			if (StringUtils.isNotBlank(condition.getTransgressActionCodesStr())) {
				bufUnion.append(actionCodesStr).append(SQL_UNION_STAT11)
						.append(condition.getTransgressActionCodesStr())
						.append(SQL_UNION_STAT12);
			}

			// 定制每日查询
			if (condition.isEachDayStat()) {
				if ("CLSJ".equals(condition.getTimeCondition())) {
					bufUnion.append(SQL_UNION_STAT_BEGIN_TIME11).append(
							condition.getBeginHourMinute()).append(
							SQL_UNION_STAT_BEGIN_TIME2).append(
							SQL_UNION_STAT_END_TIME11).append(
							condition.getEndHourMinute()).append(
							SQL_UNION_STAT_END_TIME2);
				} else {
					bufUnion.append(SQL_UNION_STAT_BEGIN_TIME1).append(
							condition.getBeginHourMinute()).append(
							SQL_UNION_STAT_BEGIN_TIME2).append(
							SQL_UNION_STAT_END_TIME1).append(
							condition.getEndHourMinute()).append(
							SQL_UNION_STAT_END_TIME2);
				}
			} else {// 时间区间查询
				// 如果起始时间不为空,则赋值起始时间
				if ("CLSJ".equals(condition.getTimeCondition())) {
					if (StringUtils.isNotBlank(condition.getBeginHourMinute())) {
						bufUnion.append(SQL_UNION_STAT_BEGIN_TIME11).append(
								condition.getBeginHourMinute()).append(
								SQL_UNION_STAT_BEGIN_TIME2);
					}
					// 如果截至时间不为空,则赋值截至时间
					if (StringUtils.isNotBlank(condition.getEndHourMinute())) {
						bufUnion.append(SQL_UNION_STAT_END_TIME11).append(
								condition.getEndHourMinute()).append(
								SQL_UNION_STAT_END_TIME2);
					}
				} else {
					if (StringUtils.isNotBlank(condition.getBeginHourMinute())) {
						bufUnion.append(SQL_UNION_STAT_BEGIN_TIME1).append(
								condition.getBeginHourMinute()).append(
								SQL_UNION_STAT_BEGIN_TIME2);
					}
					// 如果截至时间不为空,则赋值截至时间
					if (StringUtils.isNotBlank(condition.getEndHourMinute())) {
						bufUnion.append(SQL_UNION_STAT_END_TIME1).append(
								condition.getEndHourMinute()).append(
								SQL_UNION_STAT_END_TIME2);
					}
				}

			}
			// 设定要部门条件
			bufUnion.append("'").append(condition.getDeptCode()).append("%'");

			// 如果车辆使用性质信息不为空,则加上使用性质条件
			if (StringUtils.isNotBlank(condition.getVehicleUseCodes())) {
				bufUnion.append(SQL_UNION_STAT_VEHICLE1).append(
						condition.getVehicleUseCodes()).append(
						SQL_UNION_STAT_VEHICLE2);
			}
			// 如果号牌种类不为空，则加上号牌种类条件
			if (StringUtils.isNotBlank(condition.getFlapperTyps())) {
				bufUnion.append(SQL_UNION_STAT_FLAPPER1).append(
						condition.getFlapperTyps()).append(
						SQL_UNION_STAT_FLAPPER2);
			}
			buf.append(bufUnion.toString());
		}
		buf.append(SQL_END);
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

package com.googlecode.jtiger.assess.transgress.stat.dao;

import org.apache.commons.lang.xwork.StringUtils;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.transgress.TransgressConstants;
import com.googlecode.jtiger.assess.transgress.stat.StatCondition;

public class ConditionSqlUtil {

	// ----------VIOLATION表sql语句
	private static final String SQL_STAT_VIOLATION = "SELECT COUNT(*) ROWS_COUNT FROM "
			+ AssessConstants.VIO_VIOLATION + " VV WHERE 1=1 ";
	private static final String SQL_STAT_VIOLATION_WFXW = "AND VV.WFXW IN (";
	private static final String RIGHT_PPARENTHESIS = ") ";

	private static final String SQL_STAT_VIOLATION_BEGIN_TIME_WFSJ = "and VV.WFSJ >= to_date('";
	private static final String SQL_STAT_VIOLATION_BEGIN_TIME_CLSJ = "and VV.CLSJ >= to_date('";
	private static final String TIME_EXPRESSION = "','yyyy-mm-dd hh24:mi:ss') ";
	private static final String SQL_STAT_VIOLATION_END_TIME_WFSJ = "and VV.WFSJ <= to_date('";
	private static final String SQL_STAT_VIOLATION_END_TIME_CLSJ = "and VV.CLSJ <= to_date('";
	private static final String SQL_STAT_VIOLATION_FXJG = "and trim(VV.FXJG) like ";
	private static final String SQL_STAT_VIOLATION_VEHICLE_SYXZ = " and vv.SYXZ IN(";

	private static final String SQL_STAT_VIOLATION_HPZL = " and vv.HPZL IN(";

	// ----------FORCE表sql语句
	private static final String SQL_STAT_FORCE = "SELECT COUNT(*) ROWS_COUNT FROM "
			+ AssessConstants.VIO_FORCE + "  VF WHERE 1 = 1 ";
	private static final String SQL_STAT_FORCE_WFXW = "AND VF.WFXW1 IN (";

	private static final String SQL_STAT_FORCE_BEGIN_TIME_WFSJ = "and VF.WFSJ >= to_date('";
	private static final String SQL_STAT_FORCE_BEGIN_TIME_JSCJSJ = "and VF.JSCJSJ >= to_date('";

	// private static final String SQL_STAT_FORCE_BEGIN_TIME2 =
	// "','yyyy-mm-dd hh24:mi:ss') ";

	private static final String SQL_STAT_FORCE_END_TIME_WFSJ = "and VF.WFSJ <= to_date('";
	private static final String SQL_STAT_FORCE_END_TIME_JSCJSJ = "and VF.JSCJSJ <= to_date('";

	private static final String SQL_STAT_FORCE_FXJG = " and VF.cjbj = '0' and trim(VF.FXJG) like ";
	private static final String SQL_STAT_FORCE_VEHICLE_SYXZ = " and VF.SYXZ IN(";

	private static final String SQL_STAT_FORCE_STAT_HPZL = " and VF.HPZL IN(";

	// -----------vio_surveil表sql语句
	private static final String SQL_STAT_SURVEIL = "SELECT COUNT(*) ROWS_COUNT FROM "
			+ AssessConstants.VIO_SURVEIL + "  VS WHERE 1 = 1 ";
	private static final String SQL_STAT_SURVEIL_WFXW = "AND VS.WFXW IN (";

	private static final String SQL_STAT_SURVEIL_BEGIN_TIME_WFSJ = "and VS.WFSJ >= to_date('";
	private static final String SQL_STAT_SURVEIL_BEGIN_TIME_CLSJ = "and VS.CLSJ >= to_date('";

	private static final String SQL_STAT_SURVEIL_END_TIME_WFSJ = "and VS.WFSJ <= to_date('";
	private static final String SQL_STAT_SURVEIL_END_TIME_CLSJ = "and VS.CLSJ <= to_date('";

	private static final String SQL_STAT_SURVEIL_CJJG = " and VS.CLBJ = '0' and trim(VS.CJJG) like ";
	private static final String SQL_STAT_SURVEIL_VEHICLE_SYXZ = " and VS.SYXZ IN(";

	private static final String SQL_STAT_SURVEIL_STAT_HPZL = " and VS.HPZL IN(";

	/**
	 * 根据条件和目标表,构建sql语句
	 * 
	 * @param condition
	 * @param table
	 * @return
	 */
	public String buildSql(StatCondition condition, String table) {
		// 违法表
		if (AssessConstants.VIO_VIOLATION.equals(table)) {
			return sqlViolation(condition);
		} else if (AssessConstants.VIO_FORCE.equals(table)) {
			return sqlForce(condition);
		} else {
			return sqlSurveil(condition);
		}
	}

	/**
	 * 非现场文本记录表表sql
	 * 
	 * @param condition
	 * @return
	 */
	private String sqlSurveil(StatCondition condition) {
		StringBuffer buf = new StringBuffer();
		buf.append(SQL_STAT_SURVEIL);
		if (StringUtils.isNotBlank(condition.getTransgressActionCodesStr())) {
			buf.append(SQL_STAT_SURVEIL_WFXW).append(
					condition.getTransgressActionCodesStr()).append(
					RIGHT_PPARENTHESIS);
		}

		// 违法/发现时间
		if (TransgressConstants.WFSJ.equals(condition.getTimeCondition())) {
			buf.append(SQL_STAT_SURVEIL_BEGIN_TIME_WFSJ).append(
					condition.getBeginHourMinute()).append(TIME_EXPRESSION)
					.append(SQL_STAT_SURVEIL_END_TIME_WFSJ).append(
							condition.getEndHourMinute()).append(
							TIME_EXPRESSION);

			// 处理时间
		} else {
			buf.append(SQL_STAT_SURVEIL_BEGIN_TIME_CLSJ).append(
					condition.getBeginHourMinute()).append(TIME_EXPRESSION)
					.append(SQL_STAT_SURVEIL_END_TIME_CLSJ).append(
							condition.getEndHourMinute()).append(
							TIME_EXPRESSION);
		}
		// 发现机关
		buf.append(SQL_STAT_SURVEIL_CJJG).append("'").append(
				condition.getDeptCode()).append("%'");
		// 如果车辆使用性质信息不为空,则加上使用性质条件
		if (StringUtils.isNotBlank(condition.getVehicleUseCodes())) {
			buf.append(SQL_STAT_SURVEIL_VEHICLE_SYXZ).append(
					condition.getVehicleUseCodes()).append(RIGHT_PPARENTHESIS);
		}
		// 如果号牌种类不为空，则加上号牌种类条件
		if (StringUtils.isNotBlank(condition.getFlapperTyps())) {
			buf.append(SQL_STAT_SURVEIL_STAT_HPZL).append(
					condition.getFlapperTyps()).append(RIGHT_PPARENTHESIS);
		}
		return buf.toString();
	}

	/**
	 * 强制表sql
	 * 
	 * @param condition
	 * @return
	 */
	private String sqlForce(StatCondition condition) {
		StringBuffer buf = new StringBuffer();
		buf.append(SQL_STAT_FORCE);
		if (StringUtils.isNotBlank(condition.getTransgressActionCodesStr())) {
			buf.append(SQL_STAT_FORCE_WFXW).append(
					condition.getTransgressActionCodesStr()).append(
					RIGHT_PPARENTHESIS);
		}
		// 违法/发现时间
		if (TransgressConstants.WFSJ.equals(condition.getTimeCondition())) {
			buf.append(SQL_STAT_FORCE_BEGIN_TIME_WFSJ).append(
					condition.getBeginHourMinute()).append(TIME_EXPRESSION)
					.append(SQL_STAT_FORCE_END_TIME_WFSJ).append(
							condition.getEndHourMinute()).append(
							TIME_EXPRESSION);

			// 处理时间
		} else {
			buf.append(SQL_STAT_FORCE_BEGIN_TIME_JSCJSJ).append(
					condition.getBeginHourMinute()).append(TIME_EXPRESSION)
					.append(SQL_STAT_FORCE_END_TIME_JSCJSJ).append(
							condition.getEndHourMinute()).append(
							TIME_EXPRESSION);
		}
		// 发现机关
		buf.append(SQL_STAT_FORCE_FXJG).append("'").append(
				condition.getDeptCode()).append("%'");
		// 如果车辆使用性质信息不为空,则加上使用性质条件
		if (StringUtils.isNotBlank(condition.getVehicleUseCodes())) {
			buf.append(SQL_STAT_FORCE_VEHICLE_SYXZ).append(
					condition.getVehicleUseCodes()).append(RIGHT_PPARENTHESIS);
		}
		// 如果号牌种类不为空，则加上号牌种类条件
		if (StringUtils.isNotBlank(condition.getFlapperTyps())) {
			buf.append(SQL_STAT_FORCE_STAT_HPZL).append(
					condition.getFlapperTyps()).append(RIGHT_PPARENTHESIS);
		}
		return buf.toString();
	}

	/**
	 * 违法表sql
	 * 
	 * @param condition
	 * @return
	 */
	private String sqlViolation(StatCondition condition) {
		StringBuffer buf = new StringBuffer();
		buf.append(SQL_STAT_VIOLATION);
		if (StringUtils.isNotBlank(condition.getTransgressActionCodesStr())) {
			buf.append(SQL_STAT_VIOLATION_WFXW).append(
					condition.getTransgressActionCodesStr()).append(
					RIGHT_PPARENTHESIS);
		}
		// 违法/发现时间
		if (TransgressConstants.WFSJ.equals(condition.getTimeCondition())) {
			buf.append(SQL_STAT_VIOLATION_BEGIN_TIME_WFSJ).append(
					condition.getBeginHourMinute()).append(TIME_EXPRESSION)
					.append(SQL_STAT_VIOLATION_END_TIME_WFSJ).append(
							condition.getEndHourMinute()).append(
							TIME_EXPRESSION);

			// 处理时间
		} else {
			buf.append(SQL_STAT_VIOLATION_BEGIN_TIME_CLSJ).append(
					condition.getBeginHourMinute()).append(TIME_EXPRESSION)
					.append(SQL_STAT_VIOLATION_END_TIME_CLSJ).append(
							condition.getEndHourMinute()).append(
							TIME_EXPRESSION);
		}
		// 发现机关
		buf.append(SQL_STAT_VIOLATION_FXJG).append("'").append(
				condition.getDeptCode()).append("%'");
		// 如果车辆使用性质信息不为空,则加上使用性质条件
		if (StringUtils.isNotBlank(condition.getVehicleUseCodes())) {
			buf.append(SQL_STAT_VIOLATION_VEHICLE_SYXZ).append(
					condition.getVehicleUseCodes()).append(RIGHT_PPARENTHESIS);
		}
		// 如果号牌种类不为空，则加上号牌种类条件
		if (StringUtils.isNotBlank(condition.getFlapperTyps())) {
			buf.append(SQL_STAT_VIOLATION_HPZL).append(
					condition.getFlapperTyps()).append(RIGHT_PPARENTHESIS);
		}
		return buf.toString();
	}
}

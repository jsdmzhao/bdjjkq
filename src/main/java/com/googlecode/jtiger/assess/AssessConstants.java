package com.googlecode.jtiger.assess;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.googlecode.jtiger.core.Constants;
import com.googlecode.jtiger.core.util.ResBundleUtil;

public final class AssessConstants {
	/** 违法库表连接数据源 ,默认为远程 */
	public static final String ASSESS_DS = ResBundleUtil.getString(
			Constants.RESOURCE_BUNDLE, "assess.ds", "dsRemote");
	/** VIO_VIOLATION表名,默认为公安网库表名 */
	public static final String VIO_VIOLATION = ResBundleUtil.getString(
			Constants.RESOURCE_BUNDLE, "vio_violation",
			"vio_admin.vio_violation");

	/** VIO_FORCE表名,,默认为公安网库表名 */
	public static final String VIO_FORCE = ResBundleUtil.getString(
			Constants.RESOURCE_BUNDLE, "vio_force", "vio_admin.vio_force");
	/** VIO_SURVEIL表明,默认为公安网表明 */
	public static final String VIO_SURVEIL = ResBundleUtil.getString(
			Constants.RESOURCE_BUNDLE, "vio_surveil", "vio_admin.vio_surveil");
	/** 任务常量 */
	public static final String TASK_CONST = "TASK_CONST";
	/** 任务常量名 */
	public static final String TASK_CONST_NAME = "任务常量";
	/** 日常勤务 */
	public static final String TASK_DUTY = "TASK_DUTY";
	/** B组考核标准 */
	public static final String TASK_GROUP_B = "TASK_GROUP_B";
	/** 重奖 */
	public static final String TASK_AWARD = "TASK_AWARD";
	/** ,一票否决 */
	public static final String TASK_REJECT = "TASK_REJECT";
	/** 其他奖项 */
	public static final String TASK_OTHER = "TASK_OTHER";

	public static final Map<String, String> ADD_OR_DECREASE_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		// ADD_OR_DECREASE_MAP.put("", "请选择");
		ADD_OR_DECREASE_MAP.put("0", "减分");
		ADD_OR_DECREASE_MAP.put("1", "加分");
	}
	/** 包岗领导同扣 */
	public static final String CAST_LEADER_Y = "1";
	/** 包岗领导不同扣 */
	public static final String CAST_LEADER_N = "0";
	public static final Map<String, String> CAST_LEADER_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		CAST_LEADER_MAP.put("0", "包岗领导不同扣");
		CAST_LEADER_MAP.put("1", "包岗领导同扣");
	}
	/** 用于定时考核的cron */
	public static final String CRON_ASSESS = "assessQuartz";
	/** 用于定时日报考核的cron */
	public static final String CRON_ASSESS_DAILY = "assessDailyQuartz";
	/**默认日报cron表达式*/
	public static final String CRON_ASSESS_DAILY_DEFAULT = "0 0 6 * * ?";
	/** 默认cron表达式 */
	public static final String CRON_ASSESS_DEFAULT = "0 0 0 1 * ?";
	/** 考核涉及的大队的编号,如果是"all"则涉及全部大队,现阶段默认为市区6个大队 */
	public static final String ASSESS_DEPT_CODES = ResBundleUtil.getString(
			Constants.RESOURCE_BUNDLE, "assess_depts_city",
			"130604,130603,130602,130641,130642,130643");
	/** 部门类别-支队 */
	public static final String DEPT_TYPE_0 = "0";
	/** 部门类别-外勤(大队) */
	public static final String DEPT_TYPE_1 = "1";
	/** 部门类别-行政 */
	public static final String DEPT_TYPE_2 = "2";

}

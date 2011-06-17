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
	/** 任务常量 */
	public static final String TASK_CONST = "TASK_CONST";
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

}

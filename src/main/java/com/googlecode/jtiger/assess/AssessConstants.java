package com.googlecode.jtiger.assess;

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
	public static final String DUTY = "DUTY";

}

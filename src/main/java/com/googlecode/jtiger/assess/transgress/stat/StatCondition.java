package com.googlecode.jtiger.assess.transgress.stat;

import java.util.Date;

public final class StatCondition {
	/** 是否是每天统计(每天定制统计/选定日期区间统计) */
	private boolean isEachDayStat;
	/** 开始时分 --定制每天查询 */
	private String beginHourMinute;
	/** 结束时分 --定制每天查询 */
	private String endHourMinute;

	/** 开始时间--选定时间区间查询 */
	private Date beginTime;
	/** 截至时间--选定时间区间查询 */
	private Date endTime;
	/** 违法行为代码查询字符串 */
	private String transgressActionCodesStr;
	/** 要统计的大队编码 */
	private String deptCode;
	/** 机动车使用性质 */
	private String vehicleUseCodes;

	public String getTransgressActionCodesStr() {
		return transgressActionCodesStr;
	}

	public void setTransgressActionCodesStr(String transgressActionCodesStr) {
		this.transgressActionCodesStr = transgressActionCodesStr;
	}

	public String getBeginHourMinute() {
		return beginHourMinute;
	}

	public void setBeginHourMinute(String beginHourMinute) {
		this.beginHourMinute = beginHourMinute;
	}

	public String getEndHourMinute() {
		return endHourMinute;
	}

	public void setEndHourMinute(String endHourMinute) {
		this.endHourMinute = endHourMinute;
	}

	public boolean isEachDayStat() {
		return isEachDayStat;
	}

	public void setEachDayStat(boolean isEachDayStat) {
		this.isEachDayStat = isEachDayStat;
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

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getVehicleUseCodes() {
		return vehicleUseCodes;
	}

	public void setVehicleUseCodes(String vehicleUseCodes) {
		this.vehicleUseCodes = vehicleUseCodes;
	}

}

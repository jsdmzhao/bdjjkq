package com.googlecode.jtiger.modules.hr;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * OA项目中的常量
 * 
 * @author Sam Lee
 */
public final class HrConstants {
	/**
	 * 私有构造器，防止常量类被实例化
	 */
	private HrConstants() {
	}

	/**
	 * 默认密码
	 */
	public static final String PASSWORD = "111";

	/**
	 * 1-锁定
	 */
	public static final String LOCKED = "1";

	/**
	 * 0-解锁
	 */
	public static final String UNLOCKED = "0";

	/**
	 * 性别常量，M-男性
	 */
	public static final String GENT = "M";

	/**
	 * 性别常量，F-女性
	 */
	public static final String LADY = "F";

	/**
	 * 婚姻常量，Y-已婚
	 */
	public static final String MARRIED_Y = "Y";

	/**
	 * 婚姻常量，N-未婚
	 */
	public static final String MARRIED_N = "N";

	/**
	 * 尚未审核：0-尚未审核
	 */
	public static final String WAITCHECK = "0";

	/**
	 * 部门类别：0-非学校
	 */
	public static final String UNSCHOOL = "0";

	/**
	 * 部门类别：1-学校
	 */
	public static final String SCHOOL = "1";

	/**
	 * 审核是否通过常量：Y-审核通过
	 */
	public static final String PASS = "Y";

	/**
	 * 审核是否通过常量：N-未通过
	 */
	public static final String UNPASS = "N";

	/**
	 * 学历常量，0-初中以下
	 */
	public static final String ELEMENTARY = "0";

	/**
	 * 学历常量，1-初中
	 */
	public static final String JUNIOR = "1";

	/**
	 * 学历常量，2-高中
	 */
	public static final String SENIOR = "2";

	/**
	 * 学历常量，3-大专
	 */
	public static final String JUNIOR_COLLEGE = "3";

	/**
	 * 学历常量，4-本科
	 */
	public static final String UNDERGRADUATE = "4";

	/**
	 * 学历常量，5-本科以上
	 */
	public static final String DOCTOR = "5";

	/**
	 * 学历常量，6-中专
	 */
	public static final String TECHNICAL_SCHOOL = "6";

	/**
	 * 就业状态，0-不需要就业
	 */
	public static final String NONEED = "0";

	/**
	 * 就业状态，1-需要就业
	 */
	public static final String NEED = "1";

	/**
	 * 就业状态，2-在读(缺省)
	 */
	public static final String READING = "2";

	/**
	 * 就业状态，3-已就业
	 */
	public static final String NEEDED = "3";

	/**
	 * 就业状态，4-推迟
	 */
	public static final String LATERNEED = "4";

	/**
	 * 就业状态，5-二次就业
	 */
	public static final String SECONDNEED = "5";

	/**
	 * 政治面貌，0-党员
	 */
	public static final String PARTY = "0";

	/**
	 * 政治面貌，1-团员
	 */
	public static final String MEMBER = "1";

	/**
	 * 政治面貌，2-群众
	 */
	public static final String MULTITUDE = "2";

	/**
	 * 政治面貌，3-其他
	 */
	public static final String OTHER = "3";

	/**
	 * 角色名称常量:ROLE_STU-学生
	 */
	public static final String ROLE_STU = "ROLE_STU";

	/**
	 * 角色名称常量:ROLE_MAST-班主任
	 */
	public static final String ROLE_MAST = "ROLE_MAST";

	/**
	 * 角色名称常量:ROLE_TEACHER-教师
	 */
	public static final String ROLE_TEACHER = "ROLE_TEACHER";

	/**
	 * 角色名称常量:ROLE_TEACH_QUALITY-教质经理
	 */
	public static final String ROLE_TEACH_QUALITY = "ROLE_TEACH_QUALITY";

	/**
	 * 角色名称常量:ROLE_ACADEMIC-学术经理
	 */
	public static final String ROLE_ACADEMIC = "ROLE_ACADEMIC";

	/**
	 * 角色名称常量:ROLE_CORP_SERV-就业服务部
	 */
	public static final String ROLE_CORP_SERV = "ROLE_CORP_SERV";

	/**
	 * 角色名称常量:ROLE_JOB_FINDER-就业专员
	 */
	public static final String ROLE_JOB_FINDER = "ROLE_JOB_FINDER";

	/**
	 * 角色名称常量:ROLE_BOSS-老板/总经理
	 */
	public static final String ROLE_BOSS = "ROLE_BOSS";

	/**
	 * 角色名称常量:ROLE_ADMIN-系统管理员
	 */
	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	/**
	 * 角色名称常量:ROLE_EXAMINATION-审核就业状态
	 */
	public static final String ROLE_EXAMINATION = "ROLE_EXAMINATION";

	/**
	 * 角色名称常量:ROLE_ASSET_MANAGER-资产管理员
	 */
	public static final String ROLE_ASSET_MANAGER = "ROLE_ASSET_MANAGER";

	/**
	 * 角色名称常量:ROLE_SERVICE-行政主管
	 */
	public static final String ROLE_SERVICE = "ROLE_SERVICE";

	/**
	 * 角色名称常量:ROLE_NOTICE_PROMULGATOR-通知公告管理员
	 */
	public static final String ROLE_NOTICE_PROMULGATOR = "ROLE_NOTICE_PROMULGATOR";

	/**
	 * 角色名称常量:ROLE_DEPT_MANAGER-部门经理
	 */
	public static final String ROLE_DEPT_MANAGER = "ROLE_DEPT_MANAGER";

	/**
	 * 角色名称常量：ROLE_ATTENDANCE_MANAGER考勤管理员
	 */
	public static final String ROLE_ATTENDANCE_MANAGER = "ROLE_ATTENDANCE_MANAGER";
	/** 机构类别-支队 */
	public static final String DEPT_TYPE_0 = "0";
	/** 机构类别-大队(市) */
	public static final String DEPT_TYPE_1 = "1";
	/** 机构类别-大队(县) */
	public static final String DEPT_TYPE_2 = "2";
	/** 机构类别-中队(市) */
	public static final String DEPT_TYPE_3 = "3";
	/** 机构类别-中队(县) */
	public static final String DEPT_TYPE_4 = "4";

	/**
	 * 未审核，已通过审核，未通过审核Map
	 */
	public static final Map<String, String> STATE_PASS_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		STATE_PASS_MAP.put(WAITCHECK, "等待审核记录");
		STATE_PASS_MAP.put(PASS, "已审核通过记录");
		STATE_PASS_MAP.put(UNPASS, "审核未通过记录");
	}

	/**
	 * 部门类别
	 */
	public static final Map<String, String> DEPT_IS_SCHOOL = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		DEPT_IS_SCHOOL.put(UNSCHOOL, "非学校");
		DEPT_IS_SCHOOL.put(SCHOOL, "学校");
	}

	/**
	 * 婚姻常量Map
	 */
	public static final Map<String, String> MARRIED_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		MARRIED_MAP.put(MARRIED_N, "未婚");
		MARRIED_MAP.put(MARRIED_Y, "已婚");
	}

	/**
	 * 审核是否通过常量Map
	 */
	public static final Map<String, String> PASS_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		PASS_MAP.put(PASS, "审核通过");
		PASS_MAP.put(UNPASS, "审核未通过");
	}

	/**
	 * 政治面貌Map
	 */
	public static final Map<String, String> DEGREE_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		DEGREE_MAP.put(ELEMENTARY, "初中以下");
		DEGREE_MAP.put(JUNIOR, "初中");
		DEGREE_MAP.put(TECHNICAL_SCHOOL, "中专");
		DEGREE_MAP.put(SENIOR, "高中");
		DEGREE_MAP.put(JUNIOR_COLLEGE, "大专");
		DEGREE_MAP.put(UNDERGRADUATE, "本科");
		DEGREE_MAP.put(DOCTOR, "本科以上");
	}

	/**
	 * 学历常量Map
	 */
	public static final Map<String, String> POLITICAL_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		POLITICAL_MAP.put(PARTY, "党员");
		POLITICAL_MAP.put(MEMBER, "团员");
		POLITICAL_MAP.put(MULTITUDE, "群众");
		POLITICAL_MAP.put(OTHER, "其他");
	}

	/**
	 * 就业需要Map
	 */
	public static final Map<String, String> JOBREQ_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		JOBREQ_MAP.put(NONEED, "不需要就业");
		JOBREQ_MAP.put(NEED, "需要就业");
		JOBREQ_MAP.put(READING, "在读");
		JOBREQ_MAP.put(NEEDED, "已就业");
		JOBREQ_MAP.put(LATERNEED, "推迟就业");
		JOBREQ_MAP.put(SECONDNEED, "二次需要就业");
	}

	/**
	 * 性别常量Map
	 */
	public static final Map<String, String> SEX_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		SEX_MAP.put(GENT, "男");
		SEX_MAP.put(LADY, "女");
	}
}

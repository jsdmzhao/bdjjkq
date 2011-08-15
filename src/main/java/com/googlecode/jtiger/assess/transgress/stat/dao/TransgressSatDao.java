package com.googlecode.jtiger.assess.transgress.stat.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.stereotype.Repository;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.transgress.TransgressConstants;
import com.googlecode.jtiger.assess.transgress.stat.StatCondition;
import com.googlecode.jtiger.core.dao.jdbc.BaseJdbcDao;

@Repository
public class TransgressSatDao extends BaseJdbcDao {
	private ConditionSqlUtil util = new ConditionSqlUtil();
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected String getDataSourceName() {
		return AssessConstants.ASSESS_DS;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> stat(StatCondition condition) {
		//List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		/*
		 * String actionCodesStr = condition.getTransgressActionCodesStr() ==
		 * null ? "''" : condition.getTransgressActionCodesStr();
		 */
		condition.setTransgressActionCodesStr(condition
				.getTransgressActionCodesStr() == null ? "''" : condition
				.getTransgressActionCodesStr());
		// 仅统计强制表
		if (TransgressConstants.UNION_FORCE_ONLY.equals(condition
				.getUnionForce())) {
			return summary(statForce(condition));
			// 关联强制表
		} else if (TransgressConstants.UNION_FORCE_TRUE.equals(condition
				.getUnionForce())) {
			// 关联非现场文本记录表
			if (TransgressConstants.UNION_SURVIL_TRUE.equals(condition
					.getVioSurveil())) {
				return summary(statViolation(condition), statForce(condition),
						statSurveil(condition));
				// 不关联 非现场文本记录表
			} else {
				return summary(statViolation(condition), statForce(condition));
			}
			// 不关联强制表
		} else {
			// 关联非现场文本记录表
			if (TransgressConstants.UNION_SURVIL_TRUE.equals(condition
					.getVioSurveil())) {
				return summary(statViolation(condition), statSurveil(condition));
				// 不关联非现场文本记录表
			} else {
				return summary(statViolation(condition));
			}
		}
	}

	private List<Map<String, Object>> statViolation(StatCondition condition) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 违法表
		String sqlViolation = util.buildSql(condition,
				AssessConstants.VIO_VIOLATION);
		logger.debug(sqlViolation);
		list = getJdbcTemplate().query(sqlViolation,
				new ColumnMapRowMapper());
		return list;
	}

	private List<Map<String, Object>> statForce(StatCondition condition) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = util.buildSql(condition, AssessConstants.VIO_FORCE);
		logger.debug(sql);
		list = getJdbcTemplate().query(sql, new ColumnMapRowMapper());
		return list;
	}

	private List<Map<String, Object>> statSurveil(StatCondition condition) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 非现场文本记录表
		String sqlSurveil = util.buildSql(condition,
				AssessConstants.VIO_SURVEIL);
		logger.debug(sqlSurveil);
		list = getJdbcTemplate().query(sqlSurveil,
				new ColumnMapRowMapper());

		return list;
	}

	/**
	 * 累加各个表的统计数据
	 * 
	 * @param lists
	 * @return
	 */
	private List<Map<String, Object>> summary(
			List<Map<String, Object>>... lists) {
		BigDecimal total = new BigDecimal(0);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (List<Map<String, Object>> list : lists) {
			BigDecimal current = (BigDecimal) list.get(0).get("ROWS_COUNT");
			total = total.add(current);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("TOTAL_COUNT", total);
		result.add(map);

		return result;
	}
}

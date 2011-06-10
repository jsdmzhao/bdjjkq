package com.googlecode.jtiger.assess.transgress.statcfg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.jtiger.assess.transgress.statcfg.StatCfgConstants;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressType;
import com.googlecode.jtiger.core.service.BaseGenericsManager;

@Service
public class TransgressTypeManager extends BaseGenericsManager<TransgressType> {
	/**
	 * 得到所有一级类别
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransgressType> getFirstLevelTypes() {
		String hql = "from TransgressType tt where tt.type = ? order by tt.code";

		return getDao().query(hql, StatCfgConstants.TRANSGRESSS_TYPE_1);
	}

	/**
	 * 根据一级类别得到二级类别
	 * 
	 * @param firstLevelTypeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TransgressType> getSecondLevelTypesByFirstLevel(
			String firstLevelTypeId) {
		String hql = "from TransgressType tt where tt.parentTransgressType.id = ? order by tt.code";

		return getDao().query(hql, firstLevelTypeId);
	}

}

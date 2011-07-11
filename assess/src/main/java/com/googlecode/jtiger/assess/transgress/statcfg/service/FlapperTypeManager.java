package com.googlecode.jtiger.assess.transgress.statcfg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.jtiger.assess.transgress.statcfg.model.FlapperType;
import com.googlecode.jtiger.core.service.BaseGenericsManager;

@Service
public class FlapperTypeManager extends BaseGenericsManager<FlapperType> {
	/**
	 * 得到所有号牌种类实体
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FlapperType> getAllFlapperTypes() {
		String hql = "from FlapperType ft order by ft.code";

		return getDao().query(hql);
	}
}

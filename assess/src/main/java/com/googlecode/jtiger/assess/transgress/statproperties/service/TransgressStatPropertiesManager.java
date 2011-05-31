package com.googlecode.jtiger.assess.transgress.statproperties.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.googlecode.jtiger.assess.transgress.statproperties.model.TransgressStatProperties;
import com.googlecode.jtiger.core.service.BaseGenericsManager;

@Service
public class TransgressStatPropertiesManager extends
		BaseGenericsManager<TransgressStatProperties> {
	@SuppressWarnings("unchecked")
	public TransgressStatProperties getIt() {
		String hql = "from TransgressStatProperties tsp ";
		List<TransgressStatProperties> list = getDao().query(hql);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}

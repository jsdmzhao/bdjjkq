package com.googlecode.jtiger.assess.transgress.statcfg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.jtiger.assess.transgress.statcfg.model.VehicleUseCode;
import com.googlecode.jtiger.core.service.BaseGenericsManager;

@Service
public class VehicleUseCodeManager extends BaseGenericsManager<VehicleUseCode> {
	/**
	 * 得到全部机动车使用性质代码实体
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<VehicleUseCode> getAllVehicleUseCodes() {
		String hql = "from VehicleUseCode vuc order by vuc.id";

		return getDao().query(hql);
	}
}

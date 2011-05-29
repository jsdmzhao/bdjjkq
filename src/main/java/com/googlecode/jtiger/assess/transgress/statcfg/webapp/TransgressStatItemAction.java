package com.googlecode.jtiger.assess.transgress.statcfg.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.transgress.statcfg.StatCfgConstants;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressAction;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressType;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressStatItemManager;
import com.googlecode.jtiger.core.util.ReflectUtil;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransgressStatItemAction extends
		DefaultCrudAction<TransgressStatItem, TransgressStatItemManager> {
	/** 统计项关联的违法行为代码数组 */
	private String[] transgressActions;

	public String edit() {

		return INPUT;
	}

	/**
	 * 得到所有一级类别
	 * 
	 * @return
	 */
	public List<TransgressType> getFirstLevelTypes() {
		return getManager().getFirstLevelTypes();
	}

	/**
	 * 根据一级类别得到二级类别
	 * 
	 * @param firstLevelTypeId
	 * @return
	 */
	public List<TransgressType> getSecondLevelTypesByFirstLevel(
			String firstLevelTypeId) {
		return getManager().getSecondLevelTypesByFirstLevel(firstLevelTypeId);
	}

	/**
	 * 根据二级类别得到违法行为
	 * 
	 * @param secondLevelTypeId
	 * @return
	 */
	public List<TransgressAction> getTransgressActionsBySecondLevelType(
			String secondLevelTypeId) {
		return getManager().getTransgressActionsBySecondLevelType(
				secondLevelTypeId);
	}

	@Override
	public String index() {

		getRequest().setAttribute("items", getManager().get());
		// firstLevelTypes = getFirstLevelTypes();
		getRequest().setAttribute("firstLevelTypes", getFirstLevelTypes());
		return INDEX;
	}

	public String save() {
		return SUCCESS;
	}

	public String remove() {
		return SUCCESS;
	}

	/**
	 * Ajax方式得到子类别
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getSecondLevelTypesByFirstLevel() {
		String id = getRequest().getParameter("firstLevelTypeId");
		TransgressType parent = getManager().getTransgressTypeById(id);
		if (parent != null
				&& StatCfgConstants.TRANSGRESSS_TYPE_1.equals(parent.getType())) {
			Set<TransgressType> childs = parent.getChildsTransgressTypes();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (TransgressType tt : childs) {
				Map<String, Object> map = ReflectUtil.toMap(tt, new String[] {
						"id", "code", "descns" }, true);
				list.add(map);
			}
			if (CollectionUtils.isNotEmpty(childs)) {
				String json = toJson(list);
				logger.debug(json);
				renderJson(json);
			}
		}

		return null;
	}

	public String[] getTransgressActions() {
		return transgressActions;
	}

	public void setTransgressActions(String[] transgressActions) {
		this.transgressActions = transgressActions;
	}

}

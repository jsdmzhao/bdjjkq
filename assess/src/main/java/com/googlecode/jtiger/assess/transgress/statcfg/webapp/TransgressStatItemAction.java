package com.googlecode.jtiger.assess.transgress.statcfg.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.MatchMode;
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
	/** 用户选择的违法行为代码 */
	private String[] transgressActionCodes;
	/** 保存二级类别的id,为的是在修改统计项时,能够显示用户已经关联到的违法行为,并且以这些违法行为以二级类别"分组" */
	private String[] secondLevelTypeIds;

	/** 统计项关联的违法行为代码数组 */
	private String[] transgressActions;
	/** 考虑到需要通过违法行为关联到违法类别从而能再次在界面中显示出"类别下"的违法行为,传递的不是违法行为code而是id */
	private String[] transgressActionIds;
	/** 界面传递过来的车辆使用性质代码 */
	private String[] vehicleUseCodes;

	@Override
	public String edit() {
		if (getModel() != null && getModel().getId() != null) {
			setModel(getManager().get(getModel().getId()));
		}

		// 得到实体中保存的二级类别id字符串,以能够显示出其已有"关联"(通过违法行为间接关联)的二级类别
		String secondTypeIds = getModel().getSecondLevelTypeIds();
		// 如果字符串不为空,则意味着关联到了二级类别
		if (StringUtils.isNotBlank(secondTypeIds)) {

			List<TransgressType> selectedSecondTypes = new ArrayList<TransgressType>();
			// 根据","得到id数组
			String[] sendTypeId = secondTypeIds.split(",");
			// 遍历数组,得到二级类别实体集合
			for (String id : sendTypeId) {
				TransgressType tt = getManager().getTransgressTypeById(id);
				selectedSecondTypes.add(tt);
			}
			// 传递到页面以展示本统计项目已经关联到的二级类别
			getRequest().setAttribute("selectedSecondTypes",
					selectedSecondTypes);
		}

		// 列出所有机动车使用性质实体
		getRequest().setAttribute("allVehicleUseCodes",
				getManager().getAllVehicleUseCodes());
		// 列出所有一级违法类别
		getRequest().setAttribute("firstLevelTypes", getFirstLevelTypes());
		// getRequest().setAttribute("secondLevelTypes",
		// getManager().getSecondLevelTypesByFirstLevel(firstLevelTypeId))

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
		String hql = "from TransgressStatItem tsi where 1=1";
		List<Object> args = new ArrayList<Object>();
		if (getModel() != null && getModel().getName() != null) {
			hql += " and tsi.name like ?";
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		hql += " order by tsi.id";

		page = getManager().pageQuery(pageOfBlock(), hql, args.toArray());
		restorePageData(page);

		// getRequest().setAttribute("items", getManager().get());
		// firstLevelTypes = getFirstLevelTypes();
		// getRequest().setAttribute("firstLevelTypes", getFirstLevelTypes());
		return INDEX;
	}

	@Override
	public String save() {
		/*
		 * if (transgressActionCodes != null) { StringBuffer buf = new
		 * StringBuffer(); for (int i = 0; i < transgressActionCodes.length - 1;
		 * i++) { buf.append("'").append(transgressActionCodes[i]).append("'")
		 * .append(","); } buf.append("'").append(
		 * transgressActionCodes[transgressActionCodes.length - 1])
		 * .append("'");
		 * 
		 * getModel().setTransgressActionCodes(buf.toString()); }
		 * 
		 * if (secondLevelTypeIds != null) { StringBuffer buf = new
		 * StringBuffer(); for (int i = 0; i < secondLevelTypeIds.length - 1;
		 * i++) { buf.append(secondLevelTypeIds[i]).append(","); }
		 * buf.append(secondLevelTypeIds[secondLevelTypeIds.length - 1]);
		 * getModel().setSecondLevelTypeIds(buf.toString()); }
		 */
		StringBuffer bufTypeId = new StringBuffer();
		StringBuffer bufActionCode = new StringBuffer();
		StringBuffer bufVehicleUseCodes = new StringBuffer();
		// 考虑到需要通过违法行为关联到违法类别从而能再次在界面中显示出"类别下"的违法行为,传递的不是违法行为code而是id
		if (transgressActionIds != null) {
			List<String> typeList = new ArrayList<String>();

			for (String taId : transgressActionIds) {
				TransgressAction ta = getManager()
						.getTransgressActionById(taId);
				String typeId = ta.getTransgressType().getId();
				if (!typeList.contains(typeId)) {
					typeList.add(typeId);
				}
				bufActionCode.append("'").append(ta.getCode()).append("'")
						.append(",");
			}

			if (bufActionCode.length() > 0) {
				bufActionCode.deleteCharAt(bufActionCode.length() - 1);
			}

			if (CollectionUtils.isNotEmpty(typeList)) {

				for (int i = 0; i < typeList.size() - 1; i++) {
					bufTypeId.append(typeList.get(i)).append(",");
				}
				bufTypeId.append(typeList.get(typeList.size() - 1));

			}

		}
		if (vehicleUseCodes != null) {
			for (int i = 0; i < vehicleUseCodes.length - 1; i++) {
				bufVehicleUseCodes.append("'").append(vehicleUseCodes[i])
						.append("'").append(",");
			}
			bufVehicleUseCodes.append("'").append(
					vehicleUseCodes[vehicleUseCodes.length - 1]).append("'");
		}
	
		getModel().setVehicleUseCodes(bufVehicleUseCodes.toString());
		getModel().setTransgressActionCodes(bufActionCode.toString());
		getModel().setSecondLevelTypeIds(bufTypeId.toString());

		try {
			super.save();
			render("success", "text/plain");
		} catch (Exception e) {
			e.printStackTrace();
			render(e.getMessage(), "text/plain");
		}

		return null;
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

	/**
	 * Ajax方式,根据二级类别得到违法行为
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getTransgressActionsBySecondType() {
		String id = getRequest().getParameter("secondLevelTypeId");
		TransgressType transgressType = getManager().getTransgressTypeById(id);

		if (transgressType != null
				&& StatCfgConstants.TRANSGRESSS_TYPE_2.equals(transgressType
						.getType())) {
			Set<TransgressAction> actions = transgressType
					.getTransgressActions();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (TransgressAction ta : actions) {
				Map<String, Object> map = ReflectUtil.toMap(ta, new String[] {
						"id", "code", "descns" }, true);
				list.add(map);
			}
			if (CollectionUtils.isNotEmpty(actions)) {
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

	public String[] getTransgressActionCodes() {
		return transgressActionCodes;
	}

	public void setTransgressActionCodes(String[] transgressActionCodes) {
		this.transgressActionCodes = transgressActionCodes;
	}

	public String[] getSecondLevelTypeIds() {
		return secondLevelTypeIds;
	}

	public void setSecondLevelTypeIds(String[] secondLevelTypeIds) {
		this.secondLevelTypeIds = secondLevelTypeIds;
	}

	public String[] getTransgressActionIds() {
		return transgressActionIds;
	}

	public void setTransgressActionIds(String[] transgressActionIds) {
		this.transgressActionIds = transgressActionIds;
	}

	public String[] getVehicleUseCodes() {
		return vehicleUseCodes;
	}

	public void setVehicleUseCodes(String[] vehicleUseCodes) {
		this.vehicleUseCodes = vehicleUseCodes;
	}

}
package com.googlecode.jtiger.assess.task.statcfg.webapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskManager;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressStatItemManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskAction extends DefaultCrudAction<Task, TaskManager> {
	@Autowired
	private TransgressStatItemManager tsiManager;
	private List<TransgressStatItem> transgressStatItems;

	/**
	 * 任务常量index方法
	 * 
	 * @return
	 */
	public String taskConstIndex() {
		commonIndex(AssessConstants.TASK_CONST);
		return "taskConstIndex";
	}

	/**
	 * 日常勤务index方法
	 * 
	 * @return
	 */
	public String dutyIndex() {
		commonIndex(AssessConstants.DUTY);
		return "taskDutyIndex";
	}

	/**
	 * 公用的index方法
	 * 
	 * @return
	 */
	private String commonIndex(String taskConstOrDuty) {
		// 查询标识为任务常量的记录
		String hql = "from Task t where t.taskConstOrDuty = ? ";
		List<Object> args = new ArrayList<Object>();
		args.add(taskConstOrDuty);
		// 如果界面中输入有任务名称,则查任务名称
		if (getModel() != null && StringUtils.isNotBlank(getModel().getName())) {
			hql += "and t.name like ?";
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}

		page = getManager().pageQuery(pageOfBlock(), hql, args.toArray());
		restorePageData(page);

		return INDEX;
	}

	/**
	 * 编辑任务常量方法 需要列出统计项项目
	 * 
	 * @return
	 */
	public String editTaskConst() {
		transgressStatItems = tsiManager.get();

		super.edit();

		return "editTaskConst";
	}
	/**
	 * 保存任务常量方法
	 * @return
	 */
	public String saveTaskConst(){
		//类型为任务常量
		getModel().setTaskConstOrDuty(AssessConstants.TASK_CONST);
		super.save();
		return "saveTaskConstSuccess";
	}

	/**
	 * 编辑日常勤务方法
	 * 
	 * @return
	 */
	public String editDuty() {
		super.edit();
		return "editDuty.jsp";
	}

	public List<TransgressStatItem> getTransgressStatItems() {
		return transgressStatItems;
	}

	public void setTransgressStatItems(
			List<TransgressStatItem> transgressStatItems) {
		this.transgressStatItems = transgressStatItems;
	}

}

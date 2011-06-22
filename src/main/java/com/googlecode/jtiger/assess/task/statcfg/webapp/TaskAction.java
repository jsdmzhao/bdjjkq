package com.googlecode.jtiger.assess.task.statcfg.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.AssessConstants;
import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskDetail;
import com.googlecode.jtiger.assess.task.statcfg.model.TaskType;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskDetailManager;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskManager;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskTypeManager;
import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressStatItem;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressStatItemManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.mchange.v1.util.ArrayUtils;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TaskAction extends DefaultCrudAction<Task, TaskManager> {
	@Autowired
	private TransgressStatItemManager tsiManager;
	private List<TransgressStatItem> transgressStatItems;
	@Autowired
	private TaskDetailManager taskDetailManager;
	@Autowired
	private TaskTypeManager taskTypeManager;
	private String[] detailIds;
	private String[] detailNames;
	private String[] detailAddOrDecreases;
	private String[] detailPoints;
	private String[] decreaseLeaders;

	/**
	 * 
	 */
	public String index() {
		StringBuffer buf = new StringBuffer("from Task t where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getModel().getName())) {
			buf.append("and t.name like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		if (getModel().getTaskType() != null
				&& getModel().getTaskType().getId() != null) {
			buf.append("and t.taskType.id = ?");
			args.add(getModel().getTaskType().getId());
		}

		page = getManager().pageQuery(pageOfBlock(), buf.toString(),
				args.toArray());
		restorePageData(page);

		return INDEX;
	}

	public String save() {
		// 先给已有的赋值(客户端传递过来多少个detialId,就有多少个已有的detial)
		if (detailIds != null && detailIds.length > 0) {
			for (int i = 0; i < detailIds.length; i++) {
				String id = detailIds[i];
				TaskDetail td = taskDetailManager.get(id);
				td.setName(detailNames[i]);
				td.setAddOrDecrease(detailAddOrDecreases[i]);
				td.setPoint(Float.valueOf(detailPoints[i]));
				td.setDecreaseLeader(decreaseLeaders[i]);
				taskDetailManager.save(td);
				getModel().getTaskDutyDetails().add(td);

			}
		}
		// logger.info("======>" + getModel().getTaskDutyDetails().size() + "");
		// 干掉被删除的

		List<String> ids = new ArrayList<String>();
		for (TaskDetail td : getModel().getTaskDutyDetails()) {
			ids.add(td.getId());
		}
		for (String id : ids) {
			if (ArrayUtils.indexOf(detailIds, id) == -1) {
				TaskDetail td = taskDetailManager.get(id);
				getModel().getTaskDutyDetails().remove(td);

				taskDetailManager.remove(td);
			}

		}
		// 取得id数组的最大下标,作为新添加项起始索引
		int startIndex = 0;
		if (detailIds != null && detailIds.length > 0) {
			startIndex = detailIds.length;
		}
		// 类型为B组考核标准
		getModel().setTaskConstOrDuty(AssessConstants.TASK_GROUP_B);
		super.save();
		// 剩余的是新添加的
		for (int i = startIndex; i < detailNames.length; i++) {
			TaskDetail td = new TaskDetail();
			td.setName(detailNames[i]);
			td.setAddOrDecrease(detailAddOrDecreases[i]);
			td.setPoint(Float.valueOf(detailPoints[i]));
			td.setDecreaseLeader(decreaseLeaders[i]);
			getModel().getTaskDutyDetails().add(td);
			td.setTask(getModel());
			taskDetailManager.save(td);

		}

		return SUCCESS;
	}
	/**
	 * 编辑Task
	 */
	public String edit() {
		//向界面专递TaskType集合,以供选择
		List<TaskType> taskTypes = getTaskTypes();
		getRequest().setAttribute("taskTypes", taskTypes);
		
		return INPUT;
	}

	private List<TaskType> getTaskTypes() {
		return taskTypeManager.get();
	}

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
	public String taskDutyIndex() {
		commonIndex(AssessConstants.TASK_DUTY);
		return "taskDutyIndex";
	}

	/**
	 * B组考核标准index方法
	 * 
	 * @return
	 */

	public String groupBIndex() {

		commonIndex(AssessConstants.TASK_GROUP_B);

		return "groupBIndex";
	}

	/**
	 * 重奖index方法
	 * 
	 * @return
	 */
	public String awardIndex() {
		commonIndex(AssessConstants.TASK_AWARD);
		return "awardRejectIndex";
	}

	/**
	 * 一票否决项目index方法
	 * 
	 * @return
	 */
	public String rejectIndex() {
		commonIndex(AssessConstants.TASK_REJECT);
		return "awardRejectIndex";
	}

	/**
	 * 其他奖项index方法
	 * 
	 * @return
	 */
	public String otherIndex() {
		commonIndex(AssessConstants.TASK_OTHER);
		return "awardRejectIndex";
	}

	/**
	 * 公用的index方法
	 * 
	 * @return
	 */
	private void commonIndex(String taskConstOrDuty) {
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
	 * 编辑日常勤务方法
	 * 
	 * @return
	 */
	public String editTaskDuty() {
		super.edit();
		return "editTaskDuty";
	}

	/**
	 * 编辑B组考核标准方法
	 * 
	 * @return
	 */
	public String editGroupB() {
		super.edit();
		return "editGroupB";
	}

	/**
	 * 编辑重奖一票否决及其他奖项方法
	 * 
	 * @return
	 */
	public String editAwardReject() {
		super.edit();
		return "editAwardReject";
	}

	/**
	 * 保存任务常量方法
	 * 
	 * @return
	 */
	public String saveTaskConst() {
		// 类型为任务常量
		getModel().setTaskConstOrDuty(AssessConstants.TASK_CONST);
		super.save();

		return "listTaskConst";
	}

	/**
	 * 保存日常勤务方法
	 * 
	 * @return
	 */
	public String saveTaskDuty() {

		// 类型为日常勤务
		getModel().setTaskConstOrDuty(AssessConstants.TASK_DUTY);
		super.save();

		return "listTaskDuty";
	}

	/**
	 * 保存B组考核标准方法
	 * 
	 * @return
	 */
	public String saveGroupB() {

		getModel().getTaskDutyDetails().clear();
		// logger.info("======>" + getModel().getTaskDutyDetails().size() + "");
		// 先给已有的赋值
		if (detailIds != null && detailIds.length > 0) {
			for (int i = 0; i < detailIds.length; i++) {
				String id = detailIds[i];
				TaskDetail td = taskDetailManager.get(id);
				td.setName(detailNames[i]);
				td.setAddOrDecrease(detailAddOrDecreases[i]);
				td.setPoint(Float.valueOf(detailPoints[i]));
				taskDetailManager.save(td);
				getModel().getTaskDutyDetails().add(td);

			}
		}
		// logger.info("======>" + getModel().getTaskDutyDetails().size() + "");
		// 干掉被删除的
		Set<TaskDetail> oldDetails = getModel().getTaskDutyDetails();
		for (TaskDetail td : oldDetails) {
			if (ArrayUtils.indexOf(detailIds, td.getId()) == -1) {
				getModel().getTaskDutyDetails().remove(td);
				taskDetailManager.remove(td);
			}

		}
		// 取得id数组的最大下标,作为新添加项起始索引
		int startIndex = 0;
		if (detailIds != null && detailIds.length > 0) {
			startIndex = detailIds.length;
		}
		// 类型为B组考核标准
		getModel().setTaskConstOrDuty(AssessConstants.TASK_GROUP_B);
		super.save();
		// 剩余的是新添加的
		for (int i = startIndex; i < detailNames.length; i++) {
			TaskDetail td = new TaskDetail();
			td.setName(detailNames[i]);
			td.setAddOrDecrease(detailAddOrDecreases[i]);
			td.setPoint(Float.valueOf(detailPoints[i]));
			getModel().getTaskDutyDetails().add(td);
			td.setTask(getModel());
			taskDetailManager.save(td);

		}

		return "listGroupB";
	}

	/**
	 * 保存重奖,一票否决及其他奖项
	 * 
	 * @return
	 */
	public String saveAward() {

		// 类型为日常勤务
		getModel().setTaskConstOrDuty(AssessConstants.TASK_AWARD);
		super.save();

		return "listAwardReject";
	}

	/**
	 * 删除任务常量方法
	 * 
	 * @return
	 */
	public String removeTaskConst() {
		super.remove();
		return "listTaskConst";
	}

	/**
	 * 删除日常勤务
	 */
	public String removeTaskDuty() {
		super.remove();
		return "listTaskDuty";
	}

	/**
	 * 删除B组考核标准
	 */
	public String removeGroupB() {
		super.remove();
		return "listGroupB";
	}

	/**
	 * 删除重奖,一票否决及其他奖项
	 */
	public String removeAwardReject() {
		super.remove();
		return "listAwardReject";
	}

	public List<TransgressStatItem> getTransgressStatItems() {
		return transgressStatItems;
	}

	public void setTransgressStatItems(
			List<TransgressStatItem> transgressStatItems) {
		this.transgressStatItems = transgressStatItems;
	}

	public Map<String, String> getAddOrDecreaseMap() {
		return AssessConstants.ADD_OR_DECREASE_MAP;
	}

	public String[] getDetailNames() {
		return detailNames;
	}

	public void setDetailNames(String[] detailNames) {
		this.detailNames = detailNames;
	}

	public String[] getDetailAddOrDecreases() {
		return detailAddOrDecreases;
	}

	public void setDetailAddOrDecreases(String[] detailAddOrDecreases) {
		this.detailAddOrDecreases = detailAddOrDecreases;
	}

	public String[] getDetailPoints() {
		return detailPoints;
	}

	public void setDetailPoints(String[] detailPoints) {
		this.detailPoints = detailPoints;
	}

	public String[] getDetailIds() {
		return detailIds;
	}

	public void setDetailIds(String[] detailIds) {
		this.detailIds = detailIds;
	}

	public String[] getDecreaseLeaders() {
		return decreaseLeaders;
	}

	public void setDecreaseLeaders(String[] decreaseLeaders) {
		this.decreaseLeaders = decreaseLeaders;
	}

}

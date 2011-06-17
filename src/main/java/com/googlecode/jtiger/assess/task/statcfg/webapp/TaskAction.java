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
import com.googlecode.jtiger.assess.task.statcfg.service.TaskDetailManager;
import com.googlecode.jtiger.assess.task.statcfg.service.TaskManager;
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
	private String[] detailIds;
	private String[] detailNames;
	private String[] detailAddOrDecreases;
	private String[] detailPoints;

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
	@SuppressWarnings("unchecked")
	public String groupBIndex() {

		commonIndex(AssessConstants.TASK_GROUP_B);
		List<Task> tasks = page.getData();
		for (Task t : tasks) {
			int i = 0;
			for (TaskDetail td : t.getTaskDutyDetails()) {
				if (i == 0) {
					t.setDutyItemName1(td.getName());
					t.setAddOrDecrease1(td.getAddOrDecrease());
					t.setDutyItemPoint1(td.getPoint());
					t.setDecreaseLeader1(td.getDecreaseLeader());
				} else if (i == 1) {
					t.setDutyItemName2(td.getName());
					t.setAddOrDecrease2(td.getAddOrDecrease());
					t.setDutyItemPoint2(td.getPoint());
					t.setDecreaseLeader2(td.getDecreaseLeader());
				} else {
					t.setDutyItemName3(td.getName());
					t.setAddOrDecrease3(td.getAddOrDecrease());
					t.setDutyItemPoint3(td.getPoint());
					t.setDecreaseLeader3(td.getDecreaseLeader());
				}
				i++;
			}

		}
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
		Set<TaskDetail> oldDetails = getModel().getTaskDutyDetails();
		getModel().getTaskDutyDetails().clear();
		//logger.info("======>" + getModel().getTaskDutyDetails().size() + "");
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
		//logger.info("======>" + getModel().getTaskDutyDetails().size() + "");
		// 干掉被删除的
		for (TaskDetail td : oldDetails) {
			if (ArrayUtils.indexOf(detailIds, td.getId()) == -1) {
				getModel().getTaskDutyDetails().remove(td);
				taskDetailManager.remove(td);
			}

		}
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

}

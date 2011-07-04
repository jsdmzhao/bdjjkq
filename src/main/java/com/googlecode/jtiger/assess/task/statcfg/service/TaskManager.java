package com.googlecode.jtiger.assess.task.statcfg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.googlecode.jtiger.assess.task.statcfg.model.Task;
import com.googlecode.jtiger.core.service.BaseGenericsManager;

@Service
public class TaskManager extends BaseGenericsManager<Task> {
	/**
	 * 根据类型得到任务
	 * 
	 * @return
	 */
	public List<Task> getTaskByType(String typeId) {
		String hql = "from Task t where t.taskType.id = ?";

		return query(hql, typeId);
	}
}

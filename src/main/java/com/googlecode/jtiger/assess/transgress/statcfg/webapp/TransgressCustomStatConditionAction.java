package com.googlecode.jtiger.assess.transgress.statcfg.webapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.transgress.statcfg.model.TransgressCustomStatCondition;
import com.googlecode.jtiger.assess.transgress.statcfg.service.TransgressCustomStatConditionManager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
/**
 * 自定义统计条件Action,
 * @author DELPHI
 *
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TransgressCustomStatConditionAction
		extends
		DefaultCrudAction<TransgressCustomStatCondition, TransgressCustomStatConditionManager> {
	/** 开始时分 */
	private String beginHourMinute;
	/** 结束时分 */
	private String endHourMinute;

	public String list() {
		setModel(getManager().get().get(0));
		beginHourMinute = getManager().get().get(0).getBeginHourMinute();
		endHourMinute = getManager().get().get(0).getEndHourMinute();

		return "list";
	}

	@Override
	public String save() {
		SimpleDateFormat dateSf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

		try {
			getModel().setBeginHourMinute(
					sf.format(dateSf.parse(beginHourMinute)));
			getModel().setEndHourMinute(
					sf.format(dateSf.parse(endHourMinute)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.save();
	}

	public String getBeginHourMinute() {
		return beginHourMinute;
	}

	public void setBeginHourMinute(String beginHourMinute) {
		this.beginHourMinute = beginHourMinute;
	}

	public String getEndHourMinute() {
		return endHourMinute;
	}

	public void setEndHourMinute(String endHourMinute) {
		this.endHourMinute = endHourMinute;
	}

}

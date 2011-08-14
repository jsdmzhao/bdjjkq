package com.googlecode.jtiger.assess.evaluate.stat.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.assess.core.webapp.AssessBaseAction;
import com.googlecode.jtiger.assess.evaluate.model.EvaluateRecord;
import com.googlecode.jtiger.assess.evaluate.stat.service.EvaluateRecordStatManager;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EvaluateRecordStatAction extends
		AssessBaseAction<EvaluateRecord, EvaluateRecordStatManager> {

}

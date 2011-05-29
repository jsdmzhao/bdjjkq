package ${packageName ? default('')}.${className?lower_case}.webapp;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import ${packageName ? default('')}.${className?lower_case}.model.${className ? default('')};
import ${packageName ? default('')}.${className?lower_case}.service.${className ? default('')}Manager;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ${className ? default('')}Action extends DefaultCrudAction<${className ? default('')}, ${className ? default('')}Manager> {

}
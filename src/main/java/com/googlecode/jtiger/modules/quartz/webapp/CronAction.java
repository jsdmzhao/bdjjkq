package com.googlecode.jtiger.modules.quartz.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.core.webapp.struts2.action.BaseAction;
import com.googlecode.jtiger.modules.quartz.model.Cron;
import com.googlecode.jtiger.modules.quartz.service.CronManager;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CronAction extends BaseAction {
  @Autowired
  private CronManager cronManager;
  
  private Cron c = new Cron();
  
  public String list() {
    getRequest().setAttribute("items", cronManager.get());
    return "list";
  }
  
  public String save() {
    try {
      cronManager.save(c);
      render("success", "text/plain");
    } catch (Exception e) {
      e.printStackTrace();
      render(e.getMessage(), "text/plain");
    }
    
    return null;
  }
  
  /**
   * 返回所有的Cron表达式
   * @return
   */
  public List<Map<String, String>> getCrons() {
    Map<String, String> cron1 = new HashMap<String, String>();    
    cron1.put("cron", "0 * * * * ?");
    cron1.put("name", "每隔一分钟");
    
    Map<String, String> cron2 = new HashMap<String, String>();    
    cron2.put("cron", "0 */30 * * * ?");
    cron2.put("name", "每隔30钟");
    
    Map<String, String> cron3 = new HashMap<String, String>();    
    cron3.put("cron", "0 0 * * * ?");
    cron3.put("name", "每隔1小时");
    
    List<Map<String, String>> crons = new ArrayList<Map<String, String>>();
    
    crons.add(cron1);
    crons.add(cron2);
    crons.add(cron3);
    return crons;
  }
  /**
   * @return the c
   */
  public Cron getC() {
    return c;
  }

  /**
   * @param c the c to set
   */
  public void setC(Cron c) {
    this.c = c;
  }
}

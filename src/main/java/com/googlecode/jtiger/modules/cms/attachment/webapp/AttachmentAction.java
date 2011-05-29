package com.googlecode.jtiger.modules.cms.attachment.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.modules.cms.attachment.model.Attachment;
import com.googlecode.jtiger.modules.cms.attachment.service.AttachmentManager;
import com.googlecode.jtiger.modules.upload.webapp.FlashUploadSupport;
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AttachmentAction extends FlashUploadSupport {
  private Attachment model = new Attachment();
  @Autowired
  private AttachmentManager mgr;
  /**
   * 删除文件
   */
  public String delFile() {
    try {
      mgr.remove(model);
      render("success", "text/plain");
    } catch (Exception e) {
      render(e.getMessage(), "text/plain");
    }
    return null;
  }
  /**
   * @return the model
   */
  public Attachment getModel() {
    return model;
  }
  /**
   * @param model the model to set
   */
  public void setModel(Attachment model) {
    this.model = model;
  }
}

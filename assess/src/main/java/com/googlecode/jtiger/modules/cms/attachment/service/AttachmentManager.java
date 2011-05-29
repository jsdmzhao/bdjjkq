package com.googlecode.jtiger.modules.cms.attachment.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.cms.attachment.model.Attachment;
import com.googlecode.jtiger.modules.upload.service.FileService;

@Service
public class AttachmentManager extends BaseGenericsManager<Attachment> {
  @Autowired
  private FileService fileService;
  /**
   * 删除附件，如果有对应的实体，则同时删除实体
   */
  @Override @Transactional
  public void remove(Attachment model) {
    if(model != null && StringUtils.isNotBlank(model.getUrl())) {
      Attachment att = findObject("from Attachment a where a.url=?", model.getUrl());
      if(att != null) {
        logger.debug("Delete attachment entity {}", model.getUrl());
        getDao().delete(att);
      }
      fileService.delete(model.getUrl());  
      logger.debug("Attachment has bean delete {}", model.getUrl());
    }    
  }
  
}

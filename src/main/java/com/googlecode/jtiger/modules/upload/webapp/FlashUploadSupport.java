package com.googlecode.jtiger.modules.upload.webapp;


import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.jtiger.core.webapp.struts2.action.BaseAction;
import com.googlecode.jtiger.modules.upload.img.ImageHelper;
import com.googlecode.jtiger.modules.upload.service.FileService;

/**
 * 
 * 支持SWFUpload上传的Action类
 * @author catstiger@gmail.com
 *
 */
public class FlashUploadSupport extends BaseAction {
  /**
   * 上传的文件
   */
  protected File filedata;
  
  /**
   * 原来的文件名
   */
  protected String filedataFileName;
  
  @Autowired
  protected FileService fileService;
  
  @Autowired
  protected ImageHelper imageHelper;
  
  
  /**
   * 处理上传的文件，将文件保存在临时目录下，不做缩放处理
   * ，JSON返回图片保存之后的URL
   */
  public String saveFileToTemp() {
    if(filedata == null || !filedata.exists()) {
      return null;
    }
    
    String uri = fileService.save(filedata, filedataFileName, null);
    logger.debug("Upload new file {}", uri);
    renderJson(getResponse(), toJson(uri));
    
    return null;
  }
  
  /**
   * 处理上传的文件，将文件保存在临时目录下,不过改变文件名
   */
  public String copyFileToTemp() {
    if(filedata == null || !filedata.exists()) {
      return null;
    }
    
    String uri = fileService.save(filedata, filedataFileName, null);
    File file = new File(fileService.get(uri));
    String fileName = file.getParent() + File.separator + filedataFileName;
    logger.debug("File name is {}", fileName);
    
    file.renameTo(new File(fileName));
    uri = uri.substring(0, uri.lastIndexOf("/")) + "/" + filedataFileName;
    logger.debug("Upload new file {}", uri);
    renderJson(getResponse(), toJson(uri));
    
    return null;
  }

  /**
   * @return the filedata
   */
  public File getFiledata() {
    return filedata;
  }

  /**
   * @param filedata the filedata to set
   */
  public void setFiledata(File filedata) {
    this.filedata = filedata;
  }

  /**
   * @return the filedataFileName
   */
  public String getFiledataFileName() {
    return filedataFileName;
  }

  /**
   * @param filedataFileName the filedataFileName to set
   */
  public void setFiledataFileName(String filedataFileName) {
    this.filedataFileName = filedataFileName;
  }
}

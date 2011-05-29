package com.googlecode.jtiger.modules.upload.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.googlecode.jtiger.core.ApplicationException;

@Component
public abstract class AbstractFileSerivce implements FileService {
  protected Logger logger = LoggerFactory.getLogger(getClass());
  /**
   * 测试用路径，当没有extra的时候使用,应定时删除其中的文件
   */
  public static final String TEMP_FOLDER = "temp_";
  
  public static final String DEFAULT_URL_ROOT = "/f/";
  
  
  @Value("${file.urlRoot}")
  protected String urlRoot = DEFAULT_URL_ROOT;
  
  /**
   * @see FileService#getUrlRoot()
   */
  @Override
  public String getUrlRoot() {
    return urlRoot;
  }
  
  /**
   * @see FileService#buildPath(String)
   */
  @Override
  public String buildPath(String extra) {
    return new StringBuffer(255).append(getFSRoot())
    .append(ecodingExtraCode(extra)).toString();
  }  
  

  /**
   * @see FileService#makeUrl(String, String)
   */
  @Override
  public String makeUrl(String filename, String extra) {
    String extraPath = ecodingExtraCode(extra);
    if(!StringUtils.hasText(extraPath)) {
      logger.warn("No extra path for '{}' generated. {}", filename, extra);
      throw new ApplicationException("No extra path generated.");
    }
    //将文件分隔符转换为URL分隔符
    extraPath = StringUtils.replace(extraPath, File.separator, "/");
    
    return new StringBuilder(200).append(getUrlRoot()).append(extraPath).append(filename).toString();
  }
  
  /**
   * 对附加码进行编码，生成相对路径,末尾包括@{link {@link File#separator}
   * @see FileService#buildPath(String)
   */
  protected String ecodingExtraCode(String extra) {
    StringBuffer path = new StringBuffer(200);
    
    if (StringUtils.hasText(extra)) {
      String idHash = String.valueOf(extra.hashCode()); //以extra的hash值作为路径创建的依据
      int count = 0;
      for (int i = 0; i < idHash.length(); i++) {
        path.append(idHash.charAt(i));
        count++;
        if (count == 3 || i == (idHash.length() - 1)) { // 每隔3个字符，创建一个子目录
          path.append(File.separator);
          count = 0;
        }
      }
    } else {
      path.append(TEMP_FOLDER).append(File.separator);
    }
    
    return path.toString();
  }

  /**
   * @see FileService#get(String)
   */
  @Override
  public String get(String url) {
    if(!StringUtils.hasText(url)) {
      throw new ApplicationException(new java.net.MalformedURLException(url));
    }
    String extraPath = StringUtils.replace(url, getUrlRoot(), "");
    if(extraPath.indexOf("?") > 0) {//删除URL后面的参数
      extraPath = extraPath.substring(0, extraPath.indexOf("?"));
    }
    
    return new StringBuilder(255).append(getFSRoot()).append(extraPath).toString();
  }

}

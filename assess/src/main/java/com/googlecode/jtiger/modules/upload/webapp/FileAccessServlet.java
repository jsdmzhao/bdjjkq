package com.googlecode.jtiger.modules.upload.webapp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.util.ContentTypes;
import com.googlecode.jtiger.core.util.DateUtil;
import com.googlecode.jtiger.modules.upload.img.ImageHelper;
import com.googlecode.jtiger.modules.upload.service.FileService;
import com.ibm.icu.util.Calendar;

/**
 * 查看和下载上传文件的Servlet
 * @author catstiger@gmail.com
 *
 */
public class FileAccessServlet extends HttpServlet {
  private static Logger logger = LoggerFactory.getLogger(FileAccessServlet.class);
  
  private static final String PIC_EXTS = "jpg,png,gif";
  private static Set<String> pictureFileExtensions = new HashSet<String>(10);
  static {
    String[] exts = StringUtils.split(PIC_EXTS.toLowerCase(), ",");
    for (String ext : exts) {
      pictureFileExtensions.add(ext);
    }  
  }
  private FileService fileService;
  private ImageHelper imageHelper;

  /**
   * @see GenericServlet#init(ServletConfig)
   */
  @Override
  public void init(ServletConfig config) throws ServletException {    
    super.init(config);
    //Spring
    WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
    //从Spring中取得FileService实例
    if(ctx != null && fileService == null) {
      Map<String, FileService> fileServices = ctx.getBeansOfType(FileService.class);
      if(MapUtils.isNotEmpty(fileServices)) {
        for(Iterator<FileService> itr = fileServices.values().iterator(); itr.hasNext();) {
          fileService = itr.next();
          break;
        }
      }
    }
    
    if(fileService == null) {
      logger.error("No bean of type '{}' found.", FileService.class.getName());
      throw new ApplicationException("No FileService implementation found.");      
    }
    //从Spring中取得ImageHelper实例
    if(ctx != null && imageHelper == null) {
      imageHelper = ctx.getBean("imageHelper", ImageHelper.class);
    }
    if(imageHelper == null) {
    	imageHelper = new ImageHelper();
    }
    //图片扩展名
    String pictrueFileExts = config.getInitParameter("pictrueFileExtensions");
    if (StringUtils.isBlank(pictrueFileExts)) {
      pictrueFileExts = PIC_EXTS;
    }
    
    String[] exts = StringUtils.split(pictrueFileExts.toLowerCase(), ",");
    for (String ext : exts) {
      pictureFileExtensions.add(ext);
    }   
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {     
    //super.doGet(req, resp);
    setup(req, resp); //设置响应头
    String uri = getUri(req);
    if(isPic(uri)) { //如果是图片
    	String sizeStr = req.getParameter("size"); //得到请求的图片size
    	if(StringUtils.isNotBlank(sizeStr)) {
    		String[] wh = StringUtils.split(sizeStr, "x");
    		if(ArrayUtils.isNotEmpty(wh) && wh.length == 2) {
    			//得到指定size的图片uri
    			String uriResize = 
    				imageHelper.resizeName(uri, 
    			    Integer.valueOf(wh[0]), Integer.valueOf(wh[1]));
    			//如果Resize图片确实存在，则访问该图片
    			if(new File(fileService.get(uriResize)).exists()) {
    				uri = uriResize;
    			}
    			logger.debug("Resize uri {}", uri);
    		}
    	}
    }
    fileService.read(uri, resp.getOutputStream());
  }
  
  /**
   * 返回请求URL的扩展名
   * @param req
   * @return
   */
  protected String getUri(HttpServletRequest req) {
    String uri = req.getRequestURI();
    if(StringUtils.isNotBlank(uri)) {
      if(uri.indexOf("?") > 0) {
        uri = uri.substring(0, uri.indexOf("?"));
      }
      if(uri.startsWith(req.getContextPath())) {
        uri = StringUtils.replace(uri, req.getContextPath(), "");
      }
      logger.debug("URI:{}", uri);
      return uri;
    }
    return "";
  }
  
  /**
   * 判断文件是否是图片
   * @param filename
   * @return
   */
  public static boolean isPic(String filename) {
    if(StringUtils.isBlank(filename)) {
      return false;
    }
    String ext = org.springframework.util.StringUtils.getFilenameExtension(filename);
    if(StringUtils.isBlank(ext)) {
      return false;
    }
    
    return pictureFileExtensions.contains(ext.toLowerCase());
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {    
    doGet(req, resp);
  }
  
  private void setup(HttpServletRequest req, HttpServletResponse resp) {
    resp.addHeader("Cache-Control", "max-age=31536000");
    resp.addHeader("Expires", DateUtil.add(new Date(), Calendar.YEAR, 2).toString());
    //设置Content Type
    String contentType = 
      ContentTypes.get(org.springframework.util.StringUtils.getFilenameExtension(getUri(req)));
    logger.debug("content type is {}", contentType);
    resp.setContentType(contentType);
  }
 
}

package com.googlecode.jtiger.modules.upload.img;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.util.ExecUtil;
import com.googlecode.jtiger.modules.upload.webapp.FileAccessServlet;

/**
 * 图片处理工具类
 * @author catstiger@gmail.com
 */
@Component
public class ImageHelper {
  /**
   * ImageMagick的路径
   */
  private String imageMagickDir = "";
  private String convert = "convert";
  private String identify = "identify";
  
  private static Logger logger = LoggerFactory.getLogger(ImageHelper.class);
  private ExecUtil execUtil = new ExecUtil();
  
  public ImageHelper() {
    String os = System.getProperty("os.name");
    String msg = "请安装ImageMagick For " + os;
    String msgWin = msg +
      ",并配置环境变量'IMK_HOME'指向ImageMagick的安装路径。";    
    //检验系统中是否安装了ImageMagick
    if(os != null && os.toLowerCase().indexOf("windows") >= 0) {
      imageMagickDir = System.getenv("IMK_HOME");
      if(StringUtils.isBlank(imageMagickDir) ||
          !new File(imageMagickDir + File.separator + "convert.exe").exists()){
        logger.error(msgWin);
        //throw new ApplicationException(msgWin);
      }
      if(!imageMagickDir.endsWith(File.separator)) {
        imageMagickDir += File.separator;
      }
      convert = imageMagickDir + convert;
      identify = imageMagickDir + identify;
    } else {
      if(execUtil.execute(null, convert, "-version") != 0) {
        logger.error(msg);
        //throw new ApplicationException(msg);
      }
    }
    
  }
  /**
   * 按照指定大小，生成缩略图，可以保持原有的图片比例。当原始文件大于指定的宽高时，才进行图片放大缩小
   * @param input 输入文件
   * @param output 输出文件，如果与输入文件相同，则覆盖
   * @param pixW 输出图片宽度（像素）
   * @param pixH 输出图片高度（像素）
   * @param reduce 是否给图片减肥
   */
  public void resize(File input, File output, int pixW, int pixH, boolean reduce) {
    if(input == null || !input.exists() || !input.isFile()) {
      logger.warn("Input file is not a exiting file");
      throw new ApplicationException(new FileNotFoundException(input.getAbsolutePath()));
    }
    if(output == null) {
      logger.warn("Output file is null");
      throw new ApplicationException(new FileNotFoundException(input.getAbsolutePath()));
    }
    if(!FileAccessServlet.isPic(input.getName()) || !FileAccessServlet.isPic(output.getName())) {
      logger.warn("Input or output file is not a picture.");
      throw new ApplicationException("Input or output file is not a picture.");
    }
    
    String sizeArg = new StringBuffer(100).append(pixW).append("x").append(pixH).append(">").toString();

    int exit = 0;
    if(reduce) {
      exit = execUtil.execute(null, convert, "-resize", sizeArg,
          "-strip", "-quality", "75%",
          input.getAbsolutePath(), output.getAbsolutePath()); 
    } else {
      exit = execUtil.execute(null, convert, "-resize", sizeArg,  
        input.getAbsolutePath(), output.getAbsolutePath()); 
    }
    
    logger.info("Convert result {}", exit);
  }
  /**
   * 按照指定大小，生成缩略图，缩略图按照一定的规则在原文
   * 件名上进行修改，可以保持原有的图片比例。当原始文件大于指定的宽高时，才进行图片放大缩小
   * @see #resizeName(String, int, int)
   * @see #resize(File, File, int, int, boolean)
   */
  public File resize(File input, int pixW, int pixH, boolean reduce) {
    //自动生成文件名
    String output =  resizeName(input.getAbsolutePath(), pixW, pixH);    
    File file = new File(output);
    resize(input, file, pixW, pixH, reduce);
    return file;
  }
  /**
   * 异步缩放图片，适用于单次调用（否则会出现大量的convert进程）
   * @see #resize(File, File, int, int, boolean)
   */
  @Async
  public Future<String> resizeAsync(File input, File output, int pixW, int pixH, boolean reduce) {
    try {
      resize(input, output, pixW, pixH, reduce);
    } catch(Exception e) {
      return new AsyncResult<String>("failed");
    }
    
    return new AsyncResult<String>(output.getAbsolutePath());
  }
  
  /**
   * 异步缩放图片，缩略图按照一定的规则在原文
   * 件名上进行修改，可以保持原有的图片比例。当原始文件大于指定的宽高时，才进行图片放大缩小
   * @see #resize(File, int, int, boolean)
   */
  @Async
  public Future<String> resizeAsync(File input, int pixW, int pixH, boolean reduce) {
    //自动生成文件名
    String output =  resizeName(input.getAbsolutePath(), pixW, pixH);    
    File file = new File(output);
    Future<String> res = resizeAsync(input, file, pixW, pixH, reduce);
    return res;
  }
  
  /**
   * 根据缩放比例和原来的文件名或URL，转换为新的文件名（就是在源文件名后面加缩放）
   * @param uri 原文件名
   * @param pixW 输出图片宽度（像素）
   * @param pixH 输出图片高度（像素）
   * @return 新文件名
   */
  public String resizeName(String uri, int pixW, int pixH) {
    if(StringUtils.isBlank(uri)) {
      return uri;
    }
    //去掉参数
    if(uri.indexOf("?") > 0) {
      uri = uri.substring(0, uri.indexOf("?"));
    }
    //扩展名
    String ext = org.springframework.util.StringUtils.getFilenameExtension(uri);
    //拼接新文件名
    String out = uri;
    if(uri.indexOf(".") > 0) {
      out = uri.substring(0, uri.lastIndexOf("."));
    }
    StringBuilder output = new StringBuilder(255).append(out).append("_resize_")
    .append(pixW).append("x").append(pixH);
    
    if(StringUtils.isNotBlank(ext)) {
      output.append(".").append(ext);
    }
    
    return output.toString();
  }
  
  
  
  /**
   * 获取给定图片的大小（像素）
   * @param pic 给定的图片
   * @return 第一个元素是宽度，第二个是高度
   */
  public int[] getSize(File pic) {
    if(pic == null || !pic.exists() || !pic.isFile() || !FileAccessServlet.isPic(pic.getName())) {
      return new int[]{};
    }
    String size = execUtil.exeAndReadLine(null, identify, "-format", "%wx%h", pic.getAbsolutePath());
    if(StringUtils.isNotBlank(size)) {
        String []s = StringUtils.split(size, "x");
        int []wh = new int[2];
        if(StringUtils.isNumeric(s[0])) {
          wh[0] = Integer.valueOf(s[0]);
        }
        if(StringUtils.isNumeric(s[1])) {
          wh[1] = Integer.valueOf(s[1]);
        }
        return wh;
    }
    return new int[]{};
  }
  
}

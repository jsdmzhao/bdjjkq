package com.googlecode.jtiger.core.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 生成jpg缩略图 
 * 
 * @author 54powerman
 * @version 1.0
 */
public class Thumbnail {
  /**
   * 缩略图文件夹
   */
  public static final String THUMBNAIL_FOLDER =  "_thumbnails";
  private String srcFile;
  private String destFile;
  private int width;
  private int height;
  private Image img;


  /**
   * 构造函数
   * 
   * @param fileName
   *          String
   * @throws IOException
   */
  public Thumbnail(String fileName) throws IOException {
    this(new File(fileName));
  }
  
  /**
   * 构造函数   
   */
  public Thumbnail(File file) throws IOException {
    this.srcFile = file.getPath();
    this.destFile = makeDestFile(new File(srcFile));
    img = javax.imageio.ImageIO.read(file); // 构造Image对象
    width = img.getWidth(null); // 得到源图宽
    height = img.getHeight(null); // 得到源图长
  }
  
  /**
   * 创建缩略图文件名，缩略图文件位于源文件所在目录下的{@value #THUMBNAIL_FOLDER}目录下
   */
  public static String makeDestFile(File src) {
    Assert.notNull(src);
    File parent = src.getParentFile();
    if(parent.isDirectory()) {
      File destFolder = new File(new StringBuilder(parent.getPath()).append(File.separator).append(THUMBNAIL_FOLDER).toString());
      if(!destFolder.exists()) {
        destFolder.mkdirs();
      }
      return new StringBuilder(destFolder.getPath()).append(File.separator).append(src.getName()).toString();      
    }
    return StringUtils.EMPTY;
  }

  /** */
  /**
   * 强制压缩/放大图片到固定的大小
   * 
   * @param w
   *          int 新宽度
   * @param h
   *          int 新高度
   * @throws IOException
   */
  public void resize(int w, int h) throws IOException {
    BufferedImage _image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    _image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
    FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    encoder.encode(_image); // 近JPEG编码
    out.close();
  }

  /** */
  /**
   * 按照固定的比例缩放图片
   * 
   * @param t
   *          double 比例
   * @throws IOException
   */
  public void resize(double t) throws IOException {
    int w = (int) (width * t);
    int h = (int) (height * t);
    resize(w, h);
  }

  /** */
  /**
   * 以宽度为基准，等比例放缩图片
   * 
   * @param w
   *          int 新宽度
   * @throws IOException
   */
  public void resizeByWidth(int w) throws IOException {
    int h = (int) (height * w / width);
    resize(w, h);
  }

  /** */
  /**
   * 以高度为基准，等比例缩放图片
   * 
   * @param h
   *          int 新高度
   * @throws IOException
   */
  public void resizeByHeight(int h) throws IOException {
    int w = (int) (width * h / height);
    resize(w, h);
  }

  /** */
  /**
   * 按照最大高度限制，生成最大的等比例缩略图
   * 
   * @param w
   *          int 最大宽度
   * @param h
   *          int 最大高度
   * @throws IOException
   */
  public void resizeFix(int w, int h) throws IOException {
    if (width / height > w / h) {
      resizeByWidth(w);
    } else {
      resizeByHeight(h);
    }
  }

  /** */
  /**
   * 设置目标文件名 setDestFile
   * 
   * @param fileName
   *          String 文件名字符串
   */
  public void setDestFile(String fileName) throws Exception {
    if (!fileName.toLowerCase().endsWith(".jpg")) {
      throw new Exception(" Dest File Must end with \".jpg\"");
    }
    destFile = fileName;
  }

  /** */
  /**
   * 获取目标文件名 getDestFile
   */
  public String getDestFile() {
    return destFile;
  }

  /** */
  /**
   * 获取图片原始宽度 getSrcWidth
   */
  public int getSrcWidth() {
    return width;
  }

  /** */
  /**
   * 获取图片原始高度 getSrcHeight
   */
  public int getSrcHeight() {
    return height;
  }
  
}

package com.googlecode.jtiger.core.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.core.model.BaseIdModel;

@MappedSuperclass
public abstract class BasePicModel extends BaseIdModel {
  /**
   * 大尺寸图片的宽度
   */
  @Transient
  public abstract int getLW(); 
  /**
   * 大尺寸图片的高度
   */
  @Transient
  public abstract int getLH(); 
  /**
   * 中尺寸图片的宽度
   */
  @Transient
  public abstract int getMW();
  /**
   * 中尺寸图片的高度
   */
  @Transient
  public abstract int getMH();
  /**
   * 小尺寸图片的宽度
   */
  @Transient
  public abstract int getSW();
  /**
   * 小尺寸图片的高度
   */
  @Transient
  public abstract int getSH();
  
  /**
   * 生成尺寸参数，如200x299
   * @param resize L为大尺寸，M为中尺寸，S为小尺寸，缺省为S
   * @return
   */
  public String size(String resize) {
    if(resize == null || ("LMS".indexOf(resize.toUpperCase()) < 0)) {
      resize = "S";
    }
    resize = resize.toUpperCase();
    
    int w = getMW();
    int h = getMH();
    if(StringUtils.equals("L", resize)) {
      w = getLW();
      h = getLH();
    }
    if(StringUtils.equals("S", resize)) {
      w = getSW();
      h = getSH();
    }
    
    return new StringBuilder(30).append(w).append("x").append(h).toString();
  }
}

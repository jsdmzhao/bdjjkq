package com.googlecode.jtiger.modules.cms.article.service;

import java.io.File;
import java.util.Date;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.service.BaseGenericsManager;
import com.googlecode.jtiger.modules.cms.article.model.Article;
import com.googlecode.jtiger.modules.cms.attachment.model.Attachment;
import com.googlecode.jtiger.modules.cms.attachment.service.AttachmentManager;
import com.googlecode.jtiger.modules.security.user.model.User;
import com.googlecode.jtiger.modules.upload.service.FileService;

@Service
public class ArticleManager extends BaseGenericsManager<Article> {
  @Autowired
  private FileService fileService;
 
  
  @Autowired
  private AttachmentManager attMgr;
  
  @Transactional
  public void save(Article model, User user, File cPic, String cPicFileName, 
      File tPic, String tPicFileName, String urls) {
    //处理内容图片
    String cPicUrl = null;
    if(cPic != null && cPic.exists()) {
      cPicUrl = fileService.save(cPic, cPicFileName, user.getId());
      //File file = new File(fileService.get(cPicUrl));
      //imageHelper.resize(file, file, 500, 500, true);      
    }
    //处理标题图片
    String tPicUrl = null;
    if(tPic != null && tPic.exists()) {
      tPicUrl = fileService.save(tPic, tPicFileName, user.getId());
      //File file = new File(fileService.get(tPicUrl));
      //imageHelper.resize(file, file, 150, 150, true);  
    }
    //处理附件
    addAttachments(urls, model, user);
    
    model.setTitlePic(tPicUrl);
    model.setContentPic(cPicUrl);
    model.setUpdateUser(user.getLoginId());
    Date now = new Date();
    model.setUpdateTime(now);
    //新建的文章
    if(StringUtils.isBlank(model.getId())) {
      model.setCreateTime(now);
      model.setCreateUser(user.getLoginId());
      model.setIsChecked(false);
    }
    try {
      getDao().clear();    
      getDao().merge(model);
    } catch(Exception e) {
      if(StringUtils.isNotBlank(tPicUrl)) {
        fileService.delete(tPicUrl);
      }
      if(StringUtils.isNotBlank(cPicUrl)) {
        fileService.delete(cPicUrl);
      }
      throw new ApplicationException(e);
    }
    
  }
  
  /**
   * 处理附件
   * @param urls 附件url，用|分隔
   * @param model
   * @param user
   */
  private void addAttachments(String urls, Article model, User user) {
    if(StringUtils.isNotBlank(urls)) {
      String[] attrUrls = urls.split("\\|");
      for(String url : attrUrls) {
        if(StringUtils.isNotBlank(url)) {
          Attachment att = new Attachment();          
          String filename = null;
          //得到文件名
          if(!url.endsWith("/")) {
            filename = url.substring(url.lastIndexOf("/") + 1);
          } else {
            filename = url;
          }
          //从临时目录存储到用户目录
          url = fileService.save(new File(fileService.get(url)), filename, user.getId());
          //将文件改名为原始文件名
          File file = new File(fileService.get(url));
          String newName = file.getParent() + File.separator + filename;
          logger.debug("File name is {}", newName);
          
          file.renameTo(new File(newName));
          //构建原始文件名的URL
          url = url.substring(0, url.lastIndexOf("/")) + "/" + filename;
          att.setName(filename);
          att.setUrl(url);
          att.setExtName(org.springframework.util.StringUtils.getFilenameExtension(url));
          att.setCreateTime(new Date());
          att.setCreateUser(user.getLoginId());
          
          att.setArticle(model);
          model.getAttachments().add(att);
        }
        
      }
    }
  }
  
  
  
  /**
   * 审核
   * @param model
   */
  @Transactional
  public void check(Article model) {
    Assert.notNull(model);
    Assert.hasLength(model.getCheckUser());
    Assert.hasLength(model.getId());
    
    Session session = getDao().getSessionFactory().getCurrentSession();
    session.createQuery("update Article a set a.isChecked=?,a.checkUser=? where a.id=?")
    .setBoolean(0, model.getIsChecked()).setString(1, model.getCheckUser())
    .setString(2, model.getId()).executeUpdate();
  }
  
  /**
   * 置顶
   * @param model
   */
  @Transactional
  public void top(Article model) {
    Assert.notNull(model);
    Assert.hasLength(model.getId());
    if(!model.getIsOnTop()) {
      model.setSortOrder(10000);
    }
    getDao().merge(model);
  }
  

  /**
   * 删除文章，并删除附件
   */
  @Override @Transactional
  public void remove(Article entity) {
    Assert.notNull(entity);
    Assert.hasLength(entity.getId());
    
    entity = get(entity.getId());
    if(CollectionUtils.isNotEmpty(entity.getAttachments())) {
      Set<Attachment> atts = entity.getAttachments();
      for(Attachment att : atts) {
        attMgr.remove(att);
      }
    }
    getDao().delete(entity);
  }
  
  /**
   * 置顶栏目排序
   * @param sortIds
   */
  @Transactional
  public void sort(String sortIds) {
    if(StringUtils.isBlank(sortIds)) {
      return;
    }
    
    String[] ids = StringUtils.split(sortIds, ",");
    if(ArrayUtils.isEmpty(ids)) {
      return;
    }
    Session session = getDao().getSessionFactory().getCurrentSession();
    Query query = session.createQuery("update Article a set a.sortOrder=? where a.id=?");
    for(int i = 0; i < ids.length; i++) {
      if(StringUtils.isNotBlank(ids[i])) {
        query.setInteger(0, i + 1).setString(1, ids[i]).executeUpdate();
      }
    }
    
  }
}

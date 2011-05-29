package com.googlecode.jtiger.modules.upload.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.PostConstruct;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.googlecode.jtiger.core.ApplicationException;
import com.googlecode.jtiger.core.util.FileChannelUtil;
import com.googlecode.jtiger.core.util.UUIDHex;

/**
 * 使用本地文件系统实现@{link FileServer}
 * @author catstiger@gmail.com
 *
 */
@Component
public class FileSystemFileService extends AbstractFileSerivce {
  private FileChannelUtil fileChannelUtil = new FileChannelUtil();
  
  @Value("${rootFolderName}")
  public String rootFolderName;
  
  /**
   * 启动的时候，判断旧的目录(jtiger)是否存在，如果存在,则替换成新的目录(.jtiger)
   */
  @PostConstruct
  public void init() {
    String oldPath = new StringBuffer(100).append(System.getProperty("user.home"))
    .append(File.separator).append("jtiger").append(File.separator).toString();
    File file = new File(oldPath);
    if(file.exists()) {
      file.renameTo(new File(getFSRoot()));
    }
  }
  /**
   * @see FileService#getFSRoot()
   */
  @Override
  public String getFSRoot() {
    if(!StringUtils.hasText(rootFolderName)) {
      rootFolderName = ".jtiger";
    }
    return new StringBuffer(100).append(System.getProperty("user.home"))
    .append(File.separator).append(rootFolderName).append(File.separator).toString();
  }

  /**
   * @see FileService#save(File, String)
   */
  @Override
  public String save(File file, String filename, String extra) {
    if(file == null || !file.exists()) {
      logger.warn("No uploaded file found. {}", file);
    }
    File path = new File(buildPath(extra));
    logger.debug("Generated path : {}", path.getAbsolutePath());
    if(!path.exists()) {
      if(!path.mkdirs()) {
        logger.error("Build path failed '{}'", path);
        throw new ApplicationException("Build path failed :" + path);
      }
    }
    //生成的短文件名
    String shortGeneratedFilename = new StringBuilder(100).append(UUIDHex.gen()).append(".")
    .append(StringUtils.getFilenameExtension(filename)).toString();
    //生成的文件名和路径
    String generatedFilename = new StringBuilder(255).append(path.getAbsolutePath())
    .append(File.separator).append(shortGeneratedFilename).toString();
    logger.debug("New file will be upload to '{}'", generatedFilename);
    
    try {
      fileChannelUtil.write(new File(generatedFilename), new BufferedInputStream(new FileInputStream(file)));
    } catch (FileNotFoundException e) {
      logger.error("No uploaded file found '{}'", file.getAbsolutePath());
    }
    
    return makeUrl(shortGeneratedFilename, extra);
  }

  /**
   * @see FileService#delete(String, Boolean)
   */
  @Override
  public void delete(String url) {
    File file = new File(get(url));
    if(file.exists() && file.isFile()) {
      if(!file.delete()) {
        logger.warn("File '{}' cant be deleted.", file.getAbsolutePath());
      } else {
        logger.warn("File '{}' has been deleted.", file.getAbsolutePath());
      }
    }
  }
  
  /**
   * Memcached
   */
  @Autowired(required = false)
  private MemcachedClient cache;
  /**
   * @see FileService#read(String, OutputStream)
   */
  @Override
  public void read(String url, OutputStream out) {
    byte[] fileData = null;
    if(cache  == null) {
       fileData = (byte[]) cache.get(url);
    }
    
    if(ArrayUtils.isEmpty(fileData)) {
      ByteArrayOutputStream aout = new ByteArrayOutputStream();
      
      File file = new File(get(url));
      if(!file.exists() || !file.isFile()) {
        logger.error("File of url '{}', is not found.", url);   
        return;
      }
      fileChannelUtil.read(file, aout);
      fileData = aout.toByteArray();
      if(cache != null) {
        cache.add(url, 600, fileData); //10分钟，更新一次缓存
      }
      //logger.info("File of '{}' has added to cache", url);
    } 
    try {
      FileCopyUtils.copy(fileData, out);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean exists(String url) {
    File file = new File(get(url));
    return file.exists() && file.isFile();
  }

  @Override
  public void deleteTemps() {
    String path = buildPath(null); //得到临时文件夹    
    logger.info("Clear temp path {}", path);
    File temp = new File(path);
    if(!temp.exists() || !temp.isDirectory()) {
      return;
    }
    File[] filesInTemp = temp.listFiles();
    if(ArrayUtils.isNotEmpty(filesInTemp)) {
      for(int i = 0; i < filesInTemp.length; i++) {
        filesInTemp[i].deleteOnExit();
      }
    }
    logger.info("{} files in temp folder has deleted.",  filesInTemp.length);
  }

	@Override
	public File[] get(File file) {
		if (file == null || !file.exists() || file.isDirectory()) {
			return new File[] {};
		}
		File dir = file.getParentFile();
		final String filename = file.getName();
		final String ext = StringUtils.getFilenameExtension(filename);

		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (!f.exists() || f.isDirectory()) {
					return false;
				}
				String name = f.getName();
				// 首先判断扩展名
				String ex = StringUtils.getFilenameExtension(name);
				if (!org.apache.commons.lang.StringUtils.equalsIgnoreCase(ex,
						ext)) {
					return false;
				}
				// 得到被判定文件的名字，不包括扩展名
				if (StringUtils.hasText(ex)) {
					name = name.substring(0, name.lastIndexOf("."));
				}
				// 得到参考文件的名字，不包括扩展名
				String pureName = filename;
				if (StringUtils.hasText(ext)) {
					pureName = filename.substring(0, filename.lastIndexOf("."));
				}
				// 被判定文件必须以参考文件的文件名开头
				if (!name.startsWith(pureName)) {
					return false;
				}

				return true;
			}
		});
		return (ArrayUtils.isEmpty(files)) ? new File[]{} : files;
	}

	@Override
	public void delLike(String url) {
		File file = new File(get(url));
		File[] files = get(file);
		int l = files.length;
		for(int i = 0; i < l; i++) {
			files[i].delete();
		}
	}

}

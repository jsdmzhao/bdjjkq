package com.googlecode.jtiger.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jtiger.core.ApplicationException;

public class FileChannelUtil {
  private static Logger logger = LoggerFactory.getLogger(FileChannelUtil.class);
  
  public static final int BUFFER_SIZE = 4096;
  
  public void write(File dest, InputStream in) {
    FileChannel channel = null;
    try {
      channel = new FileOutputStream(dest).getChannel();
    } catch (FileNotFoundException e) {
      logger.error("File '{}' is a directory or can not be open.", dest.getAbsolutePath());
      throw new ApplicationException(e);
    }
    try {
      int byteCount = 0;
      byte[] buffer = new byte[BUFFER_SIZE];
      int bytesRead = -1;
      while ((bytesRead = in.read(buffer)) != -1) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);
        channel.write(byteBuffer);
        byteCount += bytesRead;
      }

    } catch (IOException e) {
      throw new ApplicationException(e);
    } finally {

      try {
        if (channel != null) {
          channel.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  } 
  
  public void read(File src, OutputStream dest) {
    FileChannel channel = null;
    int bytesRead = -1;
    try {
      channel = new FileInputStream(src).getChannel();
      ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
      while ((bytesRead = channel.read(buffer)) != -1) {
        buffer.flip();
        dest.write(buffer.array(), 0, bytesRead);
        buffer.clear();
      }
    } catch (Exception e) {
      throw new ApplicationException(e);
    } finally {
      try {
        if(dest != null) {
          dest.flush();
          dest.close();
        }
        if (channel != null) {
          channel.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

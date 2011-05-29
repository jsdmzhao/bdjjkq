package com.googlecode.jtiger.modules.security.user.service;

import com.googlecode.jtiger.core.ApplicationException;

/**
 * 如果某用户不存在，则抛出本异常。
 * @author Sam
 *
 */
@SuppressWarnings("serial")
public class NoSuchUserException extends ApplicationException {

  public NoSuchUserException(String msg) {
    super(msg);
  }

}

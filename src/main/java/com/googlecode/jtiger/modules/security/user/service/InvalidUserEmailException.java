package com.googlecode.jtiger.modules.security.user.service;

import com.googlecode.jtiger.core.ApplicationException;

/**
 * 如果用户的Email不合法，则抛出该异常
 * @author Sam
 *
 */
@SuppressWarnings("serial")
public class InvalidUserEmailException extends ApplicationException {

  public InvalidUserEmailException(String msg) {
    super(msg);
  }

}

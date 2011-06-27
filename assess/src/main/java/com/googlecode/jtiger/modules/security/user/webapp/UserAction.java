package com.googlecode.jtiger.modules.security.user.webapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.googlecode.jtiger.core.dao.support.Page;
import com.googlecode.jtiger.core.webapp.struts2.action.DefaultCrudAction;
import com.googlecode.jtiger.core.webapp.struts2.action.ExtJsCrudAction;
import com.googlecode.jtiger.modules.security.user.UserConstants;
import com.googlecode.jtiger.modules.security.user.UserUtil;
import com.googlecode.jtiger.modules.security.user.model.User;
import com.googlecode.jtiger.modules.security.user.service.UserManager;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * <code>User</code>对象的struts2 action。
 * 
 * @author Sam Lee
 */
@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
/**
 * 本来是继承自ExtJsCrudAction,但是由于pageNo总是得到0,
 * 改成了DefaultCrudAction没事了,估计是由于Struts配置文件中配置不是extjs的
 * */
public class UserAction extends ExtJsCrudAction<User, UserManager> {
	/**
	 * 对应页面查询条件
	 */
	private String queryUsername;

	/**
	 * 修改密码的时候对应输入的旧密码
	 */
	private String oldPassword;

	/**
	 * 标识是否是用户自行修改个人信息，如果为<code>null</code>则表示 由管理员修改，否则，表示由用户自己修改
	 */
	private String selfEdit;

	/**
	 * 当前登录用户
	 */
	private User user;

	/**
	 * 对应上传图片的原始文件名，新文件名在getModel().getPhoto()
	 */
	private String photoFileName;

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {

		this.photoFileName = photoFileName;
	}

	@Override
	public String index() {
		StringBuffer buf = new StringBuffer("from User u where 1 = 1 ");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.isNotBlank(getModel().getName())) {
			buf.append("and u.name like ? ");
			args.add(MatchMode.ANYWHERE.toMatchString(getModel().getName()));
		}
		//buf.append("order by u.employee.id");
		page = getManager().pageQuery(pageOfBlock(), buf.toString(),
				args.toArray());
		restorePageData(page);

		return INDEX;
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.loginId", message = "登录名是必须的."),
			@RequiredStringValidator(fieldName = "model.password", message = "密码是必须的."),
			// @RequiredStringValidator(fieldName = "model.email", message =
			// "电子邮件是必须的."),
			@RequiredStringValidator(fieldName = "model.confirmPwd", message = "请两次输入密码.") }, stringLengthFields = { @StringLengthFieldValidator(fieldName = "password", minLength = "3", maxLength = "32", message = "密码应多于3字符", trim = true) }, emails = { @EmailValidator(fieldName = "model.email", message = "请输入正确的e-Mail.") }, expressions = { @ExpressionValidator(message = "两次输入的密码必须相同.", expression = "model.password==model.confirmPwd") })
	@Override
	public String save() {
		Assert.notNull(getModel());
		if (getModel().getId() == null) {
			getModel().setUserType(UserConstants.USER_TYPE_SYS);
			getModel().setStatus(UserConstants.USER_STATUS_USABLE);
		}
		String result = super.save();
		if (isSelfEdit()) {
			logger.debug("用户自己修改个人信息.");
			addActionMessage("用户信息已经成功修改。");
			return INPUT;
		} else {
			return result;
		}

	}

	/**
	 * 编辑用户
	 */
	@SkipValidation
	@Override
	public String editNew() {
		return INPUT;
	}

	/**
	 * 修改密码
	 */
	public String changePassword() {
		if (StringUtils.isBlank(oldPassword) || getModel() == null
				|| getModel().getId() == null) {
			return "changePassword";
		}
		String pwdToShow = getModel().getPassword();
		try {
			getManager().changePassword(getModel(), oldPassword);
			addActionMessage("修改密码成功，新密码是：" + pwdToShow);
		} catch (Exception e) {
			addActionError(e.getMessage());
		}
		return "changePassword";
	}

	/**
	 * ajax方式修改密码
	 * 
	 * @return
	 */
	public String userChangePassword() {
		user = UserUtil.getPrincipal(getRequest());
		if (user == null) {
			renderJson(getResponse(), "您还没有登陆不能修改密码!");
			return null;
		}

		user.setPassword(getModel().getPassword());

		if (StringUtils.isNotBlank(oldPassword)) {
			try {
				getManager().changePassword(user, oldPassword);
				renderJson(getResponse(), SUCCESS);
			} catch (Exception e) {
				renderJson(getResponse(), e.getMessage());
				return null;
			}
		}
		return null;
	}

	/**
	 * 管理员重置密码
	 * 
	 * @return
	 */
	public String resetPassword() {
		user = UserUtil.getPrincipal(getRequest());
		if (user != null
				&& UserConstants.USER_TYPE_SYS.equals(user.getUserType())
				&& getModel().getId() != null) {
			getModel().setPassword(UserConstants.RESET_PASSWORD);
			getManager().resetPassword(getModel());
			renderJson(getResponse(), UserConstants.RESET_PASSWORD);
			return null;
		}
		renderJson(getResponse(), ERROR);
		return null;
	}

	/**
	 * ajax 方式更改用户状态
	 * 
	 * @return
	 */
	public String updateStatus() {
		if (getModel().getId() != null) {
			getModel().setStatus(
					UserConstants.USER_STATUS_USABLE.equals(getModel()
							.getStatus()) ? UserConstants.USER_STATUS_UNUSABLE
							: UserConstants.USER_STATUS_USABLE);
			getManager().updateStatus(getModel());
			renderJson(getResponse(), SUCCESS);
		}
		return null;
	}

	/**
	 * 管理员删除个人与企业用户
	 * 
	 * @return
	 */
	public String removeUser() {
		user = UserUtil.getPrincipal(getRequest());
		if (user != null
				&& UserConstants.USER_TYPE_SYS.equals(user.getUserType())
				&& getModel().getId() != null) {
			getManager().removeUser(getModel());
			renderJson(getResponse(), SUCCESS);
			return null;
		}
		renderJson(getResponse(), ERROR);
		return null;
	}

	/**
	 * 启用用户
	 * 
	 * @return
	 */
	public String unsealUser() {
		if (ArrayUtils.isEmpty(selectedItems)) {
			if (getModel() != null) {
				Serializable id = extractId(getModel());
				if (id != null) {
					selectedItems = new Serializable[] { id };
				}
			}
		}
		if (selectedItems != null) {
			for (Serializable id : selectedItems) {
				if (id != null) {
					User user = getManager().get(convertId(id));
					if (user != null) {
						getManager().unsealUser(user);
					} else {
						logger.debug("用户信息不存在.");
					}
				}
			}
			logger.debug("{} items usable user.", selectedItems.length);
		}
		return SUCCESS;
	}

	public String showSelf() {
		if (getModel().getId() != null) {
			setModel(getManager().get(getModel().getId()));
		}
		return "bingo";
	}

	/**
	 * 前台注册用户修改个人信息
	 * 
	 * @return
	 */
	public String editInfo() {
		user = UserUtil.getPrincipal(getRequest());
		getModel().setLoginId(user.getLoginId());
		getModel().setPassword(user.getPassword());
		if (isSelfEdit()) {
			getManager().update(getModel());
			addActionMessage("个人信息修改成功!");
		}
		return "bingo";
	}

	/**
	 * 取得用户登陆后的信息
	 */
	public String userInfo() {
		user = UserUtil.getPrincipal(getRequest());
		return "userInfo";
	}

	/**
	 * 用户状态Map
	 */
	public Map<String, String> getUserStatusMap() {
		return UserConstants.USER_STATUS;
	}

	/**
	 * 返回性别Map
	 */
	public Map<String, String> getSexMap() {
		return UserConstants.SEX_MAP;
	}

	/**
	 * 返回学历Map
	 */
	public Map<String, String> getDegreeMap() {
		return UserConstants.DEGREE_MAP;
	}

	/**
	 * 是否用户自己修改信息
	 */
	private boolean isSelfEdit() {
		return StringUtils.isNotBlank(selfEdit);
	}

	/**
	 * @return the queryUsername
	 */
	public String getQueryUsername() {
		return queryUsername;
	}

	/**
	 * @param queryUsername
	 *            the queryUsername to set
	 */
	public void setQueryUsername(String queryUsername) {
		this.queryUsername = queryUsername;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the selfEdit
	 */
	public String getSelfEdit() {
		return selfEdit;
	}

	/**
	 * @param selfEdit
	 *            the selfEdit to set
	 */
	public void setSelfEdit(String selfEdit) {
		this.selfEdit = selfEdit;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}

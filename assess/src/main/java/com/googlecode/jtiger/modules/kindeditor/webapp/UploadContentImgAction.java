package com.googlecode.jtiger.modules.kindeditor.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.googlecode.jtiger.core.webapp.struts2.action.BaseAction;
import com.googlecode.jtiger.modules.upload.UploadUtil;

@SuppressWarnings("serial")
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UploadContentImgAction extends BaseAction {
	/**
	 * 图片文件
	 */
	private File imgFile;
	/**
	 * 图片名称
	 */
	private String imgFileFileName;
	/**
	 * 保存路径
	 */
	private String KINDEDITOR_IMG_URL = "/uploadFiles/kindeditor/";
	/**
	 * 图片说明
	 */
	private String imgTitle;
	/**
	 * 对齐方式
	 */
	private String align;
	/**
	 * 宽度
	 */
	private int imgWidth;
	/**
	 * 高度
	 */
	private int imgHeight;

	/**
	 * kindeditor图片上传
	 */
	@SuppressWarnings("unchecked")
	public String kindeditorImgUpload() throws Exception {
		if (!validatePostfix(imgFileFileName)) {
			render(getResponse(), getError("请上传规定的图片类型"), null);
		} else {
			String fileName = UploadUtil.doUpload(imgFile, imgFileFileName, KINDEDITOR_IMG_URL,
					getServletContext());
			
			String path = getRequest().getContextPath();
			String url = new StringBuffer(getRequest().getScheme())
			.append("://")
			.append(getRequest().getServerName())
			.append(":")
			.append(getRequest().getServerPort())
			.append(path).append(fileName).toString();
			
			@SuppressWarnings("rawtypes")
			Map obj = new HashMap();
			obj.put("error", 0);
			obj.put("url", url);
			obj.put("imgTitle", imgTitle);
			obj.put("align", align);
			obj.put("imgWidth", imgWidth);
			obj.put("imgHeight", imgHeight);
			render(getResponse(), JSONUtil.serialize(obj), null);
		}
		return null;
	}

	/**
	 * 验证后缀名应该从配置文件中获取
	 */
	public boolean validatePostfix(String filename) {
		// 定义可上传文件的 类型
		List<String> fileTypes = new ArrayList<String>();

		// 图片
		fileTypes.add("jpg");
		fileTypes.add("jpeg");
		fileTypes.add("bmp");
		fileTypes.add("gif");
		fileTypes.add("png");

		// 得到文件尾数 并 进行小写转换
		String postfix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		return fileTypes.contains(postfix) ? true : false;
	}

	/**
	 * 错误 信息
	 */
	@SuppressWarnings("unchecked")
	private String getError(String message) throws Exception {
		@SuppressWarnings("rawtypes")
		Map obj = new HashMap();
		obj.put("error", 1);
		obj.put("message", message);
		return JSONUtil.serialize(obj);
	}

	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getKINDEDITOR_IMG_URL() {
		return KINDEDITOR_IMG_URL;
	}

	public void setKINDEDITOR_IMG_URL(String kINDEDITOR_IMG_URL) {
		KINDEDITOR_IMG_URL = kINDEDITOR_IMG_URL;
	}

	public String getImgTitle() {
		return imgTitle;
	}

	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	}
}

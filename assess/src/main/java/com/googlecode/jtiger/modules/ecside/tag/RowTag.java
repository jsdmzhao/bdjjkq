/*
 * Copyright 2006-2007 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.jtiger.modules.ecside.tag;

import javax.servlet.jsp.JspException;

import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.bean.BaseBean;
import com.googlecode.jtiger.modules.ecside.core.bean.Row;
import com.googlecode.jtiger.modules.ecside.table.interceptor.RowInterceptor;
import com.googlecode.jtiger.modules.ecside.util.ECSideUtils;
import com.googlecode.jtiger.modules.ecside.util.ExceptionUtils;

/**
 * @author Wei Zijun
 *
 */

/**
 * @jsp.tag name="row" display-name="RowTag" body-content="JSP" description="The
 *          container which holds all the row specific information."
 * 
 */

public class RowTag extends BaseBodyTagSupport implements RowInterceptor {

	private static final long serialVersionUID = 1L;
	private String highlightClass;
	private String highlightRow;
	private String interceptor;
	private String onclick;
	private String ondblclick;
	private String onmouseout;
	private String onmouseover;
	private String style;
	private String styleClass;

	private String recordKey;

	private String selectlightClass;
	private String selectlightRow;

	private String rowId;

	// private Row row;

	public void setHighlightClass(String highlightClass) {
		this.highlightClass = highlightClass;
	}

	public void setHighlightRow(String showHighlight) {
		this.highlightRow = showHighlight;
	}

	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public int doStartTag() throws JspException {
		model = TagUtils.getModel(this);

		//boolean isExported = ExportFilterUtils.isExported(model.getContext());
		//boolean isPrint = "_print_".equals(ExportFilterUtils
		//		.getExportFileName(model.getContext()));

		Row row = null;
		if (!TagUtils.isIteratingBody(this)) {
			row = new Row(model);

			// if (!isExported || isPrint ) {
			row.setHighlightClass(TagUtils.evaluateExpressionAsString(
					"highlightClass", this.highlightClass, this, pageContext));
			row.setHighlightRow(TagUtils.evaluateExpressionAsBoolean(
					"highlightRow", this.highlightRow, this, pageContext));

			row.setSelectlightClass(TagUtils.evaluateExpressionAsString(
					"selectlightClass", this.selectlightClass, this,
					pageContext));
			row.setSelectlightRow(TagUtils.evaluateExpressionAsBoolean(
					"selectlightRow", this.selectlightRow, this, pageContext));
			// }
			row.setInterceptor(TagUtils.evaluateExpressionAsString(
					"interceptor", this.interceptor, this, pageContext));

			row.setStyle(TagUtils.evaluateExpressionAsString("style",
					this.style, this, pageContext));
			TagUtils.initExpression("style", row.getStyle(), row);

			addRowAttributes(model, row);
			model.addRow(row);

		} else {
			row = model.getRowHandler().getRow();

			// if (!isExported || isPrint ) {
			row.setOnclick(TagUtils.evaluateExpressionAsString("onclick",
					onclick, this, pageContext));
			row.setOndblclick(TagUtils.evaluateExpressionAsString("ondblclick",
					ondblclick, this, pageContext));
			row.setOnmouseout(TagUtils.evaluateExpressionAsString("onmouseout",
					onmouseout, this, pageContext));
			row.setOnmouseover(TagUtils.evaluateExpressionAsString(
					"onmouseover", onmouseover, this, pageContext));

			row.setTagAttributes(TagUtils.evaluateExpressionAsString(
					TableConstants.TAG_ATTRIBUTES, this.tagAttributes, this,
					pageContext));

			Object currentBean = TagUtils.getModel(this).getCurrentRowBean();
			String style = ECSideUtils.convertString(TagUtils.runExpression(
					"style", row, currentBean), null);
			if (style == null) {
				row.setStyle(TagUtils.evaluateExpressionAsString("style",
						this.style, this, pageContext));
			} else {
				row.setStyle(style);
			}

			row.setStyleClass(TagUtils.evaluateExpressionAsString("styleClass",
					styleClass, this, pageContext));

			row.setRecordKey(TagUtils.evaluateExpressionAsString("recordKey",
					this.recordKey, this, pageContext));
			row.setId(TagUtils.evaluateExpressionAsString("rowId", rowId, this,
					pageContext));

			// }

			modifyRowAttributes(model, row);

			model.getRowHandler().modifyRowAttributes();

		}
		return EVAL_BODY_BUFFERED;
	}

	public int doAfterBody() throws JspException {

		return super.doAfterBody();
	}

	public int doEndTag() throws JspException {

		Row row = model.getRowHandler().getRow();
		try {
			if (!TagUtils.isIteratingBody(this)) {

			} else {

				row.setAttribute(TableConstants.EXTEND_ATTRIBUTES, "");

			}
		} catch (Exception e) {
			throw new JspException("RowTag.doStartTag() Problem: "
					+ ExceptionUtils.formatStackTrace(e));
		} finally {
			doFinally();
		}

		return EVAL_PAGE;
	}

	public void doFinally() {
		super.doFinally();
	}

	public void addRowAttributes(TableModel model, Row row) {
	}

	public void modifyRowAttributes(TableModel model, Row row) {
	}

	public void release() {
		highlightClass = null;
		highlightRow = null;
		interceptor = null;
		onclick = null;
		ondblclick = null;
		onmouseout = null;
		onmouseover = null;
		style = null;
		styleClass = null;

		ondblclick = null;

		selectlightClass = null;
		selectlightRow = null;

		super.release();
	}

	public BaseBean getTagBean() {
		return TagUtils.getModel(this).getRowHandler().getRow();
	}

	public String getRecordKey() {
		return recordKey;
	}

	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}

	public String getSelectlightClass() {
		return selectlightClass;
	}

	public void setSelectlightClass(String selectlightClass) {
		this.selectlightClass = selectlightClass;
	}

	public String getSelectlightRow() {
		return selectlightRow;
	}

	public void setSelectlightRow(String selectlightRow) {
		this.selectlightRow = selectlightRow;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

}

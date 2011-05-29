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
package com.googlecode.jtiger.modules.ecside.core.bean;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.util.HtmlBuilder;

/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings({ "unchecked", "serial" })
public class Tr extends BaseBean {
	
	public static String buildTr(Tr trBean,TableModel model){
		StringBuffer tds=new StringBuffer();
		List tdList=trBean.getAllTds();
		for (int i=0;i<tdList.size();i++){
			tds.append(Td.buildTd((Td)tdList.get(i), model));
		}
		trBean.setContent(tds.toString());
		HtmlBuilder html=new HtmlBuilder();
		html.tr(1);
		html.id(trBean.getId());
		html.name(trBean.getName());
		html.style(trBean.getStyle());
		html.styleClass(trBean.getStyleClass());
		html.onclick(trBean.getOnclick());
		html.ondblclick(trBean.getOndblclick());
		html.onmouseover(trBean.getOnmouseover());
		html.onmouseout(trBean.getOnmouseout());
		html.tagAttributes(trBean.getTagAttributes());
		html.close();
		html.append(trBean.getContent());
		html.trEnd(1);
		return html.toString();
	}

    private String onclick;
    private String ondblclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;
    private String id;
    private String name;
    private String content;
    private List tdList;
    

    public Tr(TableModel model) {
       setModel(model);
       tdList=new ArrayList();
    }

    public void addTd(Td tdBean){
    	tdList.add(tdBean);
    }
    public Td getTd(int idx){
    	return (Td)tdList.get(idx);
    } 
    public List getAllTds(){
    	return tdList;
    }
    
    public String getOnclick() {
        return onclick;
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
	
    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List getTdList() {
		return tdList;
	}

	public void setTdList(List tdList) {
		this.tdList = tdList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



 
}

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

import com.googlecode.jtiger.modules.ecside.core.TableModel;


/**
 * @author Wei Zijun
 *
 */

@SuppressWarnings("serial")
public class Export extends BaseBean {
    
    private String encoding;
    private String fileName;
    private String imageName;
    private String interceptor;
    private String view;
    private String viewResolver;
    private String text;
    private String tooltip;


    public Export(TableModel model) {
    	 setModel(model);
    }

    public void defaults() {
        this.encoding = ExportDefaults.getEncoding(model, encoding);
        this.text = ExportDefaults.getText(model, text);
        this.tooltip = ExportDefaults.getTooltip(model, tooltip);
        this.viewResolver = ExportDefaults.getviewResolver(model, viewResolver);
    }

    public String getEncoding(){
        return encoding;
    }
    
    public void setEncoding(String encoding){
        this.encoding = encoding;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getViewResolver() {
        return viewResolver;
    }

    public void setViewResolver(String viewResolver) {
        this.viewResolver = viewResolver;
    }


}

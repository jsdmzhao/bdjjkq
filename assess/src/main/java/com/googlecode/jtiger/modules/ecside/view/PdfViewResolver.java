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
package com.googlecode.jtiger.modules.ecside.view;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.googlecode.jtiger.modules.ecside.core.Preferences;



/**
 * @author Wei Zijun
 *
 */

public class PdfViewResolver implements ViewResolver {
    
    //private final static String USERCONFIG_LOCATION = "exportPdf.userconfigLocation";
    //private final static String FONT_LOCATION = "exportPdf.fontLocation";

    public void resolveView(ServletRequest request, ServletResponse response, Preferences preferences, Object viewData) throws Exception {
    /*    InputStream is = new ByteArrayInputStream(((String) viewData).getBytes("UTF-8"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Driver driver = new Driver(new InputSource(is), out);
        
        String fontLocation = preferences.getPreference(FONT_LOCATION);
        String realPath=(String)request.getAttribute(TableConstants.SERVLET_REAL_PATH);
       fontLocation=realPath.replace('\\', '/')+fontLocation;
        
        String userconfigLocation = preferences.getPreference(USERCONFIG_LOCATION);
        if (userconfigLocation != null) {
            
            InputStream input = PdfViewResolver.class.getResourceAsStream(userconfigLocation);
            
            StringBuffer tempConfigurationStrBuf = new StringBuffer();   
            
            byte[] buffer = new byte[4096];   
            int len;   
            if (input!=null){
	            while ((len = input.read(buffer)) != -1) {   
	                String s = new String(buffer, 0, len);   
	                tempConfigurationStrBuf.append(s);   
	            }
            }
  
            String configurationStr = tempConfigurationStrBuf.toString(); 
            
            configurationStr = configurationStr.replaceAll("\\{webapp.font\\}", fontLocation);   
  

            ByteArrayInputStream bais = new ByteArrayInputStream(configurationStr.getBytes());  
            
            if (input != null) {
                new Options(bais);
            }
        }
        driver.setRenderer(Driver.RENDER_PDF);
        driver.run();

        byte[] contents = out.toByteArray();
        response.setContentLength(contents.length);
        response.getOutputStream().write(contents);*/
    }


}

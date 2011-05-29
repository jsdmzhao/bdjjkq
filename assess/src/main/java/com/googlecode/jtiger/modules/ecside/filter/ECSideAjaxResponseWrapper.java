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
package com.googlecode.jtiger.modules.ecside.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * @author Wei Zijun
 *
 */
public class ECSideAjaxResponseWrapper extends HttpServletResponseWrapper {


    PrintWriter pw;
    ServletOutputStream sos;
    private StringWriter writerBuffer;
    private ByteArrayOutputStream streamBuffer;

    HttpServletResponse originalResponse;

    private String redirect;

    public ECSideAjaxResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        originalResponse = httpServletResponse;
    }

    public PrintWriter getWriter() throws IOException {
        if (writerBuffer == null) {
            writerBuffer = new StringWriter();
            pw = new PrintWriter(writerBuffer);
        }
        return pw;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (streamBuffer == null) {
            streamBuffer = new ByteArrayOutputStream();
            sos = new ServletOutputStream() {
                public void write(int b) throws IOException {
                    streamBuffer.write(b);
                }

                public void write(byte b[]) throws IOException {
                    streamBuffer.write(b);
                }

                public void write(byte b[], int off, int len) throws IOException {
                    streamBuffer.write(b, off, len);
                }
            };
        }
        return sos;
    }

    /**
     * Outputs the content to OutputStream if the application is using it or to the Writer otherwise.
     */
    public void output(String content) throws IOException{
        if (streamBuffer!=null){
            streamBuffer.write(content.getBytes(originalResponse.getCharacterEncoding()));
        } else {
            writerBuffer.write(content);
        }
    }

    public String getBuffer() {

            if (streamBuffer != null) {
                try {
                   return streamBuffer.toString(originalResponse.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                   return streamBuffer.toString();
                }
            }else if (writerBuffer != null) {
                return writerBuffer.toString();
            } else {
                return "";
            }
    }

    public HttpServletResponse getOriginalResponse() {
        return originalResponse;
    }

    public String getRedirect() {
        return redirect;
    }

    public void sendRedirect(String redirect) throws IOException {
        this.redirect = redirect;
    }

    public String findSubstring(String firstDelimiter, String lastDelimiter){
        String content;
        if (streamBuffer!=null){
            try {
                content = streamBuffer.toString("UTF-8");
            } catch (UnsupportedEncodingException e) {
                content = streamBuffer.toString();
            }
        } else if (writerBuffer!=null) {
            content = writerBuffer.toString();
        } else {
            return null;
        }

        int p1 = content.indexOf(firstDelimiter);
        if (p1 != -1) {
            p1+=firstDelimiter.length();
            int p2 = content.indexOf(lastDelimiter, p1);
            if (p2!=-1){
                return content.substring(p1, p2);
            }
        }
        return content;
//        return null;
    }

    public void setContentType(String string) {
        // do nothing
    }

    public void flushBuffer() throws IOException {
        // do nothing
    }

    public void setCharacterEncoding(String string) {
        // do nothing
    }

    public void setDateHeader(String string, long l) {
        //do nothing
    }

    public void addDateHeader(String string, long l) {
        //do nothing
    }

    public void setHeader(String string, String string1) {
        //do nothing
    }

    public void addHeader(String string, String string1) {
        //do nothing
    }

    public void setIntHeader(String string, int i) {
        //do nothing
    }

    public void addIntHeader(String string, int i) {
        //do nothing
    }

    public StringWriter getWriterBuffer() {
        return writerBuffer;
    }

    public ByteArrayOutputStream getStreamBuffer() {
        return streamBuffer;
    }
}

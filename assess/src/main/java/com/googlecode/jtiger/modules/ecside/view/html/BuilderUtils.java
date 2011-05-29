/*
 * Copyright 2004 original author or authors.
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
package com.googlecode.jtiger.modules.ecside.view.html;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.TableModel;

/**
 * @author Jeff Johnston
 */
public class BuilderUtils {
    private BuilderUtils() {
    }

    public static boolean showPagination(TableModel model) {
    	String showToolBar=model.getTable().getToolbarLocation();
        return showToolBar!=null&&showToolBar.length()>0
        &&!showToolBar.equalsIgnoreCase("none")&&!showToolBar.equalsIgnoreCase("null")&&!showToolBar.equalsIgnoreCase("false");
    }
//
    public static boolean showExports(TableModel model) {
//        if (!model.getTable().isShowExports()) {
//            return false;
//        }

        return model.getExportHandler().getExports().size() > 0;
    }

//    public static boolean showStatusBar(TableModel model) {
//        return model.getTable().isShowStatusBar();
//    }

    public static boolean showTitle(TableModel model) {
        return model.getTable().isShowTitle();
    }

    public static boolean filterable(TableModel model) {
        return model.getTable().isFilterable();
    }

    public static boolean isFirstPageEnabled(int page) {
        if (page == 1) {
            return false;
        }

        return true;
    }

    public static boolean isPrevPageEnabled(int page) {
        if (page - 1 < 1) {
            return false;
        }

        return true;
    }

    public static boolean isNextPageEnabled(int page, int totalPages) {
        if (page + 1 > totalPages) {
            return false;
        }

        return true;
    }

    public static boolean isLastPageEnabled(int page, int totalPages) {
        if (page == totalPages || totalPages == 0) {
            return false;
        }

        return true;
    }

    public static int getTotalPages(TableModel model) {
        int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();

        if (currentRowsDisplayed == 0) {
            currentRowsDisplayed = model.getLimit().getTotalRows();
        }

        int totalRows = model.getLimit().getTotalRows();

        int totalPages = 1;

        if (currentRowsDisplayed != 0) {
            totalPages = totalRows / currentRowsDisplayed;
        }

        if ((currentRowsDisplayed != 0) && ((totalRows % currentRowsDisplayed) > 0)) {
            totalPages++;
        }

        return totalPages;
    }

    public static String getImage(TableModel model, String imageName) {
        String imagePath = model.getTable().getImagePath();

        if (StringUtils.isNotBlank(imagePath)) {
            int index = imagePath.indexOf("*.");
            return imagePath.substring(0, index) + imageName + imagePath.substring(index + 1);
        }

        return null;
    }
    
    /**
     * Get the form to use. If the Table form attribute 
     * is declared then use that, else use the tableId.
     * 
     * @return The name of the form.
     */
    public static String getForm(TableModel model) {
        String form = model.getTable().getForm();
        if (StringUtils.isBlank(form)) {
            form = model.getTable().getTableId();
        }

        return form;
    }
}

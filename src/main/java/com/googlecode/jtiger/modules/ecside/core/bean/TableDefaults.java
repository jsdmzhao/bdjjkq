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

import org.apache.commons.lang.StringUtils;

import com.googlecode.jtiger.modules.ecside.core.MessagesConstants;
import com.googlecode.jtiger.modules.ecside.core.TableConstants;
import com.googlecode.jtiger.modules.ecside.core.TableModel;
import com.googlecode.jtiger.modules.ecside.core.TableModelUtils;
import com.googlecode.jtiger.modules.ecside.preferences.PreferencesConstants;

/**
 * Pull the complicated values for the TableTag. Because the default values
 * could be coming from the properties or resource bundle this class will
 * abstract that out.
 */
final class TableDefaults {
    static String getTableId(String tableId) {
        if (StringUtils.isNotEmpty(tableId)) {
            return tableId;
        }

        return TableConstants.EXTREME_COMPONENTS;
    }

    static String getVar(String var, String tableId) {
        if (StringUtils.isNotEmpty(var)) {
            return var;
        }

        return tableId;
    }

    static String getStyleClass(TableModel model, String styleClass) {
        if (StringUtils.isBlank(styleClass)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_STYLE_CLASS);
        }

        return styleClass;
    }

    static String getBorder(TableModel model, String border) {
        if (StringUtils.isBlank(border)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_BORDER);
        }

        return border;
    }
    
    static Boolean isBufferView(TableModel model, Boolean bufferView) {
        if (bufferView == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_BUFFER_VIEW));
        }

        return bufferView;
    }

    static String getCellpadding(TableModel model, String cellpadding) {
        if (StringUtils.isBlank(cellpadding)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_CELLPADDING);
        }

        return cellpadding;
    }

    static String getCellspacing(TableModel model, String cellspacing) {
        if (StringUtils.isBlank(cellspacing)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_CELLSPACING);
        }

        return cellspacing;
    }

    static int getRowsDisplayed(TableModel model, int rowsDisplayed) {
        if (rowsDisplayed == 0) {
            return new Integer(model.getPreferences().getPreference(PreferencesConstants.TABLE_ROWS_DISPLAYED)).intValue();
        }

        return rowsDisplayed;
    }

//    static int getMedianRowsDisplayed(TableModel model) {
//        return new Integer(model.getPreferences().getPreference(PreferencesConstants.TABLE_MEDIAN_ROWS_DISPLAYED)).intValue();
//    }

//    static int getMaxRowsDisplayed(TableModel model) {
//        return new Integer(model.getPreferences().getPreference(PreferencesConstants.TABLE_MAX_ROWS_DISPLAYED)).intValue();
//    }

    static Boolean isSortable(TableModel model, Boolean sortable) {
        if (sortable == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SORTABLE));
        }

        return sortable;
    }

    static Boolean isClassic(TableModel model, Boolean classic) {
        if (classic == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_CLASSIC));
        }

        return classic;
    }
    
    static Boolean isGenerateScript(TableModel model, Boolean generateScript) {
        if (generateScript == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_GENERATESCRIPT));
        }

        return generateScript;
    }
    
    static Boolean isUseAjax(TableModel model, Boolean useAjax) {
        if (useAjax == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_USEAJAX));
        }

        return useAjax;
    }
    
    static Boolean isDoPreload(TableModel model, Boolean doPreload) {
        if (doPreload == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_DOPRELOAD));
        }

        return doPreload;
    }
    

    
    static Boolean isFilterable(TableModel model, Boolean filterable) {
        if (filterable == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_FILTERABLE));
        }

        return filterable;
    }

//    static Boolean isShowPagination(TableModel model, Boolean showPagination) {
//        if (showPagination == null) {
//            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SHOW_PAGINATION));
//        }
//
//        return showPagination;
//    }
//
//    static Boolean isShowExports(TableModel model, Boolean showExports) {
//        if (showExports == null) {
//            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SHOW_EXPORTS));
//        }
//
//        return showExports;
//    }
//
//    static Boolean isShowStatusBar(TableModel model, Boolean showStatusBar) {
//        if (showStatusBar == null) {
//            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SHOW_STATUS_BAR));
//        }
//
//        return showStatusBar;
//    }

    static Boolean isShowTitle(TableModel model, Boolean showTitle) {
        if (showTitle == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SHOW_TITLE));
        }

        return showTitle;
    }

    static Boolean isShowTooltips(TableModel model, Boolean showTooltips) {
        if (showTooltips == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_SHOW_TOOLTIPS));
        }

        return showTooltips;
    }
    
//    /**
//     * If the context path is not specified then put one in. However
//     * it will only provide a context if the path starts with 
//     * a slash to signal absolute and the contextPath is not 
//     * already put in.
//     *  
//     * @return
//     */
//    static String getImagePath(TableModel model, String imagePath) {
//        if (StringUtils.isNotBlank(imagePath)) {
//            return imagePath;
//        }
//        
//        String contextPath = model.getContext().getContextPath();
//        
//        String results = retrieveImagePath(model);
//        
//        if (results != null && results.startsWith("/") && !results.startsWith(contextPath)) {
//            return contextPath + results;
//        }
//        
//        return results;
//    }
    
    static String retrieveImagePath(TableModel model) {
        String resourceValue = model.getMessages().getMessage(MessagesConstants.TABLE_IMAGE_PATH);
        if (resourceValue != null) {
            return resourceValue;
        }

        return model.getPreferences().getPreference(PreferencesConstants.TABLE_IMAGE_PATH);
    }

    static String getTitle(TableModel model, String title) {
        if (TableModelUtils.isResourceBundleProperty(title)) {
            String resourceValue = model.getMessages().getMessage(title);
            if (resourceValue != null) {
                return resourceValue;
            }
        }

        return title;
    }

    static String getWidth(TableModel model, String width) {
        if (StringUtils.isBlank(width)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_WIDTH);
        }

        return width;
    }

    static Boolean getBatchUpdate(TableModel model, Boolean batchUpdate) {
        if (batchUpdate == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_BATCH_UPDATE));
        }

        return batchUpdate;
    }

    static Boolean getAutoIncludeParameters(TableModel model, Boolean autoIncludeParameters) {
        if (autoIncludeParameters == null) {
            return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_AUTO_INCLUDE_PARAMETERS));
        }

        return autoIncludeParameters;
    } 
    
    static String getFilterRowsCallback(TableModel model, String filterRowsCallback) {
        String result;

        if (StringUtils.isNotBlank(filterRowsCallback)) {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_FILTER_ROWS_CALLBACK + filterRowsCallback);
            if (StringUtils.isBlank(result)) {
                result = filterRowsCallback;
            }
        } else {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_FILTER_ROWS_CALLBACK + TableConstants.CALLBACK_DEFAULT);
        }

        return result;
    }

    static String getRetrieveRowsCallback(TableModel model, String retrieveRowsCallback) {
        String result;

        if (StringUtils.isNotBlank(retrieveRowsCallback)) {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_RETRIEVE_ROWS_CALLBACK + retrieveRowsCallback);
            if (StringUtils.isBlank(result)) {
                result = retrieveRowsCallback;
            }
        } else {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_RETRIEVE_ROWS_CALLBACK + TableConstants.CALLBACK_DEFAULT);
        }

        return result;
    }

    static String getSortRowsCallback(TableModel model, String sortRowsCallback) {
        String result;

        if (StringUtils.isNotBlank(sortRowsCallback)) {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_SORT_ROWS_CALLBACK + sortRowsCallback);
            if (StringUtils.isBlank(result)) {
                result = sortRowsCallback;
            }
        } else {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_SORT_ROWS_CALLBACK + TableConstants.CALLBACK_DEFAULT);
        }

        return result;
    }

    static String getState(TableModel model, String state) {
        String result;

        if (StringUtils.isNotBlank(state)) {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_STATE + state);
            if (StringUtils.isBlank(result)) {
                result = state;
            }
        } else {
            result = model.getPreferences().getPreference(PreferencesConstants.TABLE_STATE + TableConstants.STATE_DEFAULT);
        }

        return result;
    }

    static String getStateAttr(TableModel model, String stateAttr) {
        if (StringUtils.isBlank(stateAttr)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_STATE_ATTR);
        }

        return stateAttr;
    }

    static String getView(String view) {
        if (StringUtils.isBlank(view)) {
            return TableConstants.VIEW_HTML;
        }

        return view;
    }

    static String getMethod(TableModel model, String method) {
        if (StringUtils.isBlank(method)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_METHOD);
        }

        return method;
    }
    
    static String getTheme(TableModel model, String theme) {
        if (StringUtils.isEmpty(theme)) {
            return model.getPreferences().getPreference(PreferencesConstants.TABLE_THEME);
        }

        return theme;
    }
    
  static String getToolbarLocation(TableModel model, String toolbarLocation) {
		if (StringUtils.isEmpty(toolbarLocation)) {
			return model.getPreferences().getPreference(PreferencesConstants.TABLE_TOOLBAR_LOCATION);
		}

		return toolbarLocation;
	}
    
    static String getAlwaysShowExtend(TableModel model, String alwaysShowExtend) {
		if (StringUtils.isEmpty(alwaysShowExtend)) {
			return model.getPreferences().getPreference(PreferencesConstants.TABLE_ALWAYSSHOW_EXTEND);
		}

		return alwaysShowExtend;
	}
  
  static String getToolbarContent(TableModel model, String toolbarContent) {
		if (StringUtils.isEmpty(toolbarContent)) {
			return model.getPreferences().getPreference(PreferencesConstants.TABLE_TOOLBAR_CONTENT);
		}

		return toolbarContent;
	}
  static String getPageSizeList(TableModel model, String pageSizeList) {
	  String defaultList=model.getPreferences().getPreference(PreferencesConstants.PAGE_SIZE_LIST);
		if (StringUtils.isEmpty(pageSizeList)) {
			return defaultList;
		}
		if (pageSizeList.indexOf(",")<0 && pageSizeList.trim().toLowerCase().startsWith("max:")){
			pageSizeList=pageSizeList+","+defaultList;
		}
		return pageSizeList;
	}
  static Boolean getResizeColWidth(TableModel model, Boolean resizeColWidth) {
		if (resizeColWidth==null) {
			return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.RESIZE_COL_WIDTH));
		}

		return resizeColWidth;
	}
  static int getMinColWidth(TableModel model, int minColWidth) {
		if (minColWidth<1) {
			return Integer.parseInt(model.getPreferences().getPreference(PreferencesConstants.MIN_COL_WIDTH));
		}

		return  minColWidth;
	}
  
  static String getNearPageNum(TableModel model, String nearPageNum) {
		if (StringUtils.isBlank(nearPageNum)) {
			nearPageNum=model.getPreferences().getPreference(PreferencesConstants.NEAR_PAGE_NUM);
		}
		try{
			new Integer(nearPageNum).intValue();
		}catch(Exception e){
			nearPageNum=model.getPreferences().getPreference(PreferencesConstants.NEAR_PAGE_NUM);
		}
		return  nearPageNum;
	}
  
  static String getMaxRowsExported(TableModel model, String maxRowsExported) {
		if (StringUtils.isBlank(maxRowsExported)) {
			maxRowsExported=model.getPreferences().getPreference(PreferencesConstants.EXPORT_MAX_ROWS_EXPORTED);
		}
		try{
			new Integer(maxRowsExported).intValue();
		}catch(Exception e){
			maxRowsExported=model.getPreferences().getPreference(PreferencesConstants.EXPORT_MAX_ROWS_EXPORTED);
		}
		return  maxRowsExported;
	}
  
  
  static Boolean isEditable(TableModel model, Boolean editable) {
      if (editable == null) {
          return Boolean.valueOf(model.getPreferences().getPreference(PreferencesConstants.TABLE_EDITABLE));
      }

      return editable;
  }
  
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
var initValue = '${param.initValue}';
</script>
<style>
.x-tree-node-leaf .x-tree-node-icon {
    background-image: url(${ctx}/scripts/extjs/resources/images/default/tree/folder.gif);
}
.x-form-field-wrap .x-form-trigger{
    display: none;
}
.x-form-field-wrap .x-form-text,.x-form-field,.x-combo-noedit,.x-form-focus {
   border:1px #cecece solid;
   width:200px;
   margin: 0 0 0 0;
   padding: 0 0 0 2px;
   background: #ffffff url(${ctx}/images/icons/drop-between.gif) no-repeat right;
}
.x-combo-list {
    border:1px solid #cecece;
    background:#ffffff;
    zoom:1;
    overflow:hidden;
}

</style>
<script type="text/javascript" src="${ctx}/scripts/custom/catalog_select.js"></script>
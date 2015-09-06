<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<title>仓库管理软件</title>
<link type="text/css" rel="stylesheet" href="resource/css/easyui.css"/>
<link type="text/css" rel="stylesheet" href="resource/css/icon.css"/>
<script type="text/javascript" src="resource/js/jquery.min.js"></script>
<script type="text/javascript" src="resource/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resource/js/jquery.edatagrid.js"></script>
<script type="text/javascript" src="resource/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="resource/js/customized/init_datagrid.js"></script>
<script type="text/javascript">
	
	$(function(){
		// 初始化导航菜单
		$('#menuTree').treegrid({  
				url: rootUri + 'menuTree.json',    
		    idField:'id',    
		    treeField:'name',    
		    columns:[[    
		        {title:'导航',field:'name',width:'100%'}  
		    ]],    
		    onClickRow:function(row){
					// alert(row.id + ','+ row.name +',' + row.url);
					if (row.url != null && row.url != ''){
						$('#p').panel('refresh', rootUri + row.url); // 配置url
					}
				}
		});
		
	});
	
	// 初始化datagrid
	function contentPanelOnLoad(){ 
		parseToJson(); // 转换json对象
		
		if ($('#unit_dg').length > 0 ){ // 单位管理
			initUnitDG(); 
		}
		if ($('#category_dg').length > 0 ){ // 分类管理
			initCategoryDG();
		}
		if ($('#material_dg').length > 0 ){ // 物料管理
			initMaterialDG(); 
		}
		if ($('#stockItem_dg').length > 0 ){ // 新购入库
			initStockItemDG(); 
		}
		if ($('#search_material_dg').length > 0){ //库存查询
			initSearchMaterialDG(); 
		}
		if ($('#search_stockinout_dg').length > 0){ // 出入库查询
			initSearchStockinoutDG(); 
		}
	}
	
</script>
</head>
<body id="body">
	<!-- header -->
	<div style="height:8%;">
		<p align="left">仓库管理</p>
	</div>   
	
	<div class="easyui-layout" style="width:100%;height:85%;">
			<!-- 左边菜单栏 -->
			<div region="west" split="true" style="width:20%;height:90%;">
				<table id="menuTree" class="easyui-treegrid" style="width:100%;height:100%;">
				</table>
			</div>
	
			<!-- 中间内容 -->
			<div id="content" region="center">
				<div id="p" class="easyui-panel" data-options="fit:true,border:false,onLoad:contentPanelOnLoad">
				</div>
			</div>
	
</div> <!-- end easyui-layout  -->

<!-- footer -->
<div style="height:3%;">
	<p align="center">鹤山机械厂</p>
</div>
</body>
</html>
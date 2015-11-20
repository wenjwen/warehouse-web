<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="http://localhost:8080/warehouse/resource/css/easyui.css"/>
<link type="text/css" rel="stylesheet" href="http://localhost:8080/warehouse/resource/css/icon.css"/>
<script type="text/javascript" src="http://localhost:8080/warehouse/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/warehouse/resource/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="http://localhost:8080/warehouse/resource/js/jquery.edatagrid.js"></script>
<script type="text/javascript" src="http://localhost:8080/warehouse/resource/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="http://localhost:8080/warehouse/resource/js/customized/init_datagrid.js"></script>
</head>
<body style="width:1360px;height:768px;">
<!-- 基础信息 -->
<div class="easyui-panel" title="出库信息" style="width:100%;height:26%;">
<form id="stockInfo" action="/warehouse/stockin/save" method="post" style="width:100%;height:100%; margin-bottom:0px;">
	<table cellpadding="5">
		<tr>
 			<td>出库单号:</td>
 			<td><input class="easyui-textbox" type="text" id="stockNo" data-options="required:true" value="${stockNo }" style="width:171px;"/></td>
 			<td>出库类型:</td>
 			<td><input id="stockType" value="${stockType }" type="text" style="display:none;"/>
 				<c:if test="${stockType==4 }">
 					<input class="easyui-textbox" type="text" id="typeName" data-options="required:true" value="生产出库" style="width:100%;" readonly="readonly"></input></td>
 				</c:if>
 				<c:if test="${stockType==5 }">
 					<input class="easyui-textbox" type="text" id="typeName" data-options="required:true" value="借用出库" style="width:100%;" readonly="readonly"></input></td>
 				</c:if>
 				<c:if test="${stockType==6 }">
 					<input class="easyui-textbox" type="text" id="typeName" data-options="required:true" value="销售出库" style="width:100%;" readonly="readonly"></input></td>
 				</c:if>
 			<td>出库日期:</td>
 			<td><input class="easyui-datetimebox" type="text" id="stockTime" data-options="required:true" style="width:100%;"></input></td>
 		</tr>
 		<tr>
			<td>司机名:</td>
 			<td><input class="easyui-textbox" type="text" id="driverName" style="width:100%;"></input></td>
 			<td>车牌号:</td>
 			<td><input class="easyui-textbox" type="text" id="trunkNo" style="width:100%;"></input></td>
 			<c:if test="${stockType==4 }">
 			<td>部门(车间):</td>
 			</c:if>
 			<c:if test="${stockType==5 }">
 			<td>借用人:</td>
 			</c:if>
 			<c:if test="${stockType==6 }">
 			<td>客户:</td>
 			</c:if>
 			<td><input class="easyui-textbox" type="text" id="target" style="width:100%;" data-options="required:true"></input></td>
 		</tr>
 		<tr>
 			<td>备注:</td>
 			<td colspan="5"><input class="easyui-textbox" style="width:100%" type="text" id="remark"></input></td>
 		</tr>
	</table>
	</form>
</div>
<div class="easyui-panel" title="出库物料" style="width:100%;height:22%;">
		<form id="addForm">
	  	<table cellpadding="5">
				<tr>
		 			<td>物料:</td>
		 			<td><input id="materialComboBox" class="easyui-combobox" name="materialId"  style="width:280px;"
		 								data-options="
		 								required:true,
		 								valueField:'id',
		 								textField:'name',
		 								onSelect: function(rec){
    									$('#unitComboBox').combobox('setValue', rec.extraValue1);
    									$('#unitComboBox').combobox('setText', rec.extraValue2);
    								}"/>
		 			<td>单位:</td>
		 			<td><input id="unitComboBox" class="easyui-combobox" name="materialId" style="width:121px;"
		 								data-options="readonly:true,valueField:'id',textField:'name'"></input></td>
<!-- 		 			<td><input id="unitComboBox" class="easyui-combobox" name="materialId" style="width:171px;"
		 								data-options="required:true,valueField:'id',textField:'name',url:'/warehouse/unitList.json'"></input></td> -->
		 			<td>数量:</td>
		 			<td><input id="quantity" type="text" class="easyui-numberbox" data-options="min:0,precision:2,required:true"></input></td>
		 			<td rowspan="2"><a class="easyui-linkbutton" style="width:40px;height:60px;" href="#" onclick="addItemRow();">添加 ↓</a></td>
		 		</tr>
		 		<tr>
		 			<!-- <td>单价:</td>
		 			<td><input id="unitPrice" style="width:100%;" class="easyui-numberbox" type="text" id="quantity" data-options="min:0,precision:2,required:true"></input></td> -->
		 			<td>备注:</td>
		 			<td colspan="5"><input id="itemRemark" class="easyui-textbox" style="width:100%" type="text"></input></td>
		 		</tr>
			</table>
	  </div> 
<!-- stockitem edatagrid -->
<div style="width:100%;height:42%;">
	<table id="stockItem_dg"  style="width:100%;height:100%;" hideColumn="unitPrice"
			idField="materialId" rownumbers="true" fitColumns="true" singleSelect="false">
	</table>
	<!-- <div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#stockItem_dg').edatagrid('addRow')">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="appendRow();">新增</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleterow()">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#stockItem_dg').edatagrid('cancelRow')">取消</a> 
	</div> -->
	
</div>
<!-- 提交 -->
		<div style="width:100%;height:8%;text-align:center;">
	    	<a  class="easyui-linkbutton" style="margin-top:10px;width:50px;" href="javascript:void(0)" onclick="submitStockForm()">保存</a>
	    	<a  class="easyui-linkbutton" style="margin-top:10px;width:50px;" href="javascript:void(0)" onclick="clearForm('stockInfo')">取消</a>
	  </div>
</form>

</body>
</html>
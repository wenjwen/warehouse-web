<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<!-- 基础信息 -->
<div class="easyui-panel" style="width:100%;height:100它px;">
		<form id="takingForm">
	  	<table cellpadding="5">
				<tr>
		 			<td>物料:</td>
		 			<td><input id="materialComboBox" class="easyui-combobox" name="materialId"  style="width:171px;"
		 								data-options="required:true,
		 								valueField:'id',
		 								textField:'name',
		 								data:materialEntry,
		 								onSelect: function(rec){
    									$('#unitComboBox').combobox('setValue', rec.extraValue1);
    									$('#unitComboBox').combobox('setText', rec.extraValue2);
    									$('#balance').numberbox('setValue', rec.extraValue3); // 剩余数量balance
    								}"/>
		 			<td>账面数量:</td>
		 			<td><input id="balance" type="text" class="easyui-numberbox" data-options="readonly:true,precision:2"/></td>
		 			<td>单位:</td>
		 			<td><input id="unitComboBox" class="easyui-combobox" name="materialId" style="width:171px;"
		 								data-options="readonly:true, valueField:'id',textField:'name'"/></td>
<!-- 		 			<td><input id="unitComboBox" class="easyui-combobox" name="materialId" style="width:171px;"
		 								data-options="required:true,valueField:'id',textField:'name',url:'/warehouse/unitList.json'"/></td> -->
		 			<td rowspan="2"><a class="easyui-linkbutton" style="width:40px;height:60px;" href="#" onclick="addTakingItemRow();">添加 ↓</a></td>
		 		</tr>
		 		<tr>
		 			<td>盘点数量:</td>
		 			<td><input id="quantity" type="text" class="easyui-numberbox" data-options="min:0,precision:2,required:true"></input></td>
		 			<td>备注:</td>
		 			<td colspan="3"><input id="itemRemark" class="easyui-textbox" style="width:100%" type="text"></input></td>
		 		</tr>
			</table>
		</form>
	  </div> 
<!-- stockitem edatagrid -->
<div style="width:100%;height:78%;">
	<table id="stockTakingItem_dg"  style="width:100%;height:100%;"
			idField="materialId" rownumbers="true" fitColumns="true" singleSelect="false">
	</table>
</div>
<!-- 提交 -->
		<div style="width:100%;height:6%;text-align:center;">
	    	<a  class="easyui-linkbutton" style="margin-top:10px;width:50px;" href="javascript:void(0)" onclick="submitStockForm()">保存</a>
	  </div>

</body>
</html>
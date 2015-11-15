<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<table id="search_stockinout_dg" title="出入库查询" style="width:100%;height:100%;"
		toolbar="#toolbar" idField="id" autoSave="false"
		rownumbers="true" fitColumns="true" singleSelect="true">
</table>
<div id="toolbar">
	<table>
	<tr  style="text-align:right;">
	<td>
		<span>单号</span>
		</td><td>
		<input id="stockNo" class="easyui-textbox" style="width:171px;line-height:26px;border:1px solid #ccc"/>
		</td>
	<td>
	<span>出入库类型</span>
	</td><td>
	<input id="stockType" class="easyui-combobox" data-options="valueField:'id',textField:'name',data:stockTypeEntry" style="width:121px;line-height:26px;border:1px solid #ccc"/>
	</td><td>
	<span>物料名</span>
	</td><td>
	<input id="id" class="easyui-combobox" data-options="valueField:'id',textField:'name'" style="width:250px;line-height:26px;border:1px solid #ccc"/>
	</td>
	<td>
	<span>日期</span>
	</td><td>
	<input id="stockTime" class="easyui-datetimebox" style="width:171px;line-height:26px;border:1px solid #ccc"/>
	</td>
	<td style="text-align:center;width:100px">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearchStockinout()">搜索</a>
	</td>
	</tr><tr style="text-align:right;">
	<td>
	<span>司机</span>
	</td><td>
	<input id="driverName" class="easyui-textbox" style="width:171px;line-height:26px;border:1px solid #ccc"/>
	</td>
	<td>
		<span>车牌</span>
	</td><td>
		<input id="trunkNo" class="easyui-textbox" style="width:121px;line-height:26px;border:1px solid #ccc"/>
	</td>
	<td>
		<span id="targetName">目标</span>
		</td><td>
		<input id="target" class="easyui-textbox" style="width:250px;line-height:26px;border:1px solid #ccc"/>
		</td><td>
		<span id="sourceName">来源</span>
		</td><td>
		<input id="source" class="easyui-textbox" style="width:171px;line-height:26px;border:1px solid #ccc"/>
	</td>
	</tr>
	<!-- 
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:$('#search_stockinout_dg').edatagrid('addRow')">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#search_stockinout_dg').edatagrid('destroyRow')">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#search_stockinout_dg').edatagrid('saveRow')">保存</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#search_stockinout_dg').edatagrid('cancelRow')">取消</a>
 -->
 </table>
</div>
<script type="text/javascript">
	// 获取json字符串
	// categoryJson ='${categoryJson}';
	// unitJson = '${unitJson}';
	// materialJson = '${materialJson}';
</script>
</body>
</html>
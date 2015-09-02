<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="easyui-panel" title="库存查询" style="width:100%;height:100%;">
<table class="easyui-datagrid" style="width:100%;height:100%;"
				 data-options="url:'/warehouse/inventorySearch.json',fitColumns:true,singleSelect:true,rownumbers:true">
    <thead>
		<tr>
			<th data-options="field:'name',width:100">物料名</th>
			<th data-options="field:'size',width:100">规格</th>
			<th data-options="field:'unitName',width:80">单位</th>
			<th data-options="field:'totalQuantity',width:80">总数量</th>
			<th data-options="field:'balance',width:80">剩余数量</th>
			<th data-options="field:'unitPrice',width:100">最后单价</th>
			<th data-options="field:'avgUnitPrice',width:100">平均价格</th>
			<th data-options="field:'lastStockTake',width:120">最后盘点时间</th>
		</tr>
    </thead>
    <tbody>
	</tbody>
</table>
</div>
</body>
</html>
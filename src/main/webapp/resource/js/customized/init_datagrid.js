/**
 * 
 */
var rootUri = '/warehouse/';
//单位
function initUnitDG(){
	$('#unit_dg').edatagrid({
		url: rootUri + 'unitList.json',
		saveUrl: rootUri + 'saveUnit',
		updateUrl: rootUri + 'updateUnit',
		destroyUrl: rootUri + 'deleteUnit',
		autoSave: false,
		checkOnSelect: false,
		onError: function(index,row){
			alert(index + ', ' + row.msg);
		},
		onAdd: function(index,row){  // 添加新行时
			//alert("add row : index = " + index);				
		},
		onSave: function(index, row){  // 保存后
			if(!row.isError){
				$.messager.alert("提示","保存成功", "info");
			} else if(row.isError){
				$.messager.alert("提示","保存失败！", "info");
			}
		},
		destroyMsg:{
			norecord:{	// when no record is selected
				title:'警告',
				msg:'未选择任何条目.'
			},
			confirm:{	// when select a row
				title:'确认',
				msg:'确定要删除?'
			}
		},
		onDestroy:function(index, row){  // 删除后
			if(!row.isError){
				$.messager.alert("提示","删除成功", "info");
				$('#unit_dg').edatagrid('reload');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		}
	});
}

// 分类 
function initCategoryDG(){
	$('#category_dg').edatagrid({
		url: rootUri + 'category.json',
		saveUrl: rootUri + 'saveCategoryt',
		updateUrl: rootUri + 'updateCategoryt',
		destroyUrl: rootUri + 'deleteCategoryt',
		autoSave: false,
		checkOnSelect: false,
		onError: function(index,row){
			alert(index + ', ' + row.msg);
		},
		onAdd: function(index,row){  // 添加新行时
			//alert("add row : index = " + index);				
		},
		onSave: function(index, row){  // 保存后
			if(!row.isError){
				$.messager.alert("提示","保存成功", "info");
			} else if(row.isError){
				$.messager.alert("提示","保存失败！", "info");
			}
		},
		destroyMsg:{
			norecord:{	// when no record is selected
				title:'警告',
				msg:'未选择任何条目.'
			},
			confirm:{	// when select a row
				title:'确认',
				msg:'确定要删除?'
			}
		},
		onDestroy:function(index, row){  // 删除后
			if(!row.isError){
				$.messager.alert("提示","删除成功", "info");
				$('#category_dg').edatagrid('reload');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		}
	});
}

function initStockItemDG(){
	$('#stockItem_dg').edatagrid({
		autoSave: false,
		onError: function(index,row){
			alert(index + ', ' + row.msg);
		},
		columns:[[
		          	{field:'materialId', hidden:true},
					{field:'materialName',title:'物料',width:80},
					{field:'unitId', hidden:true},
					{field:'unitName',title:'单位',width:80},
					{field:'unitPrice',title:'单价',width:80,editor:{type:'numberbox', options:{precision:2}}},
					{field:'quantity',title:'数量',width:80,editor:{type:'numberbox', options:{precision:2}}},
					{field:'remark',title:'备注',width:140,editor:'text'},
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index){
								var d = "<a href=\"#\" onclick=\"javascript:$('#stockItem_dg').edatagrid('deleteRow','"+index+"')\">Delete</a>";
								return d;
						}
					}
				]],
	});
	
	var stockType = $('#stockType').val();
	if (stockType == 4 || stockType == 5 || stockType == 6){ // stock out
		$('#stockItem_dg').edatagrid('hideColumn', 'unitPrice');
	}
	
}

/**
 * 添加
 */
function addItemRow(){
	if (!$('#addForm').form('validate'))
		return;
	var mId = $('#materialComboBox').combo('getValue');
	var mName = $('#materialComboBox').combo('getText');
	if (mId != null && mId == mName){
		$.messager.alert("错误","'" + mName + "' 物料不存在！","error");
		return;
	}
	var uId = $('#unitComboBox').combo('getValue');
	var uName = $('#unitComboBox').combo('getText');
	if (uId != null && uId == uName){
		$.messager.alert("错误","'" + uName + "' 单位不存在！","error");
		return;
	}
	
	$('#stockItem_dg').datagrid('appendRow',{
		materialId: mId,
		materialName: mName,
		unitId: uId,
		unitName: uName,
		unitPrice: $('#unitPrice').val(),
		quantity: $('#quantity').val(),
		remark: $('#itemRemark').val()
	});
}

/**
 * 提交stock form
 */
function submitStockForm(url){
	if ($('#stockInfo').form('validate') && $('#stockItem_dg').datagrid('getRows').length > 0){
		// 取数据
		var item;
		var itemArr = [];
		var rows = $('#stockItem_dg').datagrid('getRows');
		for(var i=0; i<rows.length;i++){
			console.log('material id =' + rows[i].materialId);
			item = new Object();
			item.materialId = rows[i].materialId;
			item.unitId = rows[i].unitId;
			item.quantity = rows[i].quantity;
			item.unitPrice = rows[i].unitPrice;
			item.remark = rows[i].remark;
			itemArr.push(item);
		}
		
		$.ajax({
			url:url,
			dataType:'json',
			type:'POST',
			data : {
					stockNo:$('#stockNo').val(), 
					typeName:$('#typeName').val(),
					stockType:$('#stockType').val(),
					stockTime:$('#stockTime').combo('getValue'),
					driverName:$('#driverName').val(),
					trunkNo:$('#trunkNo').val(),
					source:$('#source').val(),
					target:$('#target').val(),
					remark:$('#remark').val(),
					itemsStr:JSON.stringify(itemArr)
				},
			success:function(data){
				if(!data.isError){
					$.messager.alert('提示','保存成功','info');
				} 
				else {
					$.messager.alert('错误','保存失败！','error');
				}
			}
		});
	}
}


/**
 * 清除指定form
 */
function clearForm(formName){
	$('#' + formName).form('clear');
}
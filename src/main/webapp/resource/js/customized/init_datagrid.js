/**
 * 
 */
var rootUri = '/warehouse/';
var unitEntry; // 单位
var categoryEntry; // 分类
//单位
function initUnitDG(){
	unitEntry = JSON.parse(unitJson);
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
				$('#p').panel('refresh');
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
				$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		}
	});
}

// 分类
function initCategoryDG(){
	//字符串转json对象
	categoryEntry = JSON.parse(categoryJson);
	$('#category_dg').edatagrid({
		url: rootUri + 'category.json',
		saveUrl: rootUri + 'saveCategory',
		updateUrl: rootUri + 'updateCategory',
		destroyUrl: rootUri + 'deleteCategory',
		autoSave: false,
		checkOnSelect: false,
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","保存失败！请检查分类名是否已存在", "error");
		},
		onAdd: function(index,row){  // 添加新行时
			
		},
		onSave: function(index, row){  // 保存后
			if(!row.isError){
				$.messager.alert("提示","保存成功", "info");
				$('#p').panel('refresh');
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
				$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		},
		columns:[[
		          {field:'ck', checkbox:true},
		          {field:'name',title:'分类名',width:80, editor:{type:'textbox', required:true}},
		          {field:'parentId',title:'上级分类',width:80,
		        	  formatter:function(value){
		        		  for(var i=0; i<categoryEntry.length; i++){
		        			  if (categoryEntry[i].id == value) return categoryEntry[i].name;
		        		  }
		        		  return value;
		        	  },
		        	  editor:{
		        		  type:'combobox',
		        		  options:{
		        			  valueField:'id',
		        			  textField:'name',
		        			  data:categoryEntry
		        		  }
		        	  }
		          },
		          {field:'remark',title:'备注',width:100, editor:{type:'textbox'}}
		          ]],
	});
}
// 物料管理
function initMaterialDG(){
	//字符串转json对象
	categoryEntry = JSON.parse(categoryJson);
	$('#material_dg').edatagrid({
		url: rootUri + 'material.json',
		saveUrl: rootUri + 'saveMaterial',
		updateUrl: rootUri + 'updateMaterial',
		destroyUrl: rootUri + 'deleteMaterial',
		autoSave: false,
		checkOnSelect: false,
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","操作失败！", "error");
		},
		onAdd: function(index,row){  // 添加新行时
			
		},
		onSave: function(index, row){  // 保存后
			if(!row.isError){
				$.messager.alert("提示","保存成功", "info");
				$('#p').panel('refresh');
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
				$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		},
		columns:[[
		          {field:'ck', checkbox:true},
		          {field:'name',title:'物料名',width:80, editor:{type:'textbox', required:true}},
		          {field:'categoryId',title:'所属分类',width:80,
		        	  formatter:function(value){
		        		  for(var i=0; i<categoryEntry.length; i++){
		        			  if (categoryEntry[i].id == value) return categoryEntry[i].name;
		        		  }
		        		  return value;
		        	  },
		        	  editor:{
		        		  type:'combobox',
		        		  options:{
		        			  valueField:'id',
		        			  textField:'name',
		        			  data:categoryEntry,
		        			  required:true
		        		  }
		        	  }
		          },
		          {field:'unitId',title:'单位',width:60,
		        	  formatter:function(value){
		        		  for(var i=0; i<unitEntry.length; i++){
		        			  if (unitEntry[i].id == value) return unitEntry[i].name;
		        		  }
		        		  return value;
		        	  },
		        	  editor:{
		        		  type:'combobox',
		        		  options:{
		        			  valueField:'id',
		        			  textField:'name',
		        			  data:unitEntry,
		        			  required:true
		        		  }
		        	  }
		          },
		          {field:'size',title:'规格',width:60, editor:{type:'textbox'}},
		          {field:'totalQuantity',title:'单价',width:60, editor:{type:'numberbox', options:{precision:2, required:true}}},
		          {field:'balance',title:'平均单价',width:60, editor:{type:'numberbox',options:{precision:2, required:true}}},
		          {field:'remark',title:'备注',width:100, editor:{type:'textbox'}}
		          ]],
	});
}
// 分类
function initCategoryDG(){
	//字符串转json对象
	categoryEntry = JSON.parse(categoryJson);
	$('#category_dg').edatagrid({
		url: rootUri + 'category.json',
		saveUrl: rootUri + 'saveCategory',
		updateUrl: rootUri + 'updateCategory',
		destroyUrl: rootUri + 'deleteCategory',
		autoSave: false,
		checkOnSelect: false,
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","保存失败！请检查分类名是否已存在", "error");
		},
		onAdd: function(index,row){  // 添加新行时
			
		},
		onSave: function(index, row){  // 保存后
			if(!row.isError){
				$.messager.alert("提示","保存成功", "info");
				$('#p').panel('refresh');
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
				$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		},
		columns:[[
		          	{field:'ck', checkbox:true},
					{field:'name',title:'分类名',width:80, editor:{type:'textbox', required:true}},
					{field:'parentId',title:'上级分类',width:80,
						formatter:function(value){
							for(var i=0; i<categoryEntry.length; i++){
								if (categoryEntry[i].id == value) return categoryEntry[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'id',
								textField:'name',
								data:categoryEntry
							}
						}
					},
					{field:'remark',title:'备注',width:100, editor:{type:'textbox'}}
				]],
	});
}

// 分类信息查询
function doSearchCategory(){
	var id = $('#id').combo('getValue');
	var name = $('#id').combo('getText');
	if (id != null && id==name){
		$('#category_dg').datagrid('load',{
			name: $('#id').combo('getText'),
			parentId: $('#parentId').combo('getValue')
		});
	}else{
		$('#category_dg').datagrid('load',{
			id: $('#id').combo('getValue'),
			name: $('#id').combo('getText'),
			parentId: $('#parentId').combo('getValue')
		});
	}
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
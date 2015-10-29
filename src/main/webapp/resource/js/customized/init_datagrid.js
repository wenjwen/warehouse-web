
var rootUri = '/warehouse/';
var unitEntry; // 单位
var categoryEntry; // 分类
var materialEntry; // 物料
var stockTypeEntry=[// 出入库类型
{"id":1,"name":"新购入库"},
{"id":2,"name":"归还入库"},
{"id":3,"name":"退货入库"},
{"id":4,"name":"生产出库"},
{"id":5,"name":"借用出库"},
{"id":6,"name":"销售出库"}]; 

// 转成js对象
function parseToJson(){
	if (typeof(unitJson) != "undefined" && unitJson != null && unitJson != ''){
		unitEntry = JSON.parse(unitJson);
	}
	if (typeof(categoryJson) != "undefined" && categoryJson != null && categoryJson != ''){
		categoryEntry = JSON.parse(categoryJson);
	}
	if (typeof(materialJson) != "undefined" && materialJson != null && materialJson != ''){
		materialEntry = JSON.parse(materialJson);
	}
}

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
			if (row.code == '19'){
				$.messager.alert("提示","此单位正在使用，不能删除！", "error");
			}
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
		          {field:'name',title:'分类名',width:80, editor:{type:'textbox', options:{required:true}}},
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
	
	$('#id').combobox('loadData', categoryEntry);
	$('#parentId').combobox('loadData', categoryEntry);
	
}
// 物料管理
function initMaterialDG(){
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
				//$('#p').panel('refresh');
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
				//$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		},
		columns:[[
		          {field:'ck', checkbox:true},
		          {field:'name',title:'物料名',width:80, editor:{type:'textbox', required:true}},
		          {field:'code',title:'编码',width:80, editor:{type:'textbox'}},
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
		          {field:'totalQuantity',title:'总数量',width:60, editor:{type:'numberbox', options:{precision:2}}},
		          {field:'balance',title:'库存数量',width:60, editor:{type:'numberbox',options:{precision:2}}},
		          {field:'remark',title:'备注',width:100, editor:{type:'textbox'}}
		          ]],
	});
}
// 物料盘点
function initStocktakeItem_DG(){
	$('#stocktakeItem_dg').edatagrid({
		url: rootUri + 'stocktakeItem.json',
		//saveUrl: rootUri + 'stocktake/saveItem',
		updateUrl: rootUri + 'stocktake/updateItem',
		//destroyUrl: rootUri + 'stocktake/deleteItem',
		autoSave: false,
		checkOnSelect: false,
		queryParams: {  // 请求时的参数
			stocktakeId: $('#stocktakeId').val(),
		},
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","操作失败！", "error");
		},
		onAdd: function(index,row){  // 添加新行时
			
		},
		onSave: function(index, rowdata){  // 保存后
			if(!rowdata.isError){
				// $.messager.alert("提示","保存成功", "info");
				//$('#p').panel('refresh');
				/*if(rowdata.quantity!=null && rowdata.balance!=null){
					rowdata.differQuantity = accSub(rowdata.quantity,rowdata.balance);
					rowdata.result = (rowdata.differQuantity < 0 ? '-1' : (rowdata.differQuantity == 0 ? '0':'1'));
					rowdata.ck = 0;
					$('#stocktakeItem_dg').edatagrid('updateRow',{index: rowdata.index, row:rowdata});
				}*/
			} else if(rowdata.isError){
				$.messager.alert("提示","保存失败！", "info");
				$('#stocktakeItem_dg').edatagrid('cancelRow');
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
				//$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","删除失败！", "info");
			}
		},
		columns:[[
		          //{field:'ck', checkbox:true},
		          {field:'materialId',title:'物料名',width:80,
		        	  formatter:function(value){
		        		  for(var i=0; i<materialEntry.length; i++){
		        			  if (materialEntry[i].id == value) return materialEntry[i].name;
		        		  }
		        		  return value;
		        	  }
		          },
		          {field:'unitId',title:'单位',width:60,
		        	  formatter:function(value){
		        		  for(var i=0; i<unitEntry.length; i++){
		        			  if (unitEntry[i].id == value) 
		        				  return unitEntry[i].name;
		        		  }
		        		  return '未知';
		        	  }/*,
		        	  editor:{
		        		  type:'combobox',
		        		  options:{
		        			  valueField:'id',
		        			  textField:'name',
		        			  data:unitEntry,
		        			  required:true
		        		  }
		        	  }*/
		          },
		          {field:'balance',title:'账面数量',width:60/*, editor:{type:'numberbox',options:{precision:2}}*/},
		          {field:'quantity',title:'盘点数量',width:60, editor:{type:'numberbox',options:{precision:2}}},
		          {field:'differQuantity',title:'相差数量',width:60/*, editor:{type:'numberbox',options:{precision:2}}*/},
		          {field:'result',title:'盘点结果',width:60, 
		        	  formatter:function(value){
		        		  switch(value){  // -1-盘亏 0-正常 1-盘盈
		        		  	case '-1': return '<span style="color:red;">盘亏</span>';
		        		  	case '0': return '<span style="color:blue;">正常</span>';
		        		  	case '1': return '<span style="color:green;">盘盈</span>';
		        		  }
		        		  return '';
		        	  }
		          },
		          {field:'remark',title:'备注',width:100, editor:{type:'textbox'}}
		          ]],
	});
	
	$('#materialId').combobox('loadData', materialEntry);
	
	// 提交后datagrid设置
	var submitted = $('#submitted').val();  
	if (submitted == '1'){ // 已提交
		$('#stocktakeItem_dg').edatagrid('disableEditing');
		//$('#stocktakeItem_dg').edatagrid('hideColumn','ck');
	}
}

// 物料查询
function initSearchMaterialDG(){
	$('#search_material_dg').datagrid({
		url: rootUri + 'material.json',  // 在MaterialController.java中
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","操作失败！", "error");
		},
		columns:[[
		          {field:'name',title:'物料名',width:80},
		          {field:'code',title:'编码',width:80},
		          {field:'categoryId',title:'所属分类',width:80,
		        	  formatter:function(value){
		        		  for(var i=0; i<categoryEntry.length; i++){
		        			  if (categoryEntry[i].id == value) return categoryEntry[i].name;
		        		  }
		        		  return value;
		        	  }
		          },
		          {field:'unitName',title:'单位',width:60},
		          {field:'size',title:'规格',width:60},
		          {field:'totalQuantity',title:'总数量',width:60},
		          {field:'balance',title:'库存数量',width:60},
		          {field:'unitPrice',title:'单价(元)',width:60},
		          {field:'avgUnitPrice',title:'平均单价(元)',width:60},
		          {field:'remark',title:'备注',width:100}
		          ]],
	});
	
	$('#id').combobox('loadData', materialEntry);
	$('#categoryId').combobox('loadData', categoryEntry);
}

// 出入库查询
function initSearchStockinoutDG(){
	$('#search_stockinout_dg').datagrid({
		url: rootUri + 'stockinout.json',  // 在MaterialController.java中
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","操作失败！", "error");
		},
		columns:[[
		          {field:'typeName',title:'出入库类型',width:40},
		          {field:'stockNo',title:'单号',width:50},
		          {field:'materialId',title:'物料',width:70,
		        	  formatter:function(value){
		        		  for(var i=0; i<materialEntry.length; i++){
		        			  if (materialEntry[i].id == value) return materialEntry[i].name;
		        		  }
		        		  return value;
		        	  }
		          },
		          {field:'unitId',title:'单位',width:30,
		        	  formatter:function(value){
		        		  for(var i=0; i<unitEntry.length; i++){
		        			  if (unitEntry[i].id == value) return unitEntry[i].name;
		        		  }
		        		  return value;
		        	  }
		          },
		          {field:'size',title:'规格',width:50},
		          {field:'quantity',title:'数量',width:40},
		          {field:'unitPrice',title:'单价(元)',width:40},
		          //{field:'avgUnitPrice',title:'平均单价(元)',width:40},
		          {field:'target',title:'目标',width:40},
		          {field:'source',title:'来源',width:40},
		          {field:'stockTime',title:'日期时间',width:60},
		          {field:'remark',title:'物料备注',width:80}
		          ]],
	});
	
	$('#id').combobox('loadData', materialEntry);
}

// 分类信息查询
function doSearchCategory(){
	var id = $('#id').combo('getValue');
	var name = $('#id').combo('getText');
	if (id != null && id==name){  // 只是手工输入(id将不准确)
		$('#category_dg').datagrid('load',{
			name: $('#id').combo('getText'),
			parentId: $('#parentId').combo('getValue')
		});
	}else{
		$('#category_dg').datagrid('load',{
			id: $('#id').combo('getValue'),
			//name: $('#id').combo('getText'),
			parentId: $('#parentId').combo('getValue')
		});
	}
}

//提交物料查询
function doSearchMaterial(){
		var id = $('#id').combo('getValue');
		var name = $('#id').combo('getText');
		if (id != null && id==name){ // 只是手工输入(id将不准确)
			$('#search_material_dg').datagrid('load',{
				//id: $('#id').combo('getValue'),
				name: $('#id').combo('getText'),
				code: $('#code').val(),
				size: $('#size').val(),
				categoryId: $('#categoryId').combo('getValue')
			});
		}else{
			$('#search_material_dg').datagrid('load',{
				id: $('#id').combo('getValue'),
				//name: $('#id').combo('getText'),
				code: $('#code').val(),
				size: $('#size').val(),
				categoryId: $('#categoryId').combo('getValue')
			});
		}
}

//提交出入库查询
function doSearchStockinout(){
	var materialId = $('#id').combo('getValue');
	var materialName = $('#id').combo('getText');
	if (materialId != null && materialId==materialName){ // id和name一样说明只是手工输入，而并不是选择的(id将不对)
		materialId = null;						
	}else {
		materialName = null;
	}
	$('#search_stockinout_dg').datagrid('load',{
		materialId: materialId,
		materialName: materialName,
		stockNo: $('#stockNo').val(),
		stockType: $('#stockType').combo('getValue'),
		stockTime: $('#stockTime').combo('getValue'),
		driverName: $('#driverName').val(),
		trunkNo: $('#trunkNo').val(),
		target: $('#target').val(),
		source: $('#source').val(),
	});
}

// TODO 出入库管理
function initStockDG(){
	$('#stock_dg').edatagrid({
		url: rootUri + 'stock/list.json',
		saveUrl: rootUri + 'saveStock',
		updateUrl: rootUri + 'saveStock',
		destroyUrl: rootUri + 'deleteStock',
		autoSave: false,
		checkOnSelect: false,
		pagination:true,//分页控件
		pageSize:20,
		pageList:[20,30,40,50],
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","操作失败！", "error");
		},
		onAdd: function(index,row){  // 添加新行时
			
		},
		onEdit: function(index,row){  // 编辑行时
			
		},
		onBeforeSave: function(index){  // 添加新行时
			
		},
		onSave: function(index, row){  // 保存后
			if(!row.isError){
				//$.messager.alert("提示","保存成功", "info");
				$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","保存失败！", "info");
				$('#stock_dg').edatagrid('cancelRow');
			}
		},
		destroyMsg:{
			norecord:{	// when no record is selected
				title:'警告',
				msg:'未选择任何条目.'
			},
			confirm:{	// when select a row
				title:'确认',
				msg:'删除后无法恢复，请三思！'
			}
		},
		onDestroy:function(index, row){  // 删除后
			if(!row.isError){
				$.messager.alert("提示","删除成功", "info");
				$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("错误","删除失败！", "error");
			}
		},
		columns:[[
		          //{field:'ck', checkbox:true},
		          {field:'stockNo',title:'单号',width:75, editor:{type:'textbox'}},
		          {field:'stockType',title:'出入库类型',width:65, 
		        	  formatter:function(value,row,index){
		        		  for(var i=0; i<stockTypeEntry.length;i++){
		        			  if (stockTypeEntry[i].id == value)
		        				  return stockTypeEntry[i].name;
		        		  }
		        		  return value;
		        	  },
		        	  editor:{
		        		  type:'combobox', 
		        		  options:{
		        			  valueField:'id',
		        			  textField:'name',
		        			  data:stockTypeEntry,
		        			  required:true
		        		  }
		        	  }
		          },
		          {field:'stockTime',title:'日期时间',width:100, editor:{type:'datetimebox', options:{required:true}}},
		          {field:'driverName',title:'司机',width:70, editor:{type:'textbox'}},
		          {field:'trunkNo',title:'车牌',width:70, editor:{type:'textbox'}},
		          {field:'source',title:'供货商/来源',width:80, editor:{type:'textbox', options:{required:true}}},
		          {field:'target',title:'客户/目的地',width:80, editor:{type:'textbox', options:{required:true}}},
		          {field:'remark',title:'备注',width:120, editor:{type:'textbox'}},
		          {field:'action',title:'操作',width:70,align:'center',
						formatter:function(value,row,index){
							var content = '';
							if (typeof(row.id) != 'undefined' && row.id != null && row.id != '' && row.id > 0){
								content = '<a style="color:blue;" href="#" onclick="goStock('+row.id +',\'' + row.typeName +'\',' + row.stockType + ');">详细记录</a>&nbsp;';
							}
							return content;
						}
					}
		          ]],
	});
}

//月度盘点
function initStocktakeDG(){
	$('#stocktake_dg').edatagrid({
		url: rootUri + 'stocktake/month.json',
		saveUrl: rootUri + 'saveStocktake',
		updateUrl: rootUri + 'saveStocktake',
		destroyUrl: rootUri + 'deleteStocktake',
		autoSave: false,
		checkOnSelect: false,
		onError: function(index,row){
			// alert(index + ', ' + row.msg);
			$.messager.alert("提示","操作失败！", "error");
		},
		onAdd: function(index,row){  // 添加新行时
			
		},
		onEdit: function(index,row){  // 编辑行时
			
		},
		onBeforeSave: function(index){  // 添加新行时
			
		},
		onSave: function(index, row){  // 保存后
			if(!row.isError){
				//$.messager.alert("提示","保存成功", "info");
				$('#p').panel('refresh');
			} else if(row.isError){
				$.messager.alert("提示","保存失败！", "info");
				$('#stocktake_dg').edatagrid('cancelRow');
			}
		},
		destroyMsg:{
			norecord:{	// when no record is selected
				title:'警告',
				msg:'未选择任何条目.'
			},
			confirm:{	// when select a row
				title:'确认',
				msg:'删除后无法恢复，请三思！'
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
		          //{field:'ck', checkbox:true},
		          {field:'name',title:'盘点标记',width:80, editor:{type:'textbox', options:{required:true}}},
		          {field:'stocktakeTime',title:'盘点日期',width:80, editor:{type:'datebox', options:{required:true}}},
		          {field:'stocktakePerson',title:'盘点人',width:80, editor:{type:'textbox', options:{required:true}}},
		          {field:'submitted',title:'是否已提交',width:60,
		        	  formatter:function(value){
		        		  var show = '否';
		        		  switch(value){
		        		  	case 0: show= '否';break;
		        		  	case 1: show= '是';break;
		        		  };
		        		  return show;
		        	  }
		          },
		          {field:'submitTime',title:'提交时间',width:80},
		          //{field:'auditot',title:'审核人',width:80},
		          {field:'remark',title:'备注',width:120, editor:{type:'textbox'}},
		          {field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index){
							var content = '';
							if (row.submitted == null || row.submitted == 0){  // 未提交的
								content = '<a style="color:blue;" href="#" onclick="goStocktake('+row.id+');">盘点</a>&nbsp;';
								content += '<a style="color:blue;" href="#" onclick="submitStocktake('+row.id+');">提交</a>';
							} else if (row.submitted == 1){  //已提交的
								content = '<a style="color:blue;" href="#" onclick="goStocktake('+row.id+');">查看盘点记录</a>';
							}
							return content;
						}
					}
		          ]],
	});
}

//盘点查询
function initSearchStocktakeDG(){
	$('#search_stocktake_dg').edatagrid({
		url: rootUri + 'stocktake/month.json',
		autoSave: false,
		checkOnSelect: false,
		columns:[[
		          //{field:'ck', checkbox:true},
		          {field:'name',title:'盘点标记',width:80/*, editor:{type:'textbox', options:{required:true}}*/},
		          {field:'stocktakeTime',title:'盘点日期',width:80/*, editor:{type:'datebox', options:{required:true}}*/},
		          {field:'stocktakePerson',title:'盘点人',width:80/*, editor:{type:'textbox', options:{required:true}}*/},
		          {field:'submitted',title:'是否已提交',width:60,
		        	  formatter:function(value){
		        		  var show = '否';
		        		  switch(value){
		        		  case 0: show= '否';break;
		        		  case 1: show= '是';break;
		        		  };
		        		  return show;
		        	  }
		          },
		          {field:'submitTime',title:'提交时间',width:80},
		          //{field:'auditot',title:'审核人',width:80},
		          {field:'remark',title:'备注',width:120/*, editor:{type:'textbox'}*/},
		          {field:'action',title:'操作',width:50,align:'center',
		        	  formatter:function(value,row,index){
		        		  var content = '';
		        		  if (row.submitted == null || row.submitted == 0){  // 未提交的
		        			  content = '<span>盘点中...</span>';
		        		  } else if (row.submitted == 1){  //已提交的
		        			  content = '<a style="color:blue;" href="#" onclick="goStocktake('+row.id+');">查看盘点记录</a>';
		        		  }
		        		  return content;
		        	  }
		          }
		          ]],
	});
}

// 打开盘点页面
function goStocktake(id){
	$('#p').panel('refresh', 'stocktake/taking?stocktakeId=' + id); // 配置url
}

// 打开出入库页面
function goStock(id, typeName, stockType){
	//$('#win').window('open');
	$('#win').window({
		title:typeName,
	    width:1000,
	    height:'100%',
	    modal:true,
	    href: rootUri + 'stock/items?stockId=' + id + '&stockType=' + stockType,
	    onLoad:initStockItemDG_win
	});
	// $('#p').panel('refresh', 'stocktake/taking?stocktakeId=' + id); // 配置url
}

function submitStocktake(id){
	$.messager.confirm('提示','提交后库存数据将以盘点数据为准，确定？',function(r){
	    if (r){ 
	    	$.ajax({
				url:'/warehouse/stocktake/submmit',
				dataType:'json',
				type:'POST',
				data : {stocktakeId:id},
				success:function(data){
					if(!data.isError){
						$.messager.alert('提示','保存成功','info');
						$('#p').panel('refresh');
					} 
					else {
						$.messager.alert('错误','保存失败！','error');
					}
				}
			});
	    }
	});
}


//盘点查询
function doSearchStocktake(){
	var id = $('#id').combo('getValue');  // 盘点标记
	var name = $('#id').combo('getText');
	if (id != null && id==name){  // 只是手工输入(id将不准确)
		$('#stocktake_dg').datagrid('load',{
			name: $('#id').combo('getText'),
			submitted: $('#submitted').combo('getValue')
		});
	}else{
		$('#stocktake_dg').datagrid('load',{
			id: $('#id').combo('getValue'),
			//name: $('#id').combo('getText'),
			submitted: $('#submitted').combo('getValue')
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
					{field:'unitPrice',title:'单价(元)',width:80,editor:{type:'numberbox', options:{precision:2}}},
					{field:'quantity',title:'数量',width:80,editor:{type:'numberbox', options:{precision:2}}},
					{field:'remark',title:'备注',width:140,editor:'text'},
					{field:'action',title:'操作',width:50,align:'center',
						formatter:function(value,row,index){
								var d = "<a href=\"#\" onclick=\"javascript:$('#stockItem_dg').edatagrid('deleteRow','"+index+"')\">删除</a>";
								return d;
						}
					}
				]],
	});
	
	var stockType = $('#stockType').val();
	if (stockType == 4 || stockType == 5 || stockType == 6){ // stock out
		$('#stockItem_dg').edatagrid('hideColumn', 'unitPrice');
	}
	
	$('#materialComboBox').combobox('loadData', materialEntry);
}

function initStockItemDG_win(){
	var stockId = $('#stockIdForItems').val();
	var stockType = $('#stockTypeForItems').val();
	
	var isHidden = false;
	var unitPriceOptions = {precision:2,required:true};
	if (stockType == 4 || stockType == 5 || stockType == 6){ // stock out
		isHidden = true;   //隐藏单价
		unitPriceOptions = {precision:2,required:false};//设置为非必填
	}
	
	$('#stockItem_dg_win').edatagrid({
		url: rootUri + 'stock/items.json?stockId=' + stockId,
		saveUrl: rootUri + 'saveStockItem?stockId=' + stockId + '&stockType=' + stockType,
		updateUrl: rootUri + 'saveStockItem?stockId=' + stockId + '&stockType=' + stockType,
		destroyUrl: rootUri + 'deleteStockItem?stockType=' + stockType,
		autoSave: false,
		onError: function(index,row){
			alert(index + ', ' + row.msg);
		},
		columns:[[
		          {field:'materialId',title:'物料名(单位)',width:100,
		        	  formatter:function(value){
		        		  if(typeof(value) != 'undefined'){
		        			  for(var i=0; i<materialEntry.length; i++){
		        				  if (materialEntry[i].id == value) 
		        					  return materialEntry[i].name + '('+ materialEntry[i].extraValue2 + ')';  // 物料名(单位)
		        			  }
		        		  }
		        		  return value;
		        	  },
		        	  editor:{
		        		  type:'combobox',
		        		  options:{
		        			  valueField:'id',
		        			  textField:'name',
		        			  data:materialEntry,
		        			  required:true
		        		  }
		        	  }
		          },
		          {field:'unitPrice',title:'单价(元)',width:60,hidden:isHidden, editor:{type:'numberbox', options:unitPriceOptions}},
		          {field:'quantity',title:'数量',width:80,editor:{type:'numberbox', options:{precision:2,required:true}}},
		          {field:'remark',title:'备注',width:140,editor:'text'},
		          {field:'action',title:'操作',width:50,align:'center',
		        	  formatter:function(value,row,index){
		        		  var d = '';
		        		  if (typeof(row.id) != 'undefined' && row.id != null && row.id != ''){
		        			  d = "<a href=\"#\" onclick=\"javascript:$('#stockItem_dg_win').edatagrid('destroyRow','"+index+"')\">删除</a>";
		        		  } else {
		        			  d = "<a href=\"#\" onclick=\"javascript:$('#stockItem_dg_win').edatagrid('deleteRow','"+index+"')\">删除</a>";
		        		  }
		        		  
		        		  return d;
		        	  }
		          }
		          ]],
	});
	
	//$('#materialComboBox').combobox('loadData', materialEntry);
}

function initStockItemImportDG_win(){
	//var stockId = $('#stockIdForImport').val();
	//var stockType = $('#stockTypeForImport').val();
	
	var isHidden = false;
	var unitPriceOptions = {precision:2,required:true};
	if (stockType == 4 || stockType == 5 || stockType == 6){ // stock out
		isHidden = true;   //隐藏单价
		unitPriceOptions = {precision:2,required:false};//设置为非必填
	}
	
	$('#stockItem_import_dg_win').edatagrid({
		/*url: rootUri + 'stock/items.json?stockId=' + stockId,
		saveUrl: rootUri + 'saveStockItem?stockId=' + stockId + '&stockType=' + stockType,
		updateUrl: rootUri + 'saveStockItem?stockId=' + stockId + '&stockType=' + stockType,
		destroyUrl: rootUri + 'deleteStockItem?stockType=' + stockType,*/
		autoSave: false,
		onError: function(index,row){
			alert(index + ', ' + row.msg);
		},
		columns:[[{field: 'stockDate', title:'日期', width:80, editor:{type:'dateBox',options:{required:true}}},
		          {field: 'stockNo', title:'单号', width:80, editor:{type:'text',options:{required:true}}},
		          {field:'materialId',hidden:true,editor:{type:'text',options:{required:true}}},
		          {field:'materialName',title:'物料名',width:140,
		        	  formatter:function(value, row, rowIndex){
		        		  if(typeof(value) != 'undefined'){
		        			  for(var i=0; i<materialEntry.length; i++){
		        				  if (materialEntry[i].name == value){
		        					  // 设置materialId
		        					  var ed = $('#stockItem_import_dg_win').datagrid('getEditor', {index:rowIndex,field:'materialId'});
		        					  $(ed).textbox('setValue', materialEntry[i].id);
		        					  return value;  // 物料名
		        				  } 
		        			  }
		        		  }
		        		  return '<span style="color:red;">未知物料:' + value + '</span>';
		        	  },
		        	  editor:{
		        		  type:'combobox',
		        		  options:{
		        			  valueField:'name',
		        			  textField:'name',
		        			  data:materialEntry,
		        			  required:true
		        		  }
		        	  }
		          },
		          {field:'remark',title:'物料说明',width:120,editor:'text'}, //跟物料写在同一个单元格、用空格隔开的备注
		          {field:'unitId',hidden:true,editor:{type:'text',options:{required:true}}},
		          {field:'unitName',title:'单位',width:80,
		        	  formatter:function(value,row, rowIndex){
		        		  if(typeof(value) != 'undefined'){
		        			  for(var i=0; i<unitEntry.length; i++){
		        				  if (unitEntry[i].name == value) {
		        					  // 设置unitId
		        					  var ed = $('#stockItem_import_dg_win').datagrid('getEditor', {index:rowIndex,field:'unitId'});
		        					  $(ed).textbox('setValue', unitEntry[i].id);
		        					  return value;  // 物料名
		        				  }
		        			  }
		        		  }
		        		  return '<span style="color:red;">未知单位:' + value + '</span>';
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
		          {field:'quantity',title:'数量',width:40,editor:{type:'numberbox', options:{precision:2,required:true}}},
		          {field:'unitPrice',title:'单价',width:60,hidden:isHidden, editor:{type:'numberbox', options:unitPriceOptions}},
		          {field:'stockRemark',title:'备注',width:140,editor:'text'},
		          {field:'action',title:'操作',width:50,align:'center',
		        	  formatter:function(value,row,index){
		        		  return '<a href=\"#\" onclick=\"javascript:$("#stockItem_import_dg_win").edatagrid("deleteRow",' +index+ ')\">删除</a>';
		        	  }
		          }
		          ]],
	});
	// 加载从excel读取出来的数据
	$('#stockItem_import_dg_win').edatagrid('loadData', dataFromExcel);
}

function initStockTakingItemDG(){
	$('#stockTakingItem_dg').edatagrid({
		autoSave: false,
		onError: function(index,row){
			alert(index + ', ' + row.msg);
		},
		columns:[[
		          {field:'materialId', hidden:true},
		          {field:'materialName',title:'物料',width:80},
		          {field:'unitId', hidden:true},
		          {field:'unitName',title:'单位',width:80},
		          {field:'quantity',title:'数量',width:80,editor:{type:'numberbox', options:{precision:2}}},
		          {field:'remark',title:'备注',width:140,editor:'text'},
		          {field:'action',title:'操作',width:50,align:'center',
		        	  formatter:function(value,row,index){
		        		  var d = "<a href=\"#\" onclick=\"javascript:$('#stockTakingItem_dg').edatagrid('deleteRow','"+index+"')\">删除</a>";
		        		  return d;
		        	  }
		          }
		          ]],
	});
	
}

/**
 * 出入库时添加物料到表单
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
 * 盘点时添加物料到表单
 */
function addTakingItemRow(){
	if (!$('#takingForm').form('validate'))
		return;
	var mId = $('#materialComboBox').combo('getValue');
	var mName = $('#materialComboBox').combo('getText');
	if (mId != null && mId == mName){
		$.messager.alert("错误","'" + mName + "' 物料不存在！","error");
		return;
	}
	var uId = $('#unitComboBox').combo('getValue');
	var uName = $('#unitComboBox').combo('getText');
/*	if (uId != null && uId == uName){
		$.messager.alert("错误","'" + uName + "' 单位不存在！","error");
		return;
	}
*/	
	$('#stockTakingItem_dg').edatagrid('addRow',{index:0,
		row:{
			materialId: mId,
			materialName: mName,
			unitId: uId,
			unitName: uName,
			balance:$('#balance').val(),
			quantity: $('#quantity').val(),
			remark: $('#itemRemark').val()
		}
	});
}

/**
 * 提交stock form
 */
function submitStockForm(){
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
			url:'/warehouse/stock/save',
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

//提交物料查询
function doSearchStocktakeItem(){
		var stId = $('#stocktakeId').val();
		var mId = $('#materialId').combo('getValue');
		var mName = $('#materialId').combo('getText');
		if (mId != null && mId != '' && mId != name){ 
			$('#stocktakeItem_dg').datagrid('load',{
				stocktakeId: stId,
				materialId: mId,
			});
		}else if(mId != null && mId != '' && mName == mId){ //id与name相同,说明只是手工输入(id将不准确)
			$.messager.alert('错误','请输入或选择正确物料！','error');
		}else{
			$('#stocktakeItem_dg').datagrid('load', {stocktakeId: stId});
		}
}

// 异步上传Excel文件到后台处理
var dataFromExcel;
function excelUpload(){
	var options = {
			title:'',  //显示在头部面板上的标题文本
			msg:'', //消息框的主体文本
			text:'读取数据中，请稍候', //显示在进度条里的文本
			interval: 300   //每次进度更新之间以毫秒为单位的时间长度
	};
	// 打开进度条
	$.messager.progress(options); 
    $.ajaxFileUpload
    (
        {
            url: 'stock/uploadExcel', //用于文件上传的服务器端请求地址
            fileElementId: 'excel', //文件上传域的ID
            secureuri: false, //是否需要安全协议，一般设置为false
            dataType: 'json', //返回值类型 一般设置为json
			// data:param,  // 表单数据,或者：data:{id: xxx, name: yyyy}
            success: function (data, status)  //服务器成功响应处理函数
            {
            	if (!data.isError){
            		dataFromExcel = data.obj;
            		importExcel("销售出库-" + data.msg);
            	}
            	else{
            		if (data.msg){
            			$.messager.alert("错误", data.msg , "error");
            		}
            		else{
            			$.messager.alert("错误", "读取数据时出错，请检查文件格式及内容是否正确！", "error");
            		}
            	}
            	// 关闭进度条
            	$.messager.progress('close');
            },
            error: function (data, status, e) //服务器响应失败处理函数
            {
            	$.messager.progress('close');
            	$.messager.alert("错误", "服务器响应" , "error");
            }
        }
    );
    
    return false;
}

function importExcel(title){
	$('#win').window({
		title:title,
	    width:1000,
	    height:'100%',
	    modal:true,
	    href: rootUri + 'stock/itemsImport',
	    onLoad:initStockItemImportDG_win    // 页面加载完后执行的函数
	});
}
	
// TODO 提交从excel导入的stock item
function submitImportStockItem(){
	// $('#stockItem_import_dg_win').edatagrid();
	if ($('#stockItem_import_dg_win').datagrid('getRows').length > 0){
		// 取数据
		var item;
		var itemArr = [];
		var rows = $('#stockItem_import_dg_win').datagrid('getRows');
		for(var i=0; i<rows.length;i++){
			//console.log('material id =' + rows[i].materialId);
			item = new Object();
			item.stockDate = rows[i].stockDate;
			item.stockNo = rows[i].stockNo;
			item.stockRemark = rows[i].stockRemark;
			item.materialId = rows[i].materialId;
			item.unitId = rows[i].unitId;
			item.quantity = rows[i].quantity;
			item.unitPrice = rows[i].unitPrice;
			item.remark = rows[i].remark;
			itemArr.push(item);
		}
		
		$.ajax({
			url:'/warehouse/stock/saveImportItems',
			dataType:'json',
			type:'POST',
			data : {itemsStr:JSON.stringify(itemArr)},
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

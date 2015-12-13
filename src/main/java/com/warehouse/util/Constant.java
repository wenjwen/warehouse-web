package com.warehouse.util;

import java.text.SimpleDateFormat;

public class Constant
{
	public static SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	public static String USER = "user";
	public static int DICT_TYPE_UNIT = 1;
	public static int DISABLED_FALSE = 0;
	public static int DISABLED_TRUE = 1;
	public static int STOCK_IN_BUY = 1; // 新购入库
	public static int STOCK_IN_RETURN = 2; // 归还入库
	public static int STOCK_IN_SALERETURN = 3; // 退货入库
	public static int STOCK_OUT_PRODUCE = 4;
	public static int STOCK_OUT_BORROW = 5;
	public static int STOCK_OUT_SALES = 6;
	
	
	public static String getStockTypeName(Integer stockType)
	{
		switch(stockType){
			case 1: return "新购入库";
			case 2: return "归还入库";
			case 3: return "退货入库";
			case 4: return "生产出库";
			case 5: return "借用出库";
			case 6: return "销售出库";
			default: return null ;
		}
	}
}

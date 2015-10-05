package com.warehouse.util;

import java.text.SimpleDateFormat;

public class Constant
{
	public static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	public static int DICT_TYPE_UNIT = 1;
	public static int DISABLED_FALSE = 0;
	public static int DISABLED_TRUE = 1;
	public static int STOCK_IN_BUY = 1; // 新购入库
	public static int STOCK_IN_RETURN = 2; // 归还入库
	public static int STOCK_IN_SALERETURN = 3; // 退货入库
	public static int STOCK_OUT_PRODUCE = 4;
	public static int STOCK_OUT_BORROW = 5;
	public static int STOCK_OUT_SALES = 6;
}

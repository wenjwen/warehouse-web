// 检查密码和重新输入密码是否相同
$.extend($.fn.validatebox.defaults.rules, {
    equals: {
		validator: function(value,param){
			return value == $(param[0]).val();
		},
		message: '两次输入密码不一样！'
    }
});


/**
 * 精确加法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accAdd(arg1,arg2)
{ 
    var r1,r2,m;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    return (accMul(arg1,m)+accMul(arg2,m))/m; 
}

/**
 * 精确乘法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accMul(arg1,arg2)
{ 
    var m=0,s1=arg1.toString(),s2=arg2.toString(); 
    try{m+=s1.split(".")[1].length}catch(e){} 
    try{m+=s2.split(".")[1].length}catch(e){} 
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m) 
}

/**
 * 精确减法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accSub(arg1,arg2)
{
  return accAdd(arg1,-arg2);
}

/**
 * 精确除法
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
function accDiv(arg1,arg2)
{ 
    return accMul(arg1,1/arg2);
}

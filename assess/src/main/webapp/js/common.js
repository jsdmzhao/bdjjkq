
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}


function CheckInputLengthAndNumber(that,vLength){
	var keycode = event.keyCode ;
	var realkey = String.fromCharCode(event.keyCode) ;
	var thatValue=that.value;
    keycode=keycode*1;

    //缺省均为event.returnValue=true;
	if ((keycode>=45)&&(keycode<=57)){
			if (thatValue.length>vLength)
			 {
				 event.returnValue = false;
			 }else{
				 event.returnValue = true;
			 }
	}else{
	    event.returnValue =false;
	}
}

//zhoujn
function mouse_over(obj){
  obj.style.backgroundColor="#ffff33"
}
//zhoujn
function mouse_out(obj){
  obj.style.backgroundColor=""
}

function clearFieldOptions(field){
  while(field.options.length>0)
  {
    field.options[0] = null;
  }
}

function addcomboxitem(field,value,caption){
  opt = document.createElement( "OPTION" );
  field.options.add(opt);
  opt.innerText =caption;
  opt.value = value;
}
function openwin(url,winname,scrollbars){
	var windowheight=screen.height;
	var windowwidth =screen.width;
	windowheight=(windowheight-600)/2;
	windowwidth=(windowwidth-800)/2;

	if(scrollbars==null)
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=590,top="+windowheight+",left="+windowwidth);
	else
	  subwinname = window.open(url,winname,"resizable=yes,scrollbars=yes,status=yes,toolbar=no,menubar=no,location=no,directories=no,width=790,height=590,top="+windowheight+",left="+windowwidth);

	return subwinname;
}
//excel 
function openwinexcel(url,winname){
  subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=1,height=1,top=10,left=10");
  return subwinname;
}
//print
function openwinprint(url,winname){
  subwinname = window.open(url,winname,"resizable=yes,scrollbars=no,status=no,toolbar=no,menubar=no,location=no,directories=no,width=1,height=1,top=10,left=10");
  return subwinname;
}


function checkNull(obj,vMc){
var v;
v=obj.value;
v=v.trim();

if (v.length==0)
{
	alert(vMc+"输入值为空！");
	return false;
}
obj.value=v;
return true;
}

//获取时间
function riqi(rq){
  gfPop.fPopCalendar(document.all[rq]);
  return false;
}
function riqi_fy(obj){
	  gfPop.fPopCalendar(obj);
	  return false;
}
//获取时间
function riqitime(rq){
  gfPoptime.fPopCalendar(document.all[rq]);
  return false;
}

//检测输入项中是否存在特殊字符
function checkSpecilCharAndNull(obj,vMc){
var s_tmp,s;

s_tmp=obj.value;
s_tmp=s_tmp.trim();

if (s_tmp.length==0)
{
	alert(vMc+"输入值为空！");
	return false;
}

//检测是否有特殊符号
re=new RegExp("[^0-9A-Za-z]");
if(s=s_tmp.match(re))
{
	alert("输入的"+vMc+"值中含有非法字符'"+s+"'请检查！");
	obj.focus();
    return false;
}
obj.value=s_tmp;
return true;
}


//检测输入的ip地址是否正确
function checkIp(obj,vMc){
var s_tmp,s;
var iValue;
s_tmp=obj.value;
s_tmp=s_tmp.trim();

if (s_tmp.length==0)
{
	alert(vMc+"输入值为空！");
	return false;
}

//检测是否有特殊符号
re=new RegExp("[^0-9]");
if(s=s_tmp.match(re))
{
	alert("输入的"+vMc+"值中含有非法字符'"+s+"'请检查！");
	obj.focus();
    return false;
}

iValue=parseInt(obj.value);
if (iValue>255)
{
	alert("输入的"+vMc+"值"+iValue+"大于255，请检查！");
	obj.focus();
    return false;
}
obj.value=s_tmp;
return true;
}

//判断日期类型是否合法 zhoujn 20060323
function isDate(i_field,thedate)
{
  if(thedate.length==0||thedate==null){
	alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd！");
	return 0;
  }
  if (!(thedate.length==8 || thedate.length==10))
   {    alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd！");
     return 0;
   }
  if (thedate.length==8)
  {
  	thedate=thedate.substr(0,4)+"-"+thedate.substr(4,2)+"-"+thedate.substr(6,2);
  }

    var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
    var r = thedate.match(reg);

     if (!(r==null))
    {

    var d= new Date(r[1],r[3]-1,r[4]);
    var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
    var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)
    if (newStr==newDate)
         {
          return 1;
         }
     alert("'"+i_field+"'日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！");
     return 0;
    }
	alert("'"+i_field+"'日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！");
    return 0;
}

//-------------------------------
//  函数名：DateAddMonth(strDate,iMonths)
//  功能介绍：获得日期加上iMonths月数后的日期
//  参数说明：strDate 日期
//  返回值  ：无返回值
//-------------------------------
function DateAddMonth(strDate,iMonths){
   var thisYear = parseFloat(strDate.substring(0,4));
   var thisMonth = parseFloat(strDate.substring(5,7));
   var thisDate = parseFloat(strDate.substring(8,10));
   var d =thisYear *12 + thisMonth + parseFloat(iMonths);

   var newMonth = d % 12;
   if (newMonth==0) {
   	newMonth=12;
   }
   var newYear = (d - newMonth) /12;
   var newDate = thisDate;
   var iMonthLastDate=getMonthLastDate(newYear,newMonth)
   if (newDate>iMonthLastDate) newDate=iMonthLastDate;
   var newDay="";

   newDay += newYear;
   if (newMonth<10) {
   	newDay +="-" + "0" + newMonth;
   }else{
   	newDay +="-" + newMonth;
   }

   if (newDate<10) {
   	newDay +="-" + "0" + newDate;
   }else{
   	newDay +="-" + newDate;
   }
   return(newDay);                                // 返回日期。
}

function getMonthLastDate(iYear,iMonth){
	var dateArray= new Array(31,28,31,30,31,30,31,31,30,31,30,31);
	var days=dateArray[iMonth-1];
	if ((((iYear % 4 == 0) && (iYear % 100 != 0)) || (iYear % 400 == 0)) && iMonth==2){
		days=29;
	}
	return days;
}

//校验传入的文本框文本是否为数值类型
function checkNumber(obj,vMc){
var v;
v=obj.value;
v=v.trim();
re=new RegExp("[^0-9]");
if(s=v.match(re))
{
alert(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
return false;
}
obj.value=v;
return true;
}

//校验传入的文本框文本是否为数值类型长度是否符合要求
function checkNumberAndLength(obj,vMc,vLength){
var v;
v=obj.value;
v=v.trim();
if (v.length!=vLength)
{
	alert("输入的"+vMc+"的长度不是"+vLength+"位！");
	 obj.focus();
	return false;
}
re=new RegExp("[^0-9]");
if(s=v.match(re))
{
alert(vMc+"输入的值中含有非法字符'"+s+"'请检查！");
obj.focus();
return false;
}
obj.value=v;
return true;
}

//增加一条违章记录
function add_wzjl()
{   
var i,k;
var x,j,m,n,y,a;
var div_content="";
	
	i=document.all["i_no"].value;
    m=document.all["m_no"].value;
	i=parseInt(i);
	m=parseInt(m);
	if(document.all["wfxw"+m].value.length<1)
	{alert("当前违法行为不能为空，请输入后再添加！");
	return false;
	}
    if(m>0)
	{    x="wfjl"+m;
	     document.all[x].style.display="none";
	     y="wfjl"+i;
		 document.all[y].style.display="block";
		 m=i;
		 document.all["m_no"].value=m;
	 }
	if(i<5)
    { i=i+1;
	   m=i;
	  document.all["i_no"].value=i;
      document.all["m_no"].value=m;
     x="wfjl"+i;
	    if(1<i&&i<6)
	    {  j=i-1;
			y="wfjl"+j;
	     document.all[y].style.display="none";
		}
	 document.all[x].style.display="block";
	 y="wfxw"+i;
	 document.all[y].focus();
   div_content="<a class=class3> 违法行为第"+m+"条  当前共"+i+"条违法行为</a>"
   document.all["member_no"].innerHTML=div_content;
	}
}
//删除一条违章记录
function del_wzjl()
{   
var i,k;
var x,j,m,n,y,a;
var div_content="";
	i=document.all["i_no"].value;
    m=document.all["m_no"].value;
	i=parseInt(i);
	m=parseInt(m);
	if(i>1)
	{
		if(document.all["wfxw"+m].value=="")
		{
            if(m==i)
		{
		 document.all["wfxw"+i].value="";
         document.all["jfs"+i].value="";
         document.all["fkje"+i].value="";
	     document.all["qzcs"+i].value="";
	     document.all["fltw"+i].value="";
	     document.all["wfnr"+i].value="";
	     document.all["wfjl"+i].style.display="none";
		 i=i-1;
		 m=m-1;
		 document.all["wfjl"+i].style.display="block";
		 div_content="<a class=class3> 违法行为第"+m+"条  当前共"+i+"条违法行为</a>"
         document.all["member_no"].innerHTML=div_content;
		 document.all["i_no"].value=i;
         document.all["m_no"].value=m;
		 }
        if(m!=i)
		{
	       for(n=0;n<(i-m);n++)
		   {
	       y=m+n;
		   a=y+1;
	       document.all["wfxw"+y].value=document.all["wfxw"+a].value;
	       document.all["jfs"+y].value=document.all["jfs"+a].value;
	       document.all["fkje"+y].value=document.all["fkje"+a].value;
	       document.all["qzcs"+y].value=document.all["qzcs"+a].value;
	       document.all["fltw"+y].value=document.all["fltw"+a].value;
	       document.all["wfnr"+y].value=document.all["wfnr"+a].value;
		   document.all["jlts"+y].value=document.all["jlts"+a].value;
		   document.all["fkje_min"+y].value=document.all["fkje_min"+a].value;
		   document.all["fkje_max"+y].value=document.all["fkje_max"+a].value;
		   document.all["qzcs_min"+y].value=document.all["qzcs_min"+a].value;
		   document.all["qzcs_max"+y].value=document.all["qzcs_max"+a].value;
		   document.all["jlts_min"+y].value=document.all["jlts_min"+a].value;
		   document.all["jlts_max"+y].value=document.all["jlts_max"+a].value;
	       }
	       document.all["wfxw"+i].value="";
           document.all["jfs"+i].value="";
           document.all["fkje"+i].value="";
	       document.all["qzcs"+i].value="";
	       document.all["fltw"+i].value="";
	       document.all["wfnr"+i].value="";
	       document.all["wfjl"+i].style.display="none";
           i=i-1;
		   div_content="<a class=class3> 违法行为第"+m+"条  当前共"+i+"条违法行为</a>"
           document.all["member_no"].innerHTML=div_content;
		   document.all["i_no"].value=i;
           document.all["m_no"].value=m;
		 }

		}

   else
		{
    if(confirm('违法行为：'+document.all["wfxw"+m].value+';\t违法分值：'+document.all["jfs"+m].value+'分;\n罚款金额：'+document.all["fkje"+m].value+'元;\t扣证天数：'+document.all["qzcs"+m].value+'天;\n法律依据：'+document.all["fltw"+m].value+';\n违法内容：'+document.all["wfnr"+m].value+';\n\n\t\t确定要删除该条违法行为吗?'))
	 {
		if(m==i)
		{
		 document.all["wfxw"+i].value="";
         document.all["jfs"+i].value="";
         document.all["fkje"+i].value="";
	     document.all["qzcs"+i].value="";
	     document.all["fltw"+i].value="";
	     document.all["wfnr"+i].value="";
	     document.all["wfjl"+i].style.display="none";
		 i=i-1;
		 m=m-1;
		 document.all["wfjl"+i].style.display="block";
		 div_content="<a class=class3> 违法行为第"+m+"条  当前共"+i+"条违法行为</a>"
         document.all["member_no"].innerHTML=div_content;
		 document.all["i_no"].value=i;
         document.all["m_no"].value=m;
		 }
        if(m!=i)
		{
	       for(n=0;n<(i-m);n++)
		   {
	       y=m+n;
		   a=y+1;
	       document.all["wfxw"+y].value=document.all["wfxw"+a].value;
	       document.all["jfs"+y].value=document.all["jfs"+a].value;
	       document.all["fkje"+y].value=document.all["fkje"+a].value;
	       document.all["qzcs"+y].value=document.all["qzcs"+a].value;
	       document.all["fltw"+y].value=document.all["fltw"+a].value;
	       document.all["wfnr"+y].value=document.all["wfnr"+a].value;
		   document.all["jlts"+y].value=document.all["jlts"+a].value;
		   document.all["fkje_min"+y].value=document.all["fkje_min"+a].value;
		   document.all["fkje_max"+y].value=document.all["fkje_max"+a].value;
		   document.all["qzcs_min"+y].value=document.all["qzcs_min"+a].value;
		   document.all["qzcs_max"+y].value=document.all["qzcs_max"+a].value;
		   document.all["jlts_min"+y].value=document.all["jlts_min"+a].value;
		   document.all["jlts_max"+y].value=document.all["jlts_max"+a].value;
	       }
	       document.all["wfxw"+i].value="";
           document.all["jfs"+i].value="";
           document.all["fkje"+i].value="";
	       document.all["qzcs"+i].value="";
	       document.all["fltw"+i].value="";
	       document.all["wfnr"+i].value="";
	       document.all["wfjl"+i].style.display="none";
           i=i-1;
		   div_content="<a class=class3> 违法行为第"+m+"条  当前共"+i+"条违法行为</a>"
           document.all["member_no"].innerHTML=div_content;
		   document.all["i_no"].value=i;
           document.all["m_no"].value=m;
		 }

	  }
	 }
	}
}
//下一条

function goto_next()
{   
var i,k;
var x,j,m,n,y,a;
var div_content="";	
	i=document.all["i_no"].value;
    m=document.all["m_no"].value;
	i=parseInt(i);
	m=parseInt(m);
	if(m<i)
    { m=m+1;
     x="wfjl"+m;
	    if(1<m&&m<6)
	    {  j=m-1;
			y="wfjl"+j;
	     document.all[y].style.display="none";
		}
	 document.all[x].style.display="block";
	 div_content="<a class=class3> 违法行为第"+m+"条  当前共"+i+"条违法行为</a>"
     document.all["member_no"].innerHTML=div_content;
	 document.all["m_no"].value=m;
	}
}
//上一条

function goto_prev()
{   
var i,k;
var x,j,m,n,y,a;
var div_content="";
	i=document.all["i_no"].value;
    m=document.all["m_no"].value;
	i=parseInt(i);
	m=parseInt(m);
	if(m>1)
	{ x="wfjl"+m;
	document.all[x].style.display="none";
	 if(m>1)
	    { j=m-1;
		 y="wfjl"+j;
	     document.all[y].style.display="block";
		}
	m=m-1;
	div_content="<a class=class3> 违法行为第"+m+"条  当前共"+i+"条违法行为</a>"
    document.all["member_no"].innerHTML=div_content;
	document.all["m_no"].value=m;
    }

}

//判断日期时间是否合法
function isDateTime(i_field,obj){
var thedate;
thedate=obj.value;
 if(thedate.length==0||thedate==null){
 	alert("请输入"+i_field);
 	obj.focus();
 	return 0;
 }
 if(thedate.length==16)
 {
   thedate=thedate+":00";
 }
  if (!(thedate.length==19))
   {    alert("'"+i_field+"'格式不对,请输入正确的格式：yyyy-mm-dd hh24:mi！");
   	obj.focus();
     return 0;
   }
  dateRegexp = new RegExp("^(\\d{4})[-](\\d{2})[-](\\d{2})[ ](\\d{2})[:](\\d{2})[:](\\d{2})$");
  var matched = dateRegexp.exec(thedate);
  if(matched != null) {
  if (!isTrueDateTime(matched[3], matched[2], matched[1],matched[4], matched[5], matched[6]))
  {
    alert("'"+i_field+"'格式不对，请输入正确的格式：yyyy-mm-dd hh24:mi！");
    obj.focus();
    return 0;
  }
  else{
    return 1;
  }
}
return 0;
}

function isTrueDateTime(day, month, year,hour,min,second) {
      if (month < 1 || month > 12) {
          return false;
          }
      if (day < 1 || day > 31) {
          return false;
          }
      if ((month == 4 || month == 6 || month == 9 || month == 11) &&
          (day == 31)) {
          return false;
          }
      if (month == 2) {
          var leap = (year % 4 == 0 &&
         (year % 100 != 0 || year % 400 == 0));
          if (day>29 || (day == 29 && !leap)) {
          return false;
          }
         }
      if(hour<0||hour>23){
         return false;
         }
      if(min<0||min>59){
         return false;
         }
      if(second<0||second>59){
         return false;
         }
      return true;
}

//校验违法时间
function checkWfsj(vNowTime,vBTime){
	if(isDateTime("违法时间",document.all["wfsj"])==0){
		return 0;
	}else{
		if(document.all["wfsj"].value>vNowTime){
			alert("违法时间不能大于系统当前时间！");
			document.all["wfsj"].focus();
			return 0;
		}
		if(document.all["wfsj"].value<vBTime){
			alert("该违法记录已超过录入期限，下次请及时录入！");
			document.all["wfsj"].focus();
			return 0;
		}
	}
return 1;
}

//校验修改时违法时间
function checkWfsj_XG(vNowTime,vXgts,vXgqx){
	if(isDateTime("违法时间",document.all["wfsj"])==0){
		return 0;
	}else{
		if(document.all["wfsj"].value>vNowTime){
			alert("违法时间不能大于系统当前时间！");
			document.all["wfsj"].focus();
			return 0;
		}
		if(parseInt(vXgts)>parseInt(vXgqx)){
			alert("该违法记录已超过修改期限，下次请及时修改！");
			return 0;
		}
	}
return 1;
}

//检测身份证号
function sfzCheck(hm)
{

      var w=new Array();
      var ll_sum;
      var ll_i;
      var ls_check;

      w[0]=7;
      w[1]=9;
      w[2]=10;
      w[3]=5;
      w[4]=8;
      w[5]=4;
      w[6]=2;
      w[7]=1;
      w[8]=6;
      w[9]=3;
      w[10]=7;
      w[11]=9;
      w[12]=10;
      w[13]=5;
      w[14]=8;
      w[15]=4;
      w[16]=2;
     ll_sum=0;

     for (ll_i=0;ll_i<=16;ll_i++)
     {   //alert("ll_i:"+ll_i+" "+hm.substr(ll_i,1)+"w[ll_i]:"+w[ll_i]+"  ll_sum:"+ll_sum);
        ll_sum=ll_sum+(hm.substr(ll_i,1)-0)*w[ll_i];

     }
     ll_sum=ll_sum % 11;


     switch (ll_sum)
      {
        case 0 :
            ls_check="1";
            break;
        case 1 :
            ls_check="0";
            break;
        case 2 :
            ls_check="X";
            break;
        case 3 :
            ls_check="9";
            break;
        case 4 :
            ls_check="8";
            break;
        case 5 :
            ls_check="7";
            break;
        case 6 :
            ls_check="6";
            break;
        case 7 :
            ls_check="5";
            break;
        case 8 :
            ls_check="4";
            break;
        case 9 :
            ls_check="3";
            break;
        case 10 :
            ls_check="2";
            break;
      }
      if (hm.substr(17,1) != ls_check)
      {
            alert("证件校验错误！------ 末位应该:"+ls_check+" 实际:"+hm.substr(17,1));
            return 0;
     }
return 1;
}
//身份证15位转18位
function  id15to18(zjhm) {
    zjhm = zjhm.substr(0, 6) + "19" + zjhm.substr(6);
	zjhm=zjhm+getmwsfzh(zjhm);
    return zjhm;
  }


function getmwsfzh(hm)
{

      var w=new Array();
      var ll_sum;
      var ll_i;
      var ls_check;

      w[0]=7;
      w[1]=9;
      w[2]=10;
      w[3]=5;
      w[4]=8;
      w[5]=4;
      w[6]=2;
      w[7]=1;
      w[8]=6;
      w[9]=3;
      w[10]=7;
      w[11]=9;
      w[12]=10;
      w[13]=5;
      w[14]=8;
      w[15]=4;
      w[16]=2;
     ll_sum=0;

     for (ll_i=0;ll_i<=16;ll_i++)
     {
        ll_sum=ll_sum+(hm.substr(ll_i,1)-0)*w[ll_i];

     }
     ll_sum=ll_sum % 11;


     switch (ll_sum)
      {
        case 0 :
            ls_check="1";
            break;
        case 1 :
            ls_check="0";
            break;
        case 2 :
            ls_check="X";
            break;
        case 3 :
            ls_check="9";
            break;
        case 4 :
            ls_check="8";
            break;
        case 5 :
            ls_check="7";
            break;
        case 6 :
            ls_check="6";
            break;
        case 7 :
            ls_check="5";
            break;
        case 8 :
            ls_check="4";
            break;
        case 9 :
            ls_check="3";
            break;
        case 10 :
            ls_check="2";
            break;
      }
	return ls_check;
}

//检测驾驶证号是否正确
function check_jszh(zjhm)
{
  var birthday="";
  var zjhm1="";
  var zjhm2="";
  var s="";
  var zjmc="";
  var zjhmt="";
  
  zjhmt=zjhm.substr(0,1);
  if(zjhmt=="C"||zjhmt=="D"||zjhmt=="E"||zjhmt=="F"||zjhmt=="G"){
	  zjmc=zjhmt;
  }else{
	  zjmc="A";
  }
  
  if(zjmc=="A")   //身份证号码
   {
       if(!(zjhm.length==15 || zjhm.length==18) )
	     {
	       alert("证件长度不对,请检查！") ;
           return 0;
	     }
        zjhm1=zjhm;
        if (zjhm.length==18)
            {
                zjhm1=zjhm.substr(0,17)	;
                zjhm2=zjhm.substr(17,1);
            }
        re=new RegExp("[^0-9]");
	 	if(s=zjhm1.match(re))
	    	{
	        alert("输入的值中含有非法字符'"+s+"'请检查！");
	        return 0;
             }
        //取出生日期
        if(zjhm.length==15 )
            {
               birthday="19"+zjhm.substr(6,6);
            }
         else
            {
			   re=new RegExp("[^0-9A-Z]");
               if(s=zjhm2.match(re))     //18位身份证对末位要求数字或字符
               {
                   alert("输入的值中含有非法字符'"+s+"'请检查！");
                   return 0;
                }
                birthday=zjhm.substr(6,8);
            }
           birthday=birthday.substr(0,4)+"-"+birthday.substr(4,2)+"-"+birthday.substr(6,2)
          //alert("birthday"+birthday)

          if(newisDate("证件号码",birthday)==0)  //检查日期的合法性
          {
             return 0;
          }
		 
         if(zjhm.length==18 )
         {
          	return(sfzCheck(zjhm));  //对18位长的身份证进行末位校验
         }
       }
   return 1;
   }


//检测日期是否正确
function newisDate(i_field,thedate)
{if (thedate.length==8)
  {
  	thedate=thedate.substr(0,4)+"-"+thedate.substr(4,2)+"-"+thedate.substr(6,2);
  }
    var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
    var r = thedate.match(reg);

     if (r==null)
    {
       alert("'"+i_field+"' 含非法字符！");
       return 0;

    }
    var d= new Date(r[1],r[3]-1,r[4]);
    var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
    var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)
    //alert("----------r:"+r+" d:"+d+" newStr:"+newStr+" newDate:"+newDate);
    if (newStr==newDate)
         {
          return 1;
         }
     alert("'"+i_field+"'日期格式不对！");
     return 0;
}

//获取发证机关
function get_fzjg(vBdfzjg){
document.all["fzjg"].value=document.all["pro"].value+document.all["az"].value;
if(vBdfzjg==document.all["fzjg"].value){
	document.all["hqydinfo"].disabled=true;
}else{
	document.all["hqydinfo"].disabled=false;
}
}

//获取发证机关
function get_fzjg_fxc(vBdfzjg){
document.all["fzjg"].value=document.all["pro"].value+document.all["az"].value;
document.all["hphm"].value=document.all["fzjg"].value;
if(vBdfzjg==document.all["fzjg"].value){
	document.all["hqydinfo"].disabled=true;
}else{
	document.all["hqydinfo"].disabled=false;
}
}

function show_it()
{
	if (jylrmain.lrlx[0].checked==true)
		{
		show_jsr();
		document.all["hphm"].value=strBdfzjg;
		document.all["jdcsyr"].value="";
		document.all["hphm"].readOnly=false;
		document.all["jdcsyr"].readOnly=false;
		document.all["hpzl"].readOnly=false;
		}
	else
		{show_fjsr();
		document.all["hphm"].value="无";
		document.all["jdcsyr"].value="无";
		document.all["hphm"].readOnly=true;
		document.all["jdcsyr"].readOnly=true;
		document.all["hpzl"].readOnly=true;
		}
}


function show_it_fxc()
{
	if (fxclrmain.lrlx[0].checked==true)
		{
		show_jsr();
		}
	else
		{show_fjsr();
		}
}

function show_jsr()
{
	document.all["fjsrlr"].style.display="none";
	document.all["jsrlr"].style.display="block";
}

function show_fjsr()
{
	document.all["jsrlr"].style.display="none";
    document.all["fjsrlr"].style.display="block";
}

//校验罚款金额
function checkFjke(obj,iNo){
var fk_min=document.all["fkje_min"+iNo].value;
var fk_max=document.all["fkje_max"+iNo].value;

if(checkNumber(obj,"罚款金额")==false){
	//obj.value=fk_max;
	return 0;
}
if((obj.value-fk_max)>0 || (obj.value-fk_min)<0)
{
	alert("[违法行为"+iNo+"]中的[罚款金额]必须在 " + fk_min + " 和 " + fk_max +" 之间");
	return 0;
	//obj.value=fk_max;
}
}

//校验罚款金额
function checkFjke_fxc(obj){
var fk_min=document.all["fkje_min"].value;
var fk_max=document.all["fkje_max"].value;

if(checkNumber(obj,"罚款金额")==false){
	//obj.value=fk_max;
	return 0;
}
if((obj.value-fk_max)>0 || (obj.value-fk_min)<0)
{
	alert("[违法行为]中的[罚款金额]必须在 " + fk_min + " 和 " + fk_max +" 之间");
	return 0;
	//obj.value=fk_max;
}
}

//校验暂扣月数
function checkQzcs(obj,iNo){
var qzcs_min=document.all["qzcs_min"+iNo].value;
var qzcs_max=document.all["qzcs_max"+iNo].value;

if(checkNumber(obj,"暂扣月数")==false){
	//obj.value=qzcs_max;
	return 0;
}
if((obj.value-qzcs_max)>0 || (obj.value-qzcs_min)<0)
{
	alert("[违法行为"+iNo+"]中的[暂扣月数]必须在 " + qzcs_min + " 和 " + qzcs_max +" 之间");
	return 0;
	//obj.value=qzcs_max;
}
}

//校验拘留天数
function checkJlts(obj,iNo){
var jlts_min=document.all["jlts_min"+iNo].value;
var jlts_max=document.all["jlts_max"+iNo].value;

if(checkNumber(obj,"拘留天数")==false){
	//obj.value=jlts_max;
	return 0;
}
if((obj.value-jlts_max)>0 || (obj.value-jlts_min)<0)
{
	alert("[违法行为"+iNo+"]中的[拘留天数]必须在 " + jlts_min + " 和 " + jlts_max +" 之间");
	return 0;
	//obj.value=jlts_max;
}
}

//检测违法地址
function checkWfdzInfo(){
var tmpkm;
var tmpms;

tmpkm="";
tmpms="";

if(checkNumberAndLength(document.all["wzdd"],"道路代码",5)==false){
	return 0;
}


if(checkNull(document.all["wfdz"],"违法地址")==false){
	return 0;
}

if(strwfdz==""){
	alert("违法地址输入不正确或不存在该道路代码！");
	document.all["wzdd"].focus();
	return 0;
}

if(document.all["wzdd"].value.substr(0,1)>4){
	if(document.all["km_num"].value.length>=3){
		if(checkNumber(document.all["km_num"],"路口号")==false){
			document.all["km_num"].focus();
			return 0;
		}
	}else{
		document.all["km_num"].focus();
		alert("请输入路口号！");
		return 0;
	}
}else{
	if(document.all["km_num"].value.length>0){
		if(checkNumber(document.all["km_num"],"公里数")==false){
			document.all["km_num"].focus();
			return 0;
		}
	}
}

	if(document.all["ms_num"].value.length>0){
		if(checkNumber(document.all["ms_num"],"米数")==false){
			document.all["ms_num"].focus();
			return 0;
		}
	}

for(var i = document.all["km_num"].value.length; i <4; i++){
	tmpkm=tmpkm+"0";
}

for(var j = document.all["ms_num"].value.length; j <3; j++){
	tmpms=tmpms+"0";
}

//生成违法地点
if(document.all["wzdd"].value.substr(0,1)>4){
	document.all["wfdd"].value=	document.all["wzdd"].value+document.all["km_num"].value+tmpkm+tmpms+document.all["ms_num"].value;
}else{
	document.all["wfdd"].value=	document.all["wzdd"].value+tmpkm+document.all["km_num"].value+tmpms+document.all["ms_num"].value;
}
return true;
}

//检测当事人信息
function checkDsr(vLb){
if(vLb=="1"){
//机动车驾驶人
	if(document.all["fzjg"].value.length!=2){
		alert("发证机关不能为空！");
		return 0;
	}
	
	document.all["jszh"].value=document.all["jszh"].value.trim();
	if(checkJszh(document.all["jszh"],"A")==0){
		return 0;
	}
	
	document.all["dabh"].value=document.all["dabh"].value.trim();
	if(document.all["dabh"].value.length==0){
		alert("档案编号不能为空！")
		document.all["dabh"].focus();
		return 0;
	}
	if(document.all["dabh"].value!="无"){
		if(checkNumber(document.all["dabh"],"档案编号")==false){
			document.all["dabh"].focus();
			return 0;
		}
		if(document.all["dabh"].value.length!=10&&document.all["dabh"].value.length!=12){
			document.all["dabh"].focus();
			alert("档案编号不为10位或12位！");
			return 0;
		}
	}
	
	if(document.all["zjcx"].value.length<1)
	{	
		alert("准驾车型不能为空！");
		document.all["zjcx"].focus();
		return 0;
	}
	
	document.all["dsr"].value=document.all["dsr"].value.trim();
	if(document.all["dsr"].value.length<2)
	{	
		alert("当事人输入不正确！");
		document.all["dsr"].focus();
		return 0;
	}

	document.all["zsxzqh"].value=document.all["zsxzqh"].value.trim();
	if(document.all["zsxzqh"].value.length==0)
	{
		document.all["zsxzqh"].value="000000";
	}else{
		if(checkNumberAndLength(document.all["zsxzqh"],"住址辖区","6")==false){
			document.all["zsxzqh"].focus();
			return 0;
		}
	}
	
}else{
    //非机动车或行人
	document.all["jszh_f"].value=document.all["jszh_f"].value.trim();
	if(checkJszh(document.all["jszh_f"],"A")==0){
		return 0;
	}
	document.all["dsr_f"].value=document.all["dsr_f"].value.trim();
	if(document.all["dsr_f"].value.length<2)
	{	
		alert("当事人输入不正确！");
		document.all["dsr_f"].focus();
		return 0;
	}
	
	document.all["zsxzqh_f"].value=document.all["zsxzqh_f"].value.trim();
	if(document.all["zsxzqh_f"].value.length==0)
	{
		document.all["zsxzqh_f"].value="000000";
	}else{
		if(checkNumberAndLength(document.all["zsxzqh_f"],"住址辖区","6")==false){
			document.all["zsxzqh_f"].focus();
			return 0;
		}
	}
}
}

//非机动车驾驶人的信息传给驾驶人信息
function copyValutToJsr(){
	document.all["jszh"].value=document.all["jszh_f"].value;
	document.all["zjcx"].value="无";
	document.all["dabh"].value="无";
	document.all["dsr"].value=document.all["dsr_f"].value;
	document.all["dh"].value=document.all["dh_f"].value;
	document.all["lxfs"].value=document.all["lxfs_f"].value;
	document.all["zsxzqh"].value=document.all["zsxzqh_f"].value;
	document.all["zsxxdz"].value=document.all["zsxxdz_f"].value;
}


//检测号牌号码
function checkHphmObj(obj){
var str="";
var s="";
	obj.value=obj.value.trim();
	if(obj.value!="无"){
		if(checkNull(obj,"号牌号码")==false){
			obj.focus();
			return 0;
		}
		if(obj.value.length<5){
			alert("号牌号码输入不正确！");
			obj.focus();
			return 0;
		}
		if(obj.value.length>10){
			alert("号牌号码输入不正确！");
			obj.focus();
			return 0;
		}
		
		str=obj.value;
		if(str.charCodeAt(0)>255){
			re=new RegExp("[^0-9A-Za-z]");
	 		if(s=str.substring(1,obj.value.length-1).match(re))
	    	{
	        alert("号牌号码输入的值中含有非法字符'"+s+"'请检查！");
	        obj.focus();
	        return 0;
             }
		}else{
			alert("号牌号码第一位必须为中文！");
			obj.focus();
			return 0;
		}
	}
}

//获取强制措施类型
function getQzcslx(){
var yhzs;
yhzs="";
if(document.all.cqzcslx)
	{
		if (document.all.cqzcslx.type=="checkbox")
		{
			//单个
			if (document.all["cqzcslx"].checked)
			{
				yhzs=document.all["cqzcslx"].value;
			}else{
				yhzs="";
			}
		}else{
			 var yhz_length=document.all["cqzcslx"].length;
			 for(var i=0;i<yhz_length;i++)
			 {
				 if(document.all["cqzcslx"].item(i).checked==true)
				 {
				 yhzs=yhzs+document.all["cqzcslx"].item(i).value;
				 }
			 }
		}
	}
document.all["qzcslx"].value=yhzs;
if(document.all["qzcslx"].value.length>4){
	alert("强制措施类型不能大于四项！");
	return 0;
}
return 1;
}

//获取撤销项目
function getCxxm(){
var yhzs;
yhzs="";
if(document.all.ccxxm)
	{
		if (document.all.ccxxm.type=="checkbox")
		{
			//单个
			if (document.all["ccxxm"].checked)
			{
				yhzs=document.all["ccxxm"].value;
			}else{
				yhzs="";
			}
		}else{
			 var yhz_length=document.all["ccxxm"].length;
			 for(var i=0;i<yhz_length;i++)
			 {
				 if(document.all["ccxxm"].item(i).checked==true)
				 {
				 yhzs=yhzs+document.all["ccxxm"].item(i).value;
				 }
			 }
		}
	}
document.all["cxxm"].value=yhzs;
return 1;
}


//扣留项目
function getCKlxm(){
var yhzs;
yhzs="";
if(document.all.cklxm)
	{
		if (document.all.cklxm.type=="checkbox")
		{
			//单个
			if (document.all["cklxm"].checked)
			{
				yhzs=document.all["cklxm"].value;
			}else{
				yhzs="";
			}
		}else{
			 var yhz_length=document.all["cklxm"].length;
			 for(var i=0;i<yhz_length;i++)
			 {
				 if(document.all["cklxm"].item(i).checked==true)
				 {
				 yhzs=yhzs+document.all["cklxm"].item(i).value;
				 }
			 }
		}
	}
document.all["klxm"].value=yhzs;
return 1;
}

//扣留收缴
function getCSjxm(){
var yhzs;
yhzs="";
if(document.all.csjxm)
	{
		if (document.all.csjxm.type=="checkbox")
		{
			//单个
			if (document.all["csjxm"].checked)
			{
				yhzs=document.all["csjxm"].value;
			}else{
				yhzs="";
			}
		}else{
			 var yhz_length=document.all["csjxm"].length;
			 for(var i=0;i<yhz_length;i++)
			 {
				 if(document.all["csjxm"].item(i).checked==true)
				 {
				 yhzs=yhzs+document.all["csjxm"].item(i).value;
				 }
			 }
		}
	}
document.all["sjxm"].value=yhzs;
return 1;
}

//获取处罚种类
function getCfzl(){
var yhzs;
yhzs="";
if(document.all.ccfzl)
	{
		if (document.all.ccfzl.type=="checkbox")
		{
			//单个
			if (document.all["ccfzl"].checked)
			{
				yhzs=document.all["ccfzl"].value;
			}else{
				yhzs="";
			}
		}else{
			 var yhz_length=document.all["ccfzl"].length;
			 for(var i=0;i<yhz_length;i++)
			 {
				 if(document.all["ccfzl"].item(i).checked==true)
				 {
				 yhzs=yhzs+document.all["ccfzl"].item(i).value;
				 }
			 }
		}
	}
document.all["cfzl"].value=yhzs;
}


//赋强制措施类型字段
function setQzcslxCheckBox(vQzcslx){
var tmpstr;
for(var i=0;i<vQzcslx.length;i++){
	tmpstr=vQzcslx.substr(i,1);
	if(tmpstr=="1"){
		document.all["cqzcslx"].item(0).checked=true;
	}
	if(tmpstr=="2"){
		document.all["cqzcslx"].item(1).checked=true;
	}
	if(tmpstr=="3"){
		document.all["cqzcslx"].item(2).checked=true;
	}
	if(tmpstr=="4"){
		document.all["cqzcslx"].item(3).checked=true;
	}
	if(tmpstr=="5"){
		document.all["cqzcslx"].item(4).checked=true;
	}
	if(tmpstr=="9"){
		document.all["cqzcslx"].item(5).checked=true;
	}
}
}

//赋扣留项目字段
function setKlxmCheckBox(vKlxm){
var tmpstr;
for(var i=0;i<vKlxm.length;i++){
	tmpstr=vKlxm.substr(i,1);
	if(tmpstr=="1"){
		document.all["cklxm"].item(0).checked=true;
	}
	if(tmpstr=="2"){
		document.all["cklxm"].item(1).checked=true;
	}
	if(tmpstr=="3"){
		document.all["cklxm"].item(2).checked=true;
	}
	if(tmpstr=="4"){
		document.all["cklxm"].item(3).checked=true;
	}
	if(tmpstr=="9"){
		document.all["cklxm"].item(5).checked=true;
	}
}
}

//将处罚种类的标记赋值给处罚种类CHECKBOX
function setCflzCheckBox(){
if(document.all["fkbj"].value=="1"){
	document.all["ccfzl"].item(0).checked=true;
}
if(document.all["zkbj"].value=="1"){
	document.all["ccfzl"].item(1).checked=true;
}
if(document.all["dxbj"].value=="1"){
	document.all["ccfzl"].item(2).checked=true;
}
if(document.all["jlbj"].value=="1"){
	document.all["ccfzl"].item(3).checked=true;
}
if(document.all["cxbj"].value=="1"){
	document.all["ccfzl"].item(4).checked=true;
}
}

//检测处罚种类字段输入是否正确
function checkCfzlCheckBox(){
	if(document.all["ccfzl"].item(0).checked==false){
		alert("处罚种类中的[罚款]必须选择！");
		return 0;
	}
	if(document.all["ccfzl"].item(1).checked==true&&document.all["zkbj"].value=="0"){
		alert("所有违法行为中均不存在[暂扣驾证]的处罚种类，不能选择[暂扣驾证]！");
		return 0;
	}
	if(document.all["ccfzl"].item(2).checked==true&&document.all["dxbj"].value=="0"){
		alert("所有违法行为中均不存在[吊销驾证]的处罚种类，不能选择[吊销驾证]！");
		return 0;
	}
	if(document.all["ccfzl"].item(3).checked==true&&document.all["jlbj"].value=="0"){
		alert("所有违法行为中均不存在[行政拘留]的处罚种类，不能选择[行政拘留]！");
		return 0;
	}
	if(document.all["ccfzl"].item(4).checked==true&&document.all["cxbj"].value=="0"){
		alert("所有违法行为中均不存在[撤销]的处罚种类，不能选择[撤销]！");
		return 0;
	}
	if(document.all["ccfzl"].item(1).checked==true&&document.all["ccfzl"].item(2).checked){
		alert("处罚种类中的[暂扣驾证]与[吊销驾证]不能并存，请重新选择！");
		return 0;
	}
	return 1;
}

//选择处罚种类
function CfzlCheck(v){

//暂扣驾证
if(v=="3"){
	if(document.all["ccfzl"].item(1).checked==true){
		alert("请选择[证件是否转递]并填写[证件领取地]！");
		qzclmain.zjsfzd(0).disabled=false;
		qzclmain.zjsfzd(1).disabled=false;
		qzclmain.zjlqd.disabled=false;
		
	}else{
		qzclmain.zjsfzd(0).disabled=true;
		qzclmain.zjsfzd(1).disabled=true;
		qzclmain.zjlqd.value="";
		qzclmain.zjlqd.disabled=true;
	}
}

//吊销驾证
if(v=="4"){
	if(document.all["ccfzl"].item(2).checked==true){
		alert("请选择[吊销驾证原因]！");
		qzclmain.dxyy.disabled=false;
	}else{
		qzclmain.dxyy.disabled=true;
	}
}

//撤销
if(v=="6"){
	if(document.all["ccfzl"].item(4).checked==true){
		alert("请选择[撤销项目]！");
		qzclmain.cxxm.disabled=false;
	}else{
		qzclmain.cxxm.disabled=true;
	}
}
}

//赋强制措施类型字段
function setCfzlCheckBox_Ldsp(v){
var tmpstr;
for(var i=0;i<v.length;i++){
	tmpstr=v.substr(i,1);
	if(tmpstr=="2"){
		document.all["ccfzl"].item(0).checked=true;
	}
	if(tmpstr=="3"){
		document.all["ccfzl"].item(1).checked=true;
	}
	if(tmpstr=="4"){
		document.all["ccfzl"].item(2).checked=true;
	}
	if(tmpstr=="5"){
		document.all["ccfzl"].item(3).checked=true;
	}
	if(tmpstr=="6"){
		document.all["ccfzl"].item(4).checked=true;
	}
}
}


/* -- 计算两个字符(YYYY-MM-DD)的日期相隔天数 -- */
function Date_Compare(asStartDate,asEndDate){
 var miStart = Date.parse(asStartDate.replace(/\-/g, '/'));
 var miEnd   = Date.parse(asEndDate.replace(/\-/g, '/'));
 return (miEnd-miStart)/(1000*24*3600);
}

//检测档案编号
function checkDabh(obj){
	obj.value=obj.value.trim();
	if(obj.value.length==0){
		alert("档案编号不能为空！")
		obj.focus();
		return 0;
	}
	if(obj.value!="无"){
		if(checkNumber(obj,"档案编号")==false){
			obj.focus();
			return 0;
		}
		if(obj.value.length!=10&&obj.value.length!=12){
			obj.focus();
			alert("档案编号不为10位或12位！");
			return 0;
		}
	}
	return 1;
}

//转换回车引号和其它字符,台帐中用20060710
function displayInfoHtml(strinfo)
{
  if(strinfo==null) strinfo="";
  while(strinfo.indexOf("A~A~")>0) {
	  strinfo = strinfo.replace("A~A~", "\n");
  }
 return strinfo;
}

function checkJszh(obj,vZjmc){
	obj.value=obj.value.trim();
	if(checkNull(obj,"证件号码")==false){
		obj.focus();
		return 0;
	}
	if(obj.value!="无"){
		if(obj.value.length<6||obj.value.length>18){
			alert("证件号码长度不符！");
			obj.focus();
			return 0;
		}else{
			if(obj.value.length==15){
				obj.value=id15to18(obj.value);
			}
			if(obj.value.length==18){
				if(check_jszh(obj.value)==0){
					obj.focus();
					return 0;
				}
			}
		}
	}
	return 1;
	}

//判断是否是钱的形式
function isMoney(pObj,errMsg){
 var obj = eval(pObj);
 strRef = "1234567890.";
 if(!checkNull(pObj,errMsg)) return false;
 for (i=0;i<obj.value.length;i++) {
  tempChar= obj.value.substring(i,i+1);
  if (strRef.indexOf(tempChar,0)==-1) {
   if (errMsg == null || errMsg =="")
    alert("数据不符合要求,请检查");
   else
    alert(errMsg+"输入不符合要求！");   
   if(obj.type=="text") 
    obj.focus(); 
   return false; 
  }else{
   tempLen=obj.value.indexOf(".");
   if(tempLen!=-1){
    strLen=obj.value.substring(tempLen+1,obj.value.length);
    if(strLen.length>2){
     if (errMsg == null || errMsg =="")
      alert("数据不符合要求,请检查");
     else
      alert(errMsg+"输入不符合要求！");  
     if(obj.type=="text") 
     obj.focus(); 
     return false; 
    }
   }
  }
 }
 return true;
}
//-------------------------------
//  函 数 getradio(obj)
//  功能介绍：取单选框的内容.
//  参数说明：单选框的数组名
//  返    回：单选框的值
//-------------------------------
function getradio(obj){
	var value="";
	var length=obj.length
	for(var i=0;i<length;i++){
		if(obj.item(i).checked){
		  value=obj.item(i).value
		}
	}
	return value;
}
//判断日期类型是否合法 zhoujn 20060323
function checkDate(i_field,thedate)
{
  if(thedate.length==0||thedate==null){
	alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd！");
	return 0;
  }
  
  if (!(thedate.length==8 || thedate.length==10))
   {    alert("'"+i_field+"'日期格式不对,\n要求为yyyy-mm-dd！");
     return 0;
   }
  if (thedate.length==8)
  {
  	thedate=thedate.substr(0,4)+"-"+thedate.substr(4,2)+"-"+thedate.substr(6,2);
  }
    var reg = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;
    var r = thedate.match(reg);
     if (!(r==null))
    {

    var d= new Date(r[1],r[3]-1,r[4]);
    var newStr=d.getFullYear()+r[2]+(d.getMonth()+1)+r[2]+d.getDate()
    var newDate=r[1]+r[2]+(r[3]-0)+r[2]+(r[4]-0)
    if (newStr==newDate)
         {
          return 1;
         }
     alert("'"+i_field+"'日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！");
     return 0;
    }
	alert("'"+i_field+"'日期格式不对,\n要求为yyyymmdd或yyyy-mm-dd！");
    return 0;
}
/*
检测开始时间和结束时间
*/
function checkKssjAndJssj(kssj,jssj){
	if(!checkDate("开始时间",kssj))return false;
	if(!checkDate("结束时间",jssj))return false;
	if(kssj>jssj){
		alert("起始时间不能大于终止时间！");
		return false;
	}
	return true;
}

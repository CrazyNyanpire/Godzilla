<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<%@ page import="com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
   String strdata = new String(request.getParameter("data").getBytes("ISO-8859-1"), "UTF-8");
   String date = MyDateUtils.getToday();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Wip Summary</title>
<link rel="stylesheet" href="/css/summary.css">
<script type="text/javascript" src="<c:url value='/js/headsummary.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/accounting.min.js' />" ></script>
<script type="text/javascript" src="<c:url value='/js/handlebars-v2.0.0.js' />" ></script>
<style>
.tablecontent{
	text-align:right;
	background-color:#eee;
}
.tableadd{
	text-align:right;
}
.tablehead{
	background-color: #1e50a2;;
}
</style>
</head>
<body>
<div class="g-head">
	    <nav class="navbar navbar-default" style="border:none;">
	        <a class="navbar-brand" href="#"><img src="<c:url value='/images/logo1.png'/>"/></a>
	        <form id="headSummaryForm">
	        <div class="God_searchontent">
	        <div class="God_searchontent_left">
		        <ul>
					<li><input type="radio" class="summaryType" value="产品" name="God_typeCheck" ><span>产品</span></li>
					<li><input type="radio" class="summaryType" value="晶圆" name="God_typeCheck"><span>晶圆</span></li>
					<li><input type="radio" class="summaryType" value="材料" name="God_typeCheck" ><span>材料</span></li>
				</ul>
			</div>
	        <div class="God_searchontent_center">
	        	<div class="tabs">
			        <ul id="tabs">
			            <li name="today" class="tab-nav-action">Today</li>
			            <li name="week" class="tab-nav">Week-to-Date</li>
			            <li name="month" class="tab-nav">Month-to-Date</li>
			            <li style="border-right:1px solid #ccc" class="tab-nav">Engineering</li>
			        </ul>
			        <p style="clear:both;margin:0;"></p>
			    </div>
			    <div id="tabs-body" class="tabs-body">
			        <div style="display:block">
				        <div style="display: inline-block;margin-right:10px;">
					        <ul>
					        	<li><input type="radio" value="Now" name="God_today"><span>Now</span></li>
					        	<li><input type="radio" value="Period" name="God_today"><span>Period</span></li>
					        </ul>
				        </div>
				        <input name="begindata" type="date">
				        <select name="beginHour" id="God_beginHour" style="height: 22px;">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>	
						</select>
						<input name="enddata" type="date">
						<select name="endHour" id="God_endHour" style="height: 22px;">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>	
						</select>
			        </div>
			        <div style="display:none">
			        <div class="God_tabs_date">
			        	<span>Week</span>
			        	<select name="week" id="God_week">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
							<option value="32">32</option>
							<option value="33">33</option>
							<option value="34">34</option>
							<option value="35">35</option>
							<option value="36">36</option>
							<option value="37">37</option>
							<option value="38">38</option>
							<option value="39">39</option>
							<option value="40">40</option>
							<option value="41">41</option>
							<option value="42">42</option>
							<option value="43">43</option>
							<option value="44">44</option>
							<option value="45">45</option>
							<option value="46">46</option>
							<option value="47">47</option>
							<option value="48">48</option>
							<option value="49">49</option>
							<option value="50">50</option>
							<option value="51">51</option>
							<option value="52">52</option>	
						</select>
					</div>
			        </div>
			        <div style="display:none">
				        <div class="God_tabs_date">
				        	<span>Month</span>
				        	<input name="month" type="month">
				        </div>
			        </div>
			        <div style="display:none">
			        
			        </div>
			    </div>
	        </div>
	        <div class="God_searchontent_right">
		        <div>
		        	<ul class="God_searchontent_left_ul">
		        		<li>Area</li>
		        		<li>Process</li>
		        		<li>Step</li>
		        	</ul>
		        </div>
		        <div>
		        	<ul>
		        		<li>
		        			<select name="headSummary_Area" id="headSummary_Area">
			        			<option value="">--请选择--</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_Process" id="headSummary_Process">
			        			<option value="">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_Step" id="headSummary_Step">
			        			<option value="">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        	</ul>
		        </div>
		        <div>
		        	<ul class="God_searchontent_left_ul">
		        		<li>Cus.Code</li>
		        		<li>Product</li>
		        		<li>Pack</li>
		        	</ul>
		        </div>
		        <div>
		        	<ul>
		        		<li>
		        			<select name="headSummary_cusCode" id="headSummary_cusCode">
			        			<option value="">Select All</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_product" id="headSummary_product">
			        			<option value="">Select All</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_pack" id="headSummary_pack">
			        			<option value="">Select All</option>
			        			<option value="1">3x3</option>
			        			<option value="2">6x6</option>
		        			</select>
		        		</li>
		        	</ul>
		        </div>
		        <a id="headSummary_search" mouseover="headsummarysearch()" href="#" style="margin-left:23px"><img style="margin-top: 15px;"src="../images/icon/u93_normal.png" alt="search"></a>
	        </div>
	        <div style="clear:both;"></div>
	        </div>
	        </form>
	        <div class="collapse navbar-collapse navbar-ex1-collapse">
	            <div class="btn-group navbar-right">
	                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
	                    <i class="glyphicon glyphicon-user"></i>
	                    <span>&nbsp;<ss3:authentication property="principal.username" /></span>
	                    <span class="caret"></span>
	                </button>
	            </div>
	        </div>
	        <SCRIPT>
			   setInterval("var array=new Date().toDateString().split(' ');timer.innerHTML=array[0]+'  '+array[2]+' - '+array[1]+' - '+array[3]+' '+new Date().toTimeString().substr(0,5)",1000);
			   //setInterval("$.get('/Date/nowDate.koala').done(function(json){var time=json['nowDateTime'];timer.innerHTML=time;});",1000);
			</SCRIPT>
			<div style="width: 146px;color: #fff;float: right;margin-top: 1.3%;"id=timer></div>
	    </nav>
	</div>
	<a href="#" title="点击下载" class="btn btn-primary export-button" download="TotalWIP.xls"><i class="icon-download-alt icon-white"></i>导出Excle</a>
	<div id="wipMainContent">
		<table class="table table-bordered" style="text-align: center;width:99%;margin:0 auto">
			<tbody>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
 $(document).ready(function () {
	var data='<%=strdata%>';
	data=eval("(" + data + ")");
 	var wipType=data['God_typeCheck'];
 	$("input[value='"+wipType+"']").attr("checked","checked");
 	$(".export-button").bind("click",function(event){
 		debugger;
	 	$(".export-button").attr("download","<%=date%> TotalWIP.xls");
		exportHandler(event);
	});
	if(wipType=="产品")
	{
		$("#headSummary_Area").empty();
		$("#headSummary_Area").append('<option value="">--请选择--</option><option value="4">Assembly</option>');
		wipType=$(this).val();
	}
	else if(wipType=="晶圆")
	{
		$("#headSummary_Area").empty();
		$("#headSummary_Area").append('<option value="">--请选择--</option><option value="3">Preassy</option>');
		wipType=$(this).val();
	}
	else 
	{
		$("#headSummary_Area").empty();
		$("#headSummary_Area").append('<option value="">--请选择--</option><option value="1">Warehouse</option><option value="2">LineStore</option>');
		wipType=$(this).val();
	}
 	$("input[name='God_typeCheck']").change(function(){
 		if($(this).val()=="产品")
			{
 			$("#headSummary_Area").empty();
				$("#headSummary_Area").append('<option value="">--请选择--</option><option value="4">Assembly</option>');
				wipType=$(this).val();
			}
 		else if($(this).val()=="晶圆")
			{
 			$("#headSummary_Area").empty();
				$("#headSummary_Area").append('<option value="">--请选择--</option><option value="3">Preassy</option>');
				wipType=$(this).val();
			}
 		else 
			{
 			$("#headSummary_Area").empty();
				$("#headSummary_Area").append('<option value="">--请选择--</option><option value="1">Warehouse</option><option value="2">LineStore</option>');
				wipType=$(this).val();
			}
 	});
	$.get('${pageContext.request.contextPath}/Customer/all.koala').done(function(msg){
 		for (var i=0;i<msg['data'].length;i++)
     	{
 			$("#headSummary_cusCode").append('<option value="'+msg['data'][i]['id']+'">'+msg['data'][i]['customerCode']+'</option>');
     	}
		});
		$.get('${pageContext.request.contextPath}/Product/all.koala').done(function(msg){
 		for (var i=0;i<msg['data'].length;i++)
     	{
 			$("#headSummary_product").append('<option value="'+msg['data'][i]['id']+'">'+msg['data'][i]['productName']+'</option>');
     	}
	});
 	$("#headSummary_search").bind("click",function(){
 		headsummaryskip();
 	});
 	wipdataGet(data);
     $("#tabs li").bind("click", function () {
         var index = $(this).index();
         var divs = $("#tabs-body > div");
         $(this).parent().children("li").attr("class", "tab-nav");//将所有选项置为未选中
         $(this).attr("class", "tab-nav-action"); //设置当前选中项为选中样式
         divs.hide();//隐藏所有选中项内容
         divs.eq(index).show(); //显示选中项对应内容
     });

 });
</script>
</html>
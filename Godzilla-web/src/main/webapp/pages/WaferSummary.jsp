<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Wafer Summary</title>
</head>
<body>
	<div class="g-head">
	    <nav class="navbar navbar-default">
	    <a class="navbar-brand" href="#"><img src="<c:url value='/images/logo1.png'/>"/></a>
	        <form id="headSummaryForm">
	        <div class="God_searchontent">
	        <div class="God_searchontent_left">
		        <ul>
					<li><input type="radio" class="summaryType" value="晶圆" name="God_typeCheck" ><span>晶圆</span></li>
					<li><input type="radio" class="summaryType" value="基板" name="God_typeCheck"><span>基板</span></li>
				</ul>
			</div>
	        <div class="God_searchontent_center">
	        	<div class="tabs">
			        <ul id="tabs">
			            <li class="tab-nav-action">Today</li>
			            <li class="tab-nav">Week-to-Date</li>
			            <li class="tab-nav">Month-to-Date</li>
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
				        <select name="beginHour" id="God_beginHour">
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
						<select name="endHour" id="God_endHour">
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
			        			<option value="Select All">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_Process" id="headSummary_Process">
			        			<option value="Select All">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_Step" id="headSummary_Step">
			        			<option value="Select All">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        	</ul>
		        </div>
		        <div>
		        	<ul class="God_searchontent_left_ul">
		        		<li>CusCode</li>
		        		<li>Part No</li>
		        		<li>Equipment</li>
		        	</ul>
		        </div>
		        <div>
		        	<ul>
		        		<li>
		        			<select name="headSummary_cusLotNo" id="headSummary_cusLotNo">
			        			<option value="Select All">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_partNo" id="headSummary_partNo">
			        			<option value="Select All">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        		<li>
		        			<select name="headSummary_equipment" id="headSummary_equipment">
			        			<option value="Select All">Select All</option>
			        			<option value="1">1</option>
			        			<option value="2">2</option>
			        			<option value="3">3</option>
		        			</select>
		        		</li>
		        	</ul>
		        </div>
		        <a id="headSummary_search" mouseover="headsummarysearch()" href="#" style="margin-left:23px"><img style="margin-top: 22px;"src="../images/icon/u93_normal.png" alt="search"></a>
	        </div>
	        <div style="clear:both;"></div>
	        </div>
	        </form>
	        </nav>
	        </div>
</body>
    <script type="text/javascript">
	    $(document).ready(function () {
	    	$("#headSummary_search").bind("click",function(){
	    		headsummarysearch();
	    		});
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
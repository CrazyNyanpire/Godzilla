 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="k" uri="http://www.openkoala.com/token"%>
<%@ taglib prefix="ss3" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" import="com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils"%>
<%@ page import="java.util.Date"%>
<%Long time = new Date().getTime();%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>Godzilla封装系统</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value='/lib/bootstrap/css/bootstrap.min.css' />"   rel="stylesheet">
    <link href="<c:url value='/css/main.css' />?time=<%=time%>" rel="stylesheet">
    <link href="<c:url value='/css/security.css' />"   rel="stylesheet">
    <link href="<c:url value='/css/koala.css' />?time=<%=time%>" rel="stylesheet">
    <link href="<c:url value='/css/organisation.css' />?time=<%=time%>" rel="stylesheet">
    <link href="<c:url value='/lib/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css' />
    " rel="stylesheet">
    <link rel="shortcut icon" href="images/ico.png">
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
</head>
<body>
	<input type="hidden" id="roleId" value="${roleId}" />
	<div class="g-head">
	    <nav class="navbar navbar-default">
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
		        <a id="headSummary_search" mouseover="headsummarysearch()" href="#" style="margin-left:23px"><img style="margin-top: 15px;"src="images/icon/u93_normal.png" alt="search"></a>
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
	                <ul class="dropdown-menu" id="userManager">
	                    <li data-target="modifyPwd"><a href="#">修改密码</a></li>
	                    <li data-target="loginOut"><a href="#">注销</a></li>
	                </ul>
	            </div>
	        </div>
	        <SCRIPT>
			   setInterval("var array=new Date().toDateString().split(' ');timer.innerHTML=array[0]+'  '+array[2]+' - '+array[1]+' - '+array[3]+' '+new Date().toTimeString().substr(0,5)",1000);
			   //setInterval("$.get('/Date/nowDate.koala').done(function(json){var time=json['nowDateTime'];timer.innerHTML=time;});",1000);
			</SCRIPT>
			<div style="width: 146px;color: #fff;float: right;margin-top: 1.3%;"id=timer></div>
	    </nav>
	</div>
	<div class="g-body">
	    <div class="col-xs-2 g-sidec">
	        <ul class="nav nav-stacked first-level-menu">
	       		
	        </ul>
	    </div>
	    <div class="col-xs-10 g-mainc">
	        <ul class="nav nav-tabs" id="navTabs">
	            <li class="active"><a href="#home" data-toggle="tab">主页</a></li>
	        </ul>
	        <div class="tab-content" id="tabContent">
	            <div id="home" class="tab-pane active"></div>
	        </div>
	    </div>
	</div>
	<div id="footer" class="g-foot">
	    <span>Copyright © 2014-2015 Godzilla</span>
	</div>
	<script type="text/javascript" src="<c:url value='/lib/jquery-1.8.3.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/lib/respond.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/lib/bootstrap/js/bootstrap.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/lib/koala-ui.plugin.js' />?time=<%=time%>" ></script>
	<script type="text/javascript" src="<c:url value='/lib/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/lib/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js' />"></script>
	<script type="text/javascript" src="<c:url value='/lib/koala-tree.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/lib/validate.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/main.js' />?time=<%=time%>" ></script>
	<script type="text/javascript" src="<c:url value='/js/headsummary.js' />?time=<%=time%>" ></script>
    <script type="text/javascript" src="<c:url value='/js/security/role.js' />?time=<%=time%>" ></script>
    <script type="text/javascript" src="<c:url value='/js/security/user.js' />?time=<%=time%>" ></script>
    <script type="text/javascript">
	    $(document).ready(function () {
	    	var wipType="";
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
	<script>
		$(function(){
            $.get(contextPath + '/auth/Resource/isResourceEmpty.koala').done(function(result){
                if(result.result){
                    var dialog = $('<div class="modal fade"><div class="modal-dialog" style="padding-top:10%;width:400px;"><div class="modal-content">' +
                    ' <div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">' +
                    '&times;</button><h4 class="modal-title">菜单初始化</h4></div><div class="modal-body">' +
                    '<h5>是否进行菜单初始化？</h5></div><div class="modal-footer"><button type="button" class="btn btn-default"' +
                    ' data-dismiss="modal">取消</button><button id="confirm" type="button" class="btn btn-primary">确定</button></div>' +
                    '</div></div></div>');
                    dialog.modal({
                        keyboard: true
                    }).on('hidden.bs.modal', function(){
                        $(this).remove();
                    }).find('#confirm').on('click', function(){
                        $.ajax({
                            type: 'GET',
                            url:'dbinit',
                            dataType: 'text'
                        }).done(function(data){
                            if(data == 'success'){
                                dialog.modal('hide');
                                $('body').message({
                                    type: 'success',
                                    content: '权限初始化成功'
                                });
                                setTimeout(function(){
                                    window.location.reload();
                                },2000);
                            }
                        });
                    });
                    return;
                }
                $.get(contextPath + '/auth/Menu/findMenuByUser.koala').done(function(data){
                    $.each(data.data, function(){
                        var $li = $('<li class="folder"><a data-toggle="collapse" href="#menuMark'+this.id+'"><span class="'+this.icon+'"></span>&nbsp;'+this.name+'&nbsp;'+
                            '<i class="glyphicon glyphicon-chevron-left" style=" float: right;font-size: 12px;position: relative;right: 8px;top: 3px;"></i></a><ul id="menuMark'+this.id+'" class="second-level-menu in"></ul></li>');
                        $('.first-level-menu').append($li);
                        renderSubMenu(this.children, $li);
                    });
                    /*
                    * 菜单收缩样式变化
                     */
                    var firstLevelMenu = $('.first-level-menu');
                    firstLevelMenu.find('[data-toggle="collapse"]').each(function(){
                        var $this = $(this);
                        firstLevelMenu.find($(this).attr('href')).on({
                            'shown.bs.collapse': function(e){
                                $this.find('i:last').addClass('glyphicon-chevron-left').removeClass('glyphicon-chevron-right');
                            },
                            'hidden.bs.collapse': function(e){
                                $this.find('i:last').removeClass('glyphicon-chevron-left').addClass('glyphicon-chevron-right');
                            }
                        });
                    });
                });
            })
			var renderSubMenu = function(data, $menu){
						$.each(data, function(){
							if(this.menuType == "2"){
		                        var $li = $('<li class="folder"><a data-toggle="collapse" href="#menuMark'+this.id+'"><span class="'+this.icon+'"></span>&nbsp;'+this.name+'&nbsp;'+
		                            '<i class="glyphicon glyphicon-chevron-right pull-right" style="position: relative; right: 12px;font-size: 12px;"></i></a><ul id="menuMark'+this.id+'" class="second-level-menu collapse"></ul></li>');
		                        $li.appendTo($menu.find('.second-level-menu:first')).find('a').css('padding-left', parseInt(this.level)*18+'px');
		                        renderSubMenu(this.children, $li);
		                    }else{
		                        var $li = $(' <li class="submenu" data-role="openTab" data-target="'+this.identifier+'" data-title="'+this.name+'" ' +
		                            'data-mark="menuMark'+this.id+'"><a ><span class="'+this.icon+'"></span>&nbsp;'+this.name+'</a></li>');
		                        $li.appendTo($menu.find('.second-level-menu:first')).find('a').css('padding-left', parseInt(this.level)*18+'px');
		                    }
						});
	                    $menu.find('[data-toggle="collapse"]').each(function(){
	                        var $this = $(this);
	                        $menu.find($(this).attr('href')).on({
	                            'shown.bs.collapse': function(e){
	                            	e.stopPropagation();
	                            	e.preventDefault();
	                                $this.find('i:last').addClass('glyphicon-chevron-left').removeClass('glyphicon-chevron-right');
	                            },
	                            'hidden.bs.collapse': function(e){
	                            	e.stopPropagation();
	                            	e.preventDefault();
	                                $this.find('i:last').removeClass('glyphicon-chevron-left').addClass('glyphicon-chevron-right');
	                            }
	                        });
	                    });
						$menu.find('li.submenu').on('click', function(){
								var $this = $(this);
								$('.first-level-menu').find('li').each(function(){
									var $menuLi = $(this);
									$menuLi.hasClass('active') && $menuLi.removeClass('active').parent().parent().removeClass('active');
								});
								$this.addClass('active').parents().filter('.folder').addClass('active');
								var target = $this.data('target');
								var title = $this.data('title');
								var mark = $this.data('mark');
								if(target && title && mark ){
									openTab(target, title, mark);
								}
							});
			};
		});
	</script>
</body>
</html>
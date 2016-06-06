<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
var materialTypeId="";
switch(AlphaId)
{
	case "103":
		materialTypeId="1";
		break;
	case "105":
		materialTypeId="3";
		break;
	case "106":
		materialTypeId="4";
		break;
	default:
		break;
		
}
var grid;
var form;
var _dialog;
$(function (){
	$.get('${pageContext.request.contextPath}/MaterialProcess/pageTotal.koala?stationId='+AlphaId).done(function(result){
		debugger;
		grid.next().children().children("#God_qtyTotle").text("In Qty:"+result['dieTotal']+" | In Capa.:"+result['inCapacityTotal']+" | Balance:"+result['balanceTotal']);
		grid.next().children().children("#God_batchTotle").text(result['countLot']);
	});
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
	        	            	                	            	                	            	                	            	                	            	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 100;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-log-in"><span style="margin-left:5px;">收料</button>', action: 'Receiving'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-minus-sign"><span style="margin-left:5px;">IQC<span class="God_span_nbsp"/>Disp</button>', action: 'Hold'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-minus-sign"><span style="margin-left:5px;">Engr<span class="God_span_nbsp"/>Disp</button>', action: 'EngrDisp'},
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-log-out"><span style="margin-left:5px;">发料</button>', action: 'dispatch'}
	                    ],
	                url:"${pageContext.request.contextPath}/MaterialProcess/pageJson.koala",
	                columns: [
   	                         	 { title: 'Material', name: 'materialName', width: 90},
		               			 { title: 'Material N.', name: 'lotNo', width: 100},
		               			 { title: 'Part N.', name: 'partId', width: 110},
      	                         { title: 'Partname_cn', name: 'partNameCN', width: 110},
      	                         { title: 'Batch N.', name: 'batchNo', width: 90},
     	                         { title: 'InQty', name: 'qtyIn', width: 50},
     	                         { title: 'In Cap.', name: 'inCapacity', width: 60},
     	                         { title: 'Balance', name: 'balance', width: 65},
      	                         { title: 'Unit', name: 'unit', width: 50},
      	                       { title: 'Curr.Status', name: 'status', width: 90, render: function (rowdata, name, index){
			                    	  if(rowdata[name]=="Hold")
			                   		  {
			                   		 	 var h = "<div id='currStatus"+rowdata['id']+"' style='color: #fff;background-color:red'>"+rowdata[name]+"</div>";
			                   		 	 return h;
			                   		  }
			                    	  else{
			                    		  return "<div id='currStatus"+rowdata['id']+"'>"+rowdata[name]+"</div>";
			                    	  } 
			                      }},
      	                       { title: 'Type', name: 'lotType', width: 50, render: function (rowdata, name, index){
      	                    	  if(rowdata[name]==="E")
      	                   		  {
      	                    		  debugger;
      	                    		  //$("div[data-value='"+rowdata['id']+"']").parent().parent().css("background-color","#ccc");
      	                    		  var h = "<div style='background-color:#eee'>"+rowdata[name]+"</div>";
      	                   		 	  return h;
      	                   		  }
      	                    	  else{
      	                    		  return rowdata[name];
      	                    	  } 
      	                      }},
      	                         { title: 'Vender', name: 'venderName', width: 90},
      	                         { title: 'Po N.', name: 'pon', width: width},
			                      { title: 'Entry Date', name: 'entryDate', width: 80},
			                      { title: 'Entry Time', name: 'entryTime', width: 80},
								  { title: 'Manufacture Date', name: 'productionDate', width: 115},
								  { title: 'Expiry Date', name: 'guaranteePeriod', width: 90},
	   	                          { title: 'User', name: 'userName', width: width},			                      
			                      { title: 'Stock Pos.', name: 'stockPos', width: 90}
	                ]
	         }).on({
	                   'Receiving': function(){
	                	   window.open("<%=path%>/DirectMaterialReceiving.jsp?materialTypeId="+materialTypeId);  
	                   },
	                   'Hold': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行Hold'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行Hold'
	                            })
	                            return;
	                        }
		                    if($("#currStatus"+indexs[0]).text()!="Waiting")
	                    	{
		                    	$('body').message({
		                            type: 'error',
		                            content: '只有Waiting状态的批次才能执行该方法'
		                            });
		                    	return false;
	                    	}
	                       self.Hold(indexs[0], $this);
	                    },
	                    'EngrDisp': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行EngrDisp'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行EngrDisp'
	                            })
	                            return;
	                        }
	                        if($("#currStatus"+indexs[0]).text()!="Hold")
	                    	{
		                    	$('body').message({
		                            type: 'error',
		                            content: '只有Hold状态的批次才能执行该操作'
		                            });
		                    	return false;
	                    	}
	                       self.EngrDisp(indexs[0], $this);
	                    },
	                   'dispatch': function(){
	                	   window.open("<%=path%>/DirectMaterialDispatch.jsp?materialTypeId="+AlphaId); 
	                   }
	         });
	    },
	    Hold: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">IQC Disp</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/WaferHold.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#holdCode2').one('click',{grid: grid}, function(e){
	                	dialog.find("#God_MHRNum").append('<div id="God_MHRNum2" class="form-group"><label class="col-lg-3 control-label">MHR:</label><div class="col-lg-9"><input name="MHRNum" style="display:inline; width:94%;" class="form-control"  type="text"  id="MHRNumID" readonly/></div></div>');
	                	$.ajax({
	                		url: '/MHR/getNewMHRNo.koala',
	                		type: 'POST',
	                		dataType: 'json',
	                	})
	                	.done(function(msg) {
	                		dialog.find("#MHRNumID").val(msg['data']);
	                	});
	                });
	                dialog.find('#holdCode1').bind('click',{grid: grid}, function(e){
	                	dialog.find("#God_MHRNum2").remove();
	                	dialog.find("#changeTime").text("Pass Time:");
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    var rs=requestParamToJson(dialog.find('form').serializeArray());
	                    var inobj={};
	                    inobj['holdTime']=rs['holdTime'].replace(" ","T")+":00.000%2B0800";
	                    inobj['id']=id;
	                    inobj['comments']=rs['comments'];
	                    if(rs['holdCode']==undefined)
		    			{
		    				dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: '请选择单选按钮'
	                            });
		    				return false;
		    			}
	                    inobj['holdCode']=rs['holdCode'];
	                    delete rs['comments'];
	                    delete rs['holdTime'];
	                    rs['data']=inobj;
	                    debugger;
	  	          		var restring=JSON.stringify(rs);
	                    $.post('${pageContext.request.contextPath}/MaterialProcess/Process/hold.koala', "data="+restring).done(function(result){
	                    	messagecheck(e,result,dialog);
	                    });
	                });
	        });
	    },
	    EngrDisp: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">EngrDisp</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/WaferEngrDisp.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               debugger;
	               $.get( '${pageContext.request.contextPath}/MaterialProcess/Process/getHoldTime/' + id + '.koala').done(function(json){
						dialog.find('#holdTimeoneID').val(json['holdTime']);
               		});
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    var rs=requestParamToJson(dialog.find('form').serializeArray());
	                    var inobj={};
	                    inobj['holdTime']=rs['holdTime'].replace(" ","T")+":00.000%2B0800";
	                    inobj['id']=id;
	                    inobj['engineerName']=rs['engineerName'];
	                    if(rs['holdCode']==undefined)
		    			{
		    				dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: '请选择单选按钮'
	                            });
		    				return false;
		    			}
	                    inobj['holdCode']=rs['holdCode'];
	                    inobj['comments']=rs['comments'];
	                    rs['data']=inobj;
	                    delete rs['engineerName'];
	                    delete rs['holdCode'];
	                    delete rs['comments'];
	                    delete rs['DisposeTime'];
	                    debugger;
	  	          		var restring=JSON.stringify(rs);
	                    $.post('${pageContext.request.contextPath}/MaterialProcess/Process/engDisp.koala', "data="+restring).done(function(result){
	                    	messagecheck(e,result,dialog);
	                    });
	                });
	        });
	    },
	    initPage: function(form){
	              form.find('.form_datetime').datetimepicker({
	                   language: 'zh-CN',
	                   format: "yyyy-mm-dd",
	                   autoclose: true,
	                   todayBtn: true,
	                   minView: 2,
	                   pickerPosition: 'bottom-left'
	               }).datetimepicker('setDate', new Date());//加载日期选择器
	               form.find('.select').each(function(){
	                    var select = $(this);
	                    var idAttr = select.attr('id');
	                    select.select({
	                        title: '请选择',
	                        contents: selectItems[idAttr]
	                    }).on('change', function(){
	                        form.find('#'+ idAttr + '_').val($(this).getValue());
	                    });
	               });
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/WaferCompanyLot/delete.koala', data).done(function(result){
	                        if(result.result == 'success'){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '删除成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.result
	                            });
	                        }
	    	});
	    }
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
	grid.getGrid().searchCondition['stationId']=parseInt(AlphaId);
	$(".table").bind("mouseover", function(){
		$("div[id^='currStatus']").unbind();
		for(var i=0;i<$("div[id^='currStatus']").length;i++)
		{
			if($("div[id^='currStatus']").eq(i).text()=="Hold")
				{
					$("div[id^='currStatus']").eq(i).css("cursor","pointer");
					$("div[id^='currStatus']").eq(i).bind('click', function(e){
						debugger;
						var dialog = $('<div class="modal fade"><div style="width:800px" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Hold Reason</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
						dialog.modal({
			                keyboard:false
			            }).on({
			                'hidden.bs.modal': function(){
			                    $(this).remove();
			                }
			            });
						var id=$(this).attr('id').replace(/[^0-9]/ig,"");
						dialog.find('.modal-body').html('<form class="form-horizontal"></form>');
						$.get('${pageContext.request.contextPath}/MaterialProcess/getHoldReason/'+id+'.koala').done(function(json){
							debugger;
							json=json['data'];
							dialog.find('.form-horizontal').append('<div class="form-group"><label class="col-lg-2 control-label">Reason:</label><div class="col-lg-9"></div>');
							dialog.find(".col-lg-9").text(json['comments']);
						});
					});
				}
		}
	});
	form.find('#search').on('click', function(){
        if(!Validator.Validate(document.getElementById("<%=formId%>"),3))return;
        var params = {};
        var status='';
        form.find('input').each(function(){
            var $this = $(this);
            if($this.attr("checked")=='checked')
            	{
            		status+="'"+$this.val()+"',";
            	}
            var name = $this.attr('name');
            if(name){
                params[name] = $this.val();
            }
        });
        status=status.substring(0,status.length-1);
        params['currStatus']=status;
        debugger;
        grid.getGrid().search(params);
        params['page']=0;
        params['page']=1000;
        params['stationId']=AlphaId;
        $.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/MaterialProcess/pageTotal.koala",
			data : params,
		}).done(function(result) {
			debugger;
			grid.next().children().children("#God_qtyTotle").text("In Qty:"+result['dieTotal']+" | In Capa.:"+result['inCapacityTotal']+" | Balance:"+result['balanceTotal']);
			grid.next().children().children("#God_batchTotle").text(result['countLot']);
		});
    });
});
</script>
</head>
<body>
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<a style="position: absolute;right: 0;margin-right: 3%;"class="btn btn-primary" href="/pages/core/Equipment-list.jsp" target="_blank" >设备状态查询维护</a>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
          <div class="form-group God_Status">
            <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:5%;">Status:&nbsp;</label>
             <div style="margin-left:15px;float:left;">
         	<input name="currStatusWaitingIQC"  type="checkbox" value="Waiting" id="currStatusIDLEID"  />
         	<span>Waiting</span>
         	<input name="currStatusPass"  type="checkbox" value="Pass"  id="currStatusRUNID"  />
         	<span>Pass</span>
         	<input name="currStatusHold"  type="checkbox" value="Hold"  id="currStatusDOWNID"  />
         	<span>Hold</span>
         	<input name="currStatusRTV"  type="checkbox" value="RTV"  id="currStatusENGRID"  />
         	<span>RTV</span>
	      	</div>
         </div>
          <div class="form-group">
          <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:5%;">Material:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="materialName" class="form-control" type="text" style="width:180px;" id="materialNameID"  />
        </div>
          <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:4%;">Batch No:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="batchNo" class="form-control" type="text" style="width:180px;" id="batchNoID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;">Vender:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="venderName" class="form-control" type="text" style="width:180px;" id="venderID"  />
        </div>
            </div>
                  <div class="form-group">
          <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:5%;">Po No:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="pon" class="form-control" type="text" style="width:180px;" id="ponID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:4%;">Part No:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="partId" class="form-control" type="text" style="width:180px;" id="partIdID"  />
        </div>
            </div>
                </td>
       <td style="vertical-align: bottom;"><button id="search" type="button"  class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;Search</button></td>
  </tr>
</table>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
<div id="God_search"><div style="display: inline-block;width: 300px;margin-left: 1%;"><span>批次统计：</span><span id="God_batchTotle"></span></div><div style="display: inline-block;width: 400px;margin-left: 5%;"><span>数量统计：</span><span id="God_qtyTotle"></span></div></div>
</div>
</body>
</html>
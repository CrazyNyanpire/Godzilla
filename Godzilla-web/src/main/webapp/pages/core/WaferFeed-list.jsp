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
var grid;
var form;
var _dialog;
$(function (){
	debugger;
	$.get('${pageContext.request.contextPath}/WaferCustomerLot/pageTotal.koala').done(function(result){
		
		grid.next().children().children("#God_qtyTotle").text("Wafer #:"+result['waferTotal']+" | Qty:"+result['dieTotal']+" | 1st P.Qty:"+result['firstPQty']+" | 2nd P.Qty:"+result['secondPQty']+" | 3rd P.Qty:"+result['thirdPQty']);
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
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'}
	                    ],
	                url:"${pageContext.request.contextPath}/WaferCustomerLot/pageJson.koala",
	                columns: [
	                  { title: 'Cus.Code', name: 'customerCode', width: width, sortable:"true", sortName: 'customerCode'},
                      { title: 'Cus.Lot N.', name: 'customerLotNo', width: width, sortable:"true", sortName: 'customerLotNo'},
                      { title: 'Product Name', name: 'productName', width: 110, sortable:"true", sortName: 'productName'},
                      { title: 'Part N.', name: 'partNumber', width: width, sortable:"true", sortName: 'partNumber'},
                      { title: 'Wafer #', name: 'wafer', width: 75, sortable:"true", sortName: 'wafer'},                      
                      { title: 'Qty', name: 'qty', width: 50, sortable:"true", sortName: 'qty'},
                      { title: '1st P.Qty', name: 'firstPQty', width: 85, sortable:"true", sortName: 'firstPQty'},
                      { title: '2nd P.Qty', name: 'secondPQty', width: 85, sortable:"true", sortName: 'secondPQty'},
                      { title: '3rd P.Qty', name: 'thirdPQty', width: 85, sortable:"true", sortName: 'thirdPQty'},
                      { title: 'Wafer.Status', name: 'waferStatusName',width: width},
                      { title: 'Curr.Status', name: 'currStatus', width: width, sortable:"true", sortName: 'currStatus', render: function (rowdata, name, index){
                    	  if(rowdata[name]=="Received")
                   		  {
                   		 	 var h = "<div style='background-color:#badcad'>"+rowdata[name]+"</div>";
                   		 	 return h;
                   		  }
                    	  else if(rowdata[name]=="Hold")
                    	  {
                    		  var h = "<div id='currStatus"+rowdata['id']+"' style='color: #fff;background-color:red'>"+rowdata[name]+"</div>";
	                   		 	 return h;
                    	  }
                    	  else{
                    		  return rowdata[name];
                    	  } 
                      }},
                      { title: 'Type', name: 'lotType', width: 60, render: function (rowdata, name, index){
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
                      { title: 'Cus.Order', name: 'customerOrder', width: width, sortable:"true", sortName: 'customerOrder'},
                      { title: 'Shipping Date', name: 'shippingDate', width: 110, sortable:"true", sortName: 'shippingDate'},
                      { title: 'Delivery Date', name: 'deliveryDate', width: 110, },
                      { title: 'User', name: 'userName', width: 70, sortable:"true", sortName: 'userName'},                     
                      { title: 'Manufacture Date', name: 'manufactureDate', width: 130, sortable:"true", sortName: 'manufactureDate'},
                      { title: 'Expiry Date', name: 'expiryDate', width: 120, sortable:"true", sortName: 'expiryDate'},
                      { title: 'Delay T.', name: 'shippingDelayTime', width: 75, render: function (rowdata, name, index){
	                    	  if(rowdata[name] != "" && rowdata[name].trim() != "-")
   	                   		  {
    	                    	 var h = "<div style='background-color:red;color:#fff'>"+rowdata[name]+"</div>";
   	                   		 	 return h;
   	                   		  }
   	                    	  else{
   	                    		  return rowdata[name];
   	                    	  } 
   	                      }}
	                ]
	         }).on({
	                   'add': function(){
	                       self.add($(this));
	                   },
	                   'modify': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行修改'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行修改'
	                            })
	                            return;
	                        }
	                       self.modify(indexs[0], $this);
	                    },
	                   'delete': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                   type: 'warning',
	                                   content: '请选择要删除的记录'
	                            });
	                            return;
	                        }
	                        var remove = function(){
	                            self.remove(indexs, $this);
	                        };
	                        $this.confirm({
	                            content: '确定要删除所选记录吗?',
	                            callBack: remove
	                        });
	                   }
	         });
	    },
	    add: function(grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
	        	+'</div></div>');
	        $.get('<%=path%>/WaferFeed-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            dialog.find("#firstPQtyID,#secondPQtyID,#thirdPQtyID").bind('change',function(){
	            	var one=0;var two=0;var three=0;
	            	if($("#firstPQtyID").val()!="")
            		{
            			one=parseInt($("#firstPQtyID").val());
            		}
	            	if($("#secondPQtyID").val()!="")
            		{
	            		two=parseInt($("#secondPQtyID").val());
            		}
	            	if($("#thirdPQtyID").val()!="")
            		{
	            		three=parseInt($("#thirdPQtyID").val());
            		}
	            	debugger;
		        	$("#qtyID").val(one+two+three);
		        
	            });
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
	              if(!Validator.Validate(dialog.find('form')[0],3))return;
	              debugger;
              		var rs=requestParamToJson(dialog.find('form').serializeArray());
              		//rs['shippingDate']=rs['shippingDate']+":00.000%2B0800";
              		var restring=JSON.stringify(rs);
	              $.post('${pageContext.request.contextPath}/WaferCustomerLot/add.koala',"data=" + restring).done(function(result){
	            	  	debugger;
	            	  	messagecheck(e,result,dialog);
	              });
	        });
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/WaferFeed-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get('${pageContext.request.contextPath}/WaferCustomerLot/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                       debugger;
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
	                                elm.setValue(json[index]);
	                            }else{
	                                elm.val(json[index]);
	                            }
	                        }
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
	                    //rs['shippingDate']=rs['shippingDate'].replace(" ","T")+":00.000%2B0800";
	                    rs['id']=id;
		          		var restring=JSON.stringify(rs);
		          		debugger;
	                    $.post('${pageContext.request.contextPath}/WaferCustomerLot/update.koala', "data="+restring).done(function(result){
	                    	debugger;
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
	    	var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">删除</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('pages/inOut/UserInformation.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               dialog.find(".form-horizontal").append('<div class="form-group"><label class="col-lg-3 control-label">Comments:</label><div class="col-lg-9"><input name="comments" style="display:inline; width:94%;" class="form-control"  type="text"  id="commentsID" dataType="Require"/><span class="required">*</span></div>');
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
				    	var data = [{ name: 'ids', value: ids.join(',') },{ name: 'userName', value: rs['userName'] },{ name: 'password', value: rs['password'] },{ name: 'comments', value: rs['comments'] }];
				    	debugger;
				    	$.post('${pageContext.request.contextPath}/WaferCustomerLot/delete.koala', data).done(function(result){
				    		debugger;
				    		messagecheck(e,result,dialog);
				    	});
	                });
	        });
	    }
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
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
            delete params['currStatusWaiting'];
            delete params['currStatusPass'];
            delete params['currStatusHold'];
            delete params['currStatusPTV'];
            debugger;
            grid.getGrid().search(params);
            params['page']=0;
            params['page']=1000;
            $.ajax({
    			type : "POST",
    			url : "${pageContext.request.contextPath}/WaferCustomerLot/pageTotal.koala",
    			data : params,
    		}).done(function(result) {
    			debugger;
    			grid.next().children().children("#God_qtyTotle").text("Wafer #:"+result['waferTotal']+" | Qty:"+result['dieTotal']+" | 1st P.Qty:"+result['firstPQty']+" | 2nd P.Qty:"+result['secondPQty']+" | 3rd P.Qty:"+result['thirdPQty']);
    			grid.next().children().children("#God_batchTotle").text(result['countLot']);
    		});
        });
});

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/WaferFeed-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/WaferCustomerLot/get/' + id + '.koala').done(function(json){
                       json = json.data;
                        var elm;
                        for(var index in json){
                           dialog.find('#'+ index + 'ID').html(json[index]);
                        }
               });
                dialog.modal({
                    keyboard:false
                }).on({
                    'hidden.bs.modal': function(){
                        $(this).remove();
                    }
                });
        });
}
</script>
</head>
<body>
<div style="width:98%;margin-right:auto; margin-left:auto; padding-top: 15px;">
<!-- search form -->
<form name=<%=formId%> id=<%=formId%> target="_self" class="form-horizontal">

<a style="position: absolute;right: 0;margin-right: 3%;"class="btn btn-primary" href="/pages/core/Equipment-list.jsp" target="_blank" >设备状态查询维护</a>	
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    	<div class="form-group God_Status">
    		<label class="control-label" style="width:100px;float:left;text-align:left;padding-left:6%;">Status:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            	<input name=currStatusWating  type="checkbox" value="Waiting" id="currStatusWaitingID"  />
            	<span>Waiting</span>
            	<input name=currStatusHold  type="checkbox" value="Hold"  id="currStatusHoldID"  />
            	<span>Hold</span>
            	<input name=currStatusReceived  type="checkbox" value="Received"  id="currStatusReceivedID"  />
            	<span>Received</span>
        	</div>
    	</div>
        <div class="form-group">
          <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:6%;">Cus Code:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name=customerCode class="form-control" type="text" style="width:180px;" id="customerCodeID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;">Cus.Lot No:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="customerLotNo" class="form-control" type="text" style="width:180px;" id="customerLotNoID"  />
        </div>
            </div>
            </td>
       <td style="vertical-align: bottom;"><button id="search" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;Search</button></td>
  </tr>
</table>
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
<div id="God_search"><div style="display: inline-block;width: 300px;margin-left: 1%;"><span>批次统计：</span><span id="God_batchTotle"></span></div><div style="display: inline-block;margin-left: 5%;"><span>数量统计：</span><span id="God_qtyTotle"></span></div></div>
</div>
</body>
</html>

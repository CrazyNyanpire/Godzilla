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
	case "001":
		materialTypeId="1";  //生产材料
		break;
	case "002":
		materialTypeId="3";  //被动元件
		break;
	case "003":
		materialTypeId="4"; //消耗品
		break;
	case "004":
		materialTypeId="5";
		break;
	default:
		break;
		
}
var grid;
var form;
var _dialog;
$(function (){
	$.get('${pageContext.request.contextPath}/MaterialLot/pageTotal/'+materialTypeId+'.koala').done(function(result){
		debugger;
		grid.next().children().children("#God_qtyTotle").text("In Qty:"+result['dieTotal']+" | In Capa.:"+result['inCapacityTotal']+" | Balance:"+result['inCapacityTotal']);
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
	                url:"${pageContext.request.contextPath}/MaterialLot/pageJson/"+materialTypeId+".koala",
	                columns: [
	 							 { title: 'Material', name: 'materialName', width: width},
 	                         { title: 'Part N.', name: 'partId', width: 110},
 	                         { title: 'Partname_cn', name: 'partNameCN', width: 110},
 	                         { title: 'Batch N.', name: 'batchNo', width: 90},
 	                         { title: 'InQty', name: 'qty', width: 50},
 	                         { title: 'In Capa.', name: 'inCapacity', width: 65},
 	                         { title: 'Balance', name: 'inCapacity', width: 65},
 	                         { title: 'Unit', name: 'unit', width: 50},
 	                        { title: 'Curr.Status', name: 'currStatus', width:90, render: function (rowdata, name, index){
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
 	                         { title: 'Vender', name: 'venderName', width: 80},
 	                         { title: 'Po N.', name: 'pon', width: 90},
 	                         { title: 'Shipping Date', name: 'shippingDate', width: 100},
 	                         //{ title: 'Shipping Time', name: 'shippingTime', width: 100},
 	                         { title: 'User', name: 'userName', width: 70},
 	                        { title: 'Delivery Date', name: 'deliveryDate', width: 100},
 	                      	 { title: 'Manufacture Date', name: 'productionDate', width: 120},
	                         { title: 'Expiry Date', name: 'guaranteePeriod', width: 110},                         
 	                         { title: 'Delay T.', name: 'shippingDelayTime', width: 70, render: function (rowdata, name, index){
 	                        	 debugger;
 		                    	  if(rowdata[name] != ""&& rowdata[name].trim() != "-")
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
	        $.get('<%=path%>/DirectMaterialFeed-add.jsp').done(function(html){
	            dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	            self.initPage(dialog.find('form'));
	            dialog.find('#materialIdID .dropdown-menu').bind("click",function(){
	        		var typearray=$("#materialIdID input").val();
	        		$.ajax({
	        			async:false,
	        			url: '${pageContext.request.contextPath}/Material/getByMaterialNameId/'+typearray+'.koala',
	        			type: 'POST',
	        			dataType: 'json',
	        		})
	        		.done(function(msg) {
	        			debugger;
	        			var contents = [{title:'请选择', value: ''}];
	        			for (var i=0;i<msg['data'].length;i++)
	        			{
	        				contents.push({title:msg['data'][i]['partId'] , value: msg['data'][i]['id']});
	        			}
	        			dialog.find("#partIdID").data('koala.select',null);
	        			var partIdobj=dialog.find('#partIdID');
	        			partIdobj['context']=partIdobj[0];
	        			partIdobj.select({
	                        title: '请选择',
	                        contents: contents
	                    }).on('change', function(){
	                    	dialog.find('#partIdID_').val($(this).getValue());
	                    });
	        			dialog.find('#partIdID .dropdown-menu').bind("click",function(){
	    	        		var typearray=$("#partIdID input").val();
	    	        		$.ajax({
	    	        			async:false,
	    	        			url: '${pageContext.request.contextPath}/Material/get/'+typearray+'.koala',
	    	        			type: 'POST',
	    	        			dataType: 'json',
	    	        		})
	    	        		.done(function(msg) {
	    	        			debugger;
	    						dialog.find('#partNameCNID').val(msg['data']['partNameCN']);
	    						dialog.find('#unitID').val(msg['data']['unit']);
	    	        		});
	    	    		});
	        		});
	    		});
		        
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
	              if(!Validator.Validate(dialog.find('form')[0],3))return;
	              var rs=requestParamToJson(dialog.find('form').serializeArray());
                  //rs['shippingDate']=rs['shippingDate']+":00.000%2B0800";
	          	  var restring=JSON.stringify(rs);
	          	  debugger;
	              $.post('${pageContext.request.contextPath}/MaterialLot/add/'+materialTypeId+'.koala', "data="+restring).done(function(result){
	            	  messagecheck(e,result,dialog);
	              });
	        });
	        
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/DirectMaterialFeed-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/MaterialLot/get/' + id + '.koala').done(function(json){
	                       json = json.data;
	                       //第二选项框赋值
	                       debugger;
	                       var typearray=json['materialNameId'];
	                       $.ajax({
        	        			async:false,
        	        			url: '${pageContext.request.contextPath}/Material/getByMaterialNameId/'+typearray+'.koala',
        	        			type: 'POST',
        	        			dataType: 'json',
        	        		})
        	        		.done(function(msg) {
        	        			var contents = [{title:'请选择', value: ''}];
        	        			for (var i=0;i<msg['data'].length;i++)
        	        			{
        	        				contents.push({title:msg['data'][i]['partId'] , value: msg['data'][i]['id']});
        	        			}
        	        			dialog.find("#partIdID").data('koala.select',null);
        	        			var partIdobj=dialog.find('#partIdID');
        	        			partIdobj['context']=partIdobj[0];
        	        			partIdobj.select({
        	                        title: '请选择',
        	                        contents: contents
        	                    }).on('change', function(){
        	                    	dialog.find('#partIdID_').val($(this).getValue());
        	                    });
        	        		});
	                       
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
		                            if(index=="partId"){
		                            	elm.setValue(json['materialId']);
		                            }
		                            else if(index=="materialId"){
		                            	elm.setValue(json['materialNameId']);
		                            }
		                            else{
		                            	elm.setValue(json[index]);
		                            }
	                            }else{
	                                elm.val(json[index]);
	                            }
	                        }
	                        dialog.find('#materialIdID .dropdown-menu').bind("click",function(){
	        	        		var typearray=$("#materialIdID input").val();
	        	        		$.ajax({
	        	        			async:false,
	        	        			url: '${pageContext.request.contextPath}/Material/getByMaterialNameId/'+typearray+'.koala',
	        	        			type: 'POST',
	        	        			dataType: 'json',
	        	        		})
	        	        		.done(function(msg) {
	        	        			debugger;
	        	        			var contents = [{title:'请选择', value: ''}];
	        	        			for (var i=0;i<msg['data'].length;i++)
	        	        			{
	        	        				contents.push({title:msg['data'][i]['partId'] , value: msg['data'][i]['id']});
	        	        			}
	        	        			dialog.find("#partIdID").data('koala.select',null);
	        	        			var partIdobj=dialog.find('#partIdID');
	        	        			partIdobj['context']=partIdobj[0];
	        	        			partIdobj.select({
	        	                        title: '请选择',
	        	                        contents: contents
	        	                    }).on('change', function(){
	        	                    	dialog.find('#partIdID_').val($(this).getValue());
	        	                    });
	        	        			dialog.find('#partIdID .dropdown-menu').bind("click",function(){
	        	    	        		var typearray=$("#partIdID input").val();
	        	    	        		$.ajax({
	        	    	        			async:false,
	        	    	        			url: '${pageContext.request.contextPath}/Material/get/'+typearray+'.koala',
	        	    	        			type: 'POST',
	        	    	        			dataType: 'json',
	        	    	        		})
	        	    	        		.done(function(msg) {
	        	    	        			debugger;
	        	    						dialog.find('#partNameCNID').val(msg['data']['partNameCN']);
	        	    						dialog.find('#unitID').val(msg['data']['unit']);
	        	    	        		});
	        	    	    		});
	        	        		});
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
	                    $.post('${pageContext.request.contextPath}/MaterialLot/update/'+materialTypeId+'.koala?id='+id, "data="+restring).done(function(result){
	                    	messagecheck(e,result,dialog);
	                    });
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
				    	$.post('${pageContext.request.contextPath}/MaterialLot/delete.koala', data).done(function(result){
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
        debugger;
        grid.getGrid().search(params);
        params['page']=0;
        params['page']=1000;
        $.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/MaterialLot/pageTotal/"+materialTypeId+".koala",
			data : params,
		}).done(function(result) {
			debugger;
			grid.next().children().children("#God_qtyTotle").text("In Qty:"+result['dieTotal']+" | In Capa.:"+result['inCapacityTotal']+" | Balance:"+result['inCapacityTotal']);
			grid.next().children().children("#God_batchTotle").text(result['countLot']);
		});
    });
});

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/DirectMaterialFeed-view.jsp').done(function(html){
               dialog.find('.modal-body').html(html);
               $.get( '${pageContext.request.contextPath}/MaterialLot/get/' + id + '.koala').done(function(json){
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
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<a style="position: absolute;right: 0;margin-right: 3%;"class="btn btn-primary" href="/pages/core/Equipment-list.jsp" target="_blank" >设备状态查询维护</a>
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
       	<div class="form-group God_Status">
    		<label class="control-label" style="width:100px;float:left;text-align:left;padding-left:5%;">Status:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            	<input name=currStatusWating  type="checkbox" value="Waiting" style="margin-top:7px;" id="currStatusWaitingID"  />
            	<span>Waiting</span>
            	<input name=currStatusHold  type="checkbox" value="Hold" style="margin-top:7px;"  id="currStatusHoldID"  />
            	<span>Hold</span>
            	<input name=currStatusReceived  type="checkbox" value="Received" style="margin-top:7px;"  id="currStatusReceivedID"  />
            	<span>Received</span>
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
       <td style="vertical-align: bottom;"><button id="search" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;Search</button></td>
  </tr>
</table>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
<div id="God_search"><div style="display: inline-block;width: 300px;margin-left: 1%;"><span>批次统计：</span><span id="God_batchTotle"></span></div><div style="display: inline-block;width: 400px;margin-left: 5%;"><span>数量统计：</span><span id="God_qtyTotle"></span></div></div>
</div>
</body>
</html>

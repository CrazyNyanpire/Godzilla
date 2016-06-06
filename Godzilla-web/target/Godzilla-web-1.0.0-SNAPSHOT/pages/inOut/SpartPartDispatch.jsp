<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>制工具及备件发料</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
var materialTypeId=location.search.split("=")[1];
var grid;
var form;
var _dialog;
function requestParamToJson(paramArray)
{
	var jsonObj={};
	$(paramArray).each(function()
		{
			jsonObj[this.name]=this.value;
		});
	console.log(jsonObj);
	return jsonObj;
}
function statuscheck(id){
	if($("#currStatus"+id).text()!="Pass")
	{
    	$('body').message({
            type: 'error',
            content: '只有Pass状态的批次才能执行该操作'
            });
    	return true;
	}
}
$(function (){
	$.get('${pageContext.request.contextPath}/SpartPartProcess/pageTotal.koala?stationId=107').done(function(result){
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
	         var width = 90;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-log-out"><span style="margin-left:5px;">发料</button>', action: 'dispatch'},
	                        //{content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-minus-sign"><span style="margin-left:5px;">分批</button>', action: 'Split'},
	                        //{content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-plus-sign"><span style="margin-left:5px;">合批</button>', action: 'Merge'}
	                    ],
	                url:"${pageContext.request.contextPath}/SpartPartProcess/Process/pageJson/107.koala",
	                columns: [
	                         	 { title: 'Part Name', name: 'partName', width: width},
		               			 { title: 'Part ID', name: 'partId', width: width},
       	                         { title: 'Part Number', name: 'partNumber', width: width},
       	                         { title: 'Description', name: 'description', width: width},
       	                         { title: 'Product', name: 'product', width: width},
       	                         { title: 'Qty', name: 'qty', width: width},
       	                         { title: 'Station', name: 'station', width: width},
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
      	                    	  { title: 'Vender', name: 'vender', width: width},
	                    	 	  { title: 'Po.N', name: 'pon', width: width},
			                      { title: 'Entry Date', name: 'entryDate', width: 80},
			                      { title: 'Entry Time', name: 'entryTime', width: 80},
								  { title: 'Manufacture Date', name: 'productionDate', width: 115},
	   	                          { title: 'User', name: 'userName', width: width},
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
			                      { title: 'Stock Pos.', name: 'stockPos', width: 90}
	                ]
	         }).on({
	                   'dispatch': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行发料'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行发料'
	                            })
	                            return;
	                        }
	                       self.dispatch(indexs[0], $this);
	                    },
	                    'Split': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行分批'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行分批'
	                            })
	                            return;
	                        }
	                       if(statuscheck(indexs[0]))return;
	                       self.Split(indexs[0], $this);
	                    },
	                   'Merge': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length != 2){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择两条记录进行合批'
	                            })
	                            return;
	                        }
	                        if($("#currStatus"+indexs[0]).text()!="Pass"||$("#currStatus"+indexs[1]).text()!="Pass")
	                    	{
	                        	$('body').message({
	                                type: 'error',
	                                content: '只有Pass状态的批次才能执行该操作'
	                                });
	                        	return true;
	                    	}
	                       self.Merge(indexs, $this);
	                    }
	         });
	    },
	    dispatch: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">发料</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/DirectMaterialDispatchForm.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SpartPartProcess/get/' + id + '.koala').done(function(json){
	            	   debugger;
                       json = json.data;
                        var elm;
                        for(var index in json){
                            elm = dialog.find('#'+ index + 'ID');
                            if(elm.hasClass('select')){
                                elm.setValue(json[index]);
                            }else{
                                elm.text(json[index]);
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
	                    rs['id']=id;
	  	          		var restring=JSON.stringify(rs);
	  	          		debugger;
	                    $.post('${pageContext.request.contextPath}/SpartPartProcess/Process/nextProcess/' + id + '.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
	                            });
	                        }
	                        else if(result.result == 'fail'){
	                        	dialog.find('.modal-content').message({
		                            type: 'error',
		                            content: '用户名或密码错误'
		                            });
	                        }else{
	                            dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                            });
	                        }
	                    });
	                });
	        });
	    },
	    Split: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">分批</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal" id="God_cancel_split">取消</button><button type="button" class="btn btn-success" id="God_next_split">下一步</button><button type="button" class="btn btn-default" style="display:none" id="God_prev_split">上一步</button><button type="button" class="btn btn-success" style="display:none;" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/DirectMaterialSplit.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SpartPartProcess/get/' + id + '.koala').done(function(json){
	            	   debugger;
                       json = json.data;
                        var elm;
                        for(var index in json){
                            elm = dialog.find('#'+ index + 'ID');
                            if(elm.hasClass('select')){
                                elm.setValue(json[index]);
                            }else{
                                elm.text(json[index]);
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
	                dialog.find('#God_next_split').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    var rs=requestParamToJson(dialog.find('form').serializeArray());
	                    var inobj={};
	                    inobj['id']=id;
	                    inobj['materialQty']=rs['materialQty'];
	                    inobj['comments']=rs['comments'];
	                    inobj['inCapacity']=rs['inCapacity'];
	                    inobj['locationIds']=rs['locationIds'];
	                    rs['data']=inobj;
	                    delete rs['comments'];
	                    delete rs['materialQty'];
	                    delete rs['locationIds'];
	                    debugger;
	  	          		var restring=JSON.stringify(rs);
   	                    $.post( '${pageContext.request.contextPath}/SpartPartProcess/Process/getSplit.koala',"data="+restring).done(function(json){
       	            	   debugger;
       	            	   $(".God_split_hide").show();
                              json = json.splitDate;
                               var elm;
                               for(var i=0;i<json.length;i++)
                           	{
   	                        	for(var index in json[i]){
   	                                elm = dialog.find('#'+ index + 'ID'+i);
   	                                if(elm.hasClass('select')){
   	                                    elm.setValue(json[i][index]);
   	                                }else{
   	                                    elm.text(json[i][index]);
   	                                }
   	                            }                    	
                           	}
                       	});
   	                    $("#God_cancel_split").css("display","none");
   	                    $("#save").css("display","initial");
   	                    $("#God_next_split").css("display","none");
   	                    $("#God_prev_split").css("display","initial");
	                    });
	                dialog.find('#God_prev_split').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    $(".God_split_hide").css("display","none");
	                    $("#God_cancel_split").css("display","initial");
	                    $("#save").css("display","none");
	                    $("#God_next_split").css("display","initial");
	                    $("#God_prev_split").css("display","none");
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    var rs=requestParamToJson(dialog.find('form').serializeArray());
	                    var inobj={};
	                    inobj['id']=id;
	                    inobj['materialQty']=rs['materialQty'];
	                    inobj['inCapacity']=rs['inCapacity'];
	                    inobj['comments']=rs['comments'];
	                    inobj['locationIds']=rs['locationIds'];
	                    rs['data']=inobj;
	                    delete rs['comments'];
	                    delete rs['materialQty'];
	                    delete rs['locationIds'];
	                    debugger;
	  	          		var restring=JSON.stringify(rs);
	                    $.post('${pageContext.request.contextPath}/SpartPartProcess/Process/split.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '分批成功'
	                            });
	                        }
	                        else if(result.result == 'fail'){
	                        	dialog.find('.modal-content').message({
		                            type: 'error',
		                            content: '用户名或密码错误'
		                            });
	                        } 	
	                        else{
	                            dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                            });
	                        }
	                    });
	                });
	        });
	    },
	    Merge: function(ids, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">合批</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/DirectMaterialMerge.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               debugger;
	               $.post( '${pageContext.request.contextPath}/SpartPartProcess/Process/getProcess.koala',"ids="+ids.join(',')).done(function(json){
	            	   debugger;
	            	   if(json.mergeData===null)
            		   {
	            		   $('body').message({
	                            type: 'error',
	                            content: '批次信息不一致无法合批'
	                            });
	            		   dialog.find('#save').hide();
	            		   return;
            		   }
                       json1 = json.data;
                       var elm;
                       for(var i=0;i<json1.length;i++)
                       	{
	                        	for(var index in json1[i]){
	                                elm = dialog.find('#'+ index + 'ID'+i);
	                                if(elm.hasClass('select')){
	                                    elm.setValue(json1[i][index]);
	                                }else{
	                                    elm.text(json1[i][index]);
	                                }
	                            }                    	
                       	}
                        json = json.mergeData;
                       	for(var index in json){
                               elm = dialog.find('#'+ index + 'ID2');
                               if(elm.hasClass('select')){
                                   elm.setValue(json[index]);
                               }else{
                                   elm.text(json[index]);
                               }
                           }  
                       	dialog.modal({
    	                    keyboard:false
    	                }).on({
    	                    'hidden.bs.modal': function(){
    	                        $(this).remove();
    	                    }
    	                });
                	});
	                
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    var rs=requestParamToJson(dialog.find('form').serializeArray());
	                    rs['ids']=ids.join(',');
	  	          		var restring=JSON.stringify(rs);
	                    $.post('${pageContext.request.contextPath}/SpartPartProcess/Process/merge.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '合批成功'
	                            });
	                        }
	                        else if(result.result == 'fail'){
	                        	dialog.find('.modal-content').message({
		                            type: 'error',
		                            content: '用户名或密码错误'
		                            });
	                        }else{
	                            dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                            });
	                        }
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
	    }
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
	grid.getGrid().searchCondition['currStatus']="'Pass'";
	form.find('#search').on('click', function(){
            if(!Validator.Validate(document.getElementById("<%=formId%>"),3))return;
            var params = {};
            form.find('input').each(function(){
                var $this = $(this);
                var name = $this.attr('name');
                if(name){
                    params[name] = $this.val();
                }
            });
            grid.getGrid().search(params);
            params['page']=0;
            params['page']=1000;
            params['stationId']=materialTypeId;
            $.ajax({
    			type : "POST",
    			url : "${pageContext.request.contextPath}/SpartPartProcess/pageTotal?stationId=107.koala",
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
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
     <div class="form-group God_Status">
          <div class="form-group">
            <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:4%;">Part Nme:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="partName" class="form-control" type="text" style="width:180px;" id="partNameID" />
        </div>
            <label class="control-label" style="width:100px;float:left;">Part ID:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="partId" class="form-control" type="text" style="width:180px;" id="partIdID"  />
        </div>
            <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:2%;">Part Number:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="partNumber" class="form-control" type="text" style="width:180px;" id="partNumberID"  />
        </div>
            </div>
        <div class="form-group">
            <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:4%;">Product:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="product" class="form-control" type="text" style="width:180px;" id="productID" />
        </div>
            <label class="control-label" style="width:100px;float:left;">Station:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="station" class="form-control" type="text" style="width:180px;" id="stationID"  />
        </div>
            <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:2%;">Vender:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="vender" class="form-control" type="text" style="width:180px;" id="venderID"  />
        </div>
            </div>
            </div>
   </td>
       <td style="vertical-align: bottom;"><button id="search" type="button" style="top: 58px;" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;Search</button></td>
  </tr>
</table>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
<div id="God_search"><div style="display: inline-block;width: 300px;margin-left: 1%;"><span>批次统计：</span><span id="God_batchTotle"></span></div><div style="display: inline-block;width: 400px;margin-left: 5%;"><span>数量统计：</span><span id="God_qtyTotle"></span></div></div>
</div>
</body>
</html>
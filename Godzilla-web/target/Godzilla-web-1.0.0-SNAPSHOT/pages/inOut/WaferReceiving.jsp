<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>晶圆收料</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
var locationobj;
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
$.ajax({
	async:false,
	url: '${pageContext.request.contextPath}/Location/all.koala',
	type: 'POST',
	dataType: 'json',
})
.done(function(msg) {
	locationobj=msg;
});
$(function (){
	$.get('${pageContext.request.contextPath}/WaferCustomerLot/pageTotal.koala').done(function(result){
		debugger;
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
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-log-in"><span>收料</button>', action: 'Receiving'}
	                    ],
	                url:"${pageContext.request.contextPath}/WaferCustomerLot/pageJson.koala",
	                columns: [
	                  { title: 'Cus.Code', name: 'customerCode', width: width},
	                  { title: 'Lot N.', name: 'LotNo', width: 100},
                      { title: 'Cus.Lot N.', name: 'customerLotNo', width: 90},
                      { title: 'Product Name', name: 'productName', width: width},
                      { title: 'Part Name', name: 'partNumber', width: 90},
                      { title: 'Qty', name: 'qty', width: 50},
                      { title: 'Wafer #', name: 'wafer', width: 60},
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
                      { title: 'Cust Order', name: 'customerOrder', width: width},
                      { title: 'Shipping Date', name: 'shippingDate', width: width},
                      { title: 'Shipping Time', name: 'shippingTime', width: width},
                      { title: 'Manufacture Date', name: 'productionDate', width: 115},
                      { title: 'Expiry Date', name: 'guaranteePeriod', width: 90},
                      { title: 'User', name: 'userName', width: 70},
                      { title: 'Curr.Status', name: 'currStatus', width: 80, render: function (rowdata, name, index){
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
                      
                      { title: 'Delay T.', name: 'shippingDelayTime', width: 70, render: function (rowdata, name, index){
                    	  if(rowdata[name] != "" && rowdata[name].trim() != "-")
	                   		  {
	                    		 var h = "<div style='background-color:red;color:#fff'>"+rowdata[name]+"</div>";
	                   		 	 return h;
	                   		  }
	                    	  else{
	                    		  return rowdata[name];
	                    	  } 
	                      }},
                      { title: 'StockPos', name: 'stockPos', width: 110, render: function (rowdata, name, index){
                    	  	var locationstr="";
                    	  	for(var i=0;i<locationobj['data'].length;i++)
                    	  		{
                    	  			locationstr+="<option value='"+locationobj['data'][i]['id']+"'>"+locationobj['data'][i]['loctionCode']+"</option>";
                    	  		}
							var h = "<select id='kucun"+rowdata.id+"' name='kucun'><option value=''>请选择</option>"+locationstr+"</select>";
							if(rowdata['currStatus']=="Received")
							{
								h="";
							}
                            return h;
                          }
                      }
	                ]
	         }).on({
	                   'Receiving': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                   type: 'warning',
	                                   content: '请选择要收料的记录'
	                            });
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行收料'
	                            })
	                            return;
	                        }
	                        self.Receiving(indexs, $this);
	                   }
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
	    Receiving: function(ids, grid){
	    	var self = this;
	        var dialog = $('<div class="modal fade"><div style="width:800px" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">收料</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/UserInformation.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               debugger;
	               for(var i=1;i<=$("div[data-value='"+ids+"']").parent().parent().children("td[index='6']").text();i++)
	            	   {
	            	   		dialog.find('#God_waferadd').append('<div class="form-group"><label class="col-lg-3 control-label">Wafer'+i+':</label><div class="col-lg-5"><input name="Wafer'+i+'" style="display:inline; width:94%;" class="form-control" Placeholder="Input WaferId..." type="text"  id="Wafer'+i+'ID" dataType="Require"/></div><div class="col-lg-3"><input name="WaferQty'+i+'" style="display:inline; width:94%;" class="form-control" Placeholder="Input WaferQty..." type="text"  id="WaferQty'+i+'ID" dataType="Require"/></div>');
	            	   }
	                self.initPage(dialog.find('form'));
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
				    	var array=[];
				    	var waferinfo=[];
				    	//获取WaferID及waferqty信息拼装array
			               for(var i=1;i<=$("div[data-value='"+ids+"']").parent().parent().children("td[index='6']").text();i++)
		            	   {
			            	   var waferinfojson={};
			            	   waferinfojson['number']=i;
			            	   waferinfojson['wafer']=rs['Wafer'+i];
			            	   waferinfojson['waferQty']=rs['WaferQty'+i];
			            	   delete rs['Wafer'+i];
			            	   delete rs['WaferQty'+i];
			            	   waferinfo.push(waferinfojson);
		            	   }
			               
				    	for(a in ids){
				    		var inobj={};
				    		if($("#kucun"+ids[a]).val()==''||$("#kucun"+ids[a]).val()==undefined)
				    			{
				    				$('body').message({
				    				//$(this).message({
			                            type: 'error',
			                            content: '库存为空时不能收料，请检查'
			                            });
				    				return false;
				    			}
				    		inobj['kucun']=$("#kucun"+ids[a]).val();
				    		inobj['id']=ids[a];
				    		inobj['lotno']=$("div[data-value='"+ids[a]+"']").parent().parent().children("td[index='1']").text();
				    		inobj['waferinfo']=waferinfo;
				    		array.push(inobj);
				    		}
	                    rs['data']=array;
	  	          		var restring=JSON.stringify(rs);
				    	debugger;
				    	$.post('${pageContext.request.contextPath}/WaferProcess/Receive/save.koala', "data=" + restring).done(function(result){
				    		messagecheck(e,result,dialog);
				    	});
		            });
	        });
	    }
	}
	PageLoader.initSearchPanel();
	PageLoader.initGridPanel();
	grid.getGrid().searchCondition['currStatus']="'Waiting'";
	$(".table").one("mouseover", function(){
		$("select[name='kucun']").on('change', function(e){
			var id=$(this)[0].id.replace(/[^0-9]/ig,'');
			$.get('${pageContext.request.contextPath}/WaferCompanyLot/getNewLotNoByCustomerLot/'+id+'.koala').done(function(json){
				debugger;
	          	$("div[data-value='"+id+"']").parent().parent().children("td[index='1']").text(json['data']['lotNo']);
			});
          });
	});
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
            $.ajax({
    			type : "POST",
    			url : "${pageContext.request.contextPath}/WaferProcess/pageTotal.koala",
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
<input type="hidden" name="page" value="0">
<input type="hidden" name="pagesize" value="10">
<table border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
        <div class="form-group">
          <label class="control-label" style="width:100px;float:left;">CusCode:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name=customerCode class="form-control" type="text" style="width:180px;" id="customerCodeID"  />
        </div>
                      <label class="control-label" style="width:100px;float:left;">Cus.Lot No:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="customerLotNo" class="form-control" type="text" style="width:180px;" id="customerLotNoID"  />
        </div>
            </div>
            </td>
       <td style="vertical-align: bottom;"><button id="search" type="button" style="top:13px" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>&nbsp;Search</button></td>
  </tr>
</table>	
</form>
<!-- grid -->
<div id=<%=gridId%>></div>
<div id="God_search"><div style="display: inline-block;width: 300px;margin-left: 1%;"><span>批次统计：</span><span id="God_batchTotle"></span></div><div style="display: inline-block;margin-left: 5%;"><span>数量统计：</span><span id="God_qtyTotle"></span></div></div>
</div>
</body>
</html>
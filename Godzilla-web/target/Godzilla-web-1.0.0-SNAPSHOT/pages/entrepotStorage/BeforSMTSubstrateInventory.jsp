<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>晶圆库存</title>
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
$(function (){
	/*
	grid改变绑定的单选框事件
	*/
	$("input[name='currStatus']").change(function() { 
		grid.unbind();
		var params={};
		if($(this).val()=="TotalWIP"){
			gridchange(grid);
			PageLoader.initGridPanel();
		}
		else if($(this).val()=="Waiting"){
			gridchange(grid);
			PageLoader.initGridPanel();
			grid.getGrid().searchCondition['currStatus']="'Waiting'";
			params['currStatus']="'Waiting'";
		}
		else if($(this).val()=="CusDisp"){
			gridchange(grid);
			PageLoader.initGridPanelone();
			grid.getGrid().searchCondition['currStatus']="'Cus. Disp'";
			params['currStatus']="'Cus. Disp'";
		}else if($(this).val()=="EngDisp"){
			gridchange(grid);
			PageLoader.initGridPanelone();
			grid.getGrid().searchCondition['currStatus']="'Eng. Disp'";
			params['currStatus']="'Eng. Disp'";
		}else{
			gridchange(grid);
			PageLoader.initGridPanelone();
		}
		params['page']=0;
        params['page']=1000;
        params['stationId']=AlphaId;
        $.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/SubstrateProcess/pageTotal.koala",
			data : params,
		}).done(function(result) {
			debugger;
			grid.next().children().children("#God_qtyTotle").text("Strip#:"+result['stripTotal']+" | Qty:"+result['dieTotal']);
			grid.next().children().children("#God_batchTotle").text(result['countLot']);
		});
		HoldReasonSearchSub();
	});
	$.get('${pageContext.request.contextPath}/SubstrateProcess/pageTotal.koala?stationId='+AlphaId).done(function(result){
		debugger;
		grid.next().children().children("#God_qtyTotle").text("Strip#:"+result['stripTotal']+" | Qty:"+result['dieTotal']);
		grid.next().children().children("#God_batchTotle").text(result['countLot']);
	});
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
	PageLoader = {
	   //
	    initSearchPanel:function(){
	    },
	    initGridPanelone: function(){
	         var self = this;
	         var width = 90;
	         return grid.grid({
	                identity:"id",
	                buttons: [
								{content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Release</button>', action: 'Release'},
		                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon "><span style="margin-left:5px;">Change<span class="God_span_nbsp"/>Lot<span class="God_span_nbsp"/>Type</button>', action: 'Change_Lot_Type'},
		                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Get<span class="God_span_nbsp"/>Traveler</button>', action: 'Get_Traveler'},
		                        //{content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Get<span class="God_span_nbsp"/>Bom</button>', action: 'Get_Bom'},		                        
		                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Lot<span class="God_span_nbsp"/>Summary</button>', action: 'Lot_Summary'},
	                    ],
	                //url:"${pageContext.request.contextPath}/WaferCompanyLot/pageJson.koala",
	                url:"${pageContext.request.contextPath}/SubstrateProcess/Process/pageJson/"+AlphaId+".koala",
	                columns: [
                     		  { title: 'Lot N.', name: 'lotNo', width: width},
		                      { title: 'Sup.Lot N.', name: 'customerLotNo', width: width},
		                      { title: 'Part N.', name: 'partNumber', width: 80},
   	                          { title: 'Batch N.', name: 'batchNo', width: width},
   	                          { title: 'Strip#', name: 'stripQtyIn', width: 60},
   	                          { title: 'Qty', name: 'qtyIn', width: 50},
		                      { title: 'Step', name: 'step', width: width},
		                      { title: 'Curr.Status', name: 'currStatus', width: width, render: function (rowdata, name, index){
		                    	  if(rowdata[name]=="Hold"||rowdata[name]=="Eng. Disp"||rowdata[name]=="Cus. Disp")
		                   		  {
		                   		 	 var h = "<div id='currStatus"+rowdata['id']+"' style='color: #fff;background-color:red'>"+rowdata[name]+"</div>";
		                   		 	 return h;
		                   		  }
		                    	  else{
		                    		  return "<div id='currStatus"+rowdata['id']+"'>"+rowdata[name]+"</div>";
		                    	  } 
		                      }},
		                      { title: 'Lot Type', name: 'lotType', width: 70},
		                      { title: 'Entry Date', name: 'entryDate', width: 80},
		                      { title: 'Entry Time', name: 'entryTime', width: 80},
		                      { title: 'Manufacture Date', name: 'productionDate', width: 115},
							  { title: 'Expiry Date', name: 'guaranteePeriod', width: 90},
		                      { title: 'User', name: 'userName', width: 70},		                      
		                      { title: 'Hold Reason', name: 'holdReason', width: 100},
		                      { title: 'Inv Loc.', name: 'stockPos', width: width}
                ]
	         }).on({
	                   'Change_Lot_Type': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行change'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行change'
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
	                       self.Change_Lot_Type(indexs[0], $this);
	                    },
	                    'Release': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行Release'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行Release'
	                            })
	                            return;
	                        }
	                        if($("#currStatus"+indexs[0]).text()=="Waiting")
	                    	{
		                    	$('body').message({
		                            type: 'error',
		                            content: '只有非Waiting状态的批次才能执行该方法'
		                            });
		                    	return false;
	                    	}
	                       self.Release(indexs[0], $this);
	                    },
	                    'Get_Traveler': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录'
	                            })
	                            return;
	                        }
	                        
	                        window.open("/pages/SubstrateRunCard.jsp?id="+indexs[0]);
	                    },
	                    'Get_Bom': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录'
	                            })
	                            return;
	                        }
	                        window.open("/pages/BomList.jsp?id="+indexs[0]);
	                    },
	                    'Lot_Summary': function(event, data){
	                    	var indexs = data.data;
	                    	if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行操作'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行操作'
	                            })
	                            return;
	                        }
	                    	window.open("/pages/SubstrateLotSummary.jsp?id="+indexs[0]+"&stationId=" + AlphaId); 
	                    }
	                   
	         });
	    },
	    initGridPanel: function(){
	         var self = this;
	         var width = 90;
	         return grid.grid({
	                identity:"id",
	                buttons: [
		                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon "><span style="margin-left:5px;">Change<span class="God_span_nbsp"/>Lot<span class="God_span_nbsp"/>Type</button>', action: 'Change_Lot_Type'},
		                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon "><span style="margin-left:5px;">Change<span class="God_span_nbsp"/>Loc.</button>', action: 'Change_Loc'},
		                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon "><span style="margin-left:5px;">Return</button>', action: 'Return'},
		                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Start</button>', action: 'Start'},
		                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon "><span style="margin-left:5px;">Cus<span class="God_span_nbsp"/>Hold</button>', action: 'Cus_Hold'},
		                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon "><span style="margin-left:5px;">Eng<span class="God_span_nbsp"/>Hold</button>', action: 'Eng_Hold'},
		                        //{content: '<button class="btn btn-danger" type="button"><span class="glyphicon "><span style="margin-left:5px;">Future<span class="God_span_nbsp"/>Hold</button>', action: 'Future_Hold'},
		                        //{content: '<button class="btn btn-primary" type="button"><span class="glyphicon "><span style="margin-left:5px;">Change<span class="God_span_nbsp"/>P_N</button>', action: 'Change_P_N'},
		                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon "><span style="margin-left:5px;">Split<span class="God_span_nbsp"/>Lot</button>', action: 'Split_Lot'},
		                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon "><span style="margin-left:5px;">Merge<span class="God_span_nbsp"/>Lot</button>', action: 'Merge_Lot'},
		                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Get<span class="God_span_nbsp"/>Traveler</button>', action: 'Get_Traveler'},
		                        //{content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Get<span class="God_span_nbsp"/>BOM</button>', action: 'Get_Bom'},		                        
		                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon "><span style="margin-left:5px;">Lot<span class="God_span_nbsp"/>Summary</button>', action: 'Lot_Summary'},
	                    ],
	                //url:"${pageContext.request.contextPath}/WaferCompanyLot/pageJson.koala",
	                url:"${pageContext.request.contextPath}/SubstrateProcess/Process/pageJson/"+AlphaId+".koala",
	                columns: [
                     		  { title: 'Lot N.', name: 'lotNo', width: width},
		                      { title: 'Sup.Lot N.', name: 'customerLotNo', width: width},
		                      { title: 'Part N.', name: 'partNumber', width: 80},
   	                          { title: 'Batch N.', name: 'batchNo', width: width},
   	                          { title: 'Strip#', name: 'stripQtyIn', width: 60},
   	                          { title: 'Qty', name: 'qtyIn', width: 50},
		                      { title: 'Step', name: 'step', width: width},
		                      { title: 'Curr.Status', name: 'currStatus', width: width, render: function (rowdata, name, index){
		                    	  if(rowdata[name]=="Hold"||rowdata[name]=="Eng. Disp"||rowdata[name]=="Cus. Disp")
		                   		  {
		                   		 	 var h = "<div id='currStatus"+rowdata['id']+"' style='color: #fff;background-color:red'>"+rowdata[name]+"</div>";
		                   		 	 return h;
		                   		  }
		                    	  else{
		                    		  return "<div id='currStatus"+rowdata['id']+"'>"+rowdata[name]+"</div>";
		                    	  } 
		                      }},
		                      { title: 'Lot Type', name: 'lotType', width: 70},
		                      { title: 'Entry Date', name: 'entryDate', width: 80},
		                      { title: 'Entry Time', name: 'entryTime', width: 80},
		                      { title: 'Manufacture Date', name: 'productionDate', width: 115},
							  { title: 'Expiry Date', name: 'guaranteePeriod', width: 90},
		                      { title: 'User', name: 'userName', width: 70},		                      
		                      { title: 'Inv Loc.', name: 'stockPos', width: width}
                ]
	         }).on({
	                   'Change_Lot_Type': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行change'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行change'
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
	                       self.Change_Lot_Type(indexs[0], $this);
	                    },
	                    'Change_Loc': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行change'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行change'
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
	                       self.Change_Loc(indexs[0], $this);
	                    },
	                    'Return': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行Rturn'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行Rturn'
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
	                       self.Return(indexs[0], $this);
	                    },
	                    'Start': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行start'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行start'
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
	                       self.Start(indexs[0], $this);
	                    },
	                    'Cus_Hold': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行hold'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行hold'
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
	                       self.Cus_Hold(indexs[0], $this);
	                    },
	                    'Eng_Hold': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行hold'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行hold'
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
	                       self.Eng_Hold(indexs[0], $this);
	                    },
	                    'Future_Hold': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行hold'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行hold'
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
	                       self.Future_Hold(indexs[0], $this);
	                    },
	                    'Change_P_N': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行change'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行change'
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
	                       self.Change_P_N(indexs[0], $this);
	                    },
	                    'Split_Lot': function(event, data){
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
	                        if($("#currStatus"+indexs[0]).text()!="Waiting")
	                    	{
		                    	$('body').message({
		                            type: 'error',
		                            content: '只有Waiting状态的批次才能执行该方法'
		                            });
		                    	return false;
	                    	}
	                       self.Split_Lot(indexs[0], $this);
	                    },
	                    'Merge_Lot': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length < 2){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择至少两条记录进行合批'
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
	                       self.Merge_Lot(indexs, $this);
	                    },
	                    'Get_Traveler': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录'
	                            })
	                            return;
	                        }
	                        
	                        window.open("/pages/SubstrateRunCard.jsp?id="+indexs[0]);
	                    },
	                    'Get_Bom': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录'
	                            })
	                            return;
	                        }
	                        window.open("/pages/BomList.jsp?id="+indexs[0]);
	                    },
	                    'Lot_Summary': function(event, data){
	                    	var indexs = data.data;
	                    	if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行操作'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行操作'
	                            })
	                            return;
	                        }
	                    	window.open("/pages/SubstrateLotSummary.jsp?id="+indexs[0]+"&stationId=" + AlphaId); 
	                    }
	                   
	         });
	    },
	    Change_Lot_Type: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Change Lot Type</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateChangeLotType.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               debugger;
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
                        dialog.find('#lotTypeIdID .dropdown-menu').bind("click",function(){
                    		var typearray=$("#lotTypeIdID input").val().split("|");
                    		$("#ActualLotTypeID").val(typearray[1]);
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
	                    var inobj={};
	                    inobj['id']=id;
	                    var Typearray=rs['lotTypeId'].split("|");
	                    inobj['lotTypeId']=Typearray[0];
	                    inobj['comments']=rs['comments'];
	                    delete rs['ActualLotType'];
	                    delete rs['lotTypeId'];
	                    delete rs['comments'];
	                    rs['data']=inobj;
	  	          		var restring=JSON.stringify(rs);
	  	          		debugger;
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/changeLotType.koala', "data="+restring).done(function(result){
	                    	messagecheck(e,result,dialog);
	                    });
	                });
	        });
	    },
	    Change_Loc: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Change Loc</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateChangeLoc.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    var inobj={};
	                    inobj['id']=id;
	                    inobj['locationIds']=rs['locationIds'];
	                    inobj['comments']=rs['comments'];
	                    delete rs['locationIds'];
	                    delete rs['comments'];
	                    rs['data']=inobj;
	  	          		var restring=JSON.stringify(rs);
	  	          		debugger;
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/changeLocations.koala', "data="+restring).done(function(result){
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
		                            content: "用户名或密码错误"
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
	    Release: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Release</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateRelease.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    var inobj={};
	                    inobj['id']=id;
	                    inobj['comments']=rs['comments'];
	                    delete rs['comments'];
	                    rs['data']=inobj;
	  	          		var restring=JSON.stringify(rs);
	  	          		debugger;
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/release.koala', "data="+restring).done(function(result){
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
		                            content: "用户名或密码错误"
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
	    Return: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 34%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Return</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('/pages/inOut/UserInformation.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               dialog.find('.form-horizontal').append('<div class="form-group"><label class="col-lg-3 control-label">Comments:</label><div class="col-lg-9"><textarea name="comments" style="display:inline; width:94%;" class="form-control"  type="text"  id="commentsID" dataType="Require"></textarea><span class="required">*</span></div></div>');

	               /*$.get( '${pageContext.request.contextPath}/MaterialProcess/get/' + id + '.koala').done(function(json){
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
                	});*/
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
	                    rs['nextStationId']='102';
	                    rs['currStatus']='Pass';
	  	          		var restring=JSON.stringify(rs);
	  	          		debugger;
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/nextProcess/'+id+'.koala', "data="+restring).done(function(result){
	                    	messagecheck(e,result,dialog);
	                    });
	                });
	        });
	    },
	    Start: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Start</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateStart.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                dialog.find('#waferLotID').on('change',{grid: grid}, function(e){
	                	debugger;
	                	$.post('${pageContext.request.contextPath}/WaferProcess/Process/getProductNameByWaferProcessId/' + dialog.find('#waferLotID input').val() + '.koala').done(function(result){
	                	dialog.find('#productNameID').val(result['data']);
	                	});
	                });
	                dialog.find('#productIdID .dropdown-menu').bind("click",function(){
    	        		var typearray=$("#productIdID input").val();
    	        		$.ajax({
    	        			async:false,
    	        			url: '${pageContext.request.contextPath}/Product/getPackByProductId/'+typearray+'.koala',
    	        			type: 'POST',
    	        			dataType: 'json',
    	        		})
    	        		.done(function(msg) {
    	        			debugger;
    	        			var contents = [{title:'请选择', value: ''}];
    	        			for (var i=0;i<msg['data'].length;i++)
    	        			{
    	        				contents.push({title:msg['data'][i]['packSize'] , value: msg['data'][i]['id']});
    	        			}
    	        			dialog.find("#packSizeID").data('koala.select',null);
    	        			var partIdobj=dialog.find('#packSizeID');
    	        			partIdobj['context']=partIdobj[0];
    	        			partIdobj.select({
    	                        title: '请选择',
    	                        contents: contents
    	                    }).on('change', function(){
    	                    	dialog.find('#packSizeID_').val($(this).getValue());
    	                    });
    	        			
    	        		});
    	    		});
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    var rs=requestParamToJson(dialog.find('form').serializeArray());
	                    var inliners={};
	                    inliners['id']=id;
	                    inliners['customerId']=rs['customerId'];
	                    inliners['productId']=rs['productId'];
	                    inliners['startFlow']=rs['startFlow'];
	                    inliners['packSizeId']=rs['packSizeId'];
	                    inliners['comments']=rs['comments'];
	                    rs['data']=inliners;
	                    delete rs['customerId'];
	                    delete rs['comments'];
	                    delete rs['productId'];
	                    delete rs['startFlow'];
	  	          		var restring=JSON.stringify(rs);
	  	          		debugger;
	                    $.post('${pageContext.request.contextPath}/ManufactureProcess/Process/start.koala', "data="+restring).done(function(result){
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
		                            content: "用户名或密码错误"
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
	    Cus_Hold: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Cus Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateCusHold.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    var inobj={};
	                    inobj['id']=id;
	                    inobj['holdCode']="";
	                    inobj['holdCodeId']=rs['holdCodeId'];
	                    inobj['comments']=rs['comments'];
	                    delete rs['holdCode'];
	                    delete rs['comments'];
	                    delete rs['holdCodeId'];
	                    rs['data']=inobj;
	  	          		var restring=JSON.stringify(rs);
	  	          		debugger;
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/hold.koala', "data="+restring).done(function(result){
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
		                            content: "用户名或密码错误"
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
	    Eng_Hold: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Eng Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateEngHold.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    var inobj={};
	                    inobj['id']=id;
	                    inobj['holdCode']="";
	                    inobj['holdCodeId']=rs['holdCodeId'];
	                    inobj['comments']=rs['comments'];
	                    delete rs['holdCode'];
	                    delete rs['comments'];
	                    delete rs['holdCodeId'];
	                    rs['data']=inobj;
	  	          		var restring=JSON.stringify(rs);
	  	          	    debugger;
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/hold.koala', "data="+restring).done(function(result){
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
		                            content: "用户名或密码错误"
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
	    Future_Hold: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Future Hold</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateFutureHold.jsp?id='+id).done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    var inobj={};
	                    inobj['id']=id;
	                    inobj['holdCode']="";
	                    inobj['holdCodeId']=rs['holdCodeId'];
	                    inobj['futureStationId']=rs['futureStationId'];
	                    inobj['comments']=rs['comments'];
	                    delete rs['holdCode'];
	                    delete rs['comments'];
	                    delete rs['futureStationId'];
	                    delete rs['holdCodeId'];
	                    rs['data']=inobj;
	  	          		var restring=JSON.stringify(rs);
	  	          	    debugger;
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/future.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
	                            });
	                        }else if(result.result == 'fail'){
	                            dialog.find('.modal-content').message({
		                            type: 'error',
		                            content: "用户名或密码错误"
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
	    Change_P_N: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Change P_N</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateChange_P_N.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/update.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
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
	    Split_Lot: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">分批</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal" id="God_cancel_split">取消</button><button type="button" class="btn btn-success" id="God_next_split">下一步</button><button type="button" class="btn btn-default" style="display:none" id="God_prev_split">上一步</button><button type="button" class="btn btn-success" style="display:none;" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateSplitLot.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    inobj['stripQty']=rs['stripQty'];
	                    inobj['qty']=rs['qty'];
	                    inobj['comments']=rs['comments'];
	                    inobj['locationIds']=rs['locationIds'];
	                    rs['data']=inobj;
	                    delete rs['comments'];
	                    delete rs['qty'];
	                    delete rs['stripQty'];
	                    delete rs['locationIds'];
	                    debugger;
	  	          		var restring=JSON.stringify(rs);
   	                    $.post( '${pageContext.request.contextPath}/SubstrateProcess/Process/getSplit.koala',"data="+restring).done(function(json){
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
	                    inobj['stripQty']=rs['stripQty'];
	                    inobj['qty']=rs['qty'];
	                    inobj['comments']=rs['comments'];
	                    inobj['locationIds']=rs['locationIds'];
	                    rs['data']=inobj;
	                    delete rs['comments'];
	                    delete rs['qty'];
	                    delete rs['stripQty'];
	                    delete rs['locationIds'];
	  	          		var restring=JSON.stringify(rs);
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/split.koala', "data="+restring).done(function(result){
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
	    Merge_Lot: function(ids, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">合批</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/SubstrateMergeLot.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               debugger;
	               $.post( '${pageContext.request.contextPath}/SubstrateProcess/Process/getProcesses.koala',"ids="+ids.join(',')).done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/Process/merge.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '合批成功'
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
	    Get_Traveler: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">发料</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/WaferGetTraveler.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/update.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
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
	    Get_Bom: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div style="width: 100%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">发料</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">提交</button></div></div></div></div>');
	        $.get('<%=path%>/WaferGetBom.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               self.initPage(dialog.find('form'));
	               $.get( '${pageContext.request.contextPath}/SubstrateProcess/get/' + id + '.koala').done(function(json){
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
	                    $.post('${pageContext.request.contextPath}/SubstrateProcess/update.koala', "data="+restring).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
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
	HoldReasonSearchSub();
	form.find('#search').on('click', function(){
        if(!Validator.Validate(document.getElementById("<%=formId%>"),3))return;
        var params = {};
        var status='';
        var type='';
        form.find('input').each(function(){
            var $this = $(this);
            if($this.attr("checked")=='checked')
            	{
            		if($this.attr("class")=='currstatus')
           			{
           				status+="'"+$this.val()+"',";
           			}
            		if($this.attr("class")=='type')
        			{
            			type+=$this.val()+",";
        			}
            		
            	}
            var name = $this.attr('name');
            if(name){
                params[name] = $this.val();
            }
        });
        status=status.substring(0,status.length-1);
        type=type.substring(0,type.length-1);
        //params['currStatus']=status;
        delete params['currStatus'];
        params['LotType']=type;
        debugger;
        grid.getGrid().search(params);
        params['page']=0;
        params['page']=1000;
        params['stationId']=AlphaId;
        $.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/SubstrateProcess/pageTotal.koala",
			data : params,
		}).done(function(result) {
			debugger;
			grid.next().children().children("#God_qtyTotle").text("Strip#:"+result['stripTotal']+" | Qty:"+result['dieTotal']);
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
    		<label class="control-label" style="width:100px;float:left;text-align:left;padding-left:6%;">Status:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            	<input name="currStatus" class="currstatus" type="radio" value="Waiting" id="currStatusWaitingID"  />
            	<span>Waiting</span>
            	<input name="currStatus" disabled class="currstatus" type="radio" value="OutSpec"  id="Out-SpecID"  />
            	<span>Out-Spec</span>
            	<input name="currStatus" class="currstatus" type="radio" value="CusDisp"  id="currStatusCusDispID"  />
            	<span>Cus.Disp</span>
            	<input name="currStatus" class="currstatus" type="radio" value="EngDisp"  id="currStatusEngDispID"  />
            	<span>Eng.Disp</span>
            	<input name="currStatus" class="currstatus" type="radio" value="TotalWIP"  id="currStatusTotalWIPID"  />
            	<span>Total WIP</span>
        	</div>
        	<label class="control-label" style="width:100px;float:left;">Type:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            	<input name=typeNP class="type" type="checkbox" value="822" id="typeNPID"  />
            	<span>NP</span>
            	<input name=typeEng class="type" type="checkbox" value="824"  id="typeEngID"  />
            	<span>Eng</span>
            	<input name=typeSWR class="type" type="checkbox" value="826"  id="typeSWRID"  />
            	<span>Cus.SWR</span>
            	<input name=typeISWR class="type" type="checkbox" value="828"  id="typeISWRID"  />
            	<span>Intern.SWR</span>            	
        	</div>
    	</div>
          <div class="form-group">
                      <label class="control-label" style="width:100px;float:left;text-align:left;padding-left:6%;">Lot No:&nbsp;</label>
            <div style="margin-left:15px;float:left;">
            <input name="lotNo" class="form-control" type="text" style="width:180px;" id="lotNoID" />
        </div>
                          <label class="control-label" style="width:100px;float:left;">Sup.Lot No:&nbsp;</label>
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
<div id="God_search"><div style="display: inline-block;width: 300px;margin-left: 1%;"><span>批次统计：</span><span id="God_batchTotle"></span></div><div style="display: inline-block;width: 300px;margin-left: 5%;"><span>数量统计：</span><span id="God_qtyTotle"></span></div></div>
</div>
</body>
</html>
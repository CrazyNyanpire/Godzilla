<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>LotSummary</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
</head>
<link rel="stylesheet" href="/css/summary.css">
<body>
<h3  style="text-align: center;">Lot Summary</h3>
	<div class="col-lg-12">
	<div style="margin:0 auto;width:90%">
        <label class="control-label">Lot N:</label>
            <div style="margin-left:15px;float:left;">
            <input name="lotNumber"  type="text" style="width:180px;" id="lotNumberID"  />
        	</div>
        <label class="control-label" style="width:100px">Product Name:</label>
            <div style="margin-left:15px;float:left;">
            <input name="productName"  type="text" style="width:180px;" id="productNameID"  />
        	</div>
        <label class="control-label">SubBatch:</label>
            <div style="margin-left:15px;float:left;">
            <input name="subBatch"  type="text" style="width:180px;" id="subBatchID"  />
        	</div>
		<br style="clear:both;"/>
	</div>
	</div>
<!-- 	<div class="col-lg-12">
	<div style="margin:0 auto;width:80%">
        <label class="control-label">Part N:</label>
            <div style="margin-left:15px;float:left;">
            <input name="partNumber"  type="text" style="width:180px;" id="partNumberID"  />
        	</div>
        <label class="control-label">Cus.Order:</label>
            <div style="margin-left:15px;float:left;">
            <input name="customerOrder" type="text" style="width:180px;" id="customerOrderID"  />
        	</div>
        <label class="control-label">Step:</label>
            <div style="margin-left:15px;float:left;">
            <input name="step" type="text" style="width:180px;" id="stepID"  />
        	</div>
		<br style="clear:both;"/>
	</div>
	</div> -->
<div class="form-group">
<div class="grid-body" style="width: 1290px;margin: 0 auto;">
<div>
	<table class="table table-bordered"  style="text-align: center;"><tbody>
	<tr class="tablehead">
		<th index="0" colspan="4"></th>
		<th index="1" colspan="3">Input</th>
		<th index="2" colspan="8">Output</th>
	</tr>
	<tr class="tablehead">
		<th index="0" width="106px">Step</th>
		<th index="1" width="20px">H</th>
		<th index="2" width="20px">S</th>
		<th index="3" width="20px">M</th>
		<th index="4" width="117px">In Date</th>
		<th index="5" width="117px">Track In</th>
		<th index="6" width="70px">In Qty</th>
		<th index="7" width="117px">Out Date</th>
		<th index="8" width="117px">Equip</th>
		<th index="9" width="70px">Out Qty</th>
		<th index="10" width="106px">Rej.Q</th>
		<th index="11" width="106px">C/T(h)</th>
		<th index="11" width="106px">UPH</th>
		<th index="11" width="60px">Yld(%)</th>
		<th index="11" width="auto">Mat.Cons.</th>
	</tr>
	</tbody>
	</table>
</div>
</div>
</div>
<script>
$(function() {
	var id="<%=request.getParameter("id")%>";
	var stationId=<%=request.getParameter("stationId")%>;
	var basicInfo={};
	$.ajax({
		async:false,
		url: '${pageContext.request.contextPath}/ManufactureProcess/get/' + id + '.koala',
		type: 'GET',
		dataType: 'json',
	}).done(function(msg) {
		debugger;
		basicInfo=msg['data'];
		$("input[name='lotNumber']").val(basicInfo['lotNo']);
		$("input[name='productName']").val(basicInfo['productName']);
		$("input[name='subBatch']").val(basicInfo['subBatch']);
	});
	$.ajax({
		async:false,
		url: '${pageContext.request.contextPath}/LotSummary/Manufacture/getLotSummary/' + id + '.koala',
		type: 'GET',
		dataType: 'json',
	}).done(function(msg) {
		//var msg={};
		//var str="{'SMT':[{'name':'基板烘烤','isHold':'1','isSplit':'1','isMerge':'1','isConsume':'1','inPut':{'inDate':'2014-10-30 14:01','TrackIn':'2014-10-30 14:05','InQty':'10'},'outPut':{'outDate':'2014-10-30 14:40','equip':'j750','outQty':'9','rejQty':'1'}},{'name':'锡膏印刷','isHold':'1','isSplit':'1','isMerge':'1','isConsume':'1','inPut':{'inDate':'2014-10-30 14:01','TrackIn':'2014-10-30 14:05','InQty':'1'},'outPut':{'outDate':'2014-11-02 15:03','equip':'j750','outQty':'1','rejQty':'1'}}],'Die Attach':[{'name':'D/A前电浆清洗','isHold':'1','isSplit':'1','isMerge':'1','isConsume':'1','inPut':{'inDate':'2014-10-30 14:01','TrackIn':'2014-10-30 14:05','InQty':'1'},'outPut':{'outDate':'2014-11-02 15:03','equip':'j750','outQty':'1','rejQty':'1'}},{'name':'D/A上片1','isHold':'1','isSplit':'1','isMerge':'1','isConsume':'1','inPut':{'inDate':'2014-10-30','TrackIn':'2014-10-30 14:05','InQty':'1'},'outPut':{'outDate':'2014-11-02','equip':'j750','outQty':'1','rejQty':'1'}}]}";
		//msg['data'] = eval('(' + str + ')');
		for(var a in msg['data'])
		{
			$(".table").append('<tr style="text-align:left"><td class="tableadd" colspan="15"><span class="glyphicon glyphicon-plus"></span>&nbsp;'+a+'</td></tr>');
			var i=1;
			for(var b in msg['data'][a])
				{
					var ct=((new Date(msg['data'][a][b]['outPut']['outDate'])-new Date(msg['data'][a][b]['inPut']['inDate']))/1000/60/60).toFixed(2);
					var uph=(msg['data'][a][b]['outPut']['outQty']/ct).toFixed(2);
					var yld=(((msg['data'][a][b]['inPut']['InQty']-msg['data'][a][b]['outPut']['rejQty'])/msg['data'][a][b]['inPut']['InQty'])*100).toFixed(2);
					if(yld=="NaN")
					{
						yld="100.00";
					}
					$(".table").append('<tr class="tablecontent" name="'+a+i+'">'+
											'<td id="customerCodeID" index="0" style="text-align:left">'+msg['data'][a][b]['name']+'</td>'+
											'<td id="lotNoID" index="1" >-</td>'+
											'<td id="customerLotNoID" index="2">-</td>'+
											'<td id="partNumberID" index="3">-</td>'+
											'<td id="stripQtyOutID" index="4">'+msg['data'][a][b]['inPut']['inDate']+'</td>'+
											'<td id="waferQtyOutID" index="5">'+msg['data'][a][b]['inPut']['TrackIn']+'</td>'+
											'<td id="customerOrderID" index="6">'+msg['data'][a][b]['inPut']['InQty']+'</td>'+
											'<td id="entryDateID" index="7">'+msg['data'][a][b]['outPut']['outDate']+'</td>'+
											'<td id="entryTimeID" index="8">'+msg['data'][a][b]['outPut']['equip']+'</td>'+
											'<td id="optUserID" index="9">'+msg['data'][a][b]['outPut']['outQty']+'</td>'+
											'<td id="statusID" lotId="'+msg['data'][a][b]['id']+'" index="10">'+msg['data'][a][b]['outPut']['rejQty']+'</td>'+
											'<td id="stockPosID" index="11">'+ct+'</td>'+
											'<td id="stockPosID" index="12">'+uph+'</td>'+
											'<td id="stockPosID" index="13">'+yld+'</td>'+
											'<td id="stockPosID" index="14"></td>'+
										'</tr>');
					if(msg['data'][a][b]['isHold']=="1")
						{
							$("tr[name='"+a+i+"']").children("td[index='1']").html('<span lotId="'+msg['data'][a][b]['id']+'" class="glyphicon glyphiconInside glyphicon-info-sign"></span>');
						}
					if(msg['data'][a][b]['isSplit']=="1")
					{
						$("tr[name='"+a+i+"']").children("td[index='2']").html('<span lotId="'+msg['data'][a][b]['id']+'" class="glyphicon glyphiconInside glyphicon-minus-sign"></span>');
					}
					if(msg['data'][a][b]['isMerge']=="1")
					{
						$("tr[name='"+a+i+"']").children("td[index='3']").html('<span lotId="'+msg['data'][a][b]['id']+'" class="glyphicon glyphiconInside glyphicon-plus-sign"></span>');
					}
					if(msg['data'][a][b]['isConsume']=="1")
					{
						$("tr[name='"+a+i+"']").children("td[index='14']").html('<span lotId="'+msg['data'][a][b]['id']+'" class="glyphicon glyphiconInside glyphicon-expand">Material<span class="God_span_nbsp"/>info.</span>');
					}
					i++;
				}
			debugger;
			i=i-1;
			$(".table").append('<tr class="tablecontent">'+
					'<td id="customerCodeID" index="0" colspan="4">Total Process</td>'+
					'<td id="stripQtyOutID" index="4">'+$("tr[name='"+a+"1']").children("td[index='4']").text()+'</td>'+
					'<td id="waferQtyOutID" index="5">-</td>'+
					'<td id="customerOrderID" index="6">'+$("tr[name='"+a+"1']").children("td[index='6']").text()+'</td>'+
					'<td id="entryDateID" index="7">'+$("tr[name='"+a+i+"']").children("td[index='7']").text()+'</td>'+
					'<td id="entryTimeID" index="8">-</td>'+
					'<td id="optUserID" index="9">'+$("tr[name='"+a+i+"']").children("td[index='9']").text()+'</td>'+
					'<td id="statusID" flag="total" index="10">'+summarySumGet(a,i,10)+'</td>'+
					'<td id="stockPosID" index="11">'+summarySumGet(a,i,11).toFixed(2)+'</td>'+
					'<td id="stockPosID" index="12">-</td>'+
					'<td id="stockPosID" index="13">'+(($("tr[name='"+a+i+"']").children("td[index='9']").text()/$("tr[name='"+a+"1']").children("td[index='6']").text())*100).toFixed(2)+'</td>'+
					'<td id="stockPosID" index="14">-</td>'+
				'</tr>');
		}
		//Consume Infomation
		$(".glyphicon-expand").bind("click",function(){
			var dialog = $('<div class="modal fade"><div style="width: 70%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Consume Infomation</h4></div><div class="modal-body thleft"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
				dialog.find('.modal-body').html("<h4 style='text-align: center;margin-top:-10px;'>"+basicInfo['lotNo']+"</h4><div id='grid_12001'></div>");
		        dialog.modal({
		            keyboard:false
		        }).on({
		            'hidden.bs.modal': function(){
		                $(this).remove();
		            }
		        });
		        var id=$(this).attr("lotId");
		        function getConsumegride(){
		        	debugger;
			        var grid = $("#grid_12001");
			        PageLoader = {
			            initGridPanel: function(){
			                 var self = this;
			                 var width = 130;
			                 return grid.grid({
			                        identity:"id",
			                        buttons: [
			                                {content: '<button class="btn btn-success" type="button"><span class="glyphicon"><span>Open-Selected</button>', action: 'modify'},
			                            ],
			                        url:'${pageContext.request.contextPath}/LotSummary/Manufacture/getDebit/'+id+'.koala',
			                        columns: [
												 { title: 'Lot N.', name: 'lotNo', width: width},
				     	                   		 { title: 'Material Type', name: 'materialType', width: width},
				       	                         { title: 'Qty', name: 'qty', width: 70},
				       	                         { title: 'User', name: 'user', width: 70},
				       	                         { title: 'Date', name: 'date', width: 200},
				       	                         { title: 'Part N.', name: 'partN.', width: width}
			                        ]
			                 }).on({
			                           'modify': function(event, data){
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
			                                debugger;
			                                if(data['item'][0]['materialType']=="Wafer")
			                                	{
			                                		window.open("/pages/WaferLotSummary.jsp?id="+indexs[0]);
			                                	}
			                                else{
			                                	window.open("/pages/MateriaLotSummary.jsp?id="+indexs[0]);
			                                }
			                                
			                            }
			                 });
			            }
			        };
			        PageLoader.initGridPanel();
		        }
		        setTimeout(getConsumegride, 1000);
		});
		//Merge Infomation
		$(".glyphicon-plus-sign").bind("click",function(){
			var dialog = $('<div class="modal fade"><div style="width: 70%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Merge Infomation</h4></div><div class="modal-body thleft"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
			dialog.find('.modal-body').html("<h4 style='text-align: center;margin-top:-10px;'>"+basicInfo['lotNo']+"</h4><div id='grid_12001'></div>");
	        dialog.modal({
	            keyboard:false
	        }).on({
	            'hidden.bs.modal': function(){
	                $(this).remove();
	            }
	        });
	        var id=$(this).attr("lotId");
	        function getMergegride(){
	        	debugger;
		        var grid = $("#grid_12001");
		        PageLoader = {
		            initGridPanel: function(){
		                 var self = this;
		                 var width = 130;
		                 return grid.grid({
		                        identity:"id",
		                        buttons: [
		                                {content: '<button class="btn btn-success" type="button"><span class="glyphicon"><span>Open-Selected</button>', action: 'modify'},
		                            ],
		                        url:'${pageContext.request.contextPath}/LotSummary/Manufacture/getMerge/'+id+'.koala',
		                        columns: [
			     	                   		 { title: 'Merge Lot', name: 'mergeLot', width: width},
			       	                         { title: 'User', name: 'user', width: width},
			       	                         { title: 'Merge Date', name: 'mergeDate', width: 200},
			       	                         { title: 'Merge Qty', name: 'mergeQty', width: width},
		                        ]
		                 }).on({
		                           'modify': function(event, data){
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
		                                window.open("/pages/LotSummary.jsp?id="+indexs[0]+"&stationId=<%=request.getParameter("stationId")%>");
		                            }
		                 });
		            }
		        };
		        PageLoader.initGridPanel();
	        }
	        setTimeout(getMergegride, 1000 );
		});
		//Split Infomation
		$(".glyphicon-minus-sign").bind("click",function(){
			var dialog = $('<div class="modal fade"><div style="width: 70%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Split Infomation</h4></div><div class="modal-body thleft"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
			dialog.find('.modal-body').html("<h4 style='text-align: center;margin-top:-10px;'>"+basicInfo['lotNo']+"</h4><div id='grid_12001'></div>");
	        dialog.modal({
	            keyboard:false
	        }).on({
	            'hidden.bs.modal': function(){
	                $(this).remove();
	            }
	        });
	        var id=$(this).attr("lotId");
	        function getSplitgride(){
	        	debugger;
		        var grid = $("#grid_12001");
		        PageLoader = {
		            initGridPanel: function(){
		                 var self = this;
		                 var width = 130;
		                 return grid.grid({
		                        identity:"id",
		                        buttons: [
		                                {content: '<button class="btn btn-success" type="button"><span class="glyphicon"><span>Open-Selected</button>', action: 'modify'},
		                            ],
		                        url:'${pageContext.request.contextPath}/LotSummary/Manufacture/getSplit/'+id+'.koala',
		                        columns: [
			     	                   		 { title: 'Split Lot', name: 'splitLot', width: width},
			       	                         { title: 'User', name: 'user', width: width},
			       	                         { title: 'Split Date', name: 'splitDate', width: 200},
			       	                         { title: 'Split Qty', name: 'splitQty', width: width},
		                        ]
		                 }).on({
		                           'modify': function(event, data){
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
		                                window.open("/pages/LotSummary.jsp?id="+indexs[0]+"&stationId=<%=request.getParameter("stationId")%>");
		                            }
		                 });
		            }
		        };
		        PageLoader.initGridPanel();
	        }
	        setTimeout(getSplitgride, 1000 );
		});
		//Hold Infomation
		$(".glyphicon-info-sign").bind("click",function(){
			var dialog = $('<div class="modal fade"><div style="width: 70%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Hold Information</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
			dialog.find('.modal-body').html('<h4 style="text-align: center;">'+basicInfo['lotNo']+'</h4>'+
					'<table class="table table-bordered"  style="text-align: center;"><tbody>'+
					'<tr class="tablehead"><th index="1" colspan="3">Hold</th>'+
					'<th index="2" colspan="3">Release</th></tr>'+
					'<tr class="tablehead"><th index="1">Hold Date</th><th index="2">User</th><th index="2">Hold Comments</th><th index="3">Release Date</th><th index="4">User</th><th index="5">Release Comments</th></tr>'+
					'<tr class="holdContent tablecontent"><td index="1"></td><td index="2"></td><td index="3"></td><td index="4"></td><td index="5"></td><td index="6"></td></tr>');
			dialog.modal({
	            keyboard:false
	        }).on({
	            'hidden.bs.modal': function(){
	                $(this).remove();
	            }
	        });
			
			var id=$(this).attr("lotId");
			$.get('${pageContext.request.contextPath}/LotSummary/Manufacture/getHold/'+id+'.koala').done(function(msg){
				dialog.find('.holdContent').children("td[index='1']").text(msg['data'][0]['holdDate']);
				dialog.find('.holdContent').children("td[index='2']").text(msg['data'][0]['holdUser']);
				dialog.find('.holdContent').children("td[index='3']").text(msg['data'][0]['holdComments']);
				dialog.find('.holdContent').children("td[index='4']").text(msg['data'][0]['releaseDate']);
				dialog.find('.holdContent').children("td[index='5']").text(msg['data'][0]['releaseUser']);
				dialog.find('.holdContent').children("td[index='6']").text(msg['data'][0]['releaseComments']);
			});
		});
		//rejetCode Information
		$("td[index='10']").bind("mouseover",function(){$(this).css("color","blue");$(this).css("cursor","pointer");});
		$("td[index='10']").bind("mouseout",function(){$(this).css("color","inherit");});
		$("td[index='10']").bind("click",function(){
		var id=$(this).attr("lotId");
		if($(this).text()=="-"||$(this).text()=="NaN"||$(this).text()=="0"||$(this).attr("flag")=="total")
		{
			return;
		}
		$.get('${pageContext.request.contextPath}/LotSummary/Manufacture/getReject/'+id+'.koala').done(function(msg){
				var dialog = $('<div class="modal fade"><div style="width: 50%;" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">RejectCode Information</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
				dialog.find('.modal-body').html('<h4 style="text-align: center;">'+msg['data'][0]['lotNo']+'</h4><table class="table table-rej table-bordered"  style="text-align: center;"><tbody>'
				+'<tr class="tablehead">'
				+'<th index="0" style="width:15%;">Order</th>'
				+'<th index="1">Prompt</th>'
				+'<th index="2">Value</th></tr>'
				+'</tbody></table>');
				dialog.modal({
		            keyboard:false
		        }).on({
		            'hidden.bs.modal': function(){
		                $(this).remove();
		            }
		        });
				//var msg={};
				//var str="[{prompt:'D.01',value:'1'},{prompt:'D.01',value:'1'},{prompt:'D.01',value:'1'},{prompt:'D.01',value:'1'},{prompt:'D.01',value:'1'}]";
				//msg['data'] = eval('(' + str + ')');
				var b=1;
				for(var a in msg['data'])
					{
						dialog.find('.table-rej').append('<tr class="tablecontent"><td>'+b+'</td><td>'+msg['data'][a]['prompt']+'</td><td>'+msg['data'][a]['value']+'</td></tr>');
						b++;
					}
			});
		});
	});
	function summarySumGet(a,i,index)
	{
		var sum=0;
		for(var j=1;j<=i;j++)
		{
			sum+=parseFloat($("tr[name='"+a+j+"']").children("td[index='"+index+"']").text());
		}
		return sum;
	}
});
</script>
</body>
</html>
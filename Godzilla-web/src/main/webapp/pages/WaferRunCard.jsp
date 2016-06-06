<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Wafer Run Card</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<script type="text/javascript" src="/js/jquery-barcode-last.min.js"></script>
<script type="text/javascript" src="/js/scrollReveal.min.js"></script>   
<style>
	td{
		border:1px solid #333;
	}
	body{
		font-size:14px;
	}
	table{
		border-collapse: collapse;
		border:2px solid #333;
		margin-bottom: 14px;
	}
	.tenp{
		width:10%;
	}
	.fifp{
		width:15%;
	}
	.twtp{
		width:20%;
	}
	.twtfp{
		width:25%;
	}
	.thirfp{
		width:35%;
	}
	.hafp{
		width:50%;
	}
	.bold_title{
		font-weight: bold;
		color:#333
	}
	.imges{
		float: right;
		margin-right: 25px;
	}
	#waferRunCard_mainContent{
		width:1180px;
		margin:0 auto;
	}
	@media print{
	#waferRunCard_mainContent{
		width: 1024px;
		margin: 0 auto;
	}
}
</style>
<body>
	<script>
		var id="<%=request.getParameter("id")%>";
		$.ajax({
			url: '/RunCardTemplate/findPreassyRunCardByWaferProcessId/'+id+'.koala',
			type: 'POST',
			dataType: 'json',
		})
		.done(function(msg) {
			/*var data={
					start:{lotNumber:'F001-A00001-01',
						unitsQty:'123',waferQty:'234',productName:'VC_5278',
						customerLotNumber:'213qwe',
						lotType:'E',partNumber:'XT-0091'
					},
					process:[{title:'Substrate Baking 基板烘烤',haveDetail:'1',detail:[{title:'Step #1 Temp. (deg C)',value:'123'},{title:'Program Name',value:'testProgram'},{title:'Step #1 Time (min)',value:'12'}]},
					         {title:'Substrate Baking 基板烘烤',haveDetail:'0',detail:[{title:'Step #1 Temp. (deg C)',value:'123'},{title:'Program Name',value:'testProgram'},{title:'Step #1 Time (min)',value:'12'}]}
					]
			};*/
			var data=msg['data'];
			for(var a in data['start'])
				{
					$('.start_'+a).text(data['start'][a]);			
				}
			$('.start_lotNumber').barcode(data['start']['lotNumber'],"code128",{barWidth:1, barHeight:30,showHRI:false});
			$('.start_lotNumberN').text(data['start']['lotNumber']);
			$('.start_customerLotNumber').barcode(data['start']['customerLotNumber'],"code128",{barWidth:1, barHeight:30,showHRI:false});
			$('.start_customerLotNumberN').text(data['start']['customerLotNumber']);
			for(var i=0;i<data['process'].length;i++)
				{
					var tableHtml='<table data-sr="wait 0.3s and enter top and scale up 20%" style="width:100%;"><tr class="bold_title" style="text-align: center;"><td style="text-align:left;width:18%" rowspan="3"><strong class="processTitle'+i+'"></strong></td><td style="width:17%" colspan="2">Equip.ID 设备号码</td><td style="width:15%">Employee ID 员工工号</td><td style="width: 14%;">Good Qty 良品数量</td><td>Defects/Scraps<br/>不良品数量</td><td>Wafer Qty<br/>条数</td><td>Date 日期</td></tr><tr style="text-align:center;"><td class="tenp" rowspan="2"></td><td style="text-align:left;font-weight: bold;">In 进料</td><td></td><td></td><td>---</td><td>---</td><td></td></tr><tr style="text-align:center;"><td style="text-align:left;font-weight: bold;">Out 出料</td><td></td><td></td><td></td><td></td><td></td></tr><tr><td colspan="8" style="height: 6px;"></td></tr><tr><td style="border-right:none;color:red;"colspan="5" rowspan="2" class="processDetail'+i+'"></td><td class="twtfp bold_title">Foup In 进料</td><td colspan="2"></td></tr><tr><td class="twtfp bold_title">Foup Out 出料</td><td colspan="2"</td></tr></table>';
					$("#waferRunCard_mainContent").append(tableHtml);
					$('.processTitle'+i).text(data['process'][i]['title']);
					debugger;
					if(data['process'][i]['haveDetail']=="1")
						{	
							for(var j=0;j<data['process'][i]['detail'].length;j++)
								{
									if(j%2==0)
										{
											$('.processDetail'+i).append("<span>"+data['process'][i]['detail'][j]['title']+" = "+data['process'][i]['detail'][j]['value']+"</span>");
										}
									if(j%2!=0)
										{
											$('.processDetail'+i).append("<span style='margin:20%'>"+data['process'][i]['detail'][j]['title']+" = "+data['process'][i]['detail'][j]['value']+"</span></p>");
										}
								}
						}
				}
			var isoutsourcing = data['start']['isoutsourcing'];
			if(isoutsourcing == 7)
			{
				$("#cardTitle").text("Wafer Outsourcing Process Traveler 晶圆委外生产流程卡");
			}
			else
			{
				$("#cardTitle").text("Wafer Process Traveler 晶圆生产流程卡");
			}
		});
		
		
	</script>
	<div id="waferRunCard_mainContent">
		<table style="width:100%;" data-sr="wait 0.5s and enter top and scale up 20%">
			<tr style="text-align:center;">
				<td colspan='7' style="font-size: 18px;border-right:none;"><strong style="margin-left: 11%;"><span id="cardTitle">Wafer Process Traveler 晶圆生产流程卡</span></strong></td>
				<td style="border-left:none;" class="tenp"><img style="width:100%" src="/images/logo.png" alt="LOGO"></td>
			</tr>
			<tr>
				<td colspan='8' style="height: 6px;"></td>
			</tr>
			<tr class="bold_title">
				<td class="twtfp">Lot Number 内部批号</td>
				<td class="twtfp" colspan='3'>Start Quantity 开始数量</td>
				<td colspan='4'>Product Name 产品名称</td>
			</tr>
			<tr>
				<td class="twtfp"><div class="start_lotNumberN" style="float:left"></div><div class="start_lotNumber" style="float:right"></div></td>
				<td class="twtfp" colspan='3'><div>Units 单元数量：<span class="start_unitsQty"></span></div><div>Wafer 晶圆片数：<span class="start_waferQty"></span></div></td>
				<td colspan='4'><span style="margin-left: 3%;" class="start_productName"></span></td>
			</tr>
			<tr>
				<td colspan='8' style="height: 6px;"></td>
			</tr>
			<tr class="bold_title">
				<td class="thirfp" colspan='2'>Customer Lot Number 客户批号</td>
				<td class="fifp" colspan='2'>Lot Type 批次类型</td>
				<td  colspan='4'>Part Number 产品编码</td>
			</tr>
			<tr>
				<td class="thirfp" colspan='2'><div class="start_customerLotNumberN" style="float:left"></div><div class="start_customerLotNumber" style="float:right"></div></td>
				<td class="fifp" colspan='2'><span style="margin-left: 3%;" class="start_lotType"></span></td>
				<td colspan='4'><span style="margin-left: 3%;" class="start_partNumber"></span></td>
			</tr>
		</table>
		<!-- <table style="width:100%;">
			<tr class="bold_title" style="text-align: center;">
				<td style="text-align:left;" rowspan="3"><strong class="processTitle">Substrate Baking 基板烘烤</strong></td>
				<td style="width:17%" colspan="2">Equip.ID 设备号码</td>
				<td>Employee ID 员工工号</td>
				<td>Good Qty 良品数量</td>
				<td>Defects/Scraps<br/>不良品数量</td>
				<td>Wafer Qty<br/>条数</td>
				<td>Date 日期</td>
			</tr>
			<tr style="text-align:center;">
				<td class="tenp" rowspan="2"></td>
				<td style="text-align:left;font-weight: bold;">In 进料</td>
				<td></td>
				<td></td>
				<td>---</td>
				<td>---</td>
				<td></td>
			</tr>
			<tr style="text-align:center;">
				<td style="text-align:left;font-weight: bold;">Out 出料</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan='8' style="height: 6px;"></td>
			</tr>
			<tr>
				<td style="border-right:none;color:red;"colspan='5' rowspan='2' class="processDetail">
					<p><span>1) Step #1 Temp. (deg C) =</span><span style="margin:20%">3) Program Name =</span></p>
					<p><span>2) Step #1 Time (min) =</span></p>
				</td>
				<td class="twtfp bold_title">
					Foup In 进料
				</td>
				<td colspan="2">
				
				</td>
			</tr>
			<tr>
				<td class="twtfp bold_title">
					Foup Out 出料
				</td>
				<td colspan="2">
					
				</td>
			</tr>
		</table> -->
	</div>
</body>
</html>
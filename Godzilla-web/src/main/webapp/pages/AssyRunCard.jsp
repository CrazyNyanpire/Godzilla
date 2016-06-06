<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Assembly Run Card</title>
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
			url: '/RunCardTemplate/findAssemblyRunCardByManufactureProcessId/'+id+'.koala',
			type: 'POST',
			dataType: 'json',
		})
		.done(function(msg) {
			/*var data={
					start:{lotNumber:'F001-A00001-01',
						unitsQty:'123',stripsQty:'234',productName:'VC_5278',
						customerLotNumber:'ed123123',
						lotType:'E',dateCode:'XT-0091',
					},
					process:[{title:'Substrate Baking 基板烘烤',haveDetail:'1',haveMagIn:'0',haveMagOut:'0',detail:[{title:'Step #1 Temp. (deg C)',value:'123'},{title:'Program Name',value:'testProgram'},{title:'Step #1 Time (min)',value:'12'}]},
					         {title:'Substrate Baking 基板烘烤',haveDetail:'0',haveMagIn:'1',haveMagOut:'1',detail:[{title:'Step #1 Temp. (deg C)',value:'123'},{title:'Program Name',value:'testProgram'},{title:'Step #1 Time (min)',value:'12'}]}
					]
			};*/
			var  data=msg['data'];
			for(var a in data['start'])
				{
					$('.start_'+a).text(data['start'][a]);			
				}
			$('.start_lotNumber').barcode(data['start']['lotNumber'],"code128",{barWidth:1, barHeight:30,showHRI:false});
			$('.start_lotNumberN').text(data['start']['lotNumber']);
			$('.start_customerLotNumber').barcode(data['start']['customerLotNumber'],"code128",{barWidth:1, barHeight:30,showHRI:false});
			$('.start_customerLotNumberN').text(data['start']['customerLotNumber']);
			$('.start_compQty').text(data['start']['component']);
			for(var i=0;i<data['process'].length;i++)
			{
				var tableHtml='<table data-sr="wait 0.3s and enter top and scale up 20%" style="width:100%;"><tr class="bold_title" style="text-align: center;"><td style="text-align:left;width:18%" rowspan="3"><strong class="processTitle'+i+'"></strong></td><td style="width:17%" colspan="2">Equip.ID 设备号码</td><td style="width:15%">Employee ID 员工工号</td><td style="width: 14%;">Good Qty 良品数量</td><td>Defects/Scraps<br/>不良品数量</td><td>Wafer Qty<br/>条数</td><td>Date 日期</td></tr><tr style="text-align:center;"><td class="tenp" rowspan="2"></td><td style="text-align:left;font-weight: bold;">In 进料</td><td></td><td></td><td>---</td><td>---</td><td></td></tr><tr style="text-align:center;"><td style="text-align:left;font-weight: bold;">Out 出料</td><td></td><td></td><td></td><td></td><td></td></tr><tr class="nbsp'+i+'"><tr><td class="processDetaila'+i+'" colspan="8" style="height: 6px;"></td></tr><tr class="display_magIn'+i+'"><td class="bold_title">Mag. In  开始料盒号码</td><td colspan="7"></td></tr><tr class="display_magOut'+i+'"><td class="bold_title">Mag. Out  结束料盒号码</td><td colspan="7"></td></tr><tr><td class="processDetail'+i+'" colspan="8" style="color:red"></td></tr></table>';
				$("#waferRunCard_mainContent").append(tableHtml);
				$('.processTitle'+i).text(data['process'][i]['title']);
				debugger;
				if(data['process'][i]['haveMagIn']=="0")
				{	
					$(".display_magIn"+i).hide();
				}
				if(data['process'][i]['haveMagOut']=="0")
				{	
					$(".display_magOut"+i).hide();
				}
				if(data['process'][i]['haveMagOut']=="0"&&data['process'][i]['haveMagIn']=="0")
				{
					$(".nbsp"+i).hide();
				}
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
				else{
					$('.processDetail'+i).parent().hide();
					$('.processDetaila'+i).parent().hide();
				}
			}
		});
	</script>
	<div id="waferRunCard_mainContent">
		<table style="width:100%;" data-sr="wait 0.5s and enter top and scale up 20%">
			<tr style="text-align:center;">
				<td colspan='7' style="font-size: 18px;border-right:none;"><strong style="margin-left: 9%;">Assembly Process Traveler 封装流程卡</strong></td>
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
				<td class="twtfp"><div class="start_lotNumberN" style="float:left;width:80px"></div><div class="start_lotNumber" style="float:right"></div></td>
				<td class="twtfp" colspan='3'><div>Comp. 被动组件数量:：<span class="start_compQty"></span></div><div>Units 单元数量：<span class="start_unitsQty"></span></div><div>Strips 基板数量：<span class="start_stripsQty"></span></div></td>
				<td colspan='4'><span style="margin-left: 3%;" class="start_productName"></span></td>
			</tr>
			<tr>
				<td colspan='8' style="height: 6px;"></td>
			</tr>
			<tr class="bold_title">
				<td class="thirfp" colspan='4'>Customer Lot Number 客户批号</td>
				<td class="twtfp" colspan='1'>Lot Type 批次类型</td>
				<td  colspan='3'>Date Code 周期</td>
			</tr>
			<tr>
				<td class="thirfp" colspan='4'><div class="start_customerLotNumberN" style="float:left"></div><div class="start_customerLotNumber" style="float:right"></div></td>
				<td class="twtfp" colspan='1'><span style="margin-left: 3%;" class="start_lotType"></span></td>
				<td colspan='3'><span style="margin-left: 3%;" class="start_dateCode"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
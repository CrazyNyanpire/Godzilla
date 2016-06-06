<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Bom List</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<style>
th{
	border: 1px solid #333;
	padding: 3px;
	text-align: center;
}
td{
	border: 1px solid #333;
}
.God_Bom_Center{
	text-align:center;
}
</style>
</head>
<body>
	<script>
		var id="<%=request.getParameter("id")%>";
		$.ajax({
			url: '/BomList/findBomListByWaferProcess/'+id+'.koala',
			type: 'POST',
			dataType: 'json',
		})
		.done(function(msg) {
			debugger;
			$(".Bom_productName").text(msg['productName']);
			$(".God_secondTitle").text(msg['secondTitle']);
			var data = msg['bomList'];
			//var data={Dicing:[{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'}],SMT:[{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'},{a:'1',s:'2',d:'3',f:'4',g:'5',h:'6',j:'7',k:'8',l:'9',q:'10'}]};
			for(a in data)
				{
					$("table").append("<tr><td class='God_Bom_Center' rowspan="+(parseInt(data[a].length)+1)+">"+a+"</td></tr>");
					for(var i=0;i<data[a].length;i++)
						{
							for(var b in data[a][i])
								{
									if(data[a][i][b]==null)
										{
											data[a][i][b]="";
										}
								}
							$("table").append("<tr><td class='God_Bom_Center'>"+data[a][i]['sn']+"</td><td>"+data[a][i]['materialName']+"</td><td>"+data[a][i]['item']+"</td><td>"+data[a][i]['partId']+"</td><td>"+data[a][i]['materialSpecification']+"</td><td>"+data[a][i]['materialSpecification']+"</td><td class='God_Bom_Center'>"+data[a][i]['qty']+"</td><td class='God_Bom_Center'>"+data[a][i]['unit']+"</td><td>"+data[a][i]['materialSpecification']+"</td><td>"+data[a][i]['materialSpecification']+"</td></tr>");
						}
				}
		})
		.fail(function() {
			console.log("error");
		})

	</script>
	<div style="border:1px solid #333;width: 1002px;margin:0 auto">
		<div style="text-align:center;border:1px solid #333;">
		<h3>B.O.M List for <span class="Bom_productName"></span></h3>
		<h3>(<span class="God_secondTitle">Preassy</span>)</h3>
		<p style="text-align:left;margin-left:10px;margin-top: 30px;">发行部门：<span>封装工程</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发行人：<span>XXX</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;版本：<span>AB-OI-A0</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生效日期：<span>2014-04-23</span></p>
		</div>
		<table style="width:1000px;margin:0 auto;">
			<tr style="background-color: antiquewhite;">
				<th style="text-align:left;">Stage</th>
				<th>S/N</th>
				<th style="text-align:left;">品名</th>
				<th>Item</th>
				<th>Material Specification</th>
				<th>Vendor P/N</th>
				<th>XX P/N</th>
				<th>Qty</th>
				<th>Unit</th>
				<th>Vendor</th>
				<th>Bar-Code</th>
			</tr>
		</table>
	</div>
</body>
</html>
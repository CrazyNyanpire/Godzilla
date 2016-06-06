function animationbind()
{
	$(".glyphiconInside").bind("click",function(){
 		var index=$(this).attr("index");
 		//$("tr[name='"+index+"child']").slideToggle("fast");
 		$("tr[name='"+index+"child']").fadeToggle("fast","linear");
 	});
}
//EXCLE 导出JS
function base64(string) {
    return window.btoa(unescape(encodeURIComponent(string)));
}
function tableToExcel(tableList, name) {
  var tables = [],
      uri = 'data:application/vnd.ms-excel;base64,',
      template = Handlebars.compile('<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{{worksheet}}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body>{{#each tables}}<table>{{{this}}}</table>{{/each}}</body></html>');
 
  for (var i = 0; i < tableList.length; i++) {
    tables.push(tableList[i].innerHTML);
  }
  var data = {
    worksheet: name || 'Worksheet',
    tables: tables
  };
  return uri + base64(template(data));
}
function exportHandler(event) {
  debugger;
  var tables = this.$('table'),table = null;
  tables.each(function (i) {
    //var t = $('<table><thead></thead><tbody></tobdy></table>');
    var t = $('<table><tbody></tobdy></table>');
    //t.find('thead').html(this.tHead.innerHTML);
    t.find('tbody').append($(this.tBodies).children(':visible').clone());
    t.find('.not-print').remove(); // not-print 是@media print中不会打印的部分
    t.find('a').replaceWith(function (i) { // 表格中不再需要的超链接也移除了
      return this.innerHTML;
    });
    table = table ? table.add(t) : t;
  });
  event.currentTarget.href = tableToExcel(table, 'summary');
}

function headsummarysearch()
{
	var obj=requestParamToJson($("form").serializeArray());
	if(obj['God_typeCheck']==undefined)
	{
		$("body").message({
            type: 'error',
            content: "请选择查询的种类"
        });
		return false;
	}
	if($(".tab-nav-action").attr("name")=="today")
		{
			if(obj['God_today']==undefined)
			{
				$("body").message({
                    type: 'error',
                    content: "请选择Tody的查询时间类型"
                });
				return false;
			}
			else if(obj['God_today']=="Now"){
				delete obj['beginHour'];
				delete obj['begindata'];
				delete obj['enddata'];
				delete obj['endHour'];
			}
			delete obj['month'];
			delete obj['week'];
		}
	else if($(".tab-nav-action").attr("name")=="week")
		{
			delete obj['beginHour'];
			delete obj['begindata'];
			delete obj['enddata'];
			delete obj['endHour'];
			delete obj['month'];
		}
	else($(".tab-nav-action").attr("name")=="month")
		{
			delete obj['beginHour'];
			delete obj['begindata'];
			delete obj['enddata'];
			delete obj['endHour'];
			delete obj['week'];	
		}
	var restring=JSON.stringify(obj);
	window.open('/pages/SubstrateSummary.jsp?data='+restring);
}
function headsummaryskip()
{
	var obj=requestParamToJson($("form").serializeArray());
	if(obj['God_typeCheck']==undefined)
	{
		$("body").message({
            type: 'error',
            content: "请选择查询的种类"
        });
		return false;
	}
	if($(".tab-nav-action").attr("name")=="today")
		{
			if(obj['God_today']==undefined)
			{
				$("body").message({
                    type: 'error',
                    content: "请选择Tody的查询时间类型"
                });
				return false;
			}
			else if(obj['God_today']=="Now"){
				delete obj['beginHour'];
				delete obj['begindata'];
				delete obj['enddata'];
				delete obj['endHour'];
			}
			delete obj['month'];
			delete obj['week'];
		}
	else if($(".tab-nav-action").attr("name")=="week")
		{
			delete obj['beginHour'];
			delete obj['begindata'];
			delete obj['enddata'];
			delete obj['endHour'];
			delete obj['month'];
		}
	else($(".tab-nav-action").attr("name")=="month")
		{
			delete obj['beginHour'];
			delete obj['begindata'];
			delete obj['enddata'];
			delete obj['endHour'];
			delete obj['week'];	
		}
	var restring=JSON.stringify(obj);
	self.location='/pages/SubstrateSummary.jsp?data='+restring;
}
function wipdataGet(data)
{
	switch(data['God_typeCheck'])
	{
		case "产品":
			$("#wipMainContent table").append('<tr class="tablehead">'
					+'<th index="0" width="10%"></th>'
					+'<th index="1" >Waiting</th>'
					+'<th index="2" >Running</th>'
					+'<th index="3" >Out-Spec</th>'
					+'<th index="4" >Cus. Hold</th>'
					+'<th index="5" >Eng. Disp</th>'
					+'<th index="6" >Total WIP</th>'
					+'<th index="7" >SWR</th>'
					+'<th index="8" width="10%">Out Yesterday</th>'
					+'<th index="9" >Out 1st Shift</th>'
					+'<th index="10">Out 2nd Shift</th>'
					+'<th index="11" >Total Out</th>'
					+'</tr>');
			$.get('/TotalWIP/Manufacture/getNowTotalWIP.koala?queryData=').done(function(msg){
			/*var msg = {};
			var str="{'SMT':[{step:'基板烘烤',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'锡膏印刷',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'贴片',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'}],"
						+"'Die Attach':[{step:'D/A前电浆清洗',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'D/A上片1',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'D/A后烘烤1',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},]}";	
			msg['data']=eval("(" + str + ")");*/
			debugger;
			var tableContent="";
			var totalwaiting=0;
			var totalrunning=0;
			var totalOutSpec=0;
			var totalcusHold=0;
			var totalengDisp=0;
			var totaltotalWip=0;
			var totalswr=0;
			var totaloutYesterday=0;
			var totalout1stShift=0;
			var totalout2ndShift=0;
			var totaltotalOut=0;
			for(var a in msg['data'])
				{
					var tdstr="";
					var fstwaiting=0;
					var fstrunning=0;
					var fstOutSpec=0;
					var fstcusHold=0;
					var fstengDisp=0;
					var fsttotalWip=0;
					var fstswr=0;
					var fstoutYesterday=0;
					var fstout1stShift=0;
					var fstout2ndShift=0;
					var fsttotalOut=0;
					for(var b in msg['data'][a])
					{
						var totalWip=(parseFloat(msg['data'][a][b]['waiting'])+parseFloat(msg['data'][a][b]['running'])+parseFloat(msg['data'][a][b]['outSpec'])+parseFloat(msg['data'][a][b]['cusHold'])+parseFloat(msg['data'][a][b]['engHold']))
						var totalOut=(parseFloat(msg['data'][a][b]['out1st'])+parseFloat(msg['data'][a][b]['out2end']));
						fstwaiting+=parseFloat(msg['data'][a][b]['waiting']);
						fstrunning+=parseFloat(msg['data'][a][b]['running']);
						fstOutSpec+=parseFloat(msg['data'][a][b]['outSpec']);
						fstcusHold+=parseFloat(msg['data'][a][b]['cusHold']);
						fstengDisp+=parseFloat(msg['data'][a][b]['engHold']);
						fsttotalWip+=parseFloat(totalWip);
						fstswr+=parseFloat(msg['data'][a][b]['swr']);
						fstoutYesterday+=parseFloat(msg['data'][a][b]['outYest']);
						fstout1stShift+=parseFloat(msg['data'][a][b]['out1st']);
						fstout2ndShift+=parseFloat(msg['data'][a][b]['out2end']);
						fsttotalOut+=parseFloat(totalOut);
						tdstr+='<tr class="tablecontent tablecontentchild" name="'+a+'child">'
						+'<td index="0" style="text-align:left;padding-left: 4px;background-color:#83ccd2"><span style="padding-right: 6px;">├</span>'+msg['data'][a][b]['step']+'</td>'
						+'<td index="1">'+accounting.formatNumber(msg['data'][a][b]['waiting'])+'</td>'
						+'<td index="2">'+accounting.formatNumber(msg['data'][a][b]['running'])+'</td>'
						+'<td index="3">'+accounting.formatNumber(msg['data'][a][b]['outSpec'])+'</td>'
						+'<td index="4">'+accounting.formatNumber(msg['data'][a][b]['cusHold'])+'</td>'
						+'<td index="5">'+accounting.formatNumber(msg['data'][a][b]['engHold'])+'</td>'
						+'<td index="6">'+accounting.formatNumber(totalWip)+'</td>'
						+'<td index="7">'+accounting.formatNumber(msg['data'][a][b]['swr'])+'</td>'
						+'<td index="8">'+accounting.formatNumber(msg['data'][a][b]['outYest'])+'</td>'
						+'<td index="9">'+accounting.formatNumber(msg['data'][a][b]['out1st'])+'</td>'
						+'<td index="10">'+accounting.formatNumber(msg['data'][a][b]['out2end'])+'</td>'
						+'<td index="11">'+accounting.formatNumber(totalOut)+'</td>'
							+'</tr>';
						
					}
					var processTd='<tr class="tablecontent" name="'+a+'" style="background-color:#83ccd2;">'
						+'<td index="0" style="text-align:left;font-weight: bold;background-color:#3db6c1"><span class="glyphicon glyphiconInside glyphicon-plus-sign" index="'+a+'" style="padding-right: 6px;"></span>'+a+'</td>'
						+'<td index="1">'+accounting.formatNumber(fstwaiting)+'</td>'
						+'<td index="2">'+accounting.formatNumber(fstrunning)+'</td>'
						+'<td index="3">'+accounting.formatNumber(fstOutSpec)+'</td>'
						+'<td index="4">'+accounting.formatNumber(fstcusHold)+'</td>'
						+'<td index="5">'+accounting.formatNumber(fstengDisp)+'</td>'
						+'<td index="6">'+accounting.formatNumber(fsttotalWip)+'</td>'
						+'<td index="7">'+accounting.formatNumber(fstswr)+'</td>'
						+'<td index="8">'+accounting.formatNumber(fstoutYesterday)+'</td>'
						+'<td index="9">'+accounting.formatNumber(fstout1stShift)+'</td>'
						+'<td index="10">'+accounting.formatNumber(fstout2ndShift)+'</td>'
						+'<td index="11">'+accounting.formatNumber(fsttotalOut)+'</td>'
						+'</tr>';
					tableContent+=processTd+tdstr;
					totalwaiting+=parseFloat(fstwaiting);
					totalrunning+=parseFloat(fstrunning);
					totalOutSpec+=parseFloat(fstOutSpec);
					totalcusHold+=parseFloat(fstcusHold);
					totalengDisp+=parseFloat(fstengDisp);
					totaltotalWip+=parseFloat(fsttotalWip);
					totalswr+=parseFloat(fstswr);
					totaloutYesterday+=parseFloat(fstoutYesterday);
					totalout1stShift+=parseFloat(fstout1stShift);
					totalout2ndShift+=parseFloat(fstout2ndShift);
					totaltotalOut+=parseFloat(fsttotalOut);
				}
			var totalTd='<tr class="tableadd">'
			+'<td class="total-firstTd">Total Wip and Output</td>'
			+'<td index="1">'+accounting.formatNumber(totalwaiting)+'</td>'
			+'<td index="2">'+accounting.formatNumber(totalrunning)+'</td>'
			+'<td index="3">'+accounting.formatNumber(totalOutSpec)+'</td>'
			+'<td index="4">'+accounting.formatNumber(totalcusHold)+'</td>'
			+'<td index="5">'+accounting.formatNumber(totalengDisp)+'</td>'
			+'<td index="6">'+accounting.formatNumber(totaltotalWip)+'</td>'
			+'<td index="7">'+accounting.formatNumber(totalswr)+'</td>'
			+'<td index="8">'+accounting.formatNumber(totaloutYesterday)+'</td>'
			+'<td index="9">'+accounting.formatNumber(totalout1stShift)+'</td>'
			+'<td index="10">'+accounting.formatNumber(totalout2ndShift)+'</td>'
			+'<td index="11">'+accounting.formatNumber(totaltotalOut)+'</td>'
			+'</tr>';
			$("#wipMainContent table").append(totalTd);
			$("#wipMainContent table").append(tableContent);
			animationbind();
			});
			break;
		case "晶圆":
			$("#wipMainContent table").append('<tr class="tablehead">'
					+'<th index="0"  width="10%"></th>'
					+'<th index="1" >Waiting</th>'
					+'<th index="2" >Running</th>'
					+'<th index="3" >Out-Spec</th>'
					+'<th index="4"  >Cus. Hold</th>'
					+'<th index="5"  >Eng. Disp</th>'
					+'<th index="6" >Total WIP</th>'
					+'<th index="7"  >SWR</th>'
					+'<th index="8"  >Out Yesterday</th>'
					+'<th index="9" >Out 1st Shift</th>'
					+'<th index="10"  >Out 2nd Shift</th>'
					+'<th index="11"  >Total Out</th>'
					+'</tr>');
			
			$.get('/TotalWIP/Wafer/getNowTotalWIP.koala?queryData=').done(function(msg){
			//var msg = {};
			/*var str="{'SMT':[{step:'基板烘烤',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'锡膏印刷',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'贴片',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'}],"
						+"'Die Attach':[{step:'D/A前电浆清洗',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'D/A上片1',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'D/A后烘烤1',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},]}";	
			msg['data']=eval("(" + str + ")");*/
			debugger;
			var tableContent="";
			var totalwaiting=0;
			var totalrunning=0;
			var totalOutSpec=0;
			var totalcusHold=0;
			var totalengDisp=0;
			var totaltotalWip=0;
			var totalswr=0;
			var totaloutYesterday=0;
			var totalout1stShift=0;
			var totalout2ndShift=0;
			var totaltotalOut=0;
			for(var a in msg['data'])
			{
				var tdstr="";
				var fstwaiting=0;
				var fstrunning=0;
				var fstOutSpec=0;
				var fstcusHold=0;
				var fstengDisp=0;
				var fsttotalWip=0;
				var fstswr=0;
				var fstoutYesterday=0;
				var fstout1stShift=0;
				var fstout2ndShift=0;
				var fsttotalOut=0;
				for(var b in msg['data'][a])
				{
						var totalWip=(parseFloat(msg['data'][a][b]['waiting'])+parseFloat(msg['data'][a][b]['running'])+parseFloat(msg['data'][a][b]['outSpec'])+parseFloat(msg['data'][a][b]['cusHold'])+parseFloat(msg['data'][a][b]['engHold']))
						var totalOut=(parseFloat(msg['data'][a][b]['out1st'])+parseFloat(msg['data'][a][b]['out2end']));
						fstwaiting+=parseFloat(msg['data'][a][b]['waiting']);
						fstrunning+=parseFloat(msg['data'][a][b]['running']);
						fstOutSpec+=parseFloat(msg['data'][a][b]['outSpec']);
						fstcusHold+=parseFloat(msg['data'][a][b]['cusHold']);
						fstengDisp+=parseFloat(msg['data'][a][b]['engHold']);
						fsttotalWip+=parseFloat(totalWip);
						fstswr+=parseFloat(msg['data'][a][b]['swr']);
						fstoutYesterday+=parseFloat(msg['data'][a][b]['outYest']);
						fstout1stShift+=parseFloat(msg['data'][a][b]['out1st']);
						fstout2ndShift+=parseFloat(msg['data'][a][b]['out2end']);
						fsttotalOut+=parseFloat(totalOut);
						tdstr+='<tr class="tablecontent tablecontentchild" name="'+a+'child">'
						+'<td index="0" style="text-align:left;padding-left: 4px;background-color:#83ccd2"><span style="padding-right: 6px;">├</span>'+msg['data'][a][b]['step']+'</td>'
						+'<td index="1">'+accounting.formatNumber(msg['data'][a][b]['waiting'])+'</td>'
						+'<td index="2">'+accounting.formatNumber(msg['data'][a][b]['running'])+'</td>'
						+'<td index="3">'+accounting.formatNumber(msg['data'][a][b]['outSpec'])+'</td>'
						+'<td index="4">'+accounting.formatNumber(msg['data'][a][b]['cusHold'])+'</td>'
						+'<td index="5">'+accounting.formatNumber(msg['data'][a][b]['engHold'])+'</td>'
						+'<td index="6">'+accounting.formatNumber(totalWip)+'</td>'
						+'<td index="7">'+accounting.formatNumber(msg['data'][a][b]['swr'])+'</td>'
						+'<td index="8">'+accounting.formatNumber(msg['data'][a][b]['outYest'])+'</td>'
						+'<td index="9">'+accounting.formatNumber(msg['data'][a][b]['out1st'])+'</td>'
						+'<td index="10">'+accounting.formatNumber(msg['data'][a][b]['out2end'])+'</td>'
						+'<td index="11">'+accounting.formatNumber(totalOut)+'</td>'
							+'</tr>';
						
					}
					var processTd='<tr class="tablecontent" name="'+a+'" style="background-color:#83ccd2;">'
						+'<td index="0" style="text-align:left;font-weight: bold;background-color:#3db6c1"><span class="glyphicon glyphiconInside glyphicon-plus-sign" index="'+a+'" style="padding-right: 6px;"></span>'+a+'</td>'
						+'<td index="1">'+accounting.formatNumber(fstwaiting)+'</td>'
						+'<td index="2">'+accounting.formatNumber(fstrunning)+'</td>'
						+'<td index="3">'+accounting.formatNumber(fstOutSpec)+'</td>'
						+'<td index="4">'+accounting.formatNumber(fstcusHold)+'</td>'
						+'<td index="5">'+accounting.formatNumber(fstengDisp)+'</td>'
						+'<td index="6">'+accounting.formatNumber(fsttotalWip)+'</td>'
						+'<td index="7">'+accounting.formatNumber(fstswr)+'</td>'
						+'<td index="8">'+accounting.formatNumber(fstoutYesterday)+'</td>'
						+'<td index="9">'+accounting.formatNumber(fstout1stShift)+'</td>'
						+'<td index="10">'+accounting.formatNumber(fstout2ndShift)+'</td>'
						+'<td index="11">'+accounting.formatNumber(fsttotalOut)+'</td>'
						+'</tr>';
					tableContent+=processTd+tdstr;
					totalwaiting+=parseFloat(fstwaiting);
					totalrunning+=parseFloat(fstrunning);
					totalOutSpec+=parseFloat(fstOutSpec);
					totalcusHold+=parseFloat(fstcusHold);
					totalengDisp+=parseFloat(fstengDisp);
					totaltotalWip+=parseFloat(fsttotalWip);
					totalswr+=parseFloat(fstswr);
					totaloutYesterday+=parseFloat(fstoutYesterday);
					totalout1stShift+=parseFloat(fstout1stShift);
					totalout2ndShift+=parseFloat(fstout2ndShift);
					totaltotalOut+=parseFloat(fsttotalOut);
				}
			var totalTd='<tr class="tableadd">'
			+'<td class="total-firstTd">Total Wip and Output</td>'
			+'<td index="1">'+accounting.formatNumber(totalwaiting)+'</td>'
			+'<td index="2">'+accounting.formatNumber(totalrunning)+'</td>'
			+'<td index="3">'+accounting.formatNumber(totalOutSpec)+'</td>'
			+'<td index="4">'+accounting.formatNumber(totalcusHold)+'</td>'
			+'<td index="5">'+accounting.formatNumber(totalengDisp)+'</td>'
			+'<td index="6">'+accounting.formatNumber(totaltotalWip)+'</td>'
			+'<td index="7">'+accounting.formatNumber(totalswr)+'</td>'
			+'<td index="8">'+accounting.formatNumber(totaloutYesterday)+'</td>'
			+'<td index="9">'+accounting.formatNumber(totalout1stShift)+'</td>'
			+'<td index="10">'+accounting.formatNumber(totalout2ndShift)+'</td>'
			+'<td index="11">'+accounting.formatNumber(totaltotalOut)+'</td>'
			+'</tr>';
			$("#wipMainContent table").append(totalTd);
			$("#wipMainContent table").append(tableContent);
			animationbind();
			});
			break;
		case "材料":
			$("#wipMainContent table").append('<tr class="tablehead">'
					+'<th index="0" width="10%"></th>'
					+'<th index="1" >Waiting</th>'
					+'<th index="2" >IQC Pass</th>'
					+'<th index="3" >Hold</th>'
					+'<th index="6" >Total WIP</th>'
					+'<th index="7" >SWR</th>'
					+'<th index="8" >Out Yesterday</th>'
					+'<th index="9">Out 1st Shift</th>'
					+'<th index="10" >Out 2nd Shift</th>'
					+'<th index="11" >Total Out</th>'
					+'</tr>');
			
			$.get('/TotalWIP/Material/getNowTotalWIP.koala?queryData=').done(function(msg){
			/*var msg = {};
			var str="{'SMT':[{step:'基板烘烤',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'锡膏印刷',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'贴片',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'}],"
						+"'Die Attach':[{step:'D/A前电浆清洗',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'D/A上片1',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},"
						+"{step:'D/A后烘烤1',waiting:'100',running:'200',outSpec:'0',cusHold:'20',engHold:'30',swr:'0',outYest:'1000',out1st:'600',out2end:'700'},]}";	
			msg['data']=eval("(" + str + ")");*/
			debugger;
			var tableContent="";
			var totalwaiting=0;
			var totalrunning=0;
			var totalOutSpec=0;
			var totalcusHold=0;
			var totalengDisp=0;
			var totaliqcPass=0;
			var totalhold=0;
			var totaltotalWip=0;
			var totalswr=0;
			var totaloutYesterday=0;
			var totalout1stShift=0;
			var totalout2ndShift=0;
			var totaltotalOut=0;
			for(var a in msg['data'])
			{
					var tdstr="";
					var fstwaiting=0;
					var fstrunning=0;
					var fstOutSpec=0;
					var fstcusHold=0;
					var fstengDisp=0;
					var fstiqcPass=0;
					var fsthold=0;
					var fsttotalWip=0;
					var fstswr=0;
					var fstoutYesterday=0;
					var fstout1stShift=0;
					var fstout2ndShift=0;
					var fsttotalOut=0;
					for(var b in msg['data'][a])
					{
						var totalWip=(parseFloat(msg['data'][a][b]['waiting'])+parseFloat(msg['data'][a][b]['running'])+parseFloat(msg['data'][a][b]['outSpec'])+parseFloat(msg['data'][a][b]['cusHold'])+parseFloat(msg['data'][a][b]['engHold']))
						var totalOut=(parseFloat(msg['data'][a][b]['out1st'])+parseFloat(msg['data'][a][b]['out2end']));
						fstwaiting+=parseFloat(msg['data'][a][b]['waiting']);
						fstrunning+=parseFloat(msg['data'][a][b]['running']);
						fstOutSpec+=parseFloat(msg['data'][a][b]['outSpec']);
						fstcusHold+=parseFloat(msg['data'][a][b]['cusHold']);
						fstengDisp+=parseFloat(msg['data'][a][b]['engHold']);
						fstiqcPass+=parseFloat(msg['data'][a][b]['iqcPass']);
						fsthold+=parseFloat(msg['data'][a][b]['hold']);
						fsttotalWip+=parseFloat(totalWip);
						fstswr+=parseFloat(msg['data'][a][b]['swr']);
						fstoutYesterday+=parseFloat(msg['data'][a][b]['outYest']);
						fstout1stShift+=parseFloat(msg['data'][a][b]['out1st']);
						fstout2ndShift+=parseFloat(msg['data'][a][b]['out2end']);
						fsttotalOut+=parseFloat(totalOut);
						tdstr+='<tr class="tablecontent tablecontentchild" name="'+a+'child">'
						+'<td index="0" style="text-align:left;padding-left: 4px;background-color:#83ccd2"><span style="padding-right: 6px;">├</span>'+msg['data'][a][b]['step']+'</td>'
						+'<td index="1">'+accounting.formatNumber(msg['data'][a][b]['waiting'])+'</td>'
						+'<td index="4">'+accounting.formatNumber(msg['data'][a][b]['iqcPass'])+'</td>'
						+'<td index="5">'+accounting.formatNumber(msg['data'][a][b]['hold'])+'</td>'
						+'<td index="6">'+accounting.formatNumber(totalWip)+'</td>'
						+'<td index="7">'+accounting.formatNumber(msg['data'][a][b]['swr'])+'</td>'
						+'<td index="8">'+accounting.formatNumber(msg['data'][a][b]['outYest'])+'</td>'
						+'<td index="9">'+accounting.formatNumber(msg['data'][a][b]['out1st'])+'</td>'
						+'<td index="10">'+accounting.formatNumber(msg['data'][a][b]['out2end'])+'</td>'
						+'<td index="11">'+accounting.formatNumber(totalOut)+'</td>'
							+'</tr>';
						
					}
					var processTd='<tr class="tablecontent" name="'+a+'" style="background-color:#83ccd2;">'
						+'<td index="0" style="text-align:left;font-weight: bold;background-color:#3db6c1"><span class="glyphicon glyphiconInside glyphicon-plus-sign" index="'+a+'" style="padding-right: 6px;"></span>'+a+'</td>'
						+'<td index="1">'+accounting.formatNumber(fstwaiting)+'</td>'
						+'<td index="4">'+accounting.formatNumber(fstiqcPass)+'</td>'
						+'<td index="5">'+accounting.formatNumber(fsthold)+'</td>'
						+'<td index="6">'+accounting.formatNumber(fsttotalWip)+'</td>'
						+'<td index="7">'+accounting.formatNumber(fstswr)+'</td>'
						+'<td index="8">'+accounting.formatNumber(fstoutYesterday)+'</td>'
						+'<td index="9">'+accounting.formatNumber(fstout1stShift)+'</td>'
						+'<td index="10">'+accounting.formatNumber(fstout2ndShift)+'</td>'
						+'<td index="11">'+accounting.formatNumber(fsttotalOut)+'</td>'
						+'</tr>';
					tableContent+=processTd+tdstr;
					totalwaiting+=parseFloat(fstwaiting);
					totalrunning+=parseFloat(fstrunning);
					totalOutSpec+=parseFloat(fstOutSpec);
					totalcusHold+=parseFloat(fstcusHold);
					totalengDisp+=parseFloat(fstengDisp);
					totaliqcPass+=parseFloat(fstiqcPass);
					totalhold+=parseFloat(fsthold);
					totaltotalWip+=parseFloat(fsttotalWip);
					totalswr+=parseFloat(fstswr);
					totaloutYesterday+=parseFloat(fstoutYesterday);
					totalout1stShift+=parseFloat(fstout1stShift);
					totalout2ndShift+=parseFloat(fstout2ndShift);
					totaltotalOut+=parseFloat(fsttotalOut);
				}
			var totalTd='<tr class="tableadd">'
			+'<td class="total-firstTd">Total Wip and Output</td>'
			+'<td index="1">'+accounting.formatNumber(totalwaiting)+'</td>'
			+'<td index="4">'+accounting.formatNumber(totaliqcPass)+'</td>'
			+'<td index="5">'+accounting.formatNumber(totalhold)+'</td>'
			+'<td index="6">'+accounting.formatNumber(totaltotalWip)+'</td>'
			+'<td index="7">'+accounting.formatNumber(totalswr)+'</td>'
			+'<td index="8">'+accounting.formatNumber(totaloutYesterday)+'</td>'
			+'<td index="9">'+accounting.formatNumber(totalout1stShift)+'</td>'
			+'<td index="10">'+accounting.formatNumber(totalout2ndShift)+'</td>'
			+'<td index="11">'+accounting.formatNumber(totaltotalOut)+'</td>'
			+'</tr>';
			$("#wipMainContent table").append(totalTd);
			$("#wipMainContent table").append(tableContent);
			animationbind();
			});
			break;
		
	}

}
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
function trackOutParamToJson(paramArray)
{
	var jsonObj={};
	var data={};
	var rejectCode=[];
	$(paramArray).each(function()
		{
			if(this.name.length<5)
			{
				var inobj={};
				inobj['id']=this.name;
				if(this.value!='')
					{
						inobj['value']=this.value;
					}
				else{
					inobj['value']="0";
				}
				rejectCode.push(inobj);
			}
			else
			data[this.name]=this.value;
		});
	jsonObj['userName']=data['userName'];
	jsonObj['password']=data['password'];
	data['rejectCode']=rejectCode;
	jsonObj['data']=data;
	delete data['userName'];
	delete data['password'];
	console.log(jsonObj);
	return jsonObj;
}
function gridchange(grid){
	grid.data('koala.grid',null);
	grid.empty();
}
/*后台信息回传判断方法*/
function messagecheck(e,result,dialog){
    if(result.result == 'success'){
        dialog.modal('hide');
        e.data.grid.data('koala.grid').refresh();
        e.data.grid.message({
            type: 'success',
            content: '保存成功'
         });
    }else if(result.result == 'fail'){
        grid.data('koala.grid').refresh();
        grid.message({
            type: 'error',
            content: '用户名密码错误'
        });
    }
    else if(result.result == 'noRight'){
        grid.data('koala.grid').refresh();
        grid.message({
            type: 'error',
            content: '无当前操作权限'
         });
    }
    else if(result.result == 'Out-Spec'){
    	dialog.modal('hide');
        e.data.grid.data('koala.grid').refresh();
       grid.message({
           type: 'error',
           content: '良率过低'
        });
    }else{
        dialog.find('.modal-content').message({
            type: 'error',
            content: result.actionError
        });
    }
}
function HoldReasonSearch()
{
	$(".table").bind("mouseover", function(){
		$("div[id^='currStatus']").unbind();
		for(var i=0;i<$("div[id^='currStatus']").length;i++)
		{
			if($("div[id^='currStatus']").eq(i).attr("style")!=undefined&&$("div[id^='currStatus']").eq(0).text()!="Waiting"&&$("div[id^='currStatus']").eq(0).text()!="Running")
				{
					$("div[id^='currStatus']").eq(i).css("cursor","pointer");
					$("div[id^='currStatus']").eq(i).bind('click', function(e){
						debugger;
						var dialog = $('<div class="modal fade"><div style="width:800px" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Hold Reason</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
						dialog.modal({
			                keyboard:false 
			            }).on({
			                'hidden.bs.modal': function(){
			                    $(this).remove();
			                }
			            });
						var id=$(this).attr('id').replace(/[^0-9]/ig,"");
						dialog.find('.modal-body').html('<form class="form-horizontal"></form>');
						$.get('/ManufactureProcess/getHoldReason/'+id+'.koala').done(function(json){
							debugger;
							json=json['data'];
							dialog.find('.form-horizontal').append('<div class="form-group"><label class="col-lg-2 control-label">Reason:</label><div class="col-lg-9"></div>');
							dialog.find(".col-lg-9").text(json['comments']);
						});
					});
				}
		}
	});
}
function HoldReasonSearchWafer()
{
	$(".table").bind("mouseover", function(){
		$("div[id^='currStatus']").unbind();
		for(var i=0;i<$("div[id^='currStatus']").length;i++)
		{
			if($("div[id^='currStatus']").eq(i).attr("style")!=undefined&&$("div[id^='currStatus']").eq(i).text()!="Waiting")
				{
					$("div[id^='currStatus']").eq(i).css("cursor","pointer");
					$("div[id^='currStatus']").eq(i).bind('click', function(e){
						debugger;
						var dialog = $('<div class="modal fade"><div style="width:800px" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Hold Reason</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
						dialog.modal({
			                keyboard:false
			            }).on({
			                'hidden.bs.modal': function(){
			                    $(this).remove();
			                }
			            });
						var id=$(this).attr('id').replace(/[^0-9]/ig,"");
						dialog.find('.modal-body').html('<form class="form-horizontal"></form>');
						$.get('/WaferProcess/getHoldReason/'+id+'.koala').done(function(json){
							debugger;
							json=json['data'];
							dialog.find('.form-horizontal').append('<div class="form-group"><label class="col-lg-2 control-label">Reason:</label><div class="col-lg-9"></div>');
							dialog.find(".col-lg-9").text(json['comments']);
						});
					});
				}
		}
	});
}
function HoldReasonSearchSub(){
	$(".table").bind("mouseover", function(){
		$("div[id^='currStatus']").unbind();
		for(var i=0;i<$("div[id^='currStatus']").length;i++)
		{
			if($("div[id^='currStatus']").eq(i).attr("style")!=undefined&&$("div[id^='currStatus']").eq(i).text()!="Waiting")
				{
					$("div[id^='currStatus']").eq(i).css("cursor","pointer");
					$("div[id^='currStatus']").eq(i).bind('click', function(e){
						debugger;
						var dialog = $('<div class="modal fade"><div style="width:800px" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Hold Reason</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
						dialog.modal({
			                keyboard:false
			            }).on({
			                'hidden.bs.modal': function(){
			                    $(this).remove();
			                }
			            });
						var id=$(this).attr('id').replace(/[^0-9]/ig,"");
						dialog.find('.modal-body').html('<form class="form-horizontal"></form>');
						$.get('/SubstrateProcess/getHoldReason/'+id+'.koala').done(function(json){
							debugger;
							json=json['data'];
							dialog.find('.form-horizontal').append('<div class="form-group"><label class="col-lg-2 control-label">Reason:</label><div class="col-lg-9"></div>');
							dialog.find(".col-lg-9").text(json['comments']);
						});
					});
				}
		}
	});
}
function HoldReasonSearchMaterial()
{
	$(".table").bind("mouseover", function(){
		$("div[id^='currStatus']").unbind();
		for(var i=0;i<$("div[id^='currStatus']").length;i++)
		{
			if($("div[id^='currStatus']").eq(i).attr("style")!=undefined&&$("div[id^='currStatus']").eq(i).text()!="Waiting")
				{
					$("div[id^='currStatus']").eq(i).css("cursor","pointer");
					$("div[id^='currStatus']").eq(i).bind('click', function(e){
						debugger;
						var dialog = $('<div class="modal fade"><div style="width:800px" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Hold Reason</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
						dialog.modal({
			                keyboard:false
			            }).on({
			                'hidden.bs.modal': function(){
			                    $(this).remove();
			                }
			            });
						var id=$(this).attr('id').replace(/[^0-9]/ig,"");
						dialog.find('.modal-body').html('<form class="form-horizontal"></form>');
						$.get('/MaterialProcess/getHoldReason/'+id+'.koala').done(function(json){
							debugger;
							json=json['data'];
							dialog.find('.form-horizontal').append('<div class="form-group"><label class="col-lg-2 control-label">Reason:</label><div class="col-lg-9"></div>');
							dialog.find(".col-lg-9").text(json['comments']);
						});
					});
				}
		}
	});
}
function funddatalistoption(a,b)
{
	return $('#'+a+' option[value="'+$("."+b).val()+'"]');
}
function waferInfoBind(){
	$(".table").bind("mouseover", function(){
		$("td[index='1']").unbind();
		$("td[index='1']").bind('mouseover', function(e){
			$(this).css("color","#0094c8");
		});
		$("td[index='1']").bind('mouseout', function(e){
			$(this).css("color","#333");
		});
		$("td[index='1']").css("cursor","pointer");
		$("td[index='1']").bind('click', function(e){
			debugger;
			var dialog = $('<div class="modal fade"><div style="width:800px" class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">Wafer Information</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div></div></div></div>');
			dialog.modal({
                keyboard:false
            }).on({
                'hidden.bs.modal': function(){
                    $(this).remove();
                }
            });
			var id=$(this).parent().children().children('div').attr("data-value");
			dialog.find('.modal-body').html('<form class="form-horizontal"></form>');
			$.get('/WaferInfo/getWaferInfo/'+id+'.koala').done(function(json){
				debugger;
				json=json['data'];
				var k=1;
				for(var i=0;i<json.length;i++)
					{
						dialog.find('.form-horizontal').append('<div class="form-group"><label class="col-lg-2 control-label">Wafer'+k+':</label><div class="col-lg-5"><input name="Wafer'+json[i]['waferNumber']+'" style="display:inline; width:94%;" class="form-control" Placeholder="Input WaferId..." type="text"/></div><div class="col-lg-4"><input name="WaferQty'+json[i]['waferNumber']+'" style="display:inline; width:94%;" class="form-control" Placeholder="Input WaferQty..." type="text" /></div>');
						k++;
					}
	            var elm;
	            for(var i=0;i<=json.length;i++){
	                elm = dialog.find("input[name='Wafer"+json[i]['waferNumber']+"']");
	                elm.val(json[i]['waferId']);
	                elm = dialog.find("input[name='WaferQty"+json[i]['waferNumber']+"']");
	                elm.val(json[i]['dieQty']);
	            }
			});
          });
	});
}
/*
 * 印刷：302
 * 贴片：303
 * 目检：306
 * 晶圆进料检验：401
 * 切割：404
 * 上片：503,520,521
 * 焊线，三目视：602,603
 * 模压：702
 * 盖印：801
 * 成品切单：901
 * 四目检：902
*/
function Track_OutViewSelect(id,dialog)
{
	switch(id){
		
		case '302':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[SP01]印刷偏移 Shift</td><td><input type="text" name="SP01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[SP02]印刷短路 Bridge</td><td><input type="text" name="SP02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[SP03]印刷少锡 Insufficient Solder</td><td><input type="text" name="SP03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[SP04]其它 Other</td><td><input type="text" name="SP04" placeholder="输入文字..." ></td></tr>');
			break;
		case '303':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">[SM01]贴片偏移 Shift</td><td><input type="text" name="SM01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[SM02]贴片少件 Missing</td><td><input type="text" name="SM02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[SM03]贴片多件 Extra Component</td><td><input type="text" name="SM03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[SM04]其它 Other</td><td><input type="text" name="SM04" placeholder="输入文字..." ></td></tr>');
			break;
		case '306':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[SR01]锡桥 Bridge</td><td><input type="text" name="SR01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[SR02]焊锡不足 Insufficient Solder</td><td><input type="text" name="SR02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[SR03]多件 Extra  Component</td><td><input type="text" name="SR03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[SR04]元器件侧立,反片 Instead</td><td><input type="text" name="SR04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[SR05]极性反 Polarity Reversal</td><td><input type="text" name="SR05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[SR06]立碑 Tombstone</td><td><input type="text" name="SR06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[SR07]基板翘曲 Warpage</td><td><input type="text" name="SR07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[SR08]推力小 Shear Strength Small</td><td><input type="text" name="SR08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[SR09]浸润性不良空焊, 假焊Non Wetting</td><td><input type="text" name="SR09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[SR10]冷焊Cold Welding</td><td><input type="text" name="SR10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>14</td><td class="God_text-align-left">[SR11]基板墨点 Substrate Dots</td><td><input type="text" name="SR11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>15</td><td class="God_text-align-left">[SR12]少件 Missing</td><td><input type="text" name="SR12" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>16</td><td class="God_text-align-left">[SR13]焊锡球 Solder Beads</td><td><input type="text" name="SR13" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>17</td><td class="God_text-align-left">[SR14]锡膏污染 Solder Spatter</td><td><input type="text" name="SR14" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>18</td><td class="God_text-align-left">[SR15]助焊剂残留 Flux Residues</td><td><input type="text" name="SR15" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>19</td><td class="God_text-align-left">[SR16]元件偏移 Shift</td><td><input type="text" name="SR16" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>20</td><td class="God_text-align-left">[SR17]异物 Foreign Material</td><td><input type="text" name="SR17" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>21</td><td class="God_text-align-left">[SR18]元件破损 Component Damage</td><td><input type="text" name="SR18" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>22</td><td class="God_text-align-left">[SR19]污染变色 Pollution Discoloration</td><td><input type="text" name="SR19" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>23</td><td class="God_text-align-left">[SR20]其它 Other</td><td><input type="text" name="SR20" placeholder="输入文字..." ></td></tr>');
			break;
		/*case '401':
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[WI01]探针痕迹Probe mark</td><td><input type="text" name="WI01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[WI02]墨迹 Ink</td><td><input type="text" name="WI02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[WI03]混料 Mixed Device</td><td><input type="text" name="WI03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[WI04]晶圆DIE PAD变色(wafer/DIE PAD discolor)</td><td><input type="text" name="WI04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[WI05]刮伤Scratch</td><td><input type="text" name="WI05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[WI06]晶圆破裂Wafer broken</td><td><input type="text" name="WI06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[WI07]碎片裂痕die crack</td><td><input type="text" name="WI07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[WI08]铝路缺铝Void of metallization</td><td><input type="text" name="WI08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[WI09]焊垫缺铝Pad void</td><td><input type="text" name="WI09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[WI10]铝路桥接Metal bridging</td><td><input type="text" name="WI10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>14</td><td class="God_text-align-left">[WI11]起泡或剥落Measling or blistering</td><td><input type="text" name="WI11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>15</td><td class="God_text-align-left">[WI12]铝腐蚀Corrosion</td><td><input type="text" name="WI12" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>16</td><td class="God_text-align-left">[WI13]焊垫氧化Bond pad oxidized</td><td><input type="text" name="WI13" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>17</td><td class="God_text-align-left">[WI14]保护层覆盖 Over coat</td><td><input type="text" name="WI14" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>18</td><td class="God_text-align-left">[WI15]外物污染 Foreigncontamination</td><td><input type="text" name="WI15" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>19</td><td class="God_text-align-left">[WI16]金属层对准 Metallization alignment</td><td><input type="text" name="WI16" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>20</td><td class="God_text-align-left">[WI17]其它 Other</td><td><input type="text" name="WI17" placeholder="输入文字..." ></td></tr>');
			break;*/
		case '404':
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[DS01]晶圆破裂Wafer Broken</td><td><input type="text" name="DS01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[DS02]晶片崩缺Die Chipping</td><td><input type="text" name="DS02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[DS03]焊点变色/腐蚀Die Pad Discolor/Corrosion</td><td><input type="text" name="DS03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[DS04]硅粉污染Si Stain</td><td><input type="text" name="DS04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[DS05]切割道偏移Kerf Width Offset</td><td><input type="text" name="DS05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[DS06]晶片飞失/脱落Die Missing</td><td><input type="text" name="DS06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[DS07]晶片污染Die Contamination</td><td><input type="text" name="DS07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[DS08]刮伤 Scratch</td><td><input type="text" name="DS08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[DS09]晶片护层掀起Die Peeling</td><td><input type="text" name="DS09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[DS10]其它 Other</td><td><input type="text" name="DS10" placeholder="输入文字..." ></td></tr>');
			break;
		case '301':
		case '304':
		case '308':
		case '309':
		case '305':
		case '413':
		case '414':
		case '415':
		case '502':
		case '601':
		case '605':
		case '701':
		case '703':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			break;
		case '504':
		case '507':
		case '509':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总量  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			break;
		case '604':
		case '505':
		case '307':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">抽样数量  Sample Size</td><td><input type="text" name="SampleQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			break;
		case '903':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">抽样数量  Sample Size</td><td><input type="text" name="SampleQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			break;
		case '1201':
		case '1202':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			break;
		case '508':
		case '506':
		case '503':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			//dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">晶圆颗数  Die Qty Out</td><td><input type="text" name="DieQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">不良总量  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[DA01]基板金属层不良 Sub Meter Layer NG</td><td><input type="text" name="DA01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[DA02]绿漆不良 Solder Mask NG</td><td><input type="text" name="DA02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[DA03]基板变形 Substrate Warpage</td><td><input type="text" name="DA03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[DA04]基板破裂 Substrate Broken</td><td><input type="text" name="DA04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[DA05]基板污染 Substrate Stain</td><td><input type="text" name="DA05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[DA06]基板异物 Sub Doreign Material</td><td><input type="text" name="DA06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[DA07]晶片破裂 Die Crack</td><td><input type="text" name="DA07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[DA08]晶片压伤 Die Imprint Damage</td><td><input type="text" name="DA08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[DA09]晶片污染 Die Contamination</td><td><input type="text" name="DA09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>14</td><td class="God_text-align-left">[DA10]墨点晶片 Ink Die</td><td><input type="text" name="DA10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>15</td><td class="God_text-align-left">[DA11]边缘晶片 Edge Die</td><td><input type="text" name="DA11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>16</td><td class="God_text-align-left">[DA12]氧化变色 Die Discolor</td><td><input type="text" name="DA12" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>17</td><td class="God_text-align-left">[DA13]溢胶 Epoxy Bleed Out</td><td><input type="text" name="DA13" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>18</td><td class="God_text-align-left">[DA14]晶片偏移 Die Offset</td><td><input type="text" name="DA14" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>19</td><td class="God_text-align-left">[DA15]晶片倾斜 Die Tilt</td><td><input type="text" name="DA15" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>20</td><td class="God_text-align-left">[DA16]异物 Foreign Material</td><td><input type="text" name="DA16" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>21</td><td class="God_text-align-left">[DA17]多上片 Double Die</td><td><input type="text" name="DA17" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>22</td><td class="God_text-align-left">[DA18]未上片 Unmount Die</td><td><input type="text" name="DA18" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>23</td><td class="God_text-align-left">[DA19]晶片刮伤 Die Scratch</td><td><input type="text" name="DA19" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>24</td><td class="God_text-align-left">[DA20]晶片丢失 Pick up Missing</td><td><input type="text" name="DA20" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>25</td><td class="God_text-align-left">[DA21]表面沾胶 Epoxy on Die Top </td><td><input type="text" name="DA21" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>26</td><td class="God_text-align-left">[DA22]胶量不足 Epoxy Short</td><td><input type="text" name="DA22" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>27</td><td class="God_text-align-left">[DA23]晶片原不良 Incoming Die Issue</td><td><input type="text" name="DA23" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>28</td><td class="God_text-align-left">[DA24]上错片 Wrong Die</td><td><input type="text" name="DA24" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>29</td><td class="God_text-align-left">[DA25]晶片方向错误 Wrong Die Orientation</td><td><input type="text" name="DA25" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>30</td><td class="God_text-align-left">[DA26]顶针印 Needle Mark</td><td><input type="text" name="DA26" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>31</td><td class="God_text-align-left">[DA27]晶片正面崩缺 Die Top Chipping</td><td><input type="text" name="DA27" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>32</td><td class="God_text-align-left">[DA28]晶片背面崩缺 Die Backside Chipping</td><td><input type="text" name="DA28" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>33</td><td class="God_text-align-left">[DA29]晶片脱落 Die Lifted</td><td><input type="text" name="DA29" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>34</td><td class="God_text-align-left">[DA30]产品混料 Product Mix</td><td><input type="text" name="DA30" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>35</td><td class="God_text-align-left">[DA31]SMT元件不良 SMT issue </td><td><input type="text" name="DA31" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>36</td><td class="God_text-align-left">[DA32]其它 Other</td><td><input type="text" name="DA32" placeholder="输入文字..." ></td></tr>');
			break;
		/*case '505':
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">抽样数量  Sample Qty Out</td><td><input type="text" name="SampleQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			break; */
		case '603':
		case '602':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[WB01]球径不符 Ball Diameter</td><td><input type="text" name="WB01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[WB02]球厚不符Ball Height</td><td><input type="text" name="WB02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[WB03]金球偏移 Ball offset</td><td><input type="text" name="WB03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[WB04]高尔夫球 Golf Ball</td><td><input type="text" name="WB04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[WB05]一焊点不粘 None stick on 1st bond</td><td><input type="text" name="WB05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[WB06]弹坑 Cratering</td><td><input type="text" name="WB06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[WB07]失铝 Peeling</td><td><input type="text" name="WB07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[WB08]鱼尾尺寸不良 Stitch size bad</td><td><input type="text" name="WB08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[WB09]二焊点不粘 None stitch on 2nd bond</td><td><input type="text" name="WB09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[WB10]Bump球上二焊点浮起 None stitch on bump ball</td><td><input type="text" name="WB10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>14</td><td class="God_text-align-left">[WB11]鱼尾断裂 Stitch Broken</td><td><input type="text" name="WB11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>15</td><td class="God_text-align-left">[WB12]点斜偏 Stitch Offset</td><td><input type="text" name="WB12" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>16</td><td class="God_text-align-left">[WB13]鱼尾重叠 Stitch Overlap</td><td><input type="text" name="WB13" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>17</td><td class="God_text-align-left">[WB14]金线受损 Wire damaded</td><td><input type="text" name="WB14" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>18</td><td class="God_text-align-left">[WB15]金球颈部断裂 Broken on Neck</td><td><input type="text" name="WB15" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>19</td><td class="God_text-align-left">[WB16]线型不良 Wire Sharp Issue</td><td><input type="text" name="WB16" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>20</td><td class="God_text-align-left">[WB17]重打线 Double wire</td><td><input type="text" name="WB17" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>21</td><td class="God_text-align-left">[WB18]残线 Remaining wire</td><td><input type="text" name="WB18" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>22</td><td class="God_text-align-left">[WB19]缺线 Missing wire</td><td><input type="text" name="WB19" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>23</td><td class="God_text-align-left">[WB20]未打线 No wire</td><td><input type="text" name="WB20" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>24</td><td class="God_text-align-left">[WB21]异物 Partical</td><td><input type="text" name="WB21" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>25</td><td class="God_text-align-left">[WB22]焊线错误 Wrong Bonding</td><td><input type="text" name="WB22" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>26</td><td class="God_text-align-left">[WB23]推拉力测试报废 Buyoff Reject</td><td><input type="text" name="WB23" placeholder="输入文字..." ></td></tr>');
			break;
		case '702':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[MD01]未灌满 Incomplete Fill</td><td><input type="text" name="MD01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[MD02]外部气孔 External Void/Bubble</td><td><input type="text" name="MD02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[MD03]胶体破损/裂缝 Mold Cracked Package</td><td><input type="text" name="MD03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[MD04]树脂溢胶 Mold Flash</td><td><input type="text" name="MD04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[MD05]背面污染 Back Contamination</td><td><input type="text" name="MD05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[MD06]金线冲弯 Wire Sweep</td><td><input type="text" name="MD06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[MD07]表面划伤 Package Surface Scratch</td><td><input type="text" name="MD07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[MD08]表面污染 Surface Contamination</td><td><input type="text" name="MD08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[MD09]基板翘曲 Substrate Warp</td><td><input type="text" name="MD09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[MD10]金线受损 Deformed /Damaged Wire</td><td><input type="text" name="MD10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>14</td><td class="God_text-align-left">[MD11]金线外露 Exposed Wire</td><td><input type="text" name="MD11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>15</td><td class="God_text-align-left">[MD12]晶片外露 Exposed Die</td><td><input type="text" name="MD12" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>16</td><td class="God_text-align-left">[MD13]晶片断裂 Cracked Die</td><td><input type="text" name="MD13" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>17</td><td class="God_text-align-left">[MD14]胶体断裂 Mold Cracked Package</td><td><input type="text" name="MD14" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>18</td><td class="God_text-align-left">[MD15]胶体缺口Chipped Package</td><td><input type="text" name="MD15" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>19</td><td class="God_text-align-left">[MD16]内部气洞 Internal Void</td><td><input type="text" name="MD16" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>20</td><td class="God_text-align-left">[MD17]流痕/污点/变色 Flow Mark /Stain/Discoloration</td><td><input type="text" name="MD17" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>21</td><td class="God_text-align-left">[MD18]基板脱层 Substrate Delamination</td><td><input type="text" name="MD18" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>22</td><td class="God_text-align-left">[MD19]胶体表面印痕 Package line imprint mark</td><td><input type="text" name="MD19" placeholder="输入文字..." ></td></tr>');
			break;
		case '801':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">基板条数  Strip Qty Out</td><td><input type="text" name="StripQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">良品颗数  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">不良总数  Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[MK01]印码缺失 Content Missing</td><td><input type="text" name="MK01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[MK02]印码偏移 Content Offset</td><td><input type="text" name="MK02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[MK03]重复印码 Double Mark</td><td><input type="text" name="MK03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[MK04]印码模糊 Illegible Mark</td><td><input type="text" name="MK04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[MK05]印码反向 Content Reverse</td><td><input type="text" name="MK05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[MK06]印码错误 Wrong Code/Character</td><td><input type="text" name="MK06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[MK07]印码刮伤 Content Scratch</td><td><input type="text" name="MK07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[MK08]印码污染 Content Contaminate</td><td><input type="text" name="MK08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[MK09]印码深度 Content Depth</td><td><input type="text" name="MK09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[MK10]印码断字 Character Broken</td><td><input type="text" name="MK10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>14</td><td class="God_text-align-left">[MK11]印码对齐样式 Mark Style</td><td><input type="text" name="MK11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>15</td><td class="God_text-align-left">[MK12]印码色差 Mark Discolor</td><td><input type="text" name="MK12" placeholder="输入文字..." ></td></tr>');
			break;
		case '901':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">良品数量  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">不良条数 Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">[SG01]切偏 PKG Offset</td><td><input type="text" name="SG01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[SG02]未切穿 Incomplete Saw Thru </td><td><input type="text" name="SG02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[SG03]产品丢失 Missing Units</td><td><input type="text" name="SG03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[SG04]异物附着 Foreign Matter Adhere</td><td><input type="text" name="SG04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[SG05]胶体受损 Substrate Crack</td><td><input type="text" name="SG05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[SG06]连接区域异常 Connect Area Unusual</td><td><input type="text" name="SG06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[SG07]共面性 Coplanarity</td><td><input type="text" name="SG07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[SG08]产品尺寸公差 PKG Size Out</td><td><input type="text" name="SG08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[SG09]缺角Package Chip</td><td><input type="text" name="SG09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[SG10]绿漆剥离 Solder Mask Crack</td><td><input type="text" name="SG10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[SG11]水痕残留 Water Exist</td><td><input type="text" name="SG11" placeholder="输入文字..." ></td></tr>');
			break;
		case '902':
			dialog.find('#God_Track_In_append').empty();
			dialog.find('#God_Track_In_append').append('<tr><th style="width:6%;text-align: center;">Order</th><th>Prompt</th><th style="width:15%;text-align: center;">Value</th></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>01</td><td class="God_text-align-left">良品数量  Good Qty Out</td><td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>02</td><td class="God_text-align-left">不良条数 Scraps Qty Out</td><td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>03</td><td class="God_text-align-left">[SG01]切偏 PKG Offset</td><td><input type="text" name="SG01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>04</td><td class="God_text-align-left">[SG02]未切穿 Incomplete Saw Thru </td><td><input type="text" name="SG02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>05</td><td class="God_text-align-left">[SG03]产品丢失 Missing Units</td><td><input type="text" name="SG03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>06</td><td class="God_text-align-left">[SG04]异物附着 Foreign Matter Adhere</td><td><input type="text" name="SG04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>07</td><td class="God_text-align-left">[SG05]胶体受损 Substrate Crack</td><td><input type="text" name="SG05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>08</td><td class="God_text-align-left">[SG06]连接区域异常 Connect Area Unusual</td><td><input type="text" name="SG06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>09</td><td class="God_text-align-left">[SG07]共面性 Coplanarity</td><td><input type="text" name="SG07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>10</td><td class="God_text-align-left">[SG08]产品尺寸公差 PKG Size Out</td><td><input type="text" name="SG08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>11</td><td class="God_text-align-left">[SG09]缺角Package Chip</td><td><input type="text" name="SG09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>12</td><td class="God_text-align-left">[SG10]绿漆剥离 Solder Mask Crack</td><td><input type="text" name="SG10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>13</td><td class="God_text-align-left">[SG11]水痕残留 Water Exist</td><td><input type="text" name="SG11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>14</td><td class="God_text-align-left">[SG01]切偏 PKG Offset</td><td><input type="text" name="SG01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>15</td><td class="God_text-align-left">[SG02]未切穿 Incomplete Saw Thru </td><td><input type="text" name="SG02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>16</td><td class="God_text-align-left">[SG03]产品丢失 Missing Units</td><td><input type="text" name="SG03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>17</td><td class="God_text-align-left">[SG04]异物附着 Foreign Matter Adhere</td><td><input type="text" name="SG04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>18</td><td class="God_text-align-left">[SG05]胶体受损 Substrate Crack</td><td><input type="text" name="SG05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>19</td><td class="God_text-align-left">[SG06]连接区域异常 Connect Area Unusual</td><td><input type="text" name="SG06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>20</td><td class="God_text-align-left">[SG07]共面性 Coplanarity</td><td><input type="text" name="SG07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>21</td><td class="God_text-align-left">[SG08]产品尺寸公差 PKG Size Out</td><td><input type="text" name="SG08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>22</td><td class="God_text-align-left">[SG09]缺角Package Chip</td><td><input type="text" name="SG09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>23</td><td class="God_text-align-left">[SG10]绿漆剥离 Solder Mask Crack</td><td><input type="text" name="SG10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>24</td><td class="God_text-align-left">[SG11]水痕残留 Water Exist</td><td><input type="text" name="SG11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>25</td><td class="God_text-align-left">[MK01]印码缺失 Content Missing</td><td><input type="text" name="MK01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>26</td><td class="God_text-align-left">[MK02]印码偏移 Content Offset</td><td><input type="text" name="MK02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>27</td><td class="God_text-align-left">[MK03]重复印码 Double Mark</td><td><input type="text" name="MK03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>28</td><td class="God_text-align-left">[MK04]印码模糊 Illegible Mark</td><td><input type="text" name="MK04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>29</td><td class="God_text-align-left">[MK05]印码反向 Content Reverse</td><td><input type="text" name="MK05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>30</td><td class="God_text-align-left">[MK06]印码错误 Wrong Code/Character</td><td><input type="text" name="MK06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>31</td><td class="God_text-align-left">[MK07]印码刮伤 Content Scratch</td><td><input type="text" name="MK07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>32</td><td class="God_text-align-left">[MK08]印码污染 Content Contaminate</td><td><input type="text" name="MK08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>33</td><td class="God_text-align-left">[MK09]印码深度 Content Depth</td><td><input type="text" name="MK09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>34</td><td class="God_text-align-left">[MK10]印码断字 Character Broken</td><td><input type="text" name="MK10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>35</td><td class="God_text-align-left">[MK11]印码对齐样式 Mark Style</td><td><input type="text" name="MK11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>36</td><td class="God_text-align-left">[MK12]印码色差 Mark Discolor</td><td><input type="text" name="MK12" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>37</td><td class="God_text-align-left">[MD01]未灌满 Incomplete Fill</td><td><input type="text" name="MD01" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>38</td><td class="God_text-align-left">[MD02]外部气孔 External Void/Bubble</td><td><input type="text" name="MD02" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>39</td><td class="God_text-align-left">[MD03]胶体破损/裂缝 Mold Cracked Package</td><td><input type="text" name="MD03" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>40</td><td class="God_text-align-left">[MD04]树脂溢胶 Mold Flash</td><td><input type="text" name="MD04" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>41</td><td class="God_text-align-left">[MD05]背面污染 Back Contamination</td><td><input type="text" name="MD05" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>42</td><td class="God_text-align-left">[MD06]金线冲弯 Wire Sweep</td><td><input type="text" name="MD06" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>43</td><td class="God_text-align-left">[MD07]表面划伤 Package Surface Scratch</td><td><input type="text" name="MD07" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>44</td><td class="God_text-align-left">[MD08]表面污染 Surface Contamination</td><td><input type="text" name="MD08" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>45</td><td class="God_text-align-left">[MD09]基板翘曲 Substrate Warp</td><td><input type="text" name="MD09" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>46</td><td class="God_text-align-left">[MD10]金线受损 Deformed /Damaged Wire</td><td><input type="text" name="MD10" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>47</td><td class="God_text-align-left">[MD11]金线外露 Exposed Wire</td><td><input type="text" name="MD11" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>48</td><td class="God_text-align-left">[MD12]晶片外露 Exposed Die</td><td><input type="text" name="MD12" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>49</td><td class="God_text-align-left">[MD13]晶片断裂 Cracked Die</td><td><input type="text" name="MD13" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>50</td><td class="God_text-align-left">[MD14]胶体断裂 Mold Cracked Package</td><td><input type="text" name="MD14" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>51</td><td class="God_text-align-left">[MD15]胶体缺口Chipped Package</td><td><input type="text" name="MD15" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>52</td><td class="God_text-align-left">[MD16]内部气洞 Internal Void</td><td><input type="text" name="MD16" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>53</td><td class="God_text-align-left">[MD17]流痕/污点/变色 Flow Mark /Stain/Discoloration</td><td><input type="text" name="MD17" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>54</td><td class="God_text-align-left">[MD18]基板脱层 Substrate Delamination</td><td><input type="text" name="MD18" placeholder="输入文字..." ></td></tr>');
			dialog.find('#God_Track_In_append').append('<tr><td>55</td><td class="God_text-align-left">[MD19]胶体表面印痕 Package line imprint mark</td><td><input type="text" name="MD19" placeholder="输入文字..." ></td></tr>');
			break;
		default:
			break;
	}
}
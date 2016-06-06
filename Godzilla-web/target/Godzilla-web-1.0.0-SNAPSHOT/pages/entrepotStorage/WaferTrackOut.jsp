<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<div class="form-group">
<div class="grid-body" style="width: 98%;margin: 0 auto;overflow:auto;">
<div class="grid-table-head" style="width: 100%;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="8%">Lot N.<div class="colResize"></div></th>
<th index="1" width="8%">Cus.Lot N.<div class="colResize"></div></th>
<th index="2" width="8%">Product Name<div class="colResize"></div></th>
<th index="2" width="7%">Part N.<div class="colResize"></div></th>
<th index="4" width="6%">Wafer #<div class="colResize"></div></th>
<th index="3" width="5%">Qty<div class="colResize"></div></th>
<th index="5" width="8%">Equipment<div class="colResize"></div></th>
<th index="7" width="6%">Lot Type<div class="colResize"></div></th>
<th index="8" width="8%">Cust Order<div class="colResize"></div></th>
<th index="9" width="9%">Entry Date<div class="colResize"></div></th>
<th index="10" width="8%">Entry Time<div class="colResize"></div></th>
<th index="11" width="6%">User<div class="colResize"></div></th>
<th index="12" width="7%">Curr.Status<div class="colResize"></div></th>
<th index="13" width="auto">Inv.Pos.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 100%; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped">
<tbody>
<tr class="success">
<td id="lotNoID" index="0" width="8%"></td>
<td id="customerLotNoID" index="1" width="8%"></td>
<td id="productNameID" index="2" width="8%"></td>
<td id="partNumberID" index="2" width="7%"></td>
<td id="waferQtyInID" index="4" width="6%"></td>
<td id="qtyInID" index="3" width="5%"></td>
<td id="equipmentID" index="5" width="8%"></td>
<td id="lotTypeID" index="7" width="6%"></td>
<td id="customerOrderID" index="8" width="8%"></td>
<td id="entryDateID" index="9" width="9%"></td>
<td id="entryTimeID" index="10" width="8%"></td>
<td id="optUserID" index="11" width="6%"></td>
<td id="statusID" index="12" width="7%"></td>
<td id="stockPosID" index="13" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Track Out:</p>
	           <div class="form-group God_trackOut_show">
                    <div class="col-lg-9 col-lg-offset-2 center" >
                    <div style="overflow: auto;width:94%;max-height:240px">
                    <!-- <table id="God_Track_In_append" class="table table-bordered God_Track_In">
						<tr>
							<th style="width:6%;text-align: center;">Order</th>
							<th>Prompt</th>
							<th style="width:15%;text-align: center;">Value</th>
						</tr>
						<tr>
							<td>01</td>
							<td class="God_text-align-left">晶圆片数  Wafer Qty Out</td>
							<td><input type="text" name="WaferQtyOut" placeholder="输入文字..." dataType="Require"></td>
						</tr>
						<tr>
							<td>02</td>
							<td class="God_text-align-left">良品数量  Good Qty Out</td>
							<td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Require"></td>
						</tr>
						<tr>
							<td>03</td>
							<td class="God_text-align-left">不良数量  Scraps Qty Out</td>
							<td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Require"></td>
						</tr>
					</table>
					 -->
					 <table id="God_Track_In_append" class="table table-bordered God_Track_In">
						<tr>
							<th style="width:10%;">Number</th>
							<th style="width:30%;">WaferId</th>
							<th>Die Qty</th>
							<th>Good Die</th>
							<th>Defective Die</th>
							<th class="rejectDes" style="display:none;">Reject Description</th>
						</tr>
					</table>
					</div>
    </div>
</div>
			<div class="form-group God_split_hide God_debit_cotent">
				<label class="col-lg-2 control-label God_debit_Direct">Direct Mat:</label>
				<div class="col-lg-4">
				<p>
						<input class="God_debit_direct" type="text" placeholder="Material.." list="God_debit_directList">
						<datalist id="God_debit_directList">
							<select>
							</select>
						</datalist>
						<input class="God_debit_directQty" type="text" placeholder="Debit Qty..">
						<span class="God_debit_directAdd God_debit_add glyphicon glyphicon-plus-sign"></span>	
				</p>
				</div>
				<label class="col-lg-2 control-label God_debit_InDirect" style="width:10%">InDirect Mat:</label>
				<div class="col-lg-4">
				<p>
						<input class="God_debit_indirect" type="text" placeholder="Material.." list="God_debit_indirectList">
						<datalist id="God_debit_indirectList">
							<select>
							</select>
						</datalist>
						<input class="God_debit_indirectQty" type="text" placeholder="Debit Qty.." >
						<span class="God_debit_indirectAdd God_debit_add glyphicon glyphicon-plus-sign"></span>
				</p>
				</div>
			</div>
			<div class="form-group God_split_hide God_debit_cotent">
				<label class="col-lg-2 control-label God_debit_wafer">Wafer:</label>
				<div class="col-lg-9">
					<p>
						<input placeholder="Lot Number.." type="text" class="God_debit_lotNO" list="God_debit_lotNumberList"/>
						<datalist id="God_debit_lotNumberList">
							<select>
							</select>
						</datalist>
						<input class="God_debit_waferId" type="text" placeholder="Wafer Id.." list="God_debit_waferIdList">
						<datalist id="God_debit_waferIdList">
							<select>
							</select>
						</datalist>
						<input class="God_debit_dieQty" type="text" placeholder="Debit Qty.." >
						<span class="God_debit_waferAdd God_debit_add glyphicon glyphicon-plus-sign"></span>
					</p>
					</div>
			</div>
			<div class="form-group God_split_hide God_debit_cotent">
				<label class="col-lg-2 control-label God_debit_wafer">Consume Infomation:</label>
				<div class="col-lg-9">
					<div style="overflow: auto;width:94%;max-height:170px">
						<table id="God_debit_viewTable" class="table table-bordered God_Track_In">
							<tr>
								<th>Lot No.</th>
								<th>Wafer Id</th>
								<th>Qty</th>
								<th>Debit Qty</th>
							</tr>
						</table>
					</div>
				</div>
			</div>
	           <div class="form-group God_split_hide">
                    <label class="col-lg-2 control-label">User Name:</label>
                    <div class="col-lg-9">
                           <input name="userName" style="display:inline; width:94%;" class="form-control"  type="text"  id="userNameID" dataType=""/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group God_split_hide">
                    <label class="col-lg-2 control-label">Pass Word:</label>
                    <div class="col-lg-9">
                           <input name="password" style="display:inline; width:94%;" class="form-control"  type="password"  id="passwordID" dataType=""/>
                           <span class="required">*</span>
    </div>
</div>
</form>
<script type="text/javascript">
var selectItems = {};
var contents = [{title:'请选择', value: ''}];
$.ajax({
	async:false,
	url: '${pageContext.request.contextPath}/Customer/all.koala',
	type: 'POST',
	dataType: 'json',
})
.done(function(msg) {
	for (var i=0;i<msg['data'].length;i++)
	{
		contents.push({title:msg['data'][i]['customerCode'] , value: msg['data'][i]['id']});
	}
	selectItems['starFlowID'] = contents;
});
</script>
</body>
</html>
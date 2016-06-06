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
<th index="0" width="10%">Lot N.<div class="colResize"></div></th>
<th index="1" width="8%">Product Name<div class="colResize"></div></th>
<th index="2" width="10%">Equipment<div class="colResize"></div></th>
<th index="4" width="6%">Strip #<div class="colResize"></div></th>
<th index="3" width="5%">Qty<div class="colResize"></div></th>
<th index="6" width="6%">Lot Type<div class="colResize"></div></th>
<th index="7" width="8%">Sub. Batch<div class="colResize"></div></th>
<th index="8" width="9%">Entry Date<div class="colResize"></div></th>
<th index="9" width="8%">Entry Time<div class="colResize"></div></th>
<th index="10" width="6%">User<div class="colResize"></div></th>
<th index="11" width="7%">Curr.Status<div class="colResize"></div></th>
<th index="12" width="auto">Inv Loc.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 100%; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped">
<tbody>
<tr class="success">
<td id="lotNoID" index="0" width="10%"></td>
<td id="productNameID" index="1" width="8%"></td>
<td id="equipmentID" index="2" width="10%"></td>
<td id="stripQtyInID" index="4" width="6%"></td>
<td id="qtyInID" index="3" width="5%"></td>
<td id="lotTypeID" index="6" width="6%"></td>
<td id="subBatchID" index="7" width="8%"></td>
<td id="entryDateID" index="8" width="9%"></td>
<td id="entryTimeID" index="9" width="8%"></td>
<td id="userNameID" index="10" width="6%"></td>
<td id="statusID" index="11" width="7%"></td>
<td id="stockPosID" index="12" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p class="God_trackOut_show">Reject Code:</p>
<p class="God_split_hide">Consume Infomation:</p>
<p class="God_split_hide_user">Track Out:</p>
	           <div class="form-group God_trackOut_show">
                    <div class="col-lg-9 col-lg-offset-2 center" >
                    <div style="overflow: auto;width:94%;max-height:240px">
                    <table id="God_Track_In_append" class="table table-bordered God_Track_In">
						<tr>
							<th style="width:6%;text-align: center;">Order</th>
							<th>Prompt</th>
							<th style="width:15%;text-align: center;">Value</th>
						</tr>
						<tr>
							<td>01</td>
							<td class="God_text-align-left">晶圆片数  Wafer Qty Out</td>
							<td><input type="text" name="WaferQtyOut" placeholder="输入文字..." dataType="Number"></td>
						</tr>
						<tr>
							<td>02</td>
							<td class="God_text-align-left">良品数量  Good Qty Out</td>
							<td><input type="text" name="GoodQtyOut" placeholder="输入文字..." dataType="Number"></td>
						</tr>
						<tr>
							<td>03</td>
							<td class="God_text-align-left">不良数量  Scraps Qty Out</td>
							<td><input type="text" name="ScrapsQtyOut" placeholder="输入文字..." dataType="Number"></td>
						</tr>
					</table>
					</div>
    </div>
</div>
			<div class="form-group God_split_hide God_debit_cotent">
				<label class="col-lg-1 control-label God_debit_wafer" style="padding-top: 4px;margin-left:20px">Wafer:</label>
					<div class="col-lg-4" style="width:29%">
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
							<span class="God_debit_waferAdd God_debit_add glyphicon glyphicon-plus-sign"></span>
						</p>
					</div>
				<label class="col-lg-1 control-label God_debit_Direct" style="padding-top: 4px;">Material:</label>
				<div class="col-lg-2">
				<p>
						<input class="God_debit_direct" type="text" placeholder="Material.." list="God_debit_directList">
						<datalist id="God_debit_directList">
							<select>
							</select>
						</datalist>
						<span class="God_debit_directAdd God_debit_add glyphicon glyphicon-plus-sign"></span>	
				</p>
				</div>
			</div>
<p class="God_split_hide God_Wafer_Consume_table">Wafer Consume:</p>
			<div class="form-group God_split_hide God_debit_cotent God_Wafer_Consume_table">
				<div class="col-lg-12">
					<div style="overflow: auto;max-height:170px">
						<table id="God_debit_viewTable" class="table table-bordered God_Track_In">
							<tr>
								<th style="width: 15%;">Lot No.</th>
								<th>Cus.Lot N.</th>
								<th>Part Name</th>
								<th>Product Name</th>
								<th>Lot Type.</th>
								<th>Cust Order</th>
								<th>Wafer Id</th>
								<th style="width: 8%;">Qty</th>
								<th style="width: 10%;">Consume Qty</th>
							</tr>
						</table>
					</div>
				</div>
			</div>
<p class="God_split_hide God_Material_Consume_table">Material Consume:</p>		
			<div class="form-group God_split_hide God_debit_cotent God_Material_Consume_table">
				<div class="col-lg-12">
					<div style="overflow: auto;max-height:170px">
						<table id="God_debit_viewTable" class="table table-bordered God_Track_In">
							<tr>
								<th style="width: 8%;">Material No.</th>
								<th>Part N.</th>
								<th>Partname_cn</th>
								<th>Batch N.</th>
								<th>Unit</th>
								<th>Vender</th>
								<th>Po N.</th>
								<th >InQty</th>
								<th>In Cap.</th>
								<th>Balance</th>
								<th>Yield</th>
								<th style="width: 8%;">Consume Qty</th>
								<th style="width: 10%;">Consume Balance</th>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
	           <div class="form-group God_split_hide_user">
                    <label class="col-lg-2 control-label">User Name:</label>
                    <div class="col-lg-9">
                           <input name="userName" style="display:inline; width:94%;" class="form-control"  type="text"  id="userNameID" dataType=""/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group God_split_hide_user">
                    <label class="col-lg-2 control-label">Pass Word:</label>
                    <div class="col-lg-9">
                           <input name="password" style="display:inline; width:94%;" class="form-control"  type="password"  id="passwordID" dataType=""/>
                           <span class="required">*</span>
    </div>
</div>
</form>
</body>
</html>
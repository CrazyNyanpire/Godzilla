<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<div><p style="width:56px;margin:0 auto;font-size: 16px;margin-bottom: 10px;">Lot List</p></div>
<div class="form-group">
<div class="grid-body" style="width: 98%;margin: 0 auto;overflow: auto;">
<div class="grid-table-head" style="width: 100%;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="6%">Vender<div class="colResize"></div></th>
<th index="1" width="8%">Lot N.<div class="colResize"></div></th>
<th index="2" width="6%">Sup.Lot N.<div class="colResize"></div></th>
<th index="3" width="6%">Part N.<div class="colResize"></div></th>
<th index="4" width="6%">Batch N.<div class="colResize"></div></th>
<th index="6" width="6%">Strip #<div class="colResize"></div></th>
<th index="5" width="6%">Qty<div class="colResize"></div></th>
<th index="7" width="6%">Po N.<div class="colResize"></div></th>
<th index="8" width="8%">Entry Date<div class="colResize"></div></th>
<th index="9" width="8%">Entry Time<div class="colResize"></div></th>
<th index="11" width="8%">Expiry Date<div class="colResize"></div></th>
<th index="12" width="6%">User<div class="colResize"></div></th>
<th index="13" width="7%">Curr.Status<div class="colResize"></div></th>
<th index="14" width="auto">Stock Pos.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 100%; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped" style="margin-bottom:0;">
<tbody>
<tr class="success">
<td id="venderNameID" index="0" width="6%"></td>
<td id="lotNoID" index="1" width="8%"></td>
<td id="customerLotNoID" index="2" width="6%"></td>
<td id="partNumberID" index="3" width="6%"></td>
<td id="batchNoID" index="4" width="6%"></td>
<td id="stripQtyInID" index="6" width="6%"></td>
<td id="qtyInID" index="5" width="6%"></td>
<td id="ponID" index="7" width="6%"></td>
<td id="entryDateID" index="8" width="8%"></td>
<td id="entryTimeID" index="9" width="8%"></td>
<td id="guaranteePeriodID" index="11" width="8%"></td>
<td id="userNameID" index="12" width="6%"></td>
<td id="statusID" index="13" width="7%"></td>
<td id="stockPosID" index="14" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Review Data And Comment:</p>
  <!-- <div class="form-group">
    <label class="col-lg-2 control-label">Step:</label>
    <div class="col-lg-9">
    <div class="btn-group select" id="stepID"></div>
      <input type="hidden" name="step" id="stepID_" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div> -->
  	           <div class="form-group">
                    <label class="col-lg-2 control-label">Start Flow:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="startFlowID"></div>
                           <input type="hidden" name="startFlow" id="startFlowID_" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
  			  <div class="form-group SubOutsourcing" >
    				<label class="col-lg-2 control-label">Cus.Code:</label>
    				<div class="col-lg-9">
    				<div class="btn-group select" id="customerIdID"></div>
      					<input type="hidden" name="customerId" id="customerIdID_" dataType="Require"/>
      					<span class="required">*</span>
    </div>
  </div>
	           <div class="form-group SubOutsourcing">
                    <label class="col-lg-2 control-label">Product Name:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="productIdID"></div>
                           <input name="productId" type="hidden"  id="productIdID_" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>

<div class="form-group SubOutsourcing">
                    <label class="col-lg-2 control-label">Pack Size:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="packSizeID"></div>
                           <input name="packSizeId" type="hidden"  id="packSizeID_" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Comments:</label>
                    <div class="col-lg-9">
                           <textarea name="comments" style="display:inline; width:94%;" class="form-control"  type="text"  id="commentsID" dataType="Require"></textarea>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">User Name:</label>
                    <div class="col-lg-9">
                           <input name="userName" style="display:inline; width:94%;" class="form-control"  type="text"  id="userNameID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Pass Word:</label>
                    <div class="col-lg-9">
                           <input name="password" style="display:inline; width:94%;" class="form-control"  type="password"  id="passwordID" dataType="Require"/>
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
	selectItems['stepID'] = contents;
});
var contents = [{title:'请选择', value: ''}];
$.ajax({
	async:false,
	url: '${pageContext.request.contextPath}/DefineStationProcess/findDefineStationProcessesByType/6.koala',
	type: 'POST',
	dataType: 'json',
})
.done(function(msg) {
	for (var i=0;i<msg['data'].length;i++)
	{
		contents.push({title:msg['data'][i]['defineStationProcessName'] , value: msg['data'][i]['id']});
	}
	selectItems['startFlowID'] = contents;
});
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
	selectItems['customerIdID'] = contents;
});
var contents = [{title:'请选择', value: ''}];
$.ajax({
	async:false,
	url: '${pageContext.request.contextPath}/Product/all.koala',
	type: 'POST',
	dataType: 'json',
})
.done(function(msg){
	debugger;
		for (var i=0;i<msg['data'].length;i++)
	{
		contents.push({title:msg['data'][i]['productName'] , value: msg['data'][i]['id']});
	}
		selectItems['productIdID'] = contents;
	});

var contents = [{title:'请选择', value: ''}];
contents.push({title:'CP PASS' , value: '1'});
contents.push({title:'Pending CP' , value: '2'});
selectItems['waferStatusIdID'] = contents;
</script>
</body>
</html>
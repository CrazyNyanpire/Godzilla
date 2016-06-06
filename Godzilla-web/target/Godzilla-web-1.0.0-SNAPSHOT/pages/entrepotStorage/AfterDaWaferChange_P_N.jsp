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
<th index="3" width="5%">Qty<div class="colResize"></div></th>
<th index="4" width="6%">Strip #<div class="colResize"></div></th>
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
<td id="qtyInID" index="3" width="5%"></td>
<td id="stripQtyInID" index="4" width="6%"></td>
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
<p>Engineering Hold:</p>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Curr.Part Number:</label>
                    <div class="col-lg-9">
                           <input name="currPartNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="currPartNumberID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Change To Part Number:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="changePartNumberID"></div>
                           <input type="hidden" name="changePartNumberID" id="changePartNumberID_" dataType="Require"/>
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
                           <input name="password" style="display:inline; width:94%;" class="form-control"  type="text"  id="passwordID" dataType="Require"/>
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
	selectItems['changePartNumberID'] = contents;
});
</script>
</body>
</html>
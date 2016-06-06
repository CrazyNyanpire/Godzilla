<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<div class="form-group">
<div class="grid-body" style="width: 98%;margin: 0 auto;overflow: auto;">
<div class="grid-table-head" style="width: 100%;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="6%">Material<div class="colResize"></div></th>
<th index="1" width="7%">Material No<div class="colResize"></div></th>
<th index="2" width="10%">Part N.<div class="colResize"></div></th>
<th index="3" width=10%">Partname_cn<div class="colResize"></div></th>
<th index="4" width="6%">Batch N.<div class="colResize"></div></th>
<th index="5" width="5%">InQty<div class="colResize"></div></th>

<th index="7" width="5%">In Cap.<div class="colResize"></div></th>
<th index="6" width="6%">Balance<div class="colResize"></div></th>
<th index="8" width="6%">Unit<div class="colResize"></div></th>
<th index="9" width="6%">Vender<div class="colResize"></div></th>
<th index="10" width="6%">Po N.<div class="colResize"></div></th>
<th index="11" width="8%">Entry Date<div class="colResize"></div></th>
<th index="12" width="8%">Entry Time<div class="colResize"></div></th>
<th index="15" width="auto">Stock Pos.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 100%; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped" style="margin-bottom: 0px;">
<tbody>
<tr class="success">
<td id="materialNameID" index="0" width="6%"></td>
<td id="lotNoID" index="1" width="7%"></td>
<td id="partIdID" index="2" width="10%"></td>
<td id="partNameCNID" index="3" width="10%"></td>
<td id="batchNoID" index="4" width="6%"></td>
<td id="qtyInID" index="5" width="5%"></td>

<td id="inCapacityID" index="7" width="5%"></td>
<td id="balanceID" index="6" width="6%"></td>
<td id="unitID" index="8" width="6%"></td>
<td id="venderNameID" index="9" width="6%"></td>
<td id="ponID" index="10" width="6%"></td>
<td id="entryDateID" index="11" width="8%"></td>
<td id="entryTimeID" index="12" width="8%"></td>
<td id="stockPosID" index="15" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Customer Hold:</p>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Hold Code:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="holdCodeIdID"></div>
                           <input type="hidden" name="holdCodeId" id="holdCodeIdID_" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Comment:</label>
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
                    <label class="col-lg-2 control-label">PassWord:</label>
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
	url: '${pageContext.request.contextPath}/SpartPartProcess/Process/getScrap.koala',
	type: 'POST',
	dataType: 'json',
})
.done(function(msg) {
	debugger;
	for (var i=0;i<msg['data'].length;i++)
	{
		contents.push({title:msg['data'][i]['name'] , value: msg['data'][i]['id']});
	}
	selectItems['holdCodeIdID'] = contents;
});
</script>
</body>
</html>
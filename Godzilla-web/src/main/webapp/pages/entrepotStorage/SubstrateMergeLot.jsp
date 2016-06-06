<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<div><p style="width:80px;margin:0 auto;font-size: 16px;margin-bottom: 10px;">Merge Lot</p></div>
<div class="form-group">
<div class="grid-body" style="width: 98%;margin: 0 auto;overflow:auto;">
<div class="grid-table-head" style="width: 100%;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="10%">Lot N.<div class="colResize"></div></th>
<th index="1" width="10%">Sup.Lot N.<div class="colResize"></div></th>
<th index="2" width="6%">Part N.<div class="colResize"></div></th>
<th index="3" width="7%">Batch N.<div class="colResize"></div></th>
<th index="7" width="6%">Strip #<div class="colResize"></div></th>	
<th index="4" width="7%">Qty<div class="colResize"></div></th>
<th index="5" width="6%">Step<div class="colResize"></div></th>
<th index="6" width="6%">Lot Type<div class="colResize"></div></th>

<th index="8" width="9%">Entry Date<div class="colResize"></div></th>
<th index="9" width="8%">Entry Time<div class="colResize"></div></th>
<th index="10" width="8%">User<div class="colResize"></div></th>
<th index="11" width="7%">Curr.Status<div class="colResize"></div></th>
<th index="12" width="auto">Inv.Loc.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 100%; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped">
<tbody>
<tr class="success">
<td id="lotNoID0" index="0" width="10%"></td>
<td id="customerLotNoID0" index="1" width="10%"></td>
<td id="partNumberID0" index="2" width="6%"></td>
<td id="batchNoID0" index="3" width="7%"></td>
<td id="stripQtyInID0" index="7" width="6%"></td>
<td id="qtyInID0" index="4" width="7%"></td>
<td id="stepID0" index="5" width="6%"></td>
<td id="lotTypeID0" index="6" width="6%"></td>

<td id="entryDateID0" index="8" width="9%"></td>
<td id="entryTimeID0" index="9" width="8%"></td>
<td id="userNameID0" index="10" width="8%"></td>
<td id="statusID0" index="11" width="7%"></td>
<td id="stockPosID0" index="12" width="auto"></td>
</tr>
<tr class="success">
<td id="lotNoID1" index="0" width="10%"></td>
<td id="customerLotNoID1" index="1" width="10%"></td>
<td id="partNumberID1" index="2" width="6%"></td>
<td id="batchNoID1" index="3" width="7%"></td>
<td id="stripQtyInID1" index="7" width="6%"></td>
<td id="qtyInID1" index="4" width="7%"></td>
<td id="stepID1" index="5" width="6%"></td>
<td id="lotTypeID1" index="6" width="6%"></td>

<td id="entryDateID1" index="8" width="9%"></td>
<td id="entryTimeID1" index="9" width="8%"></td>
<td id="userNameID1" index="10" width="8%"></td>
<td id="statusID1" index="11" width="7%"></td>
<td id="stockPosID1" index="12" width="auto"></td>
</tr>
</tr>
<tr>
<td colspan='13'><strong>Merge Lot</strong></td>
</tr>
<tr class="success">
<td id="lotNoID2" index="0" width="10%"></td>
<td id="customerLotNoID2" index="1" width="10%"></td>
<td id="partNumberID2" index="2" width="6%"></td>
<td id="batchNoID2" index="3" width="7%"></td>
<td id="stripQtyInID2" index="7" width="6%"></td>
<td id="qtyInID2" index="4" width="7%"></td>
<td id="stepID2" index="5" width="6%"></td>
<td id="lotTypeID2" index="6" width="6%"></td>

<td id="entryDateID2" index="8" width="9%"></td>
<td id="entryTimeID2" index="9" width="8%"></td>
<td id="userNameID2" index="10" width="8%"></td>
<td id="statusID2" index="11" width="7%"></td>
<td id="stockPosID2" index="12" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Manual Merge:Merge Information:</p>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Comments:</label>
                    <div class="col-lg-9">
                           <textarea name="comments" style="display:inline; width:94%;" class="form-control"  type="text"  id="commentsID" dataType="Require"></textarea>
                           <span class="required">*</span>
    </div>
</div>
  <div class="form-group">
    <label class="col-lg-2 control-label">Stock Pos:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="locationIdsID"></div>
      <input type="hidden" id="locationIdsID_"  name="locationIds" dataType="Require"/>
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
    contents.push({title:'Waiting' , value:'Waiting'});
    contents.push({title:'Hold' , value:'Hold'});
    contents.push({title:'Received' , value:'Received'});
    var contents = [{title:'请选择', value: ''}];
    $.ajax({
		async:false,
		url: '${pageContext.request.contextPath}/SubstrateLocation/all.koala',
		type: 'POST',
		dataType: 'json',
	})
	.done(function(msg){
   		for (var i=0;i<msg['data'].length;i++)
    	{
			contents.push({title:msg['data'][i]['loctionCode'] , value: msg['data'][i]['id']});
    	}
   		selectItems['locationIdsID'] = contents;
   	});
    //selectItems[''] = contents;
</script>
</body>
</html>
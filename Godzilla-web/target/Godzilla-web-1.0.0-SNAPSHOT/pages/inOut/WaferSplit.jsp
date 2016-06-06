<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="../../../css/multi-select.css" media="screen" rel="stylesheet" type="text/css">
</head>
<body>
<form class="form-horizontal">
<div><p style="width:80px;margin:0 auto;font-size: 16px;margin-bottom: 10px;">Split Lot</p></div>
<div class="form-group">
<div class="grid-body" style="width: 98%;margin: 0 auto;">
<div class="grid-table-head" style="width: 100%;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="8%">Cus.Code<div class="colResize"></div></th>
<th index="1" width="8%">Lot N.<div class="colResize"></div></th>
<th index="2" width="8%">Cus.Lot N.<div class="colResize"></div></th>
<th index="3" width="8%">Part Name<div class="colResize"></div></th>
<th index="5" width="8%">Wafer #<div class="colResize"></div></th>
<th index="4" width="8%">Qty<div class="colResize"></div></th>
<th index="6" width="8%">Cust Order<div class="colResize"></div></th>
<th index="7" width="10%">Entry Date<div class="colResize"></div></th>
<th index="8" width="8%">Entry Time<div class="colResize"></div></th>
<th index="9" width="8%">User<div class="colResize"></div></th>
<th index="10" width="8%">Curr.Status<div class="colResize"></div></th>
<th index="11" width="auto">Stock Pos.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 100%; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped" style="width: 100%;">
<tbody>
<tr class="success">
<td id="customerCodeID" index="0" width="8%"></td>
<td id="lotNoID" index="1" width="8%"></td>
<td id="customerLotNoID" index="2" width="8%"></td>
<td id="partNumberID" index="3" width="8%"></td>
<td id="waferQtyInID" index="5" width="8%"></td>
<td id="qtyInID" index="4" width="8%"></td>
<td id="customerOrderID" index="6" width="8%"></td>
<td id="entryDateID" index="7" width="10%"></td>
<td id="entryTimeID" index="8" width="8%"></td>
<td id="optUserID" index="9" width="8%"></td>
<td id="statusID" index="10" width="8%"></td>
<td id="stockPosID" index="11" width="auto"></td>
</tr>
<tr class="God_split_hide"><td colspan="12"><stronge>Split Lot</stronge></td></tr>
<tr class="success God_split_hide">
<td id="customerCodeID0" index="0" width="8%"></td>
<td id="lotNoID0" index="1" width="8%"></td>
<td id="customerLotNoID0" index="2" width="8%"></td>
<td id="partNumberID0" index="3" width="8%"></td>
<td id="waferQtyInID0" index="5" width="8%"></td>
<td id="qtyInID0" index="4" width="8%"></td>
<td id="customerOrderID0" index="6" width="8%"></td>
<td id="entryDateID0" index="7" width="10%"></td>
<td id="entryTimeID0" index="8" width="8%"></td>
<td id="optUserID0" index="9" width="8%"></td>
<td id="currStatusID0" index="10" width="8%"></td>
<td id="stockPosID0" index="11" width="auto"></td>
</tr>
<tr class="success God_split_hide">
<td id="customerCodeID1" index="0" width="8%"></td>
<td id="lotNoID1" index="1" width="8%"></td>
<td id="customerLotNoID1" index="2" width="8%"></td>
<td id="partNumberID1" index="3" width="8%"></td>
<td id="waferQtyInID1" index="5" width="8%"></td>
<td id="qtyInID1" index="4" width="8%"></td>
<td id="customerOrderID1" index="6" width="8%"></td>
<td id="entryDateID1" index="7" width="10%"></td>
<td id="entryTimeID1" index="8" width="8%"></td>
<td id="optUserID1" index="9" width="8%"></td>
<td id="currStatusID1" index="10" width="8%"></td>
<td id="stockPosID1" index="11" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Manual Split:Split Information:</p>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Wafer Information:</label>
                    <div class="col-lg-9">
                    <div id="God_split_hideselect" style="width:590px;;margin:0 auto;">
	                    <select multiple="multiple" id="my-select">
					    </select>
				    </div>
    </div>
</div>
<!--	           <div class="form-group">
                    <label class="col-lg-2 control-label">Wafer Qty:</label>
                    <div class="col-lg-9">
                           <input name="waferQty" style="display:inline; width:94%;" class="form-control"  type="text"  id="waferQtyID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Die Qty:</label>
                    <div class="col-lg-9">
                           <input name="dieQty" style="display:inline; width:94%;" class="form-control"  type="text"  id="dieQtyID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>-->
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
<script src="../../../js/jquery.multi-select.js" type="text/javascript"></script>
<script type="text/javascript">
    var selectItems = {};
    var contents = [{title:'请选择', value: ''}];
    contents.push({title:'Waiting' , value:'Waiting'});
    contents.push({title:'Hold' , value:'Hold'});
    contents.push({title:'Received' , value:'Received'});
    var contents = [{title:'请选择', value: ''}];
    $.ajax({
		async:false,
		url: '${pageContext.request.contextPath}/Location/all.koala',
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
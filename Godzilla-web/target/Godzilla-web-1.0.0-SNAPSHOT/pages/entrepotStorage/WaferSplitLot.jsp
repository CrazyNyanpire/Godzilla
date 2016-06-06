<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/multi-select.css" media="screen" rel="stylesheet" type="text/css">
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
<th index="9" width="8%">Entry Date<div class="colResize"></div></th>
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
<td id="entryDateID" index="9" width="8%"></td>
<td id="entryTimeID" index="10" width="8%"></td>
<td id="optUserID" index="11" width="6%"></td>
<td id="statusID" index="12" width="7%"></td>
<td id="stockPosID" index="13" width="auto"></td>
</tr>
<tr class="God_split_hide"><td colspan="14"><stronge>Split Lot</stronge></td></tr>
<tr class="success God_split_hide">
<td id="lotNoID" index="0" width="8%"></td>
<td id="customerLotNoID0" index="1" width="8%"></td>
<td id="productNameID0" index="2" width="8%"></td>
<td id="partNumberID0" index="2" width="6%"></td>
<td id="waferQtyInID0" index="4" width="6%"></td>
<td id="qtyInID0" index="3" width="5%"></td>
<td id="equipmentID0" index="5" width="8%"></td>
<td id="lotTypeID0" index="7" width="6%"></td>
<td id="customerOrderID0" index="8" width="8%"></td>
<td id="entryDateID0" index="9" width="8%"></td>
<td id="entryTimeID0" index="10" width="8%"></td>
<td id="optUserID0" index="11" width="6%"></td>
<td id="statusID0" index="12" width="7%"></td>
<td id="stockPosID0" index="13" width="auto"></td>
</tr>
<tr class="success God_split_hide">
<td id="lotNoID1" index="0" width="8%"></td>
<td id="customerLotNoID1" index="1" width="8%"></td>
<td id="productNameID1" index="2" width="7%"></td>
<td id="partNumberID1" index="2" width="6%"></td>
<td id="waferQtyInID1" index="4" width="6%"></td>
<td id="qtyInID1" index="3" width="5%"></td>
<td id="equipmentID1" index="5" width="8%"></td>
<td id="lotTypeID1" index="7" width="6%"></td>
<td id="customerOrderID1" index="8" width="7%"></td>
<td id="entryDateID1" index="9" width="8%"></td>
<td id="entryTimeID1" index="10" width="8%"></td>
<td id="optUserID1" index="11" width="6%"></td>
<td id="statusID1" index="12" width="7%"></td>
<td id="stockPosID1" index="13" width="auto"></td>
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
<script src="/js/jquery.multi-select.js" type="text/javascript"></script>
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
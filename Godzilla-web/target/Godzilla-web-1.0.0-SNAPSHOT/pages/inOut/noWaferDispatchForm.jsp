<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<div><p style="width:56px;margin:0 auto;font-size: 16px;margin-bottom: 10px;">Lot List</p></div>
<div class="form-group">
<div class="grid-body" style="width: 98%;margin: 0 auto;">
<div class="grid-table-head" style="width: 100%;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="8%">Cus.Code<div class="colResize"></div></th>
<th index="1" width="8%">Lot N.<div class="colResize"></div></th>
<th index="2" width="8%">Cus.Lot N.<div class="colResize"></div></th>
<th index="3" width="8%">Part Name<div class="colResize"></div></th>
<th index="4" width="8%">Qty<div class="colResize"></div></th>
<th index="5" width="8%">Wafer #<div class="colResize"></div></th>
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
<td id="qtyInID" index="4" width="8%"></td>
<td id="waferQtyInID" index="5" width="8%"></td>
<td id="customerOrderID" index="6" width="8%"></td>
<td id="entryDateID" index="7" width="10%"></td>
<td id="entryTimeID" index="8" width="8%"></td>
<td id="optUserID" index="9" width="8%"></td>
<td id="statusID" index="10" width="8%"></td>
<td id="stockPosID" index="11" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Review Data And Comment:</p>
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
</body>
</html>
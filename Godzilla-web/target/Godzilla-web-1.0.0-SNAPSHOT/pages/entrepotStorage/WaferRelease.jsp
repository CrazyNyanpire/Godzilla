<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils"%>
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
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Release:</p>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Engineering Disposition:</label>
                    <div class="col-lg-9">
                           <textarea name="comments" style="display:inline; width:94%;" class="form-control"  type="text"  id="commentsID" dataType="Require"></textarea>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Dispose Time:</label>
                    <div class="col-lg-9">
                           <input name="holdTime" style="display:inline; width:94%;" class="form-control" readonly value="<%=MyDateUtils.getTodayTime(MyDateUtils.formater)%>" type="text"  id="holdTimeID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-2 control-label">Engineer Name:</label>
                    <div class="col-lg-9">
                           <input name="engineerName" style="display:inline; width:94%;" class="form-control"  type="text"  id="engineerNameID" dataType="Chinese"/>
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
</script>
</body>
</html>
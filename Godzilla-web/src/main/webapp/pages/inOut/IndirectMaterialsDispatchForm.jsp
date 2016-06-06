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
<div class="grid-table-head" style="width: 150%;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="6%">Material<div class="colResize"></div></th>
<th index="1" width="6%">Material No<div class="colResize"></div></th>
<th index="2" width="6%">Part N.<div class="colResize"></div></th>
<th index="3" width="6%">Partname_cn<div class="colResize"></div></th>
<th index="4" width="6%">Batch N.<div class="colResize"></div></th>
<th index="5" width="6%">Qty<div class="colResize"></div></th>
<th index="6" width="6%">Unit<div class="colResize"></div></th>
<th index="7" width="6%">Vender<div class="colResize"></div></th>
<th index="8" width="6%">Po N.<div class="colResize"></div></th>
<th index="9" width="6%">Entry Date<div class="colResize"></div></th>
<th index="10" width="6%">Entry Time<div class="colResize"></div></th>
<th index="11" width="8%">Manufacture Date<div class="colResize"></div></th>
<th index="12" width="8%">Expiry Date<div class="colResize"></div></th>
<th index="13" width="6%">User<div class="colResize"></div></th>
<th index="14" width="6%">Curr.Status<div class="colResize"></div></th>
<th index="15" width="6%">Stock Pos.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 150%; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped" style="margin-bottom: 0px;">
<tbody>
<tr class="success">
<td id="materialNameID" index="0" width="6%"></td>
<td id="lotNoID" index="1" width="6%"></td>
<td id="partIdID" index="2" width="6%"></td>
<td id="partNameCNID" index="3" width="6%"></td>
<td id="batchNoID" index="4" width="6%"></td>
<td id="qtyInID" index="5" width="6%"></td>
<td id="unitID" index="6" width="6%"></td>
<td id="venderNameID" index="7" width="6%"></td>
<td id="ponID" index="8" width="6%"></td>
<td id="entryDateID" index="9" width="6%"></td>
<td id="entryTimeID" index="10" width="6%"></td>
<td id="productionDateID" index="11" width="8%"></td>
<td id="guaranteePeriodID" index="12" width="8%"></td>
<td id="userNameID" index="13" width="6%"></td>
<td id="statusID" index="14" width="6%"></td>
<td id="stockPosID" index="15" width="6%"></td>
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
                    <label class="col-lg-2 control-label">PassWord:</label>
                    <div class="col-lg-9">
                           <input name="password" style="display:inline; width:94%;" class="form-control"  type="password"  id="passwordID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
</form>
</body>
</html>
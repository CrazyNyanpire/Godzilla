<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<div><p style="width:80px;margin:0 auto;font-size: 16px;margin-bottom: 10px;">Split Lot</p></div>
<div class="form-group">
<div class="grid-body" style="width: 1290px;margin: 0 auto;">
<div class="grid-table-head" style="width: 1339px;">
<table class="table table-bordered"><tbody>
<tr>
<th index="0" width="106.71875px">Cus.Code<div class="colResize"></div></th>
<th index="1" width="106.71875px">Lot N.<div class="colResize"></div></th>
<th index="2" width="106.71875px">Sup.Lot N.<div class="colResize"></div></th>
<th index="3" width="106.71875px">Part N.<div class="colResize"></div></th>
<th index="4" width="106.71875px">Qty<div class="colResize"></div></th>
<th index="5" width="106.71875px">Wafer #<div class="colResize"></div></th>
<th index="6" width="106.71875px">Cust Order<div class="colResize"></div></th>
<th index="7" width="117.390625px">Entry Date<div class="colResize"></div></th>
<th index="8" width="117.390625px">Entry Time<div class="colResize"></div></th>
<th index="9" width="106.71875px">User<div class="colResize"></div></th>
<th index="10" width="106.71875px">Curr.Status<div class="colResize"></div></th>
<th index="11" width="auto">Stock Pos.<div class="colResize"></div></th>
</tr>
</tbody>
</table>
</div>
<div class="" style="width: 1339px; position: relative;">
<table class="table table-responsive table-bordered table-hover table-striped" style="width: 1339px;">
<tbody>
<tr class="success">
<td id="customerCodeID" index="0" width="106.71875px"></td>
<td id="lotNoID" index="1" width="106.71875px"></td>
<td id="customerLotNoID" index="2" width="106.71875px"></td>
<td id="partNumberID" index="3" width="106.71875px"></td>
<td id="stripQtyOutID" index="4" width="106.71875px"></td>
<td id="waferQtyOutID" index="5" width="106.71875px"></td>
<td id="customerOrderID" index="6" width="106.71875px"></td>
<td id="entryDateID" index="7" width="117.390625px"></td>
<td id="entryTimeID" index="8" width="117.390625px"></td>
<td id="optUserID" index="9" width="106.71875px"></td>
<td id="statusID" index="10" width="106.71875px"></td>
<td id="stockPosID" index="11" width="auto"></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<hr/>
<p>Manual Split:Split Information:</p>
	           <div class="form-group">
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
                    <label class="col-lg-2 control-label">PassWord:</label>
                    <div class="col-lg-9">
                           <input name="password" style="display:inline; width:94%;" class="form-control"  type="text"  id="passwordID" dataType="Require"/>
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
    //selectItems[''] = contents;
</script>
</body>
</html>
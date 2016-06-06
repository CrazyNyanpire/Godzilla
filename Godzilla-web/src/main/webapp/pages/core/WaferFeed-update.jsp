<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	<div class="form-group">
		<label class="col-lg-3 control-label">Cus.Name:</label>
		<div class="col-lg-9">
			<div class="btn-group select" id="customerIdID"></div>
			<input type="hidden" id="customerIdID_"  name="customerId" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">Cus.Lot N.:</label>
		<div class="col-lg-9">
			<input name="customerLotNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerLotNoID" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">Part N.:</label>
		<div class="col-lg-9">
			<div class="btn-group select" id="partIdID"></div>
			<input type="hidden" id="partIdID_"  name="partId" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	  <div class="form-group">
    <label class="col-lg-3 control-label">Product Name:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="productIdID"></div>
      <input type="hidden" id="productIdID_"  name="productId" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
	<div class="form-group">
		<label class="col-lg-3 control-label">Qty:</label>
		<div class="col-lg-9">
			<input name="qty" style="display:inline; width:94%;" class="form-control"  type="text"  id="qtyID" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	
	<div class="form-group">
		<label class="col-lg-3 control-label">1stPQty:</label>
		<div class="col-lg-9">
			<input name="firstPQty" style="display:inline; width:94%;" class="form-control"  type="text"  id="firstPQtyID" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">2ndPQty:</label>
		<div class="col-lg-9">
			<input name="secondPQty" style="display:inline; width:94%;" class="form-control"  type="text"  id="secondPQtyID" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">3rdPQty:</label>
		<div class="col-lg-9">
			<input name="thirdPQty" style="display:inline; width:94%;" class="form-control"  type="text"  id="thirdPQtyID" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">Wafer#:</label>
		<div class="col-lg-9">
			<input name="wafer" style="display:inline; width:94%;" class="form-control"  type="text"  id="waferID" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	  <div class="form-group">
    <label class="col-lg-3 control-label">Wafer Status:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="waferStatusIdID"></div>
      <input type="hidden" id="waferStatusIdID_"  name="waferStatusId" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
	<div class="form-group">
		<label class="col-lg-3 control-label">Cust.Order:</label>
		<div class="col-lg-9">
			<input name="customerOrder" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerOrderID"/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">Shipping Date:</label>
		<div class="col-lg-9">
			<input type="date" readonly class="form-control" style="display:inline; width:94%;" name="shippingDate" id="shippingDateID" >
		</div>
	</div>
	
	 <div class="form-group">
		<label class="col-lg-3 control-label">Delivery Date:</label>
		<div class="col-lg-9">
			<input type="date" readonly class="form-control" style="display:inline; width:94%;" name="deliveryDate" id="deliveryDateID" >
		</div>
	</div> 
	 
	<div class="form-group">
		<label class="col-lg-3 control-label">Remark:</label>
		<div class="col-lg-9">
			<input name="remark" style="display:inline; width:94%;" class="form-control"  type="text"  id="remarkID" />
		</div>
	</div>
  	           <div class="form-group">
                    <label class="col-lg-3 control-label">Manufacture Date:</label>
                    <div class="col-lg-9">
                           <input name="productionDate" style="display:inline; width:94%;" class="form-control"  type="date"  id="productionDateID"/>
    </div>
</div>
  	           <div class="form-group">
                    <label class="col-lg-3 control-label">Expiry Date:</label>
                    <div class="col-lg-9">
                           <input name="guaranteePeriod" style="display:inline; width:94%;" class="form-control"  type="date"  id="guaranteePeriodID"/>
    </div>
</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">Status:</label>
		<div class="col-lg-9">
			<div class="btn-group select" id="currStatusID"></div>
			<input name="currStatus" id="currStatusID_" type="hidden" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-lg-3 control-label">User Name:</label>
		<div class="col-lg-9">
			<input name="userName" style="display:inline; width:94%;" class="form-control"  type="text"  id="userNameID" dataType="Require"/>
			<span class="required">*</span>
		</div>
	</div>
	<div class="form-group">
    <label class="col-lg-3 control-label">Pass Word:</label>
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
			selectItems['customerIdID'] = contents;
		});
	    var contents = [{title:'请选择', value: ''}];
	    $.ajax({
			async:false,
			url: '${pageContext.request.contextPath}/Part/all.koala',
			type: 'POST',
			dataType: 'json',
		})
		.done(function(msg){
	   		for (var i=0;i<msg['data'].length;i++)
	    	{
				contents.push({title:msg['data'][i]['partNo'] , value: msg['data'][i]['id']});
	    	}
	   		selectItems['partIdID'] = contents;
	   	});
	    var contents = [{title:'请选择', value: ''}];
	    $.ajax({
			async:false,
			url: '${pageContext.request.contextPath}/Product/all.koala',
			type: 'POST',
			dataType: 'json',
		})
		.done(function(msg){
	   		for (var i=0;i<msg['data'].length;i++)
	    	{
				contents.push({title:msg['data'][i]['productName'] , value: msg['data'][i]['id']});
	    	}
	   		selectItems['productIdID'] = contents;
	   	});
        var contents = [{title:'请选择', value: ''}];
        contents.push({title:'Waiting' , value:'Waiting'});
        contents.push({title:'Hold' , value:'Hold'});
        contents.push({title:'Received' , value:'Received'});
        selectItems['currStatusID'] = contents;
        var contents = [{title:'请选择', value: ''}];
        contents.push({title:'CP PASS.' , value: '1'});
        contents.push({title:'Pending CP.' , value: '2'});
        selectItems['waferStatusIdID'] = contents;
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Part Name:</label>
                    <div class="col-lg-9">
                           <input name="partName" style="display:inline; width:94%;" class="form-control"  type="text"  id="partNameID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Part Number:</label>
                    <div class="col-lg-9">
                           <input name="partNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="partNumberID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Description:</label>
                    <div class="col-lg-9">
                           <input name="description" style="display:inline; width:94%;" class="form-control"  type="text"  id="descriptionID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Product:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="productIdID"></div>
                           <input name="productId"  type="hidden"  id="productIdID" />
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
                    <label class="col-lg-3 control-label">Station:</label>
                    <div class="col-lg-9">
                           <input name="station" style="display:inline; width:94%;" class="form-control"  type="text"  id="stationID"/>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Type:</label>
                    <div class="col-lg-9">
                           <div class="btn-group select" id="lotTypeIdID"></div>
                           <input type="hidden" name="lotTypeId" id="lotTypeIdID_" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
  			  <div class="form-group">
				    <label class="col-lg-3 control-label">Vender:</label>
				    <div class="col-lg-9">
				    <div class="btn-group select" id="venderIdID"></div>
				      <input type="hidden" name="venderId" id="venderIdID_" />
				      <span class="required">*</span>
				    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Po.N:</label>
                    <div class="col-lg-9">
                           <input name="pon" style="display:inline; width:94%;" class="form-control"  type="text"  id="ponID"/>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Shipping Date:</label>
                    <div class="col-lg-9">
						   <input name="shippingDate" style="display:inline; width:94%;" class="form-control"  type="date"  id="shippingDateID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
<div class="form-group">
                    <label class="col-lg-3 control-label">Delivery Date:</label>
                    <div class="col-lg-9">
						   <input name="deliveryDate" style="display:inline; width:94%;" class="form-control"  type="date"  id="deliveryDateID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Remark:</label>
                    <div class="col-lg-9">
                           <input name="remark" style="display:inline; width:94%;" class="form-control"  type="text"  id="remarkID" />
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
		url: '${pageContext.request.contextPath}/Vender/all.koala',
		type: 'POST',
		dataType: 'json',
	})
	.done(function(msg) {
		debugger;
		for (var i=0;i<msg['data'].length;i++)
		{
			contents.push({title:msg['data'][i]['venderName'] , value: msg['data'][i]['id']});
		}
		selectItems['venderIdID'] = contents;
	});
    var contents = [{title:'请选择', value: ''}];
    $.ajax({
    	async:false,
    	url: '${pageContext.request.contextPath}/WaferProcess/Process/getLotTypes.koala',
    	type: 'POST',
    	dataType: 'json',
    })
    .done(function(msg) {
    	for (var i=0;i<msg['data'].length;i++)
    	{
    		contents.push({title:msg['data'][i]['value'] , value: msg['data'][i]['id']});
    	}
    	selectItems['lotTypeIdID'] = contents;
    });
    var contents = [{title:'请选择', value: ''}];
    $.ajax({
		async:false,
		url: '${pageContext.request.contextPath}/Product/all.koala',
		type: 'POST',
		dataType: 'json',
	})
	.done(function(msg){
		debugger;
   		for (var i=0;i<msg['data'].length;i++)
    	{
			contents.push({title:msg['data'][i]['productName'] , value: msg['data'][i]['id']});
    	}
   		selectItems['productIdID'] = contents;
   	});
</script>
</body>
</html>
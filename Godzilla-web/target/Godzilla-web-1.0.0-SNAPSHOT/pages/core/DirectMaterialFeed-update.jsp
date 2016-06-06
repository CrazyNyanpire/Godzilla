<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Material:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="materialIdID"></div>
                           <input name="materialId" type="hidden"  id="materialIdID_" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Part N.:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="partIdID"></div>
                           <input name="partId" type="hidden"  id="partIdID_" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Batch N.:</label>
                    <div class="col-lg-9">
                           <input name="batchNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="batchNoID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Partname_cn:</label>
                    <div class="col-lg-9">
                           <input readonly style="display:inline; width:94%;" class="form-control"  type="text"  id="partNameCNID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">InQty:</label>
                    <div class="col-lg-9">
                           <input name="qty" style="display:inline; width:94%;" class="form-control"  type="text"  id="qtyID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">In Cap.:</label>
                    <div class="col-lg-9">
                           <input name="inCapacity" style="display:inline; width:94%;" class="form-control"  type="text"  id="inCapacityID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Unit:</label>
                    <div class="col-lg-9">
                           <input name="unit" readonly type="text" style="display:inline; width:94%;" class="form-control" id="unitID" dataType="Require"/>
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
                    <label class="col-lg-3 control-label">Po N.:</label>
                    <div class="col-lg-9">
                           <input name="pon" style="display:inline; width:94%;" class="form-control"  type="text"  id="ponID" dataType="Require"/>
                           <span class="required">*</span>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Shipping Date:</label>
                    <div class="col-lg-9">
                           <input name="shippingDate" style="display:inline; width:94%;" class="form-control"  type="text" readonly id="shippingDateID" />
    </div>
</div>
<div class="form-group">
                    <label class="col-lg-3 control-label">Delivery Date:</label>
                    <div class="col-lg-9">
                           <input name="deliveryDate" style="display:inline; width:94%;" class="form-control"  type="text" readonly id="deliveryDateID" />
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
                    <label class="col-lg-3 control-label">Remark:</label>
                    <div class="col-lg-9">
                           <input name="remark" style="display:inline; width:94%;" class="form-control"  type="text"  id="remarkID" />
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
		url: '${pageContext.request.contextPath}/Material/all/'+materialTypeId+'.koala',
		type: 'POST',
		dataType: 'json',
	})
	.done(function(msg) {
		debugger;
		for (var i=0;i<msg['data'].length;i++)
		{
			contents.push({title:msg['data'][i]['materialName'] , value: msg['data'][i]['id']});
		}
		selectItems['materialIdID'] = contents;
	});
	
    var contents = [{title:'请选择', value: ''}];
	$.ajax({
		async:false,
		url: '${pageContext.request.contextPath}/Vender/all.koala',
		type: 'POST',
		dataType: 'json',
	})
	.done(function(msg) {
		for (var i=0;i<msg['data'].length;i++)
		{
			contents.push({title:msg['data'][i]['venderName'] , value: msg['data'][i]['id']});
		}
		selectItems['venderIdID'] = contents;
	});
    var contents = [{title:'请选择', value: ''}];
    contents.push({title:'Waiting' , value:'Waiting'});
    contents.push({title:'Hold' , value:'Hold'});
    contents.push({title:'Received' , value:'Received'});
    selectItems['currStatusID'] = contents;
                                                                                                                    </script>
</body>
</html>
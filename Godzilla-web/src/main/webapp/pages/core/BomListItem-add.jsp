<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	          <div class="form-group">
    <label class="col-lg-3 control-label">Product Name:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="productIdID"></div>
      <input type="hidden" id="productIdID_"  name="productId" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
  <div class="form-group">
    <label class="col-lg-3 control-label">Material Name:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="materialNameIdID"></div>
      <input type="hidden" id="materialNameIdID_"  name="materialNameId" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
  <div class="form-group">
    <label class="col-lg-3 control-label">Material:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="materialIdID"></div>
      <input type="hidden" id="materialIdID_"  name="materialId" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
    <div class="form-group">
    <label class="col-lg-3 control-label">Station:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="stationIdID"></div>
      <input type="hidden" id="stationIdID_"  name="stationId" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
  <div class="form-group">
    <label class="col-lg-3 control-label">consumeable:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="monitorID"></div>
      <input type="hidden" id="monitorID_"  name="monitor" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">consumeable:</label>
                    <div class="col-lg-9">
                           <input name="order" style="display:inline; width:94%;" class="form-control"  type="text"  id="orderID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">stage:</label>
                    <div class="col-lg-9">
                           <input name="stage" style="display:inline; width:94%;" class="form-control"  type="text"  id="stageID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">sn:</label>
                    <div class="col-lg-9">
                           <input name="sn" style="display:inline; width:94%;" class="form-control"  type="text"  id="snID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">qty:</label>
                    <div class="col-lg-9">
                           <input name="qty" style="display:inline; width:94%;" class="form-control"  type="text"  id="qtyID" />
    </div>
</div>
</form>
<script type="text/javascript">
var selectItems = {};
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
var contents = [{title:'请选择', value: ''}];
$.ajax({
	async:false,
	url: '${pageContext.request.contextPath}/MaterialName/all.koala',
	type: 'POST',
	dataType: 'json',
})
.done(function(msg){
	debugger;
		for (var i=0;i<msg['data'].length;i++)
	{
		contents.push({title:msg['data'][i]['materialName'] , value: msg['data'][i]['id']});
	}
		selectItems['materialNameIdID'] = contents;
	});
/*
contents = [{title:'请选择', value: ''}];
$.ajax({
	async:false,
	url: '${pageContext.request.contextPath}/Station/findConsumeStation.koala',
	type: 'POST',
	dataType: 'json',
})
.done(function(msg){
	debugger;
		for (var i=0;i<msg['data'].length;i++)
	{
		contents.push({title:msg['data'][i]['stationName'] , value: msg['data'][i]['id']});
	}
		selectItems['stationIdID'] = contents;
	});
contents = [{title:'请选择', value: ''}];
contents.push({title:'是' , value: '1'});
contents.push({title:'否' , value: '0'});
selectItems['monitorID_'] = contents;*/
                                                    </script>
</body>
</html>
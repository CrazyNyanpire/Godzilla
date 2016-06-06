<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
<div class="form-group">
    <label class="col-lg-3 control-label">Material Name:</label>
    <div class="col-lg-9">
      <div class="btn-group select" id="materialNameIdID"></div>
      <input type="hidden" id="materialNameIdID_"  name="materialNameId" dataType="Require"/>
      <span class="required">*</span>
    </div>
  </div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">partId:</label>
                    <div class="col-lg-9">
                           <input name="partId" style="display:inline; width:94%;" class="form-control"  type="text"  id="partIdID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">partNameCN:</label>
                    <div class="col-lg-9">
                           <input name="partNameCN" style="display:inline; width:94%;" class="form-control"  type="text"  id="partNameCNID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">unit:</label>
                    <div class="col-lg-9">
                           <input name="unit" style="display:inline; width:94%;" class="form-control"  type="text"  id="unitID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">materialSpecification:</label>
                    <div class="col-lg-9">
                           <input name="materialSpecification" style="display:inline; width:94%;" class="form-control"  type="text"  id="materialSpecificationID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">measure:</label>
                    <div class="col-lg-9">
                           <input name="measure" style="display:inline; width:94%;" class="form-control"  type="text"  id="measureID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">minCapacity:</label>
                    <div class="col-lg-9">
                           <input name="minCapacity" style="display:inline; width:94%;" class="form-control"  type="text"  id="minCapacityID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">minUnit:</label>
                    <div class="col-lg-9">
                           <input name="minUnit" style="display:inline; width:94%;" class="form-control"  type="text"  id="minUnitID" />
    </div>
</div>
</form>
<script type="text/javascript">
var selectItems = {};
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
                                                            </script>
</body>
</html>
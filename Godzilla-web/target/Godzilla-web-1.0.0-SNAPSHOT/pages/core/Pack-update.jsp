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
                    <label class="col-lg-3 control-label">packSize:</label>
                    <div class="col-lg-9">
                           <input name="packSize" style="display:inline; width:94%;" class="form-control"  type="text"  id="packSizeID" />
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
		for (var i=0;i<msg['data'].length;i++)
	{
		contents.push({title:msg['data'][i]['productName'] , value: msg['data'][i]['id']});
	}
		debugger;
		selectItems['productIdID'] = contents;
	});
            </script>
</body>
</html>
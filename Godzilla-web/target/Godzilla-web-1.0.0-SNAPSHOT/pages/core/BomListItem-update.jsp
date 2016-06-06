<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	           <div class="form-group">
                    <label class="col-lg-3 control-label">createDate:</label>
                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="createDate" id="createDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">lastModifyDate:</label>
                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="lastModifyDate" id="lastModifyDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">order:</label>
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
                                                    </script>
</body>
</html>
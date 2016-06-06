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
                    <label class="col-lg-3 control-label">rejcetCode:</label>
                    <div class="col-lg-9">
                           <input name="rejcetCode" style="display:inline; width:94%;" class="form-control"  type="text"  id="rejcetCodeID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">itemName:</label>
                    <div class="col-lg-9">
                           <input name="itemName" style="display:inline; width:94%;" class="form-control"  type="text"  id="itemNameID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">description:</label>
                    <div class="col-lg-9">
                           <input name="description" style="display:inline; width:94%;" class="form-control"  type="text"  id="descriptionID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">isUse:</label>
                    <div class="col-lg-9">
                           <div class="btn-group select" id="isUseID"></div>
	                       <input type="hidden" id="isUseID_" name="isUseAsString" />                    </div>
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
                                                    selectItems['isUseID'] = [
                   {title: '请选择', value: ''},
                   {title: '是', value: '1'},
                   {title: '否', value: '0'}
                ];
                </script>
</body>
</html>
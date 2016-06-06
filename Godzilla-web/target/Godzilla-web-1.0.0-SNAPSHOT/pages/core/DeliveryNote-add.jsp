<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	           <div class="form-group">
                    <label class="col-lg-3 control-label">DN N.:</label>
                    <div class="col-lg-9">
                           <input name="dnNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="dnNoID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">So. N.:</label>
                    <div class="col-lg-9">
                           <input name="soNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="soNoID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Customer PoN:</label>
                    <div class="col-lg-9">
                           <input name="customerPoN" style="display:inline; width:94%;" class="form-control"  type="text"  id="customerPoNID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Ivoice N.:</label>
                    <div class="col-lg-9">
                           <input name="ivoiceNo" style="display:inline; width:94%;" class="form-control"  type="text"  id="ivoiceNoID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Date:</label>
                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="dDate" id="dDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Part Number:</label>
                    <div class="col-lg-9">
                           <input name="partNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="partNumberID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Description:</label>
                    <div class="col-lg-9">
                           <input name="description" style="display:inline; width:94%;" class="form-control"  type="text"  id="descriptionID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Quantity:</label>
                    <div class="col-lg-9">
                           <input name="quantity" style="display:inline; width:94%;" class="form-control"  type="text"  id="quantityID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">U/M:</label>
                    <div class="col-lg-9">
                           <input name="um" style="display:inline; width:94%;" class="form-control"  type="text"  id="umID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Delivery Date:</label>
                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="deliveryDate" id="deliveryDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Status:</label>
                    <div class="col-lg-9">
                    <div class="btn-group select" id="statusID"></div>
                           <input name="status"  type="hidden"  id="statusID_" />
    </div>
</div>
</form>
<script type="text/javascript">
    var selectItems = {};
    var contents = [{title:'Open', value: 'Open'}];
    contents.push({title:'Close' , value: 'Close'});
    selectItems['statusID'] = contents;
</script>
</body>
</html>
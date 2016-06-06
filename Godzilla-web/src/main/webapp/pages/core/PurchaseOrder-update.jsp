<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	           <div class="form-group">
                    <label class="col-lg-3 control-label">PO Number:</label>
                    <div class="col-lg-9">
                           <input name="poNumber" style="display:inline; width:94%;" class="form-control"  type="text"  id="poNumberID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">PO Date:</label>
                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="poDate" id="poDateID" >
                        <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                     </div>
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Currency:</label>
                    <div class="col-lg-9">
                           <input name="currency" style="display:inline; width:94%;" class="form-control"  type="text"  id="currencyID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Payment Term:</label>
                    <div class="col-lg-9">
                           <input name="paymentTerm" style="display:inline; width:94%;" class="form-control"  type="text"  id="paymentTermID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">PPO Type:</label>
                    <div class="col-lg-9">
                           <input name="ppoType" style="display:inline; width:94%;" class="form-control"  type="text"  id="ppoTypeID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">Part Number :</label>
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
</form>
<script type="text/javascript">
    var selectItems = {};
                                                                                    </script>
</body>
</html>
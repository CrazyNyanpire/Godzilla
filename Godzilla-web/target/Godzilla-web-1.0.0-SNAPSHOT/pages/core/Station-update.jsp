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
                    <label class="col-lg-3 control-label">stationName:</label>
                    <div class="col-lg-9">
                           <input name="stationName" style="display:inline; width:94%;" class="form-control"  type="text"  id="stationNameID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">stationNameEn:</label>
                    <div class="col-lg-9">
                           <input name="stationNameEn" style="display:inline; width:94%;" class="form-control"  type="text"  id="stationNameEnID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">parentStation:</label>
                    <div class="col-lg-9">
                           <input name="parentStation" style="display:inline; width:94%;" class="form-control"  type="text"  id="parentStationID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">stationType:</label>
                    <div class="col-lg-9">
                           <input name="stationType" style="display:inline; width:94%;" class="form-control"  type="text"  id="stationTypeID" />
    </div>
</div>
	           <div class="form-group">
                    <label class="col-lg-3 control-label">timeOut:</label>
                 <div class="col-lg-9">
                    <div class="input-group date form_datetime" style="width:160px;float:left;" >
                        <input type="text" class="form-control" style="width:160px;" name="timeOut" id="timeOutID" >
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils"%>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<form class="form-horizontal">
	           <div class="form-group">
                    <label class="col-lg-3 control-label">IQC Confirmation:</label>
                    <div class="col-lg-9">
                           <textarea name="comments" style="display:inline; width:94%;" class="form-control"  type="text"  id="commentsID" dataType="Require"></textarea>
                           <span class="required">*</span>
    </div>
</div>
<div class="form-group">
				<input name="holdCode" style="display:inline;margin-left:40%;" type="radio"  id="holdCode1" value="Pass" /><span>Pass</span>
				<input name="holdCode" style="display:inline;margin-left:20%;" type="radio"  id="holdCode2" value="Hold" /><span>Hold</span>
				<span class="required">*</span>
</div>
<div id="God_MHRNum"></div>
	           <div class="form-group">
                    <label id="changeTime" class="col-lg-3 control-label">Hold Time:</label>
                    <div class="col-lg-9">
                           <input name="holdTime" style="display:inline; width:94%;" class="form-control" readonly value="<%=MyDateUtils.getTodayTime(MyDateUtils.formater)%>" type="text"  id="holdTimeID" dataType="Require"/>
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
    contents.push({title:'Waiting' , value:'Waiting'});
    contents.push({title:'Hold' , value:'Hold'});
    contents.push({title:'Received' , value:'Received'});
    //selectItems[''] = contents;
</script>
</body>
</html>
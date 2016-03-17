<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<%@ include file="../common/head.jsp"%>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
<div class="container" style="margin-top:10px;">
	<div style="width:45%;padding:2px;">
    	<input name="pid" type="hidden" value="${pid}">
	    <input type="text" name="keywords" id="keywords">
        <a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="#" >搜索</a>
	</div>
  <div>
	<div class="row pull-left" style="width:45%; height:80%; border:1px; border-color:#999; border-style:solid; padding:2px; overflow-y:auto;">
    	<table id="line" width="100%" border="0" cellspacing="0" cellpadding="0" class="table table-striped">
  
		</table>
    </div>
    <div class="row pull-right" style="width:45%; height:80%; border:1px; border-color:#999; border-style:solid;padding:2px; overflow-y:auto;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table table-striped">
  <tr>
    <td>1</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>2</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>3</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>4</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>5</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>6</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>7</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>8</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>9</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>10</td>
    <td>&nbsp;</td>
  </tr>
</table>
    </div>
    </div>
</div>
<script language="javascript">
	var tmppage=0;

	$.ajax({
	   type: "POST",
	   dataType:"json",
	   url: basePath+"/admin/getlinemanagejson.do",
	   data: {"startpage":tmppage},
	   success: function(result){
		   $.each(result,function(i){
						addRow(result[i]);
					});
	   }
	});
	
	function addRow(l){
		$("#line").append("<tr><td><input name=\"linebox\" type=\"checkbox\" value=\""+l.id+"\"></td><td>"+ l.lineID +"</td></tr>");
	}
</script>
</body>
</html>

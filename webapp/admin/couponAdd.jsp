<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/couponAdd.js"></script>
    <title>手动送券</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/admin/couponLineList.do" style="color: black"></a>
            手动送券
        </h2>
    </div>
    <form method="post" id="couponForm" action="${basePath}/admin/couponAdd.do">
        <input type="hidden" name="cid">
        <div class="row">
            <div style="margin-left: 30px;">
                <label>顾客名称</label>
                <input type="text" name="cname" class="span3" readonly="readonly"
                       datatype="*" nullmsg="顾客名称不能为空">
                <input type="button"  class="btn" style="margin-left: 20px;margin-bottom: 10px;" value="选择顾客" onclick="selectCustomerDialog();">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>代金券面值</label>
                <input type="text" name="vprice" class="span3"
                       datatype="money" nullmsg="代金券面值不能为空" errormsg="代金券面值格式错误"
                       onkeyup="value=value.replace(/[^0-9\.]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>代金券最低消费</label>
                <input type="text" name="lowlimit" class="span3"
                       datatype="money" nullmsg="代金券最低消费不能为空" errormsg="代金券最低消费格式错误"
                       onkeyup="value=value.replace(/[^0-9\.]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>代金券有效天数</label>
                <input type="text" name="validday" class="span3" datatype="n"
                       nullmsg="代金券有效天数不能为空" errormsg="代金券有效天数格式错误"
                       onkeyup="value=value.replace(/[^0-9]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>备注</label>
                <input type="text" name="remark" class="span3">
            </div>
        </div>
        <div class="row">
            <div class="span2" style="margin-top:8px;">
                <input type="submit" class="btn" value="保存">
            </div>
        </div>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>

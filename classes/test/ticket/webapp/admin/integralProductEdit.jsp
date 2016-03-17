<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/common/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="<%=basePath%>/common/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/integralProdutEdit.js"></script>
    <title>
        <c:if test="${empty integralProduct}">
            新增积分商品
        </c:if>
        <c:if test="${not empty integralProduct && integralProduct.status != 3}">
            编辑积分商品
        </c:if>
        <c:if test="${not empty integralProduct && integralProduct.status == 3}">
            积分商品详情
        </c:if>
    </title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/admin/integralProductList.do" style="color: black"></a>
            <c:if test="${empty integralProduct }">
                新增积分商品
            </c:if>
            <c:if test="${not empty integralProduct && integralProduct.status != 2}">
                编辑积分商品
            </c:if>
            <c:if test="${not empty integralProduct && integralProduct.status == 2}">
                积分商品详情
            </c:if>
        </h2>
    </div>
    <form method="post" id="integralProductForm" action="${basePath}/admin/integralProductEdit.do">
        <input type="hidden" name="id" value="${integralProduct.id}">

        <div class="row">
            <div style="margin-left: 30px;">
                <label>商品名称</label>
                <input type="text" name="name" value="${integralProduct.name }" class="span3" datatype="*"
                       nullmsg="商品名称不能为空">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>积分</label>
                <input type="text" name="amount" value="${integralProduct.amount }" class="span3" datatype="n"
                       nullmsg="积分不能为空" errormsg="积分格式错误"
                       onkeyup="value=value.replace(/[^0-9]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>商品类型</label>
                <select name="type" class="span3">
                    <option value="1"
                            <c:if test='${integralProduct.type==1 }'>selected</c:if> >代金券
                    </option>
                </select>
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>代金券有效天数</label>
                <input type="text" name="validday" value="${integralProduct.validday }" class="span3" datatype="n"
                       nullmsg="代金券有效天数不能为空" errormsg="代金券有效天数格式错误"
                       onkeyup="value=value.replace(/[^0-9]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>代金券最低消费</label>
                <input type="text" name="lowlimit" value="${integralProduct.lowlimit }" class="span3"
                       datatype="money" nullmsg="代金券最低消费不能为空" errormsg="代金券最低消费格式错误"
                       onkeyup="value=value.replace(/[^0-9\.]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>代金券面值</label>
                <input type="text" name="vprice" value="${integralProduct.vprice }" class="span3"
                       datatype="money" nullmsg="代金券面值不能为空" errormsg="代金券面值格式错误"
                       onkeyup="value=value.replace(/[^0-9\.]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>生效时间</label>
                <input type="text" name="startdate" value="${fn:substring(integralProduct.startdate,0,10)}"
                       class="span3" datatype="*" nullmsg="生效时间不能为空">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>失效时间</label>
                <input type="text" name="enddate" value="${fn:substring(integralProduct.enddate,0,10)}" class="span3"
                       datatype="*" nullmsg="失效时间不能为空">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>库存限制</label>
                <select name="stockflag" class="span3" onchange="showStock();">
                    <option value="0"
                            <c:if test='${integralProduct.stockflag==0 }'>selected</c:if> >不限制
                    </option>
                    <option value="1"
                            <c:if test='${integralProduct.stockflag==1 }'>selected</c:if> >限制
                    </option>
                </select>
            </div>
        </div>
        <div class="row"
             <c:if test='${empty integralProduct.stockflag||integralProduct.stockflag!=1 }'>style="display:none;"</c:if> >
            <div style="margin-left: 30px;">
                <label>库存数量
                    <c:if test='${not empty integralProduct.currentstock }'>
                        <span style="color: gray;font-size: 12px;">剩余数量${integralProduct.currentstock }</span>
                    </c:if>
                </label>
                <input type="text" name="fixedstock" value="${integralProduct.fixedstock }" class="span3"
                       datatype="n" nullmsg="库存数量不能为空" errormsg="库存数量格式错误" onkeyup="value=value.replace(/[^0-9]/g,'')">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>商品图标<span style="color: gray;font-size: 12px;">仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB</span></label>
                <input type="file" id="iconurl">

                <div id="iconurlPreview" class="pull-left" style="margin-bottom: 10px;">
                    <c:if test="${not empty integralProduct.iconurl }">
                        <img style="height:150px;" src="${integralProduct.iconurl }">
                    </c:if>
                </div>
                <input type="hidden" name="iconurl" value="${integralProduct.iconurl }">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>商品图片<span style="color: gray;font-size: 12px;">仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB</span></label>
                <input type="file" id="mainurl">

                <div id="mainurlPreview" class="pull-left" style="margin-bottom: 10px;">
                    <c:if test="${not empty integralProduct.mainurl }">
                        <img style="height:150px;" src="${integralProduct.mainurl }">
                    </c:if>
                </div>
                <input type="hidden" name="mainurl" value="${integralProduct.mainurl }">
            </div>
        </div>
        <div class="row">
            <div style="margin-left: 30px;">
                <label>商品描述</label>
                <input type="hidden" name="desc" value="${integralProduct.escapeDesc }" class="span3">
                <script id="descEditor" name="content" type="text/plain"></script>
            </div>
        </div>
        <c:if test="${empty integralProduct.status || integralProduct.status != 2}">
            <div class="row">
                <div class="span2" style="margin-top:8px;">
                    <input type="submit" class="btn" value="保存">
                </div>
            </div>
        </c:if>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>

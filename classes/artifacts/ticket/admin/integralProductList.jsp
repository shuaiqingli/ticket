<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/integralProductList.js"></script>
    <title>积分商品列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/admin/integralProductList.do" method="get">
        <div class="page-header">
            <h2>积分商品列表</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="商品名称" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
            <div class="btn-group pull-right" style="margin-right:20px;">
                <a class="btn" href="<%=basePath%>/admin/integralProductEditPage.do">
                    <i class="icon-plus-sign"></i>新增商品
                </a>
                <a class="btn" href="<%=basePath%>/user/integralProductStatistic.do">
                    <i class="icon-plus-sign"></i>兑换统计
                </a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>商品名称</th>
                <th>积分</th>
                <th>库存</th>
                <th>状态</th>
                <th>创建日期</th>
                <th class="pull-right">操作&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${integralProductList }" var="integralProduct">
                <tr class="odd">
                    <td>${integralProduct.name }</td>
                    <td>${integralProduct.amount }</td>
                    <td>
                        <c:if test="${integralProduct.stockflag == 0}">
                            -
                        </c:if>
                        <c:if test="${integralProduct.stockflag == 1}">
                            ${integralProduct.fixedstock}(剩余${integralProduct.currentstock})
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${integralProduct.status == 1}">未上架</c:if>
                        <c:if test="${integralProduct.status == 2}">已上架</c:if>
                    </td>
                    <td>${integralProduct.makedate}</td>
                    <td>
                        <div class="btn-group pull-right">
                            <c:if test="${integralProduct.status != 2}">
                                <a class="btn"
                                   href="<%=basePath%>/admin/integralProductEditPage.do?id=${integralProduct.id}">
                                    <i class="icon-pencil"></i> 编辑
                                </a>
                            </c:if>
                            <c:if test="${integralProduct.status == 2}">
                                <a class="btn"
                                   href="<%=basePath%>/admin/integralProductEditPage.do?id=${integralProduct.id}">
                                    <i class="icon-pencil"></i> 详情
                                </a>
                            </c:if>
                            <c:if test="${integralProduct.status == 1}">
                                <a class="btn" href="javascript:void(0);" onclick="puton(${integralProduct.id});">
                                    <i class="icon-pencil"></i> 上架
                                </a>
                            </c:if>
                            <c:if test="${integralProduct.status == 2}">
                                <a class="btn" href="javascript:void(0);" onclick="putoff(${integralProduct.id});">
                                    <i class="icon-pencil"></i> 下架
                                </a>
                            </c:if>
                            <a class="btn btn-danger" href="javascript:void(0);" onclick="del(${integralProduct.id})">
                                <i class="icon-remove icon-white"></i> 删除
                            </a>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>

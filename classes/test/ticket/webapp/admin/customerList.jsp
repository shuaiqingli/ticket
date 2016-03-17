<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/customerList.js"></script>
    <title>顾客列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/admin/customerList.do" method="get">
        <div class="page-header">
            <h2>顾客列表</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <select name="rank" class="span2">
                    <option value="">--请选择级别--</option>
                    <option value="0"
                            <c:if test="${param.rank=='0'}">selected</c:if> >普通
                    </option>
                    <option value="1"
                            <c:if test="${param.rank=='1'}">selected</c:if> >高级
                    </option>
                    <option value="2"
                            <c:if test="${param.rank=='2'}">selected</c:if> >站务
                    </option>
                </select>
                <input type="text" name="cname" value="${param.cname }"
                       placeholder="手机/名称" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
                <a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="javascript:void(0)" onclick="exportExcel(this);">导出EXCEL</a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>名称</th>
                <th>手机</th>
                <th>余额</th>
                <th>里程</th>
                <th>积分</th>
                <th>级别</th>
                <th>注册日期</th>
                <th class="pull-right">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${customerList }" var="customer">
                <tr class="odd">
                    <td>${customer.CName }</td>
                    <td>${customer.Mobile }</td>
                    <td>${customer.Money==null ? '0.00' : customer.Money}</td>
                    <td>${customer.Mileage==null ? '0' : customer.Mileage }</td>
                    <td>${customer.Integral==null ? '0' : customer.Integral }</td>
                    <td>
                        <c:if test="${customer.Rank==0}">普通</c:if>
                        <c:if test="${customer.Rank==1}">高级</c:if>
                        <c:if test="${customer.Rank==2}">
                            站务<c:if test="${not empty customer.UserName}">(${customer.UserName})</c:if>
                        </c:if>
                    </td>
                    <td>${customer.MakeDate }</td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="<%=basePath%>/admin/customerDetail.do?cid=${customer.CID}">
                                <i class="icon-pencil"></i> 详情
                            </a>
                            <a class="btn" href="<%=basePath%>/user/saleorderlist.do?mobile=${customer.Mobile}">
                                <i class="icon-pencil"></i> 订单列表
                            </a>
                            <c:if test="${customer.Rank==2}">
                                <a class="btn" href="javascript:void(0)"
                                   onclick="pointAdmin('${customer.CID}');">
                                    <i class="icon-pencil"></i> 绑定管理员
                                </a>
                            </c:if>
                            <a class="btn" href="javascript:void(0)"
                               onclick="pointRank('${customer.CID}', '${customer.Rank}');">
                                <i class="icon-pencil"></i> 指定级别
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

<div class="layer_dialog" style="display:none;" id="rankDialog">
    <input type="hidden" name="cid">

    <div style="margin: 10px 10px 10px 10px;">
        <label>
            <span class="title">级别 :</span>
            <select name="rank">
                <option value="0">普通</option>
                <option value="1">高级</option>
                <option value="2">站务</option>
            </select>
        </label>
    </div>
    <div class="row" style="margin-top: 20px;margin-bottom: 20px;text-align: center;">
        <input type="submit" value="保存" onclick="updateCustomerRank();">
        <input type="reset" value="取消" onclick="layer.closeAll();">
    </div>
</div>
</body>
</html>

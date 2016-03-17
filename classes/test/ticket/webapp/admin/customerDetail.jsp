<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/customerDetail.js"></script>
    <title>顾客详情</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <div class="page-header">
        <h2>
            <a id="main_page" href="<%=basePath%>/admin/customerList.do" style="color: black"></a>
            顾客详情(${customer.mobile})
        </h2>
    </div>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#information" data-toggle="tab">基本信息</a></li>
        <li><a href="#integralList" data-toggle="tab">积分流水</a></li>
        <li><a href="#couponList" data-toggle="tab">代金券列表</a></li>
    </ul>
    <div class="tab-content">
        <!-- 基本信息 -->
        <div class="tab-pane active" id="information">
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>名称</label>
                    <input type="text" name="cname" value="${customer.cname }" class="span3" readonly>
                </div>
            </div>
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>手机</label>
                    <input type="text" name="mobile" value="${customer.mobile }" class="span3" readonly>
                </div>
            </div>
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>余额</label>
                    <input type="text" name="money" value="${customer.money }" class="span3" readonly>
                </div>
            </div>
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>里程(KM)</label>
                    <input type="text" name="mileage" value="${customer.mileage }" class="span3" readonly>
                </div>
            </div>
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>积分</label>
                    <input type="text" name="integral" value="${customer.integral }" class="span3" readonly>
                </div>
            </div>
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>可用优惠券(张)</label>
                    <input type="text" name="availableCouponTotal" value="${availableCouponTotal }" class="span3" readonly>
                </div>
            </div>
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>级别</label>
                    <input type="text" name="integral"
                           value="${customer.rank==0?'普通':customer.rank==1?'高级':customer.rank==2?'站务':'其它' }"
                           class="span3" readonly>
                </div>
            </div>
            <div class="row">
                <div style="margin-left: 30px;">
                    <label>注册日期</label>
                    <input type="text" name="integral" value="${customer.makeDate }" class="span3" readonly>
                </div>
            </div>
        </div>
        <!-- 积分流水 -->
        <div class="tab-pane" id="integralList">
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>类型</th>
                            <th>积分值</th>
                            <th>积分商品</th>
                            <th>订单号</th>
                            <th>操作日期</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${integralLinelist }" var="integralLine">
                            <tr class="odd">
                                <td>${integralLine.Type==1?'买票送积分':integralLine.Type==2?'退票扣积分':integralLine.Type==3?'积分商品兑换':'其它' }</td>
                                <td>${integralLine.Integral }</td>
                                <td>${integralLine.Type==3?integralLine.ProductName:'无' }</td>
                                <td>
                                    <c:if test="${integralLine.Type==1 || integralLine.Type==2 }">
                                        <a href="<%=basePath%>/user/saleorderlist?mobile=${integralLine.OperatorID}">${integralLine.OperatorID}</a>
                                    </c:if>
                                    <c:if test="${integralLine.Type!=1 && integralLine.Type!=2 }">
                                        无
                                    </c:if>
                                </td>
                                <td>${integralLine.MakeDate }</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- 代金券列表 -->
        <div class="tab-pane" id="couponList">
            <div class="row">
                <div class="span12">
                    <table class="table table-striped dataTable">
                        <thead>
                        <tr>
                            <th>券码</th>
                            <th>类型</th>
                            <th>有效期</th>
                            <th>最低消费</th>
                            <th>面值</th>
                            <th>状态</th>
                            <th>日期</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${couponList }" var="coupon">
                            <tr class="odd">
                                <td>${coupon.VoucherCode }</td>
                                <td>${coupon.Memo }</td>
                                <td>${coupon.BeginDate } 到 ${coupon.EndDate }</td>
                                <td>${coupon.LowLimit }</td>
                                <td>${coupon.VPrice }</td>
                                <td>
                                    <c:if test="${coupon.IsUse==1}">
                                        未用
                                    </c:if>
                                    <c:if test="${coupon.IsUse==0}">
                                        已用(<a href="<%=basePath%>/user/saleorderlist?mobile=${coupon.OrderIDForUse}">${coupon.OrderIDForUse}</a>)
                                    </c:if>
                                </td>
                                <td>${coupon.MakeDate }</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>

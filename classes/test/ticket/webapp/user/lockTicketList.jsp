<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/lockTicketList.js"></script>
    <title>售票列表</title>
    <style type="text/css">
        .list td{
            padding: 20px 10px;
        }
        .item{
            cursor: pointer;line-height: 30px;border: 1px solid #ccc;text-align: center;font-size: 14px;
        }
        .check_box{
            border: 1px solid #ff6861;width: 10px;height: 10px;display: inline-block;overflow: hidden;
        }
        .check_box img{
            float: left;
        }
        .buy_btn{
            display: inline-block;background-color: #63B263;;color:#fff;border-radius: 5px;line-height: 30px;width: 50px;text-align: center;cursor: pointer;
        }
        .cancel_btn{
            display: inline-block;background-color: #ff6861;color:#fff;border-radius: 5px;line-height: 30px;width: 50px;text-align: center;margin-left: 10px;cursor: pointer;
        }
        .st_tip{
            border: 1px solid #63B263;color: #63B263;display:inline-block;line-height:14px;height:14px;
        }
        .ed_tip{
            border: 1px solid #ff6861;color: #ff6861;display:inline-block;line-height:14px;height:14px;
        }
    </style>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container" style="width: 1200px;">
    <form action="<%=basePath%>/user/lockTicketList.do" method="get">
        <div class="page-header">
            <a id="main_page" href="<%=basePath%>/user/lockTicketManage.do" style="color: black"></a>
            <h2>售票列表</h2>
        </div>
        <div class="row">
            <div class="pull-left" style="padding-left:30px;">
                <select name="type" class="span2">
                    <option value="">--请选择类型--</option>
                    <option value="0" <c:if test="${param.type=='0'}">selected</c:if> >已取消</option>
                    <option value="1" <c:if test="${param.type==1}">selected</c:if> >已支付</option>
                    <option value="2" <c:if test="${param.type==2}">selected</c:if> >已预定</option>
                </select>
                <input type="text" name="rideDate" value="${param.rideDate}" placeholder="发车日期" style="height:30px;" >
                <input type="text" name="makeDate" value="${param.makeDate}" placeholder="下单日期" style="height:30px;" >
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="姓名/电话/站务" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <div style="text-align: center;width: 100%;margin-top: 5px;">
            <div style="display:inline-block;width: 100%;">
                <table class="list" cellspacing="0" style="border-collapse:collapse;width: 100%;">
                    <tr style="background-color: #fff;border: 0px;text-align: center;border: 1px solid #ccc;">
                        <td>姓名</td>
                        <td>联系电话</td>
                        <td>票数</td>
                        <td>车次信息</td>
                        <td>发车时间</td>
                        <td>下单时间</td>
                        <td>站务</td>
                        <td>状态</td>
                        <td>验票码|座位号</td>
                        <td>操作</td>
                    </tr>
                    <c:forEach items="${dataList}" var="data">
                        <tr class="item">
                            <td>${data.LName}</td>
                            <td>${data.LMobile}</td>
                            <td>
                                <span>${data.Quantity}</span>张
                                <c:if test="${data.PayStatus == 1}">
                                    <span>(支付${data.PayQuantity}张)</span>
                                </c:if>
                            </td>
                            <td style="text-align: left;">
                                <div>${data.LineName}/${data.ShiftNum}</div>
                                <div><span class="st_tip">始</span>${data.STStartName}</div>
                                <div><span class="ed_tip">终</span>${data.STArriveName}</div>
                            </td>
                            <td>${data.RideDate} ${data.StartTime}<br><span style="color: grey;font-size: 14px;font-weight:normal;">${data.showcontent}</span></td>
                            <td>${data.MakeDate}</td>
                            <td>${data.CName}<br>${data.Mobile}</td>
                            <td>
                                <c:if test="${data.PayStatus == 0}">已取消</c:if>
                                <c:if test="${data.PayStatus == 1}">已支付</c:if>
                                <c:if test="${data.PayStatus == 2}">已预定</c:if>
                            </td>
                            <td>
                                <c:forEach items="${fn:split(data.CheckCodeList, ',')}" var="checkcode">
                                    <c:if test="${data.PayStatus == 0}">
                                        ${fn:split(checkcode, '|')[0]}|座位${fn:split(checkcode, '|')[1] <= 0?'无':fn:split(checkcode, '|')[1]}<br>
                                    </c:if>
                                    <c:if test="${data.PayStatus == 1}">
                                        <span <c:if test="${fn:split(checkcode, '|')[2] == 0}">style="color: grey;text-decoration:line-through;"</c:if>>${fn:split(checkcode, '|')[0]}|座位${fn:split(checkcode, '|')[1] <= 0?'无':fn:split(checkcode, '|')[1]}</span><br>
                                    </c:if>
                                    <c:if test="${data.PayStatus == 2}">
                                        <input type="checkbox" class="check_single" value="${fn:split(checkcode, '|')[0]}" style="margin-top: 0;">
                                        <span>${fn:split(checkcode, '|')[0]}|座位${fn:split(checkcode, '|')[1] <= 0?'无':fn:split(checkcode, '|')[1]}</span><br>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:if test="${data.PayStatus == 2}">
                                    <div class="buy_btn" onclick="paySaleOrder(this, '${data.ID}');">付款</div>
                                    <div class="cancel_btn" onclick="cancelSaleOrder('${data.ID}')">取消</div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
<%@include file="../common/footer.jsp" %>

<div class="layer_dialog" style="display:none;" id="payDialog">
    <input type="hidden" name="id">
    <div style="padding: 20px;">
        <span class="title">确认购买共<span class="number">5</span>张票?<br></span>
        <label style="padding-top: 10px;">
        <input type="checkbox" class="isLock" style="margin-top: 0;"> 锁定未付款票(不勾选取消)
        </label>
    </div>
</div>

<div class="layer_dialog" style="display:none;" id="cancelDialog">
    <input type="hidden" name="id">
    <div style="padding: 20px;">
        <span class="title">确认取消所有座位?<br></span>
        <label style="padding-top: 10px;">
            <input type="checkbox" class="isLock" style="margin-top: 0;"> 锁定未付款票(不勾选取消)
        </label>
    </div>
</div>

</body>
</html>

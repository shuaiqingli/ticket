<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
    <title>车票管理</title>
</head>
<script type="text/javascript" src="${basePath }/js/ticketmanage.js">
</script>
<script type="text/javascript">
    var $lmid = "${lm.id}";
    var $date = "${param.date}";
    var $date = "${param.ticketdate}";
</script>
<body>
<%--<c:if test="${empty param.tplid}">--%>
    <%@include file="../common/header.jsp" %>
<%--</c:if>--%>

<div class="container main_container">
    <form action="javascript:void(0)" method="get">
        <div>
            <a id="main_page" href="${basePath}/user/linemanage.do" style="color: black;border: none;"></a>
            <h2>
				<span style="color: #000;">
				</span>车票管理</h2>
        </div>
        <%--<c:if test="${empty param.tplid}">--%>
            <div class="row">
                <div class="span5" style="font: normal 18px '黑体';margin-top: 5px;">
                    &nbsp;&nbsp;&nbsp;${lm.lineid }（ ${lm.citystartname }${lm.ststartname }
                    - ${lm.cityarrivename }${lm.starrivename } ）
                </div>
                <div class="span7 pull-right">
                    <button class="btn pull-right" onclick="location.href = basePath+'/user/shiftManage?lmid=${param.lmid}&date='+$('._date').text().trim();"
                            style="margin-left: 10px;height: 35px;font-size: 14px;">
                        班次管理
                    </button>
                    <button class="btn pull-right btn_release"
                            style="margin-left: 10px;height: 35px;font-size: 14px;">
                        发布
                    </button>
                    <button class="btn pull-right btn_ticket"
                            style="margin-left: 10px;height: 35px;font-size: 14px;">
                        生成车票
                    </button>
                        <%--<button class="btn pull-right batch_approve" style="margin-left: 10px;height: 35px;font-size: 14px;">--%>
                        <%--更改票价--%>
                        <%--</button>--%>
                        <%--<button class="btn pull-right" style="margin-left: 10px;height: 35px;font-size: 14px;"--%>
                        <%--onclick="location.href='${basePath}/user/promotionlist.do?lmid=${lm.id}'">调价规则--%>
                        <%--</button>--%>
                    <button class="btn pull-right btn_setReleaseDay"
                            style="margin-left: 10px;height: 35px;font-size: 14px;"
                            data-toggle="modal" data-target="#setReleaseDay" day="${lm.releaseday}">设置
                    </button>
                <span class="pull-right" style="margin-left: 10px;height: 20px;font-size: 14px;margin-top:0.5em;">自动发布: <span
                        class="s_releaseday">${empty lm.releaseday ? 0:lm.releaseday }</span>天</span>
                        <%--<button class="btn pull-right" style="margin-left: 10px;height: 35px;font-size: 14px;" onclick="location.href='${basePath}/user/tripPriceRuleList?lmid=${lm.id}'">行程价格</button>--%>
                </div>
            </div>
        <%--</c:if>--%>
        <br/>
        <div class="row">
            <span style="margin-left:55px;">总共天数：<span
                    style="font-weight: bold;">&nbsp;${empty dateinfo.allday ? 0 : dateinfo.allday }&nbsp;</span>天</span>
        </div>
        <div class="row" style="margin-top:30px;font: normal 17px '黑体';">
            <div class="span12" style="text-align: center;margin-bottom: 1em;">
                <span class="imgprev"><img src="${basePath }/images/left.png" height="10px" width="10px"></span>
                <span class="date _date"
                      style="font: normal 17px '黑体';outline:none;background: #fff;border:none;text-align: center;border-color: #fff;">2015-11-26</span>
                <span class="weekday"
                      style="font: normal 17px '黑体';outline:none;background: #fff;border:none;text-align: center;border-color: #fff;">星期四</span>
                <span class="imgnext"><img src="${basePath }/images/right.png" height="10px" width="10px"></span>

                <%--<span class="pull-right isapprove" style="color: red;">未发布</span>--%>
                <%--<span class="pull-right isrelease" style="margin-right:20px;color: red;">未审核</span>--%>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;border-top:1px solid #ccc;margin-bottom: 0;">
            <thead>
            <tr>
                <th class="span3">班次号</th>
                <th class="span3">出发时间</th>
                <th class="span3">总票数(张)</th>
                <th class="span3">锁票数(张)</th>
                <th class="span3">正价票(张)</th>
                <th class="span3">调价票数(张)</th>
                <th class="span3">正价(元)</th>
                <th class="span3">调价(元)</th>
                <th class="span3">状态</th>
                <th class="span3">操作</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <c:forEach items="${shifts}" var="shift">
            <c:set var="quantity" value="${shift.ticketStore.allquantity - shift.ticketStore.lockquantity }"></c:set>
            <c:set var="couponquantity" value="${shift.ticketStore.couponquantity}"></c:set>
            <c:set var="sale" value="${quantity-shift.ticketStore.balanceQuantity}"></c:set>
            <table class="table table-striped ticket" style="margin-bottom: 0;">
                <tbody>
                <tr class="odd1 shift" style="color: ${shift.ticketStore.isrelease==2?'#999':''}">
                    <td class="span3"><span class="shiftCode">${shift.shiftcode}</span><i class="hided"></i></td>
                    <td class="span3"><span class="starttime">${shift.originstarttime}</span></td>
                    <td class="span3">
                        <span class="allquantity">
                            ${shift.ticketStore.allquantity}
                                <%--| ${quantity} - ${shift.ticketStore.balanceQuantity}--%>
                        </span>
                    </td>
                    <td class="span3">
                        <a href="<%=basePath%>/user/lockTicketManage.do?startDate=${date}&linename=${shift.shiftcode}&citystartid=${shift.citystartid}&cityarriveid=${shift.cityarriveid}">
                            <span class="lockquantity">${shift.ticketStore.lockquantity}</span>
                        </a>
                    </td>
                    <td class="span3">
                        <span>
                            ${sale - (couponquantity - shift.ticketStore.balancecouponquantity)<0?0:sale - (couponquantity - shift.ticketStore.balancecouponquantity)}
                            <%--${(shift.ticketStore.allquantity-shift.ticketStore.lockquantity-shift.ticketStore.balanceQuantity) - (shift.ticketStore.couponquantity - shift.ticketStore.balancecouponquantity) }--%>
                        </span>
                        /
                        <span >
                            <%--${(shift.ticketStore.allquantity - shift.ticketStore.couponquantity) - shift.ticketStore.lockquantity }--%>
                            <%--${shift.ticketStore.allquantity - shift.ticketStore.couponquantity - shift.ticketStore.lockquantity }--%>
                            <%--<br/> ${shift.ticketStore.allquantity} - ${shift.ticketStore.couponquantity} - ${shift.ticketStore.lockquantity }--%>
                            ${quantity-couponquantity<0?0:quantity-couponquantity}
                        </span>
                    </td>
                    <td class="span3">
                        <span class="balancecouponquantity">
                             ${sale>couponquantity?couponquantity:sale}
                            <%--${shift.ticketStore.couponquantity - shift.ticketStore.balancecouponquantity}--%>
                        </span>
                        /
                        <span class="couponquantity">
                                ${quantity<couponquantity?quantity:couponquantity}
                        </span>
                    </td>
                    <c:set var="minprice" value="${shift.ticketLines[0].ticketprice}"></c:set>
                    <c:set var="maxprice" value="#{minprice}"></c:set>
                    <c:set var="_minprice" value="${shift.ticketLines[0].favprice}"></c:set>
                    <c:set var="_maxprice" value="${_minprice}"></c:set>
                    <c:forEach var="tl" items="${shift.ticketLines}" varStatus="status">
                        <c:set var="minprice" value="${tl.ticketprice>minprice?tl.ticketprice:minprice}"></c:set>
                        <c:set var="maxprice" value="${tl.ticketprice<maxprice?tl.ticketprice:maxprice}"></c:set>
                        <c:set var="_minprice" value="${tl.ticketprice>_minprice?tl.favprice:_minprice}"></c:set>
                        <c:set var="_maxprice" value="${tl.ticketprice<_maxprice?tl.favprice:_maxprice}"></c:set>
                    </c:forEach>
                    <td class="span3">
                        <c:choose>
                            <c:when test="${maxprice == minprice }">
                                <span class="minprice">${maxprice}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="minprice">${maxprice}</span> - <span
                                    class="maxprice"> ${minprice} </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="span3">
                        <c:choose>
                            <c:when test="${_maxprice == _minprice }">
                                <span class="minprice">${_maxprice}</span>
                            </c:when>
                            <c:otherwise>
                                <span class="minprice">${_maxprice}</span> - <span
                                    class="maxprice"> ${_minprice} </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="span3">
                        ${shift.status==0?'正常':shift.status==1?'已取消':''}|${shift.ticketStore.isrelease==0?'未发布':shift.ticketStore.isrelease==1?'已发布':'取消发布'}
                    </td>
                    <td class="span3 no">
                        <a href="javascript:" status="${shift.ticketStore.isrelease==1?2:1}"  role="button " class="btn btn_update_release ${shift.ticketStore.isrelease==0 or not empty param.tplid?'hide':''}" style="z-index: 100">
                            ${shift.ticketStore.isrelease==1?'取消发布':'恢复发布'}
                        </a>
                    </td>
                </tr>
                <c:set value="" var="ststartname"></c:set>
                <c:forEach var="tl" items="${shift.ticketLines}">

                    <c:set var="quantity" value="${tl.allquantity}"></c:set>
                    <c:set var="couponquantity" value="${tl.couponquantity}"></c:set>
                    <c:set var="sale" value="${quantity-tl.balancequantity}"></c:set>

                    <c:if test="${ststartname != tl.ststartname}">
                        <tr class="odd2" style="display: none">
                            <td colspan="10">
                                <span class="ststartname">${tl.ststartname} (${tl.starttime})</span>
                            </td>
                        </tr>
                    </c:if>
                    <tr class="odd2 shiftdetail" style="display: none;">
                        <td class="span3" colspan="2">
                            <%--<c:if test="${ststartname != tl.ststartname}">--%>
                                <%--<span class="ststartname">${tl.ststartname} (${tl.starttime})</span> ---%>
                                <%--<span class="starrivename">${tl.starrivename}</span>--%>
                            <%--</c:if>--%>
                                <span style="margin-left: 50px;" class="starrivename">${tl.starrivename}</span>
                        </td>
                        <td class="span3"><span>${tl.allquantity}</span>&nbsp;</td>
                        <td class="span3" style="">&nbsp;</td>
                        <td class="span3">
                            <span class="">
                                 ${sale - (couponquantity - tl.balancecouponquantity)<0?0:sale - (couponquantity - tl.balancecouponquantity)} /
                                ${tl.allquantity - tl.couponquantity }
                            </span>&nbsp;
                        </td>
                        <td class="span3">
                            <span class="">
                               ${sale>couponquantity?couponquantity:sale} /
                                ${tl.couponquantity}
                            </span>&nbsp;
                        </td>
                        <td class="span3"><span class="ticketprice">${tl.ticketprice}</span></td>
                        <td class="span3"><span class=favprice>${tl.favprice}</span></td>
                        <td class="span3">&nbsp;</td>
                    </tr>
                    <c:set value="${tl.ststartname}" var="ststartname"></c:set>
                </c:forEach>

                </tbody>
            </table>
        </c:forEach>
    </form>
</div>

<!-- Modal -->
<div id="setReleaseDay" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true" style="left:40%;width: 800px;height: 380px;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">自动发布天数</h3>
    </div>
    <div class="modal-body" style="font: normal 17px '黑体';">
        <div class="row" style="margin-left: 20px;margin-top: 1px;">
            <span style="color: red;">提示：自动发布天数必须是30天以内</span>
        </div>
        <div class="row" style="margin-left: 20px;margin-top: 30px;">
            <span>自动发布天数：</span>
            <input class="releaseday" type="text" maxlength="2">
        </div>
        <br>
        <div class="row" style="text-align: center;">
            <input value="确定" type="button" class="btn btn-success updateReleaseDay"
                   style="height:40px;width: 15%;font-size: 18px;">
            <input value="取消" data-dismiss="modal" type="button" class="btn btn-danger cancel"
                   style="height:40px;width: 15%;font-size: 18px;">
        </div>
    </div>
</div>

<div class="pull-left release" style="padding-left:70px;padding-top: 30px;display: none">
    <span>开始时间：</span><input style="width: 120px;" name="begindate" placeholder="开始时间" size="16" value="" readonly="readonly" class="date begindate" type="text">
    <span>结束时间：</span><input style="width: 120px;" name="enddate" placeholder="结束时间" size="16" value="" readonly="readonly" class="date enddate" type="text">
    <br>
    <div class="first_time" style="display: none;">
        <span>发车时间：</span>
        <input style="width: 120px;" name="firsttime" placeholder="发车时间" size="16" value="" readonly="readonly" type="text">
        <div>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp" %>

</body>

<style type="text/css">
    .title {
        font-size: 30px;
        line-height: 2em;
    }

    .info {
        display: inline-block;
        line-height: 2em;
    }

    .btns {
        display: inline-block;
    }

    .days {
        display: inline-block;
        line-height: 2em;
    }

    .fl {
        float: left;
    }

    .fr {
        float: right;
    }

    .clear {
        clear: both;
    }

    .dates {
        line-height: 4em;
        text-align: center;
    }

    .list-cell {
        border-top: 1px solid #ccc;
    }

    .check-box {
        width: 10px;
        height: 10px;
        border: 1px solid #ccc;
        display: inline-block;
        line-height: 2em;
    }

    .check-box-all {
        width: 10px;
        height: 10px;
        border: 1px solid #ccc;
        display: inline-block;
        line-height: 2em;
    }

    .title-row div {
        display: inline-block;
    }

    .title-row {
        margin-left: 2em;
        margin-top: 1em;
        padding-bottom: 0.5em;

    }

    .line-box {
        border-bottom: 1px solid #ccc;
        padding-bottom: 0.5em;
    }

    .infos-row div {
        display: inline-block;
        float: left;
    }

    .detail-cell div {
        display: inline-block;
    }

    .detail-cell {
        margin-left: 13em;
    }

    .infos-row {
        margin-left: 5em;
    }

    .info-row div {
        display: inline-block;
        line-height: 2em;
    }

    .info-row .check-box {
        margin-top: 10px;
    }

    .ofh {
        overflow: hidden;
    }

    .pdl05 {
        padding-left: 1em;
    }

    .pdl1 {
        padding-left: 1em;
    }

    .pdl12 {
        padding-left: 1.2em;
    }

    .pdl2 {
        padding-left: 2em;
    }

    .pdl18 {
        padding-left: 1.8em;
    }

    .pdl25 {
        padding-left: 2.5em;
    }

    .pdl22 {
        padding-left: 2.2em;
    }

    .pdl3 {
        padding-left: 3em;
    }

    .pdl33 {
        padding-left: 3.3em;
    }

    .pdl4 {
        padding-left: 4em;
    }

    .pdl43 {
        padding-left: 4.3em;
    }

    .pdl5 {
        padding-left: 5em;
    }

    .pdr1 {
        padding-right: 1em;
    }

    .pdr2 {
        padding-right: 2em;
    }

    .mrl5 {
        margin-left: 5em;
    }

    .mrl4 {
        margin-left: 4em;
    }

    .td-cell {
        margin-left: 2em;
    }

    .checked {
        background-color: #ccc;
    }

    .showed {
        width: 0;
        height: 0;
        border-left: 5px solid transparent;
        border-right: 5px solid transparent;
        border-bottom: 5px solid #000;
        display: inline-block;
        margin-top: 0.8em;
        cursor: pointer;
        vertical-align: top;
        margin-top: 6px;
        margin-left: 6px;
    }

    .hided {
        width: 0;
        height: 0;
        border-left: 5px solid transparent;
        border-right: 5px solid transparent;
        border-top: 5px solid #000;
        display: inline-block;
        margin-top: 0.8em;
        cursor: pointer;
        vertical-align: top;
        margin-top: 6px;
        margin-left: 6px;
    }

    .info-detail {
        font-size: 17px;
    }
</style>
</html>
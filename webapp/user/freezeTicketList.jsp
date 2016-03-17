<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/freezeTicketList.js"></script>
    <title>锁票列表</title>
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
    <form action="<%=basePath%>/user/freezeTicketList.do" method="get">
        <div class="page-header">
            <a id="main_page" href="<%=basePath%>/user/lockTicketManage.do" style="color: black"></a>
            <h2>锁票列表</h2>
        </div>
        <div class="row">
            <div class="pull-left" style="padding-left:30px;">
                <select name="type" class="span2">
                    <option value="">--请选择类型--</option>
                    <option value="1" <c:if test="${param.type==1}">selected</c:if> >锁定</option>
                    <option value="2" <c:if test="${param.type==2}">selected</c:if> >解锁</option>
                </select>
                <input type="text" name="rideDate" value="${param.rideDate}" placeholder="发车日期" style="height:30px;" >
                <input type="text" name="makeDate" value="${param.makeDate}" placeholder="操作日期" style="height:30px;" >
                <input type="text" name="keyword" value="${param.keyword}"
                       placeholder="站务/线路/班次" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
        </div>
        <div style="text-align: center;width: 100%;margin-top: 5px;">
            <div style="display:inline-block;width: 100%;">
                <table class="list" cellspacing="0" style="border-collapse:collapse;width: 100%;">
                    <tr style="background-color: #fff;border: 0px;text-align: center;border: 1px solid #ccc;">
                        <td>站务</td>
                        <td>类型</td>
                        <td>线路</td>
                        <td>班次</td>
                        <td>数量</td>
                        <td>座位号</td>
                        <td>发车日期</td>
                        <td>操作时间</td>
                    </tr>
                    <c:forEach items="${dataList}" var="data">
                        <tr class="item">
                            <td>${data.username}</td>
                            <td>
                                <c:if test="${data.type == 1}">锁定</c:if>
                                <c:if test="${data.type == 2}">解锁</c:if>
                            </td>
                            <td>${data.linename}</td>
                            <td>${data.shiftnum}</td>
                            <td>${data.quantity}</td>
                            <td>${data.seats}</td>
                            <td>${data.ridedate}</td>
                            <td>${fn:substring(data.makedate,0,16)}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <%@include file="../common/page.jsp" %>
    </form>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>

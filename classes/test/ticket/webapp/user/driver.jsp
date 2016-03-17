<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <%@ include file="../common/head.jsp" %>
    <script type="text/javascript" src="<%=basePath%>/js/driver.js"></script>
    <title>司机列表</title>
</head>

<body>
<%@include file="../common/header.jsp" %>
<div class="container main_container">
    <form action="<%=basePath%>/user/drivermanage.do" method="get">
        <div class="page-header">
            <h2>司机列表</h2>
        </div>
        <div class="row">
            <div class="pull-right" style="padding-right:10px;">
                <input type="text" name="dname" value="${page.param.dname }"
                       placeholder="手机/司机名称" value="" style="height:30px;">
                <a class="btn"
                   style="padding:5px 12px;margin:-8px 0 0 10px;"
                   href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
            </div>
            <div class="btn-group pull-right" style="margin-right:20px;">
                <a class="btn" href="<%=basePath%>/user/driveradd.do">
                    <i class="icon-plus-sign"></i>新增司机
                </a>
            </div>
        </div>
        <table class="table table-striped" style="font-size: 14px;">
            <thead>
            <tr>
                <th>编号</th>
                <th>司机</th>
                <th>手机号码</th>
                <th>身份证号码</th>
                <th>制作日期</th>
                <th>是否停用</th>
                <th style="max-width: 150px;">线路</th>
                <th class="pull-right">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.data }" var="o">
                <tr class="odd">
                    <td>${o.id }</td>
                    <td>${o.dname }</td>
                    <td>${o.dmobile }</td>
                    <td>${o.idcard }</td>
                    <td>${o.makedate }</td>
                    <td>${o.isstop == 1 ? '是':'否' }</td>
                    <td style="max-width: 180px;">
                        <c:forEach items="${lineDataList }" var="lineData">
                            <c:if test="${lineData.DriverID == o.id}">
                                <a href="<%=basePath%>/user/lineadd.do?id=${lineData.id}#1">
                                        ${lineData.CityStartName}↔${lineData.CityArriveName}(${lineData.Type == 1 ? '固定':'机动' })
                                </a>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <div class="btn-group pull-right">
                            <a class="btn" href="<%=basePath%>/user/driveradd.do?id=${o.id}">
                                <i class="icon-pencil"></i> 编辑
                            </a>
                            <a class="btn" href="javascript:resetPassword('${o.id}');">
                                <i class="icon-pencil"></i> 重置密码
                            </a>
                            <a class="btn" href="<%=basePath%>/user/driverHolidayList.do?driverid=${o.id}">
                                <i class="icon-list"></i> 假期管理
                            </a>
                            <!-- 									<a class="btn btn-danger" href="javascript:void(0)" onclick="del(${o.id});"> -->
                            <!-- 										<i class="icon-remove"></i> 删除 -->
                            <!-- 									</a>  -->
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

<div class="resetPwd" style="display:none">
    <div class="row">
        <div style="margin-left: 50px;">
            <label>新密码</label>
            <input name="newpassword" type="password" class="span3" id="newpassword" value="" datatype="*" nullmsg="新密码不能为空"/>
        </div>
    </div>
    <div class="row">
        <div style="margin-left: 50px;">
            <label>确认新密码</label>
            <input name="affrimpassword" type="password" class="span3" id="affrimpassword" value="" datatype="*" nullmsg="确认新密码不能为空"/>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">

    //删除
    function del(id) {
        layer.confirm('确定删除该线路吗？',
                {
                    btn: ['确定', '取消']
                }, function (index, layero) {
// 			var url = basePath+"/user/delschedule.do";
// 			ajax({id:id,isDel:1}, url,function(json){
// 				location.reload(true);
// 			});
                });
    }

    $(function () {

    });
</script>
</html>

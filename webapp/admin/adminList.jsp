<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminList.js"></script>
	<title>管理员列表</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<form action="<%=basePath%>/admin/adminList.do" method="get">
			<div class="page-header">
				<h2>管理员列表</h2>
			</div>
			<div class="row">
				<div class="pull-right" style="padding-right:10px;">
					<input type="text" name="realName" value="${page.param.realName}"
						placeholder="姓名/手机/编号" style="height:30px;"> 
					<a class="btn"
						style="padding:5px 12px;margin:-8px 0 0 10px;"
						href="javascript:void(0)" onclick="$(this).parents('form')[0].submit();">搜索</a>
				</div>
				<div class="btn-group pull-right" style="margin-right:20px;">
					<a class="btn" href="<%=basePath%>/admin/adminDataList.do">
						<i class="icon-plus-sign"></i>管理员概览
					</a>
					<a class="btn" href="<%=basePath%>/admin/adminAdd.do">
						<i class="icon-plus-sign"></i>新增管理员
					</a>
				</div>
			</div>
			<table class="table table-striped" style="font-size: 14px;">
				<thead>
					<tr>
						<th>编号</th>
						<th>姓名</th>
						<th>手机</th>
<!-- 						<th>邮箱</th> -->
<!-- 						<th>城市</th> -->
<!-- 						<th>车站</th> -->
						<th>角色</th>
						<th>注册日期</th>
						<th class="pull-right">操作&nbsp;</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.data }" var="admin">
						<tr class="odd">
							<td>${admin.userID }</td>
							<td>${admin.realName }</td>
							<td>${admin.mobile }</td>
							<td>${empty admin.role ? '超级管理员':admin.role.rolename }</td>
							<td>${admin.makeDate}</td>
							<td>
								<div class="btn-group pull-right">
									<a class="btn" href="<%=basePath%>/admin/adminEdit.do?userID=${admin.userID}">
										<i class="icon-pencil"></i> 编辑
									</a> 
                                    <a class="btn" href="javascript:resetPassword('${admin.userID}');">
										<i class="icon-pencil"></i> 重置密码
									</a>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
            
            <div class="resetPwd" style="display:none">
				<br/>
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
			<%@include file="../common/page.jsp"%>
		</form>
	</div>

	<%@include file="../common/footer.jsp" %>
</body>
</html>
<script type="text/javascript">
	function resetPassword(userid){
		layer.open({
		    type: 1,
		    //scrollbar: true,
		    closeBtn:1,
		    btn: ['确定'],
		    offset:'25%',
		    area: ['300', '300px'],
		    title:'重置密码',
		    content: $('.resetPwd'),
		    yes: function(index){
				var newpassword = $("#newpassword").val();
				var affirmpassword = $("#affrimpassword").val();
				if(newpassword !=affirmpassword){
					layer.msg('确认新密码输入不一致');
					ee();
				}
				$.ajax({
                	type:"post",
                	url: basePath+"/admin/changePwd.do", //调用的url
                	data:{"newpassword":newpassword,"userid":userid},  //这里是要传递的参数，格式为data: "{paraName:paraValue}",下面将会看到
                	dataType: 'json',   //返回Json类型                    
                	success:function(result) {     //回调函数，data返回值
				    	layer.msg('result==='+result.status);
						if(result.status=="00"){
							layer.msg('修改成功');
						}else{
							layer.msg('修改失败');
						}
                 	}
           		});
		    	//layer.msg('这是最常用的吧'+userid);
		    },
		    success:function(){
				//layer.msg('这是最常用的吧1');
		    	//findreslist();
		    }
		});
	}
</script>

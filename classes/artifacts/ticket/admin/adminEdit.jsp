<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/adminEdit.js"></script>
	<title>
		<c:if test="${empty admin.userID}">
			新增管理员
		</c:if>
		<c:if test="${not empty admin.userID}">
			编辑管理员
		</c:if>
	</title>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container">
		<div class="page-header">
			<h2>
				<a id="main_page" href="<%=basePath%>/admin/adminList.do" style="color: black"></a>
				<c:if test="${empty admin.userID }">
					新增管理员
				</c:if>
				<c:if test="${not empty admin.userID }">
					编辑管理员
				</c:if>
			</h2>
		</div>
		<ul class="nav nav-tabs">
			<li class="active"><a href="#information" data-toggle="tab">基本信息</a></li>
			<c:if test="${not empty admin.userID}">
				<li><a href="#stationList" data-toggle="tab">站点列表</a></li>
				<li><a href="#lineList" data-toggle="tab">线路列表</a></li>
			</c:if>
		</ul>
		<div class="tab-content">
			<!-- 基本信息 -->
			<div class="tab-pane active" id="information">
				<form method="post" id="adminForm" action="${basePath}/admin/saveadmin.do">

					<%-- 			<input type="hidden" name="userID" value="${admin.userID }"> --%>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>员工编号(用于登录)</label>
							<input type="text" <c:if test="${not empty admin.userID }">readonly</c:if> name="userID" value="${admin.userID }" class="span3" datatype="*" nullmsg="员工编号不能为空">
						</div>
					</div>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>姓名</label>
							<input type="text" name="realName" value="${admin.realName }" class="span3" datatype="*" nullmsg="姓名不能为空">
						</div>
					</div>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>手机(用于登录)</label>
							<input type="text" name="mobile" value="${admin.mobile }" class="span3" datatype="m" nullmsg="手机不能为空" errormsg="手机格式错误">
						</div>
					</div>
					<!-- 			<div class="row"> -->
					<!-- 				<div style="margin-left: 30px;"> -->
					<!-- 					<label>邮箱</label> -->
					<%-- 					<input type="text" name="email" value="${admin.email }" class="span3"  nullmsg="邮箱不能为空" errormsg="邮箱格式错误">				 --%>
					<!-- 				</div> -->
					<!-- 			</div> -->
					<c:if test="${empty admin.userID }">
						<div class="row">
							<div style="margin-left: 30px;">
								<label>密码</label>
								<input type="password" name="password" value="${admin.password }" class="span3" datatype="*" nullmsg="密码不能为空" <c:if test="${admin.password != null}">isEncrypt=true</c:if> >
							</div>
						</div>
						<div class="row">
							<div style="margin-left: 30px;">
								<label>确认密码</label>
								<input type="password" name="password2" value="${admin.password }" class="span3" datatype="*" recheck="password" nullmsg="确认密码不能为空" errormsg="两次密码输入不一致" <c:if test="${admin.password != null}">isEncrypt=true</c:if>>
							</div>
						</div>
					</c:if>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>上级</label>
							<input type="text" class="span3 parentname" readonly="readonly"  value="${parent.realName }"/>
							<input type="hidden" name="parentid" class="span3" readonly="readonly" value="${parent.userID }" />
							<input class="btn choose_parent" value="选择" type="button" style="margin-top: -8px;" data-toggle="modal" data-target="#myModal2"/>
						</div>
					</div>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>公司</label>
							<input type="text" class="span3" readonly="readonly" name="companyname"  value="${admin.companyname }" datatype="*" nullmsg="公司不能为空"/>
							<input type="hidden" name="tcid" class="span3" readonly="readonly" value="${admin.tcid }" />
							<input class="btn choose_company" value="选择" type="button" style="margin-top: -8px;" data-toggle="modal" data-target="#myModal3"/>
						</div>
					</div>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>角色(权限控制)</label>
							<select name="roleID" class="span3"  nullmsg="角色不能为空">
								<option value="" style="color:red;"<c:if test='${admin.isAdmin == 1 }'>selected="selected"</c:if> >[超级管理员]</option>
								<c:forEach items="${roles }" var="role">
									<option value="${role.id}" <c:if test='${admin.roleID == role.id }'>selected</c:if> >${role.rolename }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<br/>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>是否禁用</label>
							是:<input type="radio" value="1" name="isDel" <c:if test="${admin.isDel == 1}">checked="checked"</c:if>  class="span1"/>
							否:<input type="radio" value="0" name="isDel" <c:if test="${empty admin.isDel or admin.isDel==0}">checked="checked"</c:if> class="span1"/>
						</div>
					</div>
					<br/>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>是否有审核权限</label>
							是:<input type="radio" value="1" name="isapprove" <c:if test="${admin.isapprove == 1}">checked="checked"</c:if>  class="span1"/>
							否:<input type="radio" value="0" name="isapprove" <c:if test="${empty admin.isapprove or admin.isapprove==0}">checked="checked"</c:if> class="span1"/>
						</div>
					</div><br/>
					<div class="row">
						<div style="margin-left: 30px;">
							<label>是否自动绑定线路</label>
							是:<input type="radio" value="1" name="isautobindline" <c:if test="${empty admin.isautobindline or admin.isautobindline == 1}">checked="checked"</c:if>  class="span1"/>
							否:<input type="radio" value="0" name="isautobindline" <c:if test="${admin.isautobindline == 0}">checked="checked"</c:if> class="span1"/>
						</div>
					</div>
<!-- 					<div class="row"> -->
<!-- 						<a href="#myModal" role="button" class="btn span2" data-toggle="modal">选择车站</a> -->
<!-- 					</div> -->
<!-- 					<br/> -->
<!-- 					<div class="row stationnames" style="margin-left: 10px;color: red;"> -->
<%-- 						${stationnames} --%>
<!-- 					</div> -->
					<br/>
					<div class="row">
						<div class="span2" style="margin-top:8px;">
							<input type="submit" class="btn" value="保存">
						</div>
						<div class="span2" style="margin-top:8px;">
							<input type="button" onclick="history.go(-1)"  class="btn" value="返回">
						</div>
					</div>
					<input type="hidden" name="ids" value="${ids}"/>
					<input type="hidden" name="delids" />
					<!-- Modal -->
					<div id="myModal"  class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 800px;height: 540px;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							<h3 id="myModalLabel">选择车站</h3>
						</div>
						<div class="modal-body" style="min-height: 400px;padding-left: 50px;">
							<div class="row">
								<div style="margin-left: 30px;padding-top: 10px;" class="pull-right">
						<span>城市：</label>
							<select class="span2 citys"  name="cityId" <c:if test="${not empty admin.cityId}">disabled="disabled"</c:if> >
								<option value="">--选择城市--</option>
								<c:forEach var="city" items="${citys}">
									<option  value="${city.id }" <c:if test='${city.id == admin.cityId }'>selected="selected"</c:if> >${city.cityname }</option>
								</c:forEach>
							</select>
							<!-- 			<a class="btn" style="padding:5px 12px;margin:-8px 0 0 10px;" href="javascript:void(0)" onclick="void(0)">搜索</a>				 -->
								</div>
								<div style="clear: both;"></div>
								<hr/>
								<table class="table table-striped station_list" style="font-size: 14px;">
									<thead>
									<tr>
										<th>选择</th>
										<th>城市</th>
										<th>拼音</th>
									</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn  btn-primary chooseStations" data-dismiss="modal" aria-hidden="true">确定</button>
						</div>
					</div>
					<div id="myModal2"  class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:40%;width: 800px;height: 540px;">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							<h3 id="myModalLabel">选择上级</h3>
						</div>
						<div class="modal-body" style="min-height: 400px;padding-left: 50px;">
							<div class="row pull-right">
								<input type="text"  placeholder="姓名/手机/编号" style="height:30px;">
								<a class="btn admin_query" style="padding:5px 12px;margin:-8px 0 0 10px;">搜索</a>
							</div>
							<div style="clear: both;"></div>
							<hr/>
							<table class="table table-striped admin_list" style="font-size: 14px;">
								<thead>
								<tr>
									<th>编号</th>
									<th>姓名</th>
									<th>手机</th>
								</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button class="btn  btn-primary chooseAdmin" data-dismiss="modal" aria-hidden="true">确定</button>
						</div>
					</div>
				</form>
			</div>
			<!-- 站点列表 -->
			<div class="tab-pane" id="stationList">
				<div class="row">
					<div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
						<a class="btn" href="javascript:void(0)" onclick="chooseStation();">
							<i class="icon-plus-sign"></i>新增站点
						</a>
					</div>
				</div>
				<div class="row">
					<div class="span12">
						<table class="table table-striped dataTable">
							<thead>
							<tr>
								<th>编号</th>
								<th>名称</th>
								<th>拼音</th>
								<th>是否热门</th>
								<th>所属城市</th>
								<th class="pull-right">操作</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${stationList }" var="station">
								<tr class="odd">
									<td>${station.ID }</td>
									<td>${station.CityName }</td>
									<td>${station.STPinYin }</td>
									<td>${station.ishot == 1 ? '是':'否' }</td>
									<td>${station.ParentName }</td>
									<td>
										<a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
										   onclick="delStation('${admin.userID}','${station.ID }');">
											<i class="icon-trash icon-white"></i>
										</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- 线路列表 -->
			<div class="tab-pane" id="lineList">
				<div class="row">
					<div class="btn-group pull-left" style="margin:10px 20px 10px 40px; ">
						<a class="btn" href="javascript:void(0)" onclick="chooseLine();">
							<i class="icon-plus-sign"></i>新增线路
						</a>
					</div>
				</div>
				<div class="row">
					<div class="span12">
						<table class="table table-striped dataTable">
							<thead>
							<tr>
								<th>编号</th>
								<th>线路名称</th>
								<th>始发站</th>
								<th>终点站</th>
								<th>所属公司</th>
								<th class="pull-right">操作</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${lineList }" var="line">
								<tr class="odd">
									<td>${line.LineID }</td>
									<td>${line.LineName }</td>
									<td>${line.STStartName }</td>
									<td>${line.STArriveName }</td>
									<td>${line.TransCompany }</td>
									<td>
										<a class="btn btn-danger pull-right btn-mini" href="javascript:void(0)"
										   onclick="delLine('${admin.userID}','${line.ID }');">
											<i class="icon-trash icon-white"></i>
										</a>
									</td>
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
	<div id="myModal3"  class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="left:50%;width: 400px;height: 540px;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">选择公司</h3>
  </div>
  <div class="modal-body" style="min-height: 400px;padding-left: 50px;">
	<div class="row pull-right">
		<input type="text"  placeholder="公司名称、拼音" style="height:30px;"> 
		<a class="btn company_query" style="padding:5px 12px;margin:-8px 0 0 10px;">搜索</a>			
	</div>
	<div style="clear: both;"></div>
	<hr/>
	<table class="table table-striped company_list" style="font-size: 14px;">
		<thead>
			<tr>
				<th>名称</th>
				<th>简称</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
  </div>
  <div class="modal-footer">
    <button class="btn  btn-primary chooseCompany" data-dismiss="modal" aria-hidden="true">确定</button>
  </div>
</div>
</body>
<script type="text/javascript">
$(function(e){
	if("${admin.cityId}"){
		getStationList("${admin.cityId}");
	}
	//选择城市
	$(".citys").change(function(){
		var name = $(this.options[this.selectedIndex]).text();
		console.debug(name);
		$("[name=cityName]").val(name);
		var parentID = $(this).val();
		if(parentID==""){
			return;
		}
		getStationList(parentID);
	});
	//选择站点
	$(".stationlist").change(function(){
		var name = $(this.options[this.selectedIndex]).text();
		$("[name=sTName]").val(name);
	});
	
	$('.chooseStations').click(function(){
		var items = $('#myModal').find('input:checked');
		var ids = "";
		var delids = "";
		var stationnames = "";
		$.each(items,function(i,v){
			ids+=$(v).val();
			stationnames+=$(this).parents('tr').find('td').eq(1).text();
			if(i!=items.length-1){
				ids+=",";
				stationnames+="、";
			}
		});
		var notchecked = $('#myModal').find('[type=checkbox]').not(items);
		$.each(notchecked,function(i,v){
			delids+=$(v).val();
			if(i!=notchecked.length-1){
				delids+=",";
			}
		});
		$('[name=ids]').val(ids);
		$('[name=delids]').val(delids);
		$('.stationnames').text(stationnames);
	});
	
	
	$('.choose_parent').click(function(){
		var parent = $('.admin_list tbody');
		parent.find('tr').remove();
		ajax({type:'json'}, basePath+"/admin/adminList.do",function(json){
			jeach(json.data, function(k,v){
				parent.append('<tr class="odd"><td><input name="pid" type="radio" value="'+v.userID+'"/> &nbsp;&nbsp;&nbsp;'+v.userID+'</td><td>'+v.realName+'</td><td>'+v.mobile+'</td></tr>');
			});
		})
	});
	
	$('.choose_company').click(function(){
		var parent = $('.company_list tbody');
		parent.find('tr').remove();
		ajax({type:'json'}, basePath+"/admin/companylist",function(json){
			jeach(json.data, function(k,v){
				parent.append('<tr class="odd"><td><input  type="radio" value="'+v.id+'"/> &nbsp;&nbsp;&nbsp;<span>'+v.companyname+'</span></td><td>'+v.shortname+'</td></tr>');
			});
		})
	});
	
	$('.admin_query').click(function(){
		var val = $(this).prev().val();
		var parent = $('.admin_list tbody');
		parent.find('tr').remove();
		ajax({type:'json',realName:val}, basePath+"/admin/adminList.do",function(json){
			jeach(json.data, function(k,v){
				parent.append('<tr class="odd"><td><input name="pid" type="radio" value="'+v.userID+'"/> &nbsp;&nbsp;&nbsp;'+v.userID+'</td><td>'+v.realName+'</td><td>'+v.mobile+'</td></tr>');
			});
		})
	});
	
	$('.company_query').click(function(){
		var val = $(this).prev().val();
		var parent = $('.company_list tbody');
		parent.find('tr').remove();
		ajax({type:'json',companyname:val}, basePath+"/admin/companylist",function(json){
			jeach(json.data, function(k,v){
				parent.append('<tr class="odd"><td><input  type="radio" value="'+v.id+'"/> &nbsp;&nbsp;&nbsp;<span>'+v.companyname+'</span></td><td>'+v.shortname+'</td></tr>');
			});
		})
	});
	
	//选择上级
	$('.chooseAdmin').click(function(){
		var pid = $('.admin_list').find("input:checked").first();
		$('[name=parentid]').val(pid.val());
		$('.parentname').val(pid.parents('tr').find('td').eq(1).text());
	});
	
	$('.chooseCompany').click(function(){
		var tcid = $('.company_list').find("input:checked").first();
		$('[name=tcid]').val(tcid.val());
		$('[name=companyname]').val(tcid.next().text());
	});
	
	
	//提交表单
	$("form").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
			return true;
		},
		beforeSubmit:function(curform){
		},
		callback:function(data){
		console.debug(data);
			if(data>=1){
				layer.msg('保存成功');
				location.href = basePath+"/admin/adminList.do"
			}else if(data==-100){
				layer.msg('该管理员已经存在');
			}else{
				layer.msg('保存失败');
			}
		}
	});
});
//获取城市列表
function getStationList(parentID){
	var data = {parentID:parentID};
		$.ajax({
		   type: "POST",
		   dataType:"JSON",
		   url: basePath+"/public/stationlist.do",
		   data: data,
		   success: function(json){
		   		if(json){
		   			$(".stationlist").children().not($(".stationlist").children().eq(0)).remove();
		   			console.debug(json);
		   			$('.station_list tbody tr').remove();
		   			var parent = $('.station_list tbody');
		   			$.each(json,function(i,v){
		   				var arr = "${ids}".split(',');
		   				var ischecked = false;
		   				$.each(arr,function(si,sv){
		   					if(v.iD==sv){
		   						ischecked = true;
		   					}
		   				});
		   				if(ischecked){
			   				parent.append('<tr class="odd"><td><input type="checkbox" checked value="'+v.iD+'"/></td><td>'+v.cityName+'</td><td>'+v.sTPinYin+'</td></tr>');
		   				}else{
		   					parent.append('<tr class="odd"><td><input type="checkbox" value="'+v.iD+'"/></td><td>'+v.cityName+'</td><td>'+v.sTPinYin+'</td></tr>');
		   				}
		   			});
		   		}
		   }
		});
}
</script>
</html>

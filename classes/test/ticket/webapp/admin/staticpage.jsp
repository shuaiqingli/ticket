<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<%@ include file="../common/head.jsp"%>
	<title>
		静态页面
	</title>
	<style type="text/css">
		ul,li{list-style: none;padding: 0px;margin: 0px;}
	</style>
	<style type="text/css">
	    @import url('${basePath }/css/summernote.css');
	    @import url('http://cdn.gbtags.com/twitter-bootstrap/3.2.0/css/bootstrap.css');
	    @import url('http://cdn.gbtags.com/font-awesome/4.1.0/css/font-awesome.min.css');
	</style>
	<script src="http://cdn.gbtags.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdn.gbtags.com/twitter-bootstrap/3.2.0/js/bootstrap.js"></script>
	 <script src="${basePath }/js/summernote.min.js"></script>
	<script src="${basePath }/js/summernote-zh-CN.js"></script>
</head>

<body>
	<%@include file="../common/header.jsp"%>
	<div class="container main_container" >
		<div class="page-header">
			<h2>
				静态页面
			</h2>
		</div>
		 <form id='formFile' name='formFile' method="post" action="${basePath}/user/imageFileUpload.do" 
	    	enctype="multipart/form-data" target="exec_target" style="display: none;">
		    <input type='file' id='fileUp' name='fileUp' />
		    <div id='uploadLog'>
		    </div>
		    <br />
		    <img width='200' src='' height='200' id='imgShow' alt='缩略图' />
	    </form>
	    <iframe id="exec_target" name="exec_target" style="display: none;"></iframe>
		<form method="post" id="adminForm" action="">
			<div class="row" style="margin-left: 5px;">
				<div>
<!-- 					<label>页面</label> -->
					<select name="page" style="height: 50px;width: 30%;font-size: 18px;">
					</select>
					<input value="保存"  type="button" class="btn btn-success save" style="height:50px;width: 15%;font-size: 18px;"/>
				</div>
			</div>
			<hr/>
			<div class="row">
				<div id="editor" style="padding-top: 10px;">
				</div>
			</div>
	<%@include file="../common/footer.jsp" %>
	</form>
	
	
</div>
</body>
<script>
$(document).ready(function() {
	
	$('#exec_target').load(function(){
		var self = $(this);
		var img = self.contents().find('body').text().trim();
// 		img = "${basePath}/"+img;
		$('#editor').summernote('editor.insertImage', img);
	});
	
	$('#fileUp').change(function(){
		$('#formFile').submit();
	});
	
// 	$(".uploadImages").bind('click',function(){
// 		$('#fileUp').click();
// 	});
	
     $('#editor').summernote({
		height: 500,
		width:'100%',
		lang:'zh-CN',
		onImageUpload: function(files, editor, $editable) {
// 			console.log('image upload:', files, editor, $editable);
		},
		onInit:function(){
			$('.note-link-dialog').css({margin:0,left:'25%',width:'50%',height:'40%',top:'10%'});
			$('[data-event=showImageDialog]').addClass('uploadImages').attr('data-event','').bind('click',function(){
				$('#fileUp').click();
			});
			$('.uploadImages').bind('click',function(){
					var c = $('#editor').summernote().code();
// 					$('#editor').summernote('editor.insertImage', "http://avatar.csdn.net/3/3/1/1_xmt1139057136.jpg");
			});
		},
		onChange:function(){
			var opt = $($('[name=page]')[0].options[$('[name=page]')[0].selectedIndex]);
			opt.attr('code',$('#editor').summernote().code());
		}
		
	});
     
     $('.save').click(function(){
    	 var code = $('#editor').summernote().code();
    	 var id = $('[name=page]').val();
    	 ajax({id:id,code:code}, basePath+"/admin/updatestaticinfo",function(data){
    		 if(data>=1){
 				layer.msg('操作成功');
 			}else{
 				layer.msg('操作失败');
 			}
    	 });
     });
     
     post(basePath+"/admin/staticinfos",function(json){
    	 $.each(json,function(i,v){
    		 var content = v.showcontent == undefined ? "" : v.showcontent;
    		 if(i==0){
    			 $('#editor').summernote().code(content);
    		 }
    		 var opt = $("<option></option>");
    		 opt.val(v.id);
    		 opt.attr('code',content);
    		 opt.text(v.showtitle);
	    	 $('[name=page]').append(opt);
    	 })
     });
     
     $('[name=page]').change(function(){
    	 var opt = $(this.options[this.selectedIndex]);
    	 $('#editor').summernote().code(opt.attr('code'))
     });
     
	
   });
</script>
<script type="text/javascript">
$(function(e){
	window.ishide = 1;
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
// 			console.debug(data);
			if(data>=1){
				layer.msg('操作成功');
				location.href = basePath+"/admin/reslist"
			}else if(data==-100){
				layer.msg('该记录已经存在');
			}else{
				layer.msg('操作失败');
			}
		}
	});
	
});
</script>
</html>
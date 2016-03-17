$(function() {
	var tabIndex = parseInt(location.hash.split('#')[1]);
	if(!isNaN(tabIndex) && $('.nav-tabs li').length>tabIndex){
		$('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
		$('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
	}
	
	initOrganForm();
	initMap();
	
	$("#organPicUploadForm").iframePostForm({
	    iframeID: 'iframe-post-form',
	    json : true,
	    post: function(){},
	    complete: function(data){
	    	if(data.status=="y"){
	    		var organId = $('input[name="organid"]').val();
	    		$.ajax({
	    			type : 'POST',
	    			url : basePath+'/organPicAdd.do',
	    			data : {url:data.url,organid:organId},
	    			dataType : "json",
	    			success : function(data){
	    				if(data.status=='y'){
	    					location.href=location.href.split('#')[0]+'#4';
							location.reload();
	    				}else{
	    					if(typeof(data.info) == 'string' && data.info.length > 0){
	    						layer.msg(data.info);
	    					}else{
	    						layer.msg('保存失败');
	    					}
	    				}
	    			}
	    		});
	    	}else{
				if(typeof(data.info) == 'string' && data.info.length > 0){
					layer.msg(data.info);
				}else{
					layer.msg('保存失败');
				}
			}
	    }
	});
	$("input[type=file]").change(function(){
		$(this).parents(".uploader").find(".filename").val($(this).val());
		$(this).parents('form').submit();
	});
});

function initOrganForm(){
	window.organForm = $("#organForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
			if(flag == 'add'){
				$("#organForm").attr('action',basePath+'/organAdd.do');
			}else{
				$("#organForm").attr('action',basePath+'/organEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				location.href = basePath + '/organList.do';
			}else{
				if(typeof(data.info) == 'string' && data.info.length > 0){
					layer.msg(data.info);
				}else{
					layer.msg('保存失败');
				}
			}
		}
	});
	
	$("#bannerurl").uploadify({  
        height : 27,   
        width : 80,    
        buttonText : '选择图片',  
        swf : basePath+'/common/js/uploadify.swf',  
        uploader : basePath+'/imageFileUpload.do',  
        auto : true,  
        multi : false,
        fileTypeExts : '*.jpg;*.jpeg;*.png;*.gif;*.bmp',  
        fileTypeDesc : '仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB',
        fileSizeLimit : '5MB',
        uploadLimit : 1,
        onUploadSuccess : function(file,data,response){
        	var uploadLimit = $("#bannerurl").data('uploadify').settings.uploadLimit;
        	$('#bannerurl').uploadify('settings','uploadLimit', ++uploadLimit);
        	if(response==true){
        		data = $.parseJSON(data);
        		if(data.status == 'y'){
        			$('#bannerurlPreview').html('<img style="height:150px;" src="'+data.url+'">');
        			$('input[name="bannerurl"]').val(data.url);
        			return;
        		}
        	}
        	layer.msg('上传失败');
        },
        onUploadError : function(file,data,response){
        	var uploadLimit = $("#bannerurl").data('uploadify').settings.uploadLimit;
        	$('#bannerurl').uploadify('settings','uploadLimit', ++uploadLimit);
        	layer.msg('上传失败');
        }
    });  
	
	$("#mainurl").uploadify({  
        height : 27,   
        width : 80,    
        buttonText : '选择图片',  
        swf : basePath+'/common/js/uploadify.swf',  
        uploader : basePath+'/imageFileUpload.do',  
        auto : true,  
        multi : false,
        fileTypeExts : '*.jpg;*.jpeg;*.png;*.gif;*.bmp',  
        fileTypeDesc : '仅支持格式JPG/JPEG/PNG/GIF/BMP,最大不超过5MB',
        fileSizeLimit : '5MB',
        uploadLimit : 1,
        onUploadSuccess : function(file,data,response){
        	var uploadLimit = $("#mainurl").data('uploadify').settings.uploadLimit;
        	$('#mainurl').uploadify('settings','uploadLimit', ++uploadLimit);
        	if(response==true){
        		data = $.parseJSON(data);
        		if(data.status == 'y'){
        			$('#mainurlPreview').html('<img style="height:150px;" src="'+data.url+'">');
        			$('input[name="mainurl"]').val(data.url);
        			return;
        		}
        	}
        	layer.msg('上传失败');
        },
        onUploadError : function(file,data,response){
        	var uploadLimit = $("#mainurl").data('uploadify').settings.uploadLimit;
        	$('#mainurl').uploadify('settings','uploadLimit', ++uploadLimit);
        	layer.msg('上传失败');
        }
    });  
	
}

function initMap(){
	var marker = null;
	var map = new BMap.Map("allmap"); 
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 18);
	map.addControl(new BMap.NavigationControl());
	map.enableScrollWheelZoom();
	map.enableContinuousZoom();
	map.addEventListener("click",function(e){
		$('input[name="lng"]').val(e.point.lng);
		$('input[name="lat"]').val(e.point.lat);
		map.removeOverlay(marker);
		var newpoint = new BMap.Point(e.point.lng, e.point.lat);
		marker = new BMap.Marker(newpoint);
		map.addOverlay(marker);
	});
}

function showMap(){
	if($('#mapContainer:visible').length > 0){
		$('#mapContainer').hide();
		$('#mapBtn').text('显示地图');
	}else{
		$('#mapContainer').show();
		$('#mapBtn').text('隐藏地图');
	}
}

function chooseProvince() {
	var pid = $('#provinceSelector').find("option:selected").val();
	if (typeof (pid) == 'string' && pid.length > 0) {
		$.ajax({
			type : 'POST',
			url : basePath+'/cityList.do',
			data : {pid:pid},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					var optionList = [];
					optionList.push('<option value="">--请选择城市--</option>');
					for(var i = 0;i < data.cityList.length;i++){
						optionList.push('<option value="'+data.cityList[i].id+'" >'+data.cityList[i].areaname+'</option>');
					}
					$('#citySelector').html(optionList.join(''));
					$('#districtSelector').html('<option value="">--请选择区县--</option>');
					$('#blockAreaSelector').html('<option value="">--请选择片区--</option>');
				}
			}
		});
	}
}

function chooseCity(){
	var cid = $('#citySelector').find("option:selected").val();
	if (typeof (cid) == 'string' && cid.length > 0) {
		$.ajax({
			type : 'POST',
			url : basePath+'/districtList.do',
			data : {cid:cid},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					var optionList = [];
					optionList.push('<option value="">--请选择区县--</option>');
					for(var i = 0;i < data.districtList.length;i++){
						optionList.push('<option value="'+data.districtList[i].id+'" >'+data.districtList[i].areaname+'</option>');
					}
					$('#districtSelector').html(optionList.join(''));
					$('#blockAreaSelector').html('<option value="">--请选择片区--</option>');
				}
			}
		});
	}
}

function chooseDistrict(){
	var did = $('#districtSelector').find("option:selected").val();
	if (typeof (did) == 'string' && did.length > 0) {
		$.ajax({
			type : 'POST',
			url : basePath+'/blockAreaList.do',
			data : {did:did},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					var optionList = [];
					optionList.push('<option value="">--请选择片区--</option>');
					for(var i = 0;i < data.blockAreaList.length;i++){
						optionList.push('<option value="'+data.blockAreaList[i].id+'" >'+data.blockAreaList[i].bname+'</option>');
					}
					$('#blockAreaSelector').html(optionList.join(''));
				}
			}
		});
	}
}

function chooseBlockArea(){
	var $iframe = null;
	layer.open({
	    type: 2,
	    title: '选取片区',
	    btn: ['确定', '取消'],
	    shadeClose: true,
	    shade: 0.8,
	    offset: '150px',
	    area: ['800px', 'auto'],
	    content: basePath+'/selectBlockArea.do?flag=organ',
	    success: function(layero, index){
	    	$iframe = layero.find('iframe');
	    },
	    yes : function(){
	    	var sendFeeReg = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	    	var sendFeeFlag = true;
	    	var blockAreaIdList = [];
	    	var sendFeeList = [];
	    	$iframe.contents().find('.check_single:checked').each(function(){
	    		var sendFee = $(this).parents('tr').find('input[name="sendFee"]').val();
	    		if(typeof(sendFee) == 'string' && sendFeeReg.test(sendFee)){
	    			blockAreaIdList.push($(this).val());
	    			sendFeeList.push(sendFee);
	    		}else{
	    			sendFeeFlag = false;
	    			layer.msg('配送费为空或格式错误');
	    			return false;
	    		}
	    	});
	    	if(sendFeeFlag && blockAreaIdList.length > 0 && sendFeeList.length > 0){
	    		var organId = $('input[name="organid"]').val();
	    		$.ajax({
					type : 'POST',
					url : basePath+'/bindBlockAreaToOrgan.do',
					data : {organId:organId,blockAreaIdList:blockAreaIdList.join(','),sendFeeList:sendFeeList.join(',')},
					dataType : "json",
					success : function(data){
						if(data.status=='y'){
							location.href=location.href.split('#')[0]+'#1';
							location.reload();
						}else{
							if(typeof(data.info) == 'string' && data.info.length > 0){
								layer.msg(data.info);
							}else{
								layer.msg('操作失败');
							}
						}
					},
					error : function(){
						layer.msg('操作失败');
					}
				});
	    	}
	    }
	}); 
}

function delBlockArea(id){
	if(typeof(id) == 'number' && id > 0){
		var organid = $('input[name="organid"]').val();
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/unbindBlockAreaToOrgan.do',
				data : {organid:organid,id:id},
				dataType : "json",
				success : function(data){
					if(data.status=='y'){
						location.href=location.href.split('#')[0]+'#1';
						location.reload();
					}else{
						if(typeof(data.info) == 'string' && data.info.length > 0){
							layer.msg(data.info);
						}else{
							layer.msg('操作失败');
						}
					}
				},
				error : function(){
					layer.msg('操作失败');
				}
			});
		}, function(){});
	}
}

function editBlockArea(id,sendFee){
	if(typeof(id) == 'number' && id > 0 && typeof(sendFee) == 'string' && sendFee.length > 0){
		var organid = $('input[name="organid"]').val();
		layer.prompt({
			value : sendFee,
			title : '请输入配送费'
		},function(value, index, elem){
			var sendFeeReg = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
			if(typeof(value) == 'string' && sendFeeReg.test(value)){
				$.ajax({
					type : 'POST',
					url : basePath+'/editBlockAreaToOrgan.do',
					data : {organid:organid,id:id,sendfee:value},
					dataType : "json",
					success : function(data){
						if(data.status=='y'){
							location.href=location.href.split('#')[0]+'#1';
							location.reload();
						}else{
							if(typeof(data.info) == 'string' && data.info.length > 0){
								layer.msg(data.info);
							}else{
								layer.msg('操作失败');
							}
						}
					},
					error : function(){
						layer.msg('操作失败');
					}
				});
			}else{
				layer.msg('配送费为空或格式错误');
			}
		})
	}
}

function delProductCategory(pcid){
	if(typeof(pcid) == 'number' && pcid > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/productCategoryDel.do',
				data : {pcid:pcid},
				dataType : "json",
				success : function(data){
					if(data.status=='y'){
						location.href=location.href.split('#')[0]+'#2';
						location.reload();
					}else{
						if(typeof(data.info) == 'string' && data.info.length > 0){
							layer.msg(data.info);
						}else{
							layer.msg('操作失败');
						}
					}
				},
				error : function(){
					layer.msg('操作失败');
				}
			});
		}, function(){});
	}
}

function delProduct(pid){
	if(typeof(pid) == 'number' && pid > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/productDel.do',
				data : {pid:pid},
				dataType : "json",
				success : function(data){
					if(data.status=='y'){
						location.href=location.href.split('#')[0]+'#3';
						location.reload();
					}else{
						if(typeof(data.info) == 'string' && data.info.length > 0){
							layer.msg(data.info);
						}else{
							layer.msg('操作失败');
						}
					}
				},
				error : function(){
					layer.msg('操作失败');
				}
			});
		}, function(){});
	}
}

function delOrganPic(id){
	if(typeof(id) == 'number' && id > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/organPicDel.do',
				data : {id:id},
				dataType : "json",
				success : function(data){
					if(data.status=='y'){
						location.href=location.href.split('#')[0]+'#4';
						location.reload();
					}else{
						if(typeof(data.info) == 'string' && data.info.length > 0){
							layer.msg(data.info);
						}else{
							layer.msg('操作失败');
						}
					}
				},
				error : function(){
					layer.msg('操作失败');
				}
			});
		}, function(){});
	}
}
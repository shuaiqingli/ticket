$(function() {
	var tabIndex = parseInt(location.hash.split('#')[1]);
	if(!isNaN(tabIndex) && $('.nav-tabs li').length>tabIndex){
		$('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
		$('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
	}
	
	initProductForm();
	initProductChildrenItemForm();
});

function initProductForm(){
	window.productForm = $("#productForm").Validform({
		tiptype:4, 
		ignoreHidden:true,
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
			var weekdays = '';
			$('#weekdayList').find('input:checkbox').each(function(){
				if($(this).is(':checked')){
					weekdays += '1';
				}else{
					weekdays += '0';
				}
			});
			$('input[name="weekdays"]').val(weekdays);
		},
		beforeSubmit:function(curform){
			if(flag == 'add'){
				$("#productForm").attr('action',basePath+'/productAdd.do');
			}else{
				$("#productForm").attr('action',basePath+'/productEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				var organid = $('input[name="organ.organid"]').val();
				location.href = basePath + '/organEditPage.do?organid='+organid+'#3';
			}else{
				if(typeof(data.info) == 'string' && data.info.length > 0){
					layer.msg(data.info);
				}else{
					layer.msg('保存失败');
				}
			}
		}
	});
	
	var start = {
		elem : '#discountbegin',
		format : 'YYYY-MM-DD',
		istime : false,
		istoday : false,
		festival : true,
		choose : function(dates){
			end.min = dates;
			productForm.check(false,'#discountbegin');
		}
	};
	var end = {
		elem : '#discountend',
		format : 'YYYY-MM-DD',
		istime : false,
		istoday : false,
		festival : true,
		choose : function(dates){
			start.max = dates;
			productForm.check(false,'#discountend');
		}
	};
	laydate(start);
	laydate(end);
	
	$("#pictureurl").uploadify({  
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
        	var uploadLimit = $("#pictureurl").data('uploadify').settings.uploadLimit;
        	$('#pictureurl').uploadify('settings','uploadLimit', ++uploadLimit);
        	if(response==true){
        		data = $.parseJSON(data);
        		if(data.status == 'y'){
        			$('#pictureurlPreview').html('<img style="height:150px;" src="'+data.url+'">');
        			$('input[name="pictureurl"]').val(data.url);
        			return;
        		}
        	}
        	layer.msg('上传失败');
        },
        onUploadError : function(file,data,response){
        	var uploadLimit = $("#pictureurl").data('uploadify').settings.uploadLimit;
        	$('#pictureurl').uploadify('settings','uploadLimit', ++uploadLimit);
        	layer.msg('上传失败');
        }
    });
}

function initProductChildrenItemForm(){
	window.productChildrenItemForm = $("#productChildrenItemForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
		},
		callback:function(data){
			if(data.status=="y"){
				location.href=location.href.split('#')[0]+'#2';
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
	$("#productChildrenItemForm").find('input[type="reset"]').click(function(){
		productChildrenItemForm.resetForm();
		layer.closeAll();
	});
}

function showPromotion(){
	var enablePromotion = $('#enablePromotion').is(':checked');
	if(enablePromotion){
		$('input[name="saleprice"]').parents('div.row').show();
		$('input[name="discountbegin"]').parents('div.row').show();
		$('input[name="discountend"]').parents('div.row').show();
		$('#weekdayList').parents('div.row').show();
	}else{
		$('input[name="saleprice"]').parents('div.row').hide();
		$('input[name="saleprice"]').val('');
		$('input[name="discountbegin"]').parents('div.row').hide();
		$('input[name="discountbegin"]').val('');
		$('input[name="discountend"]').parents('div.row').hide();
		$('input[name="discountend"]').val('');
		$('#weekdayList').parents('div.row').hide();
	}
}

function showStock(){
	var stockflag = $('select[name="stockflag"]').find('option:selected').val();
	if(typeof(stockflag) == 'string' && stockflag == '1'){
		$('input[name="fixedstock"]').parents('div.row').show();
	}else{
		$('input[name="fixedstock"]').parents('div.row').hide();
	}
}

function delProductChildren(id){
	if(typeof(id) == 'number' && id > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/productChildrenDel.do',
				data : {id:id},
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

function delProductChildrenItem(itemid){
	if(typeof(itemid) == 'number' && itemid > 0){
		layer.confirm('确认删除此记录?', {offset: '300px'}, function(){
			$.ajax({
				type : 'POST',
				url : basePath+'/productChildrenItemDel.do',
				data : {itemid:itemid},
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

function editProductChildrenItem(itemid){
	if(typeof(itemid) == 'number' && itemid > 0){
		$.ajax({
			type : 'POST',
			url : basePath+'/productChildrenItemGet.do',
			data : {itemid:itemid},
			dataType : "json",
			success : function(data){
				if(data.status=='y'){
					$('#productChildrenItemEditDialog').find('input[name="itemid"]').val(data.productChildrenItem.itemid);
					$('#productChildrenItemEditDialog').find('input[name="cprice"]').val(parseFloat(data.productChildrenItem.cprice).toFixed(2));
					if(data.productChildrenItem.isenabled == 1){
						$('#productChildrenItemEditDialog').find('input[name="isenabled"]').attr('checked','checked');
					}else{
						$('#productChildrenItemEditDialog').find('input[name="isenabled"]').removeAttr('checked');
					}
					if(data.productChildrenItem.isvisible == 1){
						$('#productChildrenItemEditDialog').find('input[name="isvisible"]').attr('checked','checked');
					}else{
						$('#productChildrenItemEditDialog').find('input[name="isvisible"]').removeAttr('checked');
					}
					layer.open({
					    type: 1,
					    title: '编辑餐品',
					    shadeClose: true,
					    shade: 0.8,
					    area: ['400px', 'auto'],
					    content: $('#productChildrenItemEditDialog'),
					    yes : function(){
					    }
					});
				}else{
					if(typeof(data.info) == 'string' && data.info.length > 0){
						layer.msg(data.info);
					}else{
						layer.msg('获取数据失败');
					}
				}
			},
			error : function(){
				layer.msg('获取数据失败');
			}
		});
	}
}
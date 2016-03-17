$(function() {
	var tabIndex = parseInt(location.hash.split('#')[1]);
	if(!isNaN(tabIndex) && $('.nav-tabs li').length>tabIndex){
		$('.nav-tabs li').eq(tabIndex).addClass("active").siblings().removeClass("active");
		$('.tab-content .tab-pane').eq(tabIndex).addClass("active").siblings().removeClass("active");
	}
	
	initProductChildrenForm();
	initProductChildrenItemForm();
});

function initProductChildrenForm(){
	window.productChildrenForm = $("#productChildrenForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
			if(flag == 'add'){
				$("#productChildrenForm").attr('action',basePath+'/productChildrenAdd.do');
			}else{
				$("#productChildrenForm").attr('action',basePath+'/productChildrenEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				var organid = $('input[name="organid"]').val();
				var pid = $('input[name="product.pid"]').val();
				location.href = basePath + '/productEditPage.do?organid='+organid+'&pid='+pid+'#1';
			}else{
				if(typeof(data.info) == 'string' && data.info.length > 0){
					layer.msg(data.info);
				}else{
					layer.msg('保存失败');
				}
			}
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
				location.href=location.href.split('#')[0]+'#1';
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

function chooseProduct(pid){
	if(typeof(pid) == 'number' && pid > 0){
		var $iframe = null;
		layer.open({
		    type: 2,
		    title: '选取餐品',
		    btn: ['确定', '取消'],
		    shadeClose: true,
		    shade: 0.8,
		    offset: '150px',
		    area: ['800px', 'auto'],
		    content: basePath+'/selectProduct.do?pid='+pid,
		    success: function(layero, index){
		    	$iframe = layero.find('iframe');
		    },
		    yes : function(){
		    	var priceReg = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		    	var priceFlag = true;
		    	var productIdList = [];
		    	var priceList = [];
		    	var isenabledList = [];
		    	var isvisibleList = [];
		    	$iframe.contents().find('.check_single:checked').each(function(){
		    		var price = $(this).parents('tr').find('input[name="cprice"]').val();
		    		if(typeof(price) == 'string' && priceReg.test(price)){
		    			productIdList.push($(this).val());
		    			priceList.push(price);
		    			if($(this).parents('tr').find('input[name="isenabled"]').is(':checked')){
		    				isenabledList.push(1);
		    			}else{
		    				isenabledList.push(0);
		    			}
		    			if($(this).parents('tr').find('input[name="isvisible"]').is(':checked')){
		    				isvisibleList.push(1);
		    			}else{
		    				isvisibleList.push(0);
		    			}
		    		}else{
		    			priceFlag = false;
		    			layer.msg('优惠价格为空或格式错误');
		    			return false;
		    		}
		    	});
		    	if(priceFlag && productIdList.length > 0 && priceList.length > 0 && isenabledList.length > 0 && isvisibleList.length > 0){
		    		var childrenid = $('input[name="id"]').val();
		    		$.ajax({
						type : 'POST',
						url : basePath+'/productChildrenItemAdd.do',
						data : {childrenid:childrenid,productIdList:productIdList.join(','),priceList:priceList.join(','),isenabledList:isenabledList.join(','),isvisibleList:isvisibleList.join(',')},
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
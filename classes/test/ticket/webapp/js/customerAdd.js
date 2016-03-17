$(function() {
	$('#wanna_restaurants').keyup(function(){
		$('#wanna_restaurant_list').html('');
		run_restaurant_query();
	});
	
	$('#w_restaurants').keyup(function(){
		$('#w_restaurant_list').html('');
		w_restaurants_query();
	});
	
	$('#w_products').keyup(function(){
		$('#w_products_list').html('');
		w_products_query();
	});
	
	initCustomerForm();
});

function initCustomerForm(){
	window.customerForm = $("#customerForm").Validform({
		tiptype:4, 
		postonce:true,
		ajaxPost:true,
		beforeCheck:function(curform){
		},
		beforeSubmit:function(curform){
			if(!$('input[name="password"]').attr('isEncrypt')){
				var hash = hex_md5($('input[name="password"]').val());
				$('input[name="password"]').attr('isEncrypt',true);
				$('input[name="password"]').val(hash);
				$('input[name="password2"]').val(hash);
			}
			if(flag == 'add'){
				$("#customerForm").attr('action',basePath+'/customerAdd.do');
			}else{
				$("#customerForm").attr('action',basePath+'/customerEdit.do');
			}
		},
		callback:function(data){
			if(data.status=="y"){
				//layer.msg('保存成功');
				location.href = basePath + '/customerList.do';
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

function remove_wanna_restaurant(restaurant_id) {
	$
			.post(
					'http://192.168.4.178:8081/admin/fwd_controller/remove_wanna_restaurant/',
					{
						restaurant_id : restaurant_id,
						customer_id : $("#customer_id").val()
					}, function(a) {
						if (a != 0) {
							$("#wanna_restaurant_" + restaurant_id).remove();
						}
					});
}

function remove_wanna_product(restaurant_id, category_id, product_id) {
	$
			.post(
					"http://192.168.4.178:8081/admin/fwd_controller/remove_wanna_product",
					{
						customer_id : $("#customer_id").val(),
						restaurant_id : restaurant_id,
						category_id : category_id,
						product_id : product_id
					}, function(a) {
						if (a != 0) {
							$("#wanna_product_" + product_id).remove();
						}
					});
}

function run_restaurant_query()
{
	$.post("http://192.168.4.178:8081/admin/restaurants/restaurant_autocomplete/", { name: $('#wanna_restaurants').val(), limit:10},
		function(data) {
	
			$('#wanna_restaurant_list').html('');
	
			$.each(data, function(index, value){
				$('#wanna_restaurant_list').append('<option value="'+index+'">'+value+'</option>');
			});
	
	}, 'json');
}

function add_wanna_restaurant()
{
	var restaurant_id = $("#wanna_restaurant_list").find("option:selected").val();
	var restaurant_name = $("#wanna_restaurant_list").find("option:selected").text();
	if(restaurant_id != undefined)
	{
		$.post(
			'http://192.168.4.178:8081/admin/fwd_controller/save_wanna_restaurant/',
			{
				customer_id : $("#customer_id").val() ,
				restaurant_id : restaurant_id
			},
			function (data)
			{
				if(data != 0)
				{
					//location.reload();
					//alert(restaurant_name+restaurant_id);
					$("#wanna_restaurant_empty").empty();
					$("#wanna_restaurants_table").append("<tr id='wanna_restaurant_"+restaurant_id+"'><td>&nbsp;</td><td>"+restaurant_name+"</td><td><a onclick='remove_wanna_restaurant("+restaurant_id+")' href='#' class='btn btn-danger btn-small'><b class='icon-trash'></b>删除</a></td></tr>");
				}
			}
			);
	}
	else
	{
		alert("请选择一个餐馆");
	}
}

function w_restaurants_query()
{
	$.post("http://192.168.4.178:8081/admin/restaurants/restaurant_autocomplete/", { name: $('#w_restaurants').val(), limit:10},
		function(data) {
			$('#w_restaurant_list').html('');
			$.each(data, function(index, value){
				$('#w_restaurant_list').append('<option value="'+index+'">'+value+'</option>');
			});
	
	}, 'json');
}

function choose_wanna_restaurant()
{
	var restaurant_id = $("#w_restaurant_list").find("option:selected").val();
	var restaurant_name = $("#w_restaurant_list").find("option:selected").text();
	if(restaurant_id != undefined)
	{
		$("#choose_w_restaurant").val(restaurant_id);
	}
	else
	{
		alert("请选择一个餐馆");
	}
}

function w_products_query()
{
	if( $("#choose_w_restaurant").val())
	{
		$.post(
		"http://192.168.4.178:8081/admin/fwd_controller/products_autocomplete/", 
		{ 
			name: $('#w_products').val(), 
			restaurant_id: $("#choose_w_restaurant").val()
		},
		function(data) 
		{
			if(data != '')
			{
				$('#w_products_list').html('');
				$.each(data, function(index, value){
					$('#w_products_list').append('<option value="'+value.id+","+value.restaurant_id+","+value.restaurant_category_id+'">'+value.name+'</option>');
				});
			}
			else
			{
				$('#w_products_list').append('<option>没有餐品</option>');
			}
		}, 'json');
	}
	else
	{
		alert("请选择一个餐馆");
	}
	
}

function add_wanna_product()
{
	var v = $("#w_products_list").find("option:selected").val().split(",");
	$.post
	(
		"http://192.168.4.178:8081/admin/fwd_controller/save_wanna_product/" , 
		{
			customer_id : $("#customer_id").val() ,
			product_id : v[0] , 
			restaurant_id : v[1] ,
			category_id : v[2]
		},
		function (data)
		{
			if(data && data != 0)
			{
				$("#wanna_product_empty").empty();
				$("#wanna_products_table").append("<tr id='wanna_product_"+v[0]+"'><td>"+$("#w_products_list").find("option:selected").text()+"</td><td>"+$("#w_restaurant_list").find("option:selected").text()+"</td><td><a href='#' class='btn btn-small btn-danger' onclick='remove_wanna_product("+v[1]+","+v[2]+","+v[0]+")'><b class='icon-trash'></b>删除</a></td></tr>");
			}
			else
			{
				alert("餐品已添加或者不在目录中");
			}
		}
	);
}
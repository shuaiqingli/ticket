$(function(){
	post("web/api/customer/info", function(json){
		var customer = json.datas[0];
		$('.username').text(customer.cname);
		$('.mobile').text(customer.mobile);
	});
});
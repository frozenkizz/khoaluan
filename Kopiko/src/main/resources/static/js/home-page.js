$(document).ready(function() {
	$.ajax({
		url: '/payment/v1/api/home',
		type: 'GET',
		dataType: 'JSON',
		success: function(responseModel) {
			console.log(responseModel);
		}
	})
})
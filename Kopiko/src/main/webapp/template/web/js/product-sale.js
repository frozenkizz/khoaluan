$(document).ready(function() {
 
	var dataSend = {
		productName : $().val()
	};
	$.ajax({
		url: '/api/v1/search/sale',
		type: 'GET',
		data : dataSend,
		dataType: 'json',
		success: function(data) {
			var result = "";
			console.log(data);
			$('#productSale').empty();
			data.forEach(item => {
				console.log(item.productName)
				result = `
					<div class="col-6 col-lg-3 col-md-4 category-wrap">
						<div class="category-box">
							<img src="/uploads/images/${item.imageUrl}" alt="#" width="100%"
								class="img img-responsive">
							<p class="category-name">${item.productName}</p>
							<div
								class="card-text text-left font-weight-bold d-flex justify-content-center">
								<span class="product__price product__price-old">${currencyFormat(item.price)}đ</span> 
								<span class="product__price product__price-new">${currencyFormat(item.salePrice)}đ</span>
							</div>
							<a href="/product/${ item.productId }" class="btn  margin__btn-add-cart m-auto">Xem sản phẩm</a>
						</div>
					</div>
					`;
					$('#productSale').append(result);
			});
		}
	})
	
	function currencyFormat(price) {
	return price.toLocaleString('vi-VN', { useGrouping: true });
}
 
 
	/*//var dataSend = JSON.stringify({ productName : 123});
	var dataSend = {
		productName : $().val()
	};
	$.ajax({
		url: '/api/v1/search/sale',
		type: 'GET',
		data : dataSend,
		dataType: 'json',
		// contentType: 'text/plain',
		success: function(data) {
			var result = "";
			console.log(data);
			$('#productSale').empty();
			data.forEach(item => {
				result = `
					<div class="col-6 col-lg-3 col-md-4  ">
						<div class="category-box">
							<img src="<c:url value='/img/category2.jpg'/>" alt="#" width="100%"
								class="img img-responsive">
							<p class="category-name">${item.productName}</p>
							<div
								class="card-text text-left font-weight-bold d-flex justify-content-center">
								<span class="product__price product__price-old">${item.price}</span>
								<span class="product__price product__price-new">${item.salePrice}</span>
							</div>
							<a href="" class="btn  margin__btn-add-cart m-auto">Thêm vào
								Giỏ</a>
						</div>
					</div>
					`;
					$('#productSale').append(result);
			});
		}
	})*/
})
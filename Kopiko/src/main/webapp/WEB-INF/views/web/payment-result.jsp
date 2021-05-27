<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/common/taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value='/template/web/css/PagingOrderDetail.css'/>" />

<style>
.container {
	font-size: 1.5rem !important;
}
</style>
<!-- page chi tiet hoa don -->
<div class="container-fluid bg-light border-bottom py-5 mb-5">
	<div class="container position-relative " style="height: 44px;">
		<div class="d-flex align-items-center py-4 h-100 position-absolute">
			<div class="nav-item nav-item-home">
				<a href="" class="nav-link buy-infor-header">Trang chủ</a>
			</div>
			<div class="item__pagination"></div>
			<div class="nav-item nav-item-infomation buy-infor-header">Đơn
				hàng của tôi</div>
		</div>
	</div>

	<div class="container mt-2">
		<h5 class="mb-4" align="center">Kết Quả Giao Dịch</h5>

		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th scope="col">Tên</th>
						<th scope="col">Giá trị</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Mã giao dịch</td>
						<td><label>${Invoice.vnp_TransactionNo}</label></td>
					</tr>
					<tr>
						<td>Đơn đặt hàng</td>
						<td><a href="/order-detail/${Invoice.vnp_TxnRef}">${invoice.vnp_TxnRef}</a></td>
					</tr>
					<tr>
						<td>Số tiền</td>
						<td><label>${Invoice.vnp_Amount}</label></td>
					</tr>
					<tr>
						<td>Nội dung thanh toán</td>
						<td><label>${Invoice.vnp_OrderInfo}</label></td>
					</tr>
					<tr>
						<td>Mã GD Tại VNPAY</td>
						<td><label>${Invoice.vnp_TransactionNo}</label></td>
					</tr>
					<tr>
						<td>Mã Ngân hàng</td>
						<td><label>${Invoice.vnp_BankCode}</label></td>
					</tr>
					<tr>
						<td>Kết quả</td>
						<td><label>${Invoice.result}</label></td>
					</tr>

				</tbody>
			</table>

		</div>
		<div class="d-flex">
			<a class="btn btn-primary m-auto mt-4" href="/home">Về trang chủ</a>
		</div>

	</div>
</div>
<!-- end page chi tiet hoa don -->

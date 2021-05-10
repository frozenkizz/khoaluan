var table;
var url = '/admin/payment/findAll ';
function initTableData() {
	/** Data from an URL */
	$.get(url, function(responseData) {
		/*alert(`data=${JSON.stringify(responseData)}`)*/
		table = $('#dataTable').DataTable({
			orderCellsTop: true,
			fixedHeader: true,
			processing: true,
			data: responseData,
			columns: [
				{ data: 'paymentMethodId' },
				{ data: 'paymentMethodName' },
				{
					render: function(data, type, row) {
						return `<div class="action-btns text-center">
								<a class="edit-btn" data-id="${row.paymentMethodId}" data-name="${row.paymentMethodName}" data-toggle="modal" data-target="#myModal">
									<i class="fas fa-edit"></i>
								</a> | 
								<a class="delete-btn" data-id="${row.paymentMethodId}" data-name="${row.paymentMethodName}" data-toggle="modal" data-target="#confirmDeleteModal">
									<i class="fas fa-trash-alt"></i>
								</a>
							</div>`
					}
				}
			],
			dom: 'Bfrtip',
			buttons: [
				{
					text: 'Add New Payment Method',
					action: function(e, dt, node, config) {
						$('.modal-title').text('Add New Payment Method');
						$('#paymentMethodId').parent().addClass("d-none");
						$('#myModal').modal('toggle')
						resetForm();
					}
				}
			]
		});
	}).fail(function() {
		alert("Cannot get data from URL");
	})
}

$(document).ready(function() {
	initTableData();

	/** Hover change background tag thead */
	$("#list-header").on({
		mouseenter: function() {
			$(this).css("background-color", "lightgray");
		},
		mouseleave: function() {
			$(this).css("background-color", "lightblue");
		}
	})

	/** Add input search header datatable */
	$('#dataTable thead tr').clone(true).appendTo('#dataTable thead');
	$('#dataTable thead tr:eq(1) th').each(function(i) {
		var title = $(this).text();
		$(this).html('<input type="text" placeholder="Search ' + title + '" />');
		$('input', this).on('keyup change', function() {
			if (table.column(i).search() !== this.value) {
				table.column(i).search(this.value).draw();
			}
		});
	});
	$('#dataTable thead tr:eq(1) th').addClass('d-none')

	/** Show modal form update payment method */
	$("#dataTable").on('click', '.edit-btn', function() {
		$('#paymentMethodId').parent().removeClass("d-none");
		$('.modal-title').text('Edit Payment Method');
		$.ajax({
			url: '/admin/payment/find/' + $(this).data("id"),
			type: 'GET',
			success: function(responseData) {
				$("#paymentMethodId").val(responseData.paymentMethodId);
				$("#paymentMethodName").val(responseData.paymentMethodName);
			}
		})
	})

	/** Submit Update Payment Method */
	$("#btnSubmitPaymentMethod").on('click', function(event) {
		event.preventDefault();
		var $paymentMethodId = $("#paymentMethodId")
		var $paymentMethodName = $("#paymentMethodName")
		var isAction = $paymentMethodId.val() == undefined || $paymentMethodId.val() == "";
		var paymentEntity = {
			paymentMethodId: $paymentMethodId.val() ? $paymentMethodId.val() : null,
			paymentMethodName: $paymentMethodName.val(),
		}
		console.log('paymentEntity: ' + JSON.stringify(paymentEntity))

		$("#paymentMethodInfoForm").validate({
			rules: {
				paymentMethodName: {
					required: true,
					minlength: 2,
					maxlength: 100,
				}
			},
			messages: {
				paymentMethodName: {
					required: "Payment method name is required",
					minlength: "Your payment method name must consist of at least 2 characters",
					maxlength: "Your payment method name must less than 100 characters"
				}
			},
			errorElement: "div",
			errorClass: "error-message-invalid"
		});
		if ($("#paymentMethodInfoForm").valid()) {
			$.ajax({
				url: '/admin/payment/' + (isAction ? "add" : "update"),
				type: 'POST',
				dataType: 'JSON',
				data: paymentEntity,
				success: function(responseData) {
					if (responseData.responseCode == 100) {
						/**Reload datatable */
						reloadDataTable();
						$('#myModal').modal('toggle');
						$('#announcemnet strong:eq(0)').removeClass("text-warning").addClass("text-success");
						$('#notification').text((isAction ? "Add New Payment Method " : "Update Payment Method ") + "Success!");
						$("#announcemnet").toast('show');
					} else {
						$('#announcemnet strong:eq(0)').removeClass("text-success").addClass("text-warning");
						$('#notification').text("The value you entered has been duplicated!");
						$("#announcemnet").toast('show');
					}
				},
				error: function(e) {
					console.log('error paymentEntity: ' + JSON.stringify(paymentEntity))
					alert('Update failed! ' + JSON.stringify(e));
				}
			})
		}

	})

	/** Show modal delete payment method */
	$("#dataTable").on('click', '.delete-btn', function() {
		$("#deletePaymentMethodName").text($(this).data("name"));
		$("#btnSubmitDelete").attr('data-id', $(this).data('id'));
	})

	/** Submit delete payment method */
	$("#btnSubmitDelete").on('click', function() {
		console.log('.data("") = ' + $(this).data('id') + ', .attr= ' + $(this).attr('data-id'));
		$.ajax({
			url: '/admin/payment/delete/' + $(this).attr('data-id'),
			type: 'DELETE',
			success: function(responseData) {
				reloadDataTable();
				$('#confirmDeleteModal').modal('toggle');
				$('#announcemnet strong:eq(0)').removeClass("text-warning").addClass("text-success");
				$('#notification').text("Delete Payment Method Success!");
				$("#announcemnet").toast('show');
				console.log(responseData + ' success: ' + $(this).data('id'))
			},
			error: function(e) {
				console.log('error: ' + $(this).data('id'))
				alert('Delete payment method failed ' + JSON.stringify(e));
			}

		})
		console.log('Payment method id after:' + $(this).data('id'))
	})
})

function reloadDataTable() {
	$('#dataTable').dataTable().fnDestroy();
	initTableData();
}

function resetForm() {
	$("#paymentMethodId").val('');
	$("#paymentMethodName").val('');
}



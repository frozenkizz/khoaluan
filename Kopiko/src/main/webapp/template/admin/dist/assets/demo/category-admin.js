var table;
var url = '/admin/category/view ';
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
				{ data: 'categoryId' },
				{ data: 'categoryCode' },
				{ data: 'categoryName' },
				{
					render: function(data, type, row) {
						return `<div class="action-btns text-center">
								<a class="edit-btn" data-id="${row.categoryId}" data-name="${row.categoryName}" data-toggle="modal" data-target="#myModal">
									<i class="fas fa-edit"></i>
								</a> | 
								<a class="delete-btn" data-id="${row.categoryId}" data-name="${row.categoryName}" data-toggle="modal" data-target="#confirmDeleteModal">
									<i class="fas fa-trash-alt"></i>
								</a>
							</div>`
					}
				}
			],
			dom: 'Bfrtip',
			buttons: [
				{
					text: 'Add New Category',
					action: function(e, dt, node, config) {
						$('.modal-title').text('Add New Category');
						$('#categoryId').parent().addClass("d-none");
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

	/** Show modal form update category */
	$("#dataTable").on('click', '.edit-btn', function() {
		$('#categoryId').parent().removeClass("d-none");
		$('.modal-title').text('Edit Category');
		$.ajax({
			url: '/admin/category/find/' + $(this).data("id"),
			type: 'GET',
			success: function(responseData) {
				$("#categoryId").val(responseData.categoryId);
				$("#categoryCode").val(responseData.categoryCode);
				$("#categoryName").val(responseData.categoryName);
			}
		})
	})

	/** Submit Update category */
	$("#btnSubmitCategory").on('click', function(event) {
		event.preventDefault();
		var $categoryId = $("#categoryId")
		var $categoryCode = $("#categoryCode")
		var $categoryName = $("#categoryName")
		var isAction = $categoryId.val() == undefined || $categoryId.val() == "";
		var categoryEntity = {
			categoryId: $categoryId.val() ? $categoryId.val() : null,
			categoryCode: $categoryCode.val(),
			categoryName: $categoryName.val(),
		}
		console.log('categoryEntity: ' + JSON.stringify(categoryEntity))
		
		$("#categoryInfoForm").validate({
			rules: {
				categoryCode: {
					required: true,
					minlength: 2,
					maxlength: 100,
				},
				categoryName: {
					required: true,
					minlength: 2,
					maxlength: 100,
				}
			},
			messages: {
				categoryCode: {
					required: "Category code  is required",
					minlength: "Your category code must consist of at least 2 characters",
					maxlength: "Your category code must less than 100 characters"
				},
				categoryName: {
					required: "Category name  is required",
					minlength: "Your category name must consist of at least 2 characters",
					maxlength: "Your category name must less than 100 characters"
				}
			},
			errorElement: "div",
			errorClass: "error-message-invalid"
		});
		if ($("#categoryInfoForm").valid()) {
			$.ajax({
				url: '/admin/category/' + (isAction ? "add" : "update"),
				type: 'POST',
				dataType: 'JSON',
				data: categoryEntity,
				success: function(responseData) {
					if (responseData.responseCode == 100) {
						/**Reload datatable */
						reloadDataTable();
						$('#myModal').modal('toggle');
						$('#announcemnet strong:eq(0)').removeClass("text-warning").addClass("text-success");
						$('#notification').text((isAction ? "Add New Category " : "Update Category ") + "Success!");
						$("#announcemnet").toast('show');
					} else {
						$('#announcemnet strong:eq(0)').removeClass("text-success").addClass("text-warning");
						$('#notification').text("The value you entered has been duplicated!");
						$("#announcemnet").toast('show');
					}
				},
				error: function(e) {
					console.log('error categoryEntity: ' + JSON.stringify(categoryEntity))
					alert('Update failed! ' + JSON.stringify(e));
				}
			})
		}

	})

	/** Show modal delete category */
	$("#dataTable").on('click', '.delete-btn', function() {
		$("#deleteCategoryName").text($(this).data("name"));
		$("#btnSubmitDelete").attr('data-id', $(this).data('id'));
	})

	/** Submit delete category */
	$("#btnSubmitDelete").on('click', function() {
		console.log('.data("") = ' + $(this).data('id') + ', .attr= ' + $(this).attr('data-id'));
		$.ajax({
			url: '/admin/category/delete/' + $(this).attr('data-id'),
			type: 'DELETE',
			success: function(responseData) {
				reloadDataTable();
				$('#confirmDeleteModal').modal('toggle');
				$('#announcemnet strong:eq(0)').removeClass("text-warning").addClass("text-success");
				$('#notification').text("Delete Category Success!");
				$("#announcemnet").toast('show');
				console.log(responseData + ' success: ' + $(this).data('id'))
			},
			error: function(e) {
				console.log('error: ' + $(this).data('id'))
				alert('Delete category failed ' + JSON.stringify(e));
			}

		})
		console.log('Category id after:' + $(this).data('id'))
	})
})

function reloadDataTable() {
	$('#dataTable').dataTable().fnDestroy();
	initTableData();
}

function resetForm() {
	$("#categoryId").val('');
	$("#categoryCode").val('');
	$("#categoryName").val('');
}



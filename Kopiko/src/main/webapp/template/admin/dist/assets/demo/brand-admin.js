var table;
var url = '/admin/brand/findAll';
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
				{ data: 'brandId' },
				{ render: function(data, type, row) {
						return `<img class="img-brand" src="/uploads/images/${row.brandImgUrl}"/>`;
					} 
				},
				{ data: 'brandName' },
				{ data: 'info' },
				{
					render: function(data, type, row) {
						return `<div class="action-btns text-center">
								<a class="edit-btn" data-id="${row.brandId}" data-name="${row.brandName}" data-toggle="modal" data-target="#myModal">
									<i class="fas fa-edit"></i>
								</a> | 
								<a class="delete-btn" data-id="${row.brandId}" data-name="${row.brandName}" data-toggle="modal" data-target="#confirmDeleteModal">
									<i class="fas fa-trash-alt"></i>
								</a>
							</div>`
					}
				}
			],
			dom: 'Bfrtip',
			lengthMenu: [
			[ 10, 25, 50, -1 ],
			[ '10 Filas', '25 Filas', '50 Filas', 'Mostrar todo' ]
			],
			buttons: [
				{
					text: 'Add New Brand',
					action: function(e, dt, node, config) {
						resetFormModal($('#brandInfoForm'));
						$('.modal-title').text('Add New Brand');
						$('#brandId').parent().addClass("d-none");
						$('#myModal').modal('toggle')
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

	/** Show modal form update brand */
	$("#dataTable").on('click', '.edit-btn', function() {
		$('#brandId').parent().removeClass("d-none");
		$('.modal-title').text('Edit Brand');
		$.ajax({
			url: '/admin/brand/find/' + $(this).data('id'),
			type: 'GET',
			success: function(responseData) {
				resetFormModal($('#brandInfoForm'));
				console.log('responseData: ' + JSON.stringify(responseData))
				$("#brandId").val(responseData.brandId);
				$("#brandName").val(responseData.brandName);
				$("#info").val(responseData.info);
				$("#brandImgUrl").val(responseData.brandImgUrl);
			}
		})
	})

	/** Submit Update Brand */
	$("#btnSubmitBrand").on('click', function(event) {
		event.preventDefault();
		var $brandId = $("#brandId")
		var $brandImgUrl = $("#brandImgUrl")
		var $brandName = $("#brandName")
		var $imageFile = $("#imageFile")
		var $info = $("#info")
		var isAddAction = $brandId.val() == undefined || $brandId.val() == "";
		var brandEntity = {
			brandId: $brandId.val() ? $brandId.val() : null,
			brandImgUrl: $brandImgUrl.val(),
			brandName: $brandName.val(),
			imageFile: $imageFile.val(),
			info: $info.val(),
		}
		var formData = new FormData($("#brandInfoForm")[0]);
		console.log('brandEntity: ' + JSON.stringify(brandEntity))
		$("#brandInfoForm").validate({
			rules: {
				brandName: {
					required: true,
					minlength: 2,
					maxlength: 100,
				},
				imageFile: {
					required: isAddAction
				}
			},
			messages: {
				brandName: {
					required: "Brand Name  is required",
					minlength: "Your brand name must consist of at least 2 characters",
					maxlength: "Your brand name must less than 100 characters"
				},
				imageFile: {
					required: "Image brand is required",
				}
			},
			errorElement: "div",
			errorClass: "error-message-invalid"
		});
		if ($("#brandInfoForm").valid()) {
			$.ajax({
				url: '/admin/brand/' + (isAddAction ? "add" : "update"),
				type: 'POST',
				processData: false,
				contentType: false,
				enctype: 'multipart/form-data',
				data: formData,
				success: function(responseData) {
					if (responseData.responseCode == 100) {
						/**Reload datatable */
						reloadDataTable();
						$('#myModal').modal('toggle');
						$('#announcemnet strong:eq(0)').removeClass("text-warning").addClass("text-success");
						$('#notification').text((isAddAction ? "Add New Brand " : "Update Brand ") + "Success!");
						$("#announcemnet").toast('show');
					} else if(responseData.responseCode == 1) {
						$('#announcemnet strong:eq(0)').removeClass("text-success").addClass("text-warning");
						$('#notification').text("The value you entered has been duplicated!");
						$("#announcemnet").toast('show');
					}
				},
				error: function(e) {
					console.log('error brandEntity: ' + JSON.stringify(brandEntity))
					alert('Update failed! ' + JSON.stringify(e));
				}
			})
		}

	})

	/** Show modal delete brand */
	$("#dataTable").on('click', '.delete-btn', function() {
		$("#deleteBrandName").text($(this).data("name"));
		$("#btnSubmitDelete").attr('data-id', $(this).data('id'));
	})
	

	/** Submit delete brand */
	$("#btnSubmitDelete").on('click', function() {
		console.log('.data("") = ' + $(this).data('id') + ', .attr= ' + $(this).attr('data-id'));
		$.ajax({
			url: '/admin/brand/delete/' + $(this).attr('data-id'),
			type: 'DELETE',
			success: function(responseData) {
				reloadDataTable();
				$('#confirmDeleteModal').modal('toggle');
				$('#announcemnet strong:eq(0)').removeClass("text-warning").addClass("text-success");
				$('#notification').text("Delete Brand Success!");
				$("#announcemnet").toast('show');
				console.log(responseData + ' success: ' + $(this).data('id'))
			},
			error: function(e) {
				console.log('error: ' + $(this).data('id'))
				alert('Delete brand failed ' + JSON.stringify(e));
			}
		})
		console.log('Brand id after:' + $(this).data('id'))
	})
	
	$('#imageFile').on('change', previewImages);
})

function reloadDataTable() {
	$('#dataTable').dataTable().fnDestroy();
	initTableData();
}

function previewImages() {
	  var preview = document.querySelector('#previewonly');
	  if (this.files) {
	    [].forEach.call(this.files, readAndPreview);
	  }
	  function readAndPreview(file) {
	    if (!/\.(jpe?g|png|gif)$/i.test(file.name)) {
	      return alert(file.name + " is not an image");
	    } 
	    var reader = new FileReader();
	    reader.addEventListener("load", function() {
	      var image = new Image();
	      image.height = 100;
	      image.margin = 5;
	      image.src    = this.result;
	      preview.appendChild(image);
	    });
	    reader.readAsDataURL(file);
	  }
	}

/* Rest form add new brand*/
function resetFormModal($formElement) {
	$formElement[0].reset();
	$formElement.find("input[type*='file']").val("");
	$formElement.validate().destroy();
	$formElement.find(".error-message-invalid").removeClass('error-message-invalid');
	$formElement.find("img").attr('src', '');
	$('#previewonly img').attr('src', '');
	$('#imageFile').attr('src', '');
}

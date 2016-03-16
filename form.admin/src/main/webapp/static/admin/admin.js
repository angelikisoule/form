/**
 * Confirm Dialog For Anchors With Class .confirm-dialog and 
 * A Custom 'dialog' Attribute Containing The Confirm Question
 */
$(document).on("click", ".confirm-dialog", function(event) {
	var _this = $(this);
	var _accept = 'Delete';
	if(_this.attr('accept')!=null && _this.attr('accept')!='') _accept = _this.attr('accept');
	event.preventDefault();
	bootbox.confirm({
		title:'<strong>Confirm</strong>',
		message: _this.attr('dialog'),
		buttons: {
			'cancel': {
				label:'Cancel',
				className:'btn-default'
			},
			'confirm': {
				label:_accept,
				className:'btn-danger'
			}
		},
		callback: function(result) {
			if(result) {
				location.href=_this.attr('href');
			}
		}
	});
});
/**
 * An AJAX Call To Delete An Article/Author Relationship
 */
$(document).on("click", ".delete-author", function(event) {
	var _this = $(this);
	event.preventDefault();
	bootbox.confirm({
		title:'<strong>Confirm</strong>',
		message: 'Are You Sure You Want To Delete This Author?',
		buttons: {
			'cancel': {
				label:'Cancel',
				className:'btn-default'
			},
			'confirm': {
				label:'Delete Author',
				className:'btn-danger'
			}
		},
		callback: function(result) {
			if(result) {
				$.ajax({
					url: _this.attr('href'),
					success: function(data) {
						var _parent = _this.parent('p');
						if(data=="success") {
							_parent.replaceWith("<div class='alert alert-success alert-sm' role='alert'>Author Deleted Successfully</div>");
							$('.articleState').html('EDIT');
						}
						else { /*Only One Author*/
							$("<div class='alert alert-danger alert-sm' role='alert'>You Can Not Delete The Only Author Of An Article</div>").insertBefore(_parent);
						}
						setTimeout(function() {
							$('.alert.alert-danger, .alert.alert-success').remove();
						}, 5000);
					},
					error: function(data) {
						/*You Can Catch Some Errors Here*/
					}
				});
			}
		}
	});
});
/**
 * An AJAX Call To Delete An Article/Category Relationship
 */
$(document).on("click", ".delete-category", function(event) {
	var _this = $(this);
	event.preventDefault();
	bootbox.confirm({
		title:'<strong>Confirm</strong>',
		message: 'Are You Sure You Want To Delete This Category?',
		buttons: {
			'cancel': {
				label:'Cancel',
				className:'btn-default'
			},
			'confirm': {
				label:'Delete Category',
				className:'btn-danger'
			}
		},
		callback: function(result) {
			if(result) {
				$.ajax({
					url: _this.attr('href'),
					success: function(data) {
						var _parent = _this.parent('p');
						_parent.replaceWith("<div class='alert alert-success alert-sm' role='alert'>Category Deleted Successfully</div>");
						$('.articleState').html('EDIT');
						setTimeout(function() {
							$('.alert.alert-success').remove();
						}, 5000);
					},
					error: function(data) {
						/*You Can Catch Some Errors Here*/
					}
				});
			}
		}
	});
});
/**
 * An AJAX Call To Delete An Article/RelatedArticle Relationship
 */
$(document).on("click", ".delete-related", function(event) {
	var _this = $(this);
	event.preventDefault();
	bootbox.confirm({
		title:'<strong>Confirm</strong>',
		message: 'Are You Sure You Want To Delete This Related Article?',
		buttons: {
			'cancel': {
				label:'Cancel',
				className:'btn-default'
			},
			'confirm': {
				label:'Delete Article',
				className:'btn-danger'
			}
		},
		callback: function(result) {
			if(result) {
				$.ajax({
					url: _this.attr('href'),
					success: function(data) {
						var _parent = _this.parent('p');
						_parent.replaceWith("<div class='alert alert-success alert-sm' role='alert'>Related Article Deleted Successfully</div>");
						$('.articleState').html('EDIT');
						setTimeout(function() {
							$('.alert.alert-success').remove();
							if($('.form-group.related p').length==0) $('.form-group.related').remove();
						}, 5000);
					},
					error: function(data) {
						/*You Can Catch Some Errors Here*/
					}
				});
			}
		}
	});
});
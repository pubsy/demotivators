function handleFileSelect(evt) {
	var files = evt.target.files;
	
	for (var i = 0, f; f = files[i]; i++) {
		var reader = new FileReader();
		reader.onload = (function(theFile) {
			return function(e) {
				$('#preview img').attr('src', e.target.result);
				$('#preview').show();
			};
		})(f);
		reader.readAsDataURL(f);
	}
}

$(document).ready(function(){
	$('#image').bind('change', handleFileSelect);
}); 

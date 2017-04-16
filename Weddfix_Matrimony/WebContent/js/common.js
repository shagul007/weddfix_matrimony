$(function() {
	$('#nav li a').click(function() {
		$('#nav li a').removeClass('active');
		$(this).addClass('active');
	});
});

function checkName(id) {
	var stat = false;
	var regExp = /^[a-zA-Z\s.]*$/;
	var valo = new String();
	var chars = id.value.split("");
	for ( var i = 0; i < chars.length; i++) {
		if (chars[i].match(regExp)) {
			valo += chars[i];
			stat = true;
		} else {
			stat = false;
		}
	}
	if (id.value != valo) {
		id.value = valo;
	}
	return stat;
}

function checkNumeric(id) {
	var stat = false;
	var regExp = /^[0-9]*$/;
	var valo = new String();
	var chars = id.value.split("");
	for ( var i = 0; i < chars.length; i++) {
		if (chars[i].match(regExp)) {
			valo += chars[i];
			stat = true;
		} else {
			stat = false;
		}
	}
	if (id.value != valo) {
		id.value = valo;
	}
	return stat;
}

function checkAlphaNumeric_Space_Slash_Comma(id) {
	var stat = false;
	var regExp = /^[a-zA-Z_0-9_\s_\d-_\A-Z-_\a-z-_\.,_//]*$/;
	var valo = new String();
	var chars = id.value.split("");
	for ( var i = 0; i < chars.length; i++) {
		if (chars[i].match(regExp)) {
			valo += chars[i];
			stat = true;
		} else {
			stat = false;
		};
	}
	if (id.value != valo) {
		id.value = valo;
	}
	return stat;
}

function toUpperCase(id) {
	id.value = id.value.toUpperCase();
};

$(document).ready(function() {
	setTimeout(function() {
		$(".readonly").attr('readonly', 'readonly');
	}, 1000);
	
	setTimeout(function() {
		$(".calendar").attr('readonly', 'readonly');
	}, 1000);
	
	$(".calendar").datepicker({
		  changeYear: true,
		  yearRange: "1900:+0",
		  maxDate: 0,
		  dateFormat: 'dd/mm/yy'
	});
	
	setTimeout(function() {
		$(".futureCalendar").attr('readonly', 'readonly');
	}, 1000);
	
	$(".futureCalendar").datepicker({
		  changeYear: true,
		  minDate: 0,
		  dateFormat: 'dd/mm/yy'
	});
});

function isValidFile(obj){
	
	var fup = $(obj).val();		
	var fileName = fup;		
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1);		
	if(ext == "gif" || ext == "GIF" || ext == "JPEG" || ext == "jpeg" || ext == "jpg" || ext == "JPG" || ext == "png" || ext == "PNG")
		{
			return true;
		} 
	else
		{
			alert("Upload Jpg, Jpeg, Png, Gif file only");
			$(obj).val("");
			return false;
		}
	 
}



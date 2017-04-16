function validate(elementIds) {
	var count = 0;
	var length = elementIds.length;
	var flag = false;
	$("#logoutInfo").hide();
	$.each( elementIds, function( index, id ){
		var elementValue = document.forms["form"][id].value;
		
		if (elementValue == null || elementValue == "" || elementValue == "-1" || id == 'email' || id == 'password' || id == 'password2' || id == 'mobile') {
			if(elementValue == null || elementValue == "" || elementValue == "-1"){
				$('#' + id).addClass( 'error' );
				if($("#errorValue").val() == undefined){
					$('#error').append(
					'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please fill all mandetory fields.</span><input type="hidden" id="errorValue" value="1">').html;
				}
			} else {
				count = count + 1;
				$('#' + id).removeClass('error');
			}
		
			if(id == 'validateFromAgeToAge'){
				var fromAge = document.forms["form"]["fromAge"].value;
				var toAge = document.forms["form"]["toAge"].value;
				if (parseInt(fromAge) > parseInt(toAge)) {
					count = count - 1;
					$('#toAge').addClass( 'error' );
					if($("#fromAgeMessageValue").val() == undefined){
						$('#toAge').after(
						'<span id="fromAgeMessage" style="width: auto; display: block; color: red;">Sorry, Invalid age range. '+fromAge+' to '+toAge+'.</span><input type="hidden" id="fromAgeMessageValue" value="1">').html;
					}
				} else {
					count = count + 1;
					$('#fromAgeMessage').remove();
					$('#fromAgeMessageValue').remove();
				}
			}
			
			if(id == 'email' && $("#emailMessageValue").val() == undefined && elementValue != null && elementValue != ""){
				var atpos = elementValue.indexOf("@");
				var dotpos = elementValue.lastIndexOf(".");
				if (atpos < 1 || dotpos < atpos + 2
						|| dotpos + 2 > elementValue.length) {
					count = count - 1;
					$('#' +id).after(
					'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
				} else {
					$('#emailMessage').remove();
					$('#emailMessageValue').remove();
				}
			} else if(id == 'email' && elementValue != null && elementValue != "") {
				var atpos = elementValue.indexOf("@");
				var dotpos = elementValue.lastIndexOf(".");
				if (atpos < 1 || dotpos < atpos + 2
						|| dotpos + 2 > elementValue.length) {
					count = count - 1;
					if($("#emailMessageValue").val() == undefined){
					$('#' +id).after(
					'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
					}
				} else {
					$('#emailMessage').remove();
					$('#emailMessageValue').remove();
				}
			}
			if(id == 'password' && $("#passwordMessageValue").val() == undefined && elementValue != null && elementValue != ""){
				if (elementValue.length < 6) {
					count = count - 1;
					$('#' +id).after(
					'<span id="passwordMessage" style="width: auto; display: block; color: red;">Password should be minimum six characters.</span><input type="hidden" id="passwordMessageValue" value="1">').html;
				} else {
					$('#passwordMessage').remove();
					$('#passwordMessageValue').remove();
				}
			} else if(id == 'password' && elementValue != null && elementValue != "") {
				 if (elementValue.length < 6) {
					 count = count - 1;
					 if($("#passwordMessageValue").val() == undefined){
						$('#' +id).after(
						'<span id="passwordMessage" style="width: auto; display: block; color: red;">Password should be minimum six characters.</span><input type="hidden" id="passwordMessageValue" value="1">').html;
					 }
					 } else {
							$('#passwordMessage').remove();
							$('#passwordMessageValue').remove();
					 }
			}
			if(id == 'password2' && $("#passwordMessageValue2").val() == undefined && elementValue != null && elementValue != ""){
			if (elementValue != document.forms["form"]["password"].value) {
				count = count - 1;
				$('#password2').after(
				'<span id="passwordMessage2" style="width: auto; display: block; color: red;">Both Password are not equal. Please re-enter password.</span><input type="hidden" id="passwordMessageValue2" value="1">').html;
				document.forms["form"]["password"].value = "";
				document.forms["form"][id].value = "";
			} else {
				$('#passwordMessage2').remove();
				$('#passwordMessageValue2').remove();
			}
			} 
			if(id == 'password2' && elementValue != null && elementValue != "") {
				if (elementValue != document.forms["form"]["password"].value) {
					count = count - 1;
					 if($("#passwordMessageValue2").val() == undefined){
						$('#' +id).after(
						'<span id="passwordMessage2" style="width: auto; display: block; color: red;">Both Password are not equal. Please re-enter password.</span><input type="hidden" id="passwordMessageValue2" value="1">').html;
						document.forms["form"]["password"].value = "";
						document.forms["form"][id].value = "";
					 }
					 } else {
							$('#passwordMessage2').remove();
							$('#passwordMessageValue2').remove();
					 }
				}
			if(id == 'mobile' && $("#mobileMessageValue").val() == undefined && elementValue != null && elementValue != ""){
				if (elementValue.length < 10) {
					count = count - 1;
					$('#' +id).after(
					'<span id="mobileMessage" style="width: auto; display: block; color: red;">Invalid Mobile Number.</span><input type="hidden" id="mobileMessageValue" value="1">').html;
				 } else {
						$('#mobileMessage').remove();
						$('#mobileMessageValue').remove();
				 }
			 } else if(id == 'mobile' && elementValue != null && elementValue != "") {
				 if (elementValue.length < 10) {
					 count = count - 1;
					 if($("#mobileMessageValue").val() == undefined){
						$('#' +id).after(
						'<span id="mobileMessage" style="width: auto; display: block; color: red;">Invalid Mobile Number.</span><input type="hidden" id="mobileMessageValue" value="1">').html;
					 }
					 } else {
							$('#mobileMessage').remove();
							$('#mobileMessageValue').remove();
					 }
				}
		} else {
			count = count + 1;
			$('#' + id).removeClass('error');
		}
//		alert(count+" "+length);			
		if(length == count && $('#validateUser').val() == undefined){
			$('#error').hide();
			flag = true;
		} else {
			if(length == count) {
				$('#error').hide();
				checkUserAlreadyExist(document.forms["form"]["email"].value);
			}
		}
	});
	
	if(flag){
//		alert("Success");
		document.getElementById("form").submit();
	}
	
}

function checkUserAlreadyExist(email) {
	$.ajax({
		data : {
			emailId : email
		},
		url : "checkUserAlreadyExist.action",
		dataType : "json",
		success : function(data) {
			if (data.emailId != email) {
				$('#emailExistMessage').remove();
				document.getElementById("form").submit();
			} else {
				if($("#emailExistMessageValue").val() == undefined){
					$('#email').after(
					'<span id="emailExistMessage" style="width: auto; display: block; color: red;">This email already exist.</span><input type="hidden" id="emailExistMessageValue" value="1">').html;
				}
				return false;
			}
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			return false;
		}
	});
}

function validateSubscriberFileds(elementIds) {
	var count = 0;
	var length = elementIds.length;
	var flag = false;
	$.each( elementIds, function( index, id ){
		var elementValue = document.forms["subscriber"][id].value;
		
		if (elementValue == null || elementValue == "" || elementValue == "-1" || id == 'subscriberEmail') {
			if(elementValue == null || elementValue == "" || elementValue == "-1"){
				$('#' + id).addClass('error');
			} else {
				count = count + 1;
				$('#' + id).removeClass('error');
			}
		
			if(id == 'subscriberEmail' && $("#emailMessageValue").val() == undefined && elementValue != null && elementValue != ""){
				var atpos = elementValue.indexOf("@");
				var dotpos = elementValue.lastIndexOf(".");
				if (atpos < 1 || dotpos < atpos + 2
						|| dotpos + 2 > elementValue.length) {
					count = count - 1;
					$('#' +id).after(
					'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
				} else {
					$('#emailMessage').remove();
					$('#emailMessageValue').remove();
				}
			} else if(id == 'subscriberEmail' && elementValue != null && elementValue != "") {
				var atpos = elementValue.indexOf("@");
				var dotpos = elementValue.lastIndexOf(".");
				if (atpos < 1 || dotpos < atpos + 2
						|| dotpos + 2 > elementValue.length) {
					count = count - 1;
					if($("#emailMessageValue").val() == undefined){
					$('#' +id).after(
					'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
					}
				} else {
					$('#emailMessage').remove();
					$('#emailMessageValue').remove();
				}
			}
		} else {
			count = count + 1;
			$('#' + id).removeClass('error');
		}
		if(length == count && $('#validateUser').val() == undefined){
			$('#error').hide();
			flag = true;
		} else {
			if(length == count) {
				$('#error').hide();
				checkSubscriberEmailAlreadyExist(document.forms["subscriber"]["subscriberEmail"].value);
			}
		}
	});
	
	if(flag){
		submitSubscriberForm();
	}
	
}

function checkSubscriberEmailAlreadyExist(email) {
	$.ajax({
		data : {
			emailId : email
		},
		url : "checkSubscriberEmailAlreadyExist.action",
		dataType : "json",
		success : function(data) {
			if (data.emailId != email) {
				submitSubscriberForm();
			} else {
				$("#subscriberError").show();
				return false;
			}
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			return false;
		}
	});
}

function submitSubscriberForm() {
	var form = $("#subscriber").serialize();
	$.ajax({
		data : form,
		url : "subscriber_success.action",
		type : "post",
		dataType : "json",
		success : function(data) {
			$("#subscriberError").hide();
			$("#subscriber").hide();
			$("#subscriberSuccess").show();
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			$("#subscriber").show();
			$("#subscriberError").hide();
			$("#subscriberSuccess").hide();
		}
	});
}

function validateForgotEmailFileds(elementIds) {
	var count = 0;
	var length = elementIds.length;
	$.each( elementIds, function( index, id ){
		var elementValue = document.forms["form"][id].value;
		
		if (elementValue == null || elementValue == "" || elementValue == "-1" || id == 'email') {
			if(elementValue == null || elementValue == "" || elementValue == "-1"){
				$('#' + id).addClass('error');
			} else {
				count = count + 1;
				$('#' + id).removeClass('error');
			}
		
			if(id == 'email' && $("#emailMessageValue").val() == undefined && elementValue != null && elementValue != ""){
				var atpos = elementValue.indexOf("@");
				var dotpos = elementValue.lastIndexOf(".");
				if (atpos < 1 || dotpos < atpos + 2
						|| dotpos + 2 > elementValue.length) {
					count = count - 1;
					$('#' +id).after(
					'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
				} else {
					$('#emailMessage').remove();
					$('#emailMessageValue').remove();
				}
			} else if(id == 'email' && elementValue != null && elementValue != "") {
				var atpos = elementValue.indexOf("@");
				var dotpos = elementValue.lastIndexOf(".");
				if (atpos < 1 || dotpos < atpos + 2
						|| dotpos + 2 > elementValue.length) {
					count = count - 1;
					if($("#emailMessageValue").val() == undefined){
					$('#' +id).after(
					'<span id="emailMessage" style="width: auto; display: block; color: red;">Invalid Email Address.</span><input type="hidden" id="emailMessageValue" value="1">').html;
					}
				} else {
					$('#emailMessage').remove();
					$('#emailMessageValue').remove();
				}
			}
		} else {
			count = count + 1;
			$('#' + id).removeClass('error');
		}
		if(length == count){
			$('#error').hide();
			flag = true;
			checkEmailExist(document.forms["form"]["email"].value);
		}
	});
	
}

function checkEmailExist(email) {
	$.ajax({
		data : {
			emailId : email
		},
		url : "checkUserAlreadyExist.action",
		dataType : "json",
		success : function(data) {
			if (data.emailId == email) {
				$('#emailExistMessage').remove();
				document.getElementById("form").submit();
			} else {
				if($("#emailExistMessageValue").val() == undefined){
					$('#email').after(
					'<span id="emailExistMessage" style="width: auto; display: block; color: red;">This user not exist.</span><input type="hidden" id="emailExistMessageValue" value="1">').html;
				}
				return false;
			}
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			return false;
		}
	});
}

function loadStateByCountryId(obj) {
	var id = $(obj).val();
	$("#stateId option").remove();
	$("#stateId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- SELECT ---"));
	$("#cityId option").remove();
	$("#cityId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- SELECT ---"));
	$.ajax({
		data : {

			country_Id : id
		},
		url : "loadStateByCountryId.action",
		dataType : "json",
		success : function(data) {
				$.each(data.stateMap, function(key,
						value) {
					$("#stateId").append(
							$("<option></option>").attr("value", key).text(
									value));
				});
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			alert(xhr);
			alert(textStatus);
			alert(errorThrown);
		}
	});
	
}

function loadCityByStateId(obj) {
	var id = $(obj).val();
	$("#cityId option").remove();
	$("#cityId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- SELECT ---"));
	$.ajax({
		data : {

			state_Id : id
		},
		url : "loadCityByStateId.action",
		dataType : "json",
		success : function(data) {
				$.each(data.cityMap, function(key,
						value) {
					$("#cityId").append(
							$("<option></option>").attr("value", key).text(
									value));
				});
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			alert(xhr);
			alert(textStatus);
			alert(errorThrown);
		}
	});
	
}


function loadCasteByReligionId(obj) {
	var id = $(obj).val();
	$("#casteId option").remove();
	$("#casteId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- SELECT ---"));
	$.ajax({
		data : {

			religionId : id
		},
		url : "loadCasteByReligionId.action",
		dataType : "json",
		success : function(data) {
				$.each(data.casteMap, function(key,
						value) {
					$("#casteId").append(
							$("<option></option>").attr("value", key).text(
									value));
				});
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			alert(xhr);
			alert(textStatus);
			alert(errorThrown);
		}
	});
	
}

function loadPPStateByCountryId(obj) {
	var id = $(obj).val();
	$("#stateId option").remove();
	$("#stateId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- ANY ---"));
	$("#cityId option").remove();
	$("#cityId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- ANY ---"));
	$.ajax({
		data : {

			country_Id : id
		},
		url : "loadStateByCountryId.action",
		dataType : "json",
		success : function(data) {
				$.each(data.stateMap, function(key,
						value) {
					$("#stateId").append(
							$("<option></option>").attr("value", key).text(
									value));
				});
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			alert(xhr);
			alert(textStatus);
			alert(errorThrown);
		}
	});
	
}

function loadPPCityByStateId(obj) {
	var id = $(obj).val();
	$("#cityId option").remove();
	$("#cityId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- ANY ---"));
	$.ajax({
		data : {

			state_Id : id
		},
		url : "loadCityByStateId.action",
		dataType : "json",
		success : function(data) {
				$.each(data.cityMap, function(key,
						value) {
					$("#cityId").append(
							$("<option></option>").attr("value", key).text(
									value));
				});
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			alert(xhr);
			alert(textStatus);
			alert(errorThrown);
		}
	});
	
}

function loadPPCasteByReligionId(obj) {
	var id = $(obj).val();
	$("#casteId option").remove();
	$("#casteId").append(
			$("<option></option>").attr("value", "-1").text(
					"--- ANY ---"));
	$.ajax({
		data : {

			religionId : id
		},
		url : "loadCasteByReligionId.action",
		dataType : "json",
		success : function(data) {
				$.each(data.casteMap, function(key,
						value) {
					$("#casteId").append(
							$("<option></option>").attr("value", key).text(
									value));
				});
		},
		error : function(xhr, textStatus, errorThrown) {
			// Handle error
			alert(xhr);
			alert(textStatus);
			alert(errorThrown);
		}
	});
	
}

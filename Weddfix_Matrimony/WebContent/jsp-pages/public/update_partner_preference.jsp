<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	var userId = '<%=session.getAttribute("userId") %>';
	if(userId == 'null') {
		window.location.href = "login";
	}

	function validateForm() {
		validateUpdatePartnerPreferenceFields([ 'fromAge', 'toAge', 'validateFromAgeToAge', 'maritalStatusId', 'bodyType', 'complexion', 'fromHeightId', 'toHeightId', 'validateFromHeightToHeight', 'food' ]);
	}
	
	function validateUpdatePartnerPreferenceFields(elementIds) {
		var count = 0;
		var length = elementIds.length;
		var flag = false;
		$.each( elementIds, function( index, id ){
			var elementValue = document.forms["form"][id].value;
			
			if (elementValue == null || elementValue == "" || elementValue == "-1") {
				$('#' + id).addClass( 'error' );
				if($("#errorValue").val() == undefined){
					$('#error').append(
					'<span id="errorMessage" style="color: red; margin-bottom: 20px; float: left;">* Please fill all mandetory fields.</span><input type="hidden" id="errorValue" value="1">').html;
				}
			
		} else {
			if(id != 'bodyType' && id != 'complexion' && id != 'food'){
				count = count + 1;
			}
			$('#' + id).removeClass('error');
		}
		
		if(id == 'validateFromAgeToAge'){
			var fromAge = document.forms["form"]["fromAge"].value;
			var toAge = document.forms["form"]["toAge"].value;
			if (parseInt(fromAge) > parseInt(toAge)) {
				count = count - 1;
				if($("#fromAgeMessageValue").val() == undefined){
					$('#toAge').addClass( 'error' );
					$('#toAge').after(
					'<span id="fromAgeMessage" style="width: auto; display: block; color: red;">Sorry, Invalid age range. '+fromAge+' to '+toAge+'.</span><input type="hidden" id="fromAgeMessageValue" value="1">').html;
				}
			} else {
				count = count + 1;
				$('#fromAgeMessage').remove();
				$('#fromAgeMessageValue').remove();
			}
		}
			if(id == 'bodyType') {
				$('#' + id).parent().addClass( 'error' ); 
				if($("#bodyType:checked").length>0) {
					count = count + 1;
					$('#' + id).parent().removeClass('error');
				}
			}
			
			 if(id == 'complexion'){
				$('#' + id).parent().addClass( 'error' ); 
				if($("#complexion:checked").length>0) {
					count = count + 1;
					$('#' + id).parent().removeClass('error');
				}
			}
			 
			 if(id == 'validateFromHeightToHeight'){
					var fromHeight = document.forms["form"]["fromHeightId"].value;
					var toHeight = document.forms["form"]["toHeightId"].value;
					if (parseInt(fromHeight) > parseInt(toHeight)) {
						count = count - 1;
						if($("#fromHeightMessageValue").val() == undefined){
							$('#toHeightId').addClass( 'error' );
							$('#toHeightId').after(
							'<span id="fromHeightMessage" style="width: auto; display: block; color: red;">Sorry, Invalid height range.</span><input type="hidden" id="fromHeightMessageValue" value="1">').html;
						}
					} else {
						count = count + 1;
						$('#fromHeightMessage').remove();
						$('#fromHeightMessageValue').remove();
					}
				}
			
			if(id == 'food'){
				$('#' + id).parent().addClass( 'error' ); 
				if($("#food:checked").length>0) {
					count = count + 1;
					$('#' + id).parent().removeClass('error');
				}
			} 
			
//			alert(count+" "+length);			
			if(length == count){
				$('#error').hide();
				flag = true;
			} 
		});
		
		if(flag){
//			alert("Success");
			formSubmit();
		}
		}
	
	$(document).ready(function() {
		var bodyType = '<%=session.getAttribute("botyType")%>';
		$("input[name=bodyType1][value=" + bodyType + "]").prop('checked', true);
		$("#bodyType1").val(bodyType);
		
		var complexion = '<%=session.getAttribute("complexion")%>';
		$("input[name=complexion1][value=" + complexion + "]").prop('checked', true);
		$("#complexion1").val(complexion);
		
		var food = '<%=session.getAttribute("food")%>';
		$("input[name=food1][value=" + food + "]").prop('checked', true);
		$("#food1").val(food);
		
		$("#fromAge").val('<%=session.getAttribute("fromAge")%>');
		$("#toAge").val('<%=session.getAttribute("toAge")%>');
		
		$("#maritalStatusId").val('<%=session.getAttribute("maritalStatusId")%>');
		$("#fromHeightId").val(<%=session.getAttribute("fromHeightId")%>);
		$("#toHeightId").val(<%=session.getAttribute("toHeightId")%>);
		$("#religionId").val('<%=session.getAttribute("religionId")%>');
		$("#casteId").val('<%=session.getAttribute("casteId")%>');
		$("#motherTongueId").val('<%=session.getAttribute("motherTongueId")%>');
		$("#educationId").val('<%=session.getAttribute("educationId")%>');
		$("#occupationId").val('<%=session.getAttribute("occupationId")%>');
		$("#country_Id").val('<%=session.getAttribute("countryId")%>');
		$("#countryId").val('<%=session.getAttribute("countryId")%>');
		$("#stateId").val(<%=session.getAttribute("stateId")%>);
		$("#cityId").val(<%=session.getAttribute("cityId")%>);
		onLoadStateByCountryId();
		onLoadCityByStateId();
		onLoadCasteByReligionId();
		
	});
	
	function onLoadStateByCountryId() {
		var id = '<%=session.getAttribute("countryId")%>';
		var stateId = '<%=session.getAttribute("stateId")%>';
		$("#stateId option").remove();
		$("#stateId").append(
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
					$("#stateId option[value="+stateId+"]").attr('selected', 'selected');
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				alert(xhr);
				alert(textStatus);
				alert(errorThrown);
			}
		});
		
	}

	function onLoadCityByStateId() {
		var id = '<%=session.getAttribute("stateId")%>';
		var cityId = '<%=session.getAttribute("cityId")%>';
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
					$("#cityId option[value="+cityId+"]").attr('selected', 'selected');
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				alert(xhr);
				alert(textStatus);
				alert(errorThrown);
			}
		});
		
	}

	function onLoadCasteByReligionId() {
		var id = '<%=session.getAttribute("religionId")%>';
		var casteId = '<%=session.getAttribute("casteId")%>';
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
					$("#casteId option[value="+casteId+"]").attr('selected', 'selected');
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				alert(xhr);
				alert(textStatus);
				alert(errorThrown);
			}
		});
		
	}
	
	function setCountryId() {
		$("#countryId").val($("#country_Id").val());
	}
	
	function setBodyType(obj) {
		$("#bodyType1").val($(obj).val());
	}
	
	function setComplexion(obj) {
		$("#complexion1").val($(obj).val());
	}
	
	function setFood(obj) {
		$("#food1").val($(obj).val());
	}
	
	function formSubmit(){

		 $.ajax({
		     url:'update_partner_preference_success.action',
		     type: "POST",
	         async: false,
		     data: $("#form").serialize(),
		     success: function (data) {
		    	 $("#success").show();
		    },
		 	error : function(xhr, textStatus, errorThrown) {
				// Handle error
		 		alert("error");
			}
		});
		}
	
	function closePopup() {
		$("#success").hide();
		window.location.replace("update_partner_preference");
	}
	
</script>
</head>

<body>
<div class="container">
 <br />
	<h3>Profile Settings<br />
<%--         <small>A simple, sheek navigation bar style!</small> --%>
    </h3>
    <br />
    
    <div class="row">
        <div class="col-sm-2">
            <nav class="nav-sidebar">
                <ul class="nav">
                    <li><a href="<%=request.getContextPath()%>/profile_settings">Account Details</a></li>
                    <li><a href="<%=request.getContextPath()%>/change_password">Change Password</a></li>
                    <li><a href="<%=request.getContextPath()%>/profile_picture">Profile Picture</a></li>
                    <li><a href="<%=request.getContextPath()%>/privacy_settings">Privacy Settings</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_profile">Update Profile</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_personal_details">Update Personal Details</a></li>
                    <li class="active"><a href="<%=request.getContextPath()%>/update_partner_preference">Partner Preference</a></li>
                    <li><a href="<%=request.getContextPath()%>/deactivate_profile">Deactivate Profile</a></li>
                    <li><a href="<%=request.getContextPath()%>/delete_profile">Delete Profile</a></li>
<!--                     <li class="nav-divider"></li> -->
<!--                     <li><a href="javascript:;"><i class="glyphicon glyphicon-off"></i> Sign in</a></li> -->
                </ul>
            </nav>
        </div>
        <div class="col-sm col-sm-offset-2">
<section id="form-container">
	 <div class="main-content">
		<s:actionerror />
		<s:form cssClass="form" action="registerSuccess" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background" style="width: 730px;">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Update Partner Preference</h1>
			</div>
			<s:hidden name="id" id="id" value="%{partnerPreferenceBean['id']}" />
			<s:hidden name="countryId" id="countryId" />
			<s:hidden name="bodyType" id="bodyType1" />
			<s:hidden name="complexion" id="complexion1" />
			<s:hidden name="food" id="food1" />
			<s:hidden name="validateFromAgeToAge" id="validateFromAgeToAge" />
			<s:hidden name="validateFromHeightToHeight" id="validateFromHeightToHeight" />
			<div class="form-row">
			<label class="multiple_box"><span>Age</span> </label>
				<input id="fromAge" name="fromAge" type="text" maxlength="2" onkeyup="checkNumeric(this);" style="width: 50px;" />
			To 
				<input id="toAge" name="toAge" type="text" maxlength="2" onkeyup="checkNumeric(this);" style="width: 50px;" />
			</div>
			<div class="form-row">
			<label> <span>Marital status</span> 
			<select id="maritalStatusId" name="maritalStatusId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="maritalStatusMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
				<label style="float: left;"> <span>Body type</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" id="bodyType" name="bodyType1" onclick="setBodyType(this);" value="Slim"> <span>Slim</span>
							<input type="radio" id="bodyType" name="bodyType1" onclick="setBodyType(this);" value="Average"> <span>Average</span>
							<input type="radio" id="bodyType" name="bodyType1" onclick="setBodyType(this);" value="Athletic"> <span>Athletic</span>
							<input type="radio" id="bodyType" name="bodyType1" onclick="setBodyType(this);" value="Heavy"> <span>Heavy</span>
						</label>
				  </div>
			  </div>
			  </div>
			<div class="form-row">
				<label style="float: left;"> <span>Complexion</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" id="complexion" name="complexion1" onclick="setComplexion(this);" value="Very Fair"> <span>Very Fair</span>
							<input type="radio" id="complexion" name="complexion1" onclick="setComplexion(this);" value="Fair"> <span>Fair</span>
							<input type="radio" id="complexion" name="complexion1" onclick="setComplexion(this);" value="Wheatish"> <span>Wheatish</span>
							<input type="radio" id="complexion" name="complexion1" onclick="setComplexion(this);" value="Wheatish Brown"> <span>Wheatish Brown</span>
							<input type="radio" id="complexion" name="complexion1" onclick="setComplexion(this);" value="Dark"> <span>Dark</span>
						</label>
					</div>
				  </div>
			  </div>
			  <div class="form-row">
			  <label class="multiple_box"><span>Height</span> </label>
			<select id="fromHeightId" name="fromHeightId" style="min-width: 50px;">
					<option value="-1">-SELECT-</option>
				<s:iterator value="heightMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			To
			<select id="toHeightId" name="toHeightId" style="min-width: 50px;">
					<option value="-1">-SELECT-</option>
				<s:iterator value="heightMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</div>
			<div class="form-row">
				<label style="float: left;"> <span>Food</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" id="food" name="food1" onclick="setFood(this);" value="Vegetarian"> <span>Vegetarian</span>
							<input type="radio" id="food" name="food1" onclick="setFood(this);" value="Non-Vegetarian"> <span>Non-Vegetarian</span>
							<input type="radio" id="food" name="food1" onclick="setFood(this);" value="Eggetarian"> <span>Eggetarian</span>
						</label>
					</div>
				  </div>
			  </div>
			  <div class="form-row">
			<label> <span>Religion</span> 
			<select id="religionId" name="religionId"  onchange="loadPPCasteByReligionId(this)">
					<option value="-1">--- ANY ---</option>
				<s:iterator value="religionMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Caste</span> 
			<select id="casteId" name="casteId">
					<option value="-1">--- ANY ---</option>
				<%-- <s:iterator value="casteMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Mother Tongue</span> 
			<select id="motherTongueId" name="motherTongueId">
					<option value="-1">--- ANY ---</option>
				<s:iterator value="motherTongueMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Highest Education</span> 
			<select id="educationId" name="educationId">
					<option value="-1">--- ANY ---</option>
				<s:iterator value="educationMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Occupation</span> 
			<select id="occupationId" name="occupationId">
					<option value="-1">--- ANY ---</option>
				<s:iterator value="occupationMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Country</span> 
			<select id="country_Id" name="country_Id" onchange="setCountryId(); loadPPStateByCountryId(this)" onkeyup="setCountryId()">
					<option value="-1">--- ANY ---</option>
				<s:iterator value="countryMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>State</span> 
			<select id="stateId" name="stateId" onchange="loadPPCityByStateId(this)">
					<option value="-1">--- ANY ---</option>
				<%-- <s:iterator value="stateMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>City</span> 
			<select id="cityId" name="cityId">
					<option value="-1">--- ANY ---</option>
				<%-- <s:iterator value="cityMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			<div class="form-row">
				<button type="button" onclick="return validateForm();">Update</button>
			</div>
			</div>
			</div>
		</s:form>
	</div>
	</section>
	</div>
	</div>
	</div>
 <div id="success" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
		<div class="content">
			<p>Your partner preference has been updated successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
</body>
</html>

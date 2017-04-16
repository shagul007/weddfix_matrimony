<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<script type="text/javascript">
	function validateForm() {
		validate([ 'userProfileId', 'fullName', 'genderId', 'maritalStatusId', 'dob', 'email' ,'password',
					'password2', 'mobile', 'heightId', 'educationId', 'occupationId', 'religionId', 'country_Id', 'stateId', 'cityId', 'aboutYou' ]);
	}
	
	$(document).ready(function() {
		$("#country_Id option[value=98]").attr('selected', 'selected');
		$("#countryId").val($("#country_Id").val());
		onLoadStateByCountryId();
	});
	
	function onLoadStateByCountryId() {
		var id = $("#country_Id").val();
		$("#stateId option").remove();
		$("#stateId").append(
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

	function setCountryId() {
		$("#countryId").val($("#country_Id").val());
	}
	
</script>
</head>

<body>
<section id="form-container">
	 <div class="main-content">
		<s:actionerror />
		<s:form cssClass="form" action="partner_preference" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Weddfix Registration</h1>
			</div>
			<s:hidden id="validateUser" value="1" />
			<s:hidden name="countryId" id="countryId" />
			<div class="form-row">
			<label> <span>Profile for</span> 
			<select id="userProfileId" name="userProfileId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="profileMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Name</span> 
				<input id="fullName" name="fullName" type="text" maxlength="50" onkeyup="checkName(this);" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Gender</span> 
			<select id="genderId" name="genderId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="genderMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
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
			<label> <span>Date of Birth</span> 
				<input id="dob" name="dateOfBirth" type="text" class="calendar" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Email</span> 
				<input id="email" name="email" type="text" maxlength="100" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Password</span> 
				<input id="password" name="password" type="password" maxlength="20" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Re-type Password</span> 
				<input id="password2" name="password2" type="password" maxlength="20" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Mobile</span> 
				<input id="mobile" name="mobile" type="text" maxlength="10" onkeyup="checkNumeric(this);" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Height</span> 
			<select id="heightId" name="heightId">
					<option value="-1">--- Feet/Inches ---</option>
				<s:iterator value="heightMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Highest Education</span> 
			<select id="educationId" name="educationId">
					<option value="-1">--- Select ---</option>
				<s:iterator value="educationMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Occupation</span> 
			<select id="occupationId" name="occupationId">
					<option value="-1">--- Select ---</option>
				<s:iterator value="occupationMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Religion</span> 
			<select id="religionId" name="religionId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="religionMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Country</span> 
			<select id="country_Id" name="country_Id" onchange="setCountryId(); loadStateByCountryId(this);" onkeyup="setCountryId()">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="countryMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>State</span> 
			<select id="stateId" name="stateId" onchange="loadCityByStateId(this)">
					<option value="-1">--- SELECT ---</option>
				<%-- <s:iterator value="stateMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>City</span> 
			<select id="cityId" name="cityId">
					<option value="-1">--- SELECT ---</option>
				<%-- <s:iterator value="cityMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
			</select>
			</label>
			</div>
			<div class="form-row">
				<label><span>Something About You</span>
					<textarea name="aboutYou" id="aboutYou"></textarea>
				</label>
			</div>
			<%-- <div class="form-row">
			<label> <span>Mother Tongue</span> 
			<select id="motherTonqueId" name="motherTonqueId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="motherTongueMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Address 1</span> 
				<input id="address1" name="address1" type="text" maxlength="250" onkeyup="checkAlphaNumaric_Space_Slash_Comma(this);" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Address 2</span> 
				<input id="address2" name="address2" type="text" maxlength="250" onkeyup="checkAlphaNumaric_Space_Slash_Comma(this);" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>City</span> 
				<input id="city" name="city" type="text" maxlength="50" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>Pincode</span> 
				<input id="pincode" name="pincode" type="text" maxlength="6" onkeyup="checkNumeric(this);" />
			</label>
			</div>
			<div class="form-row">
			<label> <span>State</span> 
			<select id="stateId" name="stateId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="stateMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Country</span> 
			<select id="country_Id" name="country_Id" disabled="disabled" onchange="setCountryId()" onkeyup="setCountryId()">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="countryMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div> --%>
			<div class="form-row">
				<button type="button" onclick="return validateForm();">Register</button>
				<input type="button" class="reset" onclick="this.form.reset()"
					value="Reset" />
			</div>
			</div>
			</div>
		</s:form>
	</div>
	</section>
</body>
</html>

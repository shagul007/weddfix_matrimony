<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript">
	var userId = '<%=session.getAttribute("userId") %>';
	if(userId == 'null') {
		window.location.href = "register";
	}
	
	function validateForm() {
		$("#profileIncomplete").hide();
		validateRegister2Fields([ 'maritalStatusId', 'casteId', 'country_Id', 'stateId', 'cityId', 'heightId', 'weightId', 'bodyType', 'complexion', 'physicalStatus',
					'educationId', 'occupationId', 'employedIn', 'currencyId', 'monthlyIncome', 'food', 'smoking', 'drinking', 'familyStatus', 'familyType',
					'familyValues', 'aboutYou' ]);
	}
	
	function validateRegister2Fields(elementIds) {
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
				if(id != 'bodyType' && id != 'complexion' && id != 'physicalStatus' && id != 'employedIn'
						&& id != 'food' && id != 'smoking' && id != 'drinking' && id != 'familyStatus'
						&& id != 'familyType' && id != 'familyValues'){
					count = count + 1;
				}
				$('#' + id).removeClass('error');
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
				
				 if(id == 'physicalStatus'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#physicalStatus:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				}
				
				if(id == 'employedIn'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#employedIn:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				} 
				
				if(id == 'food'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#food:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				} 
				
				if(id == 'smoking'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#smoking:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				} 
				
				if(id == 'drinking'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#drinking:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				} 
				
				if(id == 'familyStatus'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#familyStatus:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				} 
				
				if(id == 'familyType'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#familyType:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				} 
				
				if(id == 'familyValues'){
					$('#' + id).parent().addClass( 'error' ); 
					if($("#familyValues:checked").length>0) {
						count = count + 1;
						$('#' + id).parent().removeClass('error');
					}
				}  
				
// 			alert(count+" "+length);			
			if(length == count){
				$('#error').hide();
				flag = true;
			} 
		});
		
		if(flag){
//			alert("Success");
			document.getElementById("form").submit();
		}
		
	}
	
	$(document).ready(function() {
		$("#country_Id option[value=98]").attr('selected', 'selected');
		$("#countryId").val($("#country_Id").val());
		var religionId = '<%=session.getAttribute("religionId")%>';
		$("#religionId option[value="+religionId+"]").attr('selected', 'selected');
		onLoadStateByCountryId();
		onLoadCasteByReligionId();
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

	function onLoadCasteByReligionId() {
		var id = '<%=session.getAttribute("religionId")%>';
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
	
	function setCountryId() {
		$("#countryId").val($("#country_Id").val());
	}
	
	function setBodyType(obj) {
		$("#bodyType1").val($(obj).val());
	}
	
	function setComplexion(obj) {
		$("#complexion1").val($(obj).val());
	}
	
	function setPhysicalStatus(obj) {
		$("#physicalStatus1").val($(obj).val());
	}
	
	function setEmployedIn(obj) {
		$("#employedIn1").val($(obj).val());
	}
	
	function setFood(obj) {
		$("#food1").val($(obj).val());
	}
	
	function setSmoking(obj) {
		$("#smoking1").val($(obj).val());
	}
	
	function setDrinking(obj) {
		$("#drinking1").val($(obj).val());
	}
	
	function setFamilyStatus(obj) {
		$("#familyStatus1").val($(obj).val());
	}
	
	function setFamilyType(obj) {
		$("#familyType1").val($(obj).val());
	}
	
	function setFamilyValues(obj) {
		$("#familyValues1").val($(obj).val());
	}
	
</script>
</head>

<body>
<section id="form-container">
	 <div class="main-content">
		<s:actionerror />
		<s:form cssClass="form" action="partner_preference" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background" style="width: 730px;">
			<% if(request.getParameter("profileIncomplete") != null) { if(request.getParameter("profileIncomplete").toString().equals("true")) { %>
			<div id="profileIncomplete" style="color: red; margin-bottom: 15px;">Your profile is incomplete. Please complete your profile.</div>
			<%}} %>			
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Personal Information</h1>
			</div>
			<s:hidden name="countryId" id="countryId" />
			<s:hidden name="bodyType" id="bodyType1" />
			<s:hidden name="complexion" id="complexion1" />
			<s:hidden name="physicalStatus" id="physicalStatus1" />
			<s:hidden name="employedIn" id="employedIn1" />
			<s:hidden name="food" id="food1" />
			<s:hidden name="smoking" id="smoking1" />
			<s:hidden name="drinking" id="drinking1" />
			<s:hidden name="familyStatus" id="familyStatus1" />
			<s:hidden name="familyType" id="familyType1" />
			<s:hidden name="familyValues" id="familyValues1" />
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
			<label> <span>Religion</span> 
			<select id="religionId" name="religionId" disabled="disabled" onchange="loadCasteByReligionId(this)">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="religionMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-row">
			<label> <span>Caste</span> 
			<select id="casteId" name="casteId">
					<option value="-1">--- SELECT ---</option>
				<%-- <s:iterator value="casteMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator> --%>
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
			<div class="form-title-row">
				<h1>Physical Attributes</h1>
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
			<%-- <div class="form-row">
			<label> <span>Cms</span> 
			<select id="cmsId" name="cmsId">
					<option value="-1">--- Cms ---</option>
				<s:iterator value="cmsMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div> --%>
			<div class="form-row">
			<label> <span>Weight</span> 
			<select id="weightId" name="weightId">
					<option value="-1">--- Kgs ---</option>
				<s:iterator value="weightMap">
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
				<label style="float: left;"> <span>Physical status</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" name="physicalStatus1" id="physicalStatus" onclick="setPhysicalStatus(this);" value="Normal"> <span>Normal</span>
							<input type="radio" name="physicalStatus1" id="physicalStatus" onclick="setPhysicalStatus(this);" value="Physically challenged"> <span>Physically challenged</span>
						</label>
					</div>
				  </div>
			  </div>
			<div class="form-title-row">
				<h1>Education &amp; Occupation</h1>
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
				<label style="float: left;"> <span>Employed in</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" name="employedIn1" id="employedIn" onclick="setEmployedIn(this);" value="Government"> <span>Government</span>
							<input type="radio" name="employedIn1" id="employedIn" onclick="setEmployedIn(this);" value="Private"> <span>Private</span>
							<input type="radio" name="employedIn1" id="employedIn" onclick="setEmployedIn(this);" value="Business"> <span>Business</span>
							<input type="radio" name="employedIn1" id="employedIn" onclick="setEmployedIn(this);" value="Defence"> <span>Defence</span>
							<input type="radio" name="employedIn1" id="employedIn" onclick="setEmployedIn(this);" value="Self Employed"> <span>Self Employed</span>
						</label>
					</div>
				  </div>
			  </div>
			 <div class="form-row">
				<label> <span>Monthly Income</span> 
					<select id="currencyId" name="currencyId" style="min-width: 65px;">
						<s:iterator value="currencyMap">
							<option value="<s:property value="key" />"><s:property value="value" /></option>
						</s:iterator>
					</select>
					<input id="monthlyIncome" name="monthlyIncome" type="text" maxlength="14" onkeyup="checkNumeric(this);" style="width: 160px;"/>
				</label>
			</div>
			<div class="form-title-row">
				<h1>Habits</h1>
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
				<label style="float: left;"> <span>Smoking</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" id="smoking" name="smoking1" onclick="setSmoking(this);" value="No"> <span>No</span>
							<input type="radio" id="smoking" name="smoking1" onclick="setSmoking(this);" value="Occasionally"> <span>Occasionally</span>
							<input type="radio" id="smoking" name="smoking1" onclick="setSmoking(this);" value="Yes"> <span>Yes</span>
						</label>
					</div>
				  </div>
			  </div>
			  <div class="form-row">
				<label style="float: left;"> <span>Drinking</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" id="drinking" name="drinking1" onclick="setDrinking(this);" value="No"> <span>No</span>
							<input type="radio" id="drinking" name="drinking1" onclick="setDrinking(this);" value="Drinks Socially"> <span>Drinks Socially</span>
							<input type="radio" id="drinking" name="drinking1" onclick="setDrinking(this);" value="Yes"> <span>Yes</span>
						</label>
					</div>
				  </div>
			  </div>
			 <div class="form-title-row">
				 <h1>Family Profile</h1>
			 </div>
			 <div class="form-row">
				<label style="float: left;"> <span>Family status</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" name="familyStatus1" id="familyStatus" onclick="setFamilyStatus(this);" value="Middle class"> <span>Middle class</span>
							<input type="radio" name="familyStatus1" id="familyStatus" onclick="setFamilyStatus(this);" value="Upper middle class"> <span>Upper middle class</span>
							<input type="radio" name="familyStatus1" id="familyStatus" onclick="setFamilyStatus(this);" value="Rich"> <span>Rich</span>
							<input type="radio" name="familyStatus1" id="familyStatus" onclick="setFamilyStatus(this);" value="Affluent"> <span>Affluent</span>
						</label>
					</div>
				  </div>
			  </div>
			  <div class="form-row">
				<label style="float: left;"> <span>Family type</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label>
							 <input type="radio" name="familyType1" id="familyType" onclick="setFamilyType(this);" value="Joint"> <span>Joint</span>
							 <input type="radio" name="familyType1" id="familyType" onclick="setFamilyType(this);" value="Nuclear"> <span>Nuclear</span>
						</label>
					</div>
				  </div>
			  </div>
			  <div class="form-row">
				<label style="float: left;"> <span>Family values</span>
				</label>
				<div class="form-radio-buttons">
					<div>
						<label> 
							<input type="radio" name="familyValues1" id="familyValues" onclick="setFamilyValues(this);" value="Orthodox"> <span>Orthodox</span>
							<input type="radio" name="familyValues1" id="familyValues" onclick="setFamilyValues(this);" value="Traditional"> <span>Traditional</span>
							<input type="radio" name="familyValues1" id="familyValues" onclick="setFamilyValues(this);" value="Moderate"> <span>Moderate</span>
							<input type="radio" name="familyValues1" id="familyValues" onclick="setFamilyValues(this);" value="Liberal"> <span>Liberal</span>
						</label>
					</div>
				  </div>
			  </div>
			<div class="form-row">
				<label><span>Something About You</span>
					<textarea name="aboutYou" id="aboutYou"></textarea>
				</label>
			</div>
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

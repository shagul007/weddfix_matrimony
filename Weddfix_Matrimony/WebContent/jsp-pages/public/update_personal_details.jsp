<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<script type="text/javascript">
	var userId = '<%=session.getAttribute("userId") %>';
	if(userId == 'null') {
		window.location.href = "login";
	}

	function validateForm() {
		validateUpdatePersonalDetailsFields([ 'casteId', 'motherTongueId', 'weightId', 'bodyType', 'complexion', 'physicalStatus',
		                  					'employedIn', 'currencyId', 'monthlyIncome', 'food', 'smoking', 'drinking', 'familyStatus', 'familyType',
		                					'familyValues', 'fathersStatus', 'mothersStatus' ]);
	}
	
	function validateUpdatePersonalDetailsFields(elementIds) {
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
		
		var physicalStatus = '<%=session.getAttribute("physicalStatus")%>';
		$("input[name=physicalStatus1][value=" + physicalStatus + "]").prop('checked', true);
		$("#physicalStatus1").val(physicalStatus);
		
		var employedIn = '<%=session.getAttribute("employedIn")%>';
		$("input[name=employedIn1][value=" + employedIn + "]").prop('checked', true);
		$("#employedIn1").val(employedIn);
		
		var food = '<%=session.getAttribute("food")%>';
		$("input[name=food1][value=" + food + "]").prop('checked', true);
		$("#food1").val(food);
		
		var smoking = '<%=session.getAttribute("smoking")%>';
		$("input[name=smoking1][value=" + smoking + "]").prop('checked', true);
		$("#smoking1").val(smoking);
		
		var drinking = '<%=session.getAttribute("drinking")%>';
		$("input[name=drinking1][value=" + drinking + "]").prop('checked', true);
		$("#drinking1").val(drinking);
		
		var familyStatus = '<%=session.getAttribute("familyStatus")%>';
		$("input[name=familyStatus1][value=" + familyStatus + "]").prop('checked', true);
		$("#familyStatus1").val(familyStatus);
		
		var familyType = '<%=session.getAttribute("familyType")%>';
		$("input[name=familyType1][value=" + familyType + "]").prop('checked', true);
		$("#familyType1").val(familyType);
		
		var familyValues = '<%=session.getAttribute("familyValues")%>';
		$("input[name=familyValues1][value=" + familyValues + "]").prop('checked', true);
		$("#familyValues1").val(familyValues);
		
		$("#religionId").val('<%=session.getAttribute("religionId")%>');
		$("#casteId").val('<%=session.getAttribute("casteId")%>');
		$("#motherTongueId").val('<%=session.getAttribute("motherTongueId")%>');
		$("#weightId").val(<%=session.getAttribute("weightId")%>);
		$("#currencyId").val(<%=session.getAttribute("currencyId")%>);
		$("#fathersStatus").val('<%=session.getAttribute("fathersStatus")%>');
		$("#mothersStatus").val('<%=session.getAttribute("mothersStatus")%>');
		$("#numberOfBrothers").val('<%=session.getAttribute("numberOfBrothers")%>');
// 		$("#country_Id option[value=98]").attr('selected', 'selected');
		$("#brothersMarried").val('<%=session.getAttribute("brothersMarried")%>');
		$("#numberOfSisters").val('<%=session.getAttribute("numberOfSisters")%>');
		$("#sistersMarried").val(<%=session.getAttribute("sistersMarried")%>);
		onLoadCasteByReligionId();
		
	});
	
	function onLoadCasteByReligionId() {
		var id = '<%=session.getAttribute("religionId")%>';
		var casteId = '<%=session.getAttribute("casteId")%>';
		$("#casteId option").remove();
		$("#casteId").append(
				$("<option></option>").attr("value", "-1").text(
						"--- SELECT ---"));
		$.ajax({
			data : {

				religion_Id : id
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
	
	function formSubmit(){

		 $.ajax({
		     url:'update_personal_details_success.action',
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
		window.location.replace("update_personal_details");
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
                    <li class="active"><a href="<%=request.getContextPath()%>/update_personal_details">Update Personal Details</a></li>
                    <li><a href="<%=request.getContextPath()%>/update_partner_preference">Partner Preference</a></li>
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
		<s:form cssClass="form" action="partner_preference" name="form" id="form" method="post">
		<div class="form-innner-wrraper">
		  <div class="form-color-background" style="width: 730px;">
			<div id="error"></div>
			<div class="form-title-row">
				<h1>Update Personal Information</h1>
			</div>
			<c:if test="${personalDetailsBean['id'] == null}">
			<s:hidden name="id" id="id" />
			</c:if>
			<c:if test="${personalDetailsBean['id'] != null}">
			<s:hidden name="id" id="id" value="%{personalDetailsBean['id']}" />
			</c:if>
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
			<label> <span>Religion</span> 
			<select id="religionId" name="religionId" onchange="loadCasteByReligionId(this)" disabled="disabled">
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
			<c:if test="${personalDetailsBean['subCaste'] == null || personalDetailsBean['subCaste'] == ''}">
			<div class="form-row">
			<label> <span>SubCaste</span> 
				<input id="subCaste" name="subCaste" type="text" maxlength="100" onkeyup="checkName(this);" />
			</label>
			</div>
			</c:if>
			<c:if test="${personalDetailsBean['subCaste'] != null && personalDetailsBean['subCaste'] != ''}">
			<div class="form-row">
			<label> <span>SubCaste</span> 
				<input id="subCaste" name="subCaste" type="text" maxlength="100" value="<s:property value="%{personalDetailsBean['subCaste']}" />" onkeyup="checkName(this);" />
			</label>
			</div>
			</c:if>
			<div class="form-row">
			<label> <span>Mother Tongue</span> 
			<select id="motherTongueId" name="motherTongueId">
					<option value="-1">--- SELECT ---</option>
				<s:iterator value="motherTongueMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
			</label>
			</div>
			<div class="form-title-row">
				<h1>Physical Attributes</h1>
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
					<c:if test="${personalDetailsBean['monthlyIncome'] == null}">
					<input id="monthlyIncome" name="monthlyIncome" type="text"  maxlength="14" onkeyup="checkNumeric(this);" style="width: 160px;"/>
					</c:if>
					<c:if test="${personalDetailsBean['monthlyIncome'] != null}">
					<input id="monthlyIncome" name="monthlyIncome" type="text"  value="<s:property value="%{personalDetailsBean['monthlyIncome']}" />" maxlength="14" onkeyup="checkNumeric(this);" style="width: 160px;"/>
					</c:if>
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
				<label> <span>Father's Status</span> 
					<select id="fathersStatus" name="fathersStatus">
							<option value="-1">--- SELECT ---</option>
							<option value="Employed"> Employed </option>
							<option value="Business Man"> Business Man </option>
							<option value="Professional"> Professional </option>
							<option value="Retired"> Retired </option>
							<option value="Not Employed"> Not Employed </option>
							<option value="Passed Away"> Passed Away </option>
					</select>
				</label>
			</div>
			<div class="form-row">
				<label> <span>Mother's Status</span> 
					<select id="mothersStatus" name="mothersStatus">
						<option value="-1">--- SELECT ---</option>
						<option value="Homemaker">Homemaker</option>
						<option value="Employed"> Employed </option>
						<option value="Business Man"> Business Man </option>
						<option value="Professional"> Professional </option>
						<option value="Retired"> Retired </option>
						<option value="Passed Away"> Passed Away </option>
					</select>
				</label>
			</div>
			<div class="form-row">
				<label> <span>No. of Brothers</span> 
					<select id="numberOfBrothers" name="numberOfBrothers">
						<option value="-1">--- SELECT ---</option>
						<option value="None">none</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">5+</option>
					</select>
				</label>
			</div>
			<div class="form-row">
				<label> <span>Brothers Married</span> 
					<select id="brothersMarried" name="brothersMarried">
						<option value="-1">--- SELECT ---</option>
						<option value="None">none</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">5+</option>
					</select>
				</label>
			</div>
			<div class="form-row">
				<label> <span>No. of Sisters</span> 
					<select id="numberOfSisters" name="numberOfSisters">
						<option value="-1">--- SELECT ---</option>
						<option value="None">none</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">5+</option>
					</select>
				</label>
			</div>
			<div class="form-row">
				<label> <span>Sisters Married</span> 
					<select id="sistersMarried" name="sistersMarried">
						<option value="-1">--- SELECT ---</option>
						<option value="None">none</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">5+</option>
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
			<p>Your personal details has been updated successfully.</p>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="closePopup();">Close</a>
		</div>
	</div>
	</div>
</body>
</html>

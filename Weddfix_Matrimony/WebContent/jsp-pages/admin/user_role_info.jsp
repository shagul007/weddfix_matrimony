<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link href="/matrimony/css/jquery.dataTables.min.css" rel="stylesheet">
<script src="/matrimony/js/jquery.dataTables.min.js"></script>
<style type="text/css">
.dataTables_wrapper .dataTables_paginate {
    float: right;
    padding-top: 0.25em;
    text-align: right;
}
</style>
<script type="text/javascript">
	var role = '<%=session.getAttribute("role")%>';
	if (role == 'null') {
		window.location.href = "login";
	}

	if (role != 'ADMIN') {
		window.location.href = "home";
	}
	
	$(document).ready(function(){
	    $('#myTable').dataTable(
				{
// 					bFilter: false,
// 					bInfo: false,
// 					"aoColumns": [{"bSortable": false}, null],
// 					"aaSorting": [{"bSortable": false}],
// 					"bLengthChange": false,
					"aLengthMenu": [5, 10],
			        "iDisplayLength": 5
				}		
		);
	});
	
	function loadPopupForm(id, roleId, statusId) {
		$("#userId").val(id);
		$("#roleId1").val(roleId);
		$("#statusId1").val(statusId);
		$("#roleId option[value="+roleId+"]").attr('selected', 'selected');
		$("#statusId option[value="+statusId+"]").attr('selected', 'selected');
		$("#updatePopup").show();
		$("#success").hide();
		$("#error").hide();
	}
	
	function updateUser() {
		var id = $("#userId").val();
		var role = $("#roleId").val();
		var desc = $("#roleId option:selected").text();
		var status = $("#statusId").val();
		
		$.ajax({
			data : {
				userId : id,
				userRole : role,
				userRoleDesc : desc,
				userStatus : status
			},
			url : "updateUserRoleAndStatus.action",
			dataType : "json",
			success : function(data) {
				$("#error").hide();
				$("#success").show();
				window.location.href = "user_role";
			},
			error : function(xhr, textStatus, errorThrown) {
				// Handle error
				$("#success").hide();
				$("#error").show();
			}
		});
	}
	
	function closePopup() {
		$("#updatePopup").hide();
	}
</script>
</head>
<body>
	<div style="min-height: 428px;">
	<h2 style="text-align: center; color: #531844;">User Role</h2>
	<table border="1" id="myTable" class="table table-striped">
	<thead> 
		<tr>
			<th>Profile ID</th>
			<th>Name</th>
			<th>Email</th>
			<th>Mobile</th>
			<th>Role</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
		</thead>
		<tbody> 
		<s:iterator value="userProfiles">
			<tr>
				<td style="text-align: left;"><s:property value="%{profileId}" /></td>
				<td style="text-align: left;"><s:property value="%{fullName}" /></td>
				<td style="text-align: left;"><s:property value="%{email}" /></td>
				<td style="text-align: left;"><s:property value="%{mobile}" /></td>
				<td style="text-align: left;"><s:property value="%{role}" /></td>
				<td style="text-align: left;"><s:property value="%{status}" /></td>
				<td style="text-align: center;"><a href="#" onclick="loadPopupForm('<s:property value="%{id}" />','<s:property value="%{roleId}" />','<s:property value="%{statusId}" />');" data-toggle="modal" data-target="#popup-form">Update Role or Status</a></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	
	<!-- Start popup modal window -->
	<div id="updatePopup" class="overlay" style="opacity: 1; visibility: visible; display: none;">
	<div class="popup">
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-edit"></i>Update user role or status</h4>
		</div>
		<div class="content">
			<form>
            <input type="hidden" id="userId" />
            <input type="hidden" id="roleId1" />
            <input type="hidden" id="statusId1" />
            <div class="form-group">
              <label style="float: left; width: 25%;">Role</label>
              <select id="roleId" name="roleId">
				<s:iterator value="roleMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
            </div>
            <div class="form-group">
              <label style="float: left; width: 25%;">Status</label>
               <select id="statusId" name="statusId">
				<s:iterator value="statusMap">
					<option value="<s:property value="key" />"><s:property value="value" /></option>
				</s:iterator>
			</select>
            </div>
          </form>
		</div>
		<div class="alerty-action">
			<a class="btn-ok" id="btnUpload" onclick="updateUser();">Update</a>
			<a class="btn-cancel" onclick="closePopup();">Cancel</a>
              <span id="success" style="display: none; color: #a56a96; float: left;">Updated successfully...</span>
              <span id="error" style="display: none; color: red; float: left;">Error while updating...</span>
		</div>
	</div>
	</div>
	<!-- End -->
	</div>
</body>
</html>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="header" />
<%-- <tiles:insertAttribute name="menu" /> --%>
<tiles:insertAttribute name="body" />
<tiles:insertAttribute name="footer" />

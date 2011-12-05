<%@ page import="au.org.intersect.bdcp.UserStore"%>
<%@ page import="au.org.intersect.bdcp.enums.UserRole"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Select User Role</title>
</head>
<body>
<div class="body">
 
<h1>User role and identifier (optional)</h1>

<g:form>
 
  <g:hasErrors bean="${user}">
    <div class="errors"><g:renderErrors bean="${user}" as="list" />
    </div>
  </g:hasErrors>
  <g:if test="${accountStatus == 'Failed'}">
    <p>Please assign a role.</p>
  </g:if>
 
      <g:hiddenField name="username" value="${username}" />
      <g:hiddenField name="givenName" value="${givenName}" />
      <g:hiddenField name="sn" value="${sn}" />
 
      <div class="dialog">
          <label>User role</label>
            <g:select id="selectRole" noSelection="['':'']" from="${UserRole.list()}" keys="${UserRole.listValues()}" name="authority" value="${userInstance?.authority}"></g:select>
            <label><g:message
					code="admin.nlaIdentifier.label" default="NLA Persistence Identifier" /></label>
					<g:textField name="nlaIdentifier" value="${nlaIdentifier}" />
            <h3><g:message
					code="admin.title.label" default="Title" /></h3>
					<g:textField name="title" value="${title}" />
      </div>
 
      <div class="rowBottom"></div>
 
      <div class="buttons">
            <span class="button">
                  <g:actionSubmit name="save" id="select" class="save right" controller="admin" action="create" value="${message(code: 'default.button.select.label', default: 'Select')}" />
            </span>
            <span class="menuButton">
                  <g:link controller="admin" elementId="Back" class="list" action="searchUsers" params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Back</g:link>
            </span>
      </div>
 
</g:form></div>

 
</body>
</html>
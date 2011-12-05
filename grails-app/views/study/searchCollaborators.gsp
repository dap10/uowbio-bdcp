
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <title><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></title>
        
    </head>
    <body>
  <div class="body">
    <h1>Add Collaborator to study ${studyInstance.studyTitle}</h1>
	<g:form controller="study" action="listUsers" method="post" url="${createLink(mapping: 'studyCollaborators', controller:'study', action:'listUsers', params:['studyId': studyInstance.id])}">
	<div class="dialog">
	<table>
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><label for="firstName">First
				Name</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: studyInstance, field: 'studyTitle', 'errors')}">
				<g:textField name="firstName" value="${firstName}" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="surname">Surname</label>
				</td>
				<td valign="top"
					class="value ${hasErrors(bean: studyInstance, field: 'studyTitle', 'errors')}">
				<g:textField name="surname" value="${session?.surname}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="userid">User
				ID</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: studyInstance, field: 'studyTitle', 'errors')}">
				<g:textField name="userid" value="${session?.userid}" /></td>
			</tr>

		</tbody>
	</table>
	</div>
	<span class="button"><g:submitButton name="create" id="search" class="save button" value="Search" /></span>
</g:form> 

<g:render template="collList" />

<div class="buttons">
	<span class="menuButton"><g:link elementId="Back" controller="study" class="list" action="listCollaborators" params="[studyId: studyInstance.id]">Back</g:link></span>
	<span class="button"><g:link elementId="cancel" controller="study" class="list" action="searchCollaborators" params="[studyId: studyInstance.id]">Cancel</g:link></span>	
</div>
</div>
        
	
    </body>
</html>
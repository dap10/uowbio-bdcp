
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <title>Select researcher to create project for</title>
        
    </head>
    <body>
  <div class="body">
    <h1>Select researcher to create project for</h1>
	<g:form controller="project" action="listUsers" method="post">
	<div class="dialog">
	<table>
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><label for="firstName">First
				Name</label></td>
				<td valign="top">
				<g:textField name="firstName" value="${firstName}" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="surname">Surname</label>
				</td>
				<td valign="top">
				<g:textField name="surname" value="${session?.surname}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="userid">User
				ID</label></td>
				<td valign="top">
				<g:textField name="userid" value="${session?.userid}" /></td>
			</tr>

		</tbody>
	</table>
	</div>
	<span class="button"><g:submitButton name="create" id="search" class="save button list" value="Search" /></span>
</g:form> 

<g:render template="userList" />

<div class="buttons">
	<span class="button"><g:link elementId="Back" controller="project" class="list" action="listAll">Back</g:link></span>
	<span class="button"><g:link elementId="cancel" controller="project" class="list" action="searchUsers">Cancel</g:link></span>	
</div>
</div>
        
	
    </body>
</html>
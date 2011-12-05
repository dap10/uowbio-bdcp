<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>New User Account</title>
</head>
<body>
<div class="body">

<h1>New User Account</h1>
<g:form action="searchUsers" method="post">
	<div class="dialog">
	<table>
		<tbody>

			<tr class="prop">
				<td valign="top" class="name"><label for="firstName">First
				Name</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
				<g:textField name="firstName" value="${firstName}" /></td>
			</tr>
			<tr class="prop">
				<td valign="top" class="name"><label for="surname">Surname</label>
				</td>
				<td valign="top"
					class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
				<g:textField name="surname" value="${session?.surname}" /></td>
			</tr>

			<tr class="prop">
				<td valign="top" class="name"><label for="userid">User
				ID</label></td>
				<td valign="top"
					class="value ${hasErrors(bean: projectInstance, field: 'projectTitle', 'errors')}">
				<g:textField name="userid" value="${session?.userid}" /></td>
			</tr>

		</tbody>
	</table>
	</div>

    <span class="button"><g:submitButton	name="create" id="search" class="save button" value="Search" /></span>
  
  </g:form> <g:render template="list" model="['matches': matches]" />

  <div class="buttons">
    <span class="menuButton"><g:link elementId="Back" controller="admin" class="list" action="accountAdmin">Back</g:link></span>
    <span class="button"><g:link elementId="cancel" controller="admin" class="list" action="accountAdmin">Cancel</g:link></span>
  </div>
  
</div>
</body>
</html>
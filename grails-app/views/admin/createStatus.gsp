<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<title>Account Creation ${accountStatus}
</title>
</head>
<body>
<div class="body">

<p>Account Creation ${accountStatus}
</p>

<g:hasErrors bean="${user}">
	<div class="errors"><g:renderErrors bean="${user}" as="list" />
	</div>
</g:hasErrors> <g:if test="${accountStatus == 'Failed'}">
	<p>Please create the account again</p>
	<p>or contact your system administrator for support</p>
	<div class="buttons"><g:link controller="admin"
		elementId="searchUsers" class="create" action="searchUsers"
		params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Return to Search Results</g:link>
	</div>
</g:if> <g:else>
	<g:link controller="admin" elementId="accountAdmin" class="create"
		action="accountAdmin">Return to Account Administration</g:link>
</g:else></div>
</body>
</html>
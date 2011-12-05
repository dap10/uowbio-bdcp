<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="main" />
<g:javascript library="jquery" plugin="jquery" />
<jqui:resources />
<script type="text/javascript">
        $(function() {
            // a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
            $( "#dialog:ui-dialog" ).dialog( "destroy" );
     
            $( "#dialog-confirm" ).dialog({
                  autoOpen: false,
                  resizable: false,
                  height:140,
                  modal: true,
                  buttons: {
                        "Yes": function() {
                              $( this ).dialog( "close" );
                              window.location.href = "${createLink(controller:"admin", action:'searchUsers', params:"[surname:session.surname, firstName: session.firstName, userid: session.userid]")}"
                        },
                        "No": function() {
                              $( this ).dialog( "close" );
                        }
                  }
            });
 
            $('#Cancel').click(function() {
                  $('#dialog-confirm').dialog('open');
                  // prevent the default action, e.g., following a link
                  return false;
            });
                 
      });
            
          </script>
 
<title>Confirm Account Creation</title>
</head>
<body>
 
<div id="dialog-confirm" title="Cancel the creation of a user account?">
<p><span class="ui-icon ui-icon-alert"
      style="float: left; margin: 0 7px 20px 0;"></span>Are you sure?</p>
</div>
<div class="body">
 
<p>Confirm account creation for ${title} ${givenName} ${sn}, with userid: ${username} with role: ${rolename}. (User will receive an
email notification</p>
<p>advising of new account.)</p>
<br />

<div class="rowTop"><g:link controller="admin" class="create"
      elementId="Confirm" class="button" params="[role: role, username: username, nlaIdentifier: nlaIdentifier, title: title]" action="save"
      method="post">Confirm</g:link> <g:link controller="admin"
      class="create" elementId="Cancel" class="button" action="searchUsers"
      params="[surname:session.surname, firstName: session.firstName, userid: session.userid]">Cancel</g:link>
</div>
<div class="rowBottom">
<div class="buttons"><g:link controller="admin" elementId="Back"
      class="create" action="addRole"
      params="[role: role, username: username]">Back</g:link>
</div>
</div>
</div>
</body>
</html>
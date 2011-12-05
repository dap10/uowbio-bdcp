
<%@ page import="au.org.intersect.bdcp.Participant" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <meta name="layout" content="main" />
    <g:javascript library="application" />
    <g:javascript library="jquery" plugin="jquery"/>
 		<jqui:resources />
    <g:set var="entityName" value="${message(code: 'collaborator.label', default: 'Collaborator')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>

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
		                              window.location.href = $(this).data("url");
		                              
		                        },
		                        "No": function() {
		                              $( this ).dialog( "close" );
		                        }
		                  }
		            });

		            $('.myDelete').click(function() {
			  			  $("#dialog-confirm").data("url", $(this).attr("href"));
		                  $('#dialog-confirm').dialog('open');
		                  // prevent the default action, e.g., following a link
		                  return false;
			        });
			        
		      });
            
		</script>
  
  </head>
  <body>
    
    <div id="dialog-confirm" title="Cancel the deletion of Collaborator?">
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure?</p>
		</div>
		
      <div class="body" id="tab6">
        <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <div id="study">
        
        <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab6']}" />
        
		<g:if test="${username}">
			<p>Collaborator <u>${username}</u> added to study <u>${studyInstance.studyTitle}</u></p>
		</g:if>	
        <g:render template="collaborators" />
        </div>
    	</div>
  </body>
</html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'sessionFile.label', default: 'SessionFile')}" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />        
        <title>Browse Files</title>
    </head>
    <body>
        <div class="body">
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <h1>Browse For Files</h1>
            <span><B>Path:</B> ${path}</span>
            <h2 id="warning">Warning: files uploaded cannot be removed</h2>
            <br />
            <g:render template="/shared/uploadApplet" 
            	model='["uploadUrl":"${request.siteUrl}/study/${params.studyId}/session/${params.sessionId}/sessionFile/uploadFiles","destDir":"${params.directory}","redirUrl":"${request.siteUrl}/study/${params.studyId}/sessionFile/upload?done=true"]' />
         
    <div class="buttons">
    <span class="button"><g:link mapping="sessionFileDetails" controller="sessionFile" class="create list" elementId="Cancel" action="list" params="['studyId': params.studyId,'sessionId': params.sessionId]">Cancel</g:link></span>
    </div>
    </div>
    </body>
</html>

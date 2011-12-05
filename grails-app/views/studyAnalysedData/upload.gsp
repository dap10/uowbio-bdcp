
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <title><g:message code="default.list.label" args="[entityName]" /> - Upload Analysed data folder</title>
    </head>
    <body>
    <div class="body" id="tab7"> 
            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
    
    <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab7']}" />

	<div id="tabs-details">
            <h1><g:message code="study.files.analysed.folder.upload.title" args="${[folderName.folder]}"/></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:render template="/shared/uploadApplet" 
            	model='["uploadUrl":"${request.siteUrl}/studyAnalysedData/${params.studyId}/uploadFiles","destDir":"${folderName.folder}","redirUrl":"${request.siteUrl}/studyAnalysedData/${params.studyId}/upload?done=true"]' />
            
	</div>
    <div class="buttons">
            <span class="button"><g:link elementId="cancel" class="list" mapping="studyAnalysedData" controller="studyAnalysedData" action="upload" id="${studyInstance.id}" params="[studyId: studyInstance.id, cancel:true]">Cancel</g:link></span>
    </div>
	</div> 
    </body>
</html>

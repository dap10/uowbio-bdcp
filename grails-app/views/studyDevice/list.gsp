
<%@ page import="au.org.intersect.bdcp.StudyDevice" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDevice.label', default: 'StudyDevice')}" />
        <title><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></title>
    </head>
    <body>
        
        <div class="body" id="tab5"> 
          <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
          <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
          </g:if>
    
            <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab5']}" />
            
            <g:link elementId="Add Device" mapping="studyDeviceDetails" class="create button" action="create" params="[studyId: studyInstance.id]">+ Add Device</g:link>

            <div class="projects">
                <g:each in="${deviceGroupsMapping}" status="i" var="deviceGroupMap">
                	<h2>${deviceGroupMap?.deviceGroup.groupingName}</h2>
	                <ul>
		                <g:each in="${deviceGroupMap?.devices}" status="j" var="deviceInstance">
			                <li>${deviceInstance?.name} - ${deviceInstance.vendor} - ${deviceInstance.modelName} 
			                	<g:link elementId="edit_${j}" mapping="studyDeviceFieldDetails" class="button" action="edit" params="['deviceId':deviceInstance.id,'studyId':params.studyId]">Edit</g:link>
			                	<g:link elementId="forms[${j}]" mapping="studyDeviceManuals" class="button" controller="studyDevice" action="listStudyDeviceManuals" params="[deviceId:deviceInstance.id, studyId: params.studyId]">Manuals</g:link>
			                </li>
		                </g:each>
	                </ul>
                </g:each>
            </div>
        </div>
        
    </body>
</html>

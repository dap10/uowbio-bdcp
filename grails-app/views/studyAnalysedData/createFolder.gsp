
<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <g:javascript library="application" />
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <title><g:message code="default.list.label" args="[entityName]" /> - Create Analysed data folder</title>
    </head>
    <body>
    <div class="body" id="tab7"> 
            <h1><g:message code="default.showTitle.label" args="[studyInstance.studyTitle]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
    
    <g:render template="/study/tabs" model="${[studyInstance:studyInstance, tab:'tab7']}" />

	<div id="tabs-details">
            <h1><g:message code="study.files.analysed.folder.create.title" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${folderName}">
            <div class="errors">
                <g:renderErrors bean="${folderName}" as="list" />
            </div>
            </g:hasErrors>
            
            <g:form mapping="studyAnalysedData" params="[studyId: studyInstance.id]" action="doCreateFolder" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="folder"><g:message code="study.files.analysed.folder.label" default="Folder" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: folderName, field: 'folder', 'errors')}">
                                    <g:textField name="folder" value="${folderName.folder}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" id="save" mapping="studyAnalysedData" params="[studyId: studyInstance.id]" class="save right list" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link elementId="cancel" class="list" mapping="studyAnalysedData" controller="studyAnalysedData" action="list" id="${studyInstance.id}" params="[studyId: studyInstance.id]">Cancel</g:link></span>
                </div>
            </g:form>
	</div>
	</div> 
    </body>
</html>

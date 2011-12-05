

<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:javascript library="jquery" plugin="jquery" />
        <jqui:resources />
        <script type="text/javascript" src="${resource(dir:'jquery-ui-datepicker',file:'jquery.ui.datepicker-en-AU.js')}"></script>
        <title>Results Template Form for ${studyAnalysedData.folder}</title>
    </head>
    <body>
        <div class="body">
            <h1>Results Template Form for ${studyAnalysedData.folder}</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <g:each in="${studyAnalysedDataFields}" status="i" var="studyAnalysedDataField">
            <g:hasErrors bean="${studyAnalysedDataField}">
            <div class="errors">
                <g:renderErrors bean="${studyAnalysedDataField}" as="list" />
            </div>
            </g:hasErrors>
            </g:each>
            
            
            <g:form id="saveForm" method="post"  mapping="studyAnalysedData" params="['studyId':studyInstance.id,'folder':studyAnalysedData.folder]" action="updateData">
                <g:hiddenField name="mode" value="${mode}" />
                <div class="dialog">
                    <g:hiddenField name="fieldsSize" value="${studyAnalysedDataFields.size()}" />
                    <table>
                       <g:each in="${studyAnalysedDataFields}" status="i" var="studyAnalysedDataField" >
                            <g:hiddenField name="studyAnalysedDataFields[${i}].id" value="${studyAnalysedDataField.id}" />
                            <g:hiddenField name="studyAnalysedDataFields[${i}].version" value="${studyAnalysedDataField.version}" />
                            <g:hiddenField name="studyAnalysedDataFields[${i}].resultsDetailsFieldId" value="${studyAnalysedDataField.resultsDetailsField.id}" />
                            <g:set var="templateName" value="${studyAnalysedDataField.resultsDetailsField.fieldType.toString().toLowerCase() + ('view'.equals(mode) ? '_view' : '')}" />
                            <g:render template="/shared/${templateName}" 
                            model = "[fieldName:'studyAnalysedDataFields',defField:'resultsDetailsField',i:i, dataField: studyAnalysedDataField]" />
                        </g:each>
                    </table>
                </div>
                <g:if test="${!'view'.equals(mode)}">
                <div class="buttons">
                    <span class="button"><g:actionSubmit id="update" action="updateData" class="save right list" value="${message(code: 'default.button.'+nextAction+'.label', default: nextAction)}" /></span>
                   <span class="button"><g:link elementId="cancel" class="list" mapping="studyAnalysedData" action="list" params="[studyId:studyInstance.id]">Cancel</g:link></span>
                </div>
                </g:if>
                <g:else>
                <div class="buttons">
                   <span class="button" ><g:link elementId="edit" class="list" style="float:right;" mapping="studyAnalysedData" action="editData" params="[studyId:studyInstance.id,folder:studyAnalysedData.folder,mode:'editOnly']">Edit</g:link></span>
                   <span class="button"><g:link elementId="cancel" class="list" mapping="studyAnalysedData" action="list" params="[studyId:studyInstance.id]">Return</g:link></span>
                </div>
                </g:else>
            </g:form>
        </div>
    </body>
</html>

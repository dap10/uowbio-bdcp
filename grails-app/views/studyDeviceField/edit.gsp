

<%@ page import="au.org.intersect.bdcp.*" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDeviceField.label', default: 'StudyDeviceField')}" />
        <g:javascript library="jquery" plugin="jquery" />
        <jqui:resources />
        <script type="text/javascript" src="${resource(dir:'jquery-ui-datepicker',file:'jquery.ui.datepicker-en-AU.js')}"></script>
        <title>${deviceInstance} Details Template Form</title>
    </head>
    <body>
        <div class="body">
            <h1>${deviceInstance} Details Template Form</h1>
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
            <g:each in="${studyDeviceFields}" status="i" var="studyDeviceFieldInstance">
            <g:hasErrors bean="${studyDeviceFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${studyDeviceFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            </g:each>
            
            
            <g:form id="saveForm" method="post"  mapping="studyDeviceFieldDetails" controller="studyDeviceField" params="['deviceId':deviceInstance.id,'studyId':studyInstance.id]" action="update">
                <div class="dialog">
                    <g:hiddenField name="fieldsSize" value="${studyDeviceFields.size()}" />
                    <table>
                       <g:each in="${studyDeviceFields}" status="i" var="studyDeviceField">
                            <g:hiddenField name="studyDeviceFields[${i}].id" value="${studyDeviceField.id}" />
                            <g:hiddenField name="studyDeviceFields[${i}].version" value="${studyDeviceField.version}" />
                            <g:hiddenField name="studyDeviceFields[${i}].deviceFieldId" value="${studyDeviceField.deviceField.id}" />
                            <g:render template="/shared/${studyDeviceField.deviceField.fieldType.toString().toLowerCase()}"
                             model = "['fieldName':'studyDeviceFields','defField':'deviceField','i':i, 'dataField': studyDeviceField]"/>
                        </g:each>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit id="update" action="update" class="save right list" value="${message(code: 'default.button.'+nextAction+'.label', default: nextAction)}" /></span>
                   <span class="button"><g:link elementId="cancel" class="list" mapping="studyDeviceDetails" controller="studyDevice" action="list" params="[studyId: params.studyId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

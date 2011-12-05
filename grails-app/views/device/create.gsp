

<%@ page import="au.org.intersect.bdcp.Device" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'device.label', default: 'Device')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviceInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form mapping="deviceDetails" controller="device" action="save" params="[deviceGroupId: params.deviceGroupId]" >
                <g:render template="modifyDialog" model= ['body': body()]] />
                <div class="buttons">
                    <span class="button"><g:submitButton id="save" name="save" class="save right list" value="${message(code: 'default.button.create.label', default: 'Save')}" /></span>
                    <span class="button"><g:link mapping="deviceDetails" class="list" controller="device" elementId="cancel" action="list" params="[deviceGroupId: params.deviceGroupId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

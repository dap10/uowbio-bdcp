

<%@ page import="au.org.intersect.bdcp.DeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceField.label', default: 'DeviceField')}" />
        <title>Edit ${deviceInstance.name} Metadata Template Static Field</title>
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources />
        <g:render template="ckeditor" />
    </head>
    <body>
        <div class="body">
            <h1>Edit ${deviceInstance.name} Metadata Template Static text</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviceFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviceFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
             <g:form mapping="deviceFieldDetails" action="update" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldLabel"><g:message code="deviceField.fieldLabel.label" default="Field Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'fieldLabel', 'errors')}">
                                    <g:textField id="label" name="fieldLabel" value="${deviceFieldInstance?.fieldLabel}" />
                                </td>
                            </tr>
                           
                            <g:hiddenField name="fieldType" value="${deviceFieldInstance?.fieldType}" />
                            
                            <tr class="radiobutton" id="staticFieldRow">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="deviceField.staticContent.label" default="Static content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'staticContent', 'errors')}">
                                    <ckeditor:editor name="staticContent">${deviceFieldInstance?.staticContent}</ckeditor:editor>
                                </td>
                            </tr>
                           
                            <g:hiddenField name="device.id" value="${params.deviceId}" />
                            <g:hiddenField name="id" value="${deviceFieldInstance?.id}" />
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="update" class="save" value="${message(code: 'default.button.update.label', default: 'Save')}" /></span>
                    <span class="button"><g:link mapping="deviceFieldDetails" elementId="cancel" action="list" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

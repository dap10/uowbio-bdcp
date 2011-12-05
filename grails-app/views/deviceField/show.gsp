
<%@ page import="au.org.intersect.bdcp.DeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceField.label', default: 'DeviceField')}" />
        <title>Show Field Details</title>
    </head>
    <body>
        <div class="body">
            <h1>Show Field Details</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table id="fieldDetailsTable">
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="deviceField.fieldLabel.label" default="Field Label" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: deviceFieldInstance, field: "fieldLabel")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="deviceField.fieldType.label" default="Field Type" /></td>
                            
                            <td valign="top" class="value"><g:message code="deviceField.fieldType.${deviceFieldInstance?.fieldType?.getName()}" /></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="deviceField.mandatory.label" default="Mandatory" /></td>
                            
                            <td valign="top" class="value">${deviceFieldInstance.mandatoryStatus()}</td>
                            
                        </tr>
                        
                        <tr class="radiobutton">
                            <td valign="top" class="name"><g:message code="deviceField.fieldOptions.label" default="Field Options" /></td>
                            <td valign="top" class="value">
                            <g:each in="${deviceFieldInstance?.fieldOptions.split('\n')}" status="i" var="deviceFieldOption">
                            ${deviceFieldOption}<br /><br />
                            </g:each>
                            </td>
                        </tr>
                        
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${deviceFieldInstance?.id}" />
                    <span class="button"><g:link mapping="deviceFieldDetails" class="list" elementId="Back" action="list" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]">Back</g:link></span>
                </g:form>
            </div>
        </div>
    </body>
</html>

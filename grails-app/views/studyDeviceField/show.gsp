
<%@ page import="au.org.intersect.bdcp.StudyDeviceField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'studyDeviceField.label', default: 'StudyDeviceField')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyDeviceFieldInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.text.label" default="Text" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyDeviceFieldInstance, field: "text")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.textArea.label" default="Text Area" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyDeviceFieldInstance, field: "textArea")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.numeric.label" default="Numeric" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyDeviceFieldInstance, field: "numeric")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.date.label" default="Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${studyDeviceFieldInstance?.date}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.time.label" default="Time" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${studyDeviceFieldInstance?.time}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.radioButtonsOption.label" default="Radio Buttons Option" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyDeviceFieldInstance, field: "radioButtonsOption")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.dropDownOption.label" default="Drop Down Option" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyDeviceFieldInstance, field: "dropDownOption")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.staticContent.label" default="Static Content" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: studyDeviceFieldInstance, field: "staticContent")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.dateCreated.label" default="Date Created" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${studyDeviceFieldInstance?.dateCreated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.deviceField.label" default="Device Field" /></td>
                            
                            <td valign="top" class="value"><g:link controller="deviceField" action="show" id="${studyDeviceFieldInstance?.deviceField?.id}">${studyDeviceFieldInstance?.deviceField?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.lastUpdated.label" default="Last Updated" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${studyDeviceFieldInstance?.lastUpdated}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="studyDeviceField.studyDevice.label" default="Study Device" /></td>
                            
                            <td valign="top" class="value"><g:link controller="studyDevice" action="show" id="${studyDeviceFieldInstance?.studyDevice?.id}">${studyDeviceFieldInstance?.studyDevice?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${studyDeviceFieldInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>

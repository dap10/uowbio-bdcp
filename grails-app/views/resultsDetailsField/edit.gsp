

<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<%@ page import="au.org.intersect.bdcp.enums.FieldType" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <title>Edit Results Details Field</title>
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources />
        <g:render template="/shared/ckeditor" />
    </head>
    <body>
        <div class="body">
            <h1>Edit Results Details Field</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${resultsDetailsFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${resultsDetailsFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${resultsDetailsFieldInstance?.id}" />
                <g:hiddenField name="version" value="${resultsDetailsFieldInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldLabel"><g:message code="resultsDetailsField.fieldLabel.label" default="Field Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldLabel', 'errors')}">
                                    <g:textField id="label" name="fieldLabel" value="${resultsDetailsFieldInstance?.fieldLabel}" />
                                </td>
                            </tr>
                           
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <g:message code="resultsDetailsField.fieldType.label" default="Field Type" />
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldLabel', 'errors')}">
                                    ${message(code:'deviceField.fieldType.'+resultsDetailsFieldInstance?.fieldType.name)}
                                </td>
                            </tr>
                            
                            <g:if test="${FieldType.STATIC_TEXT.equals(resultsDetailsFieldInstance?.fieldType)}">
                            
                            <tr class="radiobutton" id="staticFieldRow">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="resultsDetailsField.staticContent.label" default="Static content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'staticContent', 'errors')}">
                                    <ckeditor:editor name="staticContent">${resultsDetailsFieldInstance?.staticContent}</ckeditor:editor>
                                </td>
                            </tr>
                            
                            </g:if>
                            <g:else>
                            
                            <tr class="radiobutton" id="mandatoryFieldRow">
                                <td valign="top" class="name">
                                    <label for="mandatory"><g:message code="resultsDetailsField.mandatory.label" default="Mandatory" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'mandatory', 'errors')}">
                                    <g:checkBox name="mandatory" value="${resultsDetailsFieldInstance?.mandatory}" />
                                </td>
                            </tr>
                           
                            </g:else>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:link controller="resultsDetailsField" elementId="cancel" action="list">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>


<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <title>Show Results Details Field</title>
    </head>
    <body>
        <div class="body">
            <h1>Show Results Details Field</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table id="fieldDetailsTable">
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="resultsDetailsField.fieldLabel.label" default="Field Label" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean:resultsDetailsFieldInstance, field: "fieldLabel")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="resultsDetailsField.fieldType.label" default="Field Type" /></td>
                            
                            <td valign="top" class="value"><g:message code="resultsDetailsField.fieldType.${resultsDetailsFieldInstance?.fieldType?.getName()}" /></td>
                            
                        </tr>
                        
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="resultsDetailsField.mandatory.label" default="Mandatory" /></td>
                            
                            <td valign="top" class="value">${resultsDetailsFieldInstance?.mandatoryStatus()}</td>
                            
                        </tr>
                        
                        <tr class="radiobutton">
                            <td valign="top" class="name"><g:message code="resultsDetailsField.fieldOptions.label" default="Field Options" /></td>
                            <td valign="top" class="value">
                            <g:each in="${resultsDetailsFieldInstance?.fieldOptions.split('\n')}" status="i" var="resultsDetailsFieldOption">
                            ${resultsDetailsFieldOption}<br /><br />
                            </g:each>
                            </td>
                        </tr>
                        
                        
                        
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${resultsDetailsFieldInstance?.id}" />
                    <span class="button"><g:link controller="resultsDetailsField" class="list" elementId="Back" action="list" >Back</g:link></span>
                </g:form>
            </div>
        </div>
    </body>
</html>



<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <title>Add New Results Details Template Field</title>
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources />
        <g:render template="/shared/ckeditor" />
        <script type="text/javascript">
        $(function() {
            function showOrHideRelationship(options, elementId) {
                var $checked = $("input[name='fieldType']:checked");
                if ($checked.size() > 0 && $.inArray($checked.val(),options) > -1) {
                    $(elementId).show();
                } else {
                    $(elementId).hide();
                }
            }
            function showOrHide() {
            	showOrHideRelationship(['TEXT','TEXTAREA','NUMERIC','DATE','TIME','DROP_DOWN','RADIO_BUTTONS'],'#mandatoryFieldRow');
                showOrHideRelationship(['STATIC_TEXT'],'#staticFieldRow');
                showOrHideRelationship(['DROP_DOWN', 'RADIO_BUTTONS'],'#fieldOptionsRow');
            }
            $("input[name='fieldType']").change(function() {
                showOrHide();
            });
            showOrHide();
        })
        </script>
    </head>
    </head>
    <body>
        <div class="body">
            <h1>Add New Results Details Template Field</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${resultsDetailsFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${resultsDetailsFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fieldLabel"><g:message code="resultsDetailsField.fieldLabel.label" default="Field Label" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldLabel', 'errors')}">
                                    <g:textField name="fieldLabel" value="${resultsDetailsFieldInstance?.fieldLabel}" />
                                </td>
                            </tr>
                        
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldType"><g:message code="resultsDetailsField.fieldType.label" default="Field Type" /></label>
                                </td>
                                <td style="padding:0px 0px;">
                                <table style="border: 0px; padding:0px;">
                                <g:each in="${au.org.intersect.bdcp.enums.FieldType?.listValues().partition(3)}" var="row">
                                <tr>
                                   <g:each in="${row}" var="fieldTypeOption">
                                   <g:set var="checked" value="${fieldTypeOption.equals(resultsDetailsFieldInstance?.fieldType)?'checked=checked':''}"/>
                                   <td><span><input type="radio" value="${fieldTypeOption}" name="fieldType"
                                     ${checked}
                                     >&nbsp;<g:message code="resultsDetailsField.fieldType.${fieldTypeOption.name}" /></span></td>
                                   </g:each>
                                </tr>
                                </g:each>
                                </table>
                                </td>
                            </tr>
                            
                            <tr class="radiobutton" id="fieldOptionsRow">
                                <td valign="top" class="name">
                                    <label for="fieldOptions"><g:message code="resultsDetailsField.fieldLabel.label" default="Field Options" /></label>
                                </td>
                                
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'fieldOptions', 'errors')}">
                                    <p>Enter each option on a new line:</p>
                                    <g:textArea id="fieldOptions" name="fieldOptions" value="${resultsDetailsFieldInstance?.fieldOptions}" />
                                </td>
                            </tr>

                            <tr class="radiobutton" id="staticFieldRow">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="resultsDetailsField.staticContent.label" default="Static content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'staticContent', 'errors')}">
                                    <ckeditor:editor name="staticContent">${resultsDetailsFieldInstance?.staticContent}</ckeditor:editor>
                                </td>
                            </tr>
                            
                            <tr class="radiobutton" id="mandatoryFieldRow">
                                <td valign="top" class="name">
                                    <label for="mandatory"><g:message code="resultsDetailsField.mandatory.label" default="Mandatory" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: resultsDetailsFieldInstance, field: 'mandatory', 'errors')}">
                                    <g:checkBox name="mandatory" value="${resultsDetailsFieldInstance?.mandatory}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" class="save right list" value="${message(code: 'default.button.create.label', default: 'Save')}" /></span>
                     <span class="button"><g:link elementId="cancel" class="list" controller="resultsDetailsField" action="list" >Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

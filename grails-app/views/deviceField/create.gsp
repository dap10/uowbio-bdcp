

<%@ page import="au.org.intersect.bdcp.DeviceField" %>
<%@ page import="au.org.intersect.bdcp.enums.FieldType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'deviceField.label', default: 'DeviceField')}" />
        <title>Add New ${deviceInstance.name} Details Template Field</title>
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources />
        <g:render template="ckeditor" />
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
    <body>
        <div class="body">
            <h1>Add New ${deviceInstance.name} Details Template Field</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${deviceFieldInstance}">
            <div class="errors">
                <g:renderErrors bean="${deviceFieldInstance}" as="list" />
            </div>
            </g:hasErrors>
             <g:form mapping="deviceFieldDetails" action="save" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]" >
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
                           
                            <tr class="radiobutton">
                                <td valign="top" class="name">
                                    <label for="fieldType"><g:message code="deviceField.fieldType.label" default="Field Type" /></label>
                                </td>
                                <td style="padding:0px 0px;">
                                <table style="border: 0px; padding:0px;">
                                <g:each in="${au.org.intersect.bdcp.enums.FieldType?.listValues().partition(3)}" var="row">
                                <tr>
                                   <g:each in="${row}" var="fieldTypeOption">
                                   <g:set var="checked" value="${fieldTypeOption.equals(deviceFieldInstance?.fieldType)?'checked=checked':''}"/>
                                   <td><span><input type="radio" value="${fieldTypeOption}" name="fieldType"
                                     ${checked}
                                     >&nbsp;<g:message code="deviceField.fieldType.${fieldTypeOption.name}" /></span></td>
                                   </g:each>
                                </tr>
                                </g:each>
		                        </table>
		                        </td>
                            </tr>
                        
                            <tr class="radiobutton" id="fieldOptionsRow">
                                <td valign="top" class="name">
                                    <label for="fieldOptions"><g:message code="deviceField.fieldLabel.label" default="Field Options" /></label>
                                </td>
                                
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'fieldOptions', 'errors')}">
                                    <p>Enter each option on a new line:</p>
                                    <g:textArea id="fieldOptions" name="fieldOptions" value="${deviceFieldInstance?.fieldOptions}" />
                                </td>
                            </tr>

                            <tr class="radiobutton" id="staticFieldRow">
                                <td valign="top" class="name">
                                    <label for="staticContent"><g:message code="deviceField.staticContent.label" default="Static content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'staticContent', 'errors')}">
                                    <ckeditor:editor name="staticContent">${deviceFieldInstance?.staticContent}</ckeditor:editor>
                                </td>
                            </tr>
                            
                            <tr class="radiobutton" id="mandatoryFieldRow">
                                <td valign="top" class="name">
                                    <label for="mandatory"><g:message code="deviceField.mandatory.label" default="Mandatory" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceFieldInstance, field: 'mandatory', 'errors')}">
                                    <g:checkBox name="mandatory" value="${deviceFieldInstance?.mandatory}" />
                                </td>
                            </tr>
                           
                            <g:hiddenField name="device.id" value="${params.deviceId}" />
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="save" class="save right list" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                    <span class="button"><g:link mapping="deviceFieldDetails" class="list" elementId="cancel" action="list" params="[deviceGroupId: params.deviceGroupId, deviceId: params.deviceId]">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

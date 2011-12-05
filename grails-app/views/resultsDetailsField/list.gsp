
<%@ page import="au.org.intersect.bdcp.ResultsDetailsField" %>
<%@ page import="au.org.intersect.bdcp.enums.FieldType" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField')}" />
        <g:javascript library="jquery" plugin="jquery"/>
        <jqui:resources /> 
        <script type="text/javascript">
   		$(function(){
   	   		var $dialog = $('#optionsDialog');
   	   	   	$dialog.dialog({autoOpen:false,
   	   	   	   	width:200,
   	   	   	   	height:150,
   	   	   	   	modal:true,
   	   	   	   	});
 	   	   	$('.view_options').click(function(obj) {
 	 	   	   	var opts = $(obj.currentTarget).attr('fieldoptions');
 	 	   	   	$dialog.html('Options:<br/><ul><li>' + opts.split("\\n").join('</li><li>') + '</li></ul>').dialog('open');
 	 	   	   	});
   	   		
   		})
   		</script>       
        <title>Results Details Template</title>
    </head>
    <body>
        <div class="body">
            <h1>Results Details Template</h1>
            <br />
            <g:link elementId="Add Field" class="create" class="button" controller="resultsDetailsField" action="create" >Add Field</g:link>
            <br />
            <br />
            
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:if test="${resultsDetailsFieldInstanceList?.size() > 0}">
            <div class="list">
                <table id="listTable">
                    <thead>
                        <tr>
                            <th>${message(code: 'resultsDetailsField.fieldLabel.label', default: 'Field Label')}</th>
                        
                            <th>${message(code: 'resultsDetailsField.fieldType.label', default: 'Field Type')}</th>
                        
                            <th>${message(code: 'resultsDetailsField.mandatory.label', default: 'Mandatory')}</th>
                            <th>&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${resultsDetailsFieldInstanceList}" status="i" var="resultsDetailsFieldInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td>${fieldValue(bean: resultsDetailsFieldInstance, field: "fieldLabel")}</td>
                        
                            <td><g:message code="resultsDetailsField.fieldType.${resultsDetailsFieldInstance?.fieldType?.getName()}" />
                            
                            <g:if test="${[FieldType.DROP_DOWN, FieldType.RADIO_BUTTONS].contains(resultsDetailsFieldInstance?.fieldType)}"
                            ><a id="show[${i}]" class="view_options" style="cursor:pointer;" 
                                fieldOptions="${resultsDetailsFieldInstance.fieldOptions.replace("\n","\\n")}">(view options)</a>
                            </g:if>
                             </td>
                            
                            <td>${resultsDetailsFieldInstance?.mandatoryStatus()}</td>
                            
                            <td><g:link elementId="edit_${i}" controller="resultsDetailsField" action="edit" class="button" id="${resultsDetailsFieldInstance?.id}"
                             >Edit</g:link
                             ></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            </g:if>
            <br />
            <g:link class="list" class="button" controller="admin" action="resultsAdmin">Return to Results Administration</g:link>
        </div>
        <div id="optionsDialog"><!-- FF3 --></div>
    </body>
</html>

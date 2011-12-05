

<%@ page import="au.org.intersect.bdcp.Study" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'study.label', default: 'Study')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
        <g:javascript library="jquery" plugin="jquery"/>
   		<jqui:resources />
        <script type="text/javascript">
    	function toggleSubmit(obj){
			e=document.getElementById("d"+obj.selectedIndex)
	    		if(obj.selectedIndex == '1')
	        	{
	    	          e.style.display = '';
	
	        	}
	    	    else
	        	{
	    	          document.getElementById("d1").style.display="none"
		    	      document.getElementById("additionalEthicsRequirements").value=""    
	        	}
    		}

    	$(document).ready(function(){
 		   if (${studyInstance?.hasAdditionalEthicsRequirements == "Yes"})
        		   {
					 	document.getElementById("d1").style.display=""
        		   }
    		   else
        		   {
						document.getElementById("d1").style.display="none"
        		   }
    		});
    				
		</script>
    </head>
    <body>
         <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${studyInstance}">
            <div class="errors">
                <g:renderErrors bean="${studyInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${studyInstance?.id}" />
                <g:hiddenField name="version" value="${studyInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="studyTitle"><g:message code="study.studyTitle.label" default="Study Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'studyTitle', 'errors')}">
                                    <g:textField name="studyTitle" value="${studyInstance?.studyTitle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="uowEthicsNumber"><g:message code="study.uowEthicsNumber.label" default="UOW Ethics Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'uowEthicsNumber', 'errors')}">
                                    <g:textField name="uowEthicsNumber" value="${studyInstance?.uowEthicsNumber}" />
                                </td>
                            </tr>
                            
                             <tr class="prop">
                            <td valign="top" class="name">
                                 <label for="hasAdditionalEthicsRequirements">Additional Ethics Requirements</label>
                        	</td>
                        	<td valign="top" class="value">
                        	    <g:select name="hasAdditionalEthicsRequirements" from="${studyInstance.constraints.hasAdditionalEthicsRequirements.inList}" value="${studyInstance?.hasAdditionalEthicsRequirements}" onchange="toggleSubmit(this)"/>
                        	    
                        	</tr>
							
                            <tr class="prop" id="d1" style="display:none">
                                <td valign="top" class="name">
                                    <label for="additionalEthicsRequirements"><g:message code="study.additionalEthicsRequirements.label" default="Additional Ethics Details" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'additionalEthicsRequirements', 'errors')}">
                                    <g:textArea id="additionalEthicsRequirements" name="additionalEthicsRequirements" value="${studyInstance?.additionalEthicsRequirements}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="study.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" value="${studyInstance?.description}" rows="5" cols="40"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="industryPartners"><g:message code="study.industryPartners.label" default="Industry Partners" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'industryPartners', 'errors')}">
                                    <g:textField name="industryPartners" value="${studyInstance?.industryPartners}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="study.keywords.label" default="Keywords" /></label>
                                  <div class="explanation"><g:message code="study.keywords.explanation" default="Separated by comma (,)" /></div>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'keywords', 'errors')}">
                                    <g:textArea name="keywords" value="${studyInstance?.keywords}" rows="5" cols="40"/>
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="collaborators"><g:message code="study.collaborators.label" default="Collaborators" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'collaborators', 'errors')}">
                                    <g:textField name="collaborators" value="${studyInstance?.collaborators}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="startDate"><g:message code="study.startDate.label" default="Start Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'startDate', 'errors')}">
                                    <g:datePicker name="startDate" precision="month" value="${studyInstance?.startDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endDate"><g:message code="study.endDate.label" default="End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'endDate', 'errors')}">
                                    <g:datePicker name="endDate" precision="month" value="${studyInstance?.endDate}"  />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numberOfParticipants"><g:message code="study.numberOfParticipants.label" default="Number of Participants" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'numberOfParticipants', 'errors')}">
                                    <g:textField name="numberOfParticipants" value="${studyInstance?.numberOfParticipants}" />
                                </td>
                            </tr>
                            
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="inclusionExclusionCriteria"><g:message code="study.inclusionExclusionCriteria.label" default="Inclusion Exclusion Criteria" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: studyInstance, field: 'inclusionExclusionCriteria', 'errors')}">
                                    <g:textArea name="inclusionExclusionCriteria" value="${studyInstance?.inclusionExclusionCriteria}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save list right" id="save" action="update" value="${message(code: 'default.button.save.label', default: 'Save')}" /></span>
                	<span class="button"><g:link elementId="cancel" class="list" controller="study" action="show" id="${studyInstance.id}">Cancel</g:link></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

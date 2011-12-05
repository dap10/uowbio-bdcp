
<%@ page import="au.org.intersect.bdcp.Device" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'device.label', default: 'Device')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
      <div class="body">
        <h1><g:message code="default.show.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <div class="dialog">
          <table id="deviceTable">
            <tbody>
            
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.name.label" default="Name" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "name")}</td>
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.description.label" default="Description" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "description")}</td>
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.manufacturer.label" default="Manufacturer" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "manufacturer")}</td>
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.locationOfManufacturer.label" default="Location Of Manufacturer" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "locationOfManufacturer")}</td>       
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.model.label" default="Model" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "modelName")}</td>
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.serialNumber.label" default="Serial Number" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "serialNumber")}</td>
              </tr>
              
	<tr class="prop">
                  <td valign="top" class="name"><g:message code="device.uowAssetNumber.label" default="UOW Asset Number" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "uowAssetNumber")}</td>                      
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.dateOfPurchase.label" default="Date Of Purchase" /></td>
                  <td valign="top" class="value"><g:formatDate format="dd/MM/yyyy" date="${deviceInstance?.dateOfPurchase}" /></td>                      
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.dateOfDelivery.label" default="Date Of Delivery" /></td>
                  <td valign="top" class="value"><g:formatDate format="dd/MM/yyyy" date="${deviceInstance?.dateOfDelivery}" /></td>
                  
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.purchasePrice.label" default="Purchase Price" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "purchasePrice")}</td>
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.vendor.label" default="Vendor" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "vendor")}</td>
              </tr>
          
              <tr class="prop">
                  <td valign="top" class="name"><g:message code="device.fundingSource.label" default="Funding Source" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "fundingSource")}</td>
              </tr>
              
            	<tr class="prop">
                  <td valign="top" class="name"><g:message code="device.maintServiceInfo.label" default="Maintenance/Service Information (Please record the date, details and cost for each entry)" /></td>
                  <td valign="top" class="value">${fieldValue(bean: deviceInstance, field: "maintServiceInfo")}</td>
              </tr>
            
            </tbody>
          </table>
        </div>
        <div class="buttons">
            <span class="menuButton"><g:link class="list" mapping="deviceDetails" controller="device" elementId="Back" action="list" params="[deviceGroupId: params.deviceGroupId]">Back</g:link></span>
        </div>
      </div>
    </body>
</html>

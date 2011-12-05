<div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="device.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" cols="40" rows="5" value="${deviceInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="device.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'description', 'errors')}">
                                    <g:textArea name="description" cols="40" rows="5" value="${deviceInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="manufacturer"><g:message code="device.manufacturer.label" default="Manufacturer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'manufacturer', 'errors')}">
                                    <g:textField name="manufacturer" cols="40" rows="5" value="${deviceInstance?.manufacturer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="locationOfManufacturer"><g:message code="device.locationOfManufacturer.label" default="Location Of Manufacturer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'locationOfManufacturer', 'errors')}">
                                    <g:textField name="locationOfManufacturer" cols="40" rows="5" value="${deviceInstance?.locationOfManufacturer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="modelName"><g:message code="device.model.label" default="Model" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'modelName', 'errors')}">
                                    <g:textField name="modelName" cols="40" rows="5" value="${deviceInstance?.modelName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="serialNumber"><g:message code="device.serialNumber.label" default="Serial Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'serialNumber', 'errors')}">
                                    <g:textField name="serialNumber" cols="40" rows="5" value="${deviceInstance?.serialNumber}" />
                                </td>
                            </tr>
                            
							<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="uowAssetNumber"><g:message code="device.uowAssetNumber.label" default="UOW Asset Number" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'uowAssetNumber', 'errors')}">
                                    <g:textField name="uowAssetNumber" cols="40" rows="5" value="${deviceInstance?.uowAssetNumber}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateOfPurchase"><g:message code="device.dateOfPurchase.label" default="Date Of Purchase" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'dateOfPurchase', 'errors')}">
                                    <g:datePicker name="dateOfPurchase" precision="day" value="${deviceInstance?.dateOfPurchase}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateOfDelivery"><g:message code="device.dateOfDelivery.label" default="Date Of Delivery" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'dateOfDelivery', 'errors')}">
                                    <g:datePicker name="dateOfDelivery" precision="day" value="${deviceInstance?.dateOfDelivery}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="purchasePrice"><g:message code="device.purchasePrice.label" default="Purchase Price" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'purchasePrice', 'errors')}">
                                    <g:textField name="purchasePrice" cols="40" rows="5" value="${deviceInstance?.purchasePrice}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="vendor"><g:message code="device.vendor.label" default="Vendor" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'vendor', 'errors')}">
                                    <g:textField name="vendor" cols="40" rows="5" value="${deviceInstance?.vendor}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="fundingSource"><g:message code="device.fundingSource.label" default="Funding Source" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'fundingSource', 'errors')}">
                                    <g:textField name="fundingSource" cols="40" rows="5" value="${deviceInstance?.fundingSource}" />
                                </td>
                            </tr>
                            
							<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description"><g:message code="device.maintServiceInfo.label" default="Maintenance/Service Information (Please record the date, details and cost for each entry)" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceInstance, field: 'maintServiceInfo', 'errors')}">
                                    <g:textArea name="maintServiceInfo" cols="40" rows="5" value="${deviceInstance?.maintServiceInfo}" />
                                </td>
                            </tr>
                            
                            <g:hiddenField name="deviceGroup.id" value="${params.deviceGroupId}" />
                        </tbody>
                    </table>
                </div>
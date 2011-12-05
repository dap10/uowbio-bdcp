<div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="groupingName"><g:message code="deviceGroup.groupingName.label" default="Grouping Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: deviceGroupInstance, field: 'groupingName', 'errors')}">
                                    <g:textField name="groupingName" value="${deviceGroupInstance?.groupingName}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
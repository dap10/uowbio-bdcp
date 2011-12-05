<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="time">${dataField[defField].fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dataField, field: 'time', 'errors')}">
                                    <joda:timePicker name="${fieldName}[${i}].time" precision="minute" value="${dataField.time}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
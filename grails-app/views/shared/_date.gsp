<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="date">${dataField[defField].fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dataField, field: 'date', 'errors')}">
                                    <g:jqDatePicker name="${fieldName}[${i}].date" value="${dataField.date}" />
                                </td>
                            </tr>
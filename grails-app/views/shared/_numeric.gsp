 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="numeric">${dataField[defField].fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dataField, field: 'numeric', 'errors')}">
                                    <g:textField name="${fieldName}[${i}].numeric" value="${fieldValue(bean: dataField, field: 'numeric')}"/>
                                </td>
                            </tr>
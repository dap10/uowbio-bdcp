 <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="text">${dataField[defField].fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dataField, field: 'text', 'errors')}">
                                    <g:textField name="${fieldName}[${i}].text" cols="40" rows="5" value="${dataField.text}" />
                                </td>
                            </tr>

<tr class="prop">
                                
                                <td valign="top" class="name">
                                    <label for="textArea">${dataField[defField].fieldLabel}</label>
                                </td>
                                
                                <td valign="top" class="value ${hasErrors(bean: dataField, field: 'textArea', 'errors')}">
                                    <g:textArea name="${fieldName}[${i}].textArea" cols="40" rows="5" value="${dataField.textArea}" />
                                </td>
                                
                            </tr>
                            
<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dropDownOption">${dataField[defField].fieldLabel}</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: dataField, field: 'dropDownOption', 'errors')}">
                                    <g:select name="${fieldName}[${i}].dropDownOption" noSelection="['':'']" from="${dataField[defField].getFieldOptionsList()}" value="${dataField.dropDownOption}" />
                                </td>
                            </tr>
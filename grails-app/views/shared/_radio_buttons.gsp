
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="radioButtonsOption">${dataField[defField].fieldLabel}</label>
                                </td>
                                <td style="padding:0px 0px;" class="value ${hasErrors(bean: dataField, field: 'radioButtonsOption', 'errors')}">
                                <table style="padding:0px;" class="radioGroup">
                                <g:each in="${dataField[defField].getFieldOptionsList().partition(3)}" var="optionsRow">
                                <tr>
                                   <g:each in="${optionsRow}" var="fieldOptionInstance">
                                   <g:set var="checked" value="${fieldOptionInstance.trim().equals(dataField.radioButtonsOption?.trim())?'checked=checked':''}"/>
                                   <td><input type="radio" value="${fieldOptionInstance}" name="${fieldName}[${i}].radioButtonsOption"
                                     ${checked}>&nbsp;${fieldOptionInstance}</td>
                                   </g:each>
                                </tr>                                
                                </g:each>
                                </table>
                                </td>
                            </tr>

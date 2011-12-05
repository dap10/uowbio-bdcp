<tr>
                                
     <td valign="top" class="value ${hasErrors(bean: forms[i], field: 'formName', 'errors')}">
          <input type="text" id="forms[${i}].formName" name="forms[${i}].formName" value="${forms[i]?.formName}"/>
     </td>
                            
							
<td valign="top" class="value ${hasErrors(bean: forms[i], field: 'fileName', 'errors')}">
<div class="file_input_div">

  <input type="text" id="forms[${i}].fileName" name="forms[${i}].fileName" class="file_input_textbox" value="${forms[i]?.fileName}" readonly="readonly">
  <input type="button" value="Browse" class="file_input_button" />
  <input type="file" id="form.${i}" name="form.${i}" class="file_input_hidden" onchange="CopyMe(this, 'forms[${i}].fileName');" />
  
  </div></td>
  <g:hiddenField name="forms[${i}].device.id" value="${params.deviceId }" />
                            
</tr>
                            
                            
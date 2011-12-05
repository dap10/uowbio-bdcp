package au.org.intersect.bdcp

import org.joda.time.LocalDate

class JqueryDatePickerTagLib {

def jqDatePicker = {attrs, body ->  
    def out = out 
    def name = attrs.name 
    def id = attrs.id ?: name
    def value = attrs?.value
    def dateValue
    def dayValue
    def monthValue
    def yearValue
    if (value != null)
    {
        if (value instanceof LocalDate)
        {
            dateValue = value.getLocalMillis()
            dayValue = value.getDayOfMonth()
            monthValue = value.getMonthOfYear()
            yearValue = value.getYear()
        }
        else if (value instanceof Date)
        {
            dateValue = value.getTime()
            dayValue = value.toCalendar().get(Calendar.DATE)
            monthValue = value.toCalendar().get(Calendar.MONTH)
            yearValue = value.toCalendar().get(Calendar.YEAR)
        }
        
    }
    
    def jqName = name.replace('.','\\\\.')
    jqName = jqName.replace('[','\\\\[')
    jqName = jqName.replace(']','\\\\]')
    
    //Create date text field and supporting hidden text fields need by grails

    out.println "<input type=\"text\" name=\"${name}\" id=\"${id}\" readonly=\"readonly\"/>"
    out.println "<input type=\"hidden\" name=\"${name}_day\" id=\"${id}_day\" value=\"${dayValue}\" />"
    out.println "<input type=\"hidden\" name=\"${name}_month\" id=\"${id}_month\" value=\"${monthValue}\" />"
    out.println "<input type=\"hidden\" name=\"${name}_year\" id=\"${id}_year\" value=\"${yearValue}\" />"

    //Code to parse selected date into hidden fields required by grails
out.println "<script type=\"text/javascript\"> \$(document).ready(function(){"
out.println "\$(\"#${jqName}\").datepicker({"
out.println "changeMonth: true,"
out.println "changeYear: true,"
out.println "onClose: function(dateText, inst) {"
out.println "if (dateText.length != 0){"
out.println "var dateParts = dateText.split(\"/\");"
out.println "var dateTextFormatted = new Date(dateParts[2], (dateParts[1] - 1) ,dateParts[0]).toString();"
out.println "\$(\"#${jqName}_month\").attr(\"value\",new Date(dateTextFormatted).getMonth() +1);"
out.println "\$(\"#${jqName}_day\").attr(\"value\",new Date(dateTextFormatted).getDate());"
out.println "\$(\"#${jqName}_year\").attr(\"value\",new Date(dateTextFormatted).getFullYear());"
out.println "}"
out.println "else{"
out.println "\$(\"#${jqName}_month\").attr(\"value\",'');"
out.println "\$(\"#${jqName}_day\").attr(\"value\",'');"
out.println "\$(\"#${jqName}_year\").attr(\"value\",'');"
out.println "}"
out.println "}"
out.println "});"
if (dateValue != null)
{
    out.println "\$(\"#${jqName}\").datepicker(\"setDate\", new Date(${dateValue}) ).trigger('change');"
}
out.println "\$(\"#${jqName}\").datepicker( \$.datepicker.regional[ 'en-AU' ] );"
out.println "})</script>"


    }
} 

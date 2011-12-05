package au.org.intersect.bdcp

import au.org.intersect.bdcp.enums.FieldType

class FieldTypeUtils
{
    static validFieldOptions(String val)
    {
        if (val != null && !val.isEmpty())
        {
            val = val.replaceAll("\r", "")
            
            List<String> fieldOptions = Arrays.asList(val.split("\n"))
            if (fieldOptions.size() < 2)
            {
                return ['size.toosmall']
            }
            for (int i = 0; i < fieldOptions.size();i++)
            {
                if (fieldOptions.subList(0,i).contains(fieldOptions.get(i)))
                {
                    return ['unique']
                }
            }
            return true
        }
        else
        {
            return ['nullable']
        }
        
    }
    
    static def getFieldOptionsList(fieldOptions)
    {
        fieldOptions == null ? [] : fieldOptions.tokenize("\n").collect({it.trim()})
    }
	
	static def mandatoryStatus(fieldType, mandatory)
	{
		if (fieldType == FieldType.STATIC_TEXT)
		{
			return "N/A"
		}
		return mandatory.toString().capitalize()
	}

}

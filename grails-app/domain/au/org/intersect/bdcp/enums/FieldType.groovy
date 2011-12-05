package au.org.intersect.bdcp.enums

import au.org.intersect.bdcp.util.TextUtils
import java.text.NumberFormat

public enum FieldType
{
    TEXT('textField.label','text', { fieldDef, val -> validateText(fieldDef, val)}),
	
    TEXTAREA('textArea.label','textArea', { fieldDef, val -> validateText(fieldDef, val)}),
	
    NUMERIC('numeric.label','numeric', { fieldDef, val -> validateNumber(fieldDef, val)}),

    DATE('date.label','date', { fieldDef, val -> validateNotNull(fieldDef, val)}),

    TIME('time.label','time', { fieldDef, val -> validateNotNull(fieldDef, val)}),
	
    DROP_DOWN("dropDown.label",'dropDownOption', { fieldDef, val -> validateOptions(fieldDef, val)}),
	
    RADIO_BUTTONS("radioButtons.label",'radioButtonsOption', { fieldDef, val -> validateOptions(fieldDef, val)}),
	
	STATIC_TEXT('staticText.label', null, null)
    
    String name
	String valueColumn
	final public Closure fieldValidate
    
    FieldType(String name, String valueColumn, Closure fieldValidate)
    {
        this.name = name
		this.valueColumn = valueColumn
		this.fieldValidate = fieldValidate
    }
    
    String getName()
    {
        return this.name
    }
	
	public void updateInstance(domObj, fromInput) {
		if (valueColumn == null)
			return
		domObj."$valueColumn" = fromInput[valueColumn]
	}

	void validateInstance(fieldDescription, domainObject) {
		if (valueColumn == null) {
			return
		}
	    fieldValidate(fieldDescription, domainObject)
	}
    
    static list()
    {
        def items = []
        this.values().each { items.add(it.getName())}
        return items
    }
    
    static listValues()
    {
        def items = []
        this.values().each { items.add(it)}
        return items
    }
	
	static validateText(fieldDef, val) {
			def minValue = 1
			def maxValue = 255
			if (TextUtils.isNotEmpty(val))
			{
				def l = val.length()
				if (maxValue < l || l < minValue)
				{
					def messageProp = l < minValue ? 'size.toosmall' : 'size.toobig'
					def limitVal = l < minValue ? minValue : maxValue
					return [messageProp, fieldDef.fieldLabel, limitVal]
				}
			}
			else if (fieldDef.mandatory)
			{
				return ['nullable', fieldDef.fieldLabel]
			}
	}
	
	static validateNumber(fieldDef, val) {
		def minVal = -0.9E16
		def maxVal = 0.9E16
		if (val!= null)
		{
			if (!val.isNumber())
			{
				return ['not.number', fieldDef.fieldLabel]
			}
			else
			{
				if (val.toBigDecimal() < minVal)
				{
					def nf = NumberFormat.getInstance()
					return ['range.toosmall', fieldDef.fieldLabel, minVal]
				}
				else if (val.toBigDecimal() > maxVal)
				{
					def nf = NumberFormat.getInstance()
					return ['range.toobig', fieldDef.fieldLabel, maxVal]
				}
			}
		}
		else if (fieldDef.mandatory)
		{
			return ['nullable', fieldDef.fieldLabel]
		}

	}
	
	static validateNotNull(fieldDef, val) {
		if (val == null && fieldDef.mandatory)
		{
			return ['nullable', fieldDef.fieldLabel]
		}
	}
	
	static validateOptions(fieldDef, val) {
		if (!TextUtils.isNotEmpty(val) && fieldDef.mandatory)
		{
			return ['nullable', fieldDef.fieldLabel]
		}
		if (TextUtils.isNotEmpty(val)) {
			def options = Arrays.asList(fieldDef.fieldOptions.replaceAll("\r","").split("\n"))
			if (!options.contains(val)) {
				return ['invalid', fieldDef.fieldLabel]
			}
		}
	}
    
}

package au.org.intersect.bdcp.constraints

import java.util.regex.Matcher

class ValidFilenameConstraint
{
    static name = "validFilename"
    static defaultMessageCode = "default.invalid.filename.message"
    static failureCode = "invalid.characters"

    def supports = { type ->
        return type!= null && String.class.isAssignableFrom(type);
    }

    static persistent = false

    def patterns = [ 
		~"[/\\\\?%*:|\"<>]", // special chars
		~"(?i)^\\s*(CON|PRN|AUX|CLOCK[\$]|NUL|COM[0123456789]|LPT[0123456789])[:]?\\s*\$", // reserved names windows
		~"^([.]|[\$])"  // can't start with those (more restrictive, but better)
		]

    def validate = { propertyValue -> 
	return patterns.find({pattern ->
		def matcher = propertyValue =~ pattern
		matcher.find()
		}) == null
    }



}

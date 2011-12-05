package au.org.intersect.bdcp


class Component {
	
	String name
	String description

	static belongsTo = [study:Study]
	
	static hasMany = [sessions:Session]
    static constraints = {
		name(blank:false, size:1..1000, unique:'study', validFilename:true)
		description(blank:false, size:1..1000)
    }
    
    def getSessionsList() {
            return sessions.sort { x,y -> x.name <=> y.name}
    }
}

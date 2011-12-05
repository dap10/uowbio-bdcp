package au.org.intersect.bdcp

import java.io.File
import java.util.concurrent.Executors

import au.org.intersect.bdcp.ldap.LdapUser
import groovy.xml.StreamingMarkupBuilder
import org.hibernate.FetchMode as FM

class RifcsService
{
	def grailsApplication
	
	private static final int THREADS = 3
	private static final String FILEPREFIX_STUDY = "study"
	private def executor = Executors.newFixedThreadPool(THREADS)
	
	private Map common = [
			'originatingSource' : 'http://www.uow.edu.au',
			'@group' : 'University of Wollongong',
			'collection.email.address' : 'biomechanics@uow.edu.au',
			'collection.accessRights' : 'Access on request only',
			'collection.physical.address' : """Biomechanics Research Laboratory
School of Health Sciences
University of Wollongong
Northfields Avenue
Wollongong N.S.W. 2522"""
			]
	
    static transactional = false

    def fileService
	
	def schedulePublishing = 
	{
		ctxPath ->
		def staticCtx = makeContext(ctxPath)
		executor.submit {
			try {
			   studiesToXml(staticCtx)
			   usersToXml(staticCtx)
			   staticsToXml(staticCtx)
			} catch(Exception e) {
				e.printStackTrace(System.err)
			}
		}
	}
	
	private def makeContext =
	{	baseCtxPath ->
		def staticCtx = fileService.createContext( "rifcs")
		fileService.getFileReference( grailsApplication.config.files.rifcs.location, ".").mkdirs()
		return staticCtx
	}
	
	private def makeKey =
	{
		obj ->
		return "oai:au.edu.uow.biomechanics:" + makeName(obj)
	}
	
	private def makeName =
	{
		obj ->
		String cname = obj.getClass().getSimpleName()
		return cname + "~" + obj.id
	}
	
	private def makeFilename =
	{
		obj ->
		return makeName(obj) + ".xml"
	}
	
	
	private def makeRelation =
	{
		obj, type ->
		return obj == null ? null : [key:makeKey(obj), type:type]
	}
	
	private def studiesToXml =
	{
		staticCtx ->
		Study.withTransaction {
			def studies = Study.findAllByPublished(true).findAll {
				File f = fileService.getFileReference( grailsApplication.config.files.rifcs.location, makeFilename(it));
				!f.exists() || f.lastModified() < it.lastUpdated.getTime()
			}
			studies.each {
				def related = [
					makeRelation(it.project.owner, "isManagedBy"),
					makeRelation(StaticMetadataObject.findByShortDescription('bml'), "isOwnedBy")
					]
				objectToXml(staticCtx, createStudyXml, it, related)
			}
		}
	}
	
	private def usersToXml =
	{
		staticCtx ->
		UserStore.withTransaction() {
			def users = UserStore.findAllPublished().findAll {
				File f = fileService.getFileReference( grailsApplication.config.files.rifcs.location, makeFilename(it));
				!f.exists() || f.lastModified() < it.lastUpdated.getTime()
			}
			users.each { user ->
				def criteria = Study.createCriteria()
				def rows = criteria.list {
					join 'project'
					project {
						eq 'owner', user
					}
				}
				def related = rows.collect {
					makeRelation(it,"isManagerOf")
				}
				objectToXml(staticCtx, createUserXml, user, related)
			}
		}
	}
	
	private def staticsToXml =
	{
		staticCtx ->
		staticUOWToXml(staticCtx, StaticMetadataObject.findByShortDescription('uow'))
		staticBMLToXml(staticCtx, StaticMetadataObject.findByShortDescription('bml'))
	}
	
	private def staticUOWToXml =
	{
		staticCtx, staticData ->
		def bml = StaticMetadataObject.findByShortDescription('bml')
		def related = [makeRelation(bml, "hasPart")]
		objectToXml(staticCtx, createStaticXml, staticData, related)
	}

	private def staticBMLToXml =
	{
		staticCtx, staticData ->
		def uow = StaticMetadataObject.findByShortDescription('uow')
		Study.withTransaction() {
			def related = [makeRelation(uow, "isPartOf")]
			def relatedStudies = Study.findAllPublished().collect { study ->
				makeRelation(study,"isOwnerOf")
				}
			related.addAll(relatedStudies)
			objectToXml(staticCtx, createStaticXml, staticData, related)
		}
	}
	
	private def objectToXml =
	{
		staticCtx, Closure closure, object, related ->
		println("Publishing " + makeName(object) + ' @ ' + (new Date()))
		File file = fileService.getFileReference( grailsApplication.config.files.rifcs.location, makeFilename(object))
		def xml = closure(object, related)		
		new FileOutputStream(file) << streamXml(xml)
	}
	
	private def createStaticXml =
	{
		obj, related ->
		def root = new XmlSlurper().parseText(obj.xmlContent)
		def regObjSubtype = root.registryObject.children().find { ["party","collection","activity","service"].contains(it.name()) }
		related.each { relatedAssoc ->
			if (relatedAssoc != null) {
				regObjSubtype.appendNode {
					relatedObject {
						key(relatedAssoc['key'])
						relation('type':relatedAssoc['type'])
					}
				}
			}
		}
		// this closure bridges XmlSlurper to StreamMarkupBilder used for domain objects
		return { binder ->
			mkp.xmlDeclaration()
			mkp.declareNamespace('':'http://ands.org.au/standards/rif-cs/registryObjects')
			mkp.yield root
		}
	}
	
	public def streamXml =
	{
		xml ->
		new StreamingMarkupBuilder().bind(xml)
	}
	
	public def createStudyXml =
	{
		Study study, related ->
		def root = { builder ->
			mkp.xmlDeclaration()
			mkp.declareNamespace('':'http://ands.org.au/standards/rif-cs/registryObjects')
			registryObjects {
				registryObject(group:common['@group']) {
					key(makeKey(study))
					originatingSource(type:"authoritative") { mkp.yield(common['originatingSource'])}
					collection(type:"collection") {
						name(type:"primary") {
							namePart { mkp.yield(study.studyTitle)}
						}
						identifier(type:"local") { mkp.yield(makeName(study)) }
						location {
							address {
								physical(type:"streetAddress") {
									addressPart(type:"text") { mkp.yield(common['collection.physical.address']) }
								}
							}
							address {
								electronic(type:"email") {
									value { mkp.yield(common['collection.email.address']) }
								}
							}
						}
						description(type:"full") { mkp.yield(study.description) }
						description(type:"rights") { mkp.yield(common['collection.accessRights']) }
						related.each { relatedAssoc ->
							if (relatedAssoc != null) {
								relatedObject {
									key(relatedAssoc['key'])
									relation('type':relatedAssoc['type'])
								}
							}
						}
						study.keywords.split(',').each { keyword ->
							  subject(type:'local', 'xml:lang':'en') { mkp.yield(keyword.trim()) }
						}
					}
				}
	
			}
		}
		return root
	}
	
	private def createUserXml =
	{
		UserStore user, related ->
		def builder = new StreamingMarkupBuilder()
		builder.encoding = "UTF-8"
		def ldapUser = LdapUser.find(filter: "(uid=${user.username})")
		def root = {
			mkp.xmlDeclaration()
			mkp.declareNamespace('':'http://ands.org.au/standards/rif-cs/registryObjects')
			registryObjects {
				registryObject(group:common['@group']) {
					key(makeKey(user))
					originatingSource(type:"authoritative") { mkp.yield(common['originatingSource'])}
					party(type:"person") {
						name(type:"primary") {
							namePart(type:"title") { mkp.yield(user.title)}
							namePart(type:"first") { mkp.yield(ldapUser.givenName)}
							namePart(type:"last") { mkp.yield(ldapUser.sn)}
						}
						identifier(type:"local") { mkp.yield(user.username) }
						location {
							address {
								physical(type:"streetAddress") {
									addressPart(type:"text") { mkp.yield(common['collection.physical.address']) }
								}
							}
							address {
								electronic(type:"email") {
									value { mkp.yield(ldapUser.mail) }
								}
							}
						}
						related.each { relatedAssoc ->
							if (relatedAssoc != null) {
								relatedObject {
									key(relatedAssoc['key'])
									relation('type':relatedAssoc['type'])
								}
							}
						}
					}
				}
			}
		}
		return root
	}

}

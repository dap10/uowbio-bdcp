package au.org.intersect.bdcp.constraints

import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.hibernate.Criteria
import org.hibernate.FlushMode
import org.hibernate.Session
import org.hibernate.criterion.Restrictions
import org.springframework.orm.hibernate3.HibernateCallback

class UniqueIgnoreCaseConstraint
{
    static name = "uniqueIgnoreCase"
    static defaultMessageCode = "default.not.uniqueIgnoreCase.message"

    def supports = { type ->
        return type!= null && String.class.isAssignableFrom(type);
    }

    static expectsParams = ['scope']
    
    static persistent = true

    def scope
    def target
    
    def dbCall = { propertyValue, Session session -> 
        session.setFlushMode(FlushMode.MANUAL);
        
        try {
            boolean shouldValidate = true;
            if(propertyValue != null && DomainClassArtefactHandler.isDomainClass(propertyValue.getClass())) {
                shouldValidate = session.contains(propertyValue)
            }
            if(shouldValidate) {
                Criteria criteria = session.createCriteria( constraintOwningClass )
                        .add(Restrictions.eq( constraintPropertyName, propertyValue ).ignoreCase())
                 if( target != null && scope != null && scope != "none") 
                 {
                                criteria.add(Restrictions.eq( scope,
                                        GrailsClassUtils.getPropertyOrStaticPropertyOrFieldValue(target, scope)));
                 }

                return criteria.list()
            } else {
                return null
            }
        } finally {
            session.setFlushMode(FlushMode.AUTO)
        }
    }

    def validate = { propertyValue, targetValue -> 
        scope = params.scope
        target = targetValue
        dbCall.delegate = delegate
        def _v = dbCall.curry(propertyValue) as HibernateCallback
        def result = hibernateTemplate.executeFind(_v)
        if (result.size() == 0)
        {
            return true
        }
        else if(result.size() == 1)
        {
            return result.get(0)?.id == target?.id
        }
        else
        {
            return false
        }
        
    }



}

package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class ResultsDetailsFieldController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def list = {
        [resultsDetailsFieldInstanceList: ResultsDetailsField.list(), resultsDetailsFieldInstanceTotal: ResultsDetailsField.count()]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def create = {
        def resultsDetailsFieldInstance = new ResultsDetailsField()
        resultsDetailsFieldInstance.properties = params
        return [resultsDetailsFieldInstance: resultsDetailsFieldInstance]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def save = {
        def resultsDetailsFieldInstance = new ResultsDetailsField(params)
        if (resultsDetailsFieldInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'resultsDetailsField.label', default: 'Results Details Field'), resultsDetailsFieldInstance.fieldLabel])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [resultsDetailsFieldInstance: resultsDetailsFieldInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def show = {
        def resultsDetailsFieldInstance = ResultsDetailsField.get(params.id)
        if (!resultsDetailsFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField'), params.id])}"
            redirect(action: "list")
        }
        else {
            [resultsDetailsFieldInstance: resultsDetailsFieldInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def edit = {
        def resultsDetailsFieldInstance = ResultsDetailsField.get(params.id)
        if (!resultsDetailsFieldInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultsDetailsField.label', default: 'ResultsDetailsField'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [resultsDetailsFieldInstance: resultsDetailsFieldInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def update = {
        def resultsDetailsFieldInstance = ResultsDetailsField.get(params.id)
        if (resultsDetailsFieldInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (resultsDetailsFieldInstance.version > version) {
                    
                    resultsDetailsFieldInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'resultsDetailsField.label', default: 'Results Details Field')] as Object[], "Another user has updated this Results Details Field while you were editing")
                    render(view: "edit", model: [resultsDetailsFieldInstance: resultsDetailsFieldInstance])
                    return
                }
            }
            resultsDetailsFieldInstance.properties = params
            if (!resultsDetailsFieldInstance.hasErrors() && resultsDetailsFieldInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'resultsDetailsField.label', default: 'Results Details Field'), resultsDetailsFieldInstance.fieldLabel])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [resultsDetailsFieldInstance: resultsDetailsFieldInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultsDetailsField.label', default: 'Results Details Field'), params.id])}"
            redirect(action: "list")
        }
    }
}

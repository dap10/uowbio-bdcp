package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class DeviceGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def list = {
        def DeviceGroupList = DeviceGroup.list(params)
        def sortedDeviceGroup = DeviceGroupList.sort {x,y -> x.groupingName <=> y.groupingName}
        [deviceGroupInstanceList: sortedDeviceGroup, deviceGroupInstanceTotal: DeviceGroup.count()]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def create = {
        def deviceGroupInstance = new DeviceGroup()
        deviceGroupInstance.properties = params
        return [deviceGroupInstance: deviceGroupInstance]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def save = {
        def deviceGroupInstance = new DeviceGroup(params)
        if (deviceGroupInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'deviceGroup.label', default: 'Device Grouping'), deviceGroupInstance.groupingName])}"
            redirect(action: "list")
        }
        else {
            render(view: "create", model: [deviceGroupInstance: deviceGroupInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def show = {
        def deviceGroupInstance = DeviceGroup.get(params.id)
        if (!deviceGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deviceGroupInstance: deviceGroupInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def edit = {
        def deviceGroupInstance = DeviceGroup.get(params.id)
        if (!deviceGroupInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceGroup.label', default: 'DeviceGroup'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [deviceGroupInstance: deviceGroupInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def update = {
        def deviceGroupInstance = DeviceGroup.get(params.id)
        if (deviceGroupInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviceGroupInstance.version > version) {
                    
                    deviceGroupInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'deviceGroup.label', default: 'Device Grouping')] as Object[], "Another user has updated this Device Grouping while you were editing")
                    render(view: "edit", model: [deviceGroupInstance: deviceGroupInstance])
                    return
                }
            }
            deviceGroupInstance.properties = params
            if (!deviceGroupInstance.hasErrors() && deviceGroupInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'deviceGroup.label', default: 'Device Grouping'), deviceGroupInstance.groupingName])}"
                redirect(action: "list")
            }
            else {
                render(view: "edit", model: [deviceGroupInstance: deviceGroupInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'deviceGroup.label', default: 'Device Grouping'), params.groupingName])}"
            redirect(action: "list")
        }
    }

}

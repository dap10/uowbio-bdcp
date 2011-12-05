package au.org.intersect.bdcp

import grails.plugins.springsecurity.Secured

class DeviceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def index = {
        redirect(action: "list", params: params)
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def deviceGroupInstance = DeviceGroup.findById(params.deviceGroupId)
        def sortedDeviceInstanceList = Device.findAllByDeviceGroup(deviceGroupInstance).sort {x,y -> x.name <=> y.name }
        [deviceInstanceList: sortedDeviceInstanceList, deviceInstanceTotal: Device.count(), "deviceGroupInstance": deviceGroupInstance]
    }
	
	@Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
	def manualList = {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def deviceManualFormInstanceList = DeviceManualForm.findAllByDevice(Device.get(params.deviceId))
		[deviceId: params.deviceId, deviceGroupId: params.deviceGroupId, deviceManualFormInstanceList: deviceManualFormInstanceList]
	}

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def create = {
        def deviceInstance = new Device()
        deviceInstance.properties = params
        return [deviceInstance: deviceInstance]
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def save = {
        def deviceInstance = new Device(params)
        if (deviceInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'device.label', default: 'Device'), deviceInstance.name])}"
            redirect( controller: "device", action: "list", mapping: "deviceDetails", params: [deviceGroupId: params.deviceGroupId])
        }
        else {
            render(view: "create", model: [deviceInstance: deviceInstance])
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def show = {
        def deviceInstance = Device.get(params.id)
        if (!deviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'device.label', default: 'Device'), params.id])}"
            redirect(action: "list")
        }
        else {
            [deviceInstance: deviceInstance]
        }
    }

    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def edit = {
        def deviceInstance = Device.get(params.id)
        if (!deviceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'device.label', default: 'Device'), params.name])}"
            redirect(action: "list")
        }
        else {
            return [deviceInstance: deviceInstance]
        }
    }
    
    @Secured(['IS_AUTHENTICATED_REMEMBERED', 'ROLE_LAB_MANAGER', 'ROLE_SYS_ADMIN'])
    def update = {
        def deviceInstance = Device.get(params.id)
        if (deviceInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (deviceInstance.version > version) {
                    
                    deviceInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'device.label', default: 'Device')] as Object[], "Another user has updated this Device while you were editing")
                    render(view: "edit", model: [deviceInstance: deviceInstance])
                    return
                }
            }
            deviceInstance.properties = params
            if (!deviceInstance.hasErrors() && deviceInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'device.label', default: 'Device'), deviceInstance.name])}"
                redirect( controller: "device", action: "list", mapping: "deviceDetails", params: [deviceGroupId: params.deviceGroupId])
            }
            else {
                render(view: "edit", model: [deviceInstance: deviceInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'device.label', default: 'Device'), params.id])}"
            redirect( controller: "device", action: "list", mapping: "deviceDetails", params: [deviceGroupId: params.deviceGroupId])
        }
    }

}

eventCompileStart = { msg ->
    new File("grails-app/views/_version.gsp").text = "git describe --tags --long".execute().text
}

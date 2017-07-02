def test1() {
  println "test1"
}

def getnexusrepo(String url) {
    def nexuslist = []
    def response = httpRequest acceptType: 'APPLICATION_JSON', url: "${url}"
    def jsonout = response.content
    def json = readJSON text: jsonout
    json.data.each {
        repo = it.remoteUri
        repo && nexuslist << it.remoteUri
    }
    return nexuslist
}

def test3(String nexuslist, String repofile) {
    writeFile file: "repofile", text: "${repofile}"
    def repolist = new File("repofile")
    repolist.eachLine { line ->
        println line
    }
}

def test2() {
  println "test2"
}
return this

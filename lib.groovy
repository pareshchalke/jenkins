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

def test3(String repolist, String repofile) {
    def codelist = new File("${repofile}")
    println "${repolist}"
    codelist.eachLine { line ->
        println line
    }
    //println list
}

def test2() {
  println "test2"
}
return this

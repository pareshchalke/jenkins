import groovy.json.*

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
    //println nexuslist
    return nexuslist
}

def generatelist(String[] nexusrepo, String repofile) {
    def newlist = []
    def workspace = env.WORKSPACE
    def repolist = new File("${workspace}/${repofile}")
    def lines = repolist.readLines()
    lines.each { String line ->
        value = nexusrepo.contains(line)
        println value
        if (value == false) {
            newlist << line
        }
    }
    //println newlist
    return newlist
}

@NonCPS
def generatepostdata(String str) {
    remoteUri = str
    repodomain = str.split('/')
    reponame = repodomain[2].replace(".","-")
    //def writer = new StringWriter()
    //def builder = new groovy.json.JsonBuilder()
    def body = [
        displayName: "smoketest",
        description: "forsmoketesting",
        genusTypeId: "type"]
    def builder = new JsonBuilder()
    def root = builder.data {
        repoType("proxy")
        id("$reponame")
        name("$reponame")
        providerRole("org.sonatype.nexus.proxy.repository.Repository")
        exposed(true)
        provider("maven2")
        format("maven2")
        repoPolicy("RELEASE")
        checksumPolicy("WARN")
        remoteStorage(
            remoteStorageUrl: "$remoteUri",
            authentication: null,
            connectionSettings: null
        )
    }
    //def json = new groovy.json.JsonBuilder()
    //def root = json.build { build_number "world"}
    return builder.toString()
    //def json = JsonOutput.toJson(root)
    //return json
}

def createrepo(String[] clist) {
    //println createlist
    clist.each {
        println it
    }
    //def postdata = generatepostdata("http://heelo.com/aseas/")
    //println postdata
    for (i = 0; i <clist.size(); i++) {
        println clist[i]
    }
    clist.each { line ->
        //println line
        //def postdata = generatepostdata("${item}")
        //println postdata
    }
}

return this

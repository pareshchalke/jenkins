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
    nexuslist
}

def generatelist(String[] nexusrepo, String repofile) {
    println nexusrepo
    nexusrepo.each {
        println "++++++++++++++ generatelist method"
        println it
    }
    def newlist = []
    def workspace = env.WORKSPACE
    def repolist = new File("${workspace}/${repofile}")
    def lines = repolist.readLines()
    lines.each { String line ->
        value = nexusrepo.contains(line)
        if (value == false) {
            line && newlist << line
        }
    }
    println newlist
    newlist
}

def createrepo(String[] clist) {
    println "************* createrepo method"
    println clist
    clist.each { items ->
        def postdata = generatepostdata("${items}")
        println postdata
    }
}

@NonCPS
def generatepostdata(String str) {
    remoteUri = str
    repodomain = str.split('/')
    reponame = repodomain[2].replace(".","-")
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
    return builder.toString()
}
return this

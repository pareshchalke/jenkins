import groovy.json.*

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

@NonCPS
def createrepo(String list, String url) {
  postdata = generatepostdata("${it}")
  postresponse = httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', url: "${url}", authentication: 'nexus-admin', httpMode: 'POST', requestBody: postdata
  println('Status: '+postresponse.status)
  println('Response: '+postresponse.content)
}

@NonCPS
def generatepostdata(String str) {
    remoteUri = str
    repodomain = str.split('/')
    reponame = repodomain[2].replace(".","-")
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

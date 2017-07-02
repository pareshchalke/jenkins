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
    /*nexuslist.each {
        println "++++++++ NEXUS LIST"
        println it
        println "++++++++"
    }*/
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
            //println line + "MMMMMMMMMMMMMMMMMMMMM"
            line && newlist << line
        }
        //println newlist
    }
    println newlist
    newlist
}

def createrepo(String[] clist, String msg) {
    println "************* createrepo method"
    println clist
    //println "${msg}"
    //def l = Eval.me(clist)
    //def l = new JsonSlurper().parseText(clist)
    //println l[0] + " ------------------------- GEN LIST"
    clist.each { items ->
        println "clist items :::::::::::::::::::::::::::"
        println items
    }
    //def postdata = generatepostdata("http://heelo.com/aseas/")
    //println postdata
    //clist.each { line ->
        //println line
        //def postdata = generatepostdata("${item}")
        //println postdata
    //}
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



return this

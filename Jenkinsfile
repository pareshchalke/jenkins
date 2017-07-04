node() {
    
    def URL = "http://172.17.0.1:8081/nexus"
    
    def p = new net.cake.nexus()

    def output = p.getnexusrepo(URL)

    genlist = p.generatelist(output as String[],"repolist")
    println "Iterate genlist"
    genlist.each {
        p.createrepo( it, URL)
        p.addrepo( it , URL )
    }
}

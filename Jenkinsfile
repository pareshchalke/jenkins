node() {
    
    def URL = "http://172.17.0.1:8081/nexus"
    def repofile = "repolist"
    def p = new net.cake.nexus()
    def output = p.getnexusrepo(URL)
    checkout scm
    genlist = p.generatelist(output as String[],repofile)
    genlist.each {
        p.createrepo( it, URL)
        p.addrepo( it , URL )
    }
}

@NonCPS

def getpostdata(String url) {
  def getpostdata = { url ->
    remoteUri = url
    repodomain = url.split('/')
    reponame = repodomain[2].replace(".","-")
    def writer = new StringWriter()
    def builder = new groovy.json.JsonBuilder()
    def root = builder.data {
      repoType "proxy"
      id "$reponame"
      name "$reponame"
      providerRole "org.sonatype.nexus.proxy.repository.Repository"
      exposed true
      provider "maven2"
      format "maven2"
      repoPolicy "RELEASE"
      checksumPolicy "WARN"
      remoteStorage {
        remoteStorageUrl "$remoteUri"
        authentication null
        connectionSettings null
      }
    }
    def json = JsonOutput.toJson(root)
    return json
  }
}

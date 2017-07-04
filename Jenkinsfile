pipeline {
    agent any
    stages {
        stage("Get nexus repo") {
            environment {
                nexusurl = 'http://172.17.0.1:8081/nexus'
                nexusgroupname = 'cake-sbt'
                repofilename = 'repolist'
                creds = 'nexus-admin'
            }
            steps {
                nexusCreaterepo(nexusurl,nexusgroupname,repofilename,creds)
            }
        }
    }
}

#!/usr/bin/env groovy

@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
import groovyx.net.http.HTTPBuilder
import groovy.json.JsonOutput

def test1() {
  println "test1"
}

def getnexusrepo(String url) {
  def http = new HTTPBuilder( "${url}" )
  def html = http.get( path : '/nexus/service/local/repositories')
  def output = http.request( GET, TEXT ) { req ->
    uri.path = '/nexus/service/local/repositories'
    headers.Accept = 'application/json'
    response.success = { resp, reader ->
      assert resp.statusLine.statusCode == 200
      return reader.text
    }
    return response.success
  }
}

def test2() {
  println "test2"
}
return this

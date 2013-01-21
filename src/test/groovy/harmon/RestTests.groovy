package harmon

//import org.apache.log4j.Logger
import cucumber.runtime.*;
import groovyx.net.http.RESTClient
import groovy.util.slurpersupport.GPathResult
import groovyx.net.http.URIBuilder
import org.apache.http.client.*

this.metaClass.mixin(cucumber.runtime.groovy.Hooks)
this.metaClass.mixin(cucumber.runtime.groovy.EN)

//def log = Logger.getLogger(this.class)
def host = 'https://api.twitter.com'
def uri = null
def path = ""
def restClient = null
def resp = null
def resourceUrl = ""
def status = 0

Given(~"I access the resource url \"([^\"]*)\"") { String url -> 
        resp = null
        status = null
	
        uri = new URIBuilder(host)	
	path = url 
}

Given(~"I provide parameter \"([^\"]*)\" as \"([^\"]*)\"") { String name, String value ->
	uri.addQueryParam name, value
}

When(~"I retrieve the results") { ->
        try {
		restClient = new RESTClient(uri.toString())
                resp = restClient.get(path: path)
        } catch (HttpResponseException ex) {
		status = ex.getStatusCode()
	}
        if (resp != null) {
		status = resp.status
       		assert ( resp.data instanceof net.sf.json.JSON )
		assert resp.data.status.size() > 0
		parsed = resp.data
        }
}

Then(~"the status code should be (\\d+)") { int expectedStatusCode ->
	assert status == expectedStatusCode
}

Then(~"it should have the field \"(.*)\" containing the value \"(.*)\"") { String field, String value ->
	assert parsed."${field}".toString().equals(value)	
} 

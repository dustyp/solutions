import groovy.xml.XmlUtil
import groovy.xml.StreamingMarkupBuilder

def workspaceDir = System.getProperty("user.dir")
def pomFile = new File("${workspaceDir}/pom.xml")
def pomText = pomFile.text
def project = new XmlSlurper().parseText(pomText)

def env = System.getenv()
def revision = env["SVN_REVISION"]

def pomVersion =  project.version[0].text()
def svnVersion = pomVersion + "." + revision
project.version[0].replaceBody(svnVersion)

def outputBuilder = new StreamingMarkupBuilder()
String result = XmlUtil.serialize(outputBuilder.bind{ 
  mkp.declareNamespace("": "http://maven.apache.org/POM/4.0.0")
  mkp.yield project 
})

pomFile.text = result
""

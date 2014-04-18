import sbt._
import Keys._
import play.Project._
import com.github.play2war.plugin._

object ApplicationBuild extends Build {

  val appName = "hed-editor-services"
  val appVersion = "1.0"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "org.avaje.ebeanorm" % "avaje-ebeanorm-api" % "3.1.1",

    //"xml-apis" % "xml-apis" % "1.4.01" force(),
    "xerces" % "xercesImpl" % "2.10.0" force(),
    "com.hermit-reasoner" % "org.semanticweb.hermit" % "1.3.8.2" force(),
    "org.apache.jena" % "jena-core" % "2.11.0" force(),
    "com.hp.hpl.jena" % "arq" % "2.8.5" force(),
    "org.apache.stanbol" % "org.apache.stanbol.client" % "0.20.0-SHARP" force() exclude("org.slf4j","slf4j-log4j12"),

    javaCore,
    javaEbean,

    "com.clarkparsia" % "Empire-core" % "0.7.3-SHARP" force() exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis"),

    "org.drools" % "drools-core" % "5.6.0.Final" force() exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis"),
    "org.drools" % "drools-compiler" % "5.6.0.Final" force() exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis"),

    "org.drools" % "drools-shapes-utilities" % "0.5.6.Final" exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis") exclude("com.hp.hpl.jena", "jena") exclude("com.hp.hpl.jena", "arq") exclude("thewebsemantic","jenabean") exclude("com.clarkparsia","Empire-core"),
    "org.drools" % "drools-shapes-generator" % "0.5.6.Final" exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis") exclude("thewebsemantic","jenabean") exclude("com.clarkparsia","Empire-core"),


    // "com.google.gdata" % "core" % "1.47.1" exclude("xml-apis","xml-apis"),

    "edu.mayo.cts2.framework" % "model" % "0.8.4" withSources() withJavadoc() exclude("org.slf4j","slf4j-log4j12"),
    "edu.mayo.cts2.framework" % "core" % "0.8.4" withSources() withJavadoc() exclude("org.slf4j","slf4j-log4j12"),

    "sharpc2b" % "sharp-editor" % "0.1-SNAPSHOT"  exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis") exclude( "org.apache.maven.doxia", "doxia-site-renderer" ),
    "sharpc2b" % "import-export" % "0.1-SNAPSHOT"  exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis") exclude( "org.apache.maven.doxia", "doxia-site-renderer" ),
    "sharpc2b" % "editor-core" % "0.1-SNAPSHOT"  exclude("xerces", "xercesImpl") exclude("xml-apis", "xml-apis")  exclude( "org.apache.maven.doxia", "doxia-site-renderer" )

    //    ,

    // Note at website (http://code.google.com/p/rest-assured/wiki/GettingStarted)
    // says put restassured before JUnit to ensure right version of hamcrest
    //
    //    "com.jayway.restassured" % "rest-assured" % "1.8.1",
    //    javaJdbc,
    //    "mysql" % "mysql-connector-java" % "5.1.21",
    //    jdbc
    //    anorm
  )

  //  val libraryDependencies = Seq(
  //
  //  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    testOptions in Test += Tests.Argument("junitxml", "console")
    //    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-s")

    ,
    resolvers += "informatics-releases" at "http://informatics.mayo.edu/maven/content/repositories/releases/",
    resolvers += "informatics-snapshots" at "http://informatics.mayo.edu/maven/content/repositories/snapshots/"
    ,
    resolvers += "atlassian groups" at "https://maven.atlassian.com/content/groups/public/"
    ,
    //    resolvers += "atlassian" at "https://maven.atlassian.com/content/repositories/atlassian-public/"
    resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

  )
  //  Resolvers.
  .settings(Play2WarPlugin.play2WarSettings: _*)
  .settings(Play2WarKeys.servletVersion := "3.0")
}


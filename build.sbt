import play.Project._

name := "play-angular-social-auth"

version := "2.2.1"

libraryDependencies ++= Seq(
  cache,
  "org.webjars" % "underscorejs" % "1.5.1",
  "org.webjars" % "jquery" % "1.10.2",
  "org.webjars" % "bootstrap" % "3.0.0" exclude("org.webjars", "jquery"),
  "org.webjars" % "angularjs" % "1.0.7" exclude("org.webjars", "jquery"),
  "org.webjars" % "requirejs" % "2.1.1",
  "org.webjars" % "webjars-play_2.10" % "2.2.0",
  "com.google.api-client" % "google-api-client" % "1.17.0-rc",
  "com.google.http-client" % "google-http-client-jackson" % "1.17.0-rc",
  "com.restfb" % "restfb" % "1.6.12",
  "org.streum" %% "configrity-core" % "1.0.0",
  "org.jasypt" % "jasypt" % "1.9.1",
  "com.typesafe" %% "scalalogging-slf4j" % "1.0.1"
)

playScalaSettings

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/repo"

// This tells Play to optimize this file and its dependencies
requireJs += "mainProd.js"

// The main config file
// See http://requirejs.org/docs/optimization.html#mainConfigFile
requireJsShim := "build.js"


import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "mclics-jobs2"
  val appVersion = "1.0"

  val adminDeps = Seq(
    javaCore,
    javaJdbc,
    javaJpa,
    cache,
    "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final"
  )

  excludeFilter in (Test, unmanagedJars) := HiddenFileFilter || "*test*.jar"

  val main = play.Project(appName, appVersion, adminDeps)
    .settings(resolvers ++= Seq(
      "Nexus Staging Repo" at "http://git.ml.com:8081/nexus/content/repositories/MLGrailsPlugins",
      "ML Arquitectura" at "http://git.ml.com:8081/nexus/content/groups/Arquitectura/",
      "Jboss repo" at "https://repository.jboss.org/nexus/content/groups/public",
      "Spy Repository" at "http://files.couchbase.com/maven2"))
    .settings(Keys.fork in (Test) := true,
      // uncomment this line to enable remote debugging
      //javaOptions in (Test) += "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=9998",
      javaOptions in (Test) += "-Xms512M",
      javaOptions in (Test) += "-Xmx2048M",
      javaOptions in (Test) += "-Xss1M",
      javaOptions in (Test) += "-XX:MaxPermSize=384M")
}

import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "modelTest"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "commons-validator" % "commons-validator" % "1.3.1",
      "joda-time" % "joda-time" % "2.1",
      "org.springframework.social" % "spring-social-core" % "1.0.1.RELEASE",
      "org.springframework.social" % "spring-social-web" % "1.0.1.RELEASE",
      "org.springframework.social" % "spring-social-facebook" % "1.0.1.RELEASE",
      "org.springframework.security" % "spring-security-crypto" % "3.1.1.RELEASE",
      "org.springframework" % "spring-jdbc" % "3.1.1.RELEASE"
      
      
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}

name := "Promptiosaurus"

version := "1.0"

lazy val `promptiosaurus` = (project in file(".")).enablePlugins(PlayScala, DebianPlugin, BuildInfoPlugin)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
	specs2 % Test,
	"com.typesafe.play" %% "anorm" % "3.0.0-M1",
	"org.webjars" %% "webjars-play" % "2.5.0",
	"mysql" % "mysql-connector-java" % "5.1.36",
	"com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3" exclude("org.webjars", "jquery"),
	"org.webjars" % "bootstrap-sass" % "3.3.1-1",
	"org.webjars" % "jquery" % "2.2.2",
	"org.webjars.bower" % "font-awesome-sass" % "4.6.2",
	"jp.t2v" %% "play2-auth"        % "0.14.2",
	"jp.t2v" %% "play2-auth-social" % "0.14.2",
	"jp.t2v" %% "play2-auth-test"   % "0.14.2" % "test",
	play.sbt.Play.autoImport.cache
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolveFromWebjarsNodeModulesDir := true

typingsFile := Some(baseDirectory.value / "app" / "assets" / "javascripts" / "typings" / "tsd.d.ts")

maintainer in Linux := "Logan Thompson <cobbleopolis@gmail.com>"

packageSummary in Linux := "RandomHaus server"

packageDescription := "A play server to run a RandomHaus instance"

//javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

(testOptions in Test) += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/report")

bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/production.conf""""

val ignoredFiles: Seq[String] = Seq(
	"client.json",
	"client.json.enc"
)

mappings in (Compile, packageBin) ~= { _.filterNot { case (_, s) =>
	ignoredFiles.contains(s)
}}
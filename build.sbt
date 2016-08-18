val appName: String = "Promptiosaurus"

val appVersion: String = "1.0"

val appScalaVersion: String = "2.11.7"

val ignoredFiles: Seq[String] = Seq(
  "client.json",
  "client.json.enc"
)

val baseDependencies = Seq(
	jdbc,
	cache,
	ws,
	specs2 % Test
)

val webJars = Seq (
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "bootstrap-sass" % "3.3.1-1",
  "org.webjars" % "jquery" % "2.2.2",
  "org.webjars.bower" % "font-awesome-sass" % "4.6.2"
)

val auth = Seq(
  "jp.t2v" %% "play2-auth"        % "0.14.2",
  "jp.t2v" %% "play2-auth-social" % "0.14.2",
  "jp.t2v" %% "play2-auth-test"   % "0.14.2" % "test",
  play.sbt.Play.autoImport.cache
)


val database = Seq(
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.scalikejdbc" %% "scalikejdbc"                    % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-config"             % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer"   % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.5.1"

)

val otherDependencies = Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3" exclude("org.webjars", "jquery"),
  "org.scalikejdbc" %% "scalikejdbc-test"   % "2.4.2"   % "test"
)

lazy val `promptiosaurus` = (project in file(".")).enablePlugins(PlayScala, DebianPlugin, BuildInfoPlugin)
  .settings(
		name := appName,
		version := appVersion,
		scalaVersion := appScalaVersion,
    libraryDependencies ++= baseDependencies ++ webJars ++ auth ++ database ++ otherDependencies,
    unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" ),
    resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    resolveFromWebjarsNodeModulesDir := true,
    typingsFile := Some(baseDirectory.value / "app" / "assets" / "javascripts" / "typings" / "tsd.d.ts"),
    maintainer in Linux := "Logan Thompson <cobbleopolis@gmail.com>",
    packageSummary in Linux := "Promptiosaurus server",
    packageDescription := "A play server to run a Promptiosaurus instance",
    (testOptions in Test) += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/report"),
    bashScriptExtraDefines += """addJava "-Dconfig.file=${app_home}/../conf/production.conf"""",
    mappings in (Compile, packageBin) ~= { _.filterNot { case (_, s) =>
      ignoredFiles.contains(s)
    }},
    scalikejdbcSettings
  )
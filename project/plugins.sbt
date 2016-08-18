logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/" 

resolvers += Resolver.url("GitHub repository", url("http://shaggyyeti.github.io/releases"))(Resolver.ivyStylePatterns) 

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")

addSbtPlugin("default" % "sbt-sass" % "0.1.9")

addSbtPlugin("name.de-vries" % "sbt-typescript" % "0.2.6")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

addSbtPlugin("org.scalikejdbc" %% "scalikejdbc-mapper-generator" % "2.4.2")
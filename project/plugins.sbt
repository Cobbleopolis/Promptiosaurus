logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.4")

addSbtPlugin("default" % "sbt-sass" % "0.1.9")

addSbtPlugin("name.de-vries" % "sbt-typescript" % "0.2.6")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.6.1")
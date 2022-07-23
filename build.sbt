name := "bfc"
organization := "cz.cvut.fit.bioop"
version := "1.0"

scalaVersion := "2.13.3"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"

assemblyJarName in assembly := "bfc.jar"
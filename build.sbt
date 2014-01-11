name := "neo4j-scala"

organization := "eu.fakod"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xlint")

libraryDependencies ++= Seq(
  "org.neo4j" % "neo4j-kernel" % "1.9.4",
  "org.neo4j" % "neo4j-lucene-index" % "1.9.4",
  "org.neo4j" % "neo4j-shell" % "1.9.4",
  "org.neo4j" % "neo4j-rest-graphdb" % "1.9",
  "org.neo4j" % "neo4j-cypher" % "1.9.4",
  "org.scala-lang" % "scala-reflect" % "2.10.3",
  "junit" % "junit" % "4.7" % "test",
  "org.specs2" %% "specs2" % "2.3.3" % "test"
)

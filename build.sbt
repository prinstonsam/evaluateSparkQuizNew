import Dependencies._
import SettingsProject._

lazy val quiz_loader = (project in file("."))
  .addCommonSettings
  .addcommonAssemblySettings
  .settings(
    name := "quized-project",
    version := "0.2",
    scalaVersion := VersionsCommon.projectScalaVersion,
    libraryDependencies ++= quizDependencies
  )
  .settings(projectSettings)
package com.googlecode.kanbanik.model
import com.mongodb.casbah.MongoConnection

trait KanbanikEntity {
  
  val conn = MongoConnection()("kanbanik")

  object Coll extends Enumeration {
    val Workflowitems = Value("workflowitems")
    val Boards = Value("boards")
    val Tasks = Value("tasks")
    val Projects = Value("projects")
    val TaskId = Value("taskid")
  }

  def coll(collName: Coll.Value) = {
    conn(collName.toString())
  }

}
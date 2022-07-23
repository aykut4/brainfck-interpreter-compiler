package bfc

import bf._

import scala.collection.mutable
import scala.io.Source


class Parser {
  def parseFileOptimized(fileName: String, program: Program, programCounter: ProgramCounter): Boolean = {
    val source: Source = Source.fromFile(fileName, "UTF-8")
    var index: Int = 0
    val indexStack: mutable.Stack[Int] = new mutable.Stack[Int]()
    while (source.hasNext) {
      val tmp = source.next()

      tmp match {
        case '+' =>
          if (index != 0 && program.text.last.id() == 3) {
            program.text.last.asInstanceOf[Inc].inc()
          } else {
            program.text.append(Inc())
            index += 1
          }

        case '-' =>
          if (index != 0 && program.text.last.id == 4) {
            program.text.last.asInstanceOf[Dec].inc()
          }
          else {
            program.text.append(Dec())
            index += 1
          }

        case '<' =>
          if (index != 0 && program.text.last.id == 1) {
            program.text.last.asInstanceOf[MovL].inc()
          }
          else {
            program.text.append(MovL())
            index += 1
          }

        case '>' =>
          if (index != 0 && program.text.last.id == 2) {
            program.text.last.asInstanceOf[MovR].inc()
          }
          else {
            program.text.append(MovR())
            index += 1
          }

        case '.' =>
          program.text.append(Out)
          index += 1

        case ',' =>
          program.text.append(In)
          index += 1

        case '[' =>
          program.text.append(JumpF(index))
          indexStack.push(index)
          index += 1

        case ']' =>
          program.text.append(JumpB(index))
          if (indexStack.nonEmpty) {
            programCounter.loops.addOne((indexStack.top, index))
            programCounter.loops.addOne((index, indexStack.top))
            indexStack.pop()
          }
          else {
            return false
          }
          index += 1

        case _ =>
      }
    }
    program.text.append(EOF)
    source.close()
    true
  }

  def parseFile(fileName: String, program: Program, programCounter: ProgramCounter): Boolean = {
    val source: Source = Source.fromFile(fileName, "UTF-8")
    var index: Int = -1
    val indexStack: mutable.Stack[Int] = new mutable.Stack[Int]()
    while (source.hasNext) {
      val tmp = source.next()
      index += 1
      tmp match {
        case '+' =>
          program.text.append(Inc())

        case '-' =>
          program.text.append(Dec())

        case '<' =>
          program.text.append(MovL())

        case '>' =>
          program.text.append(MovR())

        case '.' =>
          program.text.append(Out)

        case ',' =>
          program.text.append(In)

        case '[' =>
          program.text.append(JumpF(index))
          indexStack.push(index)

        case ']' =>
          program.text.append(JumpB(index))
          if (indexStack.nonEmpty) {
            programCounter.loops.addOne((indexStack.top, index))
            programCounter.loops.addOne((index, indexStack.top))
            indexStack.pop()
          }
          else {
            return false
          }

        case _ => index -= 1
      }
    }
    program.text.append(EOF)
    source.close()
    true
  }
}
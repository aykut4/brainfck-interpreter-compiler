package bf

import bfc._

object Main {

  def isAllDigits(x: String): Boolean = x forall Character.isDigit

  def printUsage(): Unit = println("Usage: ./bf [-m SIZE] [-n] <input>")

  def main(args: Array[String]): Unit = {

    val interpreter: Interpreter = new Interpreter()
    var size: Int = 32768
    var memWrap: Boolean = true

    args.length match {
      case 1 => interpreter.run(args.apply(0), size, memWrap)
      case 2 =>
                if (args.apply(0) == "-n") {
                  memWrap = false
                  interpreter.run(args.apply(1), size, memWrap)
                }
                else {
                  printUsage()
                }
      case 3 =>
                if (args.apply(0) == "-m" && isAllDigits(args.apply(1))) {
                  size = args.apply(1).toInt
                  interpreter.run(args.apply(2), size, memWrap)
                }
                else {
                  printUsage()
                }
      case 4 =>
                if (args.apply(0) == "-m" && args.apply(2) == "-n" && isAllDigits(args.apply(1))) {
                  memWrap = false
                  size = args.apply(1).toInt
                  interpreter.run(args.apply(3), size, memWrap)
                }
                else {
                  printUsage()
                }
      case _ => printUsage()
    }

  }
}

package bfc

import scala.collection.mutable

class Program(fileName: String, programCounter: ProgramCounter) {
  val text: mutable.ArrayBuffer[Inst] = new mutable.ArrayBuffer[Inst]()

  def parse(optimization: Boolean): Boolean = {
    val parser: Parser = new Parser()
    if (optimization) parser.parseFileOptimized(fileName, program = this, programCounter)
    else parser.parseFile(fileName, program = this, programCounter)
  }
}


class ProgramCounter {
  var loops: mutable.Map[Int, Int] = mutable.Map[Int, Int]()
  var pcr: Int = 0
  var flagEOF: Boolean = false

  def fetch (program: Program): Inst = program.text.apply(pcr)
}

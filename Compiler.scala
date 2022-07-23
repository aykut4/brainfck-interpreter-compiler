package bfc

import java.io.{File, PrintWriter}

class Compiler(fileName: String, optimization: Boolean, outName: String, _target: String) {
  val programCounter: ProgramCounter = new ProgramCounter()
  val program: Program = new Program(fileName, programCounter)
  val assembly: Assembly = new Assembly(_target)
  val target: Target = new Target()

  def compile(): Unit = {
    if (!program.parse(optimization)) {
      println("Syntax error!")
      return
    }
    assembly.init()
    while (!programCounter.flagEOF) {
      val inst: Inst = programCounter.fetch(program)
      inst.compile(target, assembly, programCounter)
    }

    val pw = new PrintWriter(new File(outName+".s"))
    for (x <- assembly.text) {
      pw.write(x)
    }
    pw.close()
  }
}

class CompilerWrapper {
  def compile(fileName: String, optimization: Boolean, outName: String, target: String): Unit = {
    val compiler: Compiler = new Compiler(fileName, optimization, outName, target)
    compiler.compile()
  }
}

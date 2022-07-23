package bf

import bfc._

class Memory(size: Int, memWrap: Boolean) {
  val block: Array[Byte] = new Array[Byte](size)
  var ir: Int = 0
  val memoryWrapping: Boolean = memWrap

  def apply(): Byte = block.apply(ir)
  def update(value: Byte): Unit = block.update(ir, value)
  def incrementRegister(cnt: Int): Unit = {
    if (ir < size-cnt) ir += cnt
  }
  def decrementRegister(cnt: Int): Unit = {
    if (ir >= cnt) ir -= cnt
  }
}


class VM(fileName: String, size: Int, memWrap: Boolean) {
  val memory: Memory = new Memory(size, memWrap)
  val programCounter: ProgramCounter = new ProgramCounter()
  val program: Program = new Program(fileName, programCounter)
  val optimization: Boolean = true

  def run(): Unit = {
    if (!program.parse(optimization)) {
      println("Syntax error!")
      return
    }
    while (!programCounter.flagEOF) {
      val inst: Inst = programCounter.fetch(program)
      inst.execute(memory, programCounter)
    }
  }
}


class Interpreter {
  def run(fileName: String, size: Int, memWrap: Boolean): Unit = {
    val vm: VM = new VM(fileName, size, memWrap)
    vm.run()
  }
}


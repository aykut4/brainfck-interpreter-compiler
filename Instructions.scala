package bfc

import scala.io.StdIn
import bf._


sealed trait Inst {
  def execute (memory: Memory, programCounter: ProgramCounter): Unit
  def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit
  def id(): Int
}


case class MovL() extends Inst {
  var value: Int = 1
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    memory.decrementRegister(value)
    programCounter.pcr += 1
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileMovLGNU(value, assembly, programCounter)
  }
  override def id(): Int = 1
  def inc(): Unit = value += 1
}


case class MovR() extends Inst {
  var value: Int = 1
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    memory.incrementRegister(value)
    programCounter.pcr += 1
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileMovRGNU(value, assembly, programCounter)
  }
  override def id(): Int = 2
  def inc(): Unit = value += 1
}


case class Inc() extends Inst {
  var value: Int = 1
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    if(memory.memoryWrapping) memory.update(((memory.apply()+value) & 0x7F).toByte)
    else memory.update((memory.apply()+value).toByte)
    programCounter.pcr += 1
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileIncGNU(value, assembly, programCounter)
  }
  override def id(): Int = 3
  def inc(): Unit = value += 1

}


case class Dec() extends Inst {
  var value: Int = 1
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    if(memory.memoryWrapping) memory.update(((memory.apply()-value) & 0x7F).toByte)
    else memory.update((memory.apply()-value).toByte)
    programCounter.pcr += 1
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileDecGNU(value, assembly, programCounter)
  }
  override def id(): Int = 4
  def inc(): Unit = value += 1
}


case object Out extends Inst {
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    print(memory.apply().toChar)
    programCounter.pcr += 1
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileOutGNU(assembly, programCounter)
  }
  override def id(): Int = 5
}


case object In extends Inst {
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    memory.update(StdIn.readByte())
    programCounter.pcr += 1
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileInGNU(assembly, programCounter)
  }
  override def id(): Int = 6
}


case class JumpF(index: Int) extends Inst {
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    if (memory.apply() == 0) {
      programCounter.pcr = programCounter.loops.apply(index) + 1
    }
    else {
      programCounter.pcr += 1
    }
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileJumpFGNU(index, assembly, programCounter)
  }
  override def id(): Int = 7
}


case class JumpB(index: Int) extends Inst {
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    if (memory.apply() != 0) {
      programCounter.pcr = programCounter.loops.apply(index)
    }
    else {
      programCounter.pcr += 1
    }
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileJumpBGNU(index, assembly, programCounter)
  }
  override def id(): Int = 8
}

// the last instruction
case object EOF extends Inst {
  override def execute(memory: Memory, programCounter: ProgramCounter): Unit = {
    programCounter.flagEOF = true
  }
  override def compile (target: Target, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    if (assembly.target == "gnu") target.compileEOFGNU(assembly, programCounter)
  }
  override def id(): Int = 9
}

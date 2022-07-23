package bfc

class Target {

  def compileMovLGNU(value: Int, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    var tmp: String = "sub edi, "
    tmp += value.toString
    assembly.write(tmp+"\n")
  }
  def compileMovRGNU(value: Int, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    var tmp: String = "add edi, "
    tmp += value.toString
    assembly.write(tmp+"\n")

  }
  def compileIncGNU(value: Int, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    var tmp: String = "add byte ptr [edi], "
    tmp += value.toString
    assembly.write(tmp+"\n")
  }
  def compileDecGNU(value: Int, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    var tmp: String = "sub byte ptr [edi], "
    tmp += value.toString
    assembly.write(tmp+"\n")
  }
  def compileOutGNU(assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    assembly.write("mov eax,0x04\n")
    assembly.write("xor ebx,ebx\n")
    assembly.write("inc ebx\n")
    assembly.write("mov ecx,edi\n")
    assembly.write("int 0x80\n")
  }
  def compileInGNU(assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    assembly.write("mov eax,0x03\n")
    assembly.write("xor ebx,ebx\n")
    assembly.write("mov ecx,edi\n")
    assembly.write("int 0x80\n")
  }
  def compileJumpFGNU(index: Int, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    val self: String = programCounter.loops.apply(index).toString
    val pair: String = programCounter.loops.apply(programCounter.loops.apply(index)).toString
    assembly.write("_LOOPID"+self+pair+":\n")
    assembly.write("cmp byte ptr [edi],0x00\n")
    assembly.write("je "+"_LOOPID"+pair+self+"\n")
  }
  def compileJumpBGNU(index: Int, assembly: Assembly, programCounter: ProgramCounter): Unit = {
    programCounter.pcr += 1
    val self: String = programCounter.loops.apply(index).toString
    val pair: String = programCounter.loops.apply(programCounter.loops.apply(index)).toString
    assembly.write("_LOOPID"+self+pair+":\n")
    assembly.write("cmp byte ptr [edi],0x00\n")
    assembly.write("jne "+"_LOOPID"+pair+self+"\n")
  }
  def compileEOFGNU(assembly: Assembly, programCounter: ProgramCounter): Unit = {
    assembly.text.append("mov eax,0x01\n")
    assembly.text.append("xor ebx,ebx\n")
    assembly.text.append("int 0x80\n")
    programCounter.flagEOF = true
  }
}
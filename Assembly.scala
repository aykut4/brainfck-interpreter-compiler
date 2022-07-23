package bfc

import scala.collection.mutable

class Assembly(_target: String) {
  val text: mutable.ArrayBuffer[String] = new mutable.ArrayBuffer[String]()
  val target: String = _target

  def init(): Unit = {
    text.append(".intel_syntax noprefix\n")
    text.append(".data\n")
    text.append(".text\n")
    text.append(".global main\n")
    text.append("main:\n")
    text.append("mov eax,0x2d\n")
    text.append("xor ebx,ebx\n")
    text.append("int 0x80\n")
    text.append("add eax,32768\n")
    text.append("mov ebx,eax\n")
    text.append("mov eax,0x2d\n")
    text.append("int 0x80\n")
    text.append("mov edi,eax\n")
    text.append("sub edi,32768\n")
    text.append("xor edx,edx\n")
    text.append("inc edx\n")
  }
  def write(instruction: String): Unit = {
    text.append(instruction)
  }
}
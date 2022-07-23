## Build

The following will compile all code, run tests and if all is good, create a fat-jar with all dependencies inside `target/scala-2.13`.

```
sbt assembly
```
# Description
```
The project was developed on Debian GNU/Linux 9
Necessary technology stack:
    - Linux OS
    - sbt
    - scalatest
    - as (GNU Assembler) (in order to assemble the produced assembly source code to an object file)
    - ld (GNU Linker) (in order to link the produced object file to create elf executable)
    - see below for the correct usage of as and ld


Tests include a BenchmarkTest:
Can be avoided or can be used standalone for performance insight
```
## Use

Once it has been built and the jar file exists, you can use the `bf` and `bfc` commands:
```
Usage: ./bf [-m SIZE] [-n] <input>
! the order of the options matters

./bf examples/hello.bf
./bf -m 65536 examples/hello.bf
./bf -n examples/hello.bf
./bf -m 65536 -n examples/hello.bf
```
or
```
Usage: ./bfc [-O 0] <outputfile> <input>
! the order of the options matters
! for the full optimization omit -O option

./bfc examples/hello.bf
./bfc -O 0 examples/hello.bf
./bfc hello examples/hello.bf
```

## Compiled Assembly

Once the assembly source code is produced by the compiler, you need following commands:
```
as --32 <filename.s> -o <output.o>
ld -m elf_i386 -e main -s <output.o>

./a.out
```

## Benchmark
```
System Specs on which the program was executed:
- Intel Core i7 HM175
- NVidia GeForce GTX 1050Ti 4GB DDR5
- 16GB RAM DDR4 2400MHz


Interpreter execution time for mandelbrot.bf:
- 36.358254815 s

Execution time for comparison from Github Kostya Benchmark Scala for mandelbrot.bf:
- 22.953 s

Execution time of the produced assembly source for mandelbrot.bf (Baseline):
- 3.118487596 s

Execution time of the produced assembly source for mandelbrot.bf (Optimized):
- 1.157009374 s
```

## Optimizations
```
There are two optimizations implemented:
1) Direct Jumps:
    The jump locations are precalculated, so that the program can proceed to the next jump instantly.

2) Instruction Squashing:
    Subsequent instructions of MovL, MovR, Inc, Dec are squashed into one of it's kind with the amount of time
    they were encountered subsequently.
```


## Example programs

In `examples`, there are number of BF programs to experiment with.
Each file starts with a comment about what it does and were does it come from.
 
More programs can be found:
- http://www.hevanet.com/cristofd/brainfuck/
- https://copy.sh/brainfuck/
 

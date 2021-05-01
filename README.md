<!--
  Copyright (c) 2021 Eliah Kagan

  Permission to use, copy, modify, and/or distribute this software for any
  purpose with or without fee is hereby granted.

  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
  REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
  AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
  INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
  LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
  OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
  PERFORMANCE OF THIS SOFTWARE.
-->

# recursive-lambda - effectively recursive anonymous functions

This is a demonstration of some techniques that can be used to achieve the
effect of recursive lambda expressions in languages that don&rsquo;t, strictly
speaking, support them.

This repository contains sample programs in Java, C#, and C++.

## License

The contents of this repository are written by Eliah Kagan and offered under
[**0BSD**](https://spdx.org/licenses/0BSD.html), which is a
[&ldquo;public-domain
equivalent&rdquo;](https://en.wikipedia.org/wiki/Public-domain-equivalent_license)
license. See [`LICENSE`](LICENSE) and/or the commented license headers at the
top of each source code file.

The example programs were written in 2020. Documentation was added in 2021.

## Kinds of techniques demonstrated

Most languages don&rsquo;t directly support lambda expressions. This includes
Java, C#, and C++. But there are workarounds:

- **[Combinatory](https://en.wikipedia.org/wiki/Fixed-point_combinator):** In
  all three of those languages (and many others), a lambda can have an
  additional parameter used to pass it to itself.

  An important implementation detail, in a statically typed language, is what
  type that parameter should be. Casting, recursively defined types, and
  genericity can be useful, individually and sometimes in combination, in
  solving that problem.

  An important interface detail is whether this augmented function object
  should be directly accessible to outside callers, or wrapped inside (or
  captured by) another function object that does not

- **[Capturing](https://en.wikipedia.org/wiki/Closure_(computer_programming)#Lexical_environment):**
  In C# and C++, a lambda can capture a variable that is then modified to
  reference it. But in Java this is not allowed because a variable must be
  effectively final to be eligible for capture.

- **Alternatives:** The goals one might wish to use a recursive lambda to
  achieve can be achieved by writing a class with a single method that calls
  itself. Some languages, particularly C#, support recursive local functions
  (functions defined inside functions).

These concepts are not limited to Java, C#, and C++, but those are the only
languages demonstrated here.

## Notes

A few of the programs in this repository are almost the same as in my [example
of a recursive anonymous class in
Java](https://gist.github.com/EliahKagan/f9afae7460b68a797415fa7be80fd307)
gist. This repository contains C# and C++ examples in addition to more Java
examples than in that gist. (I consider this to supersede that gist.)

The C# programs are supplied as [LINQPad queries](https://www.linqpad.net/) but
the could be modified to not use LINQPad&rsquo;s `Dump` extension method.

The C++ programs use the [Boost Multiprecision
Library](https://github.com/boostorg/multiprecision#boost-multiprecision-library)
and the build configuration system [CMake](https://cmake.org/).

## Bugs/caveats

Caching the result of big-integer exponentiation like this isn&rsquo;t really a
good idea. It is very, very unlikely to lead to an increase in performance, due
to the large constants associated with hash-table lookup. Also, if this were
used long enough to get numerous hits, one would also want to implement cache
invalidation. The implementations here are written for demonstration purposes
and they are not expected to be fast in practice, as written.

The naming convention used here is okay for C# and C++ but not really sensible
for Java. I&rsquo;ve named the Java files the same way, though, so that it is
easier to figure out what a file contains by comparing its name to all the
other filenames. (The names used in [that
gist](https://gist.github.com/EliahKagan/f9afae7460b68a797415fa7be80fd307) are
more idiomatic, since I wasn&rsquo;t trying to make sure they could be easily
compared to names of source files in other languages.)

Even though the manual effort needed to turn the `.linq` files into `.cs` files
is small, I should probably add corresponding `.cs` files to this repository,
since not everyone can run LINQPad.

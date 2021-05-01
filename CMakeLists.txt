# Copyright (c) 2020 Eliah Kagan
#
# Permission to use, copy, modify, and/or distribute this software for any
# purpose with or without fee is hereby granted.
#
# THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
# REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
# AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
# INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
# LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
# OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
# PERFORMANCE OF THIS SOFTWARE.

cmake_minimum_required(VERSION 3.0.0)
project(recursive-lambda VERSION 0.1.0)

include(CTest)
enable_testing()

set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

if(MSVC)
    # cl and clang-cl accept /W4 (but -Weverything will override in clang-cl).
    string(REGEX REPLACE /W[123] /W4 CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS})

    # cl and clang-cl accept /permissive- for stricter conformance.
    add_compile_options(/permissive-)
else()
    # gcc/g++ and clang/clang++ require comparably strict conformance to
    # /permissive- unless -fpermissive is passed. But they accept -pedantic
    # for even more conformance warnings, and -pedantic-errors for errors.
    add_compile_options(-pedantic-errors)
endif()

if(${CMAKE_CXX_COMPILER_ID} STREQUAL "Clang")
    # Clang accepts -Weverything with both clang/clang++ and clang-cl.
    # We will keep all but a few of the warnings that -Weverything enables.
    add_compile_options(
        -Weverything
        -Wno-c++98-compat
        -Wno-c++98-compat-pedantic

        # FIXME: Suppress (just) in Boost header, and just with Clang/MSVC
        # (regular Clang, and also the MSVC compiler, don't need them).
        #-Wno-comma
        #-Wno-deprecated
        #-Wno-documentation
        #-Wno-float-conversion
        #-Wno-float-equal
        #-Wno-missing-noreturn
        #-Wno-old-style-cast
        #-Wno-sign-conversion
        #-Wno-reserved-id-macro
        #-Wno-undef
        #-Wno-zero-as-null-pointer-constant
    )
elseif(MSVC)
    # cl but not clang-cl accepts /Za to reject Microsoft extensions.
    add_compile_options(/Za)

    # cl but not clang-cl accepts /analyze for static analysis (default rules).
    add_compile_options(/analyze)
else()
    # Enable most warnings.
    add_compile_options(-Wall -Wextra)
endif()

find_package(Boost REQUIRED)
add_executable(recursive-lambda-stdfunction recursive-lambda-stdfunction.cpp)
add_executable(recursive-lambda-combinatory recursive-lambda-combinatory.cpp)
target_link_libraries(recursive-lambda-stdfunction PRIVATE Boost::boost)
target_link_libraries(recursive-lambda-combinatory PRIVATE Boost::boost)

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)

cmake_minimum_required(VERSION 3.13)
project(TrieST)

# set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++23 -Wall -Werror -pedantic")

find_package(GTest REQUIRED)

include_directories(include/)

add_executable(main
        src/main.cpp
        src/TrieST.cpp
        src/unit_test.cpp
)
target_link_libraries(main
        GTest::GTest
        GTest::Main
)
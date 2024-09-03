cmake_minimum_required(VERSION 3.5.0)
project(Task-1-C++ VERSION 0.1.0 LANGUAGES C CXX)

add_executable(Task-1-C++ main.cpp CLI.cpp Assets.cpp Intermediaries.cpp Portfolio.cpp SaveHelper.cpp)

project(AssetViewer-C++ VERSION 0.1.0 LANGUAGES C CXX)

add_executable(AssetViewer-C++ ../../Task-3/AssetViewer/src/proto/main2.cpp)

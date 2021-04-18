# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.




add_library(
        DroidDemoJni
        SHARED

        others/hello.c
        others/JniRenderer.c

        jniCalls/assimpActivity.cpp
        jniCalls/gestureClass.cpp
        jniCalls/myGLRenderer.cpp
        nativeCode/common/assimpLoader.cpp
        nativeCode/common/assimpLoader.h
        nativeCode/common/misc.cpp
        nativeCode/common/misc.h
        nativeCode/common/myGLCamera.cpp
        nativeCode/common/myGLCamera.h
        nativeCode/common/myGLFunctions.cpp
        nativeCode/common/myGLFunctions.h
        nativeCode/common/myGLM.h
        nativeCode/common/myJNIHelper.cpp
        nativeCode/common/myJNIHelper.h
        nativeCode/common/myLogger.h
        nativeCode/common/myShader.cpp
        nativeCode/common/myShader.h
        nativeCode/modelAssimp/modelAssimp.cpp
        nativeCode/modelAssimp/modelAssimp.h
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

include_directories(${CMAKE_SOURCE_DIR}/include/glm)

set(shared_libs ../../../../libs)

include_directories(${CMAKE_SOURCE_DIR}/include)
add_library(assimp SHARED IMPORTED )
set_target_properties(assimp PROPERTIES IMPORTED_LOCATION ${shared_libs}/${ANDROID_ABI}/libassimp.so)

include_directories(${CMAKE_SOURCE_DIR}/include)
add_library(opencv_java4 SHARED IMPORTED )
set_target_properties(opencv_java4 PROPERTIES IMPORTED_LOCATION ${shared_libs}/${ANDROID_ABI}/libopencv_java4.so)

target_link_libraries(DroidDemoJni opencv_java4 assimp android EGL GLESv2 GLESv3 m ${log-lib} )
# 集成说明

## 集成Assimp

* 下载Assimp 4.1.0版本：<https://github.com/assimp/assimp/archive/v4.1.0.zip>
* 解压后本地目录为`D:\Code\temp\assimp-4.1.0`
* 将`scripts\android_crosscompile\make_android.bat`拷贝为`scripts\android_crosscompile\make_android_self_defined.bat`
* 将`scripts\android_crosscompile\make_android_self_defined.bat`中的内容进行自定义配置，我的配置如下：

```shell
@echo off

set ASSIMP_PATH=D:\Code\temp\assimp-4.1.0
set CMAKE_PATH="C:\Users\jiangxin\AppData\Local\Android\Sdk\cmake\3.6.4111459\bin\cmake.exe"
set ANDROID_NDK_PATH=C:\Users\jiangxin\AppData\Local\Android\Sdk\ndk\22.0.7026061
set ANDROID_CMAKE_PATH=C:\Users\jiangxin\AppData\Local\Android\Sdk\ndk\22.0.7026061\build\cmake

pushd %ASSIMP_PATH%

rmdir /s /q build
mkdir build
cd build

%CMAKE_PATH% .. ^
  -G"MinGW Makefiles" ^
  -DCMAKE_BUILD_TYPE=Release ^
  -DCMAKE_CXX_FLAGS="%CMAKE_CXX_FLAGS% -Os -Wall" ^
  -DCMAKE_TOOLCHAIN_FILE=%ANDROID_CMAKE_PATH%\android.toolchain.cmake ^
  -DCMAKE_MAKE_PROGRAM=%ANDROID_NDK_PATH%\prebuilt\windows-x86_64\bin\make.exe ^
  -DANDROID_NDK=%ANDROID_NDK_PATH% ^
  -DANDROID_NATIVE_API_LEVEL=android-16 ^
  -DASSIMP_ANDROID_JNIIOSYSTEM=ON ^
  -DANDROID_ABI=arm64-v8a ^
  -DASSIMP_BUILD_ZLIB=ON ^
  -DASSIMP_BUILD_TESTS=OFF ^
  -DASSIMP_BUILD_ASSIMP_TOOLS=OFF ^
  -DASSIMP_NO_EXPORT=ON

%CMAKE_PATH% --build .

popd
```

* 执行如下编译命令：

```shell
cd D:\Code\temp\assimp-4.1.0\scripts\android_crosscompile
.\make_android_self_defined.bat
```

* 将`assimp-4.1.0\build\codelibassimp.so`放到`app\libs\`
* 将`assimp-4.1.0\include`中的目录放到`app\src\main\cpp\include`
* 将`assimp-4.1.0\build\include\assimp\config.h`拷贝到`app\src\main\cpp\assimp-4.1.0\include\assimp`

## 集成OpenCV

OpenCV的继承比较简单，官网提供了Android平台所需的动态库和C++头文件。

* 下载OpenCV 4.5.1版本：<https://cfhcable.dl.sourceforge.net/project/opencvlibrary/4.5.1/opencv-4.5.1-android-sdk.zip>
* 解压后本地目录为`D:\Code\temp\opencv-4.5.1-android-sdk`
* 将`OpenCV-android-sdk\sdk\native\libs\arm64-v8a\libopencv_java4.so`拷贝到`app\libs\`
* 将`OpenCV-android-sdk\sdk\native\jni\include`中的内容拷贝到`app\src\main\cpp\include`

## 集成GLM

* 下载GLM 0.9.9.8版本：<https://github.com/g-truc/glm/archive/refs/tags/0.9.9.8.zip>
* 解压后本地目录为`D:\Code\temp\glm-0.9.9.8`
* 将`glm-0.9.9.8\glm`中的内容拷贝到`app\src\main\cpp\include`

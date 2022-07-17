#指定当前路径
LOCAL_PATH := $(call my-dir)

#CLEAR_VARS变量由Build System提供。并指向一个指定的GNU Makefile，由它负责清理很多LOCAL_xxx.
#例如：LOCAL_MODULE, LOCAL_SRC_FILES, LOCAL_STATIC_LIBRARIES等等。但不清理LOCAL_PATH.
#这个清理动作是必须的，因为所有的编译控制文件由同一个GNU Make解析和执行，其变量是全局的。所以清理后才能避免相互影响。
include $(CLEAR_VARS)

#指定生成动态库名DroidDemoJni，生成的动态库文件libDroidDemoJni.so
LOCAL_MODULE := DroidDemoJni

#指定生成动态库的源文件
LOCAL_SRC_FILES := hello.c JniRenderer.c

LOCAL_LDLIBS := -llog -landroid -lEGL -lGLESv3

#指定生成动态库
include $(BUILD_SHARED_LIBRARY)

# 示例如何在Makefile中打印变量
$(warning " LOCAL_PATH is $(LOCAL_PATH)")

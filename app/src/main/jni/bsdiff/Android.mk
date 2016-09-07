LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := bsdiffjni
LOCAL_SRC_FILES := blocksort.c \
	               bzip2.c \
	               bzip2recover.c	\
	               bzlib.c	\
	               bzlib.h	\
	               bzlib_private.h	\
	               compress.c	\
	               bsdiffjni.c	\
	               bsdiff.h	\
	               bsdiff.c	\
	               bspatch.c	\
	               bspatch.h	\
	               crctable.c	\
	               decompress.c \
	               dlltest.c	\
	               huffman.c	\
	               randtable.c \
	               spewG.c
# LOCAL_ALLOW_UNDEFINED_SYMBOLS := true
LOCAL_LDLIBS:=-L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)
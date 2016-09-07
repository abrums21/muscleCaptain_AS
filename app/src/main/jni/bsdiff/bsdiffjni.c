/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>

#include <sys/types.h>

//#include "./../bzip/include/bzlib.h"
#include "bzlib.h"
#include <err.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include <stdint.h>
#include <string.h>

#include "bsdiff.h"
#include "bspatch.h"

#include <android/log.h>

static int bz2_write(struct bsdiff_stream* stream, const void* buffer, int size) {
	int bz2err;
	BZFILE* bz2;

	bz2 = (BZFILE*) stream->opaque;
	BZ2_bzWrite(&bz2err, bz2, (void*) buffer, size);
	if (bz2err != BZ_STREAM_END && bz2err != BZ_OK)
		return -1;

	return 0;
}

static void offtout(int64_t x, uint8_t *buf) {
	int64_t y;

	if (x < 0)
		y = -x;
	else
		y = x;

	buf[0] = y % 256;
	y -= buf[0];
	y = y / 256;
	buf[1] = y % 256;
	y -= buf[1];
	y = y / 256;
	buf[2] = y % 256;
	y -= buf[2];
	y = y / 256;
	buf[3] = y % 256;
	y -= buf[3];
	y = y / 256;
	buf[4] = y % 256;
	y -= buf[4];
	y = y / 256;
	buf[5] = y % 256;
	y -= buf[5];
	y = y / 256;
	buf[6] = y % 256;
	y -= buf[6];
	y = y / 256;
	buf[7] = y % 256;

	if (x < 0)
		buf[7] |= 0x80;
}

jstring Java_com_chenglong_muscle_util_MyPatchUtil_bsdiff(JNIEnv* env, jobject thiz,
		jstring oldFilePath, jstring newFilePath, jstring patchFilePath) {

	char data[128];
	memset(data, 0, sizeof(data));
	const char* oldFilePathChar = (*env)->GetStringUTFChars(env, oldFilePath,
			0);
	const char* newFilePathChar = (*env)->GetStringUTFChars(env, newFilePath,
			0);
	const char* patchFilePathChar = (*env)->GetStringUTFChars(env,
			patchFilePath, 0);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "oldFilePath = %s",
			oldFilePathChar);
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "newFilePath = %s",
			newFilePathChar);
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "patchFilePath = %s",
			patchFilePathChar);

	int fd;
	int bz2err;
	uint8_t *old, *new;
	off_t oldsize, newsize;
	uint8_t buf[8];
	FILE * pf;
	struct bsdiff_stream stream;
	BZFILE* bz2;

	memset(&bz2, 0, sizeof(bz2));
	stream.malloc = malloc;
	stream.free = free;
	stream.write = bz2_write;

//	char argv[]={'a','b','c','e'};

//	if (argc != 4)
//		errx(1, "usage: %s oldfile newfile patchfile\n", argv[0]);

	/* Allocate oldsize+1 bytes instead of oldsize bytes to ensure
	 that we never try to malloc(0) and get a NULL pointer */
	if (((fd = open(oldFilePathChar, O_RDONLY, 0)) < 0)
			|| ((oldsize = lseek(fd, 0, SEEK_END)) == -1)
			|| ((old = malloc(oldsize + 1)) == NULL)
			|| (lseek(fd, 0, SEEK_SET) != 0)
			|| (read(fd, old, oldsize) != oldsize) || (close(fd) == -1))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"error oldFilePath = %s", oldFilePathChar);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "open oldFilePath %s",
			oldFilePathChar);
//
//	/* Allocate newsize+1 bytes instead of newsize bytes to ensure
//	 that we never try to malloc(0) and get a NULL pointer */
	if (((fd = open(newFilePathChar, O_RDONLY, 0)) < 0)
			|| ((newsize = lseek(fd, 0, SEEK_END)) == -1)
			|| ((new = malloc(newsize + 1)) == NULL)
			|| (lseek(fd, 0, SEEK_SET) != 0)
			|| (read(fd, new, newsize) != newsize) || (close(fd) == -1))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"error newFilePath = %s", oldFilePathChar);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "open newFilePath success");
//
//	/* Create the patch file */
	if ((pf = fopen(patchFilePathChar, "w")) == NULL)
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"error patchFilePath = %s", patchFilePathChar);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
			"open pathFilePath success");
//
//	/* Write header (signature+newsize)*/
	offtout(newsize, buf);
	if (fwrite("ENDSLEY/BSDIFF43", 16, 1, pf) != 1
			|| fwrite(buf, sizeof(buf), 1, pf) != 1)
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"error Failed to write header");

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "offtout(newsize, buf); ");
//
	if (NULL == (bz2 = BZ2_bzWriteOpen(&bz2err, pf, 9, 0, 0)))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"error BZ2_bzWriteOpen, bz2err=%d", bz2err);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "BZ2_bzWriteOpen ");
//
	stream.opaque = bz2;
	if (bsdiff(old, oldsize, new, newsize, &stream))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "bsdiff");

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "bsdiff ");
//
	BZ2_bzWriteClose(&bz2err, bz2, 0, NULL, NULL);
	if (bz2err != BZ_OK)
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"BZ2_bzWriteClose, bz2err=%d", bz2err);

	if (fclose(pf))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "fclose");

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "fclose path file ");

	/* Free the memory we used */
	free(old);
	free(new);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "free file ... ");

	return (*env)->NewStringUTF(env, "Hello from JNI !");
}

static int bz2_read(const struct bspatch_stream* stream, void* buffer, int length)
{
	int n;
	int bz2err;
	BZFILE* bz2;

	bz2 = (BZFILE*)stream->opaque;
	n = BZ2_bzRead(&bz2err, bz2, buffer, length);
	if (n != length)
		return -1;

	return 0;
}

static int64_t offtin(uint8_t *buf)
{
	int64_t y;

	y=buf[7]&0x7F;
	y=y*256;y+=buf[6];
	y=y*256;y+=buf[5];
	y=y*256;y+=buf[4];
	y=y*256;y+=buf[3];
	y=y*256;y+=buf[2];
	y=y*256;y+=buf[1];
	y=y*256;y+=buf[0];

	if(buf[7]&0x80) y=-y;

	return y;
}

jstring Java_com_chenglong_muscle_util_MyPatchUtil_bspatch(JNIEnv* env,
		jobject thiz, jstring oldFilePath, jstring newFilePath, jstring patchFilePath) {

	char data[128];
	memset(data, 0, sizeof(data));
	const char* oldFilePathChar = (*env)->GetStringUTFChars(env, oldFilePath,
			0);
	const char* newFilePathChar = (*env)->GetStringUTFChars(env, newFilePath,
			0);
	const char* patchFilePathChar = (*env)->GetStringUTFChars(env,
			patchFilePath, 0);

	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "oldFilePath = %s",
			oldFilePathChar);
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "newFilePath = %s",
			newFilePathChar);
	__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "patchFilePath = %s",
			patchFilePathChar);

	FILE * f;
	int fd;
	int bz2err;
	uint8_t header[24];
	uint8_t *old, *new;
	int64_t oldsize, newsize;
	BZFILE* bz2;
	struct bspatch_stream stream;

//	if (argc != 4)
//		errx(1, "usage: %s oldfile newfile patchfile\n", argv[0]);

	/* Open patch file */
	if ((f = fopen(patchFilePathChar, "r")) == NULL)
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "error fopen(%s)",
				patchFilePathChar);

	/* Read header */
	if (fread(header, 1, 16, f) != 16) {
		if (feof(f))
			__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
					"error Corrupt patch\n");
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "error fread(%s)",
				patchFilePathChar);
	}

	/* Check for appropriate magic */
	if (memcmp(header, "ENDSLEY/BSDIFF43", 16) != 0)
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"error Corrupt patch\n");

	/* Read lengths from header */
	newsize = offtin(header + 16);
	if (newsize < 0)
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg","error Corrupt patch\n");

	/* Close patch file and re-open it via libbzip2 at the right places */
	if (((fd = open(oldFilePathChar, O_RDONLY, 0)) < 0)
			|| ((oldsize = lseek(fd, 0, SEEK_END)) == -1)
			|| ((old = malloc(oldsize + 1)) == NULL)
			|| (lseek(fd, 0, SEEK_SET) != 0)
			|| (read(fd, old, oldsize) != oldsize) || (close(fd) == -1))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "error %s",
				oldFilePathChar);

	if ((new = malloc(newsize + 1)) == NULL)
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "error null");

	if (NULL == (bz2 = BZ2_bzReadOpen(&bz2err, f, 0, 0, NULL, 0)))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg",
				"error BZ2_bzReadOpen bz2err=%d", bz2err);

	stream.read = bz2_read;
	stream.opaque = bz2;
	if (bspatch(old, oldsize, new, newsize, &stream))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "error bspatch");

	/* Clean up the bzip2 reads */
	BZ2_bzReadClose(&bz2err, bz2);
	fclose(f);

	/* Write the new file */
	if (((fd = open(newFilePathChar, O_CREAT | O_TRUNC | O_WRONLY, 0666)) < 0)
			|| (write(fd, new, newsize) != newsize) || (close(fd) == -1))
		__android_log_print(ANDROID_LOG_INFO, "JNIMsg", "error %s",
				newFilePathChar);

	free(new);
	free(old);

	return (*env)->NewStringUTF(env, "Hello from JNI !");
}

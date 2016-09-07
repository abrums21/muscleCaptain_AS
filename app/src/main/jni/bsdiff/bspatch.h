#ifndef BSPATCH_H
#define BSPATCH_H

struct bspatch_stream
{
	void* opaque;
	int (*read)(const struct bspatch_stream* stream, void* buffer, int length);
};

int bspatch(const uint8_t* old, int64_t oldsize, uint8_t* newt, int64_t newsize, struct bspatch_stream* stream);

#endif /* HELLONEON_INTRINSICS_H */

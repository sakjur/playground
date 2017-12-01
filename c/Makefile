CC=clang
CFLAGS=-Wall -Wextra -std=c99 -O3
LDFLAGS=-lm

all: deciword.out

deciword.out: deciword.c
	$(CC) $(LDFLAGS) $(CFLAGS) $< -o $@

clean:
	rm deciword.out

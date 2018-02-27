CC=clang
CFLAGS=-Wall -Wextra -std=c99 -O3

all: deciword.out ssh-generate.out

deciword.out: deciword.c
	$(CC) -lm $(CFLAGS) $< -o $@

ssh-generate.out: ssh-generate.c
	$(CC) -lssh $(CFLAGS) $< -o $@

clean:
	rm deciword.out

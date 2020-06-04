CC = cc
SRC = cluster.c common.c pool.c sync.c
OBJ = $(patsubst %.c, target/%.o, $(SRC))

default: target/classes/linux-x86_64/librados-jni.so

clean:
	rm -rf target

target/classes/linux-x86_64:
	mkdir -p target/classes/linux-x86_64

target/%.o : ../native/src/%.c target/classes/linux-x86_64
	$(CC) $(CFLAGS) -Werror -Wall -Wmissing-prototypes -Wstrict-prototypes -Wmissing-declarations -Wpointer-arith -std=c9x -Wdeclaration-after-statement -Wformat -Wformat-security -Wunused -Wno-unknown-pragmas -fPIC -c $< -o $@ -I../native/include -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux

target/classes/linux-x86_64/librados-jni.so: $(OBJ)
	$(CC) $(CFLAGS) -shared $(OBJ) -o $@ $(LDFLAGS) -Wl,--no-as-needed -ldl

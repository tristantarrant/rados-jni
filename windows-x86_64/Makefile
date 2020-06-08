.SUFFIXES: .dll

all: target\classes\win-x86_64\rados-jni.dll
OBJECTS = target\cluster.obj target\common.obj target\pool.obj target\sync.obj

{../native/src}.c{target}.obj:
    cl $<  /machine:AMD64 /Fo$@ -I..\native\include -I"$(JAVA_HOME)\include" -I"$(JAVA_HOME)\include\win32" /LD /c

target:
  mkdir target

target\classes: target
  mkdir target\classes

target\classes\win-x86_64: target\classes
  mkdir target\classes\win-x86_64

target\classes\win-x86_64\wfssl.dll: target\classes\win-x86_64 $(OBJECTS)
   link $(OBJECTS)  /machine:AMD64 /DLL -out:target\classes\win-x86_64\rados-jni.dll

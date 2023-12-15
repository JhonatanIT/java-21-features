package org.example;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class JEP434ForeignFunctionMemoryAPI {

    public static long getStringLength(String content) throws Throwable {
        // 1. Get a lookup object for commonly used libraries
        SymbolLookup stdlib = Linker.nativeLinker().defaultLookup();

        // 2. Get a handle on the strlen function in the C standard library
        MethodHandle strlen = Linker.nativeLinker().downcallHandle(
                stdlib.find("strlen").orElseThrow(),
                FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));

        long len = 0;

        // 3. Convert Java String to C string and store it in off-heap memory
        try (Arena offHeap = Arena.ofConfined()) {
            MemorySegment str = offHeap.allocateUtf8String(content);

            // 4. Invoke the foreign function
            len = (long) strlen.invoke(str);
        }
        // 5. Off-heap memory is deallocated at the end of try-with-resources
        // 6. Return the length.
        return len;
    }

    public static void main(String[] args) throws Throwable {
        System.out.println(getStringLength("Java 20 demo!"));
    }
}

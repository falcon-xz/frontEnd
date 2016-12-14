package com.xz.io;

import com.google.common.base.Charsets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * falcon -- 2016/12/1.
 */
public class test {
    public static void main(String[] args) {
        System.out.println((int) Math.ceil((double) Charsets.UTF_8.newEncoder().maxBytesPerChar()));

    }
}

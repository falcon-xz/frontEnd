package com.xz.io.cust;

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
 * falcon -- 2016/12/5.
 * 基于flume ResettableFileInputStream 改写的按行读取NIO实例
 */
public class ReadLineNIO {
    private Charset charset = Charsets.UTF_8;
    private CharsetDecoder decoder = charset.newDecoder();
    private int maxCharWidth = (int) Math.ceil((double) charset.newEncoder().maxBytesPerChar());
    private File file;
    private FileInputStream fis;
    private FileChannel fc;
    private ByteBuffer byteBuffer;
    private CharBuffer charBuffer = CharBuffer.allocate(1);
    private long position = 0;
    private char separator = '\n';
    private int bufferSize = 16384;

    public ReadLineNIO(String fileName) throws IOException {
        this(fileName, null, null);
    }

    public ReadLineNIO(String fileName, Character separator) throws IOException {
        this(fileName, null, separator);
    }

    public ReadLineNIO(String fileName, Integer bufferSize) throws IOException {
        this(fileName, bufferSize, null);
    }

    public ReadLineNIO(String fileName, Integer bufferSize, Character separator) throws IOException {
        //参数初始化
        if (fileName == null || "".equals(fileName)) {
            throw new FileNotFoundException("fileName:" + fileName + " is not illegal");
        }
        if (bufferSize != null && bufferSize != 0) {
            this.bufferSize = bufferSize;
        }
        if (separator != null && separator != '\u0000') {
            this.separator = separator;
        }
        //变量初始化
        file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("fileName:" + fileName + " is not exists");
        }
        fis = new FileInputStream(file);
        fc = fis.getChannel();
        byteBuffer = ByteBuffer.allocate(this.bufferSize);
        byteBuffer.flip();

    }

    private int readChar() throws IOException {
        //初始化 bytebuffer 后filp 设置为读状态 查看bytebuffer中剩余未读的数量
        if (this.byteBuffer.remaining() < this.maxCharWidth) {
            this.byteBuffer.clear();
            this.byteBuffer.flip();
            refilebuffer();
        }
        //初始pos
        int start = this.byteBuffer.position();
        //清除 charBuffer 内的数据
        this.charBuffer.clear();
        //将 bytebuffer 读到 charbuffer里
        decoder.decode(byteBuffer, charBuffer, false);
        // bytebuffer 读入 charbuffer 之后的pos
        int end = this.byteBuffer.position() - start;
        // charBuffer 设置为读状态
        charBuffer.flip();
        if (charBuffer.hasRemaining()) {
            char c = charBuffer.get();
            //更改记录pos的偏移量
            this.position += (long) end;
            return c;
        } else {
            return -1;
        }
    }

    private void refilebuffer() throws IOException {
        //压缩
        byteBuffer.compact();
        //设置偏移量 第一次0 第二次2 第三次4
        fc.position(position);
        //读取
        fc.read(this.byteBuffer);
        byteBuffer.flip();
    }

    public String readLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        int readChars = 0;
        while ((c = readChar()) != -1) {
            readChars++;

            // FIXME: support \r\n
            if (c == separator) {
                break;
            }
            sb.append((char) c);
        }
        if (readChars > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }

    public void release() throws IOException {
        this.fc.close();
        this.fis.close();
    }

    public static void main(String[] args) {
        try {
            ReadLineNIO t = new ReadLineNIO("E:\\test\\2.txt");
            while (true) {
                Thread.sleep(10);
                System.out.println("line:" + t.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

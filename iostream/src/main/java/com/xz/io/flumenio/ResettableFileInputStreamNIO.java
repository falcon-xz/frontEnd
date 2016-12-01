package com.xz.io.flumenio;

/**
 * falcon -- 2016/12/1.
 */
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.flume.serialization.*;

public class ResettableFileInputStreamNIO {
    private File file = new File("E:\\test\\1.txt");

    ResettableInputStream in;

    boolean isGetRI;

    public ResettableInputStream getRI() throws IOException {
        PositionTracker tracker =
                DurablePositionTracker.getInstance(new File("E:\\test\\1_ResettableFileInputStreamNIO_meta.txt"), file.getPath());
        return new ResettableFileInputStream(file, tracker,
                ResettableFileInputStream.DEFAULT_BUF_SIZE, Charset.defaultCharset(),DecodeErrorPolicy.REPLACE);
    }

    private String readLine() throws IOException {
        if (!isGetRI) {
            in = getRI();
            isGetRI = true;
        }
        StringBuilder sb = new StringBuilder();
        int c;
        int readChars = 0;
        while ((c = in.readChar()) != -1) {
            readChars++;

            // FIXME: support \r\n
            if (c == '\n') {
                break;
            }

            sb.append((char)c);

        /*  if (readChars >= maxLineLength) {
            logger.warn("Line length exceeds max ({}), truncating line!",
                maxLineLength);
            break;
          }*/
        }

        if (readChars > 0) {
            return sb.toString();
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        ResettableFileInputStreamNIO t = new ResettableFileInputStreamNIO();
        String line ;
        while ((line = t.readLine() )!= null){
            System.out.println(line);
        }
        t.in.mark();
    }
}

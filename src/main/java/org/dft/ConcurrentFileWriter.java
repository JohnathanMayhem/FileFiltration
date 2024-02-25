package org.dft;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ConcurrentFileWriter implements Closeable, AutoCloseable {



    private void createWriter() throws IOException {
        Path path = Path.of(filename);
        if (Files.exists(path)) {
            if (aFlag) {
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            } else {
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
                writer.write("");
                writer.close();
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            }
        } else {
            writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            this.ctreatedFlag = true;
        }
    }

    boolean ctreatedFlag;
    String filename;
    boolean aFlag;
    Writer writer = null;


    public ConcurrentFileWriter(String filename, boolean aFlag) {
        this.ctreatedFlag = false;
        this.writer = null;
        this.aFlag = aFlag;
        this.filename = filename;
    }

    synchronized void write(String data) throws IOException {
        if (this.writer == null) {
            createWriter();
        }
        this.writer.write(data);
        this.writer.write("\n");
        this.writer.flush();
        if(this.ctreatedFlag) {
            Path path = Path.of(filename);
            this.writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            this.writer.flush();

        }

    }

    @Override
    synchronized public void close() throws IOException {
        writer.close();
    }
}

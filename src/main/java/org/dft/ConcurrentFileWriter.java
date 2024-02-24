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
        System.out.println(filename);
        System.out.println(Files.exists(path));
        if (Files.exists(path)) {
            if (aFlag) {
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            } else {
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
                writer.write("");
                writer.close();
                writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                System.out.println("we come here");
            }
        } else {
            writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW);
            this.ctreatedFlag = true;
            System.out.println("Flag created");
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
        System.out.print(data);
        System.out.println(" writed");
        if(this.ctreatedFlag) {
            Path path = Path.of(filename);
            this.writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            this.writer.flush();
            System.out.println("Changed");
        }

    }

    @Override
    synchronized public void close() throws IOException {
        writer.close();
    }
}

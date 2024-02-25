package org.dft;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class FileWriterExecutor {

    Statistic statistic;
    private String outString = "string.txt";
    private String outIntegers = "integers.txt";
    private String outFloats = "floats.txt";
    ExecutorService executor;
    ConcurrentFileWriter stringFileWriter;
    ConcurrentFileWriter integerFileWriter;
    ConcurrentFileWriter floatFileWriter;
    Pattern integerPatter = Pattern.compile("^[0-9]+$");
    Pattern floatPatter = Pattern.compile("^[0-9]+[.][0-9]+$");

    public FileWriterExecutor(int amountOfThread, boolean aFlag, boolean extendedFormat) throws IOException {
        this.statistic = new Statistic(extendedFormat);
        this.executor = Executors.newFixedThreadPool(amountOfThread);
        stringFileWriter = new ConcurrentFileWriter(outString, aFlag);
        integerFileWriter = new ConcurrentFileWriter(outIntegers, aFlag);
        floatFileWriter = new ConcurrentFileWriter(outFloats, aFlag);
    }

    void run(List<BufferedReader> readers) {
        for (BufferedReader reader : readers) {
            executor.execute(() -> {
                reader.lines().forEach((str) -> {
                    try {
                        if (integerPatter.matcher(str).find()) {
                            integerFileWriter.write(str);
                            this.statistic.addInteger(str);
                        } else if (floatPatter.matcher(str).find()) {
                            this.statistic.addFloat(str);
                            floatFileWriter.write(str);
                        } else {
                            this.statistic.setTheLongestString(str);
                            stringFileWriter.write(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
        }
        executor.shutdown();
        try {
            if (this.stringFileWriter != null) {
                this.stringFileWriter.close();
            }
            if (this.floatFileWriter != null) {
                this.floatFileWriter.close();
            }
            if (this.integerFileWriter != null) {
                this.integerFileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Statistic getStatistic() {
        while (!executor.isTerminated()) {
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return this.statistic;
    }
}

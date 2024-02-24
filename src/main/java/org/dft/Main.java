package org.dft;

import org.dft.arguments.ArgsParser;
import org.dft.arguments.ArgumentError;
import org.dft.arguments.Arguments;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        FileWriterExecutor fileWriterExecutor;
        Arguments arguments = ArgsParser.getArguments(args);
        List<BufferedReader> readers = new ArrayList<>();
        Statistic statistic;
        if (arguments.isExecutable()) {
            for (var fileName : arguments.getSources()) {
                try {
                    readers.add(new BufferedReader(new FileReader(fileName)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (!readers.isEmpty()) {
                try {
                    fileWriterExecutor = new FileWriterExecutor(readers.size(), arguments.isaFlag(), arguments.isExtendedFormat());
                    fileWriterExecutor.run(readers);
                    System.out.println(fileWriterExecutor.getStatistic().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } else {
            System.err.println(ArgumentError.fatalMessage);
        }
    }
}

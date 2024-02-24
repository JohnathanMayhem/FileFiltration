package org.dft.arguments;

import java.util.ArrayList;

public class Arguments {

    ArrayList<String> sources;
    boolean aFlag;
    boolean pFlag;
    boolean oFlag;
    boolean extendedFormat;
    String outputPath;
    String integerOutput;
    String floatOutput;
    String stringOutput;

    public ArrayList<String> getSources() {
        return sources;
    }

    public boolean isaFlag() {
        return aFlag;
    }

    public boolean ispFlag() {
        return pFlag;
    }

    public boolean isoFlag() {
        return oFlag;
    }

    public boolean isExtendedFormat() {
        return extendedFormat;
    }

    public String getIntegerOutput() {
        return integerOutput;
    }

    public String getFloatOutput() {
        return floatOutput;
    }

    public String getStringOutput() {
        return stringOutput;
    }

    String prefix;

    public Arguments(ArgumentsBuilder builder) {
        this.sources = builder.sources;
        this.aFlag = builder.aFlag;
        this.pFlag = builder.pFlag;
        this.oFlag = builder.oFlag;
        this.extendedFormat = builder.extendedFormat;
        this.outputPath = builder.outputPath;
        this.prefix = builder.prefix;
        String integerOutput = "integers.txt";
        String floatOutput = "floats.txt";
        String stringOutput = "strings.txt";
        for (var e : builder.isValid()) {
            e.printStackTrace();
        }
    }

    public boolean isExecutable() {
        if (this.sources == null) {
            return false;
        } else {
            if (pFlag && prefix != null) {
                this.integerOutput = prefix + integerOutput;
                this.floatOutput = prefix + floatOutput;
                this.stringOutput = prefix + stringOutput;
            }
            if (oFlag && outputPath != null) {
                StringBuilder builder = new StringBuilder();
                builder.append(outputPath);
                builder.append("\\");
                builder.append(integerOutput);
                this.integerOutput = builder.toString();
                builder.delete(outputPath.length() + 1, integerOutput.length());
                builder.append(floatOutput);
                this.floatOutput = builder.toString();
                builder.delete(outputPath.length() + 1, floatOutput.length());
                builder.append(stringOutput);
                this.stringOutput = builder.toString();
            }
        }
        return !(this.sources.isEmpty());
    }
};

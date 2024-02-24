package org.dft.arguments;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ArgumentsBuilder {

    private final String[] illigalSymbols = {">", "<", ":", "\\", "/", "|", "?", "*", "\""};
    ArrayList<String> sources;
    boolean aFlag;
    boolean pFlag;
    boolean oFlag;
    boolean extendedFormat;
    String outputPath;
    String prefix;

    public void setSources(String source) throws ArgumentError {
        Path p = Path.of(source);
        if (Files.notExists(p)) {
            throw new ArgumentError(String.format(ArgumentError.messageOfSourceFileNotExist, source));
        }
        sources.add(source);
    }

    public void setAFlag() {
        this.aFlag = true;
    }

    public void setPFlag() {
        this.pFlag = true;
    }

    public void setOFlag() {
        this.oFlag = true;
    }

    public void setExtendedFormat(boolean flag) {
        this.extendedFormat = flag;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setPrefix(String prefix) throws ArgumentError {
        if (this.prefix != null) {
            Pattern pattern = Pattern.compile("[^<>:;,?\"*|/]+");
            Matcher matcher = pattern.matcher(prefix);
            if (matcher.find()) {
                throw new ArgumentError(ArgumentError.messageOfIllegalPrefix);
            }
        } else {
            this.prefix = prefix;
        }
    }

    public ArgumentsBuilder() {
        this.sources = new ArrayList<>();
        this.aFlag = false;
        this.oFlag = false;
        this.pFlag = false;
        this.extendedFormat = false;
        this.outputPath = null;
        this.prefix = null;

    }

    public ArrayList<ArgumentError> isValid() {
        ArrayList<ArgumentError> errors = new ArrayList<>();
        if (sources == null) {
            errors.add(new ArgumentError(ArgumentError.messageOfNoSourceFile));

        }
        if (sources != null) {
            if (sources.isEmpty()) {
                errors.add(new ArgumentError(ArgumentError.messageOfNoSourceFile));
            }
        }
        if (this.pFlag && prefix == null) {
            errors.add(new ArgumentError(ArgumentError.messageOfNoPrefix));
        }
        if (this.oFlag && this.outputPath == null) {
            errors.add(new ArgumentError(ArgumentError.messageOfNoOutputPath));
        }
        return errors;
    }
}

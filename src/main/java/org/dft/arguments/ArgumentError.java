package org.dft.arguments;


public class ArgumentError extends Exception {

    public final static String fatalMessage = "Input parameters are incorrect.\n" + "Usage must be: java -jar filefiltration.jar [FLAGS] " + "[INPUT_FILES]";

    public final static String messageOfNoSourceFile = "Usage must be: java -jar filefiltration.jar [FLAGS] " +
            "[INPUT_FILES]";
    public final static String messageOfSourceFileNotExist = "%s no such file";
    public final static String messageOfNoOutputPath = "no directory";
    public final static String messageOfNoPrefix = "Usage must be: filefiltration.jar [FLAGS] [-p] [PREFIX] [INPUT_FILES] " +
            "or filefiltration.jar [FLAGS] [INPUT_FILES] [-p] [PREFIX]";
    public final static String messageOfIllegalPrefix = "PREFIX must not have symbols [%s]";

    public final static String messageOfIllegalFlag = "%s is not a flag";

    public ArgumentError(String message) {
        super(message);
    }
}

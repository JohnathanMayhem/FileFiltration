package org.dft.arguments;

public class ArgsParser {

    public ArgsParser() {

    }

    public static Arguments getArguments(String[] argv) {
        ArgumentsBuilder argumentsBuilder = new ArgumentsBuilder();
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].equals("-a")) {
                argumentsBuilder.setAFlag();
                continue;
            }
            if (argv[i].equals("-p")) {
                argumentsBuilder.setPFlag();
                if (i < argv.length - 1) {
                    if (!(argv[i + 1].contains("-"))) {
                        try {
                            argumentsBuilder.setPrefix(argv[i + 1]);
                        } catch (ArgumentError e) {
                            e.printStackTrace();
                        }
                        ++i;
                        continue;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            if (argv[i].equals("-s") || argv[i].equals("-f")) {
                argumentsBuilder.setExtendedFormat(argv[i].equals("-f"));
                continue;
            }
            if (argv[i].equals("-o")) {
                argumentsBuilder.setOFlag();
                if (i + 1 < argv.length) {
                    if (!argv[i + 1].contains("-")) {
                        argumentsBuilder.setOutputPath(argv[i]);
                    }
                }
                continue;
            }
            if (argv[i].contains("-")) {
                ArgumentError e = new ArgumentError(String.format(ArgumentError.messageOfIllegalFlag, argv[i]));
                e.printStackTrace();
                continue;
            }
            try {
                argumentsBuilder.setSources(argv[i]);
            } catch (ArgumentError e) {
                e.printStackTrace();
            }
        }
        return new Arguments(argumentsBuilder);
    }
}

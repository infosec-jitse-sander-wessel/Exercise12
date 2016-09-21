import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 21-9-2016.
 */
public class Controller {

    private CommandLine commandLine;
    private final Options options;

    Controller(String[] args) throws Exception {
        this.options = getOptions();
        CommandLineParser parser = new BasicParser();
        try {
            this.commandLine = parser.parse(this.options, args);
        } catch (ParseException e) {
            System.out.println("Incorrect arguments: " + e.getMessage());
            printHelpPage();
            throw new Exception("Incorrect input");
        }
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "Display this help page");
        return options;
    }

    private void printHelpPage() {
        System.out.println("Usage: <number> <power> <modulo>");
    }

    public void run() throws ParseException {
        if (commandLine.hasOption("h")) {
            printHelpPage();
            return;
        }

    }


}

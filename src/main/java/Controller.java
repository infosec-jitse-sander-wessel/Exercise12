import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        System.out.println("Usage: <prime number>");
    }

    public void run() throws ParseException {
        if (commandLine.hasOption("h")) {
            printHelpPage();
            return;
        }

        String[] arguments = commandLine.getArgs();

        if (arguments.length != 1 || !StringUtils.isNumeric(arguments[0])) {
            printHelpPage();
            throw new ParseException("Invalid arguments, one and only one prime number is required");
        }

        int number = Integer.parseInt(arguments[0]);

        List<Integer> factors = primeFactorization(number - 1);

        List<Integer> validGenerators = IntStream.range(2, number)
                .filter(generator -> validGenerator(generator, number, factors))
                .boxed()
                .collect(Collectors.toList());
        System.out.println(validGenerators);
    }

    private boolean validGenerator(int generator, int number, List<Integer> factors) {
        List<Integer> invalidGenerators = factors.stream()
                .filter(e -> generatorFormula(generator, number, e))
                .collect(Collectors.toList());
        return invalidGenerators.size() == 0;
    }

    private boolean generatorFormula(int generator, int number, int factor) {
        BigInteger bigGenerator = BigInteger.valueOf(generator);
        BigInteger bigNumber = BigInteger.valueOf(number);
        BigInteger bigFactor = BigInteger.valueOf(factor);

        BigInteger power = bigNumber.subtract(BigInteger.ONE).divide(bigFactor);
        BigInteger genPowered = bigGenerator.modPow(power, bigNumber);

        return genPowered.equals(BigInteger.ONE);
    }

    private List<Integer> primeFactorization(int number) {
        List<Integer> factors = new ArrayList<>();
        int temp = number;
        for (int i = 2; i <= temp / i; i++) {
            while (temp % i == 0) {
                factors.add(i);
                temp /= i;
            }
        }
        if (temp > 1) {
            factors.add(temp);
        }

        return factors;
    }

}

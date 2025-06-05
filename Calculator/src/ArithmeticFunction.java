
import java.util.ArrayList;

public class ArithmeticFunction implements ArithmeticInterface {

    private final ArrayList<Double> operands;
    private final ArrayList<String> operations;
    private InputSection inputSection;

    public ArithmeticFunction(ArrayList<Double> operands, ArrayList<String> operations) {
        this.operands = operands;
        this.operations = operations;
    }

    @Override
    public double addition(double num1, double num2) {
        return num1 + num2;
    }

    @Override
    public double subtraction(double num1, double num2) {
        return num1 - num2;
    }

    @Override
    public double multiply(double num1, double num2) {
        return num1 * num2;
    }

    @Override
    public double divide(double num1, double num2) {
        handleDivideByZero(num2);
        return num1 / num2;
    }

    private void handleDivideByZero(double num2) {
        if (num2 == 0) {
            inputSection.setInputField("NaN");
            throw new ArithmeticException("Cannot divide by zero");

        }
    }

    public String performOperation() {

        double result = operands.get(0);
        StringBuilder operationString = new StringBuilder("[").append(operands.get(0)).append("]");

        for (int i = 0; i < operations.size(); i++) {
            String operation = operations.get(i);
            // Ensure that there are enough operands in the list
            double operand = operands.get(i + 1);
            operationString.append(" ").append(operation).append(" [").append(operand).append("]");

            switch (operation) {
                case "/":
                    result = divide(result, operand);
                    break;
                case "*":
                    result = multiply(result, operand);
                    break;
                case "+":
                    result = addition(result, operand);
                    break;
                case "-":
                    result = subtraction(result, operand);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation sign: " + operation);
            }
        }

        return checkResultIsNum(result, operationString);
    }

    private String checkResultIsNum(double result, StringBuilder operationString) {
        if (result % 1 == 0) {
            System.out.println("Performing operation: " + operationString + " = " + result);
            return String.valueOf((int) result);
        } else {
            System.out.println("Performing operation: " + operationString + " = " + result);
            return String.valueOf(result);
        }
    }
}

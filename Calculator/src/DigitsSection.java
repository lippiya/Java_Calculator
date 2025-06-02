
import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.regex.*;

class DigitsSection extends JPanel {
    private InputSection inputSection;
    private ArrayList<Double> operands = new ArrayList<>();
    private ArrayList<String> operations = new ArrayList<>();

    Font myFont = new Font("Arial", Font.PLAIN, 30);

    ArithmeticFunction arithmeticFunction;
    ScientificFunction scientificFunction;

    public DigitsSection(InputSection inputSection) {
        this.inputSection = inputSection;

        setLayout(new GridLayout(5, 4, 5, 5));

        String[] buttonLabels = { "C", "+/-", "%", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "del", "0", ".", "=" };

        // Creating buttons and setting properties
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(myFont);
            button.setForeground(Color.decode("#FFFFFF"));

            button.setBackground(getBackgroundColor(label));

            button.addActionListener(e -> {
                String buttonText = button.getText();
                String input = inputSection.getInputFieldText();

                if (buttonText.equals("C")) {
                    inputSection.deleteInputField();
                } else if (buttonText.equals("del")) {
                    inputSection.removeCurrentText();
                } else if (buttonText.equals("=")) {
                    // Handling evaluation
                    char sign = extractSign(input);
                    if (sign == ' ') {
                        applyScientificFunctions();
                    } else {
                        splitInput(sign, input);
                    }
                } else if (buttonText.equals("%")) {

                    handlePercentageCalculation(input);
                } else if (buttonText.equals("+/-")) {

                    tooglePositiveOrNegativeSign(input);
                } else {
                    inputSection.updateInputField(buttonText);
                }
            });
            add(button);
        }
    }

    private void tooglePositiveOrNegativeSign(String input) {
        double number = Double.parseDouble(input);
        double result = number * -1;
        inputSection.setInputField(String.valueOf(result));
    }

    private void handlePercentageCalculation(String input) {
        char sign = extractSign(input);
        String[] operands = input.split("[" + sign + "]");
        double num = Double.parseDouble(operands[0]);
        double result = num / 100.0;
        inputSection.setInputField(String.valueOf(result));
    }

    private Color getBackgroundColor(String label) {
        if (label.equals("C") || label.equals("+/-") || label.equals("%")) {
            return Color.decode("#454442");
        } else if (label.equals("/") || label.equals("*") || label.equals("-") || label.equals("+")
                || label.equals("=")) {
            return Color.decode("#FF9F09");
        } else {
            return Color.decode("#636361");
        }
    }

    private void splitInput(char sign, String input) {

        String[] operands = input.split(Pattern.quote(String.valueOf(sign)));
        double num1 = Double.parseDouble(operands[0]);
        double num2 = Double.parseDouble(operands[1]);
        ScientificFunction scientificFunction = new ScientificFunction(num1, "^", inputSection, inputSection);
        if (sign == '√') {
            double result = scientificFunction.customRoot(num2, num1);
            inputSection.setInputField(String.valueOf(result));
        } else if (sign == '^') {
            double result = scientificFunction.customPower(num1, num2);
            inputSection.setInputField(String.valueOf(result));
        } else if (sign == 'E') {
            double result = num1 * (Math.pow(10, num2));
            inputSection.setInputField(String.valueOf(result));
        } else {
            String[] split = input.split("(?=[-+*/()])|(?<=[-+*/()])");
            ArrayList<Double> operandsList = new ArrayList<>();
            ArrayList<String> operationsList = new ArrayList<>();

            for (String token : split) {
                try {
                    if (token.matches("sin\\d*|cos\\d*|tan\\d*|log\\d*|ln\\d*")) {

                        String functionName = token.substring(0, 3).trim();
                        String numericPart = token.substring(3).trim();
                        double numericValue = Double.parseDouble(numericPart);
                        double number = performScientificAction(functionName, numericValue, inputSection);
                        operandsList.add(number);
                    }

                    double number = Double.parseDouble(token);
                    operandsList.add(number);

                } catch (NumberFormatException error) {
                    if (token.equals("+")) {
                        operationsList.add(token);
                    } else if (token.equals("-")) {
                        operationsList.add(token);
                    } else if (token.equals("/")) {
                        operationsList.add(token);
                    } else if (token.equals("*")) {
                        operationsList.add(token);
                    }

                }
            }

            ArithmeticFunction arithmeticFunction = new ArithmeticFunction(operandsList, operationsList);
            String result = arithmeticFunction.performOperation();
            inputSection.setInputField(result);
        }
    }

    private char extractSign(String input) {
        String newInput = removeBrackets(input);
        System.out.println(newInput);
        char sign = ' ';
        for (char c : newInput.toCharArray()) {
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '√' || c == '^' || c == 'E') {
                sign = c;
                break;
            }
        }
        return sign;
    }

    public String removeBrackets(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) || c == '.' || c == '-' || c == '+' || c == '*' || c == '/' || c == '%' || c == '√'
                    || c == '^' || c == 'E') {
                result.append(c);
            }
        }
        return result.toString();
    }

    private void applyScientificFunctions() {
        String inputText = inputSection.getInputFieldText();
        double result = 0;

        if (inputText.startsWith("sin")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "sin", inputSection, inputSection);
            result = scientificFunction.sin();
        } else if (inputText.startsWith("arcSin")) {
            String numericPart = inputText.substring(6);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "arcSin", inputSection, inputSection);
            result = scientificFunction.arcSin();
        } else if (inputText.startsWith("cos")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "cos", inputSection, inputSection);
            result = scientificFunction.cos();
        } else if (inputText.startsWith("arcCos")) {
            String numericPart = inputText.substring(6);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "sinh", inputSection, inputSection);
            result = scientificFunction.arcCos();
        } else if (inputText.startsWith("tan")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "tan", inputSection, inputSection);
            result = scientificFunction.tan();
        } else if (inputText.startsWith("arcTan")) {
            String numericPart = inputText.substring(6);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "arcTan", inputSection, inputSection);
            result = scientificFunction.arcTan();
        } else if (inputText.startsWith("log")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "log", inputSection, inputSection);
            result = scientificFunction.log();
        } else if (inputText.startsWith("ln")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "ln", inputSection, inputSection);
            result = scientificFunction.ln();
        } else if (inputText.startsWith("sih")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "sinh", inputSection, inputSection);
            result = scientificFunction.sinH();
        } else if (inputText.startsWith("arcSiH")) {
            String numericPart = inputText.substring(6);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "arcSinh", inputSection, inputSection);
            result = scientificFunction.arcSinH();
        } else if (inputText.startsWith("coh")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "cosh", inputSection, inputSection);
            result = scientificFunction.cosH();
        } else if (inputText.startsWith("arcCoH")) {
            String numericPart = inputText.substring(6);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "arcCosh", inputSection, inputSection);
            result = scientificFunction.arcCosH();
        } else if (inputText.startsWith("tah")) {
            String numericPart = inputText.substring(3);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "tanh", inputSection, inputSection);
            result = scientificFunction.tanH();
        } else if (inputText.startsWith("arcTaH")) {
            String numericPart = inputText.substring(6);
            double num = Double.parseDouble(numericPart);
            scientificFunction = new ScientificFunction(num, "sinh", inputSection, inputSection);
            result = scientificFunction.arcTanH();
        } else if (inputText.startsWith("√")) {
            String numericPart = inputText.substring(1); // Extract numeric part after "√"
            double num = Double.parseDouble(numericPart);
            result = Math.sqrt(num);
        }

        inputSection.setInputField(String.valueOf(result));
    }

    private static double performScientificAction(String functionName, double numericValue, InputSection inputSection) {
        double result = 0;
        switch (functionName) {
            case "sin":
                ScientificFunction sinFunction = new ScientificFunction(numericValue, "sin", inputSection,
                        inputSection);
                result = sinFunction.sin();
                break;
            case "cos":
                ScientificFunction cosFunction = new ScientificFunction(numericValue, "cos", inputSection,
                        inputSection);
                result = cosFunction.cos();
                break;
            case "tan":
                ScientificFunction tanFunction = new ScientificFunction(numericValue, "tan", inputSection,
                        inputSection);
                result = tanFunction.tan();
                break;
            case "log":
                ScientificFunction logFunction = new ScientificFunction(numericValue, "log", inputSection,
                        inputSection);
                result = logFunction.log();
                break;
            case "ln":
                ScientificFunction lnFunction = new ScientificFunction(numericValue, "ln", inputSection, inputSection);
                result = lnFunction.ln();
                break;
            default:
                throw new IllegalArgumentException("Unsupported scientific function: " + functionName);
        }
        return result;
    }

}
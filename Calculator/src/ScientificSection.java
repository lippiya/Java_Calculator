import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

interface ScientificAction {
    void execute();
}

public class ScientificSection extends JPanel {
   
    private Font myFont = new Font("Arial", Font.PLAIN, 30);
    
    private InputSection inputSection;
    private ResultHandler resultHandler;
    private MemoryHandler memoryHandler;

    private boolean showInverseFunctions = false;

   
    private final Map<String, ScientificAction> scientificActions = new HashMap<>();

    public ScientificSection(InputSection inputSection) {
        this.inputSection = inputSection;
        this.memoryHandler = inputSection;
        this.resultHandler = inputSection;
        this.setLayout(new GridLayout(6, 5)); 

        Font myFont = new Font("Arial", Font.BOLD, 24);
        String[] scientificButtonLabels = {
                "(", ")", "mc", "m+", "m-",
                "mr", "2nd", "x²", "x³", "X^y",
                "e^x", "10^x", "1/x", "√", "3√", "x√y",
                "LN(x)", "log", "x!", "sin", "cos",
                "tan", "e","Rad", "sinh",
                "cosh", "tanh", "π"};

        String[] renderNewButtons = {
                "arcSin", "arcCos", "arcTan",
                "arcSiH", "arcCoH", "arcTaH" };

        initScientificActions(scientificButtonLabels, renderNewButtons);

        for (String label : scientificButtonLabels) {
            JButton button = new JButton(label);
            button.setFont(myFont);
            button.setBackground(Color.decode("#454442"));
            button.setForeground(Color.decode("#FFFFFF"));

            button.addActionListener(e -> {
                ScientificAction action = scientificActions.get(label);
                if (action != null) {
                    action.execute();
                } else {
                    inputSection.updateInputField(label);
                }
            });

            this.add(button);
        }
    }

    private void initScientificActions(String[] labels, String[] inverseLabels) {
        scientificActions.put("(", () -> inputSection.updateInputField("("));
        scientificActions.put(")", () -> inputSection.updateInputField(")"));

        scientificActions.put("mc", () -> memoryHandler.resetMemory());
        scientificActions.put("m+", () -> applyUnaryFunction("mplus"));
        scientificActions.put("m-", () -> {
            double numberToRemove = Double.parseDouble(inputSection.getInputFieldText());
            memoryHandler.subtractFromMemory(numberToRemove);
        });
        scientificActions.put("mr", () -> memoryHandler.getMemoryValue());

        scientificActions.put("x²", () -> applyUnaryFunction("square"));
        scientificActions.put("x³", () -> applyUnaryFunction("cube"));
        scientificActions.put("√", () -> applyUnaryFunction("sqroot"));
        scientificActions.put("3√", () -> applyUnaryFunction("cubeRoot"));
        scientificActions.put("1/x", () -> applyUnaryFunction("fraction"));
        scientificActions.put("x!", () -> applyUnaryFunction("factorial"));

        scientificActions.put("e", () -> inputSection.updateInputField(String.valueOf(Math.E)));
        scientificActions.put("π", () -> resultHandler.showResult(String.valueOf(Math.PI)));

        scientificActions.put("LN(x)", () -> inputSection.updateInputField("ln"));
        scientificActions.put("log", () -> inputSection.updateInputField("log"));
        scientificActions.put("sin", () -> inputSection.updateInputField("sin"));
        scientificActions.put("cos", () -> inputSection.updateInputField("cos"));
        scientificActions.put("tan", () -> inputSection.updateInputField("tan"));
        scientificActions.put("tanh", () -> inputSection.updateInputField("tah"));
        scientificActions.put("sinh", () -> inputSection.updateInputField("sih"));
        scientificActions.put("cosh", () -> inputSection.updateInputField("coh"));
        scientificActions.put("arcSin", () -> inputSection.updateInputField("arcSin"));
        scientificActions.put("arcCos", () -> inputSection.updateInputField("arcCos"));
        scientificActions.put("arcTan", () -> inputSection.updateInputField("arcTan"));
        scientificActions.put("arcSiH", () -> inputSection.updateInputField("arcSiH"));
        scientificActions.put("arcCoH", () -> inputSection.updateInputField("arcCoH"));
        scientificActions.put("arcTaH", () -> inputSection.updateInputField("arcTaH"));

        scientificActions.put("x√y", () -> inputSection.updateInputField("√"));
        scientificActions.put("X^y", () -> inputSection.updateInputField("^"));
        scientificActions.put("10^x", () -> applyUnaryFunction("powerTen"));
        scientificActions.put("e^x", () -> applyUnaryFunction("exponentialPower"));


        scientificActions.put("2nd", () -> {
            showInverseFunctions = !showInverseFunctions;
            for (int i = 0; i < inverseLabels.length; i++) {
                JButton secondButton = (JButton) getComponent(labels.length - 1 - i);
                secondButton.setText(showInverseFunctions ? inverseLabels[i]
                        : labels[labels.length - 1 - i]);
            }
        });
    }

    private void applyUnaryFunction(String functionName) {
        String inputText = inputSection.getInputFieldText();
        if (inputText.isEmpty() || !inputText.matches("[-+]?\\d*(\\.\\d+)?")) {
            return;
        }
        double num = Double.parseDouble(inputSection.getInputFieldText());
        ScientificFunction scientificFunction = new ScientificFunction(num, functionName, resultHandler, memoryHandler);
        double result = 0;
        switch (functionName) {
            case "mc":
                result = scientificFunction.mc();
                System.out.println(result);
                break;
            case "mplus":
                result = scientificFunction.mplus();
                break;
            case "mminus":
                result = scientificFunction.mminus();
                break;
            case "square":
                result = scientificFunction.square();
                break;
            case "cube":
                result = scientificFunction.cube();
                break;
            case "sqroot":
                result = scientificFunction.sqroot();
                break;
            case "cubeRoot":
                result = scientificFunction.cubeRoot();
                break;
            case "fraction":
                result = scientificFunction.fraction();
                break;
            case "exponential":
                result = scientificFunction.exponential();
                break;
            case "factorial":
                result = scientificFunction.factorial();
                break;
            case "pi":
                result = Math.PI;
                break;
            case "powerTen":
                result = scientificFunction.tenthPower();
                break;
            case "exponentialPower":
                result = scientificFunction.exponentialPower();
                break;
            case "mread":
                result = scientificFunction.mread();
                System.out.print("You clicked the mr button");
                break;
            default:
                break;
        }
        resultHandler.showResult(String.valueOf(result));
    }
}


import java.awt.*;
import javax.swing.*;

public class CalculatorFrame extends JFrame {

    public CalculatorFrame(String name, int width, int height) {
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);

        InputSection inputSection = new InputSection();
        ScientificSection scientificSection = new ScientificSection(inputSection);
        DigitsSection digitsSection = new DigitsSection(inputSection);

        // Set the preferred size of the InputSection
        inputSection.setPreferredSize(new Dimension(width, height / 5));

        // Create a main panel to hold all sections
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputSection, BorderLayout.NORTH);

        // Create a panel to hold the scientific and digits sections side by side
        JPanel sectionsPanel = new JPanel(new GridLayout(1, 2));
        sectionsPanel.add(scientificSection);
        sectionsPanel.add(digitsSection);

        mainPanel.add(sectionsPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

}

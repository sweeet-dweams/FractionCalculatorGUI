package src.fractionCalculator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI implements ActionListener {

    private long num1;
    private long num2;
    private long den1;
    private long den2;

    private JTextField numerator1;
    private JTextField denominator1;
    private JTextField numerator2;
    private JTextField denominator2;
    private JTextArea resultArea;
    private JComboBox<String> operationsBox;
    private JButton calculateButton;

    /**
     * Constructor method that creates the window with all the required elements
     * @param width - The width of the window in pixels
     * @param height - The height of the window in pixels
     */
    public CalculatorGUI(int width, int height) {
        //Creating the window
        JFrame calculatorGUI = new JFrame("Fraction Calculator");
        calculatorGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculatorGUI.setSize(width, height);
        
        JPanel calculatorPanel = new JPanel();
        
        calculatorPanel.setLayout(new GridLayout(3, 6));
        
        //Creating the numerator and denominator for both fractions
        this.numerator1 = new JTextField();
        this.denominator1 = new JTextField();
        this.numerator2 = new JTextField();
        this.denominator2 = new JTextField();
        this.resultArea = new JTextArea(3, 20);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //Creates the drop down menu for the operations.
        String[] operations = {"+", "-", "*", "/"};
        this.operationsBox = new JComboBox<>(operations);
        this.calculateButton = new JButton("Calculate");
        
        //Layout
        calculatorPanel.add(new JLabel("Numerator 1:"));
        calculatorPanel.add(numerator1);

        calculatorPanel.add(new JLabel("Numerator 2:"));
        calculatorPanel.add(numerator2);

        calculatorPanel.add(new JLabel("Denominator 1:"));
        calculatorPanel.add(denominator1);

        calculatorPanel.add(new JLabel("Denominator 2:"));
        calculatorPanel.add(denominator2);


        calculatorPanel.add(calculateButton);
        calculatorPanel.add(operationsBox);
        calculatorPanel.add(new JLabel(" "));
        calculatorPanel.add(resultArea);

        calculatorGUI.add(calculatorPanel);
        calculatorGUI.setVisible(true);

        this.calculateButton.addActionListener(this);
    }

    public Fraction calculate(String operation) {

        this.num1 = Long.parseLong(numerator1.getText());
        this.den1 = Long.parseLong(denominator1.getText());

        this.num2 = Long.parseLong(numerator2.getText());
        this.den2 = Long.parseLong(denominator2.getText());

        Fraction frac1 = new Fraction(this.num1, this.den1);
        Fraction frac2 = new Fraction(this.num2, this.den2);
        Fraction answer = new Fraction(1); //initializing to avoid error message :)

        if (operation.equals("+")) {
            answer = frac1.add(frac2);
        }
        else if (operation.equals("-")) {
            answer = frac1.subtract(frac2);
        }
        else if (operation.equals("*")) {
            answer = frac1.multiply(frac2);
        }
        else if (operation.equals("/")) {
            answer = frac1.divide(frac2);
        }

        return answer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String operator = (String) this.operationsBox.getSelectedItem();
        Fraction answer = calculate(operator);
        resultArea.setText(answer.toString());
    }
}

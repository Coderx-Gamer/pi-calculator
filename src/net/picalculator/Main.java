package net.picalculator;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Main {
    public static void main(String[] args) throws Exception {
        // Happy pi day :D
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        JFrame frame = new JFrame("Pi Calculator");
        frame.setBounds(0, 0, 600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        JLabel piLabel = new JLabel("Pi Calculator");
        piLabel.setBounds(0, 5, 600, 40);
        piLabel.setFont(new Font("plain", Font.PLAIN, 42));
        piLabel.setHorizontalAlignment(SwingConstants.CENTER);
        piLabel.setFocusable(false);

        JLabel iterationsLabel = new JLabel("Iterations:");
        iterationsLabel.setBounds(200, 175, 90, 25);
        iterationsLabel.setFont(new Font("plain", Font.PLAIN, 12));
        iterationsLabel.setFocusable(false);

        JTextField iterationsInput = new JTextField();
        iterationsInput.setBounds(260, 178, 90, 20);
        iterationsInput.setBorder(BorderFactory.createEtchedBorder());

        JLabel precisionLabel = new JLabel("Precision:");
        precisionLabel.setBounds(200, 200, 90, 25);
        precisionLabel.setFont(new Font("plain", Font.PLAIN, 12));
        precisionLabel.setFocusable(false);

        JTextField precisionInput = new JTextField();
        precisionInput.setBounds(260, 203, 90, 20);
        precisionInput.setBorder(BorderFactory.createEtchedBorder());

        JTextArea outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setEditable(false);

        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBounds(50, 300, 500, 100);
        outputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        JLabel loadingLabel = new JLabel("Loading...");
        loadingLabel.setBounds(270, 135, 200, 50);
        loadingLabel.setFont(new Font("plain", Font.PLAIN, 16));
        loadingLabel.setVisible(false);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(200, 100, 200, 50);
        calculateButton.setFont(new Font("plain", Font.PLAIN, 18));
        calculateButton.addActionListener((e) -> {
            if (isPositiveInteger(iterationsInput.getText()) && isPositiveInteger(precisionInput.getText())) {
                loadingLabel.setVisible(true);
                calculateButton.setEnabled(false);
                new Thread(() -> {
                    outputArea.setText(calculatePi(iterationsInput.getText(), Integer.parseInt(precisionInput.getText())));
                    loadingLabel.setVisible(false);
                    calculateButton.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Successfully calculated pi! (the digits are not exactly correct relative to the precision variable, iterations makes it more precise, precision is for the amount of digits regardless)", "Success", JOptionPane.INFORMATION_MESSAGE);
                }).start();
            } else {
                JOptionPane.showMessageDialog(null, "Please input positive integers for both precision and iterations.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        calculateButton.setFocusable(false);

        frame.add(piLabel);
        frame.add(calculateButton);
        frame.add(iterationsLabel);
        frame.add(iterationsInput);
        frame.add(precisionLabel);
        frame.add(precisionInput);
        frame.add(loadingLabel);
        frame.add(outputScroll);

        frame.setVisible(true);
    }

    private static String calculatePi(String iterations, int precision) {
        BigDecimal pi = new BigDecimal("4");
        int j = 0;
        for (BigInteger i = new BigInteger("3"); i.compareTo(new BigInteger("" + iterations).multiply(new BigInteger("2")).add(new BigInteger("3"))) == -1; i = i.add(new BigInteger("2"))) {
            BigDecimal decimal = new BigDecimal("4").divide(new BigDecimal(i.toString()), new MathContext(precision));
            if (j % 2 == 0) {
                pi = pi.subtract(decimal);
            } else {
                pi = pi.add(decimal);
            }
            j++;
        }
        return pi.toString();
    }

    private static boolean isPositiveInteger(String string) {
        if (string.isEmpty()) {
            return false;
        }

        int i = 0;
        for (char c : string.toCharArray()) {
            if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9')) {
                return false;
            }
            i++;
        }
        return true;
    }
}

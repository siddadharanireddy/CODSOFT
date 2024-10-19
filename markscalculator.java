
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MarksCalculatorGUI extends JFrame implements ActionListener {
    private JTextField subject1Field, subject2Field, subject3Field, subject4Field, subject5Field;
    private JTextArea resultArea;

    public MarksCalculatorGUI() {
        // Set up the GUI
        setTitle("Marks Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel subject1Label = new JLabel("Enter marks for Subject 1:");
        JLabel subject2Label = new JLabel("Enter marks for Subject 2:");
        JLabel subject3Label = new JLabel("Enter marks for Subject 3:");
        JLabel subject4Label = new JLabel("Enter marks for Subject 4:");
        JLabel subject5Label = new JLabel("Enter marks for Subject 5:");
        
        subject1Field = new JTextField(10);
        subject2Field = new JTextField(10);
        subject3Field = new JTextField(10);
        subject4Field = new JTextField(10);
        subject5Field = new JTextField(10);
        
        JButton calculateButton = new JButton("Calculate");
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        calculateButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 5, 5));
        panel.add(subject1Label);
        panel.add(subject1Field);
        panel.add(subject2Label);
        panel.add(subject2Field);
        panel.add(subject3Label);
        panel.add(subject3Field);
        panel.add(subject4Label);
        panel.add(subject4Field);
        panel.add(subject5Label);
        panel.add(subject5Field);
        panel.add(calculateButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double marks1 = Double.parseDouble(subject1Field.getText());
            double marks2 = Double.parseDouble(subject2Field.getText());
            double marks3 = Double.parseDouble(subject3Field.getText());
            double marks4 = Double.parseDouble(subject4Field.getText());
            double marks5 = Double.parseDouble(subject5Field.getText());

            if (marks1 < 0 || marks1 > 100 || marks2 < 0 || marks2 > 100 ||
                marks3 < 0 || marks3 > 100 || marks4 < 0 || marks4 > 100 ||
                marks5 < 0 || marks5 > 100) {
                resultArea.setText("Marks should be between 0 and 100 for all subjects.\n");
                return;
            }

            double totalMarks = marks1 + marks2 + marks3 + marks4 + marks5;

            double averagePercentage = totalMarks / 5.0;

            String grade;
            if (averagePercentage >= 90) {
                grade = "A";
            } else if (averagePercentage >= 80) {
                grade = "B";
            } else if (averagePercentage >= 70) {
                grade = "C";
            } else if (averagePercentage >= 60) {
                grade = "D";
            } else {
                grade = "F";
            }
            resultArea.setText("Total Marks: " + totalMarks + "\n");
            resultArea.append("Average Percentage: " + averagePercentage + "%\n");
            resultArea.append("Grade: " + grade + "\n");
        } catch (NumberFormatException ex) {
            resultArea.setText("Please enter valid numbers for all subjects.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MarksCalculatorGUI gui = new MarksCalculatorGUI();
            gui.setVisible(true);
        });
    }
}

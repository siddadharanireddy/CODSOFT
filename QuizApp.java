import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class QuizApp extends JFrame implements ActionListener {
    String[][] questions = {
            {"What is the capital of France?", "London", "Berlin", "Paris", "Madrid", "Paris"},
            {"Who developed Java?", "Microsoft", "Apple", "Sun Microsystems", "Google", "Sun Microsystems"},
            {"Which planet is known as the Red Planet?", "Venus", "Mars", "Jupiter", "Saturn", "Mars"},
            {"What is the largest ocean on Earth?", "Indian Ocean", "Atlantic Ocean", "Arctic Ocean", "Pacific Ocean", "Pacific Ocean"},
            {"Who wrote 'Hamlet'?", "Charles Dickens", "Leo Tolstoy", "William Shakespeare", "Mark Twain", "William Shakespeare"}
    };
    int index = 0;
    int score = 0;
    int timeLimit = 10; 

    JLabel questionLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup optionsGroup;
    JButton submitButton;
    JLabel timerLabel;
    Timer timer;
    int timeRemaining;

    public QuizApp() {
        setTitle("Quiz App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        questionLabel = new JLabel("", JLabel.CENTER);
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionsGroup.add(options[i]);
            add(options[i]);
        }
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        timerLabel = new JLabel("Time remaining: " + timeLimit + " seconds", JLabel.CENTER);

        add(questionLabel);
        add(timerLabel);
        add(submitButton);
        nextQuestion();
    }

    private void nextQuestion() {
        if (index < questions.length) {
            questionLabel.setText(questions[index][0]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(questions[index][i + 1]);
                options[i].setSelected(false);
            }
            timeRemaining = timeLimit;
            timerLabel.setText("Time remaining: " + timeLimit + " seconds");
            timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    timeRemaining--;
                    timerLabel.setText("Time remaining: " + timeRemaining + " seconds");
                    if (timeRemaining <= 0) {
                        timer.stop();
                        checkAnswer();
                    }
                }
            });
            timer.start();
        } else {
            showResults();
        }
    }

    private void checkAnswer() {
        timer.stop();
        String selectedOption = "";
        for (JRadioButton option : options) {
            if (option.isSelected()) {
                selectedOption = option.getText();
                break;
            }
        }
        if (selectedOption.equals(questions[index][5])) {
            score++;
        }
        index++;
        nextQuestion();
    }

    private void showResults() {
        String result = "Quiz Finished!\nYour Score: " + score + "/" + questions.length + "\n";
        for (int i = 0; i < questions.length; i++) {
            result += "Q" + (i + 1) + ": " + questions[i][0] + "\n";
            result += "Correct Answer: " + questions[i][5] + "\n\n";
        }

        JOptionPane.showMessageDialog(this, result, "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            checkAnswer();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuizApp().setVisible(true);
            }
        });
    }
}

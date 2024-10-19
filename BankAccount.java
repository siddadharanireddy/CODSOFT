import java.awt.BorderLayout;
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

// Class representing the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

// Class representing the ATM machine
class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            account.deposit(amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }

    public double checkBalance() {
        return account.getBalance();
    }
}

// Main class to create the GUI
public class ATMGUI extends JFrame implements ActionListener {
    private ATM atm;
    private JTextField amountField;
    private JTextArea displayArea;

    public ATMGUI() {
        // Create a BankAccount with an initial balance of $1000
        BankAccount account = new BankAccount(1000.0);
        atm = new ATM(account);

        // Set up the GUI
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel amountLabel = new JLabel("Enter amount:");
        amountField = new JTextField(10);
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton balanceButton = new JButton("Check Balance");
        displayArea = new JTextArea(5, 30);
        displayArea.setEditable(false);

        // Add action listeners
        withdrawButton.addActionListener(this);
        depositButton.addActionListener(this);
        balanceButton.addActionListener(this);

        // Set up the layout
        JPanel panel = new JPanel();
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(balanceButton);

        // Add components to the frame
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        String amountText = amountField.getText();
        double amount;

        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid amount. Please enter a valid number.\n");
            return;
        }

        switch (command) {
            case "Withdraw":
                if (atm.withdraw(amount)) {
                    displayArea.append("Successfully withdrew $" + amount + ".\n");
                } else {
                    displayArea.append("Insufficient balance or invalid amount.\n");
                }
                break;
            case "Deposit":
                if (atm.deposit(amount)) {
                    displayArea.append("Successfully deposited $" + amount + ".\n");
                } else {
                    displayArea.append("Invalid deposit amount.\n");
                }
                break;
            case "Check Balance":
                double balance = atm.checkBalance();
                displayArea.append("Current balance: $" + balance + "\n");
                break;
        }

        amountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMGUI atmGUI = new ATMGUI();
            atmGUI.setVisible(true);
        });
    }
}

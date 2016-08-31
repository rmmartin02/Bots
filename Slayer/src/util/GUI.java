package util;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private static int foodAmount;
    private static int cowhideAmount;
    private static String food;
    private static int eatAt;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GUI() {
        setTitle("Swik's Slayer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 275, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblFoodId = new JLabel("Food Name:");
        lblFoodId.setBounds(10, 25, 128, 14);
        contentPane.add(lblFoodId);

        textField = new JTextField();
        textField.setBounds(148, 22, 101, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblAmountToWithdraw = new JLabel("Food to Withdraw:");
        lblAmountToWithdraw.setBounds(10, 50, 128, 14);
        contentPane.add(lblAmountToWithdraw);

        textField_1 = new JTextField();
        textField_1.setBounds(148, 47, 101, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblEatAt = new JLabel("Eat At:");
        lblEatAt.setBounds(10, 75, 128, 14);
        contentPane.add(lblEatAt);

        textField_2 = new JTextField();
        textField_2.setBounds(148, 72, 101, 20);
        contentPane.add(textField_2);
        textField_2.setColumns(10);


        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                food = textField.getText();
                foodAmount = Integer.valueOf(textField_1.getText());
                eatAt = Integer.valueOf(textField_2.getText());
                setVisible(false);
            }
        });
        btnStart.setBounds(82, 128, 89, 23);
        contentPane.add(btnStart);
    }

    public static String getFood() {
        return food;
    }

    public static int getFoodAmount() {
        return foodAmount;
    }

    public static int getEatAt() {
        return eatAt;
    }

    public static int getCowhideAmount() {
        return cowhideAmount;
    }
}


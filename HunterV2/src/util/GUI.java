package util;

import org.tbot.methods.Skills;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


/**
 * Created by Russell on 2/12/2016.
 */
public class GUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton startButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    int selectedHunt;
    int selectedType;
    int selectedPattern;
    private String[] HuntOptions = {"Birds", "Falconry", "Salamanders", "Chinchompas"};
    private String[] birds = {Var.CRIMSON_SWIFT.getHunting(), Var.COPPER_LONGTAIL.getHunting(), Var.CERULEAN_TWITCH.getHunting(), Var.TROPICAL_WAGTAIL.getHunting()};
    private String[] sals = {Var.GREEN_SAL.getHunting(),Var.ORANGE_SAL.getHunting(), Var.RED_SAL.getHunting(), Var.BLACK_SAL.getHunting()};
    private String[] kebs = {Var.SPOTTED_KEBBIT.getHunting(), Var.DARK_KEBBIT.getHunting(),Var.DASHING_KEBBIT.getHunting()};
    private String[] chins = {Var.GREY_CHINS.getHunting(), Var.RED_CHINS.getHunting()};
    private String[] trap3Options = {"Triangle", "Vertical Line", "Horizontal Line", "Diagonal Line"};
    private String[] trap4Options = {"Box", "Diamond", "Vertical Line", "Horizontal Line", "Diagonal Line"};
    private String[] trap5Options = {"X", "Vertical Line", "Horizontal Line", "Diagonal Line"};

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

    public GUI() {

        setTitle("Swik's Hunter");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 374, 443);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbl3 = new JLabel("Hunting");
        lbl3.setBounds(143,14,128,14);
        contentPane.add(lbl3);

        comboBox3 = new JComboBox(new DefaultComboBoxModel<>(HuntOptions));
        comboBox3.setBounds(100,41,150,20);
        contentPane.add(comboBox3);

        JLabel lbl2 = new JLabel("Type");
        lbl2.setBounds(156,77,128,14);
        contentPane.add(lbl2);

        comboBox2 = new JComboBox(new DefaultComboBoxModel<>());
        comboBox2.setBounds(100,104,150,20);
        contentPane.add(comboBox2);

        JLabel lbl1 = new JLabel("Pattern");
        lbl1.setBounds(143,141,128,14);
        contentPane.add(lbl1);
        lbl1.setVisible((comboBox3.getSelectedIndex()==0 || comboBox3.getSelectedIndex()==1) && Skills.getCurrentLevel(Skills.Skill.HUNTER)>=40);

        comboBox1 = new JComboBox(new DefaultComboBoxModel<>());
        comboBox1.setBounds(100,168,150,20);
        contentPane.add(comboBox1);
        comboBox1.setVisible((comboBox3.getSelectedIndex()==0 || comboBox3.getSelectedIndex()==1) && Skills.getCurrentLevel(Skills.Skill.HUNTER)>=40);

        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox1.setVisible(comboBox3.getSelectedIndex()==0 || comboBox3.getSelectedIndex()==3);
                if(comboBox3.getSelectedIndex()==0){
                    comboBox2.setModel(new DefaultComboBoxModel<>(birds));
                    if(Var.numTraps()>2){
                        comboBox1.setModel(getPatternOptions());
                    }
                }
                if(comboBox3.getSelectedIndex()==1){
                    comboBox2.setModel(new DefaultComboBoxModel<>(kebs));
                }
                if(comboBox3.getSelectedIndex()==2){
                    comboBox2.setModel(new DefaultComboBoxModel<>(sals));
                }
                if(comboBox3.getSelectedIndex()==3){
                    comboBox2.setModel(new DefaultComboBoxModel<>(chins));
                    if(Var.numTraps()>2){
                        comboBox1.setModel(getPatternOptions());
                    }
                }
            }
        });

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        startButton.setBounds(145,207,89,23);
        contentPane.add(startButton);
    }

    public void setVariables(){
        int selectedHunt = comboBox3.getSelectedIndex();
        int selectedType = comboBox2.getSelectedIndex();
        int selectedPattern = comboBox1.getSelectedIndex();
        if(selectedHunt==0){
            if(selectedType==0){
                Var.setCurrentVar(Var.CRIMSON_SWIFT);
            }
            else if(selectedType==1){
                Var.setCurrentVar(Var.COPPER_LONGTAIL);
            }
            else if(selectedType==2){
                Var.setCurrentVar(Var.CERULEAN_TWITCH);
            }
            else if(selectedType==3){
                Var.setCurrentVar(Var.TROPICAL_WAGTAIL);
            }
            TrapLocations.getInstance().setTrapLocations(selectedPattern);
        }
        if(selectedHunt==1){
            if(selectedType==0){
                Var.setCurrentVar(Var.SPOTTED_KEBBIT);
            }
            else if(selectedType==1){
                Var.setCurrentVar(Var.DARK_KEBBIT);
            }
            else if(selectedType==2){
                Var.setCurrentVar(Var.DASHING_KEBBIT);
            }
        }
        if(selectedHunt==2){
            if(selectedType==0){
                Var.setCurrentVar(Var.GREEN_SAL);
            }
            else if(selectedType==1){
                Var.setCurrentVar(Var.ORANGE_SAL);
            }
            else if(selectedType==2){
                Var.setCurrentVar(Var.RED_SAL);
            }
            else if(selectedType==3){
                Var.setCurrentVar(Var.BLACK_SAL);
            }
            TrapLocations.getInstance().setSalTrapLocations();
        }
        if(selectedHunt==3){
            if(selectedType==0){
                Var.setCurrentVar(Var.GREY_CHINS);
            }
            else if(selectedType==1){
                Var.setCurrentVar(Var.RED_CHINS);
            }
            TrapLocations.getInstance().setTrapLocations(selectedPattern);
        }
    }

    private ComboBoxModel getPatternOptions(){
        if(Var.numTraps()==5){
            return new DefaultComboBoxModel<>(trap5Options);
        }
        else if(Var.numTraps()==4){
            return new DefaultComboBoxModel<>(trap4Options);
        }
        return new DefaultComboBoxModel<>(trap3Options);
    }
}


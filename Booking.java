import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Booking extends JFrame {
    private JRadioButton oneWay, roundTrip;
    private ButtonGroup bg;
    private JPanel radioPanel, panel2, panel3, panel4, panel5, panel6;
    private int width, height;
    private JLabel l1, l2,nameLabel,errorLabel1,errorLabel2,errorLabel3;
    private JComboBox<String> from, to;
    private String selectedFrom;
    private JDateChooser dateChooser,dateChooser1,dateChooser2;
    private JButton button,button2;
    private Font boldFont;
    public Booking(String name) {
        width = 660;
        height = 640;
        setBounds(200, 200, width, height);
        setUndecorated(false);
        setLayout(null);

        oneWay = new JRadioButton("ONE-WAY");
        oneWay.setFocusable(false);
        oneWay.setOpaque(false);
        oneWay.setSelected(true);
        oneWay.setForeground(Color.WHITE);

        roundTrip = new JRadioButton("ROUND-TRIP");
        roundTrip.setFocusable(false);
        roundTrip.setOpaque(false);
        roundTrip.setForeground(Color.WHITE);

        bg = new ButtonGroup();
        bg.add(oneWay);
        bg.add(roundTrip);

        radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));
        radioPanel.setBackground(Color.red);
        radioPanel.setBounds(70, height - 500, width - 150, 50);

        radioPanel.add(oneWay);
        radioPanel.add(roundTrip);

        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(Color.white);
        panel2.setBounds(70, height - 400, width - 405, 50);
        panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBackground(Color.white);
        panel3.setBounds(295, height - 400, width - 375, 50);

        ArrayList<String> cities = new ArrayList<>();
        cities.add("From");
        cities.add("Lahore");
        cities.add("Islamabad");
        cities.add("Karachi");
        cities.add("Multan");
        cities.add("Faislabad");
        cities.add("Sakardu");
        cities.add("Peshawar");
        cities.add("Quetta");

        String[] citiesArray = new String[cities.size()];
        citiesArray = cities.toArray(citiesArray);

        from = new JComboBox<>(citiesArray);
        from.setBounds(2, 2, 251, 46);
        from.setFocusable(false);
        from.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                from.removeItem("From");
                updateToComboBox();
            }
        });

        panel2.add(from);

        cities.remove(0); 
        cities.add(0, "To");
        citiesArray = cities.toArray(new String[0]);

        to = new JComboBox<>(citiesArray);
        to.setBounds(30, 2, 253, 46);
        to.setFocusable(false);
        selectedFrom = (String) from.getSelectedItem();
        if(selectedFrom=="From"){
            to.setEnabled(false);
        }
        to.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                to.removeItem("To");
            }
        });
        panel3.add(to);
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(2, 2, 253, 46);
        dateChooser.setDate(new Date());
        styleDateChooser(dateChooser);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        panel4 = new JPanel();
        panel4.setLayout(null);
        panel4.setBackground(Color.white);
        panel4.setBounds(70, height - 300, width - 150, 50);
        panel4.add(dateChooser);

        l1 = new JLabel("Today");
        l1.setForeground(Color.red);
        l1.setBounds(380,0,50,50);
        l1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                l1.setForeground(Color.red);
                l2.setForeground(Color.lightGray);
                dateChooser.setDate(new Date());
            }
        });

        l2 = new JLabel("Tomorrow");
        l2.setForeground(Color.lightGray);
        l2.setBounds(430,0,100,50);
        l2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                l2.setForeground(Color.red);
                l1.setForeground(Color.lightGray);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, 1);
                dateChooser.setDate(cal.getTime());
            }
        });
        
        panel4.add(l1);
        panel4.add(l2);

        panel5 = new JPanel();
        panel5.setLayout(null);
        panel5.setBackground(Color.white);
        panel5.setBounds(70, height - 300, width - 405, 50);

        panel6 = new JPanel();
        panel6.setLayout(null);
        panel6.setBackground(Color.white);
        panel6.setBounds(70, height - 300, width - 150, 50);

        panel2.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        panel3.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        panel4.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        panel5.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        panel6.setBorder(BorderFactory.createLineBorder(Color.gray, 2));


        errorLabel1 = new JLabel();
        errorLabel1.setText("*Please Select the Depature and Destination!");
        errorLabel1.setBounds(80, height - 150, 300, 50);
        errorLabel1.setForeground(Color.RED);
        boldFont = new Font(errorLabel1.getFont().getName(), Font.BOLD, 12);
        errorLabel1.setFont(boldFont);
        errorLabel2 = new JLabel();
        errorLabel2.setText("*Please Select the Destination!");
        errorLabel2.setBounds(80, height - 150, 300, 50);
        errorLabel2.setForeground(Color.RED);
        boldFont = new Font(errorLabel2.getFont().getName(), Font.BOLD, 12);
        errorLabel2.setFont(boldFont);
        errorLabel3 = new JLabel();
        errorLabel3.setText("*Please Select the Return Date!");
        errorLabel3.setBounds(80, height - 150, 300, 50);
        errorLabel3.setForeground(Color.RED);
        boldFont = new Font(errorLabel3.getFont().getName(), Font.BOLD, 12);
        errorLabel3.setFont(boldFont);
        button = new JButton();
        button.setText("Search");
        button.setBounds(70, height - 200, 250, 50);
        button.setBackground(Color.decode("#F2AF13")); 
        button.setForeground(Color.white);
        
        int newSize = 16; 
        boldFont = new Font(button.getFont().getName(), Font.BOLD, newSize);
        button.setFont(boldFont);
        button2 = new JButton();
        button2.setText("My Bookings");
        button2.setBounds(330, height - 200, 250, 50);
        button2.setBackground(Color.decode("#F2AF13")); 
        button2.setForeground(Color.white);
        
        newSize = 16; 
        boldFont = new Font(button.getFont().getName(), Font.BOLD, newSize);
        button2.setFont(boldFont);
        
        nameLabel = new JLabel();
        nameLabel.setText("Hi, " + name+"!");
        nameLabel.setBounds(50, 50, 200, 50);
        nameLabel.setForeground(Color.BLACK);

        int nameLabelFontSize = 18; 
        Font nameLabelFont = new Font(nameLabel.getFont().getName(),Font.PLAIN , nameLabelFontSize);
        nameLabel.setFont(nameLabelFont);
        add(nameLabel);
        add(radioPanel);
        add(panel2);
        add(panel3);
        add(panel4);
        add(panel5);
        add(panel6);
        add(button);
        add(button2);
        roundTrip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roundTrip.isSelected()) {
                    dateChooser1 = new JDateChooser();
                    dateChooser1.setBounds(2, 2, 250, 45);
                    dateChooser1.setDate(new Date());
                    styleDateChooser(dateChooser1);
                    panel5.add(dateChooser1);
            
                    dateChooser2 = new JDateChooser();
                    dateChooser2.setBounds(255, 2, 252,45);
                    styleDateChooser(dateChooser2);
                    
                    panel6.add(dateChooser2);
                    panel4.setVisible(false);
                    panel5.setVisible(true);
                    panel6.setVisible(true);
                }
            }
        });

        oneWay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (oneWay.isSelected()) {
                    panel4.setVisible(true);
                    panel5.setVisible(false);
                    panel6.setVisible(false);
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roundTrip.isSelected()) { 
                    if (selectedFrom.equals("From")) {
                        add(errorLabel1);
                    } else if (to.getSelectedItem().equals("To")) {
                        remove(errorLabel1);
                        add(errorLabel2);
                    } else if (dateChooser2.getDate() == null) {
                        remove(errorLabel1);
                        remove(errorLabel2);
                        add(errorLabel3);
                    } else {
                        remove(errorLabel1);
                        remove(errorLabel2);
                        remove(errorLabel3);
                        dispose();
                        new Schedule(dateChooser1.getDate(), (String) from.getSelectedItem(), (String) to.getSelectedItem());

                    }
                } else if (oneWay.isSelected()) {
                    if (selectedFrom.equals("From")) {
                        add(errorLabel1);
                    } else if (to.getSelectedItem().equals("To")) {
                        remove(errorLabel1);
                        add(errorLabel2);
                    } else {
                        remove(errorLabel1);
                        remove(errorLabel2);
                        dispose();
                        new Schedule(dateChooser.getDate(), (String) from.getSelectedItem(), (String) to.getSelectedItem());
                    }
                } else {
                    remove(errorLabel1);
                    remove(errorLabel2);
                    remove(errorLabel3);
                    
                }
                repaint();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new myBookings(name);
            }
        });
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateToComboBox() {
        ArrayList<String> cities = new ArrayList<>();
        cities.add("To");
        cities.add("Lahore");
        cities.add("Islamabad");
        cities.add("Karachi");
        cities.add("Multan");
        cities.add("Faislabad");
        cities.add("Sakardu");
        cities.add("Peshawar");
        cities.add("Quetta");

        selectedFrom = (String) from.getSelectedItem();
        if (cities.contains(selectedFrom)) {
            cities.remove(selectedFrom);
        }

        to.setModel(new DefaultComboBoxModel<>(cities.toArray(new String[0])));
        to.setEnabled(true);
    }
    private void styleDateChooser(JDateChooser dateChooser) {
        dateChooser.setMinSelectableDate(new Date());
        
        dateChooser.setFocusable(false);
        dateChooser.setDateFormatString("dd MMM,yyyy");
        JTextFieldDateEditor editor=(JTextFieldDateEditor) dateChooser.getDateEditor();
        styleDateChooserEditor(editor);
    }
    private void styleDateChooserEditor(JTextFieldDateEditor editor) {
        editor.setEditable(false);
        editor.setCursor(null);
    }
    
}

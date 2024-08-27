import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassengerDetails extends JFrame {
    private JLabel flightLabel;
    private String flightId, origin, destination, price, departureTime, arrivalTime;
    private JRadioButton Vaccinated, Not_Vaccinated;
    private ButtonGroup bg;
    private JPanel radioPanel, panel2;
    private int width, height;
    private JLabel mainLabel, nameLabel, cnicLabel, phoneLabel, emailLabel, label;
    private JTextField nameField, phoneField, emailField;
    private JFormattedTextField cnicField;
    private JButton saveButton;
    private int maxPassengerCount;
    private int currentPassengerIndex;
    private Schedule scheduleWindow;

    public PassengerDetails(String flightId, String origin, String destination, String price, String departureTime, String arrivalTime, int maxPassengerCount, int currentPassengerIndex, Schedule scheduleWindow) {
        this.flightId = flightId;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.maxPassengerCount = maxPassengerCount;
        this.currentPassengerIndex = currentPassengerIndex;
        this.scheduleWindow = scheduleWindow;

        width = 660;
        height = 640;
        setBounds(200, 200, width, height);
        setUndecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        mainLabel = new JLabel("Passenger " + (currentPassengerIndex + 1));
        mainLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainLabel.setBounds(70, 30, width - 150, 50);
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);

        flightLabel = new JLabel("Flight ID: " + flightId);
        flightLabel.setFont(new Font("Arial", Font.BOLD, 18));
        flightLabel.setBounds(70, 65, width - 150, 50);
        flightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Vaccinated = new JRadioButton("Vaccinated");
        Vaccinated.setFocusable(false);
        Vaccinated.setOpaque(false);
        Vaccinated.setSelected(true);
        Vaccinated.setForeground(Color.WHITE);

        Not_Vaccinated = new JRadioButton("Not Vaccinated");
        Not_Vaccinated.setFocusable(false);
        Not_Vaccinated.setOpaque(false);
        Not_Vaccinated.setForeground(Color.WHITE);

        bg = new ButtonGroup();
        bg.add(Vaccinated);
        bg.add(Not_Vaccinated);

        radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));
        radioPanel.setBackground(Color.RED);
        radioPanel.setBounds(70, height - 500, width - 150, 50);
        radioPanel.add(Vaccinated);
        radioPanel.add(Not_Vaccinated);

        nameLabel = new JLabel("Name:");
        cnicLabel = new JLabel("CNIC:");
        phoneLabel = new JLabel("Phone Number:");
        emailLabel = new JLabel("Email:");

        nameField = new JTextField(10);
        phoneField = new JTextField(10);
        emailField = new JTextField(10);

        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("#####-#######-#");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cnicField = new JFormattedTextField(formatter);
        cnicField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String cnic = cnicField.getText().replaceAll("-", "");
                StringBuilder formattedCNIC = new StringBuilder();
                for (int i = 0; i < cnic.length(); i++) {
                    if (i == 5 || i == 12) {
                        formattedCNIC.append("-");
                    }
                    formattedCNIC.append(cnic.charAt(i));
                }
                cnicField.setText(formattedCNIC.toString());
            }
        });

        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(4, 2, 5, 5));
        panel2.setBounds(70, height - 400, width - 150, 130);

        panel2.add(nameLabel);
        panel2.add(nameField);
        panel2.add(cnicLabel);
        panel2.add(cnicField);
        panel2.add(phoneLabel);
        panel2.add(phoneField);
        panel2.add(emailLabel);
        panel2.add(emailField);

        label = new JLabel("These Details will be used to verify your identity at the airport.");
        label.setBounds(150, 90, width -150, 50);
        label.setForeground(Color.BLACK);
        label.setOpaque(false);

        saveButton = new JButton("Save");
        saveButton.setBounds(280, height - 250, 100, 30);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validdata();
            }
        });

        add(mainLabel);
        add(flightLabel);
        add(label);
        add(radioPanel);
        add(panel2);
        add(saveButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void validdata() {
        String name = nameField.getText();
        String cnic = cnicField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String vaccinatedStatus = Vaccinated.isSelected() ? "Vaccinated" : "Not Vaccinated";

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || cnic.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String phoneValidationResult = isValidPhoneNumber(phone);
        String emailValidationResult = isValidEmail(email);

        if ("success".equals(phoneValidationResult) && "success".equals(emailValidationResult)) {
            savePassengerData(name, cnic, phone, email, vaccinatedStatus);
        } else {
            String errorMessage = "\nPhone: " + phoneValidationResult + "\nEmail: " + emailValidationResult;
            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void savePassengerData(String name, String cnic, String phone, String email, String vaccinatedStatus) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("passenger_data.txt", true))) {
            int passengerId = generatePassengerId();
            writer.write("Passenger ID: " + passengerId);
            writer.newLine();
            writer.write("Name: " + name);
            writer.newLine();
            writer.write("CNIC: " + cnic);
            writer.newLine();
            writer.write("Phone: " + phone);
            writer.newLine();
            writer.write("Email: " + email);
            writer.newLine();
            writer.write("Vaccinated: " + vaccinatedStatus);
            writer.newLine();
            writer.write("Flight ID: " + flightId);
            writer.newLine();
            writer.write("Origin: " + origin);
            writer.newLine();
            writer.write("Destination: " + destination);
            writer.newLine();
            writer.write("Price: " + price);
            writer.newLine();
            writer.write("Departure Time: " + departureTime);
            writer.newLine();
            writer.write("Arrival Time: " + arrivalTime);
            writer.newLine();
            writer.write("Profile: " + getUsername());
            writer.newLine();
            writer.write("--------------");
            writer.newLine();
            JOptionPane.showMessageDialog(this,name + " Passenger ID is: " + passengerId);
            dispose();
            if (currentPassengerIndex + 1 < maxPassengerCount) {
                new PassengerDetails(flightId, origin, destination, price, departureTime, arrivalTime, maxPassengerCount, currentPassengerIndex + 1, scheduleWindow);
            } else {
                double totalPrice = Double.parseDouble(price) * maxPassengerCount;
                new Payment(flightId, origin, destination, totalPrice);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private int generatePassengerId() {
        Random random = new Random();
        return random.nextInt(9000);
    }

    public String isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("0\\d{10}")) {
            return "success";
        } else {
            return "Phone number should start with 0, be 11 digits long, and contain only digits.";
        }
    }
    private String getUsername(){
        String username = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("loggedIn.txt"))) {
            username = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace(); // You might want to handle the exception more gracefully in a real application
        }
        return username;
    }
    public String isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.com$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return "success";
        } else {
            return "Email should be in a valid format and contain '.com'.";
        }
    }
}
 

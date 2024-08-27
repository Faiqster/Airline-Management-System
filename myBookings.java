import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class myBookings extends JFrame {
    private List<Passenger> passengers;

    public myBookings(String n) {
        setUndecorated(true); // Remove the title bar
        passengers = new ArrayList<>();
        loadPassengers("passenger_data.txt");
        displayPassengers(n);

        setSize(660, 640);
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void loadPassengers(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Passenger ID:")) {
                    String passengerID = line.split(": ")[1];
                    String name = br.readLine().split(": ")[1];
                    String cnic = br.readLine().split(": ")[1];
                    String phone = br.readLine().split(": ")[1];
                    String email = br.readLine().split(": ")[1];
                    String vaccinated = br.readLine().split(": ")[1];
                    String flightID = br.readLine().split(": ")[1];
                    String origin = br.readLine().split(": ")[1];
                    String destination = br.readLine().split(": ")[1];
                    String price = br.readLine().split(": ")[1];
                    String departureTime = br.readLine().split(": ")[1];
                    String arrivalTime = br.readLine().split(": ")[1];
                    String profile = br.readLine().split(": ")[1];
                    br.readLine(); // Read the separator line
                    passengers.add(new Passenger(passengerID, name, cnic, phone, email, vaccinated, flightID, origin, destination, price, departureTime, arrivalTime, profile));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayPassengers(String profile) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(passengers.size(), 1)); // Adjusted to single column layout
        JScrollPane scrollPane = new JScrollPane(mainPanel);

        // Add the back button
        JLabel backButton = new JLabel("< Back");
        backButton.setBounds(10, 10, 95, 30);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Booking(getUsername()); // Replace with the actual frame you want to go back to
            }
        });

        // Add the back button to a separate panel at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null); // Use absolute layout for precise placement
        topPanel.add(backButton);
        topPanel.setPreferredSize(new Dimension(660, 50));

        for (Passenger passenger : passengers) {
            if (passenger.profile.equalsIgnoreCase(profile)) {
                JPanel passengerPanel = createPassengerPanel(passenger);
                mainPanel.add(passengerPanel);
            }
        }

        // Set preferred size for the main panel to fit all tickets
        mainPanel.setPreferredSize(new Dimension(600, passengers.size() * 200));

        // Add the top panel and scroll pane to the frame
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createPassengerPanel(Passenger passenger) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));
        JLabel passengerIDLabel = new JLabel("Passenger ID: " + passenger.passengerID);
        JLabel nameLabel = new JLabel("Name: " + passenger.name);
        JLabel flightIDLabel = new JLabel("Flight ID: " + passenger.flightID);
        JLabel originLabel = new JLabel("Departure: " + passenger.origin);
        JLabel destinationLabel = new JLabel("Destination: " + passenger.destination);
        JLabel priceLabel = new JLabel("Price: " + passenger.price);
        JLabel departureTimeLabel = new JLabel("Departure Time: " + passenger.departureTime);

        panel.add(passengerIDLabel);
        panel.add(flightIDLabel);
        panel.add(nameLabel);
        panel.add(originLabel);
        panel.add(destinationLabel);
        panel.add(priceLabel);
        panel.add(departureTimeLabel);

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return panel;
    }

    private String getUsername() {
        String username = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("loggedIn.txt"))) {
            username = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace(); // You might want to handle the exception more gracefully in a real application
        }
        return username;
    }

    public static void main(String[] args) {
        // Example usage
        new myBookings("exampleProfile");
    }
}

// Assume Passenger class is defined somewhere with relevant fields and constructor
class Passenger {
    String passengerID, name, cnic, phone, email, vaccinated, flightID, origin, destination, price, departureTime, arrivalTime, profile;

    public Passenger(String passengerID, String name, String cnic, String phone, String email, String vaccinated, String flightID,
                     String origin, String destination, String price, String departureTime, String arrivalTime, String profile) {
        this.passengerID = passengerID;
        this.name = name;
        this.cnic = cnic;
        this.phone = phone;
        this.email = email;
        this.vaccinated = vaccinated;
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.profile = profile;
    }
}

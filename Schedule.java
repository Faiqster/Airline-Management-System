import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Schedule extends JFrame {
    private JLabel dateLabel, fromLabel, toLabel, dateValueLabel, fromValueLabel, toValueLabel;
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private Date selectedDate;
    private String selectedFrom, selectedTo;

    public Schedule(Date date, String from, String to) {
        setUndecorated(true); // Remove the title bar
        this.selectedDate = date;
        this.selectedFrom = from;
        this.selectedTo = to;

        setTitle("Flight Schedule");
        setSize(660, 640);
        setLayout(new BorderLayout());

        dateLabel = new JLabel("Selected Date:");
        dateValueLabel = new JLabel();
        fromLabel = new JLabel("From: ");
        fromValueLabel = new JLabel();
        toLabel = new JLabel("To: ");
        toValueLabel = new JLabel();

        String[] columnNames = {"Flight ID", "Origin", "Destination", "Price", "Departure Time", "Arrival Time"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scheduleTable = new JTable(tableModel);
        scheduleTable.setRowHeight(30);
        scheduleTable.setFont(new Font("Arial", Font.PLAIN, 14));
        scheduleTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        scheduleTable.getTableHeader().setBackground(Color.RED);

        scheduleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = scheduleTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String[] flightData = new String[tableModel.getColumnCount()];
                        for (int i = 0; i < tableModel.getColumnCount(); i++) {
                            flightData[i] = (String) tableModel.getValueAt(selectedRow, i);
                        }
                        dispose();
                        openPassengerWindow(flightData);
                    }
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(dateLabel);
        panel.add(dateValueLabel);
        panel.add(fromLabel);
        panel.add(fromValueLabel);
        panel.add(toLabel);
        panel.add(toValueLabel);

        // Add the back button
        JLabel backButton = new JLabel("< Back");
        backButton.setBounds(10, 10, 95, 30);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new Booking(getUsername());
            }
        });

        // Add the back button to a separate panel at the top
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null); // Use absolute layout for precise placement
        topPanel.add(backButton);
        topPanel.setPreferredSize(new Dimension(800, 50));

        add(topPanel, BorderLayout.NORTH); // Add the top panel to the frame
        add(panel, BorderLayout.CENTER); // Add the panel to the center of the frame
        add(new JScrollPane(scheduleTable), BorderLayout.SOUTH); // Add the table to the bottom of the frame

        setSelectedDate(selectedDate);
        fromValueLabel.setText(selectedFrom);
        toValueLabel.setText(selectedTo);

        setLocationRelativeTo(null);
        setVisible(true);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        displaySchedulesForDate(selectedDate, selectedFrom, selectedTo);
    }

    public void setSelectedDate(Date date) {
        selectedDate = date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateValueLabel.setText(dateFormat.format(date));
    }

    private void displaySchedulesForDate(Date date, String from, String to) {
        List<String[]> schedules = getSchedulesForDate(date, from, to);
        tableModel.setRowCount(0);
        if (schedules.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights scheduled for this date.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (String[] schedule : schedules) {
                tableModel.addRow(schedule);
            }
        }
    }

    private List<String[]> getSchedulesForDate(Date date, String from, String to) {
        List<String[]> schedules = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader("schedule.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String scheduleDateStr = parts[4].split(" ")[0];
                    String scheduleFrom = parts[1];
                    String scheduleTo = parts[2];
                    if (scheduleDateStr.equals(dateFormat.format(date)) && scheduleFrom.equals(from) && scheduleTo.equals(to)) {
                        schedules.add(parts);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading schedules file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return schedules;
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
    private void openPassengerWindow(String[] flightData) {
        int maxPassengerCount = Integer.parseInt(JOptionPane.showInputDialog("Enter number of passengers:"));
        new PassengerDetails(flightData[0], flightData[1], flightData[2], flightData[3], flightData[4], flightData[5], maxPassengerCount, 0, this);
    }
}



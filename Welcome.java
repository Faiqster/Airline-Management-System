import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame implements ActionListener {
    private JLabel L1;
    private JButton LB, SB;
    private JPanel P;

    public Welcome() {
        setLocation(350, 200);
        setTitle("Airline Management System");
        setSize(600, 410);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("air.jpg");
        Image image = img.getImage().getScaledInstance(600, 550, Image.SCALE_SMOOTH);
        ImageIcon scaledicon = new ImageIcon(image);
        JLabel background = new JLabel(scaledicon);
        background.setLayout(new BorderLayout());
        setContentPane(background);
        L1 = new JLabel("Welcome To Airlines Management System", JLabel.CENTER);
        L1.setFont(new Font("Airlines", Font.BOLD, 25));
        L1.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(200, 30);

        LB = new JButton("Sign in");
        LB.setPreferredSize(buttonSize);
        LB.setMaximumSize(buttonSize);
        LB.setForeground(Color.RED);
        LB.setFocusPainted(false);
        LB.setFont(new Font("Airlines", Font.BOLD, 16));
        LB.setAlignmentX(Component.CENTER_ALIGNMENT);
        LB.addActionListener(this); 

        SB = new JButton("Sign up");
        SB.setPreferredSize(buttonSize);
        SB.setMaximumSize(buttonSize);
        SB.setForeground(Color.RED);
        SB.setFocusPainted(false);
        SB.setFont(new Font("Airlines", Font.BOLD, 16));
        SB.setAlignmentX(Component.CENTER_ALIGNMENT);
        SB.addActionListener(this);

        // Create panel and add components
        P = new JPanel();
        P.setOpaque(false); // Make the panel transparent
        P.setLayout(new BoxLayout(P, BoxLayout.Y_AXIS));
        P.add(Box.createRigidArea(new Dimension(0, 20)));
        P.add(L1);
        P.add(Box.createRigidArea(new Dimension(0, 20)));
        P.add(LB);
        P.add(Box.createRigidArea(new Dimension(0, 20)));
        P.add(SB);

        // Add the panel to the background label
        background.add(P, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == LB) {
            dispose();
            new Login();
        } else if (e.getSource() == SB) {
            dispose();
            new Signup();
        }
    }
}
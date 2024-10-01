

package com.mycompany.jobs_crawler;
import javax.swing.*;
/**
 *
 * @author MICHAŁ
 */
public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginForm() {
        initComponents();
        addLoginComponents();  // Dodanie komponentów po zainicjowaniu ramki
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents



    private void addLoginComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        loginButton.addActionListener(e -> performLogin());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Login:"));
        panel.add(usernameField);
        panel.add(new JLabel("Hasło:"));
        panel.add(passwordField);
        panel.add(loginButton);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(panel);

        revalidate(); // Odświeżenie ramki, aby pokazać nowe komponenty
        repaint();    // Przemalowanie ramki, aby wszystko było wyświetlone poprawnie
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if ("admin".equals(username) && "admin".equals(password)) {
            JOptionPane.showMessageDialog(this, "Logowanie do JobInsight");
            this.setVisible(false);  // Ukryj okno logowania
            new MainAppWindow().setVisible(true);  // Pokaż główne okno aplikacji
        } else {
            JOptionPane.showMessageDialog(this, "Błędny login lub hasło!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


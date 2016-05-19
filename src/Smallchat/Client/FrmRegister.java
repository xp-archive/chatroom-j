package Smallchat.Client;

import Smallchat.Action.Request.RegisterRequest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class FrmRegister extends JFrame {

    private volatile static FrmRegister instance = null;

    public static FrmRegister getInstance() {
        if (instance == null) {
            synchronized (FrmRegister.class) {
                if (instance == null) {
                    instance = new FrmRegister();
                }
            }
        }
        return instance;
    }

    JLabel lblUsername;
    JTextField fieldUsername;
    JLabel lblPassword;
    JPasswordField fieldPassword;
    JButton btnLogin;
    JButton btnRegister;

    private FrmRegister() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(null);

        lblUsername = new JLabel("用户名");
        lblUsername.setBounds(120, 30, 120, 30);
        panel.add(lblUsername);

        fieldUsername = new JTextField();
        fieldUsername.setBounds(115, 60, 240, 30);
        panel.add(fieldUsername);

        lblPassword = new JLabel("密码");
        lblPassword.setBounds(120, 90, 120, 30);
        panel.add(lblPassword);

        fieldPassword = new JPasswordField();
        fieldPassword.setEchoChar('*');
        fieldPassword.setBounds(115, 120, 240, 30);
        panel.add(fieldPassword);

        btnLogin = new JButton("登录");
        btnLogin.setBounds(115, 160, 60, 40);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FrmLogin.getInstance().setVisible(true);
            }
        });
        panel.add(btnLogin);

        btnRegister = new JButton("注册");
        btnRegister.setBounds(180, 160, 80, 40);
        btnRegister.setDefaultCapable(true);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterRequest request = new RegisterRequest(
                        fieldUsername.getText(),
                        new String(fieldPassword.getPassword())
                );
                Client.getInstance().send(request);
            }
        });
        panel.add(btnRegister);

        this.getContentPane().add(panel);
        this.getRootPane().setDefaultButton(btnRegister);

        this.setTitle("注册");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 480, 300);
        this.setResizable(false);
    }

    public void onRegisterFailure(String username) {
        JOptionPane.showMessageDialog(this, "注册失败, 请重试.");
    }

}

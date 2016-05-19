package Smallchat.Client;

import Smallchat.Action.Request.LoginRequest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class FrmLogin extends JFrame {

    private volatile static FrmLogin instance = null;

    public static FrmLogin getInstance() {
        if (instance == null) {
            synchronized (FrmLogin.class) {
                if (instance == null) {
                    instance = new FrmLogin();
                }
            }
        }
        return instance;
    }

    JLabel lblUsername;
    JTextField fieldUsername;
    JLabel lblPassword;
    JPasswordField fieldPassword;
    JButton btnRegister;
    JButton btnLogin;

    private FrmLogin() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(null);

        lblUsername = new JLabel("用户名");
        lblUsername.setBounds(120, 30, 120, 30);
        panel.add(lblUsername);

        fieldUsername = new JTextField("xp");
        fieldUsername.setBounds(115, 60, 240, 30);
        panel.add(fieldUsername);

        lblPassword = new JLabel("密码");
        lblPassword.setBounds(120, 90, 120, 30);
        panel.add(lblPassword);

        fieldPassword = new JPasswordField("19531");
        fieldPassword.setEchoChar('*');
        fieldPassword.setBounds(115, 120, 240, 30);
        panel.add(fieldPassword);

        btnRegister = new JButton("注册");
        btnRegister.setBounds(115, 160, 60, 40);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FrmRegister.getInstance().setVisible(true);
            }
        });
        panel.add(btnRegister);

        btnLogin = new JButton("登录");
        btnLogin.setBounds(180, 160, 80, 40);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginRequest request = new LoginRequest(
                        fieldUsername.getText(),
                        new String(fieldPassword.getPassword())
                );
                Client.getInstance().send(request);
            }
        });
        panel.add(btnLogin);

        this.getContentPane().add(panel);
        this.getRootPane().setDefaultButton(btnLogin);

        this.setTitle("登录");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 480, 300);
        this.setResizable(false);
    }

    public void onLoginFailure(String username) {
        JOptionPane.showMessageDialog(this, "登录失败, 请重试.");
    }

}

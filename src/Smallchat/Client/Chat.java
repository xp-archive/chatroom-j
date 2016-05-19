package Smallchat.Client;

import Smallchat.Action.Request.UnicastRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat {

    private int userId;
    private String username;
    private boolean initialized;

    private FrmChat frm;

    public Chat() {
        initialized = false;

        frm = new FrmChat();
        frm.btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UnicastRequest request = new UnicastRequest(userId, frm.fieldSend.getText());
                frm.fieldSend.setText(null);
                frm.fieldMessages.append(String.format("我: %s\r\n", request.getMessage()));

                Client.getInstance().send(request);
            }
        });
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
        onInfoChanged();
    }

    public boolean isInitialized() {
        return initialized;
    }

    private void onInfoChanged() {
        if (username == null) return;
        initialized = true;

        frm.setTitle(String.format("和 %s(%d) 聊天", username, userId));
        frm.setVisible(true);
    }

    public void onReceive(String message) {
        frm.setVisible(true);
        frm.fieldMessages.append(String.format("%s\r\n", message));
        frm.fieldMessages.setCaretPosition(frm.fieldMessages.getDocument().getLength());
    }
}

class FrmChat extends JFrame {

    JTextArea fieldMessages;
    JScrollPane scrollPaneMessages;

    JTextField fieldSend;
    JButton btnSend;

    FrmChat() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(null);

        fieldMessages = new JTextArea();
        fieldMessages.setEditable(false);
        fieldMessages.setLineWrap(true);
        fieldMessages.setWrapStyleWord(true);
        panel.add(fieldMessages);

        scrollPaneMessages = new JScrollPane();
        scrollPaneMessages.setViewportView(fieldMessages);
        scrollPaneMessages.setBounds(5, 5, 470, 310);
        panel.add(scrollPaneMessages);

        fieldSend = new JTextField();
        fieldSend.setBounds(0, 320, 360, 30);
        panel.add(fieldSend);

        btnSend = new JButton("发送");
        btnSend.setBounds(360, 320, 115, 34);
        panel.add(btnSend);

        this.getContentPane().add(panel);
        this.getRootPane().setDefaultButton(btnSend);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(0, 0, 480, 380);
        this.setResizable(false);
    }
}

package Smallchat.Client;

import Smallchat.Action.Request.CreateRoomRequest;
import Smallchat.Action.Request.GetUserByNameRequest;
import Smallchat.Action.Request.JoinRoomRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmMain extends JFrame {

    private volatile static FrmMain instance = null;

    public static FrmMain getInstance() {
        if (instance == null) {
            synchronized (FrmMain.class) {
                if (instance == null) {
                    instance = new FrmMain();
                }
            }
        }
        return instance;
    }

    JLabel lblFindUser;
    JTextField fieldUsername;
    JButton btnFindUser;
    JLabel lblCreateRoom;
    JTextField fieldRoomName;
    JButton btnCreateRoom;
    JLabel lblJoinRoom;
    JTextField fieldRoomId;
    JButton btnJoinRoom;

    private FrmMain() {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5,5,5,5));
        panel.setLayout(null);

        lblFindUser = new JLabel("一对一聊天");
        lblFindUser.setBounds(10, 10, 120, 30);
        panel.add(lblFindUser);

        fieldUsername = new JTextField();
        fieldUsername.setText(Account.getInstance().getUsername());
        fieldUsername.setBounds(5, 40, 240, 30);
        panel.add(fieldUsername);

        btnFindUser = new JButton("查找");
        btnFindUser.setBounds(5, 70, 60, 30);
        btnFindUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetUserByNameRequest request = new GetUserByNameRequest(fieldUsername.getText());
                Client.getInstance().send(request);
            }
        });
        panel.add(btnFindUser);

        lblCreateRoom = new JLabel("创建聊天室");
        lblCreateRoom.setBounds(10, 100, 120, 30);
        panel.add(lblCreateRoom);

        fieldRoomName = new JTextField("test");
        fieldRoomName.setBounds(5, 130, 240, 30);
        panel.add(fieldRoomName);

        btnCreateRoom = new JButton("创建");
        btnCreateRoom.setBounds(5, 160, 60, 30);
        btnCreateRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateRoomRequest request = new CreateRoomRequest(fieldRoomName.getText());
                Client.getInstance().send(request);
            }
        });
        panel.add(btnCreateRoom);

        lblJoinRoom = new JLabel("加入聊天室");
        lblJoinRoom.setBounds(10, 190, 120, 30);
        panel.add(lblJoinRoom);

        fieldRoomId = new JTextField("1");
        fieldRoomId.setBounds(5, 220, 240, 30);
        panel.add(fieldRoomId);

        btnJoinRoom = new JButton("加入");
        btnJoinRoom.setBounds(5, 250, 60, 30);
        btnJoinRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int roomId = Integer.parseInt(fieldRoomId.getText());
                Room room = ChatManager.getInstance().getRoom(roomId);
                if (room != null) {
                    room.frm.setVisible(true);
                } else {
                    JoinRoomRequest request = new JoinRoomRequest(roomId);
                    Client.getInstance().send(request);
                }
            }
        });
        panel.add(btnJoinRoom);

        this.getContentPane().add(panel);

        this.setTitle(String.format("%s(%d) - SmallChat", Account.getInstance().getUsername(), Account.getInstance().getUserId()));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 260, 320);
        this.setResizable(false);
    }

    public void onUserNotFound() {
        JOptionPane.showMessageDialog(this, "用户未登录或不存在.");
    }

}

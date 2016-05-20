package Smallchat.Client;

import Smallchat.Action.Request.BroadcastRequest;
import Smallchat.Action.Request.QuitRoomRequest;
import Smallchat.Action.Response.SetRoomMemberResponse;
import Smallchat.Model.RoomMemberModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Room {

    private int roomId;
    private String roomName;
    private HashMap<Integer, RoomMember> members;
    private boolean initialized;

    FrmRoom frm;

    public Room() {
        members = new HashMap<>();
        initialized = false;

        frm = new FrmRoom();
        frm.btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BroadcastRequest request = new BroadcastRequest(roomId, frm.fieldSend.getText());
                frm.fieldSend.setText(null);
                frm.fieldMessages.append(String.format("我: %s\r\n", request.getMessage()));
                Client.getInstance().send(request);
            }
        });
        frm.btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuitRoomRequest request = new QuitRoomRequest(roomId);
                Client.getInstance().send(request);
            }
        });
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
        onInfoChanged();
    }

    public RoomMember getMember(int userId) {
        return members.get(userId);
    }

    public void onInfoChanged() {
        if (roomName == null) return ;
        initialized = true;
        frm.setTitle(String.format("聊天室: %s(%d)", roomName, roomId));
        frm.setVisible(true);
    }

    public void setMembers(Collection<RoomMemberModel> models) {
        members.clear();
        for (RoomMemberModel model : models) {
            addMember(model);
        }
        refreshFrameMembers();
    }

    public void setMember(RoomMemberModel model, int status) {
        switch (status) {
            case SetRoomMemberResponse.JOIN:
                addMember(model);
                break;
            case SetRoomMemberResponse.QUIT:
                removeMember(model);
                break;
        }
        refreshFrameMembers();
    }

    private void addMember(RoomMemberModel model) {
        int userId = model.getUserId();
        RoomMember member = new RoomMember(
                userId,
                model.getUsername(),
                model.getStatus()
        );
        if (members.containsKey(userId)) {
            members.replace(userId, member);
        } else {
            members.put(userId, member);
            onReceive(userId, String.format("加入聊天室."));
        }
    }

    private void removeMember(RoomMemberModel model) {
        int userId = model.getUserId();
        if (userId == Account.getInstance().getUserId()) {
            ChatManager.getInstance().removeRoom(roomId);
            frm.dispose();
            JOptionPane.showMessageDialog(frm, String.format("您已离开聊天室 %s(%d).", roomName, roomId));
        } else {
            onReceive(userId, String.format("离开了聊天室."));
            members.remove(userId);
        }
    }

    private void refreshFrameMembers() {
        StringBuilder builder = new StringBuilder();
        Iterator<RoomMember> iter = members.values().iterator();
        while (iter.hasNext()) {
            RoomMember member = iter.next();
            builder.append(String.format("%s(%d)\r\n", member.getUsername(), member.getUserId()));
        }
        frm.lstMembers.setText(builder.toString());
    }

    public void onCreated() {
        JOptionPane.showMessageDialog(frm, String.format("聊天室 %s(%d) 创建成功.", roomName, roomId));
    }

    public void onReceive(int senderId, String message) {
        if (senderId == Account.getInstance().getUserId()) return ;

        frm.setVisible(true);
        RoomMember member = members.get(senderId);
        frm.fieldMessages.append(String.format("%s(%d): %s\r\n", member.getUsername(), member.getUserId(), message));
        frm.fieldMessages.setCaretPosition(frm.fieldMessages.getDocument().getLength());
    }

    public boolean isInitialized() {
        return initialized;
    }

}

class FrmRoom extends JFrame {

    private Room room;

    JTextArea fieldMessages;
    JScrollPane scrollPaneMessages;

    JTextField fieldSend;
    JButton btnSend;

    JTextArea lstMembers;
    JScrollPane scrollPaneMembers;

    JButton btnQuit;

    public FrmRoom() {
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

        lstMembers = new JTextArea();
        lstMembers.setEditable(false);
        lstMembers.setLineWrap(true);
        lstMembers.setWrapStyleWord(true);
        panel.add(lstMembers);

        scrollPaneMembers = new JScrollPane();
        scrollPaneMembers.setViewportView(lstMembers);
        scrollPaneMembers.setBounds(480, 5, 190, 310);
        panel.add(scrollPaneMembers);

        btnQuit = new JButton("退出聊天室");
        btnQuit.setBounds(475, 320, 200, 34);
        panel.add(btnQuit);

        this.getContentPane().add(panel);
        this.getRootPane().setDefaultButton(btnSend);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(0, 0, 680, 380);
        this.setResizable(false);
    }
}

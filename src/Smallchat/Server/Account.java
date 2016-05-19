package Smallchat.Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {

    private static Account instance = new Account();

    public static Account getInstance() {
        return instance;
    }

    public boolean exists(String username) {
        try {
            String sql = String.format("SELECT COUNT(*) FROM Account WHERE Username=?");
            PreparedStatement stmt = Db.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rets = stmt.executeQuery();
            int ret = rets.getInt(1);
            stmt.close();
            return 0 != ret;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int register(String username, String password) {
        if (exists(username)) return 0;
        try {
            String sql = String.format("INSERT INTO Account(Username, Password) VALUES(?, ?)");
            PreparedStatement stmt = Db.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int ret = 0;
            if (stmt.executeUpdate() != 0) {
                ret = login(username, password);
            }
            stmt.close();
            return ret;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int login(String username, String password) {
        try {
            String sql = String.format("SELECT Id FROM Account WHERE Username=? AND Password=?");
            PreparedStatement stmt = Db.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();
            int ret = resultSet.getInt(1);
            stmt.close();
            return ret;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean terminate(String username) {
        try {
            String sql = String.format("DELETE FROM Account WHERE Username=?");
            PreparedStatement stmt = Db.getInstance().getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            boolean ret = 0 != stmt.executeUpdate();
            stmt.close();
            return ret;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] argv) {
        try {
            Account account = new Account();
            int register = account.register("xp2", "19531");
            int login = account.login("xp2", "19531");
            boolean terminate = account.terminate("xp2");
            System.out.println(String.format("%b %d %b", register, login, terminate));
        } catch(Exception ex) {
            ;
        }
    }

}

package Smallchat.Server;

import java.sql.*;
import org.sqlite.JDBC;

public class Db {

    private static Db instance = new Db();

    public static Db getInstance() {
        return instance;
    }

    private Connection conn;

    public Db() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:smallchat.db");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public boolean execute(String sql) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            boolean ret = stmt.execute(sql);
            stmt.close();
            return ret;
        } catch(SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int queryCount(String sql) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            ResultSet ret = stmt.executeQuery(sql);
            stmt.close();
            return ret.getInt(0);
        } catch(SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public void close() {
        try {
            conn.close();
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            conn = null;
        }
    }

}

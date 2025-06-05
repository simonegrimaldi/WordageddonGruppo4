/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.connection;

import java.sql.*;

/**
 *
 * @author simonegrimaldi
 */
public class dbConnection {

    private static final String URL = "jdbc:postgresql://dpg-d10ml63ipnbc73904k90-a.oregon-postgres.render.com:5432/wordageddon_db?sslmode=require";
    private static final String USER = "wordageddon_db_user";
    private static final String PASS = "D5ZMew4bDG2DTU6v126sNH7JvSFLFYMP";
    private static Connection c = null;

    public static Connection getConnection() throws SQLException {
        if (c == null || c.isClosed()) {
            c = DriverManager.getConnection(URL, USER, PASS);
        }
        return c;
    }
}

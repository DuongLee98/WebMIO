/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Smartphone;

/**
 *
 * @author kl
 */
public class SmartphoneDAOImpl implements DAO {

    private Connection connection;

    public SmartphoneDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<Smartphone> getAll() {
        ArrayList<Smartphone> rs = new ArrayList<>();

        String sql = "SELECT * FROM Smartphone";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Smartphone Smartphone = new Smartphone();

                rs.add(Smartphone);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Smartphone Smartphone = (Smartphone) t;

            String query = "INSERT INTO Smartphone VALUE (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Smartphone b = (Smartphone) t;
            String sql = "update Smartphone set Paymentid = ? AND PayDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(3, b.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Smartphone b = (Smartphone) t;
            String sql = "delete from Smartphone where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Smartphone searchById(int id) {
        Smartphone b = new Smartphone();
        try {
            String sql = "select * from Smartphone where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);

            ResultSet rs = p.executeQuery();
            if (rs.first()) {

            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

}

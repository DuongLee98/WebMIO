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
import model.Myorder;
import model.Shipment;

/**
 *
 * @author kl
 */
public class ShipmentDAOImpl implements DAO{
private Connection connection;
private MyorderDAOImpl mdi;
    public ShipmentDAOImpl(Connection connection) {
        this.connection = connection;
        this.mdi = new MyorderDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Shipment> getAll() {
        ArrayList<Shipment> rs = new ArrayList<>();

        String sql = "SELECT * FROM Shipment";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Shipment Shipment = new Shipment();
                Myorder m = this.mdi.searchById(rss.getInt("Orderid"));
                if(m != null){
                    Shipment.setOrderId(m);
                }
                Shipment.setId(rss.getInt("Id"));
                Shipment.setStatus(rss.getString("Status"));
                Shipment.setArrived(rss.getBoolean("arrived"));
                Shipment.setArrivalAddress(rss.getString("ArrivalAddress"));
                Shipment.setShipCost(rss.getInt("ShipCost"));

                rs.add(Shipment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Shipment Shipment = (Shipment) t;
            
            String query = "INSERT INTO Shipment VALUE (null, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Shipment.getOrderId().getId());
            ps.setString(2, Shipment.getStatus());
            ps.setBoolean(3, Shipment.getArrived());
            ps.setString(4, Shipment.getArrivalAddress());
            ps.setInt(5, Shipment.getShipCost());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Shipment b = (Shipment) t;
            String sql = "update Shipment set Paymentid = ? AND PayDate = ?"
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
            Shipment b = (Shipment) t;
            String sql = "delete from Shipment where Id =? and Paymentid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Shipment searchById(int id) {
        Shipment b = null;
        try {
            String sql = "select * from Shipment where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                Myorder m = this.mdi.searchById(rs.getInt("Orderid"));
                if(m != null){
                    b.setOrderId(m);
                }
                b.setId(rs.getInt("Id"));
                b.setStatus(rs.getString("Status"));
                b.setArrived(rs.getBoolean("arrived"));
                b.setArrivalAddress(rs.getString("ArrivalAddress"));
                b.setShipCost(rs.getInt("ShipCost"));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
}

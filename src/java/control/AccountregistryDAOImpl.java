/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import config.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.userinfomation.Account;
import model.userinfomation.Accountregistry;
import model.userinfomation.Person;

/**
 *
 * @author kl
 */
public class AccountregistryDAOImpl implements DAO{
    private Connection connection;
    private AccountDAOImpl accountdi;
    public AccountregistryDAOImpl(Connection connection) {
        this.connection = connection;
        this.accountdi = new AccountDAOImpl(this.connection);
    }
    @Override
    public ArrayList<Accountregistry> getAll() {
        ArrayList<Accountregistry> rs = new ArrayList<>();

        String sql = "SELECT * FROM accountregistry";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Accountregistry account = new Accountregistry();
                account.setId(rss.getInt("Id"));
//                account.setAccountId(rss.getObject(2, Account.class));
                Account a = this.accountdi.searchById(rss.getInt("AccountId"));
                if(a != null){
                    account.setAccountId(a);
                }
                account.setRegistDate(rss.getString("RegistDate"));

                rs.add(account);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public void add(Object t) {
        try {
            Accountregistry a = (Accountregistry) t;
            String query = "INSERT INTO accountresgistry VALUE (null, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, a.getAccountId().getId());
            ps.setString(2, a.getRegistDate());
            
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Accountregistry a = (Accountregistry) t;
            String sql = "update accountregistry set Accountid = ? AND RegistDate = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.setInt(1, a.getAccountId().getId());
            p.setString(2, a.getRegistDate());
            
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Accountregistry a = (Accountregistry) t;
            String sql = "delete from accountregistry where Id =? and Accountid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, a.getId());
            p.setInt(2, a.getAccountId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Accountregistry searchById(int id) {
        Accountregistry a = null;
        try {
            String sql = "select * from accountregistry where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                a.setId(rs.getInt("Id"));
                Account account = this.accountdi.searchById(rs.getInt("AccountId"));
                if(account != null){
                    a.setAccountId(account);
                }
                a.setRegistDate(rs.getString("RegistDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    
}

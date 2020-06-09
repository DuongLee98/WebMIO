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
import model.Account;
import model.Person;

/**
 *
 * @author DuongLee
 */
public class AccountDAOImpl implements DAO {

    private Connection connection;
    private PersonDAOImpl persondao;

    public AccountDAOImpl(Connection connection) {
        this.connection = connection;
        this.persondao = new PersonDAOImpl(ConnectDB.getCon());
    }

    @Override
    public ArrayList<Account> getAll() {
        ArrayList<Account> rs = new ArrayList<>();

        String sql = "SELECT * FROM account";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rss = ps.executeQuery();
            while (rss.next()) {
                Account account = new Account();
                account.setUsrname(rss.getString("Usrname"));
                account.setId(rss.getInt("Id"));
                Person person = (Person) this.persondao.searchById(rss.getInt("Personid"));
                if (person != null) {
                    account.setPersonId(person);
                }
                account.setPassword(rss.getString("Password"));

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
            Account a = (Account) t;
            String query = "INSERT INTO account VALUE (null, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, a.getPersonId().getId());
            ps.setString(2, a.getUsrname());
            ps.setString(3, a.getPassword());
            ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void edit(Object t) {
        try {
            Account a = (Account) t;
            String sql = "update account set Personid = ? AND Usrname = ? AND Password = ?"
                    + " where Id = ?";
            PreparedStatement p = connection.prepareCall(sql);
            p.setInt(1, a.getPersonId().getId());
            p.setString(2, a.getUsrname());
            p.setString(3, a.getPassword());
            p.setInt(4, a.getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Object t) {
        try {
            Account a = (Account) t;
            String sql = "delete from account where Id =? and Personid = ?";
            PreparedStatement p = connection.prepareCall(sql);

            p.setInt(1, a.getId());
            p.setInt(2, a.getPersonId().getId());
            p.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Account searchById(int id) {
        Account a = null;
        try {
            String sql = "select * from account where Id = ?";
            PreparedStatement p = connection.prepareStatement(sql);
            p.setInt(1, id);
            
            ResultSet rs = p.executeQuery();
            if (rs.first()) {
                a.setId(rs.getInt("Id"));
                Person person = (Person) this.persondao.searchById(rs.getInt("Personid"));
                if (person != null) {
                    a.setPersonId(person);
                }
                a.setUsrname(rs.getString("Usrname"));
                a.setPassword(rs.getString("Password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

}

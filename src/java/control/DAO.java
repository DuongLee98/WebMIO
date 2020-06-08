/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;

/**
 *
 * @author DuongLee
 */
public interface DAO<T> {
    public ArrayList<T> getAll();
    public void add(T t);
    public void edit (T t);
    public void delete(T t);
    public T searchById(int id);
}

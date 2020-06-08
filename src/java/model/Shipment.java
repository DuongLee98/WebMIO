/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DuongLee
 */
@MappedSuperclass
@Table(name = "shipment")
@XmlRootElement
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "Status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Arrived")
    private boolean arrived;
    @Size(max = 255)
    @Column(name = "ArrivalAddress")
    private String arrivalAddress;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ShipCost")
    private int shipCost;
    @JoinColumn(name = "OrderId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Myorder orderId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "shipment")
    private Standardshipment standardshipment;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "shipment")
    private Expressshipment expressshipment;

    public Shipment() {
    }

    public Shipment(Integer id) {
        this.id = id;
    }

    public Shipment(Integer id, boolean arrived, int shipCost) {
        this.id = id;
        this.arrived = arrived;
        this.shipCost = shipCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public int getShipCost() {
        return shipCost;
    }

    public void setShipCost(int shipCost) {
        this.shipCost = shipCost;
    }

    public Myorder getOrderId() {
        return orderId;
    }

    public void setOrderId(Myorder orderId) {
        this.orderId = orderId;
    }

    public Standardshipment getStandardshipment() {
        return standardshipment;
    }

    public void setStandardshipment(Standardshipment standardshipment) {
        this.standardshipment = standardshipment;
    }

    public Expressshipment getExpressshipment() {
        return expressshipment;
    }

    public void setExpressshipment(Expressshipment expressshipment) {
        this.expressshipment = expressshipment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shipment)) {
            return false;
        }
        Shipment other = (Shipment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Shipment[ id=" + id + " ]";
    }
    
}

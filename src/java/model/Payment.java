/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DuongLee
 */
@MappedSuperclass
@Table(name = "payment")
@XmlRootElement
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PaymentStt")
    private boolean paymentStt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TotalWithShip")
    private int totalWithShip;
    @JoinColumn(name = "OrderId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Myorder orderId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentId")
    private List<Bill> billList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "payment")
    private Paycreditcard paycreditcard;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "payment")
    private Paycod paycod;

    public Payment() {
    }

    public Payment(Integer id) {
        this.id = id;
    }

    public Payment(Integer id, boolean paymentStt, int totalWithShip) {
        this.id = id;
        this.paymentStt = paymentStt;
        this.totalWithShip = totalWithShip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getPaymentStt() {
        return paymentStt;
    }

    public void setPaymentStt(boolean paymentStt) {
        this.paymentStt = paymentStt;
    }

    public int getTotalWithShip() {
        return totalWithShip;
    }

    public void setTotalWithShip(int totalWithShip) {
        this.totalWithShip = totalWithShip;
    }

    public Myorder getOrderId() {
        return orderId;
    }

    public void setOrderId(Myorder orderId) {
        this.orderId = orderId;
    }

    @XmlTransient
    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public Paycreditcard getPaycreditcard() {
        return paycreditcard;
    }

    public void setPaycreditcard(Paycreditcard paycreditcard) {
        this.paycreditcard = paycreditcard;
    }

    public Paycod getPaycod() {
        return paycod;
    }

    public void setPaycod(Paycod paycod) {
        this.paycod = paycod;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Payment[ id=" + id + " ]";
    }
    
}

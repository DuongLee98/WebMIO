/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "paycreditcard")
@XmlRootElement
public class Paycreditcard implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccountNumber")
    private int accountNumber;
    @Size(max = 255)
    @Column(name = "Bank")
    private String bank;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fee")
    private int fee;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PaymentId")
    private Integer paymentId;
    @JoinColumn(name = "PaymentId", referencedColumnName = "Id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Payment payment;

    public Paycreditcard() {
    }

    public Paycreditcard(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Paycreditcard(Integer paymentId, int accountNumber, int fee) {
        this.paymentId = paymentId;
        this.accountNumber = accountNumber;
        this.fee = fee;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentId != null ? paymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paycreditcard)) {
            return false;
        }
        Paycreditcard other = (Paycreditcard) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Paycreditcard[ paymentId=" + paymentId + " ]";
    }
    
}

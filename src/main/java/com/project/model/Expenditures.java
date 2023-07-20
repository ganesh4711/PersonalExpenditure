package com.project.model;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="expenditure")
public class Expenditures {
    @Id
    private int id;

//    @ManyToOne
//    @JoinColumn(name = "uname",insertable = false,updatable = false)
//    private Users user;

    @ManyToOne
    
    @JoinColumn(name = "catcode")
    private Category category;

    private Double amount;
    @Column(name="spenton")
    private Date spentOn;
    private String description;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "payment_code", referencedColumnName = "code"),
        @JoinColumn(name = "uname", referencedColumnName = "uname")
    })
    private PaymentMode payment;

    private String remarks;
    private String tags;
    
    // Getters and Setters
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public Users getUser() {
//		return user;
//	}
//	public void setUser(Users user) {
//		this.user = user;
//	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getSpentOn() {
		return spentOn;
	}
	public void setSpentOn(Date spentOn) {
		this.spentOn = spentOn;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PaymentMode getPayment() {
		return payment;
	}
	public void setPayment(PaymentMode paymentMode) {
		this.payment = paymentMode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
}


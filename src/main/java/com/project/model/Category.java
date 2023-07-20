package com.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Category {
	@Id
	private String catcode;
	
	@Column(name="cat_name")
	private String catname;
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Expenditures> catExp;
	
	public Category() {}
	
	public Category(String catCode) {
	        this.catcode = catCode;
	    }
	
	public String getCatcode() {
		return catcode;
	}
	public String getCatname() {
		return catname;
	}
	public void setCatname(String catname) {
		this.catname = catname;
	}
	public void setCatcode(String catcode) {
		this.catcode = catcode;
	}
	public List<Expenditures> getCatExp() {
		return catExp;
	}
	public void setCatExp(List<Expenditures> catExp) {
		this.catExp = catExp;
	}
	@Override
	public String toString() {
		return "Category [catcode=" + catcode + ", catname=" + catname + ", catExp=" + catExp + "]";
	}


}

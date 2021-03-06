package com.mvidosa.itm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mvidosa.usr.User;

@Entity
@Table(name="ITEM")
public class Item {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String description;

	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString(){
		return "id = "+id+", name = "+name+", description = "+description;
	}
	
	//------------------------CONSTRUCTORS------------------------//
	public Item() { }
	
	public Item(String name, String description, User user) {
		this.name = name;
		this.description = description;
		this.user = user;
	}
}

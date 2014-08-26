package com.mvidosa.itm;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mvidosa.usr.User;

@Entity
@Table(name="NEWITEM")
public class NewItem {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String description;
	
	private int ups;
	
	private int downs;
	
	private int highestBidder;
	
	@OneToMany(mappedBy="newitem")
	private List<User> voters;
	
	@ManyToOne
	@JoinColumn(name="user")
	private User owner;

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

	public int getUps() {
		return ups;
	}

	public void setUps(int ups) {
		this.ups = ups;
	}

	public int getDowns() {
		return downs;
	}

	public void setDowns(int downs) {
		this.downs = downs;
	}

	public int getHighestBidder() {
		return highestBidder;
	}

	public void setHighestBidder(int highestBidder) {
		this.highestBidder = highestBidder;
	}

	public List<User> getVoters() {
		return voters;
	}

	public void setVoters(List<User> voters) {
		this.voters = voters;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}

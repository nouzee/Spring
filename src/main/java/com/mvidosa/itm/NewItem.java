package com.mvidosa.itm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
	
	private int prize;
	
	private int ups;
	
	private int downs;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="voteditems")
	private List<User> voters;

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

	public int getPrize() {
		return prize;
	}

	public void setPrize(int prize) {
		this.prize = prize;
	}
	
	public List<User> getVoters() {
		return voters;
	}

	public void setVoters(List<User> voters) {
		this.voters = voters;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void voteUp(User user){
		this.ups++;
		this.voters.add(user);
	}
	
	public void voteDown(User user){
		this.downs++;
		this.voters.add(user);
	}
	//------------------------CONSTRUCTORS------------------------//
	public NewItem() { }
	
	public NewItem(String name, String description, int prize, User user ) {
		this.name = name;
		this.description = description;
		this.prize = prize;
		this.user = user;
		this.ups = 0;
		this.downs = 0;
		this.voters = new ArrayList<User>();
	}
}

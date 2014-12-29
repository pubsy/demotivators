package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import play.db.jpa.Model;

@Entity
public class Comment extends Model{
	
	@Type(type="text")
	@Column(nullable=false)
	private String text;
	private Date date;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Demotivator demotivator;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Demotivator getDemotivator() {
		return demotivator;
	}

	public void setDemotivator(Demotivator demotivator) {
		this.demotivator = demotivator;
	}
}

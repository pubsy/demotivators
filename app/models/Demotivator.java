package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import play.i18n.Messages;

@Entity
public class Demotivator extends Model{
	private String title;
	private String text;
	private String fileName;
	private Date date;
	
	@ManyToOne
	private Domain domain;
	
	@ManyToOne
	private User author;

	public Demotivator(String title, String text, String fileName, User author, Domain domain) {
		this.title = title;
		this.text = text;
		this.fileName = fileName;
		this.author = author;
		this.domain = domain;
		this.date = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}

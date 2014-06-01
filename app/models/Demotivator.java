package models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
public class Demotivator extends Model{
	private String title;
	private String text;
	private String fileName;
	private Date date;
	private boolean deleted;
	
	@ManyToOne
	private Domain domain;

	@ManyToOne(fetch=FetchType.EAGER)
	private User author;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="demotivator", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Comment> comments;

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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}

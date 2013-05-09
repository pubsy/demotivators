package models;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;

import play.db.jpa.Model;

@Entity
public class Domain extends Model{
	
	private String name;

	public Domain(String name){
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static Domain getOrCreate(String name){
		if(StringUtils.isBlank(name)){
			throw new RuntimeException("Domain name is empty");
		}
		
		Domain domain = Domain.find("byName", name).first();
		if(domain == null){
			domain = new Domain(name);
			domain.save();
		}
		
		return domain;
	}

}

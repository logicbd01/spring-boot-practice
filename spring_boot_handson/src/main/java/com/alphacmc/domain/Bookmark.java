/**
 * 
 */
package com.alphacmc.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

/**
 * Bookmarkドメイン
 * @author matsumoto
 *
 */
@Entity
public class Bookmark {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 名前
	 */
	@NotNull
	@Size(min = 1, max = 255)
	private String name;

	/**
	 * URL
	 */
	@NotNull
	@Size(min = 1, max = 255)
	@URL
	private String url;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}

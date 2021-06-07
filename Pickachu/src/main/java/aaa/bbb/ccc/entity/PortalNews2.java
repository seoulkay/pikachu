package aaa.bbb.ccc.entity;

import java.util.Date;

public class PortalNews2 {
	
	private Integer id;
	
	private String title;
	
	private String link;

	private String source;
	
	private String top20;
	
	private Date created;
	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getTop20() {
		return top20;
	}

	public void setTop20(String top20) {
		this.top20 = top20;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	
	
}

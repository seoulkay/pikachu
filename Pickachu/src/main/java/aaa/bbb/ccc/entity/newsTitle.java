package aaa.bbb.ccc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class newsTitle {
	
	private int id;
	
	private String title;
	
	private String link;
	
	private Date created;
	
	private String source;
	
	private String top20;
	
	private Integer value;
	
	
	
	

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getTop20() {
		return top20;
	}

	public void setTop20(String top20) {
		this.top20 = top20;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	

}

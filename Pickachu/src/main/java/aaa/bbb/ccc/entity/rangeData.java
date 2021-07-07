package aaa.bbb.ccc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class rangeData {
	
	
	
	private Integer id;
	
	private Date created;
	
	private List<top20Json> top20;

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

	public List<top20Json> getTop20() {
		return top20;
	}

	public void setTop20(List<top20Json> top20) {
		this.top20 = top20;
	}
	

}

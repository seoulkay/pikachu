package aaa.bbb.ccc.entity;


import java.util.Date;

public class Project {
	//프로젝트 아이디
	private Integer id;
	//유튜브 링크
	private String youtubeLink;
	//제목
	private String title;
	//게시 시간
	private Date created;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getYoutubeLink() {
		return youtubeLink;
	}
	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
	
}


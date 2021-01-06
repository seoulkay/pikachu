package aaa.bbb.ccc.entity;

import java.util.Date;

public class Reply {
	//속성:
	private Integer Id;
	//댓글 단사람 아이디
	private String replyId;
	
	//댓글 내용 
	private String description;
	//댓글 라이크
	private boolean replyLike;
	//포스트아이디
	private Integer postId;
	
	private Date created;
	
	
	
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer Id) {
		this.Id = Id;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public void setInstaId(String replyId) {
		this.replyId = replyId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isReplyLike() {
		return replyLike;
	}
	public void setReplyLike(boolean replyLike) {
		this.replyLike = replyLike;
	}
	
	
}

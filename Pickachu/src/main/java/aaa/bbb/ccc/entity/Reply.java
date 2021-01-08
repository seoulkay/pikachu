package aaa.bbb.ccc.entity;

import java.util.ArrayList;
import java.util.Date;


public class Reply {
	//속성:
	private Integer replyId;
	//댓글 단사람 아이디
	private String instaId;
	
	//댓글 내용 
	private String description;
	//댓글 라이크
	private boolean replyLike;
	//포스트아이디
	private Integer postId;
	
	private Date created;
	
	private Integer reReplyId;

	
	private ArrayList <Reply> reReplyList;
	
	
	
	

	public Integer getReReplyId() {
		return reReplyId;
	}
	public void setReReplyId(Integer reReplyId) {
		this.reReplyId = reReplyId;
	}
	public ArrayList<Reply> getReReplyList() {
		return reReplyList;
	}
	public void setReReplyList(ArrayList<Reply> reReplyList) {
		this.reReplyList = reReplyList;
	}
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


	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public String getInstaId() {
		return instaId;
	}
	public void setInstaId(String instaId) {
		this.instaId = instaId;
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

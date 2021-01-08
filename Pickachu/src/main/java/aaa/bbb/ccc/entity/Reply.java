package aaa.bbb.ccc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Reply {
	
	private Integer id;
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
	
	private Date create;
	
	private List <Reply> reReplyList;
	
	
	
	
	public List<Reply> getReReplyList() {
		return reReplyList;
	}
	public void setReReplyList(List<Reply> reReplyList) {
		this.reReplyList = reReplyList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreate() {
		return create;
	}
	public void setCreate(Date create) {
		this.create = create;
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

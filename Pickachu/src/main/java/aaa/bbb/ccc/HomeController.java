package aaa.bbb.ccc;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import aaa.bbb.ccc.entity.Post;
import aaa.bbb.ccc.entity.Reply;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model, String search) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			List<Post> postList = new ArrayList<Post>();
			
			//Post post = new Post();
			if(search==null){
				postList = session.selectList("aaa.bbb.ccc.BaseMapper.allPost");
				System.out.println(postList);
				//session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", post );
				
			}else {
				postList = session.selectList("aaa.bbb.ccc.BaseMapper.searchPost", search);	
				System.out.println(postList);
				model.addAttribute("search", search);
				}
				
			model.addAttribute("postList", postList );
			
			session.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	
	@RequestMapping(value = "/writeForm", method = RequestMethod.GET)
	public String writeForm(Locale locale, Model model, String search ) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			List<Post> postList = new ArrayList<Post>();
			
			//Post post = new Post();
			if(search==null){
				postList = session.selectList("aaa.bbb.ccc.BaseMapper.allPost");
				//session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", post );
				
			}else {
				postList = session.selectList("aaa.bbb.ccc.BaseMapper.searchPost", search);	
				model.addAttribute("search", search);
				}
				
			model.addAttribute("postList", postList );
			
			session.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "writeForm";
	}

	
	@RequestMapping(value = "/writeFormInput", method = RequestMethod.POST)
	public RedirectView writeFormInput(Locale locale, Model model, String instaId,	String description) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		Post post = new Post();
		post.setInstaId(instaId);
		post.setDescription(description);
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			session.insert("aaa.bbb.ccc.BaseMapper.writePost", post);
			model.addAttribute("post", post );
		
			session.commit();
			session.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return new RedirectView("home");
	}
	

	@RequestMapping(value = "/onePostView", method = RequestMethod.GET)
	public String onePostView(Locale locale, Model model, Integer postId) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();

			post=session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			model.addAttribute("post", post );
			
			List<Reply> replyList = new ArrayList<Reply>();
			
			replyList = session.selectList("aaa.bbb.ccc.BaseMapper.repliesForAPost", postId);
			System.out.println(replyList);
			model.addAttribute("replyList", replyList );
			
			session.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "onePostView";
	}	
	
	
	@RequestMapping(value = "/updatePostForm", method = RequestMethod.GET)
	public String updatePostForm(Locale locale, Model model, Integer postId) {

		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post(); 
			post = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			model.addAttribute("post", post);
			
			session.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	
		return "updatePostForm";
	}
	
	@RequestMapping(value = "/updatePostAction", method = RequestMethod.POST)
	public RedirectView updatePostAction(Locale locale, Model model, Integer postId, String instaId, String description) {

		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();
			post.setPostId(postId);
			post.setInstaId(instaId);
			post.setDescription(description);
			
			session.update("aaa.bbb.ccc.BaseMapper.updatePost", post);
			session.commit();
			session.close();
			
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 수정하다가 에러가 났어요.");
		}
		

		return new RedirectView("onePostView?postId="+postId);
	}	
	
	@RequestMapping(value = "/postDeleteAction", method = RequestMethod.GET)
	public RedirectView postDeleteAction(Locale locale, Model model, Integer postId ) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			session.delete("aaa.bbb.ccc.BaseMapper.deletePost", postId);
			
			session.commit();
			session.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 삭제하다가 에러가 났어요  ");
		}
		
		return new RedirectView("home");
	}
	
	
	@RequestMapping(value = "/replyForm", method = RequestMethod.GET)
	public String replyForm(Locale locale, Model model, Integer postId) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();

			post=session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			model.addAttribute("post", post );
			
			List<Reply> replyList = new ArrayList<Reply>();
			
			replyList = session.selectList("aaa.bbb.ccc.BaseMapper.repliesForAPost", postId);
			System.out.println(replyList);
			model.addAttribute("replyList", replyList );
			
			session.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "replyForm";
	}	

	
	
	@RequestMapping(value = "/replyAction", method = RequestMethod.POST)
	public RedirectView replyAction(Locale locale, Model model, Integer postId, String instaId, String description) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		Reply reply = new Reply();
		reply.setInstaId(instaId);
		reply.setDescription(description);
		reply.setPostId(postId);
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();
			post=session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			model.addAttribute("post", post );
			
			session.insert("aaa.bbb.ccc.BaseMapper.writeReply", reply);
			model.addAttribute("reply", reply );
		
			session.commit();
			session.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return new RedirectView("onePostView?postId="+postId);
	}	

	
	@RequestMapping(value = "/oneReplyView", method = RequestMethod.GET)
	public String oneReplyView(Locale locale, Model model, Integer postId, Integer replyId) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();

			post=session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			model.addAttribute("post", post );
			
			List<Reply> replyList = new ArrayList<Reply>();
			
			replyList = session.selectList("aaa.bbb.ccc.BaseMapper.repliesForAPost", postId);
			System.out.println(replyList);
			model.addAttribute("replyList", replyList );
			
			
			Reply reply = new Reply();

			reply=session.selectOne("aaa.bbb.ccc.BaseMapper.selectReply", replyId );
			//System.out.println(reply.getReplyId());
			
			model.addAttribute("reply", reply );
			
			List<Reply> reReplyList = new ArrayList<Reply>();
			
			reReplyList = session.selectList("aaa.bbb.ccc.BaseMapper.reReplyList", replyId);
			System.out.println(reReplyList);
			model.addAttribute("reReplyList", reReplyList );
			
			//session.close();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "oneReplyView";
	}		
	
	@RequestMapping(value = "/updateReplyForm", method = RequestMethod.GET)
	public String updateReplyForm(Locale locale, Model model, Integer postId, Integer replyId) {

		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post(); 
			post = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			model.addAttribute("post", post);
			
			Reply reply = new Reply();
			reply = session.selectOne("aaa.bbb.ccc.BaseMapper.selectReply", replyId );
			model.addAttribute("reply", reply);
			
			session.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	
		return "updateReplyForm";
	}

	@RequestMapping(value = "/updateReplyAction", method = RequestMethod.POST)
	public RedirectView updateReplyAction(Locale locale, Model model, Integer postId, Integer replyId, String instaId, String description) {

		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();
			post = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			model.addAttribute("post", post);
			
			
			Reply reply = new Reply();
			reply.setReplyId(replyId);
			reply.setInstaId(instaId);
			reply.setDescription(description);
			
			session.update("aaa.bbb.ccc.BaseMapper.updateReply", reply);
			session.commit();
			session.close();
			
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 수정하다가 에러가 났어요.");
		}
		

		return new RedirectView("onePostView?postId="+postId);
	}
	
	
}

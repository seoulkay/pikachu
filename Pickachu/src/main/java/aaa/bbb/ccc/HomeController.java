package aaa.bbb.ccc;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
			
			if(search==null){
			List<Post> postList = session.selectList("aaa.bbb.ccc.BaseMapper.allPost");
			model.addAttribute("postList", postList );
			}else {
			List<Post> postList = session.selectList("aaa.bbb.ccc.BaseMapper.searchPost", search);
			System.out.println(postList);
			
			model.addAttribute("postList", postList );
			model.addAttribute("search", search);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@RequestMapping(value = "post", method = RequestMethod.GET)
	public String postOnePage(Locale locale, Model model, Integer postId) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			Post postOne = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId);
			
			model.addAttribute("postOne", postOne );
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 하나 보여 주다가 에러가 났어요  ");
		}
		
	return "post";
	}
	
	@RequestMapping(value = "postForm", method = RequestMethod.GET)
	public String postForm(Locale locale, Model model) {
	
	return "postForm";
	}
	
	
	@RequestMapping(value = "postUpdateForm", method = RequestMethod.GET)
	public String postUpdateForm(Locale locale, Model model, int postId) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			Post postOne = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId);
			
			model.addAttribute("postOne", postOne );
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 수정하다가 에러가 났어요  ");
		}
		
	return "postUpdateForm";
	}
	
	@RequestMapping(value = "postFormAction", method = RequestMethod.POST)
	public RedirectView postFormAction(Locale locale, Model model, String instaId,String picture, String description ) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();
			post.setInstaId(instaId);
			post.setPicture(picture);
			post.setDescription(description);
			
			
			session.insert("aaa.bbb.ccc.BaseMapper.insertPost", post);
			session.commit();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 작성하려다가 에러가 났어요  ");
		}
		return new RedirectView("home");
	}
	
	@RequestMapping(value = "postUpdateAction", method = RequestMethod.POST)
	public RedirectView postUpdateAction(Locale locale, Model model, Integer postId, String instaId, String picture, String description) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();
			post.setPostId(postId);
			post.setInstaId(instaId);
			post.setPicture(picture);
			post.setDescription(description);
			
			session.update("aaa.bbb.ccc.BaseMapper.updatePost", post);
			session.commit();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 수정하다가 에러가 났어요  ");
		}
		
		return new RedirectView("post?postId=");
	}
	
	@RequestMapping(value = "postDeleteAction", method = RequestMethod.GET)
	public RedirectView postUpdateFormAction(Locale locale, Model model, Integer postId ) {
	
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
	
	
}
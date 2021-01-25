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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import aaa.bbb.ccc.entity.Like;
import aaa.bbb.ccc.entity.PageManager;
import aaa.bbb.ccc.entity.Post;
import aaa.bbb.ccc.entity.Reply;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Locale locale, Model model, String search, Integer pageSize, Integer currentPage, Integer maxPager) {
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			maxPager=5;

			if(pageSize==null||currentPage==null) {
				
				Integer postTotal = session.selectOne("aaa.bbb.ccc.BaseMapper.countPost");			
				PageManager totalSize = new PageManager();
				totalSize.setTotalSize(postTotal);
				totalSize.setCurrentPage(0);
				totalSize.setPageSize(5);
				
				int showPage = postTotal / totalSize.getPageSize();
			model.addAttribute("totalSize", totalSize );	
				pageSize=totalSize.getPageSize();
				currentPage=0;	
			}

			if(search==null||search.isEmpty()){
				
				Integer postTotal = session.selectOne("aaa.bbb.ccc.BaseMapper.countPost");
				PageManager totalSize = new PageManager();
				totalSize.setTotalSize(postTotal);
				totalSize.setCurrentPage(currentPage);
				totalSize.setPageSize(pageSize);
				List<Post> limitPostList = session.selectList("aaa.bbb.ccc.BaseMapper.limitPost", totalSize);
				int showPage = postTotal / totalSize.getPageSize();
			model.addAttribute("showPage",showPage );	
				PageManager postPm = new PageManager();
				postPm.setCurrentPage(currentPage);
				postPm.setTotal(totalSize.getTotalSize());
				postPm.setMaxPager(maxPager);
				postPm = currentPagerCalculator(postPm);
			model.addAttribute("pm", postPm );
			model.addAttribute("totalSize", totalSize );
			model.addAttribute("postList", limitPostList );
			
			}else {
			
				Integer searchPostTotal = session.selectOne("aaa.bbb.ccc.BaseMapper.countSearchPost", search);
				System.out.println("검색어를 집어넣고 전체 게시물의 갯수는 : "+searchPostTotal);
				PageManager searchTotalSize = new PageManager();
				searchTotalSize.setSearch(search);
				searchTotalSize.setTotalSize(searchPostTotal);
				searchTotalSize.setCurrentPage(currentPage);
				searchTotalSize.setPageSize(pageSize);
				int showPage = searchPostTotal / searchTotalSize.getPageSize();
			model.addAttribute("showPage",showPage );
				List<Post> limitSearchPostList = session.selectList("aaa.bbb.ccc.BaseMapper.searchLimitPost", searchTotalSize);
				PageManager postPm = new PageManager();
				postPm.setCurrentPage(currentPage);
				postPm.setTotal(searchTotalSize.getTotalSize());
				postPm.setMaxPager(maxPager);
				postPm = currentPagerCalculator(postPm);
			model.addAttribute("pm", postPm );
			model.addAttribute("postList", limitSearchPostList );
			model.addAttribute("totalSize", searchTotalSize );
			model.addAttribute("search", search);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
//	Modal post reading 
	

	@RequestMapping(value = "postOneView", method = {RequestMethod.GET})
	public @ResponseBody Post postOneView(@RequestParam("postId") Integer postId){
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		Post result = new Post() ;

		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();

			Post postOne = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId);
			
			result = postOne ;	
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 하나 보여 주다가 에러가 났어요  ");
			
		}
		System.out.println(result.getPostId());
	 return result;
	}
	
	
	
	@RequestMapping(value = "postOneUpdate", method = {RequestMethod.POST})
	public @ResponseBody Post postOneUpdate(@RequestParam("postId") Integer postId, String instaId, String picture, String description){
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		Post result = new Post() ;

		
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
			
			result = post ;	
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 수정하다가 에러가 났어요  ");
			
		}
		System.out.println(result.getPostId());
	 return result;
	}
	
	
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String postOnePage(Locale locale, Model model, Integer postId) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();

			Post postOne = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId);
			List<Reply> replyList = session.selectList("aaa.bbb.ccc.BaseMapper.allReply", postId);
			for (int i=0 ; i<replyList.size(); i++) {
				List<Reply> reReplyList = session.selectList("aaa.bbb.ccc.BaseMapper.reReply", replyList.get(i).getId());
				replyList.get(i).setReReplyList(reReplyList);
				
			}	
					
			model.addAttribute("postOne", postOne );
			model.addAttribute("replyList", replyList );
				
			
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
	public RedirectView postFormAction(Locale locale, Model model, String instaId, String picture, String description ) {
	
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
		
		return new RedirectView("/ccc/home");
	}
	
	@RequestMapping(value = "postDeleteAction", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "replyFormAction", method = RequestMethod.POST)
	public RedirectView replyFormAction(Locale locale, Model model, Integer postId, String instaId, String description ) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Reply reply = new Reply();
			reply.setInstaId(instaId);
			reply.setPostId(postId);
			reply.setDescription(description);

			session.insert("aaa.bbb.ccc.BaseMapper.insertReply", reply);
			session.commit();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 작성하려다가 에러가 났어요  ");
		}
		return new RedirectView("/ccc/post?postId="+postId);
	}
	@RequestMapping(value = "replyUpdateForm", method = RequestMethod.GET)
	public String replyUpdateForm(Locale locale, Model model, Integer id) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			Reply replyOne = session.selectOne("aaa.bbb.ccc.BaseMapper.selectReply", id);
			
			model.addAttribute("replyOne", replyOne );
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("댓글 수정하다가 에러가 났어요  ");
		}
		
	return "replyUpdateForm" ;
	}
	@RequestMapping(value = "replyUpdateAction", method = RequestMethod.POST)
	public RedirectView replyUpdateAction(Locale locale, Model model, Integer postId, Integer id, String instaId, String description) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Reply reply = new Reply();
			reply.setPostId(postId);
			reply.setId(id);
			reply.setInstaId(instaId);
			reply.setDescription(description);
			
			session.update("aaa.bbb.ccc.BaseMapper.updateReply", reply);
			session.commit();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("댓글 수정하다가 에러가 났어요  ");
		}
		
		return new RedirectView("post?postId="+postId);
	}
	
	@RequestMapping(value = "replyDeleteAction", method = RequestMethod.GET)
	public RedirectView replyDeleteAction(Locale locale, Model model, Integer postId, Integer id) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			session.delete("aaa.bbb.ccc.BaseMapper.deleteReply", id);
			session.commit();
			session.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("댓글 삭제하다가 에러가 났어요  ");
		}
		
		return new RedirectView("/ccc/post?postId="+postId);
	}
	
	@RequestMapping(value = "reReplyFormAction", method = RequestMethod.POST)
	public RedirectView reReplyFormAction(Locale locale, Model model, Integer postId, Integer id, String instaId, String description ) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Reply reply = new Reply();
			reply.setInstaId(instaId);
			reply.setPostId(postId);
			reply.setDescription(description);
			reply.setReplyId(id);

			session.insert("aaa.bbb.ccc.BaseMapper.insertReReply", reply);
			session.commit();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("대댓글 작성하려다가 에러가 났어요  ");
		}
		return new RedirectView("/ccc/post?postId="+postId);
	}
	
	public static PageManager currentPagerCalculator(PageManager pm) {
		PageManager result = new PageManager();
	
		int endPage = 0;
	
		endPage = (int) Math.ceil((pm.getCurrentPage()+1)/(double)pm.getMaxPager()) * pm.getMaxPager();
		
		
//		System.out.println("Start page" + (endPage-pm.getMaxPager()));
		result.setStartPage(endPage-pm.getMaxPager());
		
		
		int realEndPage = (int)Math.ceil(pm.getTotal()/pm.getMaxPager())+1;
		
		if(endPage > realEndPage) {
			endPage = realEndPage;
		}
		
		result.setEndPage(endPage);
		result.setTotal(pm.getTotal());
		result.setCurrentPage(pm.getCurrentPage());
		result.setMaxPager(pm.getMaxPager());
		
		return result;
	}
	
//	  public PageManager ts(Integer currentPage) {
//
//	    	PageManager ts = new PageManager();
//	    	try {
//	    		stmt = con.createStatement();
//	            rs = stmt.executeQuery("SELECT count(*) as totalSize FROM hong.gesiPost");
//	            
//	        while(rs.next()) {
//      	ts.setTotalSize(rs.getInt("totalSize"));
//      	ts.setPageSize(5); 
//      	ts.setCurrentPage(currentPage);
//	        }
//	        }catch(Exception e) {
//	    		e.printStackTrace();
//	    		System.out.println("포스트 가지고 오다가 디비에서 에러났어요!!");
//	    	}
//	       return ts;
//	    }
}
package aaa.bbb.ccc;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import aaa.bbb.ccc.entity.PageManager;
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
	public String home(Locale locale, Model model, String search,Integer pageSize, Integer currentPage, Integer maxPager) {
		

		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			PageManager page = new PageManager();
			
			Integer offset = 5;
			
			if(pageSize==null || currentPage==null) {
				pageSize =10; currentPage=0;
			}
			
			page.setPageSize(pageSize);
			page.setCurrentPage(currentPage*offset);
			page.setSearch(search);
			
			System.out.println(page.getPageSize());
			System.out.println(page.getCurrentPage());
			
			model.addAttribute("page", page);
			
			List<Post> postList = new ArrayList<Post>();
					
			Integer total = 0;
			
			//Post post = new Post();
			if(search==null){
				total= session.selectOne("aaa.bbb.ccc.BaseMapper.totalSize");
				
				postList = session.selectList("aaa.bbb.ccc.BaseMapper.showPostByPage", page);
				System.out.println(postList);
	
			}
			else {
				total= session.selectOne("aaa.bbb.ccc.BaseMapper.totalSizeSearched", search);
				
				postList = session.selectList("aaa.bbb.ccc.BaseMapper.searchPostByPage", page);	
				System.out.println(postList);
					
				model.addAttribute("search", search);
			}
			
			
			
			PageManager postPm = new PageManager();
			
			postPm.setCurrentPage(currentPage);
			postPm.setTotalSize(total);
			postPm.setPageSize(page.getPageSize());
			postPm.setMaxPager(5);
			
			System.out.println("토탈"+postPm.getTotalSize());
			System.out.println("맥스페이져"+postPm.getMaxPager());
			System.out.println("커런트"+postPm.getCurrentPage());
			System.out.println("페이지사이즈"+postPm.getPageSize());
			
			
			postPm = currentPagerCalculatorIH(postPm);
			postPm.setPageSize(page.getPageSize());
			
			
			
			System.out.println("페이지계산 후");
			System.out.println("토탈"+postPm.getTotalSize());
			System.out.println("맥스페이져"+postPm.getMaxPager());
			System.out.println("커런트"+postPm.getCurrentPage());
			System.out.println("페이지사이즈"+postPm.getPageSize());
			
			System.out.println("스타트"+postPm.getStartPage());
			System.out.println("앤드"+postPm.getEndPage());
			
			model.addAttribute("pm", postPm);
			
			
			int totalEndPage = total/postPm.getPageSize();
			System.out.println("막페이지"+totalEndPage);
			
		
			model.addAttribute("endPage", totalEndPage);
			
			
			
			
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
		

		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();
			post.setInstaId(instaId);
			post.setDescription(description);
			
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
			
			for(int i=0; i < replyList.size(); i++) {
				List<Reply> reReplyList = new ArrayList<Reply>();
				reReplyList = session.selectList("aaa.bbb.ccc.BaseMapper.reReplyList", replyList.get(i).getReplyId());
				replyList.get(i).setReReplyList(reReplyList);
			}
			
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
			
			for(int i=0; i < replyList.size(); i++) {
				List<Reply> reReplyList = new ArrayList<Reply>();
				reReplyList = session.selectList("aaa.bbb.ccc.BaseMapper.reReplyList", replyList.get(i).getReplyId());
				replyList.get(i).setReReplyList(reReplyList);
			}
			
			
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
		

		return new RedirectView("onePostView?postId="+postId+"&replyId="+replyId);
	}
	
	@RequestMapping(value = "/replyDeleteAction", method = RequestMethod.GET)
	public RedirectView replyDeleteAction(Locale locale, Model model, Integer postId, Integer replyId ) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			session.delete("aaa.bbb.ccc.BaseMapper.deleteReply", replyId);
			
			session.commit();
			session.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 삭제하다가 에러가 났어요  ");
		}
		
		return new RedirectView("onePostView?postId="+postId);
	}
	
	
	@RequestMapping(value = "/reReplyForm", method = RequestMethod.GET)
	public String reReplyForm(Locale locale, Model model, Integer postId, Integer replyId) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();

			post=session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			//System.out.println(post.getPostId());
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
		
		return "reReplyForm";
	}	
	
	
	@RequestMapping(value = "/reReplyAction", method = RequestMethod.POST)
	public RedirectView reReplyAction(Locale locale, Model model, Integer postId, Integer replyId, String instaId, String description) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		Reply reply = new Reply();
		reply.setReReplyId(replyId);
		reply.setInstaId(instaId);
		reply.setDescription(description);
		reply.setPostId(postId);
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			
			session.insert("aaa.bbb.ccc.BaseMapper.writeReReply", reply);
			model.addAttribute("reply", reply );
		
			session.commit();
			session.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return new RedirectView("oneReplyView?postId="+postId+"&replyId="+replyId);
	}	

	
	@RequestMapping(value = "/oneReReplyView", method = RequestMethod.GET)
	public String oneReReplyView(Locale locale, Model model, Integer postId, Integer replyId, Integer reReplyId) {
		
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
		
		return "oneReReplyView";
	}		
		
	
	
    public static PageManager currentPagerCalculatorIH(PageManager postPm) {
    	PageManager result = new PageManager();
    
    	int endPage = 0;
    	
    	endPage = (int) Math.ceil((postPm.getCurrentPage()+1) / (double) postPm.getMaxPager()) * postPm.getMaxPager();
    	//ok
    	
    	//System.out.println("Start page"+ (endPage-maxPager));
    	result.setStartPage(endPage-postPm.getMaxPager());
    	
    	int realEndPage = (int)Math.ceil(postPm.getTotalSize()/postPm.getMaxPager())+1;
    	
    	if(endPage > realEndPage) {
    		endPage = realEndPage;
    	}
    	
    	//System.out.println("End page" + (endPage));
    	result.setEndPage(endPage);
    	result.setTotalSize(postPm.getTotalSize());
    	result.setCurrentPage(postPm.getCurrentPage());
    	result.setMaxPager(postPm.getMaxPager());
    	
    	return result;
    }
    
    
    
    @RequestMapping(value = "onePostViewAjax", method = {RequestMethod.GET}, produces = "application/json")
	public @ResponseBody Post onePost(@RequestParam("postId") Integer postId){
    	
    	String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		Post onePost = new Post();
		
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Post post = new Post();

			post=session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", postId );
			
			session.close();
		
			onePost=post;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("네가 여기 도착했다는건 지금 "+postId+"번 글을 보고 있다는 뜻이지."); 
		
		return onePost;

	}


    @RequestMapping(value = "updatePostAjax", method = {RequestMethod.GET}, produces = "application/json")
	public @ResponseBody Post updatePost(@RequestParam("postId") Integer postId, String instaId, String description){
    	
    	String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		Post onePost = new Post();
		
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
		
			onePost=post;
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("포스트 수정하다가 에러가 났어요.");
		}
		System.out.println("네가 여기 도착했다는건 지금 "+postId+"번 글을 수정 성공했다는 뜻이지."); 
		
		return onePost;

	}
    
    
	
}

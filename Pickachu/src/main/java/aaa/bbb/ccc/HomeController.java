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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			ArrayList<Post> postAllList = new ArrayList<Post>();
			
			
			int count = session.selectOne("aaa.bbb.ccc.BaseMapper.countAll");
			
			for(int i=1; i<= count ; i++) {
				Post post = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", i);
				postAllList.add(post);
			}
			
			model.addAttribute("postAllList", postAllList );
			
			
			Post post = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", 1);
			//System.out.println(post.getPostId());
			//model.addAttribute("post", post );
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
}

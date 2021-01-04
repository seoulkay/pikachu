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
	public String home(Locale locale, Model model, String search) {
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			List<Post> postList = new ArrayList<Post>();
			
			Post post = new Post();
			if(search==null){
					postList = session.selectList("aaa.bbb.ccc.BaseMapper.allPost");
					session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", post );
				
				}else {
					postList = session.selectList("aaa.bbb.ccc.BaseMapper.searchPost", search);	
					model.addAttribute("search", search);
				}
				
			model.addAttribute("postList", postList );
				
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
}

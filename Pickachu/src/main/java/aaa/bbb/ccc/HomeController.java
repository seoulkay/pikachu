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
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
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
			Post post = session.selectOne("aaa.bbb.ccc.BaseMapper.selectPost", 1);
			System.out.println(post.getPostId());
			
//			model.addAttribute("post", post );
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@Scheduled(cron = "0 * * * * *")
	public void scheduleTaskUsingCronExpression() {
		//System.out.println("크론 탭 cron!");
		
	    //long now = System.currentTimeMillis() / 1000;
	    //System.out.println("schedule tasks using cron jobs - " + now);
	    
	    //System.out.println("디비에 국가코드가 없는것을 찾아 어래이 리스트로 만듬");
	    //System.out.println("하나씩 찾아서 국가 코드를 넣어줌");
	    
	    //System.out.println(hey());
		
		List<Integer> nullCountryList = getCountryNullLoginAttempts();
		
	}
	
	// 디비에서 국가코드가 null인 행들을 찾기 （파라미터 : 없음, 리턴: 인트 배열!리스트)
	public static List<Integer> getCountryNullLoginAttempts(){
		List<Integer> result = new ArrayList<Integer>();
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	public static int[] hey() {
		int[] result = {1,2,3};
		return result;
	}
	
}

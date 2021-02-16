package aaa.bbb.ccc;


import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

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
import aaa.bbb.ccc.entity.Member;
import aaa.bbb.ccc.entity.PageManager;
import aaa.bbb.ccc.entity.Post;
import aaa.bbb.ccc.entity.Reply;
import aaa.bbb.ccc.entity.loginLog;

import javax.servlet.http.HttpServletRequest;
@Controller
public class adminControler {

	
	private static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
	
	@RequestMapping(value = "admin/pages/examples/login.html", method = RequestMethod.GET)
	public String login(Locale locale, Model model ) {
	
		
		
		return "admin/pages/examples/login";
	}
	
	
	@RequestMapping(value = "loginAction", method = RequestMethod.POST)
	public String loginAction(Locale locale, Model model, String id, String password, HttpServletRequest request ) {
	
		
		System.out.println(getClientIp(request));
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		Integer result = 0;
		Member idPsw = new Member();
		idPsw.setId(id);
		idPsw.setPassword(password);
		Member loginLog = new Member();
		loginLog.setLoginId(id);
		loginLog.setSourceIp(getClientIp(request));
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd");
		Date time = new Date();
		String time1 = format1.format(time);
		
		
		
		System.out.println("id = "+id+password);
		System.out.println("resurt = "+result);
		System.out.println(time1);
		String em = "Email이나 비밀번호를 다시 확인하세요. ";
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Member indexUser = new Member();
			indexUser = session.selectOne("aaa.bbb.ccc.BaseMapper.indexUser", id);
			
			Integer countAllMember = session.selectOne("aaa.bbb.ccc.BaseMapper.countAllMember");
			int countSuccessLogin = session.selectOne("aaa.bbb.ccc.BaseMapper.countSuccessLogin");
			int countLogin = session.selectOne("aaa.bbb.ccc.BaseMapper.countLogin");
			Integer countTodayLogin = session.selectOne("aaa.bbb.ccc.BaseMapper.countTodayLogin", time1);
			Integer countAllPost = session.selectOne("aaa.bbb.ccc.BaseMapper.countPost");
			Double successRate = Math.floor((double)countSuccessLogin/(double)countLogin * 100.0) ;
			Integer count = session.selectOne("aaa.bbb.ccc.BaseMapper.countIdPsw", idPsw);
			System.out.println(successRate);
			
			if(count==0) {
				result = count ;
				
			
				session.insert("aaa.bbb.ccc.BaseMapper.insertLoginLogFalse", loginLog);
				session.commit();
				session.close();
				model.addAttribute("errorMessage",em);
				return "admin/pages/examples/login";
				
				
			}else {
				result = 1 ;
				
				session.insert("aaa.bbb.ccc.BaseMapper.insertLoginLogTrue", loginLog);
				session.commit();
				session.close();
				
				
				model.addAttribute("indexUser",indexUser);
				model.addAttribute("countAllMember",countAllMember);
				model.addAttribute("successRate",successRate);
				model.addAttribute("countTodayLogin",countTodayLogin);
				model.addAttribute("countAllPost",countAllPost);
				return "admin/index";
			}
		
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("idPsw check error in sql");
			
		}
		
		return "";
	}
	
	@RequestMapping(value = "loginCountAjax", method = {RequestMethod.GET})
	public @ResponseBody List<loginLog> loginCountAjax(@RequestParam("data") int data1){
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<loginLog> result = new ArrayList<loginLog>();
		SimpleDateFormat format1 = new SimpleDateFormat ("MM");
		Date time = new Date();
		String time1 = format1.format(time);
		System.out.println(time1);
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			result = session.selectList("aaa.bbb.ccc.BaseMapper.countLogAjax", time1);
			
		
			System.out.println("결과는 "+result);
			
		} catch (IOException e) {
			e.printStackTrace();	
			
		}
			return result;
		}
	
	
	@RequestMapping(value = "loginCountSuccessAjax", method = {RequestMethod.GET})
	public @ResponseBody List<loginLog> loginCountSuccessAjax(@RequestParam("data") int data1){
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<loginLog> result = new ArrayList<loginLog>();
		SimpleDateFormat format1 = new SimpleDateFormat ("MM");
		Date time = new Date();
		String time1 = format1.format(time);
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			result = session.selectList("aaa.bbb.ccc.BaseMapper.countLogSuccessAjax", time1);
			
			
			
			System.out.println("성공한 결과는 "+result);
			
		} catch (IOException e) {
			e.printStackTrace();	
			
		}
			return result;
		}
	
	@RequestMapping(value = "loginSuccessAjax", method = {RequestMethod.GET})
	public @ResponseBody List<loginLog> loginSuccessAjax(@RequestParam("data") int data1){
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<loginLog> result = new ArrayList<loginLog>();
		
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			result = session.selectList("aaa.bbb.ccc.BaseMapper.countLogSuccessAjax");
			
			System.out.println("결과는 "+result);
			
		} catch (IOException e) {
			e.printStackTrace();	
			
		}
			return result;
		}
	
	
	@RequestMapping(value = "admin/pages/examples/loginCheck", method = {RequestMethod.POST})
	public @ResponseBody Integer loginCheck(@RequestParam("id") String id, String password, HttpServletRequest request){
		
		System.out.println(getClientIp(request));
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		Integer result = 0;
		Member idPsw = new Member();
		idPsw.setId(id);
		idPsw.setPassword(password);
		Member loginLog = new Member();
		loginLog.setLoginId(id);
		loginLog.setSourceIp(getClientIp(request));
		
		
		System.out.println("id = "+id+password);
		System.out.println("resurt = "+result);
		
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Integer count = session.selectOne("aaa.bbb.ccc.BaseMapper.countIdPsw", idPsw);
			
			if(count==0) {
				result = count ;
				
			
				session.insert("aaa.bbb.ccc.BaseMapper.insertLoginLogFalse", loginLog);
				session.commit();
				session.close();
			}else {
				result = 1 ;
				
				session.insert("aaa.bbb.ccc.BaseMapper.insertLoginLogTrue", loginLog);
				session.commit();
				session.close();
			}
		
			System.out.println("resurt = "+result);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("idPsw check error in sql");
			
		}
		
	 return result;
	}
	
	@RequestMapping(value = "admin/pages/examples/loginLogInsert", method = RequestMethod.POST)
	public RedirectView loginLogInsert(Locale locale, Model model, String loginId, HttpServletRequest request, boolean successful ) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Member loginLog = new Member();
			loginLog.setLoginId(loginId);
			loginLog.setSourceIp(getClientIp(request));
			loginLog.setSuccessful(successful);
			
			System.out.println("여기오긴온다."+loginLog+loginId+successful);
			
			session.insert("aaa.bbb.ccc.BaseMapper.insertLoginLog", loginLog);
			session.commit();
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("로그인 자료 작성하려다가 에러가 났어요  ");
		}
		return new RedirectView("");
	}
	
	
	
	
	@RequestMapping(value = "admin/index", method = RequestMethod.GET)
	public String adminIndex(Locale locale, Model model) {
	
		return "admin/index";
	}
	
	@RequestMapping(value = "admin/index2", method = RequestMethod.GET)
	public String adminIndex2(Locale locale, Model model) {
	
		return "admin/index2";
	}
	
	
	@RequestMapping(value = "admin/index3", method = RequestMethod.GET)
	public String adminIndex3(Locale locale, Model model) {
	
		return "admin/index3";
	}
	
	
	//admin/pages/tables/simple.html
	
	@RequestMapping(value = "admin/pages/tables/simple.html", method = RequestMethod.GET)
	public String simpleTables(Locale locale, Model model, String search) {
	
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			System.out.println("검색어는 : "+search);
			
			if(search==null||search.isEmpty()){
			List<Member> memberList = session.selectList("aaa.bbb.ccc.BaseMapper.memberList");		
			model.addAttribute("memberList",memberList);
			System.out.println("여기서 멤버리스트를 보내준다 "+memberList);
			}else {
			Member tableSearch = new Member();	
			tableSearch.setSearch(search);	
				
			List<Member> searchMemberList = session.selectList("aaa.bbb.ccc.BaseMapper.searchMemberList", tableSearch);		
			model.addAttribute("memberList",searchMemberList);
			model.addAttribute("tableSearch",search);
			System.out.println("검색어가 있음으로 검색된 멤버리스트를 보내준다 "+searchMemberList);
	
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return "admin/pages/tables/simple";
	}
}



	
	
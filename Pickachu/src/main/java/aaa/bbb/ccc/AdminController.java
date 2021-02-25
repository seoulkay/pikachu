package aaa.bbb.ccc;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import aaa.bbb.ccc.entity.Member;
import aaa.bbb.ccc.entity.Post;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import aaa.bbb.ccc.entity.LoginCount;
import aaa.bbb.ccc.entity.LoginLog;

@Controller
public class AdminController {
	
	@RequestMapping(value = "admin/index", method = RequestMethod.GET)
	public String adminIndex(Locale locale, Model model) {
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
				
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
		
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return "admin/index";
	}
	
	@RequestMapping(value = "admin/pages/tables/simple.html", method = RequestMethod.POST)
	public String adminTable(Locale locale, Model model, String search) {
		//회원정보를 가지고 와라.
		//반복문 돌려라.
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			List<Member> memberList = new ArrayList<Member>();
			
			if(search==null){
				
				memberList = session.selectList("aaa.bbb.ccc.BaseMapper.showMember");
				System.out.println("서치가 없을 때 전체 회원은 " + memberList);
	
			}
			else {
				memberList= session.selectList("aaa.bbb.ccc.BaseMapper.searchMember", search);
	
				System.out.println("당신이 서치한 회원은 다음과 같다." + memberList);
					
				model.addAttribute("search", search);
			}
			
			model.addAttribute("memberList", memberList );
			
			session.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return "admin/pages/tables/simple";
	}

	
	@RequestMapping(value = "admin/pages/examples/login.html", method = RequestMethod.GET)
	public String adminLogIn(Locale locale, Model model, HttpServletRequest request) {
		
		System.out.println(getClientIp(request));
		
		return "admin/pages/examples/logIn";
	}

	
	
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

	
	@RequestMapping(value = "admin/pages/examples/loginAction", method = RequestMethod.POST)
	public String loginAction(Locale locale, Model model, String eMailMember,	String passwordMember, HttpServletRequest ipRequest) {
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			Member member = new Member();
			member.seteMailMember(eMailMember);
			member.setPasswordMember(passwordMember);
						
			int loginSuccess = 0;
			loginSuccess = session.selectOne("aaa.bbb.ccc.BaseMapper.loginSuccess", member);
						
			LoginLog log1 = new LoginLog();
			log1.setLoginId(eMailMember);
			log1.setSourceIP(getClientIp(ipRequest));
			log1.setLoginSuccess(loginSuccess);
			
			Member log2 = new Member();
			
			session.insert("aaa.bbb.ccc.BaseMapper.loginLog", log1);			
			log2 = session.selectOne("aaa.bbb.ccc.BaseMapper.searchMember2", eMailMember);
			System.out.println(log2);
			session.commit();
			
			
			String errorMessage = "";
			
			
			if(loginSuccess==1) {
				System.out.println(eMailMember);
				System.out.println(passwordMember);
				System.out.println(loginSuccess);
				System.out.println("login success.");
				
				Integer totalPost = 0;
				Integer totalMember = 0;
				double totalLoginSuccess = 0;
				double totalLoginTry = 0;
				double loginSuccessPer = 0;
				Integer loginToday = 0;
				
				totalPost = session.selectOne("aaa.bbb.ccc.BaseMapper.totalSize");
				totalMember = session.selectOne("aaa.bbb.ccc.BaseMapper.totalMember");
				totalLoginSuccess = session.selectOne("aaa.bbb.ccc.BaseMapper.totalLoginSuccess");
				totalLoginTry = session.selectOne("aaa.bbb.ccc.BaseMapper.totalLoginTry");
				loginToday = session.selectOne("aaa.bbb.ccc.BaseMapper.loginToday");	
				
				session.close();
				
				loginSuccessPer = totalLoginSuccess / totalLoginTry * 100.0;
				String Per = String.format("%.2f", loginSuccessPer);
				
				model.addAttribute("totalPost", totalPost);
				model.addAttribute("totalMember", totalMember);
				model.addAttribute("Per", Per);
				model.addAttribute("loginToday", loginToday);
				model.addAttribute("error", errorMessage);
				model.addAttribute("log2", log2);
				
				return "admin/index";
				
			}else {
				
				System.out.println(eMailMember);
				System.out.println(passwordMember);
				System.out.println(loginSuccess);
				System.out.println("login failed.");
				errorMessage = "아이디 또는 비밀번호를 확인하세요.";
				model.addAttribute("error", errorMessage );
				return "admin/pages/examples/logIn";
			}
			
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
		return "";
	}	
		

	@RequestMapping(value = "admin/pages/examples/loginCount", method = RequestMethod.GET)
	public @ResponseBody List<LoginCount> loginCount(@RequestParam("loginCount") Locale locale, Model model) {
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<LoginCount> count = new ArrayList<LoginCount>();
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			count=session.selectList("aaa.bbb.ccc.BaseMapper.loginCount");
			System.out.println(count);
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
		return count;
	}
	
	@RequestMapping(value = "admin/calendarTest", method = RequestMethod.GET)
	public @ResponseBody ArrayList <LoginCount> calendarTest(Locale locale, Model model) {
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
				
        //System.out.println("current: " + df.format(cal.getTime()));
        
        ArrayList <LoginCount> lastSeven = new ArrayList<LoginCount>(); 
        //LoginCount day1 = new LoginCount();
        
        try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			
			//db에 "며칠전(time1)"인자를 넣어야하기 때문에 날짜를 만든다. 
			Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        cal.add(Calendar.DATE, -6);
	        
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        String time1 = new String();
	        time1 = df.format(cal.getTime());			
			System.out.println("오늘로부터 7일전 날짜는 : "+time1);
	        
			List<LoginCount> count = new ArrayList<LoginCount>();
			
			//count=session.selectList("aaa.bbb.ccc.BaseMapper.sevenDayLogins");
			count=session.selectList("aaa.bbb.ccc.BaseMapper.sevenDayLoginsSubQ", time1);
			System.out.println("db에서 넘어온 7일전 로그인카운트 객체 수: "+count.size());		
			
			for(int l=0; l<7; l++) {
				//캘린더 객체 만들어서 오늘로 지정 
    			Calendar cal1 = Calendar.getInstance();
	        	cal1.setTime(new Date());
	        	//오늘에서부터 하루씩 마이너스 된 날짜로 반복 
	        	cal1.add(Calendar.DATE, -l);
	        	
	        	//cal1의 월,일을 분리해서 각각 자바 먼쓰, 자바데이트에 넣어줌. 
	        	DateFormat m = new SimpleDateFormat("M");
	        	DateFormat d = new SimpleDateFormat("d");
	        	String javaMonth = m.format(cal1.getTime());
    	       	String javaDate = d.format(cal1.getTime());
				
    	       	//String temp = javaMonth+"-"+javaDate+"-0";
    	       	
    	       	//로그인 
    	       	LoginCount test = new LoginCount();
    	       	test.setLoginCount("0");
    	       	test.setLoginMonth(javaMonth);
    	       	test.setLoginDate(javaDate);
    	       	test.setLoginSuccess(0);
    	       	
				for(int h=0; h<count.size();h++) {
					//로그인 객체 day0에 sql에서 받아온 로그인 배열을 순서대로 집어넣는다.
					LoginCount day0 = count.get(h);
					String sqlMonth = day0.getLoginMonth();
		    		String sqlDate = day0.getLoginDate(); 		    		
		    		
		    		//System.out.println("javaMonth :"+javaMonth+"---"+"sqlMonth: "+sqlMonth);
		    		//System.out.println("javaMonth :"+javaDate+"---"+"sqlMonth: "+sqlDate);
		    		if(javaMonth.equals(sqlMonth) && javaDate.equals(sqlDate)) {
        	       		//temp = javaMonth+"-"+javaDate+"-"+day0.getLoginCount();
        	       		test.setLoginCount(day0.getLoginCount());
        	       		test.setLoginSuccess(day0.getLoginSuccess());
        	       		
        	       		System.out.println("로그인이 있었던 날 : "+javaMonth+"-"+javaDate+"-"+"로그인 카운트 수:"+day0.getLoginCount());
        	       	  
		    		}else {
		    			System.out.println("로그인이 없었던 날 : "+javaMonth+"-"+javaDate+"-"+"로그인 카운트 수:"+test.getLoginCount());
		    		}
				}
				lastSeven.add(test);
				//System.out.println(lastSeven);
			}
			System.out.println(lastSeven);

    		
			
		}catch (IOException e){
			e.printStackTrace();
		}

	
		return lastSeven;
	}
	
	
	@RequestMapping(value = "admin/ipGeoTest", method = RequestMethod.GET)
	public String ipGeoTest(Locale locale, Model model, HttpServletRequest request ) {
		String result = new String();
		
		System.out.println(getClientIp(request));
		
		IPGeolocationAPI api = new IPGeolocationAPI("");
		
		GeolocationParams geoParams = new GeolocationParams();
		geoParams.setIPAddress(getClientIp(request));
		geoParams.setFields("geo,time_zone,currency");
		geoParams.setIncludeSecurity(true);
		Geolocation geolocation = api.getGeolocation(geoParams);
		System.out.println("나라코드는  "+geolocation.getCountryCode2());
		
		if(geolocation.getStatus() == 200) {
		    System.out.println(geolocation.getCountryName());
		    System.out.println(geolocation.getCurrency().getName());
		    System.out.println(geolocation.getTimezone().getCurrentTime());
		    
		} else {
		    System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
		    geoParams.setIPAddress("13.225.134.116");
			Geolocation geolocationResult = api.getGeolocation(geoParams);
			
			System.out.println(" 변환된 나라코드는  "+geolocationResult.getCountryCode2());
		}    
		
		return result;
		
	
	}
	
	
	
}
//로그인 체크
//어떤 아이디를 
//어디에서
//언제
//로그인을 성공하고/실패했는지
//쓰기 읽기만 있다. 수x 지우기 x
	
//디비에다가..
//int id
//String login id
//String sourceIp  255.1.1.1
//172.0.0.1 < 내 컴퓨터
//10.0.0.0 < 내 라우터(게이트웨이)

//내컴퓨터 >게이트웨이 > 인터넷프로바이더 >인터넷 
//172.0.0.1 > 10.0.0.1 > 123.23.23.30(퍼블릭 ip) 유동 / 고정 

//Date created
//boolean successful
	
//logIn action
//boolean result = false

//비번 아이디 체크 (select count(*) from Member
	
//if(result){
//로그인 레코드에 한줄 쓰기; insert into 어쩌구저쩌구;
//id 자동완성, login 192.168.1.3
	
	//	return "home";
//}else{
	//로그인 레코드에 한줄 쓰기; insert into 어쩌구저쩌구;
	//return "logIn"; 
//

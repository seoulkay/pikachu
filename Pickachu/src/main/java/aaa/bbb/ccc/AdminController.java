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
import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
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
import aaa.bbb.ccc.entity.CountryData;
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
		
		//scheduleTaskUsingCronExpression();
		
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
		
		//System.out.println(getClientIp(ipRequest));
		
		
		
		
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
			
			IPGeolocationAPI api = new IPGeolocationAPI("b2351617dfad414eaaf53967ef6457e8");
			GeolocationParams geoParams = new GeolocationParams();
			
			geoParams.setIPAddress(getClientIp(ipRequest));
			geoParams.setFields("geo,time_zone,currency");
			geoParams.setIncludeSecurity(true);
			Geolocation geolocation = api.getGeolocation(geoParams);
			System.out.println("나라코드는  "+geolocation.getCountryCode2());

			if(geolocation.getStatus() == 200) {
			    System.out.println(geolocation.getCountryName());
			    System.out.println(geolocation.getCurrency().getName());
			    System.out.println(geolocation.getTimezone().getCurrentTime());
			    log1.setCountryCode(geolocation.getCountryCode2().toLowerCase());
			}else{
			    System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
			    geoParams.setIPAddress("13.225.134.116");
				Geolocation geolocationResult = api.getGeolocation(geoParams);
				log1.setCountryCode(geolocationResult.getCountryCode2().toLowerCase());
				System.out.println(" 변환된 나라코드는  "+geolocationResult.getCountryCode2().toLowerCase());
				System.out.println(log1.getCountryCode());
			}
			
			
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
	
	@RequestMapping(value = "admin/loginCountAjax", method = RequestMethod.GET)
	public @ResponseBody List <LoginLog> loginCountAjax(@RequestParam("data") int data1) {
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		//sql 뽑아오는 모든 데이
		List<LoginLog> count = new ArrayList<LoginLog>();
		
		List<LoginLog> countSuccessful = new ArrayList<LoginLog>();
		List<LoginLog> countLoginFailure = new ArrayList<LoginLog>();
		
		//ajax로 보낼 리턴 데이
		List<LoginLog> result = new ArrayList<LoginLog>();

		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("M");
		DateFormat df2 = new SimpleDateFormat("d");
		
		for(int i = 0 ; i < 7 ; i ++ ) {
			LoginLog test1 = new LoginLog();
			Calendar cal = Calendar.getInstance();
	        cal.setTime(new Date());
	        cal.add(Calendar.DATE, -i);
	        test1.setDate(df2.format(cal.getTime()));
	        test1.setMonth(df1.format(cal.getTime()));
	        test1.setSuccessTotal(0);
	        test1.setTotal(0);
	        result.add(test1);
	        countSuccessful.add(test1);
	        countLoginFailure.add(test1);

			  }
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
				Calendar cal = Calendar.getInstance();
		        cal.setTime(new Date());
		        cal.add(Calendar.DATE, -7);
		        String time1 = df.format(cal.getTime());
		        System.out.println(time1);
		        count = session.selectList("aaa.bbb.ccc.BaseMapper.sevenDayLoginsSubQ", time1);
		        
		        //count 결과를 두개로 나눠보자
		        
		      
		    for(int i=0;i<countSuccessful.size();i++) {
		    	for(int j=0;j<count.size();j++) {
		    		if(countSuccessful.get(i).getMonth().equals(count.get(j).getMonth()) && 
		    				countSuccessful.get(i).getDate().equals(count.get(j).getDate()) &&
		    				count.get(j).getLoginSuccess() == 1) 
		    		{ //로그인 성공 횟수를 날짜별로 비교해서 넣어준다  
		    			countSuccessful.get(i).setSuccessTotal(count.get(j).getTotal());
		    		}
		    	}     	
		    }
		    
		    for(int i=0;i<countLoginFailure.size();i++) {
		    	for(int j=0;j<count.size();j++) {
		    		if(countLoginFailure.get(i).getMonth().equals(count.get(j).getMonth()) && 
		    				countLoginFailure.get(i).getDate().equals(count.get(j).getDate()) &&
		    				count.get(j).getLoginSuccess() == 0) 
		    		{ //로그인 실패 횟수를 날짜별로 비교해서 넣어준다  
		    			countLoginFailure.get(i).setTotal(count.get(j).getTotal());
		    		}
		    	}     	
		    }
		    	
		    //리턴할 아이들을 만들어 보자 
		    for(int i=0;i<result.size();i++) {
				result.get(i).setTotal(countSuccessful.get(i).getSuccessTotal()+countLoginFailure.get(i).getTotal()); //전체 횟수를 합쳐서  넣어준다 
				System.out.println(result.get(i).getMonth()+"-"+result.get(i).getDate()+"의 로그인 전체횟수는 : "+result.get(i).getTotal());
			}
	
			for(int i=0;i<result.size();i++) {
				result.get(i).setSuccessTotal(countSuccessful.get(i).getSuccessTotal()); //성공횟수를 넣어준다 
				System.out.println(result.get(i).getMonth()+"-"+result.get(i).getDate()+"의 로그인 성공횟수는 : "+result.get(i).getSuccessTotal());
			}

		} catch (IOException e) {
			e.printStackTrace();	
			
		}
		
			return result;
	}
	
	
	@RequestMapping(value = "admin/ipGeoTest", method = RequestMethod.GET)
	public @ResponseBody Geolocation ipGeoTest(Locale locale, Model model, HttpServletRequest request ) {
		String result = new String();
		
		//System.out.println(getClientIp(request));
		
		IPGeolocationAPI api = new IPGeolocationAPI("b2351617dfad414eaaf53967ef6457e8");
		
		GeolocationParams geoParams = new GeolocationParams();
		geoParams.setIPAddress("125.209.222.142");
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
		
		
		return geolocation;
		
	
	}
	
	@RequestMapping(value = "admin/countCountryCodeAjax", method = {RequestMethod.GET})
	public @ResponseBody List<CountryData> countCountryCodeAjax(@RequestParam("data") int data1){
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<CountryData> countByCountry= new ArrayList<CountryData>();
		//List<CountryData> result= new ArrayList<CountryData>();
		System.out.println(countByCountry);
		CountryData countryCodes = new CountryData();
		countryCodes.setCountryCodeTotal(0);
		countryCodes.setCountryCode("");
		countByCountry.add(countryCodes);
		//result.add(countryCodes);
	
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			countByCountry = session.selectList("aaa.bbb.ccc.BaseMapper.countCountryCode");
			System.out.println("테스트 : "+countByCountry);
		} catch (IOException e) {
			e.printStackTrace();	
			
		}
		
		//scheduleTaskUsingCronExpression();
		
			return countByCountry;
		
		}
	
//	@Configuration
//	@EnableScheduling
//	public class SpringConfig {
//	    
//	}
//	
//	@Scheduled(cron = "1 * * * * ?")
//	public void scheduleTaskUsingCronExpression() {
//	 
//	    //long now = System.currentTimeMillis() / 1000;
//	    System.out.println(
//	      "크론 매분 실행");
//	}
//	
//	@RequestMapping(value = "admin/cronTest", method = RequestMethod.GET)
//	public void cronTest() {
//		
//		scheduleTaskUsingCronExpression();
//		
//	}


	
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

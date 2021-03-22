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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
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
import aaa.bbb.ccc.entity.JsoupReply;
import aaa.bbb.ccc.entity.LoginCount;
import aaa.bbb.ccc.entity.LoginLog;
import aaa.bbb.ccc.Scheduler;

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
		System.out.println("나라코드는  "+geolocation.getCountryCode2().toLowerCase());
		
		if(geolocation.getStatus() == 200) {
		    System.out.println(geolocation.getCountryName());
		    System.out.println(geolocation.getCurrency().getName());
		    System.out.println(geolocation.getTimezone().getCurrentTime());
		    
		} else {
		    System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
		    geoParams.setIPAddress("13.225.134.116");
			Geolocation geolocationResult = api.getGeolocation(geoParams);
			
			System.out.println(" 변환된 나라코드는  "+geolocationResult.getCountryCode2().toLowerCase());
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
	
	
//	@Scheduled(cron = "*/20 * * * * * ")
//	public void scheduleCron() {
//		
//		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
//		Date time = new Date();
//		String time1 = format1.format(time);
//		
//	  
//	    System.out.println(
//	      "어드민컨트롤러에서 20초마다 크론 실행 : " + time1);
//	}
	
//	@Scheduled(cron = "*/4 * * * * * ")
//	public void nullCountryCheck() {
//		
//		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
//		Date time = new Date();
//		String time1 = format1.format(time);
//	  
//	    System.out.println(
//	      "4초마다 DB에서 국가코드 null id 찾아라 : " + time1);
//	    
//	    List <Integer> countryNull = new ArrayList <Integer>() ;
//	    
//	    String resource = "aaa/bbb/ccc/mybatis_config.xml";
//		InputStream inputStream;
//										
//		try {
//
//			inputStream = Resources.getResourceAsStream(resource);
//			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//			SqlSession session = sqlSessionFactory.openSession();
//			countryNull = session.selectList("aaa.bbb.ccc.BaseMapper.noCountryId");
//			
//			System.out.println(countryNull);
//			
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//	
//	}
	
	
	
	//스케줄러 파일
	@Scheduled(cron = "* */5 * * * * ")
	public void nullCountryIdUpdate() {

		Date date_now = new Date(System.currentTimeMillis());
		
		List<Integer> idArray = new ArrayList<Integer>(); 
		idArray = getCountryNullId();				 
		
		//updateCountryCode(1,"8.8.8.8" );		
		
		for(int i = 0; i<idArray.size(); i++) {			
			updateCountryCode(idArray.get(i), noCountryIP(idArray.get(i)));		
			System.out.println(date_now);
		}
		
		//jsoupTest();
		
	}
	

	
	
	
	//noCountryIP(getCountryNullId().get(i)) 로 받아온 아이피들을 국가코드로 변환하고,
	
	public static void updateCountryCode(Integer id, String ip) {
		
		
		LoginLog log = new LoginLog();
		
		IPGeolocationAPI api = new IPGeolocationAPI("b2351617dfad414eaaf53967ef6457e8");
		GeolocationParams geoParams = new GeolocationParams();		
		
		geoParams.setIPAddress(ip);
		geoParams.setFields("geo,time_zone,currency");
		geoParams.setIncludeSecurity(true);
		Geolocation geolocation = api.getGeolocation(geoParams);
		
		log.setIdLoginTry(id);
		log.setCountryCode(geolocation.getCountryCode2().toLowerCase());
		
		System.out.println("log의 아이디: "+log.getIdLoginTry());
		System.out.println("log의 새 국가코드: "+log.getCountryCode());
		System.out.println(log);
		
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			session.update("aaa.bbb.ccc.BaseMapper.updateCountryCode", log );
			
			session.commit();
			session.close();
						
		}catch (IOException e) {
				e.printStackTrace();
				System.out.println("서버 업데이트 실패");
		}
		
	}
	
	//디비에서 국가코드가 null인 행들을 찾기. 인티저 아이디의 배열이 리턴된다. (파라미터 없음, 리턴은 인트 배열)
	public static List<Integer> getCountryNullId(){
		List<Integer> countryNull = new ArrayList<Integer>();
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
											
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			countryNull = session.selectList("aaa.bbb.ccc.BaseMapper.getCountryNullId");
				
			//System.out.println(countryNull);
				
		}catch (IOException e) {
				e.printStackTrace();
		}
		
		return countryNull;
	}
	
	//국가코드 없는 아이디를 가지고 아이피 알아내기.
	public static String noCountryIP(Integer id) {	
		
		
//		for(int i = 0; i<getCountryNullId().size(); i++) {
//			noCountryIP(getCountryNullId().get(i));
//		}
		
		String countryNullIp = "";
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			//디비에서 아이디를 가지고 아이피 하나 셀렉해서 스트링에 넣기.
			countryNullIp = session.selectOne("aaa.bbb.ccc.BaseMapper.noCountryIP", id);
			
			//SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
			//Date time = new Date();
			//String time1 = format1.format(time);
			
			//System.out.println(countryNullIp+" : "+time1);
				
		}catch (IOException e) {
				e.printStackTrace();
		}
		
		return countryNullIp;
	}

	

	
	


	
	//iamk.shop의 게시판의 내용을 가져와서 내 데이터 베이스에 넣는다.
	
	public static List<JsoupReply> jsoupTest2() {
		//String head = "";
		
		Document doc;
		
		//JSON배열인 replies를 반복돌려서 하나씩 JAVA 객체로 만들어야 한다. 			
		List<JsoupReply> reList = new ArrayList<JsoupReply>();	
		
		try {
			doc = Jsoup.connect("http://www.iamk.shop:8080/reply?page=0").ignoreContentType(true).get();
			//ignoreContentType(true) --> 해당 주소에서 파싱한 데이터형태가 JSON이기 때문에 doc의 자료형인 html과 충돌한다. 그래서 그거 무시하기 위한 함수.
			
			//System.out.println(doc);
						//System.out.println("웹사이트 헤더 : "+ doc.title());
						//Elements newsHeadlines = doc.select(".masthead");
						//Elements contacts = doc.select(".contact-section .container .row .col-md-4 .card .card-body .text-uppercase ");
						//Elements contacts = doc.select(".contact-section .row .col-lg-8 .table #tbody tr");
						//Elements contacts = doc.select("body");
			
			//바디 안에 있는 내용만 문자로 만들어라.
			String tBody = doc.select("body").text();
			//그런데 지금 스트링 상태임. 이 자료들을 어떻게 데려올 것인가...?
			
			//스트링으로 받은 자료를 JSON으로 파싱하고, 그걸 JAVA 객체로 만드는게 목표  
			//simple.parser 레포지토리 이용 
			JSONParser parser = new JSONParser();
			
			//파싱한 걸 갖다가 그냥 객체 안에 넣는다.  
			Object obj = parser.parse(tBody);				
			
			//그 객체를 JSON 객체 jsonObj 만든다.
			// () 캐스팅 : 비슷한 애들중에 변환이 가능한 객체들을 하나로 변환시킬 때 캐스팅을 할 수 있음. 
			JSONObject jsonObj = (JSONObject) obj;
						
			//가지고 온 자료 안의 한 녀석이 배열이어서, 그걸 따로 불러내서 JSON배열 객체로 만든다.
			JSONArray replies= (JSONArray) jsonObj.get("replies");
			
			//JSON 배열 형태임			
							
			
								
			
			for(int i=0;i<replies.size();i++) {
				
				//일단 JSON객체로 만든다.
				JSONObject reply = (JSONObject) replies.get(i);
				
				//JSON 객체에 있는 것들을 자바 객체에 넣는 작업 
				JsoupReply re = new JsoupReply();
				re.setPassword(reply.get("password").toString());
				re.setContent(reply.get("content").toString());
				re.setWriter(reply.get("writer").toString());
				re.setId(Integer.parseInt(reply.get("id").toString()));
				re.setPublished(Boolean.parseBoolean(reply.get("published").toString()));				
				reList.add(re);
				//System.out.println(reply.get("password"));						
			}

			System.out.println(reList);
			
		
			
							//			for (Element headline : newsHeadlines) {
							//				System.out.println(headline.text());
							//			}
										
							//			for (Element contact : contacts) {
							//				System.out.println(contact.text());
							//			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reList;

	}
	
	
	
	@Scheduled(cron = "*/10 * * * * * ")
	public void jsoupSchedular() {
		
		//jsoupTest();
		//reListToDB(jsoupTest2());				
		//jsoupTest3();
		//jsoupLastIdCheck();
		//reListToDB(jsoupTest0());
		//jsoupTest5();
		jsoupListCopy();
	}
	
	
	//reList 디비에 넣기 마이바티스 함수  
	public static void reListToDB(List<JsoupReply> reList){
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			
			session.insert("aaa.bbb.ccc.BaseMapper.insertResultAll", reList);
			
			session.commit();
			session.close();
			
			System.out.println("DB에 자료를 잘 넣었습니다.");
			
		}catch (Exception e) {
				e.printStackTrace();
		}
	}
	
	//디비 자료 개수 세기 
	public static Integer countDB() {
		Integer count = 0;
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			count=session.selectOne("aaa.bbb.ccc.BaseMapper.countDB");
			System.out.println("hw_utf8.JsoupBoard의 현재 데이터 수는 "+count+"개 입니다.");
		}catch (IOException e) {
				e.printStackTrace();
		}
		
		return count;
	}
	
	
	//디비 마지막 아이디 확인하기
	public static Integer lastIdDB(){
		Integer id = 0;
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			id = session.selectOne("aaa.bbb.ccc.BaseMapper.lastId");
			System.out.println("DB의 최신 아이디는 : "+id+"입니다.");
			
		}catch (Exception e) {
				e.printStackTrace();
		}
		return id;
	}	

	
	//게시판 마지막 아이디 확인하기  
	public static Integer jsoupLastIdCheck() {
		Document doc0;
		Integer lastId = 0;
		
		try {
			doc0 = Jsoup.connect("http://www.iamk.shop:8080/reply").ignoreContentType(true).get();
			
			String tBody0 = doc0.select("body").text();
			
			JSONParser parser0 = new JSONParser();
			
			Object obj0 = parser0.parse(tBody0);				
			
			JSONObject jsonObj0 = (JSONObject) obj0;						
			JSONArray replies= (JSONArray) jsonObj0.get("replies");
			JSONObject reply = (JSONObject) replies.get(0);
			
			lastId = Integer.parseInt(reply.get("id").toString());
			System.out.println("iamk 게시판의 최신 아이디는 : "+ lastId+"입니다.");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lastId;
	}
	

	
	//자료가 없을 때까지 게시판 자료 가져와서 리스트에 넣는 함수
	public static List<JsoupReply> jsoupUntilNull() {
		
		Document doc;
		List<JsoupReply> reList = new ArrayList<JsoupReply>();	

		Integer i = 0;
		while(true) {
			try {
				doc = Jsoup.connect("http://www.iamk.shop:8080/reply?page="+i).ignoreContentType(true).get();
				
				String tBody = doc.select("body").text();
				JSONParser parser = new JSONParser(); 
				Object obj = parser.parse(tBody);				
				JSONObject jsonObj = (JSONObject) obj;
				JSONArray replies= (JSONArray) jsonObj.get("replies");
				
				if(replies.size()==0) {
					System.out.println("더이상 가져올 게시판 글이 없어서 전체 게시판 복사하기를 종료할 예정입니다.");
					break;
					
				}else {											
					for(int j=0;j<replies.size();j++) {
						
						JSONObject reply = (JSONObject) replies.get(j);

						JsoupReply re = new JsoupReply();
						re.setPassword(reply.get("password").toString());
						re.setContent(reply.get("content").toString());
						re.setWriter(reply.get("writer").toString());
						re.setId(Integer.parseInt(reply.get("id").toString()));
						re.setPublished(Boolean.parseBoolean(reply.get("published").toString()));				
						reList.add(re);
					}
				}
			//System.out.println(reList);

			} catch (Exception e) {
				e.printStackTrace();
			}
		i++;
		}
		return reList;

	}

	//디비에 없는 게시판의 새 글을 데려오는 함수
	public static List<JsoupReply> jsoupMinusDB() {
		
		Document doc;		
		List<JsoupReply> reList = new ArrayList<JsoupReply>();	

		Integer last = lastIdDB();
		
		boolean outerLoop = false;
		int i = 0;
		
		while(true) {

			try {
				doc = Jsoup.connect("http://www.iamk.shop:8080/reply?page="+i).ignoreContentType(true).get();
				
				String tBody = doc.select("body").text();
				System.out.println(tBody);
				
				JSONParser parser = new JSONParser(); 
				Object obj = parser.parse(tBody);				
				JSONObject jsonObj = (JSONObject) obj;
				JSONArray replies= (JSONArray) jsonObj.get("replies");
								
				if(replies.size()==0) {
					break;
				}
				
				
				for(int j=0;j<replies.size();j++) {
					
				JSONObject reply = (JSONObject) replies.get(j);
					//이프 : 라스트 아이디 >= 리플라이의 아이디 
					if(Integer.parseInt(reply.get("id").toString())<=last) {
						System.out.println("게시판 글 최신 아이디: "+Integer.parseInt(reply.get("id").toString())+"디비 최신 아이디: "+last);
						outerLoop =true;
						break;
					}

					JsoupReply re = new JsoupReply();
					re.setPassword(reply.get("password").toString());
					re.setContent(reply.get("content").toString());
					re.setWriter(reply.get("writer").toString());
					re.setId(Integer.parseInt(reply.get("id").toString()));
					re.setPublished(Boolean.parseBoolean(reply.get("published").toString()));				
					reList.add(re);
				}

			//System.out.println(reList);
			
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(outerLoop) {
				break;
			}
			
		i++;
		}
		return reList;

	}
	
	
	public static void jsoupListCopy() {
				
		//1.내 디비에 자료가 있는지 확인해라.(count) ---> countDB() 함수B		
		if(countDB()==0) {
			//2.자료가 없으면(countDB()=0) 
			//jsoup으로 게시판 0페이지부터 내용 전체를 데려와라.
				//페이지를 계속 반복해서 데려온다.
				//데려온 자료가 null이 뜨면 작업 종료.
			//내 디비에 넣어라. 함수A
			reListToDB(jsoupUntilNull());
		}else{
			//3.자료가 있으면(countDB()!=0)
			//없는 것만 데려와라.
				//jsoup으로 0페이지 마지막 id를 구해라. ==> jsoupLastIdCheck();
				//내 디비 마지막 id를 구해라. ==> lastIdDB();
				//둘을 비교해서
				Integer idCheck = jsoupLastIdCheck();
				Integer lastDbid = lastIdDB();
				
				if(idCheck<=lastDbid){
					//같거나 jsoup id가 더 작으면 -> 작업 종료
					System.out.println("귀하의 복사 게시판은 최신입니다.");
				}else {
					//jsoup 아이디가 더 크면
					//두 아이디를 뺀 숫자만큼의 jsoup 자료를 데려와라.
					//내 디비에 넣어라. 함수A
					//reListToDB(두 아이디 뺀 숫자만큼의 Jsoup 배열);
					reListToDB(jsoupMinusDB());
				}

		}

		
	}
	

	
	
	
	
	
	
	
	
	public static List<JsoupReply> jsoupTest0() {
		
		Document doc0;
		Long totalPages = (long) 0;
		
		try {
			doc0 = Jsoup.connect("http://www.iamk.shop:8080/reply").ignoreContentType(true).get();	
			String tBody0 = doc0.select("body").text();
			
			JSONParser parser0 = new JSONParser();
			Object obj0 = parser0.parse(tBody0);				
			JSONObject jsonObj0 = (JSONObject) obj0;
			totalPages= (Long) jsonObj0.get("totalPages");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
						
		
		Document doc;
		//JSON배열인 replies를 반복돌려서 하나씩 JAVA 객체로 만들어야 한다. 			
		List<JsoupReply> reList = new ArrayList<JsoupReply>();	

		//토탈페이지 수만큼 반복을 돌려서 reList에 JsoupReply객체들을 집어넣는다. 
		for(int i=0; i<totalPages;i++) {
			//조건 필요 : 내 디비 최고값이랑 가져온 글의 최고값이랑 비교하는 글이 필요하다.
			
			try {
				doc = Jsoup.connect("http://www.iamk.shop:8080/reply?page="+i).ignoreContentType(true).get();
				
							
				//바디 안에 있는 내용만 문자로 만들어라.
				String tBody = doc.select("body").text();
				//그런데 지금 스트링 상태임. 이 자료들을 어떻게 데려올 것인가...?
				
				//스트링으로 받은 자료를 JSON으로 파싱하고, 그걸 JAVA 객체로 만드는게 목표  
				//simple.parser 레포지토리 이용 
				JSONParser parser = new JSONParser();
				
				//파싱한 걸 갖다가 그냥 객체 안에 넣는다.  
				Object obj = parser.parse(tBody);				
				
				//그 객체를 JSON 객체 jsonObj 만든다.
				// () 캐스팅 : 비슷한 애들중에 변환이 가능한 객체들을 하나로 변환시킬 때 캐스팅을 할 수 있음. 
				JSONObject jsonObj = (JSONObject) obj;
							
				//가지고 온 자료 안의 한 녀석이 배열이어서, 그걸 따로 불러내서 JSON배열 객체로 만든다.
				JSONArray replies= (JSONArray) jsonObj.get("replies");
				
				//JSON 배열 형태임			
								
	
				
				for(int j=0;j<replies.size();j++) {
					
					//일단 JSON객체로 만든다.
					JSONObject reply = (JSONObject) replies.get(j);
					
					//JSON 객체에 있는 것들을 자바 객체에 넣는 작업 
					JsoupReply re = new JsoupReply();
					re.setPassword(reply.get("password").toString());
					re.setContent(reply.get("content").toString());
					re.setWriter(reply.get("writer").toString());
					re.setId(Integer.parseInt(reply.get("id").toString()));
					re.setPublished(Boolean.parseBoolean(reply.get("published").toString()));				
					reList.add(re);
					//System.out.println(reply.get("password"));						
				}

			//System.out.println(reList);
			
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return reList;

	}
	
	public static void jsoupTest3() {
		//String head = "";
		
		Document doc;
		
		try {
			doc = Jsoup.connect("https://www.naver.com/").get();
			System.out.println("웹사이트 헤더 : "+ doc.title());
			
			Elements navList = doc.select(".list_nav");
			Elements issues = doc.select(".issue_area");
			Elements stocks = doc.select(".card_stock");
			
			for (Element nav : navList) {
				System.out.println("네비게이션 헤더 : " + nav.text());
			}
			
			for (Element issue : issues) {
				System.out.println("실시간 이슈 : "+ issue.text());
			}
			
			for (Element stock : stocks) {
				System.out.println(stock.text());
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		}

	}
	
	
	
	
	
	public static void jsoupTest() {
		//String head = "";
		
		Document doc;
		
		try {
			doc = Jsoup.connect("https://en.wikipedia.org/").get();
			System.out.println(doc.title());
			
			Elements newsHeadlines = doc.select("#mp-itn b a");
			
			for (Element headline : newsHeadlines) {
				System.out.println("\n\t"+ headline.attr("title")+ headline.absUrl("href"));
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	
	
	public static void jsoupTest5() {
		Document doc0;
		
		try {
			doc0 = Jsoup.connect("https://mania.kr/g2/bbs/board.php?bo_table=loltalk&page=3").ignoreContentType(true).get();
			
			System.out.println(doc0);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//https://mania.kr/g2/bbs/board.php?bo_table=loltalk&page=3
	
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

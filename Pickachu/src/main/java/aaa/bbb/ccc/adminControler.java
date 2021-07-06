package aaa.bbb.ccc;


import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
import aaa.bbb.ccc.entity.countryData;
import aaa.bbb.ccc.entity.loginLog;
import aaa.bbb.ccc.entity.newsTitle;
import aaa.bbb.ccc.entity.rangeData;
import aaa.bbb.ccc.entity.top20Json;
import aaa.bbb.ccc.AlarmTask;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;

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
		
		//countryCode 생성해서 넣어주자 
		
		IPGeolocationAPI api = new IPGeolocationAPI("dc908921857b45f2b8083a266fbc964d");
		System.out.println("테스트실행중");
		GeolocationParams geoParams = new GeolocationParams();
		geoParams.setIPAddress(getClientIp(request));
		geoParams.setFields("geo,time_zone,currency");
		geoParams.setIncludeSecurity(true);
		Geolocation geolocation = api.getGeolocation(geoParams);
		System.out.println("나라코드는  "+geolocation.getCountryCode2());
		Member loginLog = new Member();
		loginLog.setLoginId(id);
		loginLog.setSourceIp(getClientIp(request));
		
		if(geolocation.getStatus() == 200) {
		    System.out.println(geolocation.getCountryName());
		    System.out.println(geolocation.getCurrency().getName());
		    System.out.println(geolocation.getTimezone().getCurrentTime());
		    loginLog.setCountryCode(geolocation.getCountryCode2());
		    
		   
		   // System.out.println(geolocation.getGeolocationSecurity().getAnonymous());
		   // System.out.println(geolocation.getGeolocationSecurity().getKnownAttacker());
		   // System.out.println(geolocation.getGeolocationSecurity().getProxy());
		   // System.out.println(geolocation.getGeolocationSecurity().getProxyType());
		   // System.out.println(geolocation.getGeolocationSecurity().getAnonymous());
		   // System.out.println(geolocation.getGeolocationSecurity().getCloudProvider());
		} else {
		    System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
		    geoParams.setIPAddress("13.225.134.116");
			Geolocation geolocationResult = api.getGeolocation(geoParams);
			loginLog.setCountryCode(geolocationResult.getCountryCode2());
			System.out.println(" 변환된 나라코드는  "+geolocationResult.getCountryCode2());
		}
		
		
		//대쉬보드에서 먹히게 소문자로 변환해주자 
		//String input = loginLog.getCountryCode();
		String input = loginLog.getCountryCode();
		String output = "";
		char tmp;
		for(int i=0; i < input.length(); i++) {
			tmp = input.charAt(i);
			if((65 <= tmp) && (tmp <=90)) {
				output += input.valueOf(tmp).toLowerCase();
			}else {
				output +=(char)tmp;
			}
		}
		
		System.out.println("소문자변환은 "+output);
		loginLog.setCountryCode(output);
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		Integer result = 0;
		Member idPsw = new Member();
		idPsw.setId(id);
		idPsw.setPassword(password);
		
		
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
	
	@RequestMapping(value = "countCountryCodeAjax", method = {RequestMethod.GET})
	public @ResponseBody List<countryData> countCountryCodeAjax(@RequestParam("data") int data1){
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<countryData> count= new ArrayList<countryData>();
		List<countryData> result= new ArrayList<countryData>();
		System.out.println(count);
		countryData countryCodeUse = new countryData();
		countryCodeUse.setCountryCodeTotal(0);
		countryCodeUse.setCountryCode("");
		count.add(countryCodeUse);
		result.add(countryCodeUse);
	
		try {
			
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			count = session.selectList("aaa.bbb.ccc.BaseMapper.countCountryCode");
			System.out.println("요기다 테스트  "+count);
		} catch (IOException e) {
			e.printStackTrace();	
			
		}
		
			return count;
		
		}
	
//	@Scheduled(cron = "*/60 * * * * *")
//	public void searchNull() {
//		String resource = "aaa/bbb/ccc/mybatis_config.xml";
//		InputStream inputStream;
//		
//		List<Integer> result= new ArrayList<Integer>();
//		
//		try {
//			
//			inputStream = Resources.getResourceAsStream(resource);
//			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//			SqlSession session = sqlSessionFactory.openSession();
//			result = session.selectList("aaa.bbb.ccc.BaseMapper.searchCountryCodeNullId");
//			System.out.println("받아온 아이들은  "+result);
//			
//		} catch (IOException e) {
//			e.printStackTrace();	
//			
//		}
//		
//		System.out.println("받아온 아이들은  "+result);	
//	}
//	
//	@Scheduled(cron = "*/60 * * * * *")
//	public void cronTest() {
//		System.out.println("컨트롤러다잇 cron = */60 * * * * * 이며, 1분마다 호출됩니다. ");
//	}
	
//	public List<Integer> getCountryNullLoginAttempts(){
//		List<Integer> result = new ArrayList<Integer>();
//		
//		return result;
//	}
	
	
	@RequestMapping(value = "loginCountAjax", method = {RequestMethod.GET})
	public @ResponseBody List<loginLog>  loginCountAjax(@RequestParam("data") int data1){

		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<loginLog> count = new ArrayList<loginLog>();
		List<loginLog> countSuccessful = new ArrayList<loginLog>();
		List<loginLog> countLoginFailure = new ArrayList<loginLog>();
		List<loginLog> result = new ArrayList<loginLog>();
		
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df1 = new SimpleDateFormat("M");
		DateFormat df2 = new SimpleDateFormat("d");
		
		for(int i = 0 ; i < 7 ; i ++ ) {
			loginLog test1 = new loginLog();
			Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(new Date());
	        cal2.add(Calendar.DATE, -i);
	        test1.setDay(df2.format(cal2.getTime()));
	        test1.setMonth(df1.format(cal2.getTime()));
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
		        count = session.selectList("aaa.bbb.ccc.BaseMapper.countNdaySuccessLogin", time1);
		        
		        //count 결과를 두개로 나눠보자
		        
		        
		    for(int i=0;i<countSuccessful.size();i++) {
		    	for(int j=0;j<count.size();j++) {
		    		if(countSuccessful.get(i).getMonth().equals(count.get(j).getMonth()) && 
		    				countSuccessful.get(i).getDay().equals(count.get(j).getDay()) &&
		    				count.get(j).getSuccessful() == 1) { //로그인 성공 횟수를 날짜별로 비교해서 넣어준다  
		    			countSuccessful.get(i).setSuccessTotal(count.get(j).getTotal());
		    		}
		    	}     	
		    }
		    
		    for(int i=0;i<countLoginFailure.size();i++) {
		    	for(int j=0;j<count.size();j++) {
		    		if(countLoginFailure.get(i).getMonth().equals(count.get(j).getMonth()) && 
		    				countLoginFailure.get(i).getDay().equals(count.get(j).getDay()) &&
		    				count.get(j).getSuccessful() == 0) { //로그인 실패 횟수를 날짜별로 비교해서 넣어준다  
		    			countLoginFailure.get(i).setTotal(count.get(j).getTotal());
		    		}
		    	}     	
		    }
		    	
		    //리턴할 아이들을 만들어 보자 
		    for(int i=0;i<result.size();i++) {
				result.get(i).setTotal(countSuccessful.get(i).getSuccessTotal()+countLoginFailure.get(i).getTotal()); //전체 횟수를 합쳐서  넣어준다 
				System.out.println(result.get(i).getMonth()+"-"+result.get(i).getDay()+"의 로그인 전체횟수는 : "+result.get(i).getTotal());
			}
	
			for(int i=0;i<result.size();i++) {
				result.get(i).setSuccessTotal(countSuccessful.get(i).getSuccessTotal()); //성공횟수를 넣어준다 
				System.out.println(result.get(i).getMonth()+"-"+result.get(i).getDay()+"의 로그인 성공횟수는 : "+result.get(i).getSuccessTotal());
			}

		} catch (IOException e) {
			e.printStackTrace();	
			
		}
		
			return result;
		
		}
	
	@RequestMapping(value = "admin/apiTest", method = {RequestMethod.GET})
	public @ResponseBody Geolocation apiTest(Locale locale, Model model){
	
		
		IPGeolocationAPI api = new IPGeolocationAPI("dc908921857b45f2b8083a266fbc964d");
		System.out.println("테스트실행중");
		GeolocationParams geoParams = new GeolocationParams();
		geoParams.setIPAddress("125.209.222.142");
		geoParams.setFields("geo,time_zone,currency");
		geoParams.setIncludeSecurity(true);
		Geolocation geolocation = api.getGeolocation(geoParams);
		
		if(geolocation.getStatus() == 200) {
		    System.out.println(geolocation.getCountryName());
		    System.out.println(geolocation.getCurrency().getName());
		    System.out.println(geolocation.getTimezone().getCurrentTime());
		   // System.out.println(geolocation.getGeolocationSecurity().getAnonymous());
		   // System.out.println(geolocation.getGeolocationSecurity().getKnownAttacker());
		   // System.out.println(geolocation.getGeolocationSecurity().getProxy());
		   // System.out.println(geolocation.getGeolocationSecurity().getProxyType());
		   // System.out.println(geolocation.getGeolocationSecurity().getAnonymous());
		   // System.out.println(geolocation.getGeolocationSecurity().getCloudProvider());
		} else {
		    System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
		}
		// Get geolocation in Russian** for IP address (1.1.1.1) and all fields
		
		geoParams.setIPAddress("1.1.1.1");
		geoParams.setLang("ru");

			return geolocation;
		
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
	
	
	@RequestMapping(value = "TaskExecutorExample", method = RequestMethod.GET)
	public String test(Locale locale, Model model) {
		

		return "TaskExecutorExample";
	}
	
	@RequestMapping(value = "admin/index2", method = RequestMethod.GET)
	public String adminIndex2(Locale locale, Model model) {
	
		return "admin/index2";
	}
	
	
	@RequestMapping(value = "admin/index3", method = RequestMethod.GET)
	public String adminIndex3(Locale locale, Model model) {
	
		return "admin/index3";
	}
	
	@RequestMapping(value = "admin/index8", method = RequestMethod.GET)
	public String adminIndex8(Locale locale, Model model) {
	
		return "admin/index8";
	}
	
	@RequestMapping(value = "admin/index4", method = RequestMethod.GET)
	public String adminIndex4(Locale locale, Model model, String source) {
		
		newsTitle sourceIs = new newsTitle();
		sourceIs.setSource(source);
		
		List<String> temp = new ArrayList<String>();
//		temp = AlarmTask.getToDayData(sourceIs);
//		String temp3 = insertTop20New(String.join(" ",AlarmTask.top20(AlarmTask.pieceWord(AlarmTask.getToDayData(sourceIs)))),"naver");
		
//		System.out.println(temp3);
		
		
		temp = step1(AlarmTask.top20MaptoString(AlarmTask.getLastTop20(sourceIs)));
		
		System.out.println(temp);
		String str = String.join(" ",temp);
		System.out.println(str);
		
		String description = "백신,백신 ,백신,백신 ,백신 ,백신,백신,백신 ,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42\n" + 
				"";
		
		
		model.addAttribute("description", str );
		return "admin/index4";
	}
	
	
	@RequestMapping(value = "admin/index5", method = RequestMethod.GET)
	public String adminIndex5(Locale locale, Model model, String source) {
		
		newsTitle sourceIs = new newsTitle();
		sourceIs.setSource(source);
		
		List<String> temp = new ArrayList<String>();
//		temp = AlarmTask.getToDayData(sourceIs);
//		String temp3 = insertTop20New(String.join(" ",AlarmTask.top20(AlarmTask.pieceWord(AlarmTask.getToDayData(sourceIs)))),"naver");
		
//		System.out.println(temp3);
		
		
		temp = step1(AlarmTask.top20MaptoString(AlarmTask.getLastTop20(sourceIs)));
		
		System.out.println(temp);
		String str = String.join(" ",temp);
		System.out.println(str);
		
		String description = "백신,백신 ,백신,백신 ,백신 ,백신,백신,백신 ,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,백신,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,오염수,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,정의용,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,김어준,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,방류,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,출연료,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,38,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,35,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,69,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,TBS,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,첫,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,대상,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,재판,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,대통령,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,63,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,부산,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,기모란,41,41,41,41,41,41,41,41,41,41,41,41,41,41,41,42,42,42,42,42,42,42,42,42,42,42,42,42,42,42\n" + 
				"";
		
		
		model.addAttribute("description", str );
		return "admin/index5";
	}
	
	
	@RequestMapping(value = "admin/index6", method = RequestMethod.GET)
	public String adminIndex6(Locale locale, Model model, String source) {
		
		newsTitle sourceIs = new newsTitle();
		sourceIs.setSource(source);
		String result = source;
		String url = newsSearchUrl(source);
		System.out.println("여기야 새로만든거 : "+jsonListToString(makeData(sourceIs)));
		
		model.addAttribute("url", url);
		model.addAttribute("source", result);
		
		return "admin/index6";
	}
	
	
	
	
	public static String jsonListToString(List<rangeData> p1) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			result = mapper.writeValueAsString(p1);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value = "/get/news20", method = {RequestMethod.GET})
	public @ResponseBody List<top20Json> news20(@RequestParam("data") String data){
	
		ObjectMapper mapper = new ObjectMapper();
		List<top20Json> jsonList = new ArrayList<top20Json>();
//		String jsonList="";
		
		newsTitle sourceIs = new newsTitle();
		sourceIs.setSource(data);
		jsonList = AlarmTask.orrm2(AlarmTask.top20MaptoString(AlarmTask.getLastTop20(sourceIs)));
//		System.out.println("news20Ajax 시작 : "+jsonList);
	
		
		
	    return jsonList;
	}
	// id를 넣으면 리스트를 반환한다. 
	@RequestMapping(value = "/get/news202", method = {RequestMethod.GET})
	public @ResponseBody List<rangeData> news202(@RequestParam("data") String data){

		List<rangeData> jsonList = new ArrayList<rangeData>();
		newsTitle sourceIs = new newsTitle();
		sourceIs.setSource(data);
		jsonList = makeData(sourceIs);

	    return jsonList;
	}
	
	
	public static String newsSearchUrl(String source) {
		String result = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=";
		String str = "daum";
		String strent = "daument";
		
		if (source.equals(str)) {
			result = "https://search.daum.net/search?w=tot&DA=23A&rtmaxcoll=NNS&q=";
		}else if (source.contentEquals(strent)) {
			result = "https://search.daum.net/search?w=tot&DA=23A&rtmaxcoll=NNS&q=";
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "ajaxSource", method = {RequestMethod.GET})
	public @ResponseBody String ajaxSource(@RequestParam("data") String source){
		String result = source;
		
		return result;
	
	}
	
	public static List<top20Json> checkNewsId(Integer id){
		List<top20Json> result = new ArrayList<top20Json>();
		
		
		return result;
	}
	
	public static List<rangeData> makeData(newsTitle source) {
		List<rangeData> result= new ArrayList<rangeData>();
		
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		List<newsTitle> top20 = new ArrayList<newsTitle>();
		
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			
			top20 = session.selectList("aaa.bbb.ccc.BaseMapper.10RangeData", source);
			
			for(int i=top20.size()-1; i >= 0; i-- ) {
				rangeData temp= new rangeData();
				System.out.println(i+"번째 반복 시작합니다.");
				temp.setId(i+1);
				temp.setCreated(top20.get(i).getCreated());
				temp.setTop20(AlarmTask.orrm2(top20MaptoString(top20.get(i).getTop20())));
				result.add(temp);
			}
			
		} catch (IOException e) {
			e.printStackTrace();	
			
		}
		
		return result;
	}
	
	public static Map<String,Integer> top20MaptoString(String p1) {
		Map<String,Integer> result = new HashMap<String,Integer>();
			String piece[] = p1.split(",");
			for(int i=0 ;i<piece.length ;i++) {
				String temp[] = piece[i].split(":");
				result.put(temp[0].trim(),Integer.parseInt(temp[1].trim()));
			}
	    System.out.println(result);
		return result ;
	}
	
	
	public static JSONObject convertMapToJson(Map<String, Integer> map) {
		
		JSONObject json = new JSONObject();
		
		
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			json.put(key, value);
		}
		
		
		return json;
	}
	
	
	public static String getFoosBySimplePathWithPathVariable(newsTitle id) {
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonList="";
				
				try {
					
					jsonList = mapper.writeValueAsString(AlarmTask.orrm2(AlarmTask.top20MaptoString(AlarmTask.getLastTop20(id))));
				}catch(IOException e) {
					e.printStackTrace();
				}
				
				
			    return jsonList.toString();
			}
			
	
	
	
	//admin/pages/tables/simple.html
	
	public static String insertTop20New(String p1, String p2) {
		String result = new String();
		
		result = p1;
		//받아온 맵을 키와 밸류로 구분해
		return  result;
	}
	
	public static void testtoday() {
		newsTitle sourceIs = new newsTitle();
		System.out.println(step1(AlarmTask.top20MaptoString(AlarmTask.getLastTop20(sourceIs))));
		
	}
	public static List<String> mapToListString(top20Json p1){
		List<String> result = new ArrayList<String>();
		Integer endPoint = p1.getSize();
		int roof = 0;
		while(roof < endPoint) {
			result.add(p1.getText());
			roof += 1;
		}
		
		return result;
	}
	
	
	public static List<String> step1(Map<String,Integer> p1){
		List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(p1.entrySet());
		List<String> result = new ArrayList<String>();
		Collections.sort(list_entries, new Comparator<Entry<String, Integer>>() {
			// compare로 값을 비교
			public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
				// 오름 차순 정렬
				return obj2.getValue().compareTo(obj1.getValue());
			}
		});
		
		int i = 0;
		for(Entry<String, Integer> entry : list_entries) {
			String title = entry.getKey();
			Integer j = entry.getValue();
			
			System.out.println(entry.getKey()+":"+entry.getValue());
			int roof = 0;
			while(roof < j) {
				System.out.println("반복해서 넣고 있지요 " + roof + "번째 돌리는 중입니다");
				result.add(title);
				roof += 1;
			}
			
			i += 1;

			if(i == 20) {
				break;
			}
	
		}
		
		return result;
	}
	
	
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



	
	
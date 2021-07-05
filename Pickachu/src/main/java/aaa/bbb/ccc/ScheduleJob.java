package aaa.bbb.ccc;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import aaa.bbb.ccc.AdminController;
import aaa.bbb.ccc.entity.PortalNews;
import aaa.bbb.ccc.entity.PortalNews2;

@Component
public class ScheduleJob {

//	@Scheduled(cron = "1 */2 * * * *")
//	public void getIamkay() {
//		System.out.println("새로운 스케줄러 작동중입니다.");
//	}
	
//	@Scheduled(cron = "1 */10 * * * * ")
//	public void naverTopNews() {
//		getNaver_newsHeadline();
//	}
//
//	@Scheduled(cron = "1 */15 * * * * ")
//	public void daumTopNews() {
//		getDaum_newsHeadline();			
//	}
//	
//	@Scheduled(cron = "1 */30 * * * * ")
//	public void topSportsNews() {
//		getNaverSports_newsHeadline();			
//		getDaumSports_newsHeadline();
//		getDcinside();
//	}
//
//	@Scheduled(cron = "1 */30 * * * * ")
//	public void top20News() {
//		news20();
//	}
//
//	@Scheduled(cron = "1 */20 * * * * ")
//	public void top20SportsNews() {
//		sportsNews20();
//	}	
	
//	@Scheduled(cron = "30 * * * * * ")
//	public void dc() {
//		getDcinside();
//
//	}	
		

//뉴스 채취 
	// 네이버 뉴스 가져오는 함수 
	public static  void getNaver_newsHeadline() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("네이버 뉴스 긁어올게 " +dateFormat.format(calendar.getTime())+" 기다려  ");
					
		try {
			Document doc = Jsoup.connect("https://news.naver.com/main/home.nhn").get();
			System.out.println(doc.title());
			Elements newsHeadlines = doc.select("ul[class=hdline_article_list]").select("li");
			PortalNews toDay = new PortalNews();
					
					
			for(Element elem : newsHeadlines) {
				toDay.setDescription(elem.select("li").text());
				toDay.setLink("https://news.naver.com/"+elem.select("div[class=hdline_article_tit] a").attr("href"));
				toDay.setSource("NAVER");				
				insertNews(toDay);						
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//다음헤드라인 뉴스 가져오는 함수 : 5개만 가져오기 
	public static void getDaum_newsHeadline() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("다음 뉴스 긁어올게 " +dateFormat.format(calendar.getTime())+" 기다려  ");
		
		try {
		Document doc = Jsoup.connect("https://www.daum.net/").get();
		System.out.println(doc.title());
		Elements newsHeadlines = doc.select("ul[class=list_txt]").select("li");
		PortalNews toDay = new PortalNews();
		int i = 0;
		
			for(Element elem : newsHeadlines) {
				toDay.setDescription(elem.select("li").text());
				toDay.setLink(elem.select("a").attr("href"));
				toDay.setSource("DAUM");
				i += 1 ;
				System.out.println(toDay.getLink());
				
				insertNews(toDay);
				System.out.println(i+"번째 뉴스 기록중");
				if(i == 5) {
					System.out.println("멈출게 5라서");
					break;
				}
			}

		}catch(IOException e) {
			e.printStackTrace();
		}

	}
	
//뉴스를 디비에 넣기.	공통 사용 함수 
	//뉴스를 하나씩 디비에 넣는 함수. 뉴스를 가져오는 네이버, 다음 함수 안에서 쓰여진다.
	public static void insertNews(PortalNews p1){
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		try {

			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();

			session.insert("aaa.bbb.ccc.BaseMapper.insertNewsByOne", p1);
			
			session.commit();
			session.close();
			
			System.out.println("DB에 자료를 잘 넣었습니다.");
			
		}catch (Exception e) {
				e.printStackTrace();
		}
	}

	
//스포츠 뉴스 채취 
	// 네이버 스포츠 뉴스 가져오는 함수 
	public static  void getNaverSports_newsHeadline() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("네이버 스포츠뉴스 긁어올게 " +dateFormat.format(calendar.getTime())+" 기다려  ");
					
		try {
			Document doc = Jsoup.connect("https://sports.news.naver.com/index").get();
			System.out.println(doc.title());
			Elements newsHeadlines = doc.select("ul[class=today_list]").select("li");
			PortalNews toDay = new PortalNews();
								
			for(Element elem : newsHeadlines) {
				//toDay.setDescription(elem.select("li[class=title]").text());
				toDay.setDescription(elem.select("li[class=today_item] a").attr("title"));
				toDay.setLink("https://sports.news.naver.com"+elem.select("li[class=today_item] a").attr("href"));
				toDay.setSource("NAVERSPORTS");				
				insertNews(toDay);						
			}
		}catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	
	//다음헤드라인 스포츠 뉴스 가져오는 함수 : 5개만 가져오기 
	public static void getDaumSports_newsHeadline() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("다음 스포츠뉴스 긁어올게 " +dateFormat.format(calendar.getTime())+" 기다려  ");
		
		try {
		Document doc = Jsoup.connect("https://sports.daum.net/").get();
		System.out.println(doc.title());
		Elements newsHeadlines = doc.select("ul[class=list_thumbs headline_type2]").select("li");
		PortalNews toDay = new PortalNews();
		int i = 0;
		
			for(Element elem : newsHeadlines) {
				toDay.setDescription(elem.select("li a div[class=cont_thumb] strong[class=tit_thumb]").text());
				toDay.setLink(elem.select("a").attr("href"));
				toDay.setSource("DAUMSPORTS");
				i += 1 ;
				System.out.println(toDay.getLink());
				
				insertNews(toDay);
				System.out.println(i+"번째 뉴스 기록중");
				if(i == 5) {
					System.out.println("멈출게 5라서");
					break;
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	//디시인사이드 실시간베스트 가져오는 함수 : 5개만 가져오기 
//	public static void getDcinside() {
//		Calendar calendar = Calendar.getInstance();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println("디시인사이드 실시간베스트 긁어올게 " +dateFormat.format(calendar.getTime())+" 기다려  ");
//		
//		try {
//		Document doc = Jsoup.connect("https://dcinside.com/").get();
//		System.out.println(doc.title());
//		Elements newsHeadlines = doc.select("article div[class=time_best] ul[class=typet_list p_1").select("li");
//		PortalNews toDay = new PortalNews();
//		int i = 0;
//		
//			for(Element elem : newsHeadlines) {
//				toDay.setDescription(elem.select("li a[class=main_log] div[class=box besttxt] p").text());
//				toDay.setLink(elem.select("li a[class=main_log]").attr("href"));
//				toDay.setSource("DCNOWBEST");
//				i += 1 ;
//				System.out.println(toDay.getDescription());
//				System.out.println(toDay.getLink());
//				
//				insertNews(toDay);
//				System.out.println(i+"번째 뉴스 기록중");
//				if(i == 5) {
//					System.out.println("멈출게 5라서");
//					break;
//				}
//			}
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
	

	//뉴스 헤드라인 단어로 잘라서 디비에 넣기 
	public static void news20() {
		//홍성 디비에서 데이터를 객체 어레이로 가져온다.
		List<PortalNews2> resultNaver = selectTodayDataNaver();
		List<PortalNews2> resultDaum = selectTodayDataDaum();

		//객체의 top20 항목을 쪼개서 맵으로 넣는다.
		Map<String, Integer> wordsMapNaver = splitWordsToMap(resultNaver);
		Map<String, Integer> wordsMapDaum = splitWordsToMap(resultDaum);

		//맵을 스트링 어레이로 바꾼다. 
		List <String> top20Naver = sortMapTop20ToStringArray(wordsMapNaver);
		List <String> top20Daum = sortMapTop20ToStringArray(wordsMapDaum);
		
		//배열을 하나의 스트링으로 바꿔준다. 
		String stringResultNaver = String.join(" ",top20Naver);
		String stringResultDaum = String.join(" ",top20Daum);
		
		insertTop20ToMyDb(stringResultNaver, "NAVER");
		insertTop20ToMyDb(stringResultDaum, "DAUM");		
	}
	
	
	public static void sportsNews20() {
		//내 디비에서 스포츠뉴스 데이터를 객체 어레이로 가져온다.
		List<PortalNews2> resultNaverSports = selectTodayDataNaverSports();
		List<PortalNews2> resultDaumSports = selectTodayDataDaumSports();
		System.out.println("네이버 스포츠 리스트"+resultNaverSports);
		System.out.println("다음 스포츠 리스트"+resultNaverSports);
		System.out.println("네이버 스포츠 1번 타이틀: "+resultNaverSports.get(0).getTitle());
		
		//객체의 top20 항목을 쪼개서 맵으로 넣는다.
		Map<String, Integer> wordsMapNaver = splitWordsToMap(resultNaverSports);
		Map<String, Integer> wordsMapDaum = splitWordsToMap(resultDaumSports);
		
		
		
		//맵을 스트링 어레이로 바꾼다. 
		List <String> top20NaverSports = sortMapTop20ToStringArray(wordsMapNaver);
		List <String> top20DaumSports = sortMapTop20ToStringArray(wordsMapDaum);
		
		//배열을 하나의 스트링으로 바꿔준다. 
		String stringResultNaver = String.join(" ",top20NaverSports);
		String stringResultDaum = String.join(" ",top20DaumSports);
		
		insertTop20ToMyDb(stringResultNaver, "NAVERSPORTS");
		insertTop20ToMyDb(stringResultDaum, "DAUMSPORTS");		
	}	
	
	
	
	public static List<PortalNews2> selectTodayDataNaverSports(){
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		List<PortalNews2> result = new ArrayList<PortalNews2>();
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			result = session.selectList("aaa.bbb.ccc.BaseMapper.selectNaverSports");
			//System.out.println("디비에서 긁어온24 뉴스 : "+result);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static List<PortalNews2> selectTodayDataDaumSports(){
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		List<PortalNews2> result = new ArrayList<PortalNews2>();
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			result = session.selectList("aaa.bbb.ccc.BaseMapper.selectDaumSports");
			//System.out.println("디비에서 긁어온24 뉴스 : "+result);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static List<PortalNews2> selectTodayDataNaver(){
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		List<PortalNews2> result = new ArrayList<PortalNews2>();
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			result = session.selectList("aaa.bbb.ccc.BaseMapper.selectTodayNewsNaver");
			//System.out.println("디비에서 긁어온24 뉴스 : "+result);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static List<PortalNews2> selectTodayDataDaum(){
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		List<PortalNews2> result = new ArrayList<PortalNews2>();
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			result = session.selectList("aaa.bbb.ccc.BaseMapper.selectTodayNewsDaum");
			//System.out.println("디비에서 긁어온24 뉴스 : "+result);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static Map<String, Integer> splitWordsToMap(List<PortalNews2> p1){
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		for(int i=0; i<p1.size();i++) {
			String newsTitle = p1.get(i).getTitle();
			//System.out.println(newsDesc);
			
			String piece[] = newsTitle.split(" ");
			
			for(int j=0;j<piece.length;j++) {
				String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
				piece[j] =piece[j].replaceAll(match, "");
				
				if(piece[j].equals("관련기사")||piece[j].equals("개수")||piece[j].equals("")) {	
					System.out.println("단어 뺄게");
				}else {			
					System.out.println(piece[j]+" 단어 저장중");
					if(result.containsKey(piece[j])) {
						result.put(piece[j], result.get(piece[j])+1);
						//System.out.println("중복단어 갯수 : "+piece[j]+1);
						
					}else {
						result.put(piece[j], 1);
					}									
				}
			}
		}
		System.out.println(result+"\n"+"생성했습니다.");
		return result;
	}

	
	public static List<String> sortMapTop20ToStringArray(Map<String,Integer> p1) {
		//리절트는 앰티에서 출발 
		List<String> result = new ArrayList<String>();
		
		//맵을 앤트리셋으로 바꾼다. 
		//정렬이 안된 상태 
		List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(p1.entrySet());
		
		//자료구조(리스트, 맵....)를 구현 Collections, 인자로 엔트리라는 객체를 받음... 
		//sort 라는 함수를 자주 쓴다. 아래는 쓰는 방식.
		//맵을 넣으면 정렬해서 리스트로 뽑아준다. 
		Collections.sort(list_entries, new Comparator<Entry<String, Integer>>() {
			// compare로 값을 비교
			public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
				// 오름 차순 정렬
				return obj2.getValue().compareTo(obj1.getValue());
			}
		});
		//list_entries는 정렬이 끝남 
		
			System.out.println("오름 차순 정렬");

			// 결과 출력
			
			int i = 0; //0에서 시작 
			
			for(Entry<String, Integer> entry : list_entries) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
				
				//한번에 1씩 증가 
				if(i<49) {
					result.add(entry.getKey() + ":" + entry.getValue()+",");				
				}else {
					//19번에서 일어나는 일 
					result.add(entry.getKey() + ":" + entry.getValue());
				}
				i += 1;
				//20이 되었다.
				
				//20번 돌면 그만 돌아라.
				if(i == 50) {					
					break;
				}
				// 리절트를 리턴하는데... 리턴값에 뭔가를 넣어준듯.
				
				// 정렬된 list_entries 를 리절트에 넣어줌				
				//result.addAll(list_entries);
				
			}
			
		return result;
	}
	
	
	public static void insertTop20ToMyDb(String p1, String p2){
		PortalNews2 top20 = new PortalNews2();
		top20.setTop20(p1);
		top20.setSource(p2);
		
		String resource = "aaa/bbb/ccc/mybatis_config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession session = sqlSessionFactory.openSession();
			session.insert("aaa.bbb.ccc.BaseMapper.insertTop20", top20);
			
			session.commit();
			session.close();
			//System.out.println("디비에서 긁어온24 뉴스 : "+result);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}

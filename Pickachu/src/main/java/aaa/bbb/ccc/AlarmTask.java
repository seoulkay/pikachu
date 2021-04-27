package aaa.bbb.ccc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import aaa.bbb.ccc.entity.JsoupReply;
import aaa.bbb.ccc.entity.Member;
import aaa.bbb.ccc.entity.PageManager;
import aaa.bbb.ccc.entity.Post;
import aaa.bbb.ccc.entity.countryData;
import aaa.bbb.ccc.entity.loginLog;
import aaa.bbb.ccc.entity.newsTitle;
import aaa.bbb.ccc.entity.top20Json;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;


@Component
public class AlarmTask {
	

	
			private static final Logger logger = LoggerFactory.getLogger(AlarmTask.class);
			
			// 배포했기때문에 중복없이 하기위해 스케쥴러잠급니다.
	
//			// 10분마다 국가코드가 null인 로그인 기록의 국가코드를 바꿔주는 함수
//			@Scheduled(cron = "0 */10 * * * *")
//			public void cronTest1() {
//				Calendar calendar = Calendar.getInstance();
//				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				List<Integer> nullCountryList = getCountryNullLoginAttempts();
//				System.out.println("스케쥴 실행 : " +dateFormat.format(calendar.getTime())+" 현재 국가 코드가 없는 로그인기록의 아이디들은  "+nullCountryList+"이며 변환을 실행합니다. ");
//				forNullCountryList(nullCountryList);
//			}
//			//아이엠케이 게시판긁어서 저장하기
//			@Scheduled(cron = "0 */30 * * * *")
//			public void getIamkay() {
//				ScheduledExecution();
//			}
//			
//			
//			// 포탈들 헤드라인 뉴스 긁어오기 
//			@Scheduled(cron = "0 */15 * * * *")
//			public void getTotalNews() {
//				getNaver_newHaedline();
//				getDaum_newHaedline();
//			}
			
			
			@Scheduled(cron = "0 */30 * * * *")
			public void todayScheduled() {
				todayTop20("NAVER");
				todayTop20("Daum");
			}
			
			//헤드라인 뉴스의 하루동안 노출빈도가 가장많은단어 20을 꺼내와 보여주고 저장한다.
			public static Map<String,Integer> todayTop20(String p1) {
				newsTitle sourceIs = new newsTitle();
				sourceIs.setSource(p1);
				System.out.println("시작합니다 "+ sourceIs.getSource() +" 스케쥴 ");
				//받아놓은 헤드라인 뉴스들중 지난 하루동안의 Top20의 키워드를 찾아 Db에 저장
				insertTop20(String.join(" ",top20(pieceWord(getToDayData(sourceIs)))),p1);
				//저장한 Top20의 뉴스중 지정한 소스의 최근 Top20을 꺼내 다시 맵에 키워드별 노출횟수로 넣어 보여줌
				System.out.println(top20MaptoString(getLastTop20(sourceIs)));
				Map<String,Integer> result = new HashMap<String,Integer>();
				result = top20MaptoString(getLastTop20(sourceIs));
				
				
				
				return result ;
			}
	
			// 키와 밸류로 나눠줘서 각각넣어줘야 한다.
			public static Map<String,Integer> top20MaptoString(newsTitle p1) {
				Map<String,Integer> result = new HashMap<String,Integer>();
					String piece[] = p1.getTop20().split(",");
					for(int i=0 ;i<piece.length ;i++) {
						String temp[] = piece[i].split(":");
						result.put(temp[0].trim(),Integer.parseInt(temp[1].trim()));
					}
			    System.out.println(result);
				return result ;
			}
			
			//top20을 맵으로 받아온걸 단어별로 짤라준다
			public static Map<String,Integer> pieceTop20(List<newsTitle> p1) {
				Map<String,Integer> result = new HashMap<String,Integer>();
					String piece[] = p1.get(0).getTop20().split(",");
					for(int i=0 ;i<piece.length ;i++) {
						String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
				        piece[i] = piece[i].replaceAll(match, "");
							if(result.containsKey(piece[i])) {
								result.put(piece[i],result.get(piece[i])+1);
							}else {
								result.put(piece[i],1);
								System.out.println("값이 없으므로 " + result + "저장하고 다음단어로 넘어갑니다.");
							}	
						}
				System.out.println(result+"를 생성했습니다.");
				return result ;
			}
			
			
			//top20을 스트링으로 DB에 저장하는함수
			public static void insertTop20(String  p1, String p2){
				newsTitle input = new newsTitle();
				input.setTop20(p1);
				input.setSource(p2);
				
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					
					System.out.println("insert하는 중"+p1);
					session.insert("aaa.bbb.ccc.BaseMapper.insertTop20", input);
					session.commit();
					session.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public static newsTitle getLastTop20(newsTitle p1) {
				
				System.out.println("시작합니다 Last top20 의 아이디 "+p1+"꺼내오기 ");
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				newsTitle result = new newsTitle();
				
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					result = session.selectOne("aaa.bbb.ccc.BaseMapper.getLastTop20", p1);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("꺼내온 last top20 = " + result);
				return result ;
			}
			//오름차순 만들어주기
			
			public static List<top20Json> orrm2(Map<String,Integer> p1){
				List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(p1.entrySet());
				List<top20Json> result = new ArrayList<top20Json>();
				
				
				System.out.println("정렬을 시작해볼까");
				Collections.sort(list_entries, new Comparator<Entry<String, Integer>>() {
					// compare로 값을 비교
					public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
						// 오름 차순 정렬
						return obj2.getValue().compareTo(obj1.getValue());
					}
				});
				
				int i = 0;
				for(Entry<String, Integer> entry : list_entries) {
					System.out.println(i+1 + "위 : " +entry.getKey() + " : " + entry.getValue());
					top20Json temp = new top20Json();
					temp.setText(entry.getKey());
					temp.setSize(entry.getValue());
					result.add(temp);
					
					
					
					i += 1;
					
					System.out.println(result);
					if(i == 20) {
						break;
					}
			
				}
				
				return result;
			}
			
			
			public static Map<String,Integer> orrm(Map<String,Integer> p1){
				List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(p1.entrySet());
				Map<String,Integer> result = new HashMap<String,Integer>();
				System.out.println("정렬을 시작해볼까");
				Collections.sort(list_entries, new Comparator<Entry<String, Integer>>() {
					// compare로 값을 비교
					public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
						// 오름 차순 정렬
						return obj2.getValue().compareTo(obj1.getValue());
					}
				});
				
				int i = 0;
				for(Entry<String, Integer> entry : list_entries) {
//					System.out.println(i+1 + "위 : " +entry.getKey() + " : " + entry.getValue());
					result.put(entry.getKey(),entry.getValue());
					i += 1;
					if(i == 20) {
						break;
					}
			
				}
				
				return result;
			}
			
			
			//top20봅는 함수
			public static List<String> top20(Map<String,Integer> p1) {
				List<Entry<String, Integer>> list_entries = new ArrayList<Entry<String, Integer>>(p1.entrySet());
				List<String> result = new ArrayList<String>();
				
				Collections.sort(list_entries, new Comparator<Entry<String, Integer>>() {
					// compare로 값을 비교
					public int compare(Entry<String, Integer> obj1, Entry<String, Integer> obj2) {
						// 오름 차순 정렬
						return obj2.getValue().compareTo(obj1.getValue());
					}
				});
//					System.out.println("오름 차순 정렬");
					// 결과 출력
					int i = 0;
					for(Entry<String, Integer> entry : list_entries) {
//						System.out.println(i+1 + "위 : " +entry.getKey() + " : " + entry.getValue());
						result.add(entry.getKey()+":"+entry.getValue());
						i += 1;
						if(i == 20) {
							break;
						}else {
							result.add(",");
						}
				
					}
				
				//
				
				
				return result;
			}
			
			//뉴스타이틀을 공백기준으로 짤라서 맵에 넣어준다
			public static Map<String,Integer> pieceWord(List<newsTitle> p1) {
				Map<String,Integer> result = new HashMap<String,Integer>();
				
				for(int i=0;i<p1.size();i++) {
					String newsTitle = p1.get(i).getTitle();
					String piece[] = newsTitle.split(" ");
					
					for(int j=0 ;j<piece.length ;j++) {
						String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
				        piece[j] =piece[j].replaceAll(match, "");
						if(piece[j].equals("관련기사")||piece[j].equals("개수")||piece[j].equals("")) {	
//							System.out.println("단어뺄게");
						}else {
//							System.out.println(piece[j]+"단어 저장중");
							if(result.containsKey(piece[j])) {
								result.put(piece[j],result.get(piece[j])+1);
							}else {
								result.put(piece[j],1);
							}	
						}
						
					}
				}
//				System.out.println(result+"생성했습니다.");
				return result ;
			}
			//소스별로 오늘의 데이터 긁어오는 함수
			public static List<newsTitle> getToDayData(newsTitle p1) {
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				
				List<newsTitle> result = new ArrayList<newsTitle>();
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					result = session.selectList("aaa.bbb.ccc.BaseMapper.getToDayNewsData", p1);

				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return result ;
			}

			// 네이버 뉴스 가져오는 함수 
			public static  void getNaver_newHaedline() {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("네이버 뉴스 긁어올게 " +dateFormat.format(calendar.getTime())+" 기다려  ");
				
				try {
				Document doc = Jsoup.connect("https://news.naver.com/main/home.nhn").get();
				System.out.println(doc.title());
				Elements newsHeadlines = doc.select("ul[class=hdline_article_list]").select("li");
				newsTitle toDay = new newsTitle();
				
				
				for(Element elem : newsHeadlines) {
					toDay.setTitle(elem.select("li").text());
					toDay.setLink("https://news.naver.com/"+elem.select("div[class=hdline_article_tit] a").attr("href"));
					toDay.setSource("NAVER");
					
					insertNews(toDay);
					
				}

				}catch(IOException e) {
					e.printStackTrace();
				}
	
			}
			
			//다음헤드라인 뉴스 가져오는 함수 5개만 
			public static void getDaum_newHaedline() {
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("다음 뉴스 긁어올게 " +dateFormat.format(calendar.getTime())+" 기다려  ");
				
				try {
				Document doc = Jsoup.connect("https://www.daum.net/").get();
				System.out.println(doc.title());
				Elements newsHeadlines = doc.select("ul[class=list_txt]").select("li");
				newsTitle toDay = new newsTitle();
				int i = 0;
				
				for(Element elem : newsHeadlines) {
					toDay.setTitle(elem.select("li").text());
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
			
			
			// 가져온 뉴스 타이틀과 url을 DB에 기록해줌
			public static void insertNews(newsTitle  p1){
			
			String resource = "aaa/bbb/ccc/mybatis_config.xml";
			InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream(resource);
				SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
				SqlSession session = sqlSessionFactory.openSession();
				
				System.out.println("insert하는 중"+p1);
				session.insert("aaa.bbb.ccc.BaseMapper.insertNews", p1);
				session.commit();
				session.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
			
			
			// 게시판을 DB에 저장하기

			public static void ScheduledExecution() {
				
				boolean check = trueNfalseAll();
				System.out.println(check);
				
				int i = -1;
				
				while (check == true){
					
					i++;
					System.out.println(urlist(i)+"에서 자료를 가져옵니다.");
					List<JsoupReply> sourceId = subtractIdFromSource(urlist(i));

					if (sourceId.isEmpty()) {
						System.out.println("id값이 없으므로 페이지를 그만 넘깁니다."+sourceId);
						check = false;
						   break;
					   }else {
						   System.out.println("id값이 있으므로 저장하겠습니다."+sourceId);
						   iamkayGesipanSave();   
					   }
					
				}
	
				System.out.println("이제 아디값 비교해서 시작할건데 내 디비에서 가장 큰 아이디값은 "+ bigId());
				System.out.println("그럼 소스값중 가장 큰 값은  "+ testSourceId());
				
				if(bigId()< testSourceId()) {
					System.out.println("저장된 아이디값이 끝값보다 작네요 저장실행합니다.");
					
							   iamkayGesipanSave();   
					System.out.println("나의 숙제는 없는애들만 페이지 옮겨가며 저장할 수 있는 기능을 만들어야 한다는 것이다.");
				}
				System.out.println("재미없게도 새로운게 없네요 종료합니다.");
			}
			
			public static String urlist(int p1) {
				int lastNum = 0+p1;
				String result = "http://www.iamk.shop:8080/reply?page="+lastNum;
			
				return result;
			}
			
			// 게시판 아이디값중 가장큰값을 찾음
			public static Integer bigId() {
				Integer result = 0;

				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					Integer ids = session.selectOne("aaa.bbb.ccc.BaseMapper.getMaxId");
					
					result = ids;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return result;
			}
			
			//소스중 가장 큰 숫자는 ?
			public static Integer testSourceId () {
				Integer result = 0;
				List<Integer> source = new ArrayList<Integer>();
				source.addAll(urlToJsonObject(urlist(0)));
				result = Collections.max(source);
				
				return result;
			}
			
//			public static List<Integer> testSourceId2(JSONObject p1){	
//				
//				List<Integer> result = new ArrayList<Integer>();
//				result.add(Integer.parseInt(p1.get("id").toString()));
//				return result;
//			}
			
			//json object가 필요해 
			public static List<Integer> urlToJsonObject(String url) {

				Document doc = null;
				List<Integer> result = new ArrayList<Integer>();
				try {
				doc = Jsoup.connect(url).ignoreContentType(true).get();
				String docText = doc.select("body").text();
				JSONArray docTextArray = jsonToJsonArray(docText);

				for(int i=0; i<docTextArray.size(); i++) {
					//JSON 객체에 있는 것들을 자바 객체에 넣는 작업
					List<Integer> littlResult = new ArrayList<Integer>();
					littlResult.add(Integer.parseInt(((JSONObject) docTextArray.get(i)).get("id").toString()));
					result.addAll(littlResult);
				}

				}catch(Exception e) {
					e.printStackTrace();
				}
				
				return result;
				
				
			}

			//전체 파싱할건지결정해주는 함수 
			public static boolean trueNfalseAll(){
				boolean result = true ; 
				Integer truefals = null ;
				
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					truefals = session.selectOne("aaa.bbb.ccc.BaseMapper.getCountId");
					System.out.println("가져온 아이는 "+truefals);
					if(truefals == 0) {
						result = true ;
						System.out.println("변환된 값은 1 "+result);
					}else {
						result = false;
						System.out.println("변환된 값은 2 "+result);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return result;
			}
			
			//페이지 넘길건지를 결정하는 함수
			public static boolean trueNfalsePageNumber(String url){
				boolean result = false ; 
//				Integer truefalsPage = null ;

				// getAllId() 아이디 싹가져와 
				List<JsoupReply> dbAllId = getAllId();
				// 소스에서 아이디 빼와 
				List<JsoupReply> sourceId = subtractIdFromSource(url);

				System.out.println("가져온 디비의 아이디들은"+dbAllId);
				System.out.println("가져온 소스의 아이디들은"+sourceId);
				System.out.println("아직 비교되게 안만들어서 그냥 false 줄게 ");

				return result;
			}
			
			// 여기서 소스를 빼서 아이디를 줄거야 
			public static List<JsoupReply> subtractIdFromSource(String url) {

				Document doc = null;
				List<JsoupReply> result = new ArrayList<JsoupReply>();
				try {
				doc = Jsoup.connect(url).ignoreContentType(true).get();
				String docText = doc.select("body").text();
				System.out.println("가져온 내용은"+docText+"입니다.");
				JSONArray docTextArray = jsonToJsonArray(docText);

				for(int i=0; i<docTextArray.size() + 1; i++) {
					//JSON 객체에 있는 것들을 자바 객체에 넣는 작업
					System.out.println("반복해서" +docTextArray.get(i)+"를 기록중입니다. ");
					result = sourceOnlyIdToList((JSONObject) docTextArray.get(i));
				}

				}catch(Exception e) {
					e.printStackTrace();
				}
				return result;
			}
			
			// 여기가 원래 되던거 
			public static void iamkayGesipanSave() {
				
				System.out.println("iamk의 게시판을 저장중입니다.");
				Document doc = null;
				
				
				//게시판의 끝번호를 가져온다. (DB)
				
				int bigId = bigId();
				System.out.println(" 내 디비에서 가장 큰 아이디값은 "+ bigId);
				int i = 0;
				boolean outerloop = false;
				while(true) {
					
					try {
						
						doc = Jsoup.connect(urlist(i)).ignoreContentType(true).get();
						String docText = doc.select("body").text();
						System.out.println("가져온 내용은"+docText+"입니다.");
						JSONArray docTextArray = jsonToJsonArray(docText);

							//가져온 소스를 반복해서 넣어준다.
						
						
						for(int j=0; j<docTextArray.size(); j++) {
							//JSON 객체에 있는 것들을 자바 객체에 넣는 작업
							JSONObject reply = (JSONObject) docTextArray.get(j);
							
							if(Integer.parseInt(reply.get("id").toString())<=bigId) {
								outerloop = true;
								break;
							}
							System.out.println("반복해서" +docTextArray.get(j)+"를 기록중입니다. ");
							insertDataServer(jsonToList((JSONObject) docTextArray.get(j)));
						}

						}catch(Exception e) {
							e.printStackTrace();
						}
					
					if(outerloop) {
						break;
					}
					
					i++;
				}
				
				
	
			}
			
				// DB에 기록해준다.
				public static void insertDataServer(List<JsoupReply>  p1){
				
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					
					System.out.println("sql에 insert하는 중입니다. "+p1);
					session.insert("aaa.bbb.ccc.BaseMapper.insertIamkay", p1);
					session.commit();
					session.close();
	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
				// Json을 받아와서 배열에 담아 리턴한다.
				public static JSONArray jsonToJsonArray(String p1){
					
					JSONArray result = new JSONArray();
					try {
						JSONParser parser = new JSONParser();
						Object obj = parser.parse(p1);
						JSONObject jsonObj = (JSONObject) obj;
						result = (JSONArray) jsonObj.get("replies");

					} catch (Exception e) {
						e.printStackTrace();	
						
					}

					return result;
				}
				
				// Json을 받아와서 Sql에 넣기위해 List에 담아준다.
				public static List<JsoupReply> jsonToList(JSONObject p1){
					
					List<JsoupReply> result = new ArrayList<JsoupReply>();
					JsoupReply setText = new JsoupReply();
					setText.setPassword(p1.get("password").toString());
					setText.setId(Integer.parseInt(p1.get("id").toString()));
					setText.setWriter(p1.get("writer").toString());
					setText.setPublished(p1.get("published").toString());
					setText.setContent(p1.get("content").toString());
	
					result.add(setText);

					return result;
				}
				
				public static List<JsoupReply> sourceOnlyIdToList(JSONObject p1){	
					
					List<JsoupReply> result = new ArrayList<JsoupReply>();
					JsoupReply setText = new JsoupReply();
					setText.setId(Integer.parseInt(p1.get("id").toString()));
					result.add(setText);
					System.out.println("디비에서 아이디만 빼서 넣었는데 이"+result);

					return result;
				}
				
			//DB에서 ID를 싹긁어와서 리턴해줘 인트로
				public static List<JsoupReply> getAllId(){
					List<JsoupReply> result = new ArrayList<JsoupReply>();
					String resource = "aaa/bbb/ccc/mybatis_config.xml";
					InputStream inputStream;
					try {
						inputStream = Resources.getResourceAsStream(resource);
						SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
						SqlSession session = sqlSessionFactory.openSession();
						result = session.selectList("aaa.bbb.ccc.BaseMapper.getAllId");
						System.out.println("가져온 id는 " + result);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return result;
				}
	


			// id리스트를 받아와 반복시켜서 ip를 찾아서나라코드를 바꿔주는 함수에 id를 넣어주는 일을 반복하는 함
			public static void forNullCountryList (List<Integer> p1) {
				
				Member sourceIpNId = new Member();
				
				for(int i=0; i < p1.size(); i++) {
					System.out.println(p1.get(i));
					//하나의 아이디를 넣어주면 id와 ip를 뽑아주는 함수를 실행한다.
					sourceIpNId = searchNullIpFromId(p1.get(i));
					System.out.println("ip를 꺼내왔다 "+sourceIpNId.getId()+sourceIpNId.getSourceIp());
					changeCountryCode(sourceIpNId.getId(),sourceIpNId.getSourceIp());
					System.out.println("변환 중인 id+ip"+sourceIpNId.getId()+sourceIpNId.getSourceIp());
				}
			}
	
			//실행시 국가코드가 null인 id를 찾아주는 함수
	
			public static List<Integer> getCountryNullLoginAttempts(){
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
	
				List<Integer> result= new ArrayList<Integer>();
				
				try {
					
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					result = session.selectList("aaa.bbb.ccc.BaseMapper.searchCountryCodeNullId");
					
				} catch (IOException e) {
					e.printStackTrace();	
					
				}
				
				return result;
			}
	
	
			// 실행시 아이디를 받아 id와 ip를 뽑는 함수 
	
			public static Member searchNullIpFromId(Integer id){
				System.out.println("받아온 아이디는 = "+id);
				
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				Member result = new Member();
				
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
					result = session.selectOne("aaa.bbb.ccc.BaseMapper.nullIdIp", id);
	
				} catch (IOException e) {
					e.printStackTrace();	
					
				}
				
				System.out.println("결과는 : "+result);
				return result;
			}
	
			//하나의 id와 하나의 IP를 뽑아 넣어주면 나라코드를 수정해주는 함수
			
			
	
			public static void changeCountryCode(String id, String sourceIp){
				
				System.out.println("지금은 changeCountryCode에 왔어요 받아온 id = " + id + "받아온 ip는 " + sourceIp);
				
				IPGeolocationAPI api = new IPGeolocationAPI("dc908921857b45f2b8083a266fbc964d");
				GeolocationParams geoParams = new GeolocationParams();
				geoParams.setIPAddress(sourceIp);
				geoParams.setFields("geo,time_zone,currency");
				geoParams.setIncludeSecurity(true);
				Geolocation geolocation = api.getGeolocation(geoParams);
				
				Member loginLog = new Member();
				loginLog.setId(id);
				
				if(geolocation.getStatus() == 200) {
				    loginLog.setCountryCode(geolocation.getCountryCode2());
				   
				} else {
				    geoParams.setIPAddress("3.124.80.217");
					Geolocation geolocationResult = api.getGeolocation(geoParams);
					loginLog.setCountryCode(geolocationResult.getCountryCode2());
				}
				
				
				//대쉬보드에서 먹히게 소문자로 변환해주자 
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
				
				loginLog.setCountryCode(output);
				
				//국가코드가 null 인 로그인 기록을 소문자로 변환된 국가코드로 수정 
				String resource = "aaa/bbb/ccc/mybatis_config.xml";
				InputStream inputStream;
				try {
					inputStream = Resources.getResourceAsStream(resource);
					SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
					SqlSession session = sqlSessionFactory.openSession();
	
					session.update("aaa.bbb.ccc.BaseMapper.updateNullIp", loginLog);
					session.commit();
					session.close();
	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
}

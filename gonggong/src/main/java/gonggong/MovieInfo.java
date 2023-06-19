package gonggong;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MovieInfo { 
	public static void main(String[] args) throws Exception  {
	
	String url ="http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.xml?key=f5eef3421c602c6cb7ea224104795888&movieCd=201240";
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	Document doc = dBuilder.parse(url);  //url : api 주소 정보에서 xml 형식의 데이터를 전송해주는  주소 url
	//doc : xml 형식 문서의 문서노드(루트노드)
	doc.getDocumentElement().normalize(); //루트 태그로 시작
	NodeList nList = doc.getElementsByTagName("movieInfo");
	//getElementsByTagName : 태그 이름으로 검색. DOM 관련 함수
	for(int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp); //하나의 태그
		Element eElement = (Element) nNode;
		System.out.println("영화코드 : " + getTagValue("movieCd",eElement));
		System.out.println("영화명(한글) : " + getTagValue("movieNm",eElement));
		System.out.println("영화명(영문) : " + getTagValue("movieNmEn",eElement));
		System.out.println("재생시간 : " + getTagValue("showTm",eElement));
		System.out.println("개봉일 : " + getTagValue("openDt",eElement));
		System.out.println("영화유형 : " + getTagValue("typeNm",eElement));
		System.out.println("제작국가명 : " + getTagValue("nationNm",eElement));
		System.out.println("장르 : " + getTagValue("genres","genre",eElement));
		System.out.println("감독명 : " + getTagValue("directors","director",eElement));
		System.out.println("출연배우 : " + getTagValue("actors","actor",eElement));

	}
}
public static String getTagValue(String tag, Element eElement) {
	String result ="";
	//getChildNodes() : 현재 태그의 하위 태그들
	NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	result = nlList.item(0).getTextContent(); //문자내용
	return result;
}
public static String getTagValue(String tag, String childTag, Element eElement) { //tag : genre childTag : genres
	String result ="";
	NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	for(int i = 0; i<eElement.getElementsByTagName(childTag).getLength(); i++) {
		result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
 	}
	return result;
}
}
package gonggong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Detail {
	
	public static void main(String[] args) throws IOException {
		String gongurl = "https://apis.data.go.kr/9720000/searchservice/detail?pageno=1";
		String search = URLEncoder.encode("자료명,홍길동", "UTF-8"); // URLEncoder.encode : 2바이트 문자열을 UTF-8 형식으로 인코딩
		StringBuilder urlBuilder = new StringBuilder(gongurl);
		urlBuilder.append(
				"?serviceKey=uzGmTh2miQpGCJ4ddl6FCQhvBgi90BGXhB26jFRFt5uPRbQ8BSG6KdDJjz37Lx6epByemmYO1ESh1xUJb9pSxw%3D%3D");
		urlBuilder.append("&displaylines=10&search=" + search);
		urlBuilder.append("&dbname="+URLEncoder.encode("일반도서","UTF-8"));
	//	urlBuilder.append(false)
	//	urlBuilder.append(false)
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 공공데이터에 접속
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code:" + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) { // 응답번호가 200보다 크고 300보다 작으면 정상처리
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		// sb : 국회 도서 자료의 응답 메세지를 xml 형태로 저장. 공공데이터에서 응답 형식이 xml 임
		// Jsoup : markup language 파싱기능을 가진 툴 html 분석. xml도 분석
		// doc : sb 문자열의 내용(xml 형식)을 DOM tree로 변경. 최상단의 문자노드 저장
		Document doc = Jsoup.parse(sb.toString());
		// recode 태그들을 elements로 저장
		Elements recodes = doc.select("recode");
		for (Element r : recodes) { // r : recode 태그 한 개
			// r.select("item") : recode 태그의 하위 item 태그들
			for (Element i : r.select("item")) {
				String name = i.select("name").html();
				String value = i.select("value").html();
				System.out.print(name + ":" + value +"\t");

			}
			System.out.println();

		}
	}
}
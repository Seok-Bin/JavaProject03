package kr.kakao.map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.net.URLEncoder;

public class KakaoApi {
    private static final String KAKAO_API_KEY = "805d7e6f989a7fb7c69c4abd685730b0";
    public static double[] getAddressCoordinate(String address) throws IOException{
        // url 만들기
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        String encodedAddress = URLEncoder.encode(address,"UTF-8"); //주소가 한글로 넘어가기때문애 인코딩시 깨지지 않게 하기위하여 인코딩
        String requestUrl = apiUrl + "?query=" + encodedAddress;

        //url 연결 (apache.http.client)를 사용
        CloseableHttpClient httpClient = HttpClients.createDefault(); //http 객체 생성
        HttpGet httpGet = new HttpGet(requestUrl); //요청 주소 생성
        httpGet.setHeader("Authorization", "KakaoAK " + KAKAO_API_KEY); // 요청 주소에 헤더 작성 (인증키)

        // 요청
        try(CloseableHttpResponse response = httpClient.execute(httpGet)){
            String responseBody = EntityUtils.toString(response.getEntity()); //요청 결과를 string로 받기 (json데이터로 받아집)
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
            JsonArray documents = jsonObject.getAsJsonArray("documents");

            // 필요한 데이터 뽑기
            if (documents.size() > 0) {
                JsonObject document = documents.get(0).getAsJsonObject();
                double latitude = document.get("y").getAsDouble();
                double longitube = document.get("x").getAsDouble();
                return new double[]{latitude, longitube};
            }else {
                return null;
            }
        }
    }
}

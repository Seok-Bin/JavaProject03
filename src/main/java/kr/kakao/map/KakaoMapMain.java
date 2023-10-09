package kr.kakao.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class KakaoMapMain {
    public static void main(String[] args) {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // 키보드를 통해 데이터를 읽어 드릴때 깨질위험이 있으므로 reader라는 문자 스트름을 이용한다
            System.out.print("주소를 입력하세요 : ");
            String address = reader.readLine();

            double[] coordinates = KakaoApi.getAddressCoordinate(address);

            if (coordinates != null){
                System.out.println("주소: "+ address);
                System.out.println("위도: "+ coordinates[0]);
                System.out.println("경도: "+ coordinates[1]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

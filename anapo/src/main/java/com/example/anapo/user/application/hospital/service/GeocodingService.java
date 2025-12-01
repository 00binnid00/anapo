package com.example.anapo.user.application.hospital.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class GeocodingService {

    @Value("${kakao.rest-api-key}")
    private String apiKey;

    public double[] geocode(String address) {

        // 주소로 검색
        double[] coords = searchAddress(address);
        if (coords != null) return coords;

        // 주소 검색 실패 → 키워드 검색
        coords = searchKeyword(address);
        if (coords != null) return coords;

        // 에러 던지지 않고, null로 반환
        return null;
    }

    private double[] searchAddress(String address) {
        try {
            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" +
                    URLEncoder.encode(address, StandardCharsets.UTF_8);

            ResponseEntity<Map> response =
                    rest.exchange(url, HttpMethod.GET, entity, Map.class);

            System.out.println("주소 검색 응답: " + response.getBody());

            List<Map<String, Object>> documents =
                    (List<Map<String, Object>>) response.getBody().get("documents");

            if (documents.isEmpty()) return null;

            Map<String, Object> first = documents.get(0);
            double lng = Double.parseDouble(first.get("x").toString());
            double lat = Double.parseDouble(first.get("y").toString());

            return new double[]{lat, lng};

        } catch (Exception e) {
            return null;
        }
    }

    private double[] searchKeyword(String keyword) {
        try {
            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + apiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" +
                    URLEncoder.encode(keyword, StandardCharsets.UTF_8);

            ResponseEntity<Map> response =
                    rest.exchange(url, HttpMethod.GET, entity, Map.class);

            System.out.println("키워드 검색 응답: " + response.getBody());

            List<Map<String, Object>> documents =
                    (List<Map<String, Object>>) response.getBody().get("documents");

            if (documents.isEmpty()) return null;

            Map<String, Object> first = documents.get(0);
            double lng = Double.parseDouble(first.get("x").toString());
            double lat = Double.parseDouble(first.get("y").toString());

            return new double[]{lat, lng};

        } catch (Exception e) {
            return null;
        }
    }
}

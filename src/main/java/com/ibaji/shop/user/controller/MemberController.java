package com.ibaji.shop.user.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@RequestMapping(value ="/member/*")
public class MemberController {

	@GetMapping(value = "/login")
	public String login(HttpServletRequest req) {
		return "member/login";
	}
	
	@GetMapping(value = "/oauth")
	public void oauth(@RequestParam("code") String code) throws MalformedURLException {
		String path = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=d6eb0beb4391a579ac512e5662767c97");
            sb.append("&redirect_uri=http://localhost:8080/member/oauth");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            String access_Token = element.getAsJsonObject().get("access_token").getAsString();
            //String refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
          
			bw.close();
			br.close();
			
			HashMap<String, Object> map = getUserInfo(access_Token);
			//이메일주소 access토큰 db에 저장개인정보생성
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public HashMap<String, Object> getUserInfo (String access_Token) { 
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String reqURL = "https://kapi.kakao.com/v2/user/me";
	    try {
	        URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        
	        JsonParser parser = new JsonParser();
	        JsonElement element = parser.parse(result);
	        
	        //JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
	        
	        //String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String email = kakao_account.getAsJsonObject().get("email").getAsString();
	        
	        //userInfo.put("nickname", nickname);
	        userInfo.put("email", email);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return userInfo;
	}
	

	@GetMapping(value = "/join")
	public String join() {
		System.out.println("join");
		
		return "member/join";
	}
	
	@PostMapping(value = "/join")
	public @ResponseBody Map<String, Object> joinPost() {
		System.out.println("test");
		
		Map<String, Object> data = new HashMap<String, Object>();
	    data.put( "name", "Mars" );
	    data.put( "age", 32 );
	    data.put( "city", "NY" );
		
		return data;
	}
	
}

package cn.truncle.yumubot.service;

import cn.truncle.yumubot.entity.BinUser;
import cn.truncle.yumubot.util.BindingUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class OSUV2Service {
    @Value("${yumubot.osu-v2.id}")
    private int oauthId;
    @Value("${yumubot.osu-v2.callBackUrl}")
    private String redirectUrl;
    @Value("${yumubot.osu-v2.token}")
    private String oauthToken;
    @Autowired
    BindingUtil bindingUtil;
    @Autowired
    RestTemplate template;
    public String getOauthUrl(String state){
        return "https://osu.ppy.sh/oauth/authorize?client_id="+oauthId+"&response_type=code&state="+state+"&scope=friends.read%20identify%20public&redirect_uri="+redirectUrl;
    }
    public JSONObject getToken(BinUser binUser){
        String url = "https://osu.ppy.sh/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap body = new LinkedMultiValueMap();
        body.add("client_id", oauthId);
        body.add("client_secret",oauthToken);
        body.add("code",binUser.getRefreshToken());
        body.add("grant_type","authorization_code");
        body.add("redirect_uri",redirectUrl);

        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        System.out.println(httpEntity);
        JSONObject s = template.postForObject(url, httpEntity, JSONObject.class);
        binUser.setAccessToken(s.getString("access_token"));
        binUser.setRefreshToken(s.getString("refresh_token"));
        binUser.nextTime(s.getLong("expires_in"));
        bindingUtil.Write(binUser);
        return s;
    }
    public JSONObject refreshToken(BinUser binUser){
        String url = "https://osu.ppy.sh/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap body = new LinkedMultiValueMap();
        body.add("client_id", oauthId);
        body.add("client_secret",oauthToken);
        body.add("refresh_token",binUser.getRefreshToken());
        body.add("grant_type","refresh_token");
        body.add("redirect_uri",redirectUrl);

        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        System.out.println(httpEntity.toString());
        JSONObject s = template.postForObject(url, httpEntity, JSONObject.class);
        binUser.setAccessToken(s.getString("access_token"));
        binUser.setRefreshToken(s.getString("refresh_token"));
        binUser.nextTime(s.getLong("expires_in"));
        bindingUtil.Write(binUser);
        return s;
    }
    public JSONObject getUserInfo(BinUser user){
        String url = "https://osu.ppy.sh/api/v2/me/osu";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + user.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<JSONObject> c =  template.exchange(url, HttpMethod.GET, httpEntity, JSONObject.class);
        user.setOsuID(c.getBody().getIntValue("id"));
        user.setOsuName(c.getBody().getString("username"));
        return c.getBody();
    }
}

package cn.truncle.yumubot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

@EnableScheduling
@Service
public class OSUService {
    private static final Logger logger = LoggerFactory.getLogger(OSUService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${yumubot.osu-peppy.baseurl}")
    private String baseurl;

    private String token = null;

    OSUService(){
    }

    public String getToken(){
        return this.token;
    }

    @Scheduled(cron = "0 0 0,12 * * ?")
    public void getClientCredentials(){
        String url = "https://osu.ppy.sh/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map body = Map.of(
                "grant_type", "client_credentials",
                "client_id", 6267,
                "client_secret", "OtA6HV0OXdTht6vGo5rgkk1eWo03ZcctcYA6MaU9",
                "scope", "public"
        );
        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        try{
        Map token = restTemplate.postForObject(url, httpEntity, Map.class);
        assert token != null;
        this.token = (String) token.get("access_token");
        }
        catch(Exception e){
                getClientCredentials();
        }
    }

    public JSONObject getUser(String userId){
        logger.debug("getUserInfo: osuId = "+userId);
        String url = baseurl + "/users/{user}/{mode}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        JSONObject response = null;
        response = restTemplate.exchange(url, HttpMethod.GET,
                httpEntity, JSONObject.class, userId, "osu").getBody();
        return response;
    }

    public JSONObject getBeatMap(String beatmapId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(baseurl)
                .pathSegment("beatmaps", beatmapId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        JSONObject response = null;
        response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                httpEntity, JSONObject.class).getBody();
        return response;
    }

    public JSONObject getUserBeatmapScore(String beatmapId, String userId){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(baseurl)
                .pathSegment("beatmaps", beatmapId, "scores", "users", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        JSONObject response = null;
        response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                httpEntity, JSONObject.class).getBody();
        return response;
    }
}

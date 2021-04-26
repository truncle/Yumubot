package cn.truncle.yumubot.service;

import cn.truncle.yumubot.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.ConnectException;
import java.util.Map;

@Service
public class OSUService {
    private static final Logger logger = LoggerFactory.getLogger(OSUService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${yumubot.osu-peppy.baseurl}")
    private String baseurl;

    private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI2MjY3IiwianRpIjoiYjhjNjQyMzUyMDZhMmIxZGFiOTgyYjA4NjUxNmYzNjQ5NmRmMGNlOWIxZjhjZTJjNTdkNTlhMjI4MDZhYzMwNjljNTViOWU2ZDViYzhlZmQiLCJpYXQiOjE2MTgzODY4MDYsIm5iZiI6MTYxODM4NjgwNiwiZXhwIjoxNjE4NDczMjA2LCJzdWIiOiIiLCJzY29wZXMiOlsicHVibGljIl19.gQte7WaMN_eW2YYmN5feBiqiwqrlAVdqRG4M82BMPAC9sP2BxyNjnYjolORQ2TPUhVIuZwLUpB9f6hV8heYfTpH51__DsxC_HPfMjDoWtFWY3U0DzVpQqpEA6ivdbkClxLDX1MeTJZ-csDSt-TNe7TiLY52XaIhlejRoLvO1FaVkOy81VPttk2uUthP6yajWpd_MYdIQ8uomgWh6PDf1RBVVd30o7J7X35hDgBOR4idr7QvyUgeJJDzwcUZcds6SHAthcDkVEgIcU85n5FOmjOWb972_uB6FdlVjj7iCO_OY6v43CTV7-SBLKovfqlLgvbigLcmL4fKBaYCFX_-tZLCL2UL1KRDxusyWXcV3NMYSzS6_HWmZ5AjBTDylcFU-5PExbCtezWW6agm6KjKrJpHJz3o0CKOjCcWpuFKbrkccd89qBnIbEJgXoVZDbVLLXNl-En9Ai696sVIe6bLOUoyfmfUn2y0r0LImpntiiTDiTxya1Uun31M5LHgtNWIw1j97aPI0HVqanV7RHEGcC_zuru6iHHRp06chjILJ-NiNXS0j9WWBXyvQCyWDpVIJ1h-CfWaL2dgtpHzOh6-pI6Nbn_z4Bu94qRonMAL7rA3RVREXfPb6j-QWRtXkEG1zLqVNqJS3FaVo347mcwPdEWXtqTVXeqIO3U6Av_ExjN8";

    OSUService(){
    }

    public String getToken(){
        return this.token;
    }

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
        Map token = restTemplate.postForObject(url, httpEntity, Map.class);
        assert token != null;
        this.token = (String) token.get("access_token");
    }

    public Map getUser(String userId){
        logger.debug("getUserInfo: osuId = "+userId);
        String url = baseurl + "/users/{user}/{mode}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        Map params = Map.of(
                "user", userId,
                "mode", "osu"
        );
        String response = null;
        response = restTemplate.exchange(url, HttpMethod.GET,
                httpEntity, String.class, userId, "osu").getBody();
        return JsonUtil.json2Map(response);
    }

    public Map getBeatMap(String beatmapId) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(baseurl)
                .pathSegment("beatmaps", beatmapId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        String response = null;
        response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                httpEntity, String.class).getBody();
        return JsonUtil.json2Map(response);
    }

    public Map getUserBeatmapScore(String beatmapId, String userId){
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(baseurl)
                .pathSegment("beatmaps", beatmapId, "scores", "users", userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        String response = null;
        response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
                httpEntity, String.class).getBody();
        return JsonUtil.json2Map(response);
    }
}

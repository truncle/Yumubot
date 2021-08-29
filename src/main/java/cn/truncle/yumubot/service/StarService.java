package cn.truncle.yumubot.service;

import cn.truncle.yumubot.model.BinUser;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StarService {
    final Logger log = LoggerFactory.getLogger(StarService.class);
    public String path;
    @Value("${dir.rundir}star/")
    public void setPath(String path) throws IOException {
        Path pt = Path.of(path);
        if(!Files.isDirectory(pt)){
            Files.createDirectories(pt);
        }
        this.path = path;
    }

    @Autowired
    OSUService osuService;


    private void writeFile(Score score){
        Path pt = Path.of(path+score.getQq_id());
        try {
            if(!Files.isRegularFile(pt)){
                Files.createFile(pt);
            }
            Files.writeString(pt,score.toString());
        } catch (IOException e) {
            log.error("积分写入异常",e);
        }
    }

    private Score readFile(long qq_id){
        Path pt = Path.of(path+qq_id);
        Score score = null;
        if(Files.isRegularFile(pt)){
            try {
                score = JSON.parseObject(Files.readString(pt), Score.class);
            } catch (IOException e) {
                log.error("积分读取异常",e);
            }
        }
        return score;
    }

    public Score getScore(long id){
        return readFile(id);
    }

    public Score addStart(BinUser user, float star){
        Score sc = null;
        sc = readFile(user.getQq());
        if(sc == null){
            sc = new Score()
                    .setQq_id(user.getQq())
                    .setBest_id(0)
                    .setStar(0);
        }

        sc.addStar(star);
        writeFile(sc);
        return sc;
    }

    public Score addStart(Score sc, float star){
        if(sc == null){
            return null;
        }
        sc.addStar(star);
        writeFile(sc);
        return sc;
    }

    public boolean delStart(BinUser user, float star){
        Score sc = readFile(user.getQq());
        if (sc == null) return false;
        if(sc.getStar() >= star){
            sc.delStar(star);
            writeFile(sc);
            return true;
        }
        return false;
    }

    public boolean delStart(Score sc, float star){
        if (sc == null) return false;
        if(sc.getStar() >= star){
            sc.delStar(star);
            writeFile(sc);
            return true;
        }
        return false;
    }

    public Score getScore(BinUser user){
        Score sc = readFile(user.getQq());
        if (sc == null) {
            sc = new Score().setQq_id(user.getQq())
                    .setBest_id(0)
                    .setStar(0)
                    .setRefouse_time(0);
            writeFile(sc);
        }
        return sc;
    }

    public boolean isRefouse(Score s){
        return System.currentTimeMillis() >= s.getRefouse_time()+(1000*60*60*24);
    }

    public Score refouseStar(Score s, float star){
        s.setRefouse_time(System.currentTimeMillis());
        s.addStar(star);
        writeFile(s);
        return s;
    }

    public static class Score {
        long qq_id;
        long best_id;
        float star;
        long refouse_time;

        public long getQq_id() {
            return qq_id;
        }

        public Score setQq_id(long qq_id) {
            this.qq_id = qq_id;
            return this;
        }

        public long getBest_id() {
            return best_id;
        }

        public Score setBest_id(long best_id) {
            this.best_id = best_id;
            return this;
        }

        public float getStar() {
            return star;
        }

        public Score setStar(float star) {
            this.star = star;
            return this;
        }

        public Score addStar(float add){
            this.star += add;
            return this;
        }

        public Score delStar(float del){
            if(del <= this.star) this.star -= del;
            return this;
        }

        public long getRefouse_time() {
            return refouse_time;
        }

        public Score setRefouse_time(long refouse_time) {
            this.refouse_time = refouse_time;
            return this;
        }

        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }
}

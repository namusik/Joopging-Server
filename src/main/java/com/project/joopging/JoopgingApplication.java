package com.project.joopging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling //스케줄링
@ServletComponentScan
public class JoopgingApplication {

    @PostConstruct
    public  void started() {
        //timezone 셋팅 (서비스 지역이 한국 한정이라 UTC 를 사용하지 않고 KST 사용)
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
    public static void main(String[] args) {
        SpringApplication.run(JoopgingApplication.class, args);
    }

}

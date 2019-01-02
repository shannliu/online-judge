package cn.edu.nju.software.judger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import cn.edu.nju.software.judger.core.JudgeClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class JudgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgerApplication.class, args);
    }

    @Value("${judge.server}")
    private String host;
    @Value("${judge.port}")
    private int port;
    @Bean
    public JudgeClient judgeClient(){
        return new JudgeClient(host,port);
    }

}


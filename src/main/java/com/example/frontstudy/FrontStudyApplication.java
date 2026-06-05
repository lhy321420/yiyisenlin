package com.example.frontstudy;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 关键：扫描com.example.demo全包 + Mapper扫描
@SpringBootApplication(scanBasePackages = "com.example.demo")
@MapperScan("com.example.demo.mapper")
public class FrontStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontStudyApplication.class,args);
    }
}
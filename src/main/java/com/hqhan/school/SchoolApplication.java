package com.hqhan.school;

import com.hqhan.school.zk.ZKCustor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SchoolApplication implements DisposableBean, CommandLineRunner {

    final static Logger logger = LoggerFactory.getLogger(ZKCustor.class);

    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }

    @Autowired
    private ZKCustor zkCustor;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("started application");
    }


    @Override
    public void destroy() throws Exception {


    }
}

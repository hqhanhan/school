package com.hqhan.school;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseCaseTest {

    final static Logger LOGGER = LoggerFactory.getLogger(BaseCaseTest.class);

    @Before
    public void before() {
        LOGGER.info("=============== Test begin =================== : {}", new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
    }

    @After
    public void after() {
        LOGGER.info("=============== Test end ===================== : {}", new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
    }

}

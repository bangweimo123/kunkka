package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.demo.client.KunkkaDemoSpringBootApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 18:57
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KunkkaDemoSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
}

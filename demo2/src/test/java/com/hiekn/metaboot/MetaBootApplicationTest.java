package com.hiekn.metaboot;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MetaBootApplicationTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Ignore
    @Test
	public void test() {
	}

}

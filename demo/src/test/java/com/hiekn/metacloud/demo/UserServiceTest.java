package com.hiekn.metacloud.demo;

import com.hiekn.metacloud.demo.bean.UserBean;
import com.hiekn.metacloud.demo.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

public class UserServiceTest extends DemoApplicationTests{

    private static final Log logger = LogFactory.getLog(UserServiceTest.class);

    @Autowired
	private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

	@Test
	public void test(){
		Assert.assertSame(userService,userService);
		Assert.assertEquals(1, 1);
		Assert.assertTrue (2 < 3);
		Assert.assertFalse(2 > 3);
		Assert.assertNotNull(userService);
		Assert.assertNull(userService);
    }

    @Test
    @Sql(statements = "insert into user (id,email) values (1,'dh@gamil.com')")
    public void testSql(){
        List<UserBean> list =  userService.list();
        boolean flag =false;
        for (UserBean o : list) {
            if("dh@gamil.com".equals(o.getEmail())){
                flag = true;
                break;
            }
        }
        Assert.assertTrue (flag);
    }

    @Test
    public void mongoTemplateTest(){
        List<UserBean> list =  userService.list();
        mongoTemplate.insert(list,"table");
    }

    @Test
    public void redisTemplateTest(){
        stringRedisTemplate.opsForValue().set("key","value");
    }


}

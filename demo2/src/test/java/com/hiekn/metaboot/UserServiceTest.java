package com.hiekn.metaboot;

import com.hiekn.boot.autoconfigure.base.model.PageModel;
import com.hiekn.boot.autoconfigure.base.model.result.RestData;
import com.hiekn.metaboot.bean.UserBean;
import com.hiekn.metaboot.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.jdbc.Sql;

public class UserServiceTest extends MetaBootApplicationTest {

    private static final Log logger = LogFactory.getLog(UserServiceTest.class);

    @Autowired
	private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TransportClient client;

	@Test
	public void testAssert(){
        assertSame(userService,userService);
        assertEquals(1, 1);
        assertTrue (2 < 3);
        assertFalse(2 > 3);
        assertNotNull(userService);
        assertNull(userService);
    }

    @Test
    @Sql(statements = "insert into user (id,email) values (1,'dh@gamil.com')")
    public void testSql(){
        UserBean userBean = new UserBean();
        userBean.setPageNo(1);
        userBean.setPageSize(10);
        RestData<UserBean> rd =  userService.listByPage(userBean);
        boolean flag =false;
        for (UserBean o : rd.getRsData()) {
            if("dh@gamil.com".equals(o.getEmail())){
                flag = true;
                break;
            }
        }
        assertTrue (flag);
    }

    @Test
    public void mongoTemplateTest(){
        UserBean userBean = new UserBean();
        userBean.setPageNo(1);
        userBean.setPageSize(10);
        RestData<UserBean> rd =  userService.listByPage(userBean);
        mongoTemplate.insert(rd.getRsData(),"table");
    }

    @Test
    public void redisTemplateTest(){
        stringRedisTemplate.opsForValue().set("key","value");
    }

    @Test
    public void elasticsearchTest(){
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("x").setTypes("y");
        SearchResponse response = searchRequestBuilder
                .setQuery(QueryBuilders.termQuery("query", 1))
                .get();
        response.getHits().forEach(hit -> logger.info(hit.getSourceAsMap()));

    }

}

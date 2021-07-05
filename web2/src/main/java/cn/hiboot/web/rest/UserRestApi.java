package cn.hiboot.web.rest;

import cn.hiboot.mcn.core.model.result.RestResp;
import com.hiekn.metacloud.feign.UserApi;
import com.hiekn.metacloud.feign.bean.UserBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("user")
@RestController
public class UserRestApi implements UserApi {

    @Value("${server.port}")
    private Integer port;

    @Value("${spring.datasource.password}")
    private String foo;

    @Override
    public RestResp<List<UserBean>> list() {
        List<UserBean> rs = new ArrayList<>();
        UserBean userBean = new UserBean();
        userBean.setName(foo);
        userBean.setPort(port);
        rs.add(userBean);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new RestResp<>(rs);
    }

    @Override
    public RestResp<UserBean> postJson(UserBean userBean) {
        return new RestResp<>(userBean);
    }

}

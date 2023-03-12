package com.taoli.niceplace;

import com.taoli.apiclientsdk.client.ApiClient;
import com.taoli.apiclientsdk.model.ClientParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class MyApplicationTest {

    @Resource
    private ApiClient apiClient;

    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
        System.out.println(newPassword);
    }

    /**
     * 测试引入SDK的可用性
     */
    @Test
    void testApi() {
        ClientParam clientParam = new ClientParam();
        clientParam.setType("1");
        String text = apiClient.getText(clientParam);
        System.out.println(text);
        //编辑距离算法
        ClientParam clientParam2 = new ClientParam();
        clientParam2.setStringParam1("taoli");
        clientParam2.setStringParam2("aolit");
        String distance = apiClient.minDistance(clientParam2);
        //注意如果测试的时候出现有时有有时无,请把所有断点去掉
        System.out.println(distance);

    }


}


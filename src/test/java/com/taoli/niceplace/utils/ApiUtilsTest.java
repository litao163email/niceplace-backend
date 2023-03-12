package com.taoli.niceplace.utils;

import com.taoli.apiclientsdk.client.ApiClient;
import com.taoli.apiclientsdk.model.ClientParam;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 测试API接口
 */
@SpringBootTest
public class ApiUtilsTest extends ApiUtils {

    @Resource
    private ApiClient apiClient;

    @Test
    public void testGetImageUrl() {
            String imageUrl = ApiUtils.getImageUrl();
            System.out.println(imageUrl);
    }

    @Test
    public void testGetText() {
        String text = ApiUtils.getText();
        System.out.println(text);
    }

    @Test
    public void testApiGetText() {
        ClientParam clientParam = new ClientParam();
        clientParam.setType("1");
        String text = apiClient.getText(clientParam);
        System.out.println(text);
    }

}
package com.taoli.niceplace.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 测试API接口
 */
@SpringBootTest
public class ApiUtilsTest extends ApiUtils {


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


}
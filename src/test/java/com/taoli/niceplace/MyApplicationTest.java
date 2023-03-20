package com.taoli.niceplace;

import com.taoli.apiclientsdk.client.ApiClient;
import com.taoli.apiclientsdk.model.ClientParam;
import com.taoli.niceplace.common.JoinThread;
import com.taoli.niceplace.common.TakeTurnsPrint;
import com.taoli.niceplace.common.TurnsPrint;
import com.taoli.niceplace.entity.VideoInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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

    /**
     * 测试theadLocal
     */
    @Test
    void testTheadLocal() {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setVideoUrl("123");
        VideoInfo.add(videoInfo);

        VideoInfo videoInfo1 = VideoInfo.get();
        String videoUrl = videoInfo1.getVideoUrl();
        System.out.println(videoUrl);
        //123

        VideoInfo.remove();
        VideoInfo videoInfo2 = Optional.ofNullable(VideoInfo.get()).orElse(new VideoInfo());
        String videoUr2 = Optional.ofNullable(videoInfo2.getVideoUrl()).orElse("456");
        System.out.println(videoUr2);
        //456
    }

    /**
     * 测试theadLocal
     */
    @Test
    void testHashMapAndTreeMap() {
        Map<String,String> hashMap = new HashMap<>(32);
        hashMap.put("nihao0","taoli0");
        hashMap.put("nihao1","taoli1");
        hashMap.put("nihao2","taoli2");
        hashMap.forEach((k,value)->{
            System.out.println(k+value);
        });
        //nihao0taoli0
        //nihao2taoli2
        //nihao1taoli1
        TreeMap<String,String> treeMap = new TreeMap<>(hashMap);
        treeMap.forEach((k,value)->{
            System.out.println(k+value);
        });
        //nihao0taoli0
        //nihao1taoli1
        //nihao2taoli2
    }

    /**
     * 测试thead-Wait/notify()
     */
    @Test
    void testWaitThead() {
        Object o=new Object();
        TakeTurnsPrint takeTurns = new TakeTurnsPrint(o);
        TurnsPrint turnsPrint = new TurnsPrint(o);
        new Thread(new Runnable() {
            @Override
            public void run() {
                takeTurns.print1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                turnsPrint.print2();
            }
        }).start();
        //测试通过
    }

    /**
     * 测试thead-join()
     */
    @Test
    void testJoinThead() {
        JoinThread t1 = new JoinThread("a");
        JoinThread t2 = new JoinThread("b");
        JoinThread t3 = new JoinThread("c");

        try {
//            t1.start();
//            t2.start();
//            t3.start();
            //打印b1-30/a1-30/c1-30

            //使用了join等同于等该线程使用了synchronized
            t1.start();
            t1.join();

            t2.start();
            t2.join();

            t3.start();
            t3.join();
            //打印a1-30/b1-30/c1-30

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


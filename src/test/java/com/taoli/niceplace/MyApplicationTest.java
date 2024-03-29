package com.taoli.niceplace;

import com.taoli.niceplace.common.*;
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


    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
        System.out.println(newPassword);
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

    /**
     * 测试thead-多个线程使用同一Synchronized代码块的锁对象
     */
    @Test
    void testSynchronizedThread() {
        Ticket ticket = new Ticket();

        SynchronizedThread windows1 = new SynchronizedThread("窗口1", ticket);
        SynchronizedThread windows2 = new SynchronizedThread("窗口2", ticket);
        SynchronizedThread windows3 = new SynchronizedThread("窗口3", ticket);

        //123都是进入预备状态
        windows1.start();
        windows2.start();
        windows3.start();
        //打印3、2、3卖票
    }
}


package com.taoli.niceplace.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import com.taoli.niceplace.common.ErrorCode;
import com.taoli.niceplace.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * 第三方接口
 *
 * @author chatgpt, taoli
 * chatgpt is very nice!
 * (之后可以开发属于自己的API管理平台)
 */
@Slf4j
public class ApiUtils {

    /**
     * 获取图片
     * @return String
     * 示例:"https:\/\/tva1.sinaimg.cn\/large\/9bd9b167ly1fzjxz375iwj20b40b4t9c.jpg"
     */
    public static String getImageUrl() {
        //第三方接口自动获取图片url:https://api.btstu.cn/
        String niceImageGiver = "https://api.btstu.cn/sjtx/api.php?lx=c1&format=json";
        Map<String, String> map = getFood(niceImageGiver, "imgurl");
        return map.get("imgurl");
    }

    /**
     * 获取随机的毒鸡汤
     *
     * @return String
     * 示例:"你骗我的样子万般投入，我又怎么舍得让你输。"
     * <p>
     * (这里之后可以开发属于自己的评论API,目前只作为视频的文案)
     */
    public static String getText() {
        //第三方获取毒鸡汤接口:https://api.btstu.cn/
        String niceImageGiver = "https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json";
        Map<String, String> map = getFood(niceImageGiver, "text");
        return map.get("text");
    }

    /**
     * 公共方法
     *
     * @param niceImageGiver
     * @param targetNameList 想要获取的目标字段名字的集合
     * @return
     */
    private static Map<String, String> getFood(String niceImageGiver, String... targetNameList) {
        HashMap<String, String> resultMap = new HashMap<>(targetNameList.length);
        try {
            if (StringUtils.isBlank(niceImageGiver)) {
                throw new BusinessException(ErrorCode.API_ERROR);
            }
            String url = niceImageGiver;
            URL obj = new URL(url);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jsonResponse = new JSONObject(response.toString());
            for (String s : targetNameList) {
                String childResult = jsonResponse.getString(s);
                resultMap.put(s, childResult);
            }

            log.info("result生成");
            return resultMap;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return resultMap;
    }


}

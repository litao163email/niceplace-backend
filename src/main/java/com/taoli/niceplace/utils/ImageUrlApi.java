package com.taoli.niceplace.utils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.taoli.niceplace.common.ErrorCode;
import com.taoli.niceplace.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;


/**
 * 第三方接口:获取图片
 * @author chatgpt,taoli
 * chatgpt is very nice!
 */
@Slf4j
public class ImageUrlApi {

    /**
     * 第三方接口自动获取图片url
     */
    private static String niceImageGiver="https://api.btstu.cn/sjtx/api.php?lx=c1&format=json";


    /**
     * 获取图片
     * @return
     */
    public static String getImageUrl(){

        String imageUrl = "emptyImage";
        try {
            if(StringUtils.isBlank(niceImageGiver)){
                throw new BusinessException(ErrorCode.API_ERROR);
            }
            String url = niceImageGiver;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            imageUrl = jsonResponse.getString("imgurl");
            if(StringUtils.isBlank(imageUrl)){
                throw new BusinessException(ErrorCode.API_ERROR);
            }
            log.info("imageUrl: " + imageUrl);
            return imageUrl;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return imageUrl;
    }

}


package com.demo.rpc.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class HttpRequest {

    private static Logger logger = Logger.getLogger(HttpRequest.class);


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    //    public static JSONObject sendPostToSDP(StringEntity reqEntity, String url)
    //            throws Exception {
    //        JSONObject rtnJson = null;
    //        try {
    //            CloseableHttpClient httpclient = HttpClients.createDefault();
    //            HttpPost httpPost = new HttpPost(url);
    //
    //            //璁剧疆瓒呮椂鏃堕棿--60s
    //            RequestConfig requestConfig = RequestConfig.custom()
    //                    .setSocketTimeout(MDA.TIME_OUT)
    //                    .setConnectTimeout(MDA.TIME_OUT)
    //                    .build();
    //            httpPost.setConfig(requestConfig);
    //
    //            reqEntity.setContentType("application/json");
    //            httpPost.setEntity(reqEntity);
    //            CloseableHttpResponse response = httpclient.execute(httpPost);
    //            HttpEntity entity = response.getEntity();
    //            rtnJson = JSONObject.fromObject(EntityUtils.toString(entity));
    //            httpclient.close();
    //        }
    //        catch (SocketTimeoutException e) {
    //            logger.info(reqEntity);
    //            logger.info(e.fillInStackTrace(), e);
    //            logger.info("璇锋眰SDP鎺ュ彛瓒呮椂锛岃姹傛椂闂磘ime=" + MDA.TIME_OUT + ":URL=" + url);
    //            throw e;
    //
    //        }
    //        catch (UnsupportedCharsetException e) {
    //            logger.info(e.fillInStackTrace(), e);
    //            e.printStackTrace();
    //            throw e;
    //        }
    //        catch (ClientProtocolException e) {
    //            logger.info(e.fillInStackTrace(), e);
    //            e.printStackTrace();
    //            throw e;
    //        }
    //        catch (ParseException e) {
    //            logger.info(e.fillInStackTrace(), e);
    //            e.printStackTrace();
    //            throw e;
    //        }
    //        catch (IOException e) {
    //            logger.info(e.fillInStackTrace(), e);
    //            e.printStackTrace();
    //            throw e;
    //        }
    //
    //        return rtnJson;
    //    }

}

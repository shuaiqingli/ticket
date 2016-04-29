package com.hengyu.ticket.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信开发工具类
 *
 * @author LGF
 */
public class WeixinHanlder {

    public static String ACCESS_TOKEN = "access_token";
    public static String TICKET = "ticket";
    //----------------------------------------------------
    public static Integer CHANGE_TOKEN_TIME = 1000 * 60 * 60 * 2;
    public static String TOKEN;
    public static String APPID;
    public static String MCHID;
    public static String APPSECRET;
    public static Properties wxConfig;
    public static boolean isDebug = true;
    public static String APP_PAY_APPID = "wx0b9764366267d23c";
    public static String APP_MCHID = "1321023201";

    static {
        wxConfig = new Properties();
        try {
            wxConfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties"));
            APPID = wxConfig.get("app_id").toString();
            MCHID = wxConfig.get("mch_id").toString();
            APPSECRET = wxConfig.get("app_secret").toString();
            TOKEN = wxConfig.get("token").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查是否是微信发送的请求
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     * @throws Exception
     */
    public static boolean checkSgin(String signature, String timestamp, String nonce) throws Exception {
        String[] strs = new String[]{timestamp, nonce, TOKEN};
        Arrays.sort(strs);
        StringBuffer sb = new StringBuffer();
        for (String string : strs) {
            sb.append(string);
        }
        String sha1 = SecurityHanlder.sha1(sb.toString());
        if (sha1.equals(signature)) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户信息
     * 获取用户基本信息（包括UnionID机制）
     * 开发者可通过OpenID来获取用户基本信息。请使用https协议。
     *
     * @param req
     * @param openid
     * @return
     */
    //{
    // "subscribe": 1,
    // "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M",
    // "nickname": "Band",
    // "sex": 1,
    // "language": "zh_CN",
    // "city": "广州",
    // "province": "广东",
    // "country": "中国",
    // "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
    // "subscribe_time": 1382694957,
    // "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
    // "remark": "",
    // "groupid": 0
    //}
    @SuppressWarnings("unchecked")
    public static Map<String, String> getUserInfoByUnionId(HttpServletRequest req, String openid) {
        if (openid == null || openid.isEmpty()) {
            return null;
        }
        String accessToken = null;
        if (wxConfig.get("wx_use_other_token").equals("true")) {
            accessToken = getOtherToken().get("wx_access_token");
        } else {
            if (req.getServletContext().getAttribute("wx_access_token") == null) {
                return null;
            }
            accessToken = req.getServletContext().getAttribute("wx_access_token").toString();
        }
        try {
            String json = URLHanlder.post(MessageFormat.format(wxConfig.getProperty("wx_unionid_user_info"), accessToken, openid));
            return JSON.parseObject(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取accesstoken
    public static String getAccessToken(HttpServletRequest req) {
        String accessToken = null;
        if (wxConfig.get("wx_use_other_token").equals("true")) {
            accessToken = getOtherToken().get("wx_access_token");
        } else {
            if (req.getServletContext().getAttribute("wx_access_token") == null) {
                return null;
            }
            accessToken = req.getServletContext().getAttribute("wx_access_token").toString();
        }
        return accessToken;
    }

    /**
     * 从外部获取token
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getOtherToken() {
        if (wxConfig.get("wx_use_other_token").equals("true")) {
            try {
                String token = URLHanlder.post(wxConfig.getProperty("wx_other_token_jsticket"));
                Map<String, String> map = JSON.parseObject(token, Map.class);
                if (!"success".equals(map.get("msg"))) {
                    System.out.println("================ 获取 token 秘钥错误 ！=====================");
                }
                return map;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    //日志打印
    public static void log(Object obj) {
        if (isDebug) {
            System.out.println(obj);
        }
    }

    /**
     * 创建支付签名
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static String createSign(Map<String, String> map) throws Exception {
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String k = it.next();
            String v = map.get(k);
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + wxConfig.getProperty("api_key"));
        String sign = SecurityHanlder.md5(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 微信支付
     *
     * @param body       商品信息
     * @param orderid    唯一订单号
     * @param money      价格
     * @param ip         ip地址
     * @param openid     下单openid
     * @param notify_url 异步通知地址
     * @return
     */
    public static Map<String, String> pay(String body, String orderid, BigDecimal money, String ip, String openid, String notify_url, String type) {
        Map<String, String> returnMap = null;
        try {
            //返回预支付信息
            SortedMap<String, String> resultMap = new TreeMap<String, String>();
            // 构造下单所需要的参数
            SortedMap<String, String> map = new TreeMap<String, String>();
            // 公众账号id
            map.put("appid", APPID);
            // 微信支付分配的商户号
            map.put("mch_id", MCHID);
            //使用js api
            if (type == null) {
                map.put("trade_type", "JSAPI");
            } else {
                map.put("trade_type", type);
            }
            //随机字符串
            map.put("nonce_str", create_nonce_str().replaceAll("-", ""));
            // 商品描述
            map.put("body", body);
            // 商户订单号
            map.put("out_trade_no", orderid);
            // 总金额，单位是分
            map.put("total_fee", String.valueOf(((int) (money.doubleValue() * 100))));
            //客户端ip
            map.put("spbill_create_ip", ip);
            // 异步通知地址
            if (notify_url == null || notify_url.isEmpty()) {
                map.put("notify_url", wxConfig.getProperty("wx_notify_url"));
            } else {
                map.put("notify_url", notify_url);
            }
            // 用户openid
            if (openid != null && !openid.trim().isEmpty()) {
                map.put("openid", openid);
            }
            // 生成sign
            map.put("sign", createSign(map));
            // 把参数转换为xml数据
            String params = XML.toXML(map);
            log("\n=================== 提交预支付订单  params: \n");
            log(params);
            // 提交数据，执行预支付
            returnMap = XML.toMap(URLHanlder.post(wxConfig.getProperty("wx_unifiedorder"), params));
            log("\n=================== 返回预支付订单信息 returnMap: \n" + returnMap);
            if ("success".equalsIgnoreCase(returnMap.get("return_code")) && "success".equalsIgnoreCase(returnMap.get("result_code"))) {
                // 获取返回的预支付ID
                String prepay_id = returnMap.get("prepay_id");
                resultMap.put("appId", returnMap.get("appid"));
                resultMap.put("nonceStr", map.get("nonce_str"));
                resultMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
                resultMap.put("package", "prepay_id=" + prepay_id);
                resultMap.put("signType", "MD5");
                // 第二次签名加密
                String paySign = WeixinHanlder.createSign(resultMap);
                resultMap.put("paySign", paySign);
                resultMap.put("openid", openid);
                resultMap.put("packagestr", "prepay_id=" + prepay_id);
                resultMap.remove("package");
                /*
				 	JS API支付所需参数
					 WeixinJSBridge.invoke('getBrandWCPayRequest', {
			             "appId": w.appId,
			             "timeStamp": w.timeStamp,
			             "nonceStr": w.nonceStr,
			             "package": w.packagestr,
			             "signType": w.signType,
			             "paySign": w.paySign
         			}
				*/
                log("=================== 加密支付信息，返回微信端 resultMap: \n" + resultMap + "\n");
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> m = new HashMap<String, String>();
        m.put("error", returnMap.get("return_msg"));
        m.put("err_code_des", returnMap.get("err_code_des"));
        return m;

    }

    /**
     * APP支付参数获取
     *
     * @param body
     * @param orderid
     * @param money
     * @param ip
     * @param notify_url
     * @param type
     * @return
     */
    /**
     * APP支付参数获取
     * @param body
     * @param orderid
     * @param money
     * @param ip
     * @param notify_url
     * @param type
     * @return
     */
    public static Map<String, String> appPay(String body,String orderid,BigDecimal money,String ip,String notify_url,String type) {
        Map<String, String> returnMap = null;
        try {
            //返回预支付信息
            SortedMap<String, String> resultMap = new TreeMap<String, String>();
            // 构造下单所需要的参数
            SortedMap<String, String> map = new TreeMap<String, String>();
            map.put("appid", APP_PAY_APPID);
            // 微信支付分配的商户号
            map.put("mch_id", APP_MCHID);
            map.put("trade_type", type);
            //随机字符串
            map.put("nonce_str", create_nonce_str().replaceAll("-",""));
            // 商品描述
            map.put("body", body);
            // 商户订单号
            map.put("out_trade_no",orderid);
            // 总金额，单位是分
            map.put("total_fee",String.valueOf(((int)(money.doubleValue()*100))));
            //客户端ip
            map.put("spbill_create_ip",ip);
            // 异步通知地址
            if(notify_url==null||notify_url.isEmpty()){
                map.put("notify_url", wxConfig.getProperty("wx_notify_url"));
            }else{
                map.put("notify_url", notify_url);
            }
            // 生成sign
            map.put("sign",createSign(map));
            // 把参数转换为xml数据
            String params = XML.toXML(map);
            log("\n=================== APP提交预支付订单  params: \n");
            log(params);
            // 提交数据，执行预支付
            returnMap = XML.toMap(URLHanlder.post(wxConfig.getProperty("wx_unifiedorder"),params));
            log("\n=================== APP返回预支付订单信息 returnMap: \n"+returnMap);
            if ("success".equalsIgnoreCase(returnMap.get("return_code")) && "success".equalsIgnoreCase(returnMap.get("result_code"))) {
                // 获取返回的预支付ID
                String prepay_id = returnMap.get("prepay_id");
                resultMap.put("appid",APP_PAY_APPID);
                resultMap.put("partnerid",returnMap.get("mch_id"));
                resultMap.put("prepayid",prepay_id);
                resultMap.put("noncestr", map.get("nonce_str"));
                resultMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
                resultMap.put("package", "Sign=WXPay");
                // 第二次签名加密
                String paySign = WeixinHanlder.createSign(resultMap);
                resultMap.put("sign", paySign);
                log("=================== APP加密支付信息，返回微信端 resultMap: \n"+resultMap+"\n");
                return resultMap;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        Map<String, String> m = new HashMap<String, String>();
        m.put("error",returnMap.get("return_msg"));
        m.put("err_code_des",returnMap.get("err_code_des"));
        return m;

    }

    /*
         1、交易时间超过一年的订单无法提交退款；
        2、微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
        一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额。
        接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
        <xml>
           <appid>wx2421b1c4370ec43b</appid>
           <mch_id>10000100</mch_id>
           <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
           <op_user_id>10000100</op_user_id>
           <out_refund_no>1415701182</out_refund_no>
           <out_trade_no>1415757673</out_trade_no>
           <refund_fee>1</refund_fee>
           <total_fee>1</total_fee>
           <transaction_id></transaction_id>
           <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
        </xml>

     */
    public static Map<String, String> returnedorder(String orderid, String returnedno, BigDecimal money, BigDecimal returnedmoney) throws Exception {
        //创建xml请求数据
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("appid", APPID);
        map.put("mch_id", MCHID);
        map.put("nonce_str", create_nonce_str());
        map.put("out_trade_no", orderid);
        map.put("out_refund_no", returnedno);
        map.put("total_fee", String.valueOf((int) (money.doubleValue() * 100)));
        map.put("refund_fee", String.valueOf((int) (returnedmoney.doubleValue() * 100)));
        map.put("op_user_id", MCHID);
        map.put("sign", createSign(map));

        String params = XML.toXML(map);


        HttpPost post = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = null;
        Map<String, String> returnmap = null;


        try {
            //加载证书
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream instream = Thread.currentThread().getContextClassLoader().getResourceAsStream("apiclient_cert.p12");
            keyStore.load(instream, MCHID.toCharArray());
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCHID.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            //发送请求
            post = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
            StringEntity es = new StringEntity(params);
            post.setEntity(es);
            response = httpclient.execute(post);
            HttpEntity e = response.getEntity();

            if (e != null) {
                //获取微信返回数据
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(e.getContent(), "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String text = "";
                while ((text = bufferedReader.readLine()) != null) {
                    sb.append(text);
                }
                //转换成 map 对象
                String result = sb.toString();
                returnmap = XML.toMap(result);
                //退款成功
                //result_code=SUCCESS, return_code=SUCCESS
                if ("SUCCESS".equalsIgnoreCase(returnmap.get("result_code")) && "SUCCESS".equalsIgnoreCase(returnmap.get("return_code"))) {
                    log(returnmap);
                    return returnmap;
                } else {
                    log(returnmap);
                }
            }

            EntityUtils.consume(e);

        } catch (Exception e) {

            e.printStackTrace();

            log(returnmap);
        } finally {

            try {
                response.close();
            } catch (IOException e) {
            }
            try {
                httpclient.close();
            } catch (IOException e) {
            }
        }
        return null;
    }



    // 微信查询订单
    public static Map<String,String> orderquery(String orderid) throws Exception{
        return  orderquery(orderid,null);
    }

    /*
     * 微信查询订单
     * https://api.mch.weixin.qq.com/pay/orderquery
     *
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     *     <mch_id>10000100</mch_id>
     *     <nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str>
     *	   <transaction_id>1008450740201411110005820873</transaction_id>
     *	   <sign>FDD167FAA73459FD921B144BAF4F4CA2</sign>
     *	</xml>
     *
     * */
    public static Map<String,String> orderquery(String orderid,String type) throws Exception{
        SortedMap<String, String> map = new TreeMap<String, String>();
        if(type!=null&&type.equalsIgnoreCase("APP")){
            map.put("appid", APP_PAY_APPID);
            map.put("mch_id", APP_MCHID);
        }else{
            // 公众账号id
            map.put("appid", APPID);
            // 微信支付分配的商户号
            map.put("mch_id", MCHID);
        }
        //随机字符串
        map.put("nonce_str", create_nonce_str().replaceAll("-",""));
        map.put("out_trade_no", orderid);
        map.put("sign",createSign(map));
        String params = XML.toXML(map);
        Map<String,String> returnmap = XML.toMap(URLHanlder.post("https://api.mch.weixin.qq.com/pay/orderquery",params));
        if ("success".equalsIgnoreCase(returnmap.get("return_code"))) {
            return returnmap;
        }else{
            log(returnmap);
        }
        return returnmap;
    }

    //	<xml>
    //	   <appid>wx2421b1c4370ec43b</appid>
    //	   <mch_id>10000100</mch_id>
    //	   <nonce_str>0b9f35f484df17a732e537c37708d1d0</nonce_str>
    //	   <out_refund_no></out_refund_no>
    //	   <out_trade_no>1415757673</out_trade_no>
    //	   <refund_id></refund_id>
    //	   <transaction_id></transaction_id>
    //	   <sign>66FFB727015F450D167EF38CCC549521</sign>
    //	</xml>
    public static Map<String, String> refundquery(String refundno) throws Exception {
        SortedMap<String, String> map = new TreeMap<String, String>();
        // 公众账号id
        map.put("appid", APPID);
        // 微信支付分配的商户号
        map.put("mch_id", MCHID);
        //随机字符串
        map.put("nonce_str", create_nonce_str().replaceAll("-", ""));
        map.put("refund_id", refundno);
        map.put("out_trade_no", refundno);
        map.put("refund_id", refundno);

        map.put("sign", createSign(map));
        String params = XML.toXML(map);
        Map<String, String> returnmap = XML.toMap(URLHanlder.post("https://api.mch.weixin.qq.com/pay/refundquery", params));
        if ("success".equalsIgnoreCase(returnmap.get("return_code"))) {
            return returnmap;
        } else {
            log(returnmap);
        }
        return null;
    }

    /*
          订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
          接口链接：https://api.mch.weixin.qq.com/pay/closeorder
       <xml>
           <appid>wx2421b1c4370ec43b</appid>
           <mch_id>10000100</mch_id>
           <nonce_str>4ca93f17ddf3443ceabf72f26d64fe0e</nonce_str>
           <out_trade_no>1415983244</out_trade_no>
           <sign>59FF1DF214B2D279A0EA7077C54DD95D</sign>
        </xml>
     */
    public Map<String, String> closeorder(String orderid) throws Exception {
        SortedMap<String, String> map = new TreeMap<String, String>();
        // 公众账号id
        map.put("appid", APPID);
        // 微信支付分配的商户号
        map.put("mch_id", MCHID);
        //随机字符串
        map.put("nonce_str", create_nonce_str().replaceAll("-", ""));
        map.put("out_trade_no", orderid);
        map.put("sign", createSign(map));
        String params = XML.toXML(map);
        Map<String, String> returnmap = XML.toMap(URLHanlder.post("https://api.mch.weixin.qq.com/pay/closeorder", params));
        if ("success".equalsIgnoreCase(returnmap.get("return_code"))) {
            return returnmap;
        } else {
            log(returnmap);
        }
        return null;
    }

    //获取用户列表
    @SuppressWarnings("unchecked")
    public static Map<String, String> getUserList(HttpServletRequest req) throws IOException {
        String access_token = null;
        if (wxConfig.get("wx_use_other_token").equals("true")) {
            access_token = getOtherToken().get("wx_access_token");
        } else {
            access_token = req.getSession().getServletContext().getAttribute("wx_access_token").toString();
        }
        String str = URLHanlder.post(new StringBuffer().append("https://api.weixin.qq.com/cgi-bin/user/get?access_token=").append(access_token).toString());
        return JSON.parseObject(str, Map.class);

    }

    /**
     * JS 签名验证
     *
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String signature = "";
        if (wxConfig.get("wx_use_other_token").equals("true")&&jsapi_ticket==null) {
            jsapi_ticket = getOtherToken().get("wx_js_ticket");
        }
        String string1;
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.toString().getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    /**
     * 转换成十六进制
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    /**
     * 随机字符串
     *
     * @return
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 随机时间
     *
     * @return
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * XML 文件处理，获取XML文件
     *
     * @author LGF
     */
    public static class XML {

        //输入流转换xml对象
        public static String toXMLString(InputStream input) throws Exception {
            try {
                return new SAXReader().read(input).asXML();
            } finally {
                input.close();
            }
        }

        //输入流转 map对象
        @SuppressWarnings("unchecked")
        public static Map<String, String> toMap(InputStream input) throws Exception {
            try {
                Document doc = new SAXReader().read(input);
                Element root = doc.getRootElement();
                List<Element> elements = root.elements();
                Map<String, String> map = new TreeMap<String, String>();
                for (Element element : elements) {
                    map.put(element.getName(), element.getText());
                }
                return map;
            } finally {
                input.close();
            }
        }

        //xml 字符串转 map
        @SuppressWarnings("unchecked")
        public static Map<String, String> toMap(String str) throws Exception {
            Map<String, String> map = new HashMap<String, String>();
            Document doc = DocumentHelper.parseText(str);
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                map.put(element.getName(), element.getText());
            }
            return map;
        }

        //map 转 xml
        @SuppressWarnings("all")
        public static String toXML(Map map) throws Exception {
            return toXML(map, null);
        }

        @SuppressWarnings("unchecked")
        public static String toXML(Map<String, Object> map, Element e) throws Exception {
            if (e == null) {
                e = DocumentHelper.createDocument().addElement("xml");
            }
            for (String key : map.keySet()) {
                Object val = map.get(key);
                if (val != null && val instanceof Map) {
                    toXML((Map<String, Object>) val, e.addElement(key));
                } else if (val instanceof Collection) {
                    Collection<Object> c = (Collection<Object>) val;
                    Iterator<Object> it = c.iterator();
                    while (it.hasNext()) {
                        toXML((Map<String, Object>) it.next(), e.addElement(key));
                    }
                } else {
                    e.addElement(key).setText(val != null ? val.toString() : "");
                }
            }
            return e.asXML();
        }

    }


    /**
     * 获取微信 access_token 线程
     *
     * @author LGF
     */
    public static class ThreadAccessToken implements Runnable {

        private ServletContext servletContext;

        //实例化后启动线程
        public ThreadAccessToken(ServletContext servletContext) {
            this.servletContext = servletContext;
            servletContext.setAttribute("wx_appId", APPID);
            servletContext.setAttribute("wx_appSecret", APPSECRET);
            setToken();
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    //两小时刷新一次签名
                    Thread.sleep(CHANGE_TOKEN_TIME);
                    setToken();
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(1000 * 20);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                    new ThreadAccessToken(servletContext);
                }
            }
        }

        /**
         * 设置 access_token
         */
        private synchronized void setToken() {
            try {
                String input = URLHanlder.post(wxConfig.getProperty("wx_access_token"));
                JSONObject json = JSONObject.parseObject(input);
                String wx_access_token = json.getString(ACCESS_TOKEN);
                String input1 = URLHanlder.post(MessageFormat.format(wxConfig.getProperty("wx_js_ticket"), wx_access_token));
                JSONObject json1 = JSONObject.parseObject(input1);
                String wx_ticket = json1.get(TICKET).toString();

                System.out.println("\n ACCESS_TOKEN: \n" + wx_access_token + "\n");
                System.out.println("\n JS_TICKET: \n" + wx_ticket + "\n");

                servletContext.setAttribute("wx_access_token", wx_access_token);
                servletContext.setAttribute("wx_ticket", wx_ticket);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //格式化微信昵称
    public static String formatNickName(String name, String regx) {
        if (regx == null) {
            //字母数字或汉字
            regx = "([A-Za-z]|[\u4E00-\u9FA5]|[0-9])*";
        }
        StringBuffer sb = new StringBuffer();
        if (name != null) {
            for (int i = 0; i < name.length(); i++) {
                char c = name.charAt(i);
                boolean is = String.valueOf(c).matches("([A-Za-z]|[\u4E00-\u9FA5]|[0-9])*");
                if (is) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static class Messaeg {

        //发送模板消息
        public static String sendTemplate(String openid, String templateId, String url, Map<String, Object> data) throws Exception {
            String token = getOtherToken().get("wx_access_token");
            if (token == null) {
                return null;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("touser", openid);
            map.put("template_id", templateId);
            map.put("url", url);
            map.put("data", data);
            String requestbody = JSON.toJSONString(map);
            StringBuilder sb_url = new StringBuilder();
            sb_url.append("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=").append(token);
            InputStream post = URLHanlder.post(sb_url.toString(), requestbody);
            return URLHanlder.toString(post);
        }
    }
}



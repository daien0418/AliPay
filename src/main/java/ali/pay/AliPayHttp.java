package ali.pay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.alipay.api.internal.util.StreamUtil;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.internal.util.codec.Base64;

public class AliPayHttp {

	private static String APP_ID = "2016091600525472";
	private static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeyhc7w3+WfXMVRvdUdJEX0+B6KHo/AaMnzaXkA5mCiVfk0RXyI30vDCarn2K7MLRLfUSV7xYph2MXuqvfM/3DcIoyk5ebksRZHlI+ypvlbseek6i/jQ1FOrbAqBjIinpeC3+1tn3AaHyV/VJNayZb5csX0CGu+Bi5ROy+qi63ZYto6cH3k4jqWC31qPJ836enLqfcn//5DaW1OMnfMxBX3btSUxh5tcJDopCIIMg+JC3o0WmI+HFbe1oknP1hT5JT1Pi96+KoPs65H3va2af0w87Wec8RGU8Gdd7FIcc8BONGQSiNWtdeu5lX+a3KSGtDRDTjLIjww9IFXG8qL/KtAgMBAAECggEBAJ6hxhQueogdS5s2CmCVE156jQd86KOJPzXEfLYN4o5GJ0iwmPwN+EHIIdnSoQ/g2D2SJ7Kh58IVPym1k36oshtNksJ0MEF7nixp3kBcqOHMiiAh47N5dohy36AYTqoJrbXGRw+CAJ73w38UU+SD5w5nwz1G8F+b+xcbhGf2HDWZ5R/F3+xQegyOTTums+ZtGbknpawUilzqpAVCbkpYJ7Ufyz87Ppm4YUyT8icmPxnbKTwNjDaONrckf6vIDX/vqxq+EJfbdgb0n0NFyN/m0ji6+LEgyt3W74YJvQI6DPCySxlNgOaHG6aaRBeH7szUFoUcD3W/b4f9HyJfEbfdUAECgYEA6epT1JMjhdJMfWt3sYezgGHgEOCRWUPXi8VjkA5WvJ2EwoB/wehQA2tYXjKYvAtBtOpDz06klS18JwMWPKpG7R4ky5jPXeNq8pqpuyMOOCOKwKsv+HBfgJNjPjZnEIoLeLeZBaS9VeXCHHt+3pm4dVIfwa1IT3+O4NhqHO7WYNECgYEArcf9R4jIsub3EZiA5VEiJJ5jfWHt6BQY0a3eSIfDm38Cot1Pt9S/mkoxbWk0kRQAfWhxSuJjcs5MD4hYu+3ZLk2M+QInua+j6eoDmAfvxsdpaNXa/uwq3VnTDZ4Herg7v1hB7yelQTs0INOslU1UQ6tgbaKwhz07S6QR5gleCx0CgYEA3DsiGEc8L7dzCraO59ey2hHMizdTwa6NFuyOM8g8Q7Mulo2zO1bFVqLPfUi34nvarkHN/DW34TgE5xoR6qYKxx8VV5cUzfna7HVPjoCP160m6wWB0a/DSuP5kU5ZNQoziMGbUePr2x7J5lUjeuNR9yuN+KR0K10WoX1AUuNPdbECgYAegSyEkg4o2UIlZI+7sTI9G1B7geUxB9YTeGG8eZhsgy8DR7XmnwHDI6Lpgb4oeUpcuSip7HNog632TAjHdEjbR+GnFCnE/n/XX6yh00LTsc1fCasXdH1RvaK+kS7e69gRDn0EbgEkIjBUUtPIoXpZJowWsqgqFSWDcZlyt9I6QQKBgBUuiJULbSXfcyn+i5BD2hLjrvGAvSLSnHCYvG5rsUkClAkLc7DkanY0lryXuQAwgX9n0XKzP1+6XnuYhppCCzWN7ncYkZxASWcUE5wp8tpSGwxv7nwurl57bUU8jWTvHWKI2D97Gtoyrk2J0mKwN6tjQ1WAyNs86qTHONUH64iq";
	// private static String
	// ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtYasgzqI25YYYeph5amD0EOwVwewvsnvdXHUtOXGCxAm7P/JWDRq/QZu9ehB4vNHxbb/8DIT/JsFOHOZTjCxPPQPbwXwxX9fLY5kBBpnQf0bbPgfkvyOobTkm7n7/LnWfMpWcCDdJNmeJbLDojNb8PNOxy7oYB+VIG4qv9+IxEbwiEG80Of1r8KuRn2NL+lFm+16swSz0fwFwpk4NmDawUV1n5mnqKWKZeBwFsYqkro9iZIXLGwsZRmzRK1xU0xaDYY0VwWzoleVSgEgp8EmtBKjFffVEt7nQDIvl7FNFGyorVReZ2nDoRxdwb73b+HokcuCBSnr1q01aZ+6pPkqMQIDAQAB";
	private static String CHARSET = "utf-8";
	private static String SELLER_ID = "2088102175904359";
	private static String URL = "https://openapi.alipaydev.com/gateway.do";

	public static void main(String args[]) {
		AliPayHttp.precreate();
	}

	public static void precreate() {
		String url = new String(URL);

		Long timestamp = Long.valueOf(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT+8"));

		Map<String, String> params = new HashMap<String, String>();
		params.put("app_id", APP_ID);
		params.put("notify_url", "http://47.104.150.47/v1/zi_jin_liu_shui/posttest");
		params.put("method", "alipay.trade.precreate");
		params.put("charset", CHARSET);
		params.put("sign_type", "RSA2");
		params.put("timestamp", df.format(new Date(timestamp.longValue())));
		params.put("version", "1.0");

		String out_trade_no = String.valueOf(System.currentTimeMillis());
		String price = "65";
		String subject = "办卡费用";
		String seller_id = SELLER_ID;

		String bizContent = "{\"out_trade_no\":\"" + out_trade_no + "\",\"seller_id\":" + seller_id
				+ ",\"total_amount\":\"" + price + "\",\"subject\":\"" + subject + "\"}";
		params.put("biz_content", bizContent);

		String sign = sign(new TreeMap<String,String>(params), CHARSET, APP_PRIVATE_KEY);// 生成签名
		params.put("sign", sign);

		 String urlParam = generateUrl(url, params, "utf-8");
		 System.out.println(urlParam);

		 String result = httpClientGet(urlParam);
		 System.out.println(result);
	}

	public static String sign(Map map, String charset, String privateKey) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Object key : map.keySet()) {
			if (map.get(key) != null && !map.get(key).toString().trim().equals("")) {
				if (stringBuilder.length() != 0)
					stringBuilder.append("&");
				stringBuilder.append(key);
				stringBuilder.append("=");
				stringBuilder.append(map.get(key));
			}
		}
//		System.out.println(stringBuilder.toString());

		PrivateKey priKey;
		try {
			priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
			Signature signature = Signature.getInstance("SHA256WithRSA");

			signature.initSign(priKey);

			if (StringUtils.isEmpty(charset))
				signature.update(stringBuilder.toString().getBytes());
			else
				signature.update(stringBuilder.toString().getBytes(charset));

			byte[] signed = signature.sign();
			return new String(Base64.encodeBase64(signed));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
		if ((ins == null) || (StringUtils.isEmpty(algorithm))) {
			return null;
		}
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		byte[] encodedKey = StreamUtil.readText(ins).getBytes();
		encodedKey = Base64.decodeBase64(encodedKey);
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	public static String generateUrl(String urlParam, Map<String, String> params, String charset) {
		StringBuffer sbParams = new StringBuffer();
		if (params != null && params.size() > 0) {
			for (Entry<String, String> entry : params.entrySet()) {
				sbParams.append(entry.getKey());
				sbParams.append("=");
				try {
					sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// sbParams.append(entry.getValue());
				sbParams.append("&");
			}
		}
		if (sbParams != null && sbParams.length() > 0) {
			urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
		}
		return urlParam;
	}

	public static String httpClientGet(String urlParam) {
		StringBuffer resultBuffer = null;
		HttpClient client = new DefaultHttpClient();
		BufferedReader br = null;
		// HttpGet httpGet = new HttpGet(urlParam);
		HttpPost httpPost = new HttpPost(urlParam);
		try {
			HttpResponse response = client.execute(httpPost);
			// read response data
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String temp;
			resultBuffer = new StringBuffer();
			while ((temp = br.readLine()) != null) {
				resultBuffer.append(temp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					br = null;
					throw new RuntimeException(e);
				}
			}
		}
		return resultBuffer.toString();
	}
}

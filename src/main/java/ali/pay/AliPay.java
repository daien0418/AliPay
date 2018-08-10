package ali.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

public class AliPay {
	private static String APP_ID="2016091600525472";
	private static String APP_PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeyhc7w3+WfXMVRvdUdJEX0+B6KHo/AaMnzaXkA5mCiVfk0RXyI30vDCarn2K7MLRLfUSV7xYph2MXuqvfM/3DcIoyk5ebksRZHlI+ypvlbseek6i/jQ1FOrbAqBjIinpeC3+1tn3AaHyV/VJNayZb5csX0CGu+Bi5ROy+qi63ZYto6cH3k4jqWC31qPJ836enLqfcn//5DaW1OMnfMxBX3btSUxh5tcJDopCIIMg+JC3o0WmI+HFbe1oknP1hT5JT1Pi96+KoPs65H3va2af0w87Wec8RGU8Gdd7FIcc8BONGQSiNWtdeu5lX+a3KSGtDRDTjLIjww9IFXG8qL/KtAgMBAAECggEBAJ6hxhQueogdS5s2CmCVE156jQd86KOJPzXEfLYN4o5GJ0iwmPwN+EHIIdnSoQ/g2D2SJ7Kh58IVPym1k36oshtNksJ0MEF7nixp3kBcqOHMiiAh47N5dohy36AYTqoJrbXGRw+CAJ73w38UU+SD5w5nwz1G8F+b+xcbhGf2HDWZ5R/F3+xQegyOTTums+ZtGbknpawUilzqpAVCbkpYJ7Ufyz87Ppm4YUyT8icmPxnbKTwNjDaONrckf6vIDX/vqxq+EJfbdgb0n0NFyN/m0ji6+LEgyt3W74YJvQI6DPCySxlNgOaHG6aaRBeH7szUFoUcD3W/b4f9HyJfEbfdUAECgYEA6epT1JMjhdJMfWt3sYezgGHgEOCRWUPXi8VjkA5WvJ2EwoB/wehQA2tYXjKYvAtBtOpDz06klS18JwMWPKpG7R4ky5jPXeNq8pqpuyMOOCOKwKsv+HBfgJNjPjZnEIoLeLeZBaS9VeXCHHt+3pm4dVIfwa1IT3+O4NhqHO7WYNECgYEArcf9R4jIsub3EZiA5VEiJJ5jfWHt6BQY0a3eSIfDm38Cot1Pt9S/mkoxbWk0kRQAfWhxSuJjcs5MD4hYu+3ZLk2M+QInua+j6eoDmAfvxsdpaNXa/uwq3VnTDZ4Herg7v1hB7yelQTs0INOslU1UQ6tgbaKwhz07S6QR5gleCx0CgYEA3DsiGEc8L7dzCraO59ey2hHMizdTwa6NFuyOM8g8Q7Mulo2zO1bFVqLPfUi34nvarkHN/DW34TgE5xoR6qYKxx8VV5cUzfna7HVPjoCP160m6wWB0a/DSuP5kU5ZNQoziMGbUePr2x7J5lUjeuNR9yuN+KR0K10WoX1AUuNPdbECgYAegSyEkg4o2UIlZI+7sTI9G1B7geUxB9YTeGG8eZhsgy8DR7XmnwHDI6Lpgb4oeUpcuSip7HNog632TAjHdEjbR+GnFCnE/n/XX6yh00LTsc1fCasXdH1RvaK+kS7e69gRDn0EbgEkIjBUUtPIoXpZJowWsqgqFSWDcZlyt9I6QQKBgBUuiJULbSXfcyn+i5BD2hLjrvGAvSLSnHCYvG5rsUkClAkLc7DkanY0lryXuQAwgX9n0XKzP1+6XnuYhppCCzWN7ncYkZxASWcUE5wp8tpSGwxv7nwurl57bUU8jWTvHWKI2D97Gtoyrk2J0mKwN6tjQ1WAyNs86qTHONUH64iq";
	private static String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtYasgzqI25YYYeph5amD0EOwVwewvsnvdXHUtOXGCxAm7P/JWDRq/QZu9ehB4vNHxbb/8DIT/JsFOHOZTjCxPPQPbwXwxX9fLY5kBBpnQf0bbPgfkvyOobTkm7n7/LnWfMpWcCDdJNmeJbLDojNb8PNOxy7oYB+VIG4qv9+IxEbwiEG80Of1r8KuRn2NL+lFm+16swSz0fwFwpk4NmDawUV1n5mnqKWKZeBwFsYqkro9iZIXLGwsZRmzRK1xU0xaDYY0VwWzoleVSgEgp8EmtBKjFffVEt7nQDIvl7FNFGyorVReZ2nDoRxdwb73b+HokcuCBSnr1q01aZ+6pPkqMQIDAQAB";
	private static String CHARSET ="utf-8";
	private static String SELLER_ID="2088102175904359";
	private static AlipayClient alipayClient = null;
	static{
		 alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
	}

	public static void main(String args[]){
//		AliPay.refund();
//		AliPay.cancel();
		AliPay.precreate();
//		AliPay.query();
	}

	public static void precreate(){
//		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
		AlipayTradePrecreateRequest precreateRequest = new AlipayTradePrecreateRequest();
		AlipayTradePrecreateModel bizModel=new AlipayTradePrecreateModel();
		precreateRequest.setBizModel(bizModel);

		bizModel.setSellerId(SELLER_ID);
		bizModel.setOutTradeNo(String.valueOf(System.currentTimeMillis()));
//		bizModel.setOutTradeNo("1532663638868");
		bizModel.setTotalAmount("55");
		bizModel.setSubject("办卡费");
		bizModel.setTimeoutExpress("2h");

		AlipayTradePrecreateResponse response;
		try {
			response = alipayClient.execute(precreateRequest);
			if(response.getCode().equals("10000")){
				System.out.println("业务处理成功！");
			}else if(response.getCode().equals("40004")){
				System.out.println("业务处理失败！");
			}else if(response.getCode().equals("20000")){
				System.out.println("系统异常！");
			}
			System.out.println(response.getBody());
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}

	public static void query(){
		AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
		AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();
		queryRequest.setBizModel(bizModel);

//		bizModel.setOutTradeNo("1532661711786");
		bizModel.setOutTradeNo("1532663638868");
		AlipayTradeQueryResponse response;
			try {
				response = alipayClient.execute(queryRequest);
				if(response.getCode().equals("10000")){
					System.out.println("业务处理成功！");
					switch (response.getTradeStatus()) {
					case "WAIT_BUYER_PAY":
						System.out.println("交易创建，等待买家付款");
						break;
					case "TRADE_CLOSED":
						System.out.println("未付款交易超时关闭，或支付完成后全额退款");
						break;
					case "TRADE_SUCCESS":
						System.out.println("交易支付成功");
						break;
					case "TRADE_FINISHED":
						System.out.println("交易结束，不可退款");
						break;
					default:
						break;
					}

				}else if(response.getCode().equals("40004")){
					System.out.println("业务处理失败！");
				}else if(response.getCode().equals("20000")){
					System.out.println("系统异常！");
				}
				System.out.println(response.getBody());
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 1.完成的交易，取消会退款
	 * 2.未完成支付的交易，取消会关闭订单
	 */
	public static void cancel(){
		AlipayTradeCancelRequest cancelRequest = new AlipayTradeCancelRequest();
		AlipayTradeCancelModel bizModel = new AlipayTradeCancelModel();
		cancelRequest.setBizModel(bizModel);
		bizModel.setOutTradeNo("1532663638868");

		AlipayTradeCancelResponse response;
			try {
				response= alipayClient.execute(cancelRequest);
				if(response.getCode().equals("10000")){
					System.out.println("业务处理成功！");
					if(response.getAction().equals("close")){
						System.out.println("关闭交易，无退款 ！");
					}else if(response.getAction().equals("refund")){
						System.out.println("产生了退款！");
					}
				}else if(response.getCode().equals("40004")){
					System.out.println("业务处理失败！");
				}else if(response.getCode().equals("20000")){
					System.out.println("系统异常！");
				}
				System.out.println(response.getBody());
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}

	}

	public static void refund(){
		AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
		AlipayTradeRefundModel bizModel = new AlipayTradeRefundModel();
		refundRequest.setBizModel(bizModel);

		bizModel.setOutTradeNo("2");
		bizModel.setOutRequestNo("2");
		bizModel.setRefundAmount("100");
		bizModel.setRefundReason("质量问题");

		AlipayTradeRefundResponse response;
			try {
				response=alipayClient.execute(refundRequest);
				if(response.getCode().equals("10000")){
					System.out.println("业务处理成功！");
				}else if(response.getCode().equals("40004")){
					System.out.println("业务处理失败！");
				}else if(response.getCode().equals("20000")){
					System.out.println("系统异常！");
				}
				System.out.println(response.getBody());
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
	}
}

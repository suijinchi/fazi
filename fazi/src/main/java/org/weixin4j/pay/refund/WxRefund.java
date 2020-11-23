package org.weixin4j.pay.refund;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 退款对象 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
 */
public class WxRefund {

	private String appid;

	/**
	 * 商户号
	 */
	private String mch_id;

	/**
	 * 随机字符串
	 *
	 * 随机字符串，不长于32位
	 */
	private String nonce_str;
	/**
	 * 签名
	 *
	 * 签名算法：https://pay.weixin.qq.com/wiki/doc/api/cash_coupon.php?chapter=4_3
	 */
	private String sign;

	private String sign_type;

	private String transaction_id;

	private String out_trade_no;

	private String out_refund_no;

	private int total_fee;

	private int refund_fee;

	private String refund_fee_type;

	private String refund_desc;

	private String refund_account;

	private String notify_url;

	public Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);

		if (StringUtils.isNotBlank(sign_type)) {
			map.put("sign_type", sign_type);
		}

		map.put("transaction_id", transaction_id);
		map.put("out_trade_no", out_trade_no);
		map.put("out_refund_no", out_refund_no);

		map.put("total_fee", String.valueOf(total_fee));
		map.put("refund_fee", String.valueOf(refund_fee));

		if (StringUtils.isNotBlank(refund_fee_type)) {
			map.put("refund_fee_type", refund_fee_type);
		}
		if (StringUtils.isNotBlank(refund_desc)) {
			map.put("refund_desc", refund_desc);
		}
		if (StringUtils.isNotBlank(refund_account)) {
			map.put("refund_account", refund_account);
		}
		if(StringUtils.isNotBlank(notify_url)){
			map.put("notify_url", notify_url);
		}
		return map;
	}

	public String toXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<appid><![CDATA[").append(appid).append("]]></appid>");
		sb.append("<mch_id><![CDATA[").append(mch_id).append("]]></mch_id>");
		sb.append("<nonce_str><![CDATA[").append(nonce_str).append("]]></nonce_str>");

		sb.append("<sign><![CDATA[").append(sign).append("]]></sign>");
		if (StringUtils.isNotBlank(sign_type)) {
			sb.append("<sign_type><![CDATA[").append(sign_type).append("]]></sign_type>");
		}
		sb.append("<transaction_id><![CDATA[").append(transaction_id).append("]]></transaction_id>");

		sb.append("<out_trade_no><![CDATA[").append(out_trade_no).append("]]></out_trade_no>");
		sb.append("<out_refund_no><![CDATA[").append(out_refund_no).append("]]></out_refund_no>");
		sb.append("<total_fee><![CDATA[").append(total_fee).append("]]></total_fee>");

		sb.append("<refund_fee><![CDATA[").append(refund_fee).append("]]></refund_fee>");
		if (StringUtils.isNotBlank(refund_fee_type)) {
			sb.append("<refund_fee_type><![CDATA[").append(refund_fee_type).append("]]></refund_fee_type>");
		}
		if (StringUtils.isNotBlank(refund_desc)) {
			sb.append("<refund_desc><![CDATA[").append(refund_desc).append("]]></refund_desc>");
		}

		if (StringUtils.isNotBlank(refund_account)) {
			sb.append("<refund_account><![CDATA[").append(refund_account).append("]]></refund_account>");
		}
		if (StringUtils.isNotBlank(notify_url)) {
			sb.append("<notify_url><![CDATA[").append(notify_url).append("]]></notify_url>");
		}

		sb.append("</xml>");
		return sb.toString();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public int getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getRefund_fee_type() {
		return refund_fee_type;
	}

	public void setRefund_fee_type(String refund_fee_type) {
		this.refund_fee_type = refund_fee_type;
	}

	public String getRefund_desc() {
		return refund_desc;
	}

	public void setRefund_desc(String refund_desc) {
		this.refund_desc = refund_desc;
	}

	public String getRefund_account() {
		return refund_account;
	}

	public void setRefund_account(String refund_account) {
		this.refund_account = refund_account;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
}

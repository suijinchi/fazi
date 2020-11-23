package org.weixin4j.pay.refund;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信退款通知结果
 *
 * @author qsyang
 * @version 1.0
 */
@XmlRootElement(name = "xml")
public class WxRefundNotifyResult {

    /**
     * 字段名：返回状态码
     *
     * 必填：是
     *
     * 类型：String(16)
     *
     * 描述：SUCCESS/FAIL
     *
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    private String return_code;
    /**
     * 字段名：返回信息
     *
     * 必填：否
     *
     * 类型：String(128)
     *
     * 描述：返回信息，如非空，为错误原因
     *
     * 签名失败、参数格式校验错误等
     */
    private String return_msg;
    
    //*** 以下字段在return_code为SUCCESS的时候有返回 ***//
    private String appid;         //商户appid
    private String mch_id;          //商户号
    private String nonce_str;		//
    private String req_info;
    
    public String getReturn_code() {
        return return_code;
    }

    @XmlElement(name = "return_code")
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    @XmlElement(name = "return_msg")
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getMch_id() {
        return mch_id;
    }

    @XmlElement(name = "mch_id")
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getAppid() {
        return appid;
    }

    @XmlElement(name = "appid")
    public void setAppid(String appid) {
        this.appid = appid;
    }

    
    
	public String getNonce_str() {
		return nonce_str;
	}
	@XmlElement(name = "nonce_str")
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getReq_info() {
		return req_info;
	}
	@XmlElement(name = "req_info")
	public void setReq_info(String req_info) {
		this.req_info = req_info;
	}

	
	
}


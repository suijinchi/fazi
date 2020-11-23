package org.weixin4j.pay.refund;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信退款结果
 *
 * @author qsyang
 * @version 1.0
 */
@XmlRootElement(name = "xml")
public class WxRefundResult {

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
    private String result_code;     //业务结果  SUCCESS/FAIL
    private String err_code;        //错误代码
    private String err_code_des;    //错误代码描述
    private String appid;         //商户appid
    private String mch_id;          //商户号
    private String nonce_str;		//
    private String sign;
    private String transaction_id;
    private String out_trade_no;
    private String out_refund_no;
    private String refund_id;
    private Integer refund_fee;
    private Integer total_fee;
    private Integer cash_fee;
    
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

    public String getSign() {
        return sign;
    }

    @XmlElement(name = "sign")
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    @XmlElement(name = "result_code")
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    @XmlElement(name = "err_code")
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    @XmlElement(name = "err_code_des")
    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
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

	public String getTransaction_id() {
		return transaction_id;
	}

	@XmlElement(name = "transaction_id")
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	@XmlElement(name = "out_trade_no")
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	@XmlElement(name = "out_refund_no")
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public String getRefund_id() {
		return refund_id;
	}

	@XmlElement(name = "refund_id")
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	public Integer getRefund_fee() {
		return refund_fee;
	}

	@XmlElement(name = "refund_fee")
	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	@XmlElement(name = "total_fee")
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public Integer getCash_fee() {
		return cash_fee;
	}

	@XmlElement(name = "cash_fee")
	public void setCash_fee(Integer cash_fee) {
		this.cash_fee = cash_fee;
	}

	@Override
	public String toString() {
		return "WxRefundResult [return_code=" + return_code + ", return_msg=" + return_msg + ", result_code=" + result_code + ", err_code=" + err_code + ", err_code_des=" + err_code_des + ", appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", sign=" + sign + ", transaction_id=" + transaction_id + ", out_trade_no=" + out_trade_no + ", out_refund_no=" + out_refund_no + ", refund_id=" + refund_id + ", refund_fee=" + refund_fee + ", total_fee=" + total_fee + ", cash_fee=" + cash_fee + "]";
	}
}

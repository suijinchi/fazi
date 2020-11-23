package org.weixin4j.pay;

public class ReturnMsg {
	private String return_code;
	private String return_msg;
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<return_code><![CDATA[").append(return_code).append("]]></return_code>");
        sb.append("<return_msg><![CDATA[").append(return_msg).append("]]></return_msg>");
        sb.append("</xml>");
        return sb.toString();
    }
}

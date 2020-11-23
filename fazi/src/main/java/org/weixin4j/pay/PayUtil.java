package org.weixin4j.pay;

import java.io.StringReader;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.zhengbangnet.common.utils.XMLUtil;
import org.weixin4j.WXXMLUtil;

/**
 * 微信支付工具
 *
 * @author YangQS
 */
public class PayUtil {

    /**
     * 对预下单Id进行H5支付
     *
     * @param appId 开发者Id
     * @param prepay_id 预下单Id
     * @param paternerKey 商户密钥
     * @return 支付对象
     */
    public static WCPay getBrandWCPayRequest(String appId, String prepay_id, String paternerKey) {
        //初始化支付对象
        return new WCPay(appId, prepay_id, paternerKey);
    }

    /**
     * chooseWXPay 旧版
     *
     * @param appId 公众号Id
     * @param jsapi_ticket jsapi验证票据
     * @param packages 签名参数
     * @param url 发起请求的url地址
     * @param paternerKey 商户密钥
     * @param appKey 商户支付密钥
     * @return 支付对象
     */
    @Deprecated
    public static WXPay getChooseWXPay(String appId, String jsapi_ticket, WXPackage packages, String url, String paternerKey, String appKey) {
        return new WXPay(appId, jsapi_ticket, packages, url, paternerKey, appKey);
    }

    /**
     * chooseWXPay 新版
     *
     * @param appId 公众号Id
     * @param jsapi_ticket jsapi验证票据
     * @param prepay_id 预下单Id
     * @param paternerKey 商户密钥
     * @param url 发起请求的url地址
     * @return 支付对象
     */
    public static WXPay getChooseWXPay(String appId, String jsapi_ticket, String prepay_id, String url, String paternerKey) {
        return new WXPay(appId, jsapi_ticket, prepay_id, url, paternerKey);
    }

    /**
     * 验证签名
     *
     * @param xmlMsg xml参数字符串
     * @param paternerKey 商户密钥
     * @return 签名验证，成功返回true,否则返回false
     */
    public static boolean verifySign(String xmlMsg, String paternerKey) {
    	try {
            //转换为Map
//            Map<String, String> M = XMLUtil.parseXml(xmlMsg);
            Map<String, String> M = WXXMLUtil.xmlToMap(xmlMsg);
            String resultSign = ""; 
            if (M.containsKey("sign")) {
            	resultSign = (String)M.get("sign");
                M.remove("sign");
            }
            //签名
            String sign = SignUtil.getSign(M, paternerKey);
            if (sign == null || sign.equals("")) {
                return false;
            }
            //判断是否一致
            return sign.equals(resultSign);
        } catch (Exception ex) {
        	ex.printStackTrace();
            return false;
        }
    }
    
    
    public static PayNotifyResult getNotifyResult(String xmlMsg) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(PayNotifyResult.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		//结果
		PayNotifyResult payNotifyResult = (PayNotifyResult) unmarshaller.unmarshal(new StringReader(xmlMsg));
    	return payNotifyResult;
    }
    
    
    
    
}

package org.weixin4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.weixin4j.pay.refund.WxRefundNotifyResult;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WXXMLUtil {

    private static final Logger logger = LoggerFactory.getLogger(WXXMLUtil.class);

    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);
        return documentBuilderFactory.newDocumentBuilder();
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
            }
            return data;
        } catch (Exception ex) {
            logger.warn("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
            throw ex;
        }

    }

    /**
     * xml转成实体类
     */
    public static Object xmlToBean(String strXML, Class<?> clazz) throws Exception {
        Map<String, String> map = xmlToMap(strXML);
        return map2Object(map, clazz);
    }

    /**
     * map转成实体类，实体类属性只能是string
     */
    public static Object map2Object(Map<String, String> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                String type = field.getGenericType().toString(); // 获取属性的类型
                String value = map.get(field.getName());
                field.setAccessible(true);
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    field.set(obj, map.get(field.getName()));
                }else if (type.equals("class java.lang.Integer")) {
                    Integer d = Integer.parseInt(value);
                    field.set(obj, d);
                }else if (type.equals("class java.lang.Long")) {
                    Long d=Long.parseLong(value);
                    field.set(obj, d);
                }else if (type.equals("class java.lang.Boolean")) {
                    Boolean d = Boolean.parseBoolean(value);
                    field.set(obj, d);
                }else if (type.equals("class java.math.BigDecimal")) {
                    BigDecimal b = new BigDecimal(value);
                    field.set(obj, b);
                }else if (type.equals("class java.lang.Double")) {
                    Double b = new Double(value);
                    field.set(obj, b);
                }else if (type.equals("class java.lang.Float")) {
                    Float b = new Float(value);
                    field.set(obj, b);
                }else{
                    field.set(obj, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static void main(String[] args) throws Exception {
        String xml =
                "   <xml>" +
                        "        <!DOCTYPE root ["+
                        "                        <!ENTITY % file SYSTEM \"file:///etc/password\">"+
                        "            <!ENTITY % xxe SYSTEM \"http://127.0.0.1:9000/xxe.dtd\">"+
                        "            %xxe;"+
                        "        ]>"+
                        "<appid><![CDATA[wxd7cf5169f036b230]]></appid>"+
                        "   <bank_type><![CDATA[CCB_DEBIT]]></bank_type>"+
                        "   <cash_fee><![CDATA[800]]></cash_fee>"+
                        "   <fee_type><![CDATA[CNY]]></fee_type>"+
                        "   <is_subscribe><![CDATA[Y]]></is_subscribe>"+
                        "   <mch_id><![CDATA[1509017601]]></mch_id>"+
                        "   <nonce_str><![CDATA[kRIy8kouY2WC22Q0g9sHvkck8kqMP5Zd]]></nonce_str>"+
                        "   <openid><![CDATA[o2d4Fj-vwyrmUMEzBK2m_iwGGCjg]]></openid>"+
                        "   <out_trade_no><![CDATA[W201809110653496420]]></out_trade_no>"+
                        "   <result_code><![CDATA[SUCCESS]]></result_code>"+
                        "   <return_code><![CDATA[SUCCESS]]></return_code>"+
                        "   <sign><![CDATA[18EAECEDB57965567D6CF6595A3CE566]]></sign>"+
                        "   <time_end><![CDATA[20180911065358]]></time_end>"+
                        "   <total_fee>800</total_fee>"+
                        "   <trade_type><![CDATA[JSAPI]]></trade_type>"+
                        "   <transaction_id><![CDATA[4200000161201809110380603637]]></transaction_id>"+
                        "   </xml>";


        String xml2 = "<xml><return_code>SUCCESS</return_code><appid><![CDATA[wxd7cf5169f036b230]]></appid><mch_id><![CDATA[1509017601]]></mch_id><nonce_str><![CDATA[7202adb0711e0dc86fa1e162be5678c6]]></nonce_str><req_info><![CDATA[y5agfHJtLwsBQulg6MypHSyV7QmdPtncTBaokzErGfd44NDZmsGGxLxvNJQkVXUyVJlMg2VwILWaYCC83AblzV8OavbGMgnh4/SogYZzqveDLywXWYos0JGDBiEETrqjKH4Vyd/7vhrpobwIpi464Gjyl0kfDcY8hYdt3xQ4kghtm3TtGZwa6E/peGVQjFkvB8TB7VlG0zITN9vf4QYa0Hz+Bn9hirXjk/2a+LXZJz7NkuWlwaSxc8FD4V2LHuolZnRi8C9wYUXaDFR0+lG4nAm/GwW5XTaPD80yp0RjwA/yFEX+2mzX6uW/DCyi7OisFGwIblwbKTACb0jCRaWKtSYABpgM4ww2zBQcNSNSIWb82cwyuCrBNpe7l6ZrsPEG+++jNYJJtU9PVuY1FHm5mO4BUVnGdpHBzynRWNeSClmS4jod9NAnVNvFKOtECC4XGrrK/NZFpguvrX4ZoBvKS/vtVNi9vI5/JPaF3Yzi9M8GIRnIujEbiZQilBe+/RfiJbfoUDR0Xz3bi0W2i6IoLDKv3htZTlu5wXcRX/C2HA2MmUxsKahQmCE3wG/9wgicLgg5cBbu8VC7/3m/O16GmJvgzX/NBbyvE0xdcYf1GqPlRl3JLRDUVUv3u7RWL5QwqRvlMWBjICFcxD3lFkAyeeQkawxjULBx/j7dBvGHYsbloQlQdtRGMl6owRiKbJ2B/C4hyeVs7MROIyGhfeENEi4Hk3A9LaXfYbJZ0V+xpCqydYatgS1Uj9QYQMvZg20SXN61eMbyAbIhf3J12YESVZ39tTQ5cOTtqy+/IbHDJP8sYFlM+flwor2SWa1Kw1nwQpA6sdi+VVCD7mar/DQUqDAlPi+uG+pljmNbw70XHuv6M6lxPc9mYCOLZHlCAz/6535FoDgwHOJDJa37cZcZco1KldYIirf65Y1L3eWc51tdYPTmCmePIG3BroGvpY5mTXDkgvotFRfxtigKc5Ay1RLKbuiLZNDI7xxeyy0fQRJ65qPU95fuSCWSxmOsPOXtT1NkD5ouuNo/zY51CIpTEdRGe4qh6RZs4EtSE6Pi+3Ofz+hbigMRDRx76dBywDNY]]></req_info></xml>";


        /*
        PayNotifyResult result = (PayNotifyResult)xmlToBean(xml,PayNotifyResult.class);
        System.out.println(result);
        */
/*
        Map<String, String> result2 = XMLUtil.parseXml(xml);
        System.out.println(result2);
*/

        WxRefundNotifyResult result = (WxRefundNotifyResult) WXXMLUtil.xmlToBean(xml2,WxRefundNotifyResult.class);
        System.out.println(result);


    }

}

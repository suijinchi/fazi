package com.zhengbangnet.modules.job;

import com.zhengbangnet.Config;
import com.zhengbangnet.common.utils.DateUtils;
import com.zhengbangnet.modules.entity.Member;
import com.zhengbangnet.modules.entity.OrdersItem;
import com.zhengbangnet.modules.service.MemberService;
import com.zhengbangnet.modules.service.OrdersItemService;
import com.zhengbangnet.modules.service.OrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.weixin4j.Configuration;
import org.weixin4j.WXTemplateMsg;
import org.weixin4j.Weixin;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderMsgJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name="ordersServiceImpl")
	private OrdersService ordersService;

    @Resource(name="ordersItemServiceImpl")
    private OrdersItemService ordersItemService;

    @Resource(name="memberServiceImpl")
    private MemberService memberService;

    @Resource(name = "weixin")
    private Weixin weixin;

    /**
     * 订单发货通知任务
     */
    @Scheduled(cron = "0 00 09 * * ? ")
//    @Scheduled(cron = "0/10 * * * * ? ")
    public void orderMsgJob1() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("payStatus", 1);
        params.put("orderStatus", 1);
        params.put("shippingMsgDate", new Date());
        params.put("isShippingMsg",0);
        params.put("minuteDiff",5);
        params.put("shippingMethod",1);
        List<HashMap<String, Object>> list = ordersService.findListByParams(params);
        for(Map<String,Object> data:list){
            this.sendMsg(data);
        }
    }

    @Scheduled(cron = "0 00 14 * * ? ")
    public void orderMsgJob2() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("payStatus", 1);
        params.put("orderStatus", 1);
        params.put("shippingMsgDate", new Date());
        params.put("isShippingMsg",0);
        params.put("minuteDiff",5);
        params.put("shippingMethod",1);
        List<HashMap<String, Object>> list = ordersService.findListByParams(params);
        for(Map<String,Object> data:list){
            this.sendMsg(data);
        }
    }

    @Scheduled(cron = "0 30 16 * * ? ")
    public void orderMsgJob3() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("payStatus", 1);
        params.put("orderStatus", 1);
        params.put("shippingMsgDate", new Date());
        params.put("isShippingMsg",0);
        params.put("minuteDiff",5);
        params.put("shippingMethod",1);
        List<HashMap<String, Object>> list = ordersService.findListByParams(params);
        for(Map<String,Object> data:list){
            this.sendMsg(data);
        }
    }


    private void sendMsg(Map<String,Object> data){
        Long id = (Long)data.get("id");
        Long memberId = (Long)data.get("memberId");
        String sn = (String)data.get("sn");
        BigDecimal amount = (BigDecimal)data.get("amount");
        String mobile = (String)data.get("mobile");
        String name = (String)data.get("name");
        String address = (String)data.get("address");

        Member member = memberService.getByPrimaryKey(memberId);
        if(member!=null){


             /*
            OPENTM414261577
            详细内容
                 {{first.DATA}}
                 订单编号：{{keyword1.DATA}}
                 订单金额：{{keyword2.DATA}}
                 收货人电话：{{keyword3.DATA}}
                 收货人姓名：{{keyword4.DATA}}
                 收货人地址：{{keyword5.DATA}}
                 {{remark.DATA}}
             内容示例
                 亲，您购买的商品已经发货啦，配送时间大概7到15分钟不等，请耐心等候哦！
                 订单编号：1513149767
                 订单金额：22.1
                 收货人电话：18380807232
                 收货人姓名：刘先生
                 收货人地址：成都市高新区保利星座三栋309
                 感谢您的购买，期待下次光临。
            */

            com.zhengbangnet.modules.entity.Orders orders = new com.zhengbangnet.modules.entity.Orders();
            orders.setModifyDate(new Date());
            orders.setShippingDate(new Date());
            orders.setIsShippingMsg(1);
            orders.setShippingStatus(1);
            orders.setExpressCompany("法滋物流");
            orders.setExpressSn("");
            orders.setId(id);
            ordersService.updateByPrimaryKeySelective(orders);

            String pname = "";
            List<OrdersItem> ordersItems = ordersItemService.getByOrdersId(id);
            for(OrdersItem oi : ordersItems){
                pname += oi.getName()+"*" + oi.getQuantity()+" ";
            }

            try{
                WXTemplateMsg msg = new WXTemplateMsg();
                msg.setTouser(member.getOpenid());
                msg.setTemplate_id(Config.getProperty("shippingMsgId"));
                msg.addItem("first", "亲，您购买的商品已经在配送当中，请耐心等候哦！");
                msg.addItem("keyword1", sn,"#173177");//
                msg.addItem("keyword2", amount.toString(),"#173177");
                msg.addItem("keyword3", mobile,"#173177");
                msg.addItem("keyword4",name,"#173177");
                msg.addItem("keyword5",address,"#173177");
                msg.addItem("remark","商品："+pname,"#173177");
                weixin.login(Configuration.getOAuthAppId(), Configuration.getOAuthSecret());
                weixin.sendTemplateMsg(msg);
            }catch(Exception e){
                logger.error("发送发货通知模板异常",e);
            }

        }
    }


}

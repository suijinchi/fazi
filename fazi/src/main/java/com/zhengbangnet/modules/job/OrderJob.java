package com.zhengbangnet.modules.job;

import com.zhengbangnet.modules.service.OrdersService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderJob {

	@Resource(name="ordersServiceImpl")
	private OrdersService ordersService;

    /**
     * 取消订单
     */
	@Scheduled(cron="1/60 * * * * ? ")
	public void cancel(){
	    ordersService.batchCancel();
	}

    /**
     * 确认收货
     */
    @Scheduled(cron = "6/60 * * * * ? ")
    public void confirmReceive() {
        ordersService.batchConfirmReceive();
    }

    /**
     * 完成订单
     */
    @Scheduled(cron = "11/60 * * * * ? ")
    public void complete() {
        ordersService.batchComplete();
    }



}

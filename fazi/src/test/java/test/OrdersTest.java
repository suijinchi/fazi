package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zhengbangnet.modules.service.OrdersService;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
public class OrdersTest {
	
	@Resource(name="ordersServiceImpl")
	private OrdersService ordersService;
	
	@Test
	public void test(){
		//测试完成操作
		ordersService.memberComplete(232L);
		
	}
	
}

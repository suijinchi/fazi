package com.zhengbangnet.modules.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob {
	
//	@Scheduled(cron="* * 0/24 * * ? ")
	@Scheduled(cron="0 * 1 * * ? ")
	public void print(){
		System.out.println("TestJob is working....");
		
	}
	
}

package aaa.bbb.ccc;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleJob {

	@Scheduled(cron = "* * * * * *")
	public void getIamkay() {
		System.out.println("새로운 스케줄러 작동");
	}
	
}

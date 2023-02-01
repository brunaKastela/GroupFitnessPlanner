package progi;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import progi.service.ClientService;
import progi.service.ScheduleService;

@Component
public class ScheduledTasks {

    private ClientService clientService;

    private ScheduleService scheduleService;

    public ScheduledTasks(ClientService clientService, ScheduleService scheduleService) {
        this.clientService = clientService;
        this.scheduleService = scheduleService;
    }

    @Scheduled(cron = "0 0 0 1 * ?") // prvog u mjesecu u ponoc
    //@Scheduled(cron = "0 * * * * *") // jednom u minuti
    public void runMonthlyTasks() throws Exception{
        clientService.resetClientsWithNextGoal();

        scheduleService.updateTrainingSchedule();
    }

}

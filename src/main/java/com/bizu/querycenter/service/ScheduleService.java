package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Add.AddTriggerToSchedule;
import com.bizu.querycenter.dto.Request.SaveScheduleRequest;
import com.bizu.querycenter.dto.Response.ScheduleResponse;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.Schedule;
import com.bizu.querycenter.model.Trigger;
import com.bizu.querycenter.repository.ScheduleRepository;
import com.bizu.querycenter.repository.TriggerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TriggerRepository triggerRepository;
    private final TriggerService triggerService;

    public ScheduleService(ScheduleRepository scheduleRepository, TriggerRepository triggerRepository, TriggerService triggerService) {
        this.scheduleRepository = scheduleRepository;
        this.triggerRepository = triggerRepository;
        this.triggerService = triggerService;
    }

    public Schedule getScheduleById(Integer id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(RuntimeException::new);

        return schedule;
    }

    public List<Schedule> getAllSchedules(){
        List<Schedule> schedules = new ArrayList<>();
        scheduleRepository.findAll().forEach(schedules::add);

        return schedules;
    }

    public Schedule getScheduleByName(String scheduleName){
        Schedule schedule = scheduleRepository.findByName(scheduleName);

        return schedule;
    }

    public ScheduleResponse saveSchedule(SaveScheduleRequest request){

        List<Schedule> schedules = getAllSchedules();
        List<Report> reports = new ArrayList<>();
        int size = schedules.size() + 2;

        Schedule schedule = Schedule.builder()
                ._id(size)
                .name(request.getName())
                .mailSubject(request.getMailSubject())
                .recipients(request.getRecipients())
                .triggers(request.getTriggers())
                .reports(reports)
                .build();

        Schedule fromDB = scheduleRepository.save(schedule);

        return ScheduleResponse.builder()
                .name(fromDB.getName())
                .mailSubject(fromDB.getMailSubject())
                .recipients(fromDB.getRecipients())
                .triggers(fromDB.getTriggers())
                .build();
    }

    public ScheduleResponse updateSchedule(Integer id, SaveScheduleRequest request){
        Schedule currentSchedule = getScheduleById(id);
        currentSchedule.setName(request.getName());
        currentSchedule.setMailSubject(request.getMailSubject());
        currentSchedule.setRecipients(request.getRecipients());

        scheduleRepository.save(currentSchedule);

        return ScheduleResponse.builder()
                .name(currentSchedule.getName())
                .mailSubject(currentSchedule.getMailSubject())
                .recipients(currentSchedule.getRecipients())
                .build();
    }

    public ScheduleResponse addTriggerToSchedule(AddTriggerToSchedule scheduleDto){
        Schedule schedule = getScheduleById(scheduleDto.getScheduleId());
        Trigger trigger = triggerService.getTriggerById(scheduleDto.getTriggerId());
        List<Trigger> triggers = schedule.getTriggers();
        List<Schedule> schedules = trigger.getSchedules();

        triggers.add(trigger);
        schedule.setTriggers(triggers);

        schedules.add(schedule);
        trigger.setSchedules(schedules);

        scheduleRepository.save(schedule);
        triggerRepository.save(trigger);

        return ScheduleResponse.builder()
                .name(schedule.getName())
                .mailSubject(schedule.getMailSubject())
                .recipients(schedule.getRecipients())
                .triggers(schedule.getTriggers())
                .build();
    }

    public void deleteScheduleById(Integer id){
        if(doesScheduleExist(id)){
            scheduleRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Schedule does not exist");
        }
    }

    private boolean doesScheduleExist(Integer id){
        return scheduleRepository.existsById(id);
    }
}

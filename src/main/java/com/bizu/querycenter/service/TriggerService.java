package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Add.AddNameToTrigger;
import com.bizu.querycenter.dto.Add.AddSubjectToSchedule;
import com.bizu.querycenter.dto.Request.SaveTriggerRequest;
import com.bizu.querycenter.dto.Response.ScheduleResponse;
import com.bizu.querycenter.dto.Response.TriggerResponse;
import com.bizu.querycenter.model.Schedule;
import com.bizu.querycenter.model.Trigger;
import com.bizu.querycenter.repository.TriggerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TriggerService {

    private final TriggerRepository triggerRepository;

    public TriggerService(TriggerRepository triggerRepository) {
        this.triggerRepository = triggerRepository;
    }

    public Trigger getTriggerById(Integer id){
        Trigger trigger = triggerRepository.findById(id).orElseThrow(RuntimeException::new);

        return trigger;
    }

    public List<Trigger> getAllTriggers(){
        List<Trigger> triggers = new ArrayList<>();
        triggerRepository.findAll().forEach(triggers::add);

        return triggers;
    }

    public Trigger getTriggerByName(String triggerName){
        Trigger trigger = triggerRepository.findByName(triggerName);

        return trigger;
    }

    public TriggerResponse saveTrigger(SaveTriggerRequest request){
        List<Trigger> triggers = getAllTriggers();

        int size = triggers.size() + 2;

        Trigger trigger = Trigger.builder()
                ._id(size)
                .name(request.getName())
                .schedules(request.getSchedules())
                .build();

        Trigger fromDB = triggerRepository.save(trigger);

        return TriggerResponse.builder()
                .name(fromDB.getName())
                .build();
    }

    public TriggerResponse addName(Integer triggerId, AddNameToTrigger name){
        Trigger t1 = getTriggerById(triggerId);

        t1.setName(name.getName());
        triggerRepository.save(t1);

        return TriggerResponse.builder()
                .name(t1.getName())
                .build();
    }

    public TriggerResponse updateTrigger(Integer id, SaveTriggerRequest request){
        Trigger currentTrigger = getTriggerById(id);
        currentTrigger.setName(request.getName());
        currentTrigger.setSchedules(request.getSchedules());

        triggerRepository.save(currentTrigger);

        return TriggerResponse.builder()
                .name(currentTrigger.getName())
                .build();
    }

    public void deleteTriggerById(Integer id){
        if(doesTriggerExist(id)){
            triggerRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Trigger does not exist");
        }
    }

    private boolean doesTriggerExist(Integer id){
        return triggerRepository.existsById(id);
    }
}

package com.bizu.querycenter.service;

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
}

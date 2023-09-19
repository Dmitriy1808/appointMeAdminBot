package com.example.appointMeAdmin.service;

import com.example.appointMeAdmin.model.WorkTime;
import com.example.appointMeAdmin.repository.WorkTimeRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WorkTimeService {

    private final WorkTimeRepository workTimeRepository;

    public WorkTimeService(WorkTimeRepository workTimeRepository) {
        this.workTimeRepository = workTimeRepository;
    }

    public List<WorkTime> getFreeWorkTimeByStartDate(Date startDate) {
        return workTimeRepository.findByIsFreeTrueAndStartWorkTimeBetween(startDate, DateUtils.addDays(startDate, 1));
    }

    public void setWorkTime(WorkTime workTime) {
        workTimeRepository.save(workTime);
    }
}

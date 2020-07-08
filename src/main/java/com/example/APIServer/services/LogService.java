package com.example.APIServer.services;


import com.example.APIServer.NotFoundException;
import com.example.APIServer.entities.LogEntity;
import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.entities.UserEntity;
import com.example.APIServer.models.LogModel;
import com.example.APIServer.repositories.StatusRepository;
import com.example.APIServer.repositories.UserRepository;
import com.example.APIServer.repositories.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LogService implements ITemplateService<LogModel, Integer> {

    private final LogRepository logRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    @Override
    public List<LogModel> getAll()
    {
        List<LogEntity> logList = logRepository.findAll();
        List<LogModel> logModels = new ArrayList<>();

        for (LogEntity log : logList)
        {
            logModels.add(new LogModel(
                    log.getLogId(),
                    log.getUser().getUserId(),
                    log.getChangedTime(),
                    log.getStatus().getStatusValue()
            ));
        }
        return logModels;
    }

    @Override
    public LogModel findById(Integer integer)
    {
        LogEntity log = logRepository.findById(integer).orElseThrow(() -> new NotFoundException("Log with ID " + integer + " is not exists"));
        return new LogModel(
                log.getLogId(),
                log.getUser().getUserId(),
                log.getChangedTime(),
                log.getStatus().getStatusValue()
        );
    }

    @Override
    public Integer create(LogModel logModel)
    {
        UserEntity user = userRepository.findById(logModel.getUserId()).orElseThrow(() -> new NotFoundException("User with id " + logModel.getUserId() + " is not exists"));
        LogEntity log = new LogEntity(user, statusRepository.findByStatus(logModel.getNewStatus().toLowerCase()).orElseGet(() -> {
                    return statusRepository.save(new StatusEntity(logModel.getNewStatus()));
                }), System.currentTimeMillis());

        logRepository.save(log);
        return log.getLogId();
    }

    public List<LogModel> getAllByStatusAndTimestamp(long time, String statusValue)
    {
        List<LogModel> logModelList = new ArrayList<>();
        List<LogEntity> logList = logRepository.findAllByTimeAndStatus(time, statusValue);

        for (LogEntity log : logList)
        {
            logModelList.add(new LogModel(
                    log.getLogId(),
                    log.getUser().getUserId(),
                    log.getChangedTime(),
                    log.getStatus().getStatusValue()));
        }
        return  logModelList;
    }
}

package com.example.APIServer;

import com.example.APIServer.entities.LogEntity;
import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.entities.UserEntity;
import com.example.APIServer.models.LogModel;
import com.example.APIServer.models.UserModel;
import com.example.APIServer.repositories.LogRepository;
import com.example.APIServer.repositories.UserRepository;
import com.example.APIServer.services.LogService;
import com.example.APIServer.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(LogService.class)
public class Tests
{
    @MockBean
    private LogRepository logRepository;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @Test
    public void returningExistingLogModelById()
    {
        int ID = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        long time = System.currentTimeMillis();

        final UserEntity user = new UserEntity(2, "", "", new StatusEntity(""));
        final LogEntity log = new LogEntity(ID, user, user.getStatus(), time);

        Mockito.when(logRepository.findById(ID)).thenReturn(Optional.of(log));

        final LogModel logModel = logService.findById(ID);

        Assertions.assertEquals(logModel, new LogModel(ID, 2, time, ""));

        Mockito.verify(logRepository).findById(ID);
        Mockito.verifyNoMoreInteractions(logRepository);
    }

    @Test
    public void returningExistingUserModelById()
    {
        int ID = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        final UserEntity user = new UserEntity(ID, "", "", new StatusEntity(""));

        Mockito.when(userRepository.findById(ID)).thenReturn(Optional.of(user));

        UserModel userModel = userService.findById(ID);
        Assertions.assertEquals(userModel, new UserModel(ID, "", "", ""));

        Mockito.verify(userRepository).findById(ID);
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void throwingNotFoundExceptionWhenNoLogId()
    {
        Mockito.when(logRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        int ID = ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE);
        try
        {
            logService.findById(ID);
            Assertions.fail("no exception");
        } catch (Exception e)
        {
            Assertions.assertEquals(NotFoundException.class, e.getClass(), "wrong exception");
            Assertions.assertEquals("Log with id " + ID + " not found", e.getMessage());
        }

        Mockito.verify(logRepository).findById(Mockito.eq(ID));
        Mockito.verifyNoMoreInteractions(logRepository);
    }
}


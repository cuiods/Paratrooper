package edu.tsinghua.paratrooper.bl.schedule;

import edu.tsinghua.paratrooper.bl.service.SoldierService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class Initializer implements ApplicationRunner {

    @Resource
    private SoldierService soldierService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        soldierService.initializeSoldierStatus();
    }
}

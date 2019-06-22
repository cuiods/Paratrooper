package edu.tsinghua.paratrooper.bl.serviceimpl;

import com.google.common.collect.Lists;
import edu.tsinghua.paratrooper.bl.service.ScheduleService;
import edu.tsinghua.paratrooper.data.repository.SoldierRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private SoldierRepository soldierRepository;

    @Override
    @Scheduled(cron = "0 0/2 * * * ?")
    public void updateSoldierStatus() {
        Lists.newArrayList(soldierRepository.findAll()).forEach(tSoldierEntity -> {
            tSoldierEntity.setAlive(0);
            soldierRepository.save(tSoldierEntity);
        });
    }
}

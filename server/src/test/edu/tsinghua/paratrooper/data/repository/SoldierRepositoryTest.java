package edu.tsinghua.paratrooper.data.repository;

import edu.tsinghua.paratrooper.BaseTest;
import edu.tsinghua.paratrooper.data.entity.TSoldierEntity;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class SoldierRepositoryTest extends BaseTest {

    @Resource
    private SoldierRepository soldierRepository;

    @Test
    public void testGet() {
        TSoldierEntity entity = soldierRepository.findOne(1);
        System.out.println(entity.getName());
    }

}
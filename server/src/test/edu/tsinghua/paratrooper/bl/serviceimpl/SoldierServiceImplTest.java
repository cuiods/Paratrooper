package edu.tsinghua.paratrooper.bl.serviceimpl;

import edu.tsinghua.paratrooper.BaseTest;
import edu.tsinghua.paratrooper.bl.service.SoldierService;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class SoldierServiceImplTest extends BaseTest {

    @Resource
    private SoldierService soldierService;

    @Test
    public void initializeSoldierStatus() {
    }

    @Test
    @WithMockUser(username="000001",roles={"SOLDIER"})
    public void registerPublicKey() {
    }

    @Test
    public void getPublicKeys() {
    }

    @Test
    public void updateStatus() {
    }
}
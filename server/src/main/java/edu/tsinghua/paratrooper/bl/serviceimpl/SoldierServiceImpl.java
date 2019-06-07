package edu.tsinghua.paratrooper.bl.serviceimpl;

import com.google.common.collect.Lists;
import edu.tsinghua.paratrooper.bl.service.SoldierService;
import edu.tsinghua.paratrooper.bl.vo.*;
import edu.tsinghua.paratrooper.data.entity.TSoldierEntity;
import edu.tsinghua.paratrooper.data.repository.BoxRepository;
import edu.tsinghua.paratrooper.data.repository.MsgRepository;
import edu.tsinghua.paratrooper.data.repository.SoldierRepository;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import edu.tsinghua.paratrooper.web.security.AppContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoldierServiceImpl implements SoldierService {

    @Resource
    private SoldierRepository soldierRepository;

    @Resource
    private MsgRepository msgRepository;

    @Resource
    private BoxRepository boxRepository;

    /**
     * Initialize soldier location and box status
     */
    @Override
    @Transactional
    public void initializeSoldierStatus() {
        msgRepository.deleteAll();
        List<TSoldierEntity> soldierEntities = Lists.newArrayList(soldierRepository.findAll());
        soldierRepository.save(soldierEntities.stream().map(TSoldierEntity::reset).collect(Collectors.toList()));
        boxRepository.deleteAll();
    }

    /**
     * Register public key
     *
     * @param key key
     * @return public key
     */
    @Override
    public ResultVo<String> registerPublicKey(String key) {
        int currentUserId = AppContext.getCurrentUserId();
        if (currentUserId == 0)
            return new ResultVo<>(ErrorCode.NO_AUTHORITY, "Please Login First","");
        TSoldierEntity entity = soldierRepository.findOne(currentUserId);
        entity.setPublicKey(key);
        soldierRepository.save(entity);
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", key);
    }

    /**
     * Get all public keys
     *
     * @return list of {@link PublicKeyVo}
     */
    @Override
    public ResultVo<List<PublicKeyVo>> getPublicKeys() {
        List<PublicKeyVo> list = Lists.newArrayList(soldierRepository.findAll()).stream()
                .map(PublicKeyVo::new).collect(Collectors.toList());
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", list);
    }

    /**
     * Update soldier status
     *
     * @param x x location
     * @param y y location
     * @return {@link StatusVo}
     */
    @Override
    public ResultVo<StatusVo> updateStatus(int x, int y) {
        int currentUserId = AppContext.getCurrentUserId();
        if (currentUserId == 0)
            return new ResultVo<>(ErrorCode.NO_AUTHORITY, "Please Login First",null);
        TSoldierEntity entity = soldierRepository.findOne(currentUserId);
        entity.setAlive(1);
        entity.setLocationX(x);
        entity.setLocationY(y);

        List<MsgVo> msgVos = new ArrayList<>();
        if (entity.getUpdateStatus() != 0) {
            msgVos = msgRepository.findByReceiveIdAndIsRead(currentUserId, 0)
                    .stream().map(entity1 -> {
                        entity1.setIsRead(1);
                        msgRepository.save(entity1);
                        return new MsgVo(entity1);
                    }).collect(Collectors.toList());
        }

        entity.setUpdateStatus(0);
        soldierRepository.save(entity);

        List<SoldierVo> soldierVos = soldierRepository.findByGroupNum(entity.getGroupNum())
                .stream().map(SoldierVo::new).collect(Collectors.toList());
        List<BoxVo> boxVos = Lists.newArrayList(boxRepository.findAll())
                .stream().map(BoxVo::new).collect(Collectors.toList());

        StatusVo statusVo = new StatusVo();
        statusVo.setMe(new SoldierVo(entity));
        statusVo.setMessages(msgVos);
        statusVo.setGroupMembers(soldierVos);
        statusVo.setBoxes(boxVos);
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", statusVo);
    }
}

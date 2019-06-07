package edu.tsinghua.paratrooper.bl.serviceimpl;

import edu.tsinghua.paratrooper.bl.service.MsgService;
import edu.tsinghua.paratrooper.bl.vo.MsgVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.data.entity.TMsgEntity;
import edu.tsinghua.paratrooper.data.entity.TSoldierEntity;
import edu.tsinghua.paratrooper.data.entity.TUserEntity;
import edu.tsinghua.paratrooper.data.repository.MsgRepository;
import edu.tsinghua.paratrooper.data.repository.SoldierRepository;
import edu.tsinghua.paratrooper.data.repository.UserRepository;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import edu.tsinghua.paratrooper.web.security.AppContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MsgServiceImpl implements MsgService {

    @Resource
    private MsgRepository msgRepository;

    @Resource
    private SoldierRepository soldierRepository;

    @Override
    public ResultVo<MsgVo> sendMsg(int reveiveId, int code, String message) {
        int currentUserId = AppContext.getCurrentUserId();
        if (currentUserId == 0)
            return new ResultVo<>(ErrorCode.NO_AUTHORITY, "Please Login First",null);
        TMsgEntity entity = new TMsgEntity();
        entity.setCode(code);
        entity.setData(message);
        entity.setIsRead(0);
        entity.setReceiveId(reveiveId);
        entity.setSendId(currentUserId);
        TMsgEntity saved = msgRepository.save(entity);
        TSoldierEntity soldierEntity = soldierRepository.findOne(reveiveId);
        soldierEntity.setUpdateStatus(1);
        soldierRepository.save(soldierEntity);
        MsgVo msgVo = new MsgVo(msgRepository.findOne(saved.getId()));
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", msgVo);
    }
}

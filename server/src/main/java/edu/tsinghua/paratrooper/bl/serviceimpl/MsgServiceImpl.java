package edu.tsinghua.paratrooper.bl.serviceimpl;

import com.google.gson.Gson;
import edu.tsinghua.paratrooper.bl.service.MsgService;
import edu.tsinghua.paratrooper.bl.vo.BoxVo;
import edu.tsinghua.paratrooper.bl.vo.MsgVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.bl.vo.SoldierVo;
import edu.tsinghua.paratrooper.data.entity.*;
import edu.tsinghua.paratrooper.data.repository.*;
import edu.tsinghua.paratrooper.util.constant.ErrorCode;
import edu.tsinghua.paratrooper.util.enums.MsgMethod;
import edu.tsinghua.paratrooper.util.lagrange.Lagrange;
import edu.tsinghua.paratrooper.util.lagrange.MyInteger;
import edu.tsinghua.paratrooper.web.security.AppContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsgServiceImpl implements MsgService {

    @Resource
    private MsgRepository msgRepository;

    @Resource
    private SoldierRepository soldierRepository;

    @Resource
    private BoxRepository boxRepository;

    @Resource
    private ApplyRepository applyRepository;

    @Resource
    private Lagrange lagrange;

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

    @Override
    public ResultVo<String> confirmAuthentication(int confirmedId) {
        int currentUserId = AppContext.getCurrentUserId();
        if (currentUserId == 0)
            return new ResultVo<>(ErrorCode.NO_AUTHORITY, "Please Login First",null);
        TSoldierEntity currentEntity = soldierRepository.findOne(currentUserId);
        TSoldierEntity confirmedEntity = soldierRepository.findOne(confirmedId);
        if (currentEntity == null || confirmedEntity == null) {
            return new ResultVo<>(ErrorCode.USER_CANNOT_FIND, "User cannot find", "id:"+confirmedId);
        }
        TSoldierEntity currentCap = soldierRepository.findByGroupNumAndCaptain(currentEntity.getGroupNum(),1);
        TSoldierEntity confirmedCap = soldierRepository.findByGroupNumAndCaptain(confirmedEntity.getGroupNum(), 1);
        Gson gson = new Gson();
        sendMsg(currentCap.getId(),MsgMethod.CONFIRM.getCode(),gson.toJson(new SoldierVo(confirmedCap, true)));
        return new ResultVo<>(ErrorCode.SUCCESS,"ok", currentCap.getName());
    }

    @Override
    @Transactional
    public ResultVo<String> confirmCaptain(int compareId, int result) {
        //Verify param
        int currentUserId = AppContext.getCurrentUserId();
        if (currentUserId == 0)
            return new ResultVo<>(ErrorCode.NO_AUTHORITY, "Please Login First",null);
        TSoldierEntity currentEntity = soldierRepository.findOne(currentUserId);
        TSoldierEntity comparedEntity = soldierRepository.findOne(compareId);
        if (currentEntity == null || comparedEntity == null) {
            return new ResultVo<>(ErrorCode.USER_CANNOT_FIND, "User cannot find", "id:"+compareId);
        }
        if (currentEntity.getCaptain()==0 || comparedEntity.getCaptain()==0 || result==0) {
            return new ResultVo<>(ErrorCode.NO_AUTHORITY, "Invalid param", "");
        }

        //Change Captain
        TSoldierEntity modify = comparedEntity;
        if (result < 0) {
            modify = currentEntity;
        }
        modify.setCaptain(0);
        Gson gson = new Gson();
        sendMsg(modify.getId(),MsgMethod.CAPTAIN.getCode(),gson.toJson(new SoldierVo(modify, true)));

        //merge group
        int originGroup = Math.max(currentEntity.getGroupNum(), comparedEntity.getGroupNum());
        int updateGroup = Math.min(currentEntity.getGroupNum(), comparedEntity.getGroupNum());
        soldierRepository.updateGroupNum(originGroup, updateGroup);
        modify.setGroupNum(updateGroup);
        soldierRepository.save(modify);

        return new ResultVo<>(ErrorCode.SUCCESS, "ok", "");
    }

    @Override
    public ResultVo<BoxVo> applyBox(int boxId, String key) {
        //Verify param
        int currentUserId = AppContext.getCurrentUserId();
        if (currentUserId == 0)
            return new ResultVo<>(ErrorCode.NO_AUTHORITY, "Please Login First",null);
        TBoxEntity entity = boxRepository.findOne(boxId);
        if (entity == null)
            return new ResultVo<>(ErrorCode.BOX_CANNOT_FIND, "Cannot find this box", null);
        TBoxApplyEntity applyEntity = applyRepository.findByUserIdAndBoxId(currentUserId, boxId);
        if (applyEntity != null)
            return new ResultVo<>(ErrorCode.BOX_REPEAT_APPLY, "You have applied this box.", null);
        //open box
        applyRepository.save(new TBoxApplyEntity(0, boxId, currentUserId, key));
        entity.setApply(entity.getApply()+1);
        if (entity.getApply()>=entity.getTotal()) {
            List<TBoxApplyEntity> boxApplyEntities = applyRepository.findByBoxId(boxId);
            Map<Integer, MyInteger> inputMap = new HashMap<>();
            for (TBoxApplyEntity box: boxApplyEntities) {
                inputMap.put(box.getUserId(), new MyInteger(box.getKey()));
            }
            boolean result = lagrange.verification(inputMap);
            if (result) {
                entity.setStatus(1);
                Gson gson = new Gson();
                for (TBoxApplyEntity box: boxApplyEntities) {
                    sendMsg(box.getUserId(), MsgMethod.BOXOPEN.getCode(), gson.toJson(new BoxVo(entity)));
                }
            }
        }
        boxRepository.save(entity);
        entity.setSoldierEntities(null);
        return new ResultVo<>(ErrorCode.SUCCESS, "ok", new BoxVo(entity));
    }
}

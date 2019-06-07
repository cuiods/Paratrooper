package edu.tsinghua.paratrooper.bl.vo;

import lombok.Data;

import java.util.List;

@Data
public class StatusVo {
    private SoldierVo me;
    private List<MsgVo> messages;
    private List<SoldierVo> groupMembers;
    private List<BoxVo> boxes;
    private List<SoldierVo> inVisionSoldiers;
}

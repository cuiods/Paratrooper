package edu.tsinghua.paratrooper.bl.service;

import edu.tsinghua.paratrooper.bl.vo.MsgVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;

public interface MsgService {

    ResultVo<MsgVo> sendMsg(int reveiveId, int code, String message);

}

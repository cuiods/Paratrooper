package edu.tsinghua.paratrooper.bl.service;

import edu.tsinghua.paratrooper.bl.vo.PublicKeyVo;
import edu.tsinghua.paratrooper.bl.vo.ResultVo;
import edu.tsinghua.paratrooper.bl.vo.StatusVo;

import java.util.List;

public interface SoldierService {

    /**
     * Initialize soldier location and box status
     */
    void initializeSoldierStatus();

    /**
     * Register public key
     * @param key key
     * @return public key
     */
    ResultVo<String> registerPublicKey(String key);

    /**
     * Get all public keys
     * @return list of {@link PublicKeyVo}
     */
    ResultVo<List<PublicKeyVo>> getPublicKeys();

    /**
     * Update status
     * @return {@link StatusVo}
     */
    ResultVo<StatusVo> updateStatus(int x, int y);

}

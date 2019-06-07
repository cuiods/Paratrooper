package edu.tsinghua.paratrooper.bl.vo;

import edu.tsinghua.paratrooper.data.entity.TSoldierEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PublicKeyVo {
    private int id;
    private String name;
    private String publicKey;

    public PublicKeyVo(TSoldierEntity soldierEntity) {
        this.id = soldierEntity.getId();
        this.name = soldierEntity.getName();
        this.publicKey = soldierEntity.getPublicKey();
    }

}

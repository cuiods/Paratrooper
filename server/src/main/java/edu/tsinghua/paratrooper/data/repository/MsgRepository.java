package edu.tsinghua.paratrooper.data.repository;

import edu.tsinghua.paratrooper.data.entity.TMsgEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MsgRepository extends CrudRepository<TMsgEntity, Integer> {

    List<TMsgEntity> findByReceiveIdAndIsRead(int recieveId, int isRead);

}

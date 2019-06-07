package edu.tsinghua.paratrooper.data.repository;

import edu.tsinghua.paratrooper.data.entity.TSoldierEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SoldierRepository extends CrudRepository<TSoldierEntity, Integer> {

    List<TSoldierEntity> findByGroupNumAndIdNot(int groupNum, int id);

}

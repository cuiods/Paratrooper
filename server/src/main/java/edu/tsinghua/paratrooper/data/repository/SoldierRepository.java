package edu.tsinghua.paratrooper.data.repository;

import edu.tsinghua.paratrooper.data.entity.TSoldierEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SoldierRepository extends CrudRepository<TSoldierEntity, Integer> {

    List<TSoldierEntity> findByGroupNumAndIdNot(int groupNum, int id);

    List<TSoldierEntity> findByGroupNum(int groupNum);

    TSoldierEntity findByGroupNumAndCaptain(int groupNum, int isCaptain);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE t_soldier SET group_num=:updateGroup WHERE group_num=:originGroup")
    void updateGroupNum(@Param("originGroup") int originGroup, @Param("updateGroup") int updateGroup);

}

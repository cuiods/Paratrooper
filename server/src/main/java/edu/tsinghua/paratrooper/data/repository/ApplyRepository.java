package edu.tsinghua.paratrooper.data.repository;

import edu.tsinghua.paratrooper.data.entity.TBoxApplyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplyRepository extends CrudRepository<TBoxApplyEntity, Integer> {

    TBoxApplyEntity findByUserIdAndBoxId(int userId, int boxId);

    List<TBoxApplyEntity> findByBoxId(int boxId);

}

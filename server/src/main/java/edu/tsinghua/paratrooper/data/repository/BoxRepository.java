package edu.tsinghua.paratrooper.data.repository;

import edu.tsinghua.paratrooper.data.entity.TBoxEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoxRepository extends CrudRepository<TBoxEntity, Integer> {
}

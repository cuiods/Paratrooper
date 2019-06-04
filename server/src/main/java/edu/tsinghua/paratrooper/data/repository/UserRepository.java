package edu.tsinghua.paratrooper.data.repository;

import edu.tsinghua.paratrooper.data.entity.TUserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<TUserEntity, Integer> {

    TUserEntity findById(int id);

    TUserEntity findByName(String name);

    TUserEntity findByNameAndPassword(String name, String password);

}

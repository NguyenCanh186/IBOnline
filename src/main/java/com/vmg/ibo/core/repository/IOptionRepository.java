package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOptionRepository extends JpaRepository<Option, Long> {

    @Query(value = "select o " +
            "from Option o " +
            "where (:key is null or o.key like %:key%)")
    Page<Option> findAllOptions(@Param("key") String key, Pageable pageable);

    List<Option> findAllByGroup(String group);

    List<Option> findAllByKey(String key);

    List<Option> findAllByKeyAndGroup(String key, String group);

    List<Option> findAllByValue(String value);

    List<Option> findAllByValueAndGroup(String value, String group);
}

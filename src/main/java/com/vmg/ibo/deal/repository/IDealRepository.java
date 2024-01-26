package com.vmg.ibo.deal.repository;

import com.vmg.ibo.deal.model.dto.DealResponse;
import com.vmg.ibo.deal.model.dto.DealFilter;
import com.vmg.ibo.deal.model.entity.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDealRepository extends JpaRepository<Deal, Long> {
    @Query(value = "SELECT c.code FROM Deal c")
    List<String> getAllCode();

    @Query(value = "SELECT d.id as id, d.code as code, udf.businessName as firstCompany, uds.businessName as secondCompany," +
            "udf.contactName as firstContact, uds.contactName as secondContact, " +
            "udf.isCustomerPersonal as firstType, uds.isCustomerPersonal as secondType, " +
            "tf.name as firstForm, ts.name as secondForm, " +
            "u.name as coordinator, d.connectionDate as connectionDate, " +
            "d.status as status " +
            "FROM Deal d " +
            "LEFT JOIN Form ff on d.first.id = ff.id LEFT JOIN Template tf on ff.template.id = tf.id left join UserDetail udf on ff.user.id = udf.idUser " +
            "LEFT JOIN Form fs on d.second.id = fs.id LEFT JOIN Template ts on fs.template.id = ts.id left join UserDetail uds on fs.user.id = uds.idUser " +
            "LEFT JOIN User u on d.coordinator.id = u.id " +
            "WHERE ((:#{#filter.query} is null OR :#{#filter.query} = '') OR (d.code LIKE CONCAT('%', :#{#filter.query}, '%') OR tf.name LIKE CONCAT('%', :#{#filter.query}, '%') OR ts.name LIKE CONCAT('%', :#{#filter.query}, '%') OR u.name LIKE CONCAT('%', :#{#filter.query}, '%'))) " +
            "AND (:#{#filter.firstForm} is null OR :#{#filter.firstForm} = '' OR tf.name LIKE CONCAT('%', :#{#filter.firstForm}, '%')) " +
            "AND (:#{#filter.secondForm} is null OR :#{#filter.secondForm} = '' OR ts.name LIKE CONCAT('%', :#{#filter.secondForm}, '%')) " +
            "AND (:#{#filter.coordinator} is null OR :#{#filter.coordinator} = '' OR u.name LIKE CONCAT('%', :#{#filter.coordinator}, '%')) " +
            "AND (:#{#filter.status} is null OR d.status = :#{#filter.status}) " +
            "AND (:#{#filter.date} is null OR :#{#filter.date} = '' OR DATE(d.connectionDate) = COALESCE(DATE(:#{#filter.date}), DATE(d.connectionDate)))")
    Page<DealResponse> getAllDeals(DealFilter filter, Pageable pageable);

}

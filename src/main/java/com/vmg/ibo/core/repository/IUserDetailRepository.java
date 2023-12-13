package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.dto.UserDetailWithUserDTO;
import com.vmg.ibo.core.model.entity.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserDetailRepository extends JpaRepository<UserDetail, Long> {

    @Query(value = "SELECT u.id as id, u.createdAt as createdAt, ud.customerCode as customerCode, ud.isCustomerPersonal as isCustomerPersonal, " +
            "ud.address as address, ud.CINumber as CINumber, ud.dateOfBirth as dateOfBirth, ud.businessName as businessName, ud.codeTax as codeTax, ud.codeReg as codeReg, " +
            "ud.contactName as contactName, ud.mainBusiness as mainBusiness, ud.title as title, ud.capitalSize as capitalSize, ud.description as description, " +
            "u.status as status, u.email as email, u.phone as phone FROM User u " +
            "JOIN UserDetail ud ON u.id = ud.idUser " +
            "WHERE (u.isAdminRoot = false OR u.isAdminRoot IS NULL) and (:isCustomerPersonal = ud.isCustomerPersonal OR :isCustomerPersonal IS NULL) " +
            "AND (:status = u.status OR :status IS NULL) " +
            "AND ((ud.capitalSize BETWEEN :fromCapitalSize AND :toCapitalSize) OR (:fromCapitalSize IS NULL AND :toCapitalSize IS NULL)) " +
            "AND ((LOWER(ud.contactName) LIKE LOWER(CONCAT('%', :contactName, '%')) OR :contactName IS NULL) or (LOWER(ud.businessName) LIKE LOWER(CONCAT('%', :contactName, '%')) OR :contactName IS NULL)) ")
    Page<UserDetailWithUserDTO> findAllUser(Pageable pageable,
                                            @Param("contactName") String contactName,
                                            @Param("isCustomerPersonal") Boolean isCustomerPersonal,
                                            @Param("status") Integer status,
                                            @Param("fromCapitalSize") Long fromCapitalSize,
                                            @Param("toCapitalSize") Long toCapitalSize);

    @Query(value = "SELECT u.id as id, u.createdAt as createdAt, ud.customerCode as customerCode, ud.isCustomerPersonal as isCustomerPersonal, " +
            "ud.address as address, ud.CINumber as CINumber, ud.dateOfBirth as dateOfBirth, ud.businessName as businessName, ud.codeTax as codeTax, ud.codeReg as codeReg, " +
            "ud.contactName as contactName, ud.mainBusiness as mainBusiness, ud.title as title, ud.capitalSize as capitalSize, ud.description as description, " +
            "u.status as status, u.email as email, u.phone as phone FROM User u " +
            "JOIN UserDetail ud ON u.id = ud.idUser " +
            "WHERE u.id = :id")
    UserDetailWithUserDTO findUserById(Long id);
}

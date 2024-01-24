package com.vmg.ibo.customer.repository;

import com.vmg.ibo.customer.model.UserDetailWithUserDTO;
import com.vmg.ibo.customer.model.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUserDetailRepository extends JpaRepository<UserDetail, Long> {

    @Query(value = "SELECT u.id as id, u.createdAt as createdAt, ud.customerCode as customerCode, ud.isCustomerPersonal as isCustomerPersonal, " +
            "ud.address as address, ud.CINumber as CINumber, ud.dateOfBirth as dateOfBirth, ud.businessName as businessName, ud.codeTax as codeTax, ud.codeReg as codeReg, " +
            "ud.contactName as contactName, ud.mainBusiness as mainBusiness, ud.title as title, ud.capitalSize as capitalSize, ud.description as description, " +
            "u.status as status, u.email as email, u.phone as phone, ud.mostRecentYearRevenue as mostRecentYearRevenue, ud.mostRecentYearProfit as mostRecentYearProfit," +
            "ud.propertyStructure as propertyStructure, ud.debtStructure as debtStructure, u.name as name, count(f.template.id) as quantityNeeded FROM User u " +
            " join UserDetail ud ON u.id = ud.idUser left join Form f on u.id = f.user.id " +
            "WHERE (u.isAdminRoot = false OR u.isAdminRoot IS NULL) and (:isCustomerPersonal = ud.isCustomerPersonal OR :isCustomerPersonal IS NULL) " +
            "AND (:status = u.status OR :status IS NULL) AND (LOWER(ud.contactName) LIKE LOWER(CONCAT('%', :username, '%')))" +
            "AND (ud.capitalSize is null or (ud.capitalSize BETWEEN :fromCapitalSize AND :toCapitalSize) OR (:fromCapitalSize IS NULL AND :toCapitalSize IS NULL)) " +
            "AND ((LOWER(ud.contactName) LIKE LOWER(CONCAT('%', :contactName, '%')) OR :contactName IS NULL) or (LOWER(ud.businessName) LIKE LOWER(CONCAT('%', :contactName, '%')) OR :contactName IS NULL) " +
            "or (LOWER(u.email) LIKE LOWER(CONCAT('%', :contactName, '%')) OR :contactName IS NULL) or (LOWER(u.phone) LIKE LOWER(CONCAT('%', :contactName, '%')) OR :contactName IS NULL) " +
            "or (LOWER(ud.customerCode) LIKE LOWER(CONCAT('%', :contactName, '%')) OR :contactName IS NULL)) group by f.user.id, u.name, ud.debtStructure, ud.propertyStructure," +
            " ud.mostRecentYearProfit, ud.mostRecentYearRevenue, u.phone, u.email," +
            " u.status, ud.description, ud.capitalSize, ud.title, ud.businessName, ud.contactName" +
            ", ud.codeTax, ud.codeReg, ud.dateOfBirth, ud.CINumber, ud.address, ud.isCustomerPersonal," +
            " ud.customerCode, u.createdAt, u.id, ud.mainBusiness")
    Page<UserDetailWithUserDTO> findAllUser(Pageable pageable,
                                            @Param("username") String username,
                                            @Param("contactName") String contactName,
                                            @Param("isCustomerPersonal") Boolean isCustomerPersonal,
                                            @Param("status") Integer status,
                                            @Param("fromCapitalSize") Long fromCapitalSize,
                                            @Param("toCapitalSize") Long toCapitalSize);

    @Query(value = "SELECT u.id as id, u.createdAt as createdAt, ud.customerCode as customerCode, ud.isCustomerPersonal as isCustomerPersonal, " +
            "ud.address as address, ud.CINumber as CINumber, ud.dateOfBirth as dateOfBirth, ud.businessName as businessName, ud.codeTax as codeTax, ud.codeReg as codeReg, " +
            "ud.contactName as contactName, ud.mainBusiness as mainBusiness, ud.title as title, ud.capitalSize as capitalSize, ud.description as description, " +
            "u.status as status, u.email as email, u.phone as phone, ud.mostRecentYearRevenue as mostRecentYearRevenue, ud.mostRecentYearProfit as mostRecentYearProfit," +
            "ud.propertyStructure as propertyStructure, ud.debtStructure as debtStructure, u.name as name FROM User u " +
            "JOIN UserDetail ud ON u.id = ud.idUser " +
            "WHERE u.id = :id")
    UserDetailWithUserDTO findUserById(Long id);
    @Query(value = "SELECT ud FROM UserDetail ud WHERE ud.idUser = :idUser")
    UserDetail findByIdUser(@Param("idUser") Long idUser);
    @Query(value = "SELECT ud.customerCode as customerCode FROM UserDetail ud where ud.customerCode != null")
    List<String> getAllCustomerCode();

    @Query(value = "SELECT u.username FROM User u")
    List<String> getAllUsername();
}

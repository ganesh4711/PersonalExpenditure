package com.project.repos;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.project.model.Expenditures;

import jakarta.websocket.server.PathParam;

public interface ExpenditureRepo extends JpaRepository<Expenditures, Integer> {

	@Query("SELECT e FROM Expenditures e WHERE e.payment.user.uname = :uname ORDER BY e.amount DESC")
	List<Expenditures> findTop3ExpensesByUser(@Param("uname") String uname, PageRequest pageable);

	@Query(value = "SELECT c.catcode, MONTH(e.spentOn) AS month, SUM(e.amount) AS total_amount FROM expenditure e "
			+ "JOIN category c ON e.catcode = c.catcode WHERE e.uname=:uname and MONTH(e.spentOn) = :month "
			+ "GROUP BY c.catcode, MONTH(e.spentOn)", nativeQuery = true)
	List<Object[]> getTotalAmtByCategoryAndMonth(@Param("month") int month,@Param("uname") String uname);

	List<Expenditures> findByPayment_User_UnameAndTagsContaining(String user, String tag);

	@Query(value = "select * from expenditure e where e.uname=:uname and e.spenton >=:startDate and e.spenton <=:endDate ORDER BY e.spenton DESC", nativeQuery = true)
	List<Expenditures> getExpensesByDates(@Param("uname") String uname, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	List<Expenditures> findByPayment_User_UnameAndPayment_Code(String uname, String code, PageRequest of);

	List<Expenditures> findByPayment_User_UnameAndCategory_Catcode(String user, String catcode, PageRequest of);

	Expenditures findByIdAndPayment_User_Uname(int id, String user);

}

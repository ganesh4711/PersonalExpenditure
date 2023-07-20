package com.project.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.model.PaymentModeId;
import com.project.model.PaymentMode;

public interface PaymentRepo extends JpaRepository<PaymentMode, PaymentModeId> {
		
	/*@Query(value="update paymentmode set name=:name , remarks=:remarks where code:code and uname:uname",nativeQuery = true)
	public PaymentMode updatePay(@Param(value = "code") String code ,
			                      @Param(value = "uname") String uname ,
			                      @Param(value = "name") String name ,
			                      @Param(value = "remarks") String remarks );*/
	   //retriving  payment modes of user
	@Query(value="select name from paymentmode where uname= :user",nativeQuery = true)
	public List<String> getPaymentModes(@Param(value = "user") String user );
}

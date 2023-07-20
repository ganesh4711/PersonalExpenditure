package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.project.model.PaymentMode;
import com.project.model.PaymentModeId;
import com.project.model.Users;
import com.project.repos.PaymentRepo;

@RestController
public class PaymentController {
	@Autowired
	private PaymentRepo pr;
	
	
	@PostMapping("/payment/add")
	public PaymentMode addPay(@RequestBody PaymentMode newPay) {
		String user =SecurityContextHolder.getContext().getAuthentication().getName();
		if (pr.findById(new PaymentModeId(newPay.getCode(), user)).isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment Mode already exists..!");
		}
		Users u = new Users();
		u.setUname(user);
		newPay.setUser(u);
		try {
			return pr.save(newPay);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data!");
		}

	}
	
	@DeleteMapping("/delete/paymode/{code}")
	public ResponseStatusException dltPayMode(@PathVariable String code) {
		String user =  SecurityContextHolder.getContext().getAuthentication().getName();
		var payId = new PaymentModeId(code, user);
		if (pr.findById(payId).isPresent()) {
			pr.deleteById(payId);
			return new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment mode not found...!");
	}

	@PutMapping("/update/paymode/{code}")
	public PaymentMode updateMode(@RequestBody PaymentMode newPay, @PathVariable String code) {
		String user =  SecurityContextHolder.getContext().getAuthentication().getName();
		var paymode = pr.findById(new PaymentModeId(code, user));
		if (paymode.isPresent()) {
			PaymentMode mode = paymode.get();
			try {
				mode.setName(newPay.getName());
				mode.setRemarks(newPay.getRemarks());
				return pr.save(mode);
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data...");
			}
		} else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment mode not found for code");
	}

	@GetMapping("get/all/paymodes")
	public List<String> getUserPaymentModes() {
		String user =  SecurityContextHolder.getContext().getAuthentication().getName();
		return pr.getPaymentModes(user);

	}

}

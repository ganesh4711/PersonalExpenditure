package com.project.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.project.model.Expenditures;
import com.project.model.PaymentModeId;
import com.project.repos.CategoryRepo;
import com.project.repos.ExpenditureRepo;
import com.project.repos.PaymentRepo;
import com.project.repos.UserRepo;

@RestController
public class ExpenditureControllr {
	@Autowired
	ExpenditureRepo er;
	@Autowired
	CategoryRepo cr;
	@Autowired
	PaymentRepo pr;
	@Autowired
	UserRepo ur;

	@GetMapping("/get/exp/catcode/{catcode}")
	public List<Expenditures> getExpByCatCode(@PathVariable String catcode) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		var category = cr.findById(catcode);
		if (category.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid cat code data!");
		}

		return er.findByPayment_User_UnameAndCategory_Catcode(user, catcode,
				PageRequest.of(0, 2, Sort.by("id").descending()));
	}

	@GetMapping("/get/exp/paymode/{code}")
	public List<Expenditures> getExpByUserPayModes(@PathVariable String code) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		var paymode = pr.findById(new PaymentModeId(code, user));
		if (paymode.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment mode....!");
		}
		return er.findByPayment_User_UnameAndPayment_Code(user, code, PageRequest.of(0, 3, Sort.by("id").descending()));
	}

	@GetMapping("/get/exp/{date1}/{date2}")
	public List<Expenditures> getExpByDates(@PathVariable Date date1, @PathVariable Date date2) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return er.getExpensesByDates(user, date1, date2);
	}

	@GetMapping("/get/exp/tag/{tag}")
	public List<Expenditures> getByTags(@PathVariable String tag) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return er.findByPayment_User_UnameAndTagsContaining(user,tag);
	}

	@GetMapping("/get/exp/month/{month}")
	public List<Object[]> getTotalAmountByCategoryAndMonth(@PathVariable int month) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return er.getTotalAmtByCategoryAndMonth(month,user);
	}

	@GetMapping("/get/exp/top3")
	public List<Expenditures> getTop3Exp() {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return er.findTop3ExpensesByUser(user, PageRequest.of(0, 3));
	}

	@GetMapping("/delete/exp/{id}")
	public String deltExp(@PathVariable int id) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		if (er.findByIdAndPayment_User_Uname(id, user)==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expenditure not found for user");
		}
		er.deleteById(id);
		return "deleted";
	}
	
	@GetMapping("/exp/update/{id}")
	public Expenditures updateExp(@RequestBody Expenditures updatedExp, @PathVariable int id) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		if (er.findByIdAndPayment_User_Uname(id, user)==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expenditure not found for user");
		}
		// validating payment mode
		var paymentMode = pr.findById(new PaymentModeId(updatedExp.getPayment().getCode(), user));
		if (paymentMode.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment mode!");
		}
		updatedExp.setPayment(paymentMode.get());
		// validating given catcode
		var category = cr.findById(updatedExp.getCategory().getCatcode());
		if (category.isEmpty()) { // no catcode
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid cat code data!");
		}
      updatedExp.setId(id);
		return er.save(updatedExp);

	}

	@PostMapping("/exp/add")
	public Expenditures getExpByCat(@RequestBody Expenditures newExp) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		
		// validating payment mode
		var paymentMode = pr.findById(new PaymentModeId(newExp.getPayment().getCode(), user));
		if (paymentMode.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid payment mode!");
		}
		newExp.setPayment(paymentMode.get());

		// validating catcode
		var category = cr.findById(newExp.getCategory().getCatcode());
		if (category.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cat code data not found!");
		try {
			return er.save(newExp);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data....!");
		}
	}
}

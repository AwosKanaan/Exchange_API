package com.zuj.exchangeAPI.controller;

import com.zuj.exchangeAPI.model.Exchange;
import com.zuj.exchangeAPI.service.ExchangeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {
	private final ExchangeService exchangeService;

	public ExchangeController(final ExchangeService exchangeService) {
		this.exchangeService = exchangeService;
	}

	@PostMapping
	public Exchange createRequest(@RequestBody Exchange newRequest) {
		return exchangeService.createExchange(newRequest);
	}
}

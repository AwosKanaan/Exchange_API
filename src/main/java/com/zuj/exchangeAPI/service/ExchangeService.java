package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.model.Exchange;
import com.zuj.exchangeAPI.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExchangeService {

	List<Exchange> getAllExchanges();
	Optional<Exchange> getExchangeById(String exchangeId) throws Exception;
	Exchange createExchange(Exchange exchange);
	Exchange patchExchange(String exchangeId, Map<String, Object> updates);
	void deleteExchange(String exchangeId) throws Exception;
}

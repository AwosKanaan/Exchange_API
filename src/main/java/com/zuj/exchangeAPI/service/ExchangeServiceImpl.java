package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dao.ExchangeDAO;
import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.model.Exchange;
import com.zuj.exchangeAPI.model.RequestStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {
	private final ExchangeDAO exchangeDAO;

	public ExchangeServiceImpl(final ExchangeDAO exchangeDAO) {
		this.exchangeDAO = exchangeDAO;
	}

	@Override
	public List<Exchange> getAllExchanges() {
		return exchangeDAO.findAll();
	}

	@Override
	public Optional<Exchange> getExchangeById(String exchangeId) throws Exception {
		Optional<Exchange> exchange = exchangeDAO.findById(exchangeId);
		if (exchange.isPresent()) {
			return exchange;
		} else {
			throw new Exception("Exchange with id: " + exchange + " is not found.");
		}
	}

	@Override
	public Exchange createExchange(Exchange exchange) {
		exchange.setStatus(RequestStatus.PENDING);
		return exchangeDAO.save(exchange);
	}

	public Exchange patchExchange(String exchangeId, Map<String, Object> updates) {
		return exchangeDAO.findById(exchangeId)
				.map(book -> {
					updates.forEach((key, value) -> {
						Field field = null;
						try {
							field = Book.class.getDeclaredField(key);
							field.setAccessible(true);
							field.set(book, value);
						} catch (NoSuchFieldException | IllegalAccessException e) {
							assert field != null;
							throw new RuntimeException(field.getName() + " does not exist.", e);
						}
					});
					return exchangeDAO.save(book);
				})
				.orElseThrow(() -> new RuntimeException("Exchange with id " + exchangeId + " is not found"));
	}

	@Override
	public void deleteExchange(String exchangeId) throws Exception {
		Optional<Exchange> exchange = exchangeDAO.findById(exchangeId);
		if (exchange.isPresent()) {
			exchangeDAO.deleteById(exchangeId);
		} else {
			throw new Exception("Exchange with id : " + exchangeId + " is not found.");
		}
	}
}

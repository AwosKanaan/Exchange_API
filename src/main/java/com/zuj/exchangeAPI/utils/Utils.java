package com.zuj.exchangeAPI.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static <T, U> T convertDtoToModel(U dto, Class<T> modelClass) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(dto);
			return mapper.readValue(jsonString, modelClass);
		} catch (Exception e) {
			//add proper logger here probably when extending the project...
			return null;
		}
	}
}

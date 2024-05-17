package com.zuj.exchangeAPI.dto;

import com.zuj.exchangeAPI.model.BookCondition;
import com.zuj.exchangeAPI.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.List;

public record BookDTO (
		String bookId,
		String title,
		String description,
		BookCondition condition,
		List<Image> encodedImages
) {
}

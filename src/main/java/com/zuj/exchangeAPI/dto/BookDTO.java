package com.zuj.exchangeAPI.dto;

import com.zuj.exchangeAPI.model.BookCondition;

public record BookDTO (String bookId, String title, String description, BookCondition condition) {}

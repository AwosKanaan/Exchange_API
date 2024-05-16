package com.zuj.exchangeAPI.dto;

import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.model.Post;

import java.util.List;

public record UserDTO (String userName, String email, String phoneNumber, double rating, List<Post> posts, List<Book> requestedBooks) {}

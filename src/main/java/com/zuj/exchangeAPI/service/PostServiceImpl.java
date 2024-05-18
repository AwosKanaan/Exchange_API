package com.zuj.exchangeAPI.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuj.exchangeAPI.dao.PostDAO;
import com.zuj.exchangeAPI.dto.BookDTO;
import com.zuj.exchangeAPI.dto.PostDTO;
import com.zuj.exchangeAPI.dto.UserDTO;
import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.model.Image;
import com.zuj.exchangeAPI.model.Post;
import com.zuj.exchangeAPI.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

	private final PostDAO postDAO;
	private final BookService bookService;
	private final UserService userService;
	private final SequenceGeneratorService sequenceGenerator;
	private final ObjectMapper mapper;

	public PostServiceImpl(
			final PostDAO postDAO,
			final BookService bookService,
			final UserService userService,
			final SequenceGeneratorService sequenceGenerator,
			final ObjectMapper mapper
	) {
		this.postDAO = postDAO;
		this.bookService = bookService;
		this.userService = userService;
		this.sequenceGenerator = sequenceGenerator;
		this.mapper = mapper;
	}

	@Override
	public List<Post> getAllPosts() {
		List<Post> posts = postDAO.findAll();
		posts.forEach(post -> {
			try {
				populateUserInPost(post);
				populateBookInPost(post);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		return posts;
	}

	@Override
	public Optional<Post> getPostByPostId(String postId) throws Exception {
		Optional<Post> optionalPost = postDAO.findByPostId(postId);
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			populateUserInPost(post);
			populateBookInPost(post);
			return Optional.of(post);
		} else {
			throw new Exception("Post with id: " + postId + " is not found.");
		}
	}

	@Override
	public Post createPost(PostDTO postDTO) throws Exception {
		Optional<User> optionalUser = userService.getUserByUserId(postDTO.userId());
		if (optionalUser.isEmpty()) {
			throw new Exception("cannot create post for non existing user consider creating one!");
		}
		Optional<Book> book = bookService.getBookByBookId(postDTO.bookId());
		if (book.isEmpty()) {
			throw new Exception("Book with id: " + postDTO.bookId() + " is not found");
		}
		Post post = new Post();
		post.setPostId(String.valueOf(sequenceGenerator.generateSequence(Post.SEQUENCE_NAME, "postId")));
		post.setBookId(book.get().getBookId());
		post.setUserId(postDTO.userId());
		populateUserInPost(post);
		populateBookInPost(post);
		post.setCreatedAt(LocalDateTime.now());
		post = postDAO.save(post);
		User user = optionalUser.get();
		List<Post> posts = getPostsForUser(user.getUserId());
		user.setPosts(posts);
		user.setUpdatedAt(LocalDateTime.now());
		userService.updateUser(user);
		return post;
	}

	@Override
	public Post updatePost(Post post) throws Exception {
		Optional<Post> optionalPost = postDAO.findByPostId(post.getPostId());
		if (optionalPost.isEmpty()) {
			throw new Exception("post with id: " + post.getPostId() + " is not found");
		}
		return postDAO.save(post);
	}

	@Override
	public Post patchPost(String postId, Map<String, Object> updates) {
		return postDAO.findByPostId(postId)
				.map(post -> {
					updates.forEach((key, value) -> {
						if (key.equals("book")) {
							try {
								BookDTO bookDTO = mapper.convertValue(value, BookDTO.class);
								Map<String, Object> updatesMap = mapper.convertValue(bookDTO, new TypeReference<>() {});
								updatesMap.entrySet().removeIf(entry -> entry.getValue() == null);
								bookService.patchBook(bookDTO.bookId(), updatesMap);
							} catch (Exception e) {
								throw new RuntimeException("Error converting book field.", e);
							}
						} else {
							Field field = null;
							try {
								field = Post.class.getDeclaredField(key);
								field.setAccessible(true);
								field.set(post, value);
							} catch (NoSuchFieldException | IllegalAccessException e) {
								assert field != null;
								throw new RuntimeException(field.getName() + " does not exist.", e);
							}
						}
					});
					post.setUpdatedAt(LocalDateTime.now());
					return postDAO.save(post);
				})
				.orElseThrow(() -> new RuntimeException("Post not found with id " + postId));
	}

	@Override
	public void deletePost(String postId) throws Exception {
		Optional<Post> post = postDAO.findByPostId(postId);
		if (post.isPresent()) {
			bookService.deleteBook(post.get().getBookId());
			postDAO.deleteByPostId(postId);
		} else {
			throw new Exception("Post with id: " + postId + " was not found");
		}
	}

	private void populateUserInPost(Post post) throws Exception {
		Optional<User> optionalUser = userService.getUserByUserId(post.getUserId());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			post.setUser(user);
		}
	}

	@Override
	public List<Post> getPostsForUser(String userId) throws Exception {
		Optional<User> optionalUser = userService.getUserByUserId(userId);
		if (optionalUser.isEmpty()) {
			throw new Exception("User with id: " + userId + " is not found");
		}
		List<Post> posts = postDAO.getPostsByUserId(userId);
		for (Post post : posts) {
			populateUserInPost(post);
			populateBookInPost(post);
		}
		return posts;
	}

	private void populateBookInPost(Post post) throws Exception {
		Optional<Book> optionalBook = bookService.getBookByBookId(post.getBookId());
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			post.setBook(book);
		}
	}
}

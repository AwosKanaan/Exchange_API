package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dao.BookRequestDAO;
import com.zuj.exchangeAPI.model.Book;
import com.zuj.exchangeAPI.model.BookRequest;
import com.zuj.exchangeAPI.model.Post;
import com.zuj.exchangeAPI.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class BookRequestServiceImpl implements BookRequestService {

	private final BookRequestDAO bookRequestDAO;
	private final PostService postService;
	private final UserService userService;
	private final BookService bookService;
	private final SequenceGeneratorService sequenceGenerator;

	public BookRequestServiceImpl(
			final BookRequestDAO bookRequestDAO,
			final PostService postService,
			final UserService userService,
			final BookService bookService,
			final SequenceGeneratorService sequenceGenerator
	) {
		this.bookRequestDAO = bookRequestDAO;
		this.postService = postService;
		this.userService = userService;
		this.bookService = bookService;
		this.sequenceGenerator = sequenceGenerator;
	}

	public BookRequest save(BookRequest bookRequest) throws Exception {
		Optional<User> requester = userService.getUserByUserId(bookRequest.getRequesterId());
		Optional<User> requestedFrom = userService.getUserByUserId(bookRequest.getRequestedFromId());
		if (requester.isEmpty() || requestedFrom.isEmpty()) {
			throw new Exception("Requester or requested from user does not exist");
		}

		Optional<Post> post = postService.getPostByPostId(bookRequest.getPost().getPostId());
		if (post.isEmpty()) {
			throw new Exception("post does not exist");
		}

		Optional<Book> book = bookService.getBookByBookId(bookRequest.getPost().getBookId());
		if (book.isEmpty()) {
			throw new Exception("book does not exist");
		}
		bookRequest.setCreatedAt(LocalDateTime.now());
		return bookRequestDAO.save(bookRequest);
	}

	@Transactional
	public void completeRequestByRequester(String requesterId) throws Exception {
		BookRequest bookRequest = bookRequestDAO.findBookRequestByRequesterId(requesterId)
				.orElseThrow(() -> new RuntimeException("Request not found"));
		bookRequest.setRequesterComplete(true);
		checkAndMarkPostAsDone(bookRequest);
		bookRequest.setUpdatedAt(LocalDateTime.now());
		bookRequestDAO.save(bookRequest);
	}

	@Transactional
	public void completeRequestByRequestedFrom(String requestedFromId) throws Exception {
		BookRequest bookRequest = bookRequestDAO.findBookRequestByRequestedFromId(requestedFromId)
				.orElseThrow(() -> new RuntimeException("Request not found"));
		bookRequest.setRequestedFromComplete(true);
		checkAndMarkPostAsDone(bookRequest);
		bookRequest.setUpdatedAt(LocalDateTime.now());
		bookRequestDAO.save(bookRequest);
	}

	@Transactional
	protected void checkAndMarkPostAsDone(BookRequest bookRequest) throws Exception {
		if (bookRequest.isRequesterComplete() && bookRequest.isRequestedFromComplete()) {
			Post post = bookRequest.getPost();
			post.setDone(true);
			postService.updatePost(post);

			User requester = userService.getUserByUserId(bookRequest.getRequesterId())
					.orElseThrow(() -> new RuntimeException("Requester not found"));
			requester.getRequestedBooks().add(post.getBook());
			userService.updateUser(requester);
		}
	}

	@Override
	public List<BookRequest> getAllBookRequests() {
		return null;
	}

	@Override
	public Optional<BookRequest> getBookRequestByBookRequestId(String bookRequestId) throws Exception {
		return Optional.empty();
	}

	@Override
	public BookRequest createBookRequest(BookRequest bookRequest) throws Exception {
		Optional<User> requester = userService.getUserByUserId(bookRequest.getRequesterId());
		Optional<User> requestedFrom = userService.getUserByUserId(bookRequest.getRequestedFromId());
		if (requester.isEmpty() || requestedFrom.isEmpty()) {
			throw new Exception("Requester or requestedFrom user(s) not found");
		}
		Optional<Post> post = postService.getPostByPostId(bookRequest.getPost().getPostId());
		if (post.isEmpty()) {
			throw new Exception("Post with id: " + bookRequest.getPost().getPostId() + " is not found");
		}
		bookRequest.setBookRequestId(String.valueOf(sequenceGenerator.generateSequence(BookRequest.SEQUENCE_NAME, "bookRequestId")));
		bookRequest.setPost(post.get());
		return bookRequestDAO.save(bookRequest);
	}

	@Override
	public BookRequest patchBookRequest(String bookRequestId, Map<String, Object> updates) {
		return null;
	}

	@Override
	public void deleteBookRequest(String bookRequestId) {
		bookRequestDAO.deleteBookRequestByBookRequestId(bookRequestId);
	}
}

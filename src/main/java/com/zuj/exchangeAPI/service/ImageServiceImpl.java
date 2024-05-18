package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.dao.ImageDAO;
import com.zuj.exchangeAPI.model.Image;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

	private final ImageDAO imageDAO;

	public ImageServiceImpl(final ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	public Image addImage(String title, MultipartFile file) throws IOException {
		Image image = new Image();
		image.setTitle(title);
		image.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		image.setFileContentType(file.getContentType());
		image = imageDAO.insert(image);
		return image;
	}

	public Image getImage(String id) {
		return imageDAO.findById(id).get();
	}

	@Override
	public Image getImageById(String id) {
		return imageDAO.getImageById(id);
	}
}
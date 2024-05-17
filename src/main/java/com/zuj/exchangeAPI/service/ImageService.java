package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
	Image addImage(String title, MultipartFile file) throws IOException;
	Image getImage(String id) throws Exception;
	Image getImageById(String id);
}

package com.zuj.exchangeAPI.model;

import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "images_upload")
@Data
public class Image {
	@Id
	private String id;
	private String title;
	private Binary image;
	private String fileContentType;
}
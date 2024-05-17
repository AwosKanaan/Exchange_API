package com.zuj.exchangeAPI.service;

import com.zuj.exchangeAPI.model.DatabaseSequence;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {
	private final MongoOperations mongoOperations;

	public SequenceGeneratorService(final MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public long generateSequence(String seqName, String classId) {
		DatabaseSequence counter = mongoOperations.findAndModify(
				query(where(classId).is(seqName)),
				new Update().inc("seq", 1),
				options().returnNew(true).upsert(true),
				DatabaseSequence.class);
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
}

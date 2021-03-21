package com.app.api.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.app.api.entity.User;

@Repository
public class UserRepository {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public User save(User user) {
		log.trace("Metodo SaveUser");
		dynamoDBMapper.save(user);
		return user;
	}

	public Optional<User> getUSer(String dni) {
		log.trace("Metodo getUSer");
		User user=null;
		Map<String, AttributeValue> eav= new HashMap<String ,AttributeValue>();
		eav.put(":dni", new AttributeValue().withS(dni));
		DynamoDBScanExpression scanExpression=new DynamoDBScanExpression()
				.withFilterExpression("dni = :dni")
				.withExpressionAttributeValues(eav);
		List<User> useResult=dynamoDBMapper.scan(User.class, scanExpression);
		if(!useResult.isEmpty() && useResult.size()>0) {
			 user=useResult.get(0);
		}
		return Optional.ofNullable(user);
	}

	public String deleteUser(String dni) {
		log.trace("Metodo deleteUser");
		User user=null;
		Map<String, AttributeValue> eav= new HashMap<String ,AttributeValue>();
		eav.put(":dni", new AttributeValue().withS(dni));
		
		DynamoDBScanExpression scanExpression=new DynamoDBScanExpression()
				.withFilterExpression("dni = :dni")
				.withExpressionAttributeValues(eav);
		
		List<User> useResult=dynamoDBMapper.scan(User.class, scanExpression);
		if(!useResult.isEmpty() && useResult.size()>0) {
			 user=useResult.get(0);
		}	
		dynamoDBMapper.delete(user);
		return "User Delete";
	}

	public String update(String dni, User user) {
		log.trace("Metodo UpdateUSer");
		dynamoDBMapper.save(user, new DynamoDBSaveExpression().withExpectedEntry("dni",
				new ExpectedAttributeValue(new AttributeValue().withS(dni))));
		return dni;
	}
	
	public List<User> getLstUsers(){
		log.trace("Metodo listUSers");
		PaginatedList<User> resultsUser= dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
		resultsUser.loadAllResults();
		return resultsUser;
	}
}

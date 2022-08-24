package pe.com.bootcamp.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import pe.com.bootcamp.domain.aggregate.BankOperationRequest;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BankOperationRequestRepository implements IBankOperationRequestRepository {

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Override
	public Mono<UnitResult<BankOperationRequest>> create(BankOperationRequest entity) {
		Mono<UnitResult<BankOperationRequest>> result;
		try {
			Query query = new Query(Criteria.where("accountNumber").is(entity.getAccountNumber())); 
			result = mongoTemplate.exists(query, BankOperationRequest.class).flatMap(i->
			{								
				if(!i.booleanValue())
					return mongoTemplate.insert(entity).map(ii-> new UnitResult<BankOperationRequest>(ii));				
				else
					return Mono.just(new UnitResult<BankOperationRequest>(true,"exists bank operation request!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<BankOperationRequest>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<BankOperationRequest>> update(BankOperationRequest entity) {
		Mono<UnitResult<BankOperationRequest>> result;
		try {			
			Query query = new Query(Criteria.where("accountNumber").is(entity.getAccountNumber())); 
			result = mongoTemplate.exists(query, BankOperationRequest.class).flatMap(i->
			{								
				if(i.booleanValue())
					return mongoTemplate.save(entity).map(ii-> new UnitResult<BankOperationRequest>(ii));				
				else
					return Mono.just(new UnitResult<BankOperationRequest>(true,"bank operation request not exists!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<BankOperationRequest>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<BankOperationRequest>> saveAll(Flux<BankOperationRequest> entities) {
		Mono<UnitResult<BankOperationRequest>> result;
		try {
			result = mongoTemplate.insertAll(entities.collectList(), BankOperationRequest.class).collectList().map(i-> new UnitResult<BankOperationRequest>(i));
		}catch (Exception e) {
			result = Mono.just(new UnitResult<BankOperationRequest>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<BankOperationRequest>> findById(String id) {
		Mono<UnitResult<BankOperationRequest>> result;
		try {
			result = mongoTemplate.findById(id, BankOperationRequest.class).map(i-> new UnitResult<BankOperationRequest>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<BankOperationRequest>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<BankOperationRequest>> findByClientIdentNum(String dni) {
		Mono<UnitResult<BankOperationRequest>> result;			
		try {			
			Query query = new Query(Criteria.where("clientIdentNum").is(dni));
			result = mongoTemplate.find(query, BankOperationRequest.class).collectList().map(i-> new UnitResult<BankOperationRequest>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<BankOperationRequest>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<BankOperationRequest>> findByAccountNumber(String accountNumber) {
		Mono<UnitResult<BankOperationRequest>> result;			
		try {			
			Query query = new Query(Criteria.where("accountNumber").is(accountNumber));			
			result = mongoTemplate.findOne(query, BankOperationRequest.class).map(i-> new UnitResult<BankOperationRequest>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<BankOperationRequest>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<BankOperationRequest>> findAll() {
		Mono<UnitResult<BankOperationRequest>> result;			
		try {						
			result = mongoTemplate.findAll(BankOperationRequest.class).collectList().map(i-> new UnitResult<BankOperationRequest>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<BankOperationRequest>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<ResultBase> deleteById(String id) {
		Mono<ResultBase> result;			
		try {
			Query query = new Query(Criteria.where("Id").is(id));
			result = mongoTemplate.remove(query,BankOperationRequest.class).flatMap(i-> Mono.just(new ResultBase(i.getDeletedCount() > 0, null)));
		}catch (Exception e) {
			result = Mono.just(new ResultBase(true,e.getMessage()));
		}
		return result;
	}
	@Override
	public Flux<BankOperationRequest> findAllStreaming() {
		return mongoTemplate.findAll(BankOperationRequest.class);
	}
}

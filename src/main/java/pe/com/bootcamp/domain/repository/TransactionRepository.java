package pe.com.bootcamp.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import pe.com.bootcamp.domain.aggregate.Transaction;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionRepository implements ITransactionRepository {

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Override
	public Mono<UnitResult<Transaction>> create(Transaction entity) {
		Mono<UnitResult<Transaction>> result;
		try {
			Query query = new Query(Criteria.where("bankAccountNumber").is(entity.getBankAccountNumber())); 
			result = mongoTemplate.exists(query, Transaction.class).flatMap(i->
			{								
				if(!i.booleanValue())
					return mongoTemplate.insert(entity).map(ii-> new UnitResult<Transaction>(ii));				
				else
					return Mono.just(new UnitResult<Transaction>(true,"exists bank account!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> update(Transaction entity) {
		Mono<UnitResult<Transaction>> result;
		try {			
			Query query = new Query(Criteria.where("bankAccountNumber").is(entity.getBankAccountNumber())); 
			result = mongoTemplate.exists(query, Transaction.class).flatMap(i->
			{								
				if(i.booleanValue())
					return mongoTemplate.save(entity).map(ii-> new UnitResult<Transaction>(ii));				
				else
					return Mono.just(new UnitResult<Transaction>(true,"bank account not exists!"));					
			});
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> saveAll(Flux<Transaction> entities) {
		Mono<UnitResult<Transaction>> result;
		try {
			result = mongoTemplate.insertAll(entities.collectList(), Transaction.class).collectList().map(i-> new UnitResult<Transaction>(i));
		}catch (Exception e) {
			result = Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> findById(String id) {
		Mono<UnitResult<Transaction>> result;
		try {
			result = mongoTemplate.findById(id, Transaction.class).map(i-> new UnitResult<Transaction>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<UnitResult<Transaction>> findByClientIdentNum(String dni) {
		Mono<UnitResult<Transaction>> result;			
		try {			
			Query query = new Query(Criteria.where("clientIdentNum").is(dni));
			result = mongoTemplate.find(query, Transaction.class).collectList().map(i-> new UnitResult<Transaction>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
		}
		return result;		
	}

	@Override
	public Mono<UnitResult<Transaction>> findByAccountNumber(String accountNumber) {
		Mono<UnitResult<Transaction>> result;			
		try {			
			Query query = new Query(Criteria.where("accountNumber").is(accountNumber));			
			result = mongoTemplate.findOne(query, Transaction.class).map(i-> new UnitResult<Transaction>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
		}
		return result;		
	}

	@Override
	public Mono<UnitResult<Transaction>> findAll() {
		Mono<UnitResult<Transaction>> result;			
		try {						
			result = mongoTemplate.findAll(Transaction.class).collectList().map(i-> new UnitResult<Transaction>(i));
		} catch (Exception e) {
			result = Mono.just(new UnitResult<Transaction>(true,e.getMessage()));
		}
		return result;
	}

	@Override
	public Mono<ResultBase> deleteById(String id) {
		Mono<ResultBase> result;			
		try {
			Query query = new Query(Criteria.where("Id").is(id));
			result = mongoTemplate.remove(query,Transaction.class).flatMap(i-> Mono.just(new ResultBase(i.getDeletedCount() > 0, null)));
		}catch (Exception e) {
			result = Mono.just(new ResultBase(true,e.getMessage()));
		}
		return result;
	}
	@Override
	public Flux<Transaction> findAllStreaming() {
		return mongoTemplate.findAll(Transaction.class);
	}
}

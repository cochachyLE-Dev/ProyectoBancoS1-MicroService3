package pe.com.bootcamp.domain.repository;

import pe.com.bootcamp.domain.aggregate.BankOperationRequest;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankOperationRequestRepository {

	Mono<UnitResult<BankOperationRequest>> create(BankOperationRequest entity);
	
	Mono<UnitResult<BankOperationRequest>> update(BankOperationRequest entity);
	
	Mono<UnitResult<BankOperationRequest>> saveAll(Flux<BankOperationRequest> entities);
	
	Mono<UnitResult<BankOperationRequest>> findById(String id);
	
	Mono<UnitResult<BankOperationRequest>> findByClientIdentNum(String dni);
	
	Mono<UnitResult<BankOperationRequest>> findByAccountNumber(String accountNumber);
	
	Mono<UnitResult<BankOperationRequest>> findAll();	
	
	Mono<ResultBase> deleteById(String id);
	
	Flux<BankOperationRequest> findAllStreaming();
}

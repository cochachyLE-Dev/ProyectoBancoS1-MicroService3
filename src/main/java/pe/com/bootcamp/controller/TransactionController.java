package pe.com.bootcamp.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.bootcamp.domain.aggregate.Transaction;
import pe.com.bootcamp.domain.repository.TransactionRepository;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Transaction")
public class TransactionController {
	@Autowired
	TransactionRepository transactionRepository; 
		
	@RequestMapping(value = "/", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> create(@RequestBody Transaction entity){
		return transactionRepository.create(entity);
	}
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	Mono<UnitResult<Transaction>> update(@RequestBody Transaction entity){
		return transactionRepository.update(entity);
	}
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> saveAll(@RequestBody Flux<Transaction> entities){
		return transactionRepository.saveAll(entities);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> findById(@PathVariable String id){
		return transactionRepository.findById(id);
	}
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> findByClientIdentNum(@PathVariable String dni){
		return transactionRepository.findByClientIdentNum(dni);
	}
	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.POST)
	Mono<UnitResult<Transaction>> findByAccountNumber(@PathVariable String accountNumber){
		return transactionRepository.findByAccountNumber(accountNumber);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	Mono<UnitResult<Transaction>> findAll(){
		return transactionRepository.findAll();
	}	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Mono<ResultBase> deleteById(@PathVariable String id){
		return transactionRepository.deleteById(id);
	}
	@RequestMapping(value = "/stream", method = RequestMethod.DELETE,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Transaction> findAllStreaming(@PathVariable String id){
		return transactionRepository.findAllStreaming().delayElements(Duration.ofSeconds(1));
	}
}

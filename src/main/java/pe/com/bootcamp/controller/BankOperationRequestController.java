package pe.com.bootcamp.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pe.com.bootcamp.domain.aggregate.BankOperationRequest;
import pe.com.bootcamp.domain.repository.BankOperationRequestRepository;
import pe.com.bootcamp.utilities.ResultBase;
import pe.com.bootcamp.utilities.UnitResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/BankOperationRequest")
public class BankOperationRequestController {
	@Autowired
	BankOperationRequestRepository bankOperationRequestRepository; 
		
	@RequestMapping(value = "/", method = RequestMethod.POST)
	Mono<UnitResult<BankOperationRequest>> create(@RequestBody BankOperationRequest entity){
		return bankOperationRequestRepository.create(entity);
	}
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	Mono<UnitResult<BankOperationRequest>> update(@RequestBody BankOperationRequest entity){
		return bankOperationRequestRepository.update(entity);
	}
	@RequestMapping(value = "/batch", method = RequestMethod.POST)
	Mono<UnitResult<BankOperationRequest>> saveAll(@RequestBody Flux<BankOperationRequest> entities){
		return bankOperationRequestRepository.saveAll(entities);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Mono<UnitResult<BankOperationRequest>> findById(@PathVariable String id){
		return bankOperationRequestRepository.findById(id);
	}
	@RequestMapping(value = "/{dni}", method = RequestMethod.GET)
	Mono<UnitResult<BankOperationRequest>> findByClientIdentNum(@PathVariable String dni){
		return bankOperationRequestRepository.findByClientIdentNum(dni);
	}
	@RequestMapping(value = "/{accountNumber}", method = RequestMethod.POST)
	Mono<UnitResult<BankOperationRequest>> findByAccountNumber(@PathVariable String accountNumber){
		return bankOperationRequestRepository.findByAccountNumber(accountNumber);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	Mono<UnitResult<BankOperationRequest>> findAll(){
		return bankOperationRequestRepository.findAll();
	}	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	Mono<ResultBase> deleteById(@PathVariable String id){
		return bankOperationRequestRepository.deleteById(id);
	}
	@RequestMapping(value = "/stream", method = RequestMethod.DELETE,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<BankOperationRequest> findAllStreaming(@PathVariable String id){
		return bankOperationRequestRepository.findAllStreaming().delayElements(Duration.ofSeconds(1));
	}
}

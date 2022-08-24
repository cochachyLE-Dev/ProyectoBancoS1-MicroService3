package pe.com.bootcamp.domain.aggregate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "BankOperationRequests")
@Data
public class BankOperationRequest {
	private String accountNumber;
	private String clientIdentNum;
}

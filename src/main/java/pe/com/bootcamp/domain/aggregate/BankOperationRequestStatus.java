package pe.com.bootcamp.domain.aggregate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "BankOperationRequestStatus")
@Data
public class BankOperationRequestStatus {

}

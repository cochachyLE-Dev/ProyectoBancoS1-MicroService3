package pe.com.bootcamp.domain.aggregate;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Document(collection = "Transactions")
@Data
public class Transaction {
	@Id
    private String id;
	private String bankAccountNumber;
	private String clientIdentNum;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date creationDate;
}

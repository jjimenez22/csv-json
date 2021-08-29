package jjimenez22.csvjson.repository;

import jjimenez22.csvjson.model.PricePaid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface PricePaidRepository extends MongoRepository<PricePaid, String> {

    PricePaid findOneByTransactionId(String transactionId);

    Page<PricePaid> findByDateOfTransferBetween(Date from, Date to, Pageable pageable);

}

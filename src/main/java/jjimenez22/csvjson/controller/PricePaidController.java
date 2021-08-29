package jjimenez22.csvjson.controller;

import jjimenez22.csvjson.model.PricePaid;
import jjimenez22.csvjson.repository.PricePaidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@RestController
@RequestMapping("pricespaid")
public class PricePaidController {
    private final Logger logger = LoggerFactory.getLogger(PricePaidController.class);
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private final PricePaidRepository repository;

    public PricePaidController(PricePaidRepository repository) {
        this.repository = repository;
    }

    @GetMapping(produces = "application/json")
    public Page<PricePaid> get(@Param("from") String from, @Param("to") String to, @Param("page") Integer page) throws ParseException {
        PageRequest pageRequest = PageRequest.of(Optional.ofNullable(page).orElse(0), 10);
        if (from != null || to != null) {
            return repository.findByDateOfTransferBetween(format.parse(from), format.parse(to), pageRequest);
        } else {
            return repository.findAll(pageRequest);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public PricePaid getByTransactionId(@PathVariable String id) {
        return repository.findOneByTransactionId(id);
    }
}

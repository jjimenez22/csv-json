package jjimenez22.csvjson.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter @Setter @NoArgsConstructor
@Document
public class PricePaid {
    private static final Logger logger = LoggerFactory.getLogger(PricePaid.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final List<Field> entityFields = Stream.of(PricePaid.class.getDeclaredFields())
            .filter(field -> !Modifier.isStatic(field.getModifiers()))
            .skip(1) // exclude id
            .collect(Collectors.toList());

    @Id
    private String id;
    private String transactionId;
    private Integer price;
    private Date dateOfTransfer;
    private String postcode;
    private String propertyType;
    private String oldNew;
    private String duration;
    private String paon;
    private String saon;
    private String street;
    private String locality;
    private String city;
    private String district;
    private String county;
    private String ppdCategoryPrice;
    private String recordStatus;

    public PricePaid(List<String> args) {
        IntStream.range(0, entityFields.size()).forEach(i -> {
            String arg = args.get(i);
            Field field = entityFields.get(i);
            field.setAccessible(true);
            try {
                if ("price".equals(field.getName())) {
                    field.set(this, Integer.valueOf(arg));
                } else if ("transactionId".equals(field.getName())) {
                    field.set(this, arg.substring(1, arg.length()-1)); // remove {}
                } else if ("dateOfTransfer".equals(field.getName())) {
                    field.set(this, DATE_FORMAT.parse(arg));
                } else {
                    field.set(this, arg);
                }
            } catch (IllegalAccessException | ParseException e) {
                e.printStackTrace();
            }
        });
    }
}

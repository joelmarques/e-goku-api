package br.com.abasteceai.address;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter@Setter
@Document(collection = "addresses")
public class AddressModel {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Indexed(name = "zip_idx", unique = true)
    private String zip;
    private String line1;
    private String line2;
    private String district;
    private String city;
    private String state;
    private String country;
    private String ibge;
    private String ddd;
}
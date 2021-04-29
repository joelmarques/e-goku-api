package br.com.abasteceai.address;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter@Setter
@EqualsAndHashCode(of = "zip")
@Document(collection = "addresses")
public class AddressModel {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;

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
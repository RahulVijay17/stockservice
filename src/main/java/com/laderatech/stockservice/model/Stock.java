package com.laderatech.stockservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock-service")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    @Min(value = 5, message = "Quantity must be greater than 6")
    @Max(value = 10,message = "Quantity must be less or equal to 10")
    private Integer productQuantity;

    @NotNull(message = "Product code must not be null")
    private Long productCode;

    @NotBlank(message="Location must not be blank")
    private String location;

   // @Pattern(regexp="(^$|[0-9]{5})",message = "Zip Code must be 6 digits")
    @Size(min = 6,max = 6,message = "Zip Code must be 6 digits")
    private String zipCode;

    @CreatedDate
    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    @JsonIgnore
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    @JsonIgnore
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    @JsonIgnore
    private String updatedBy;

}

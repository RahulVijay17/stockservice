package com.laderatech.stockservice.vo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(
        description = "StockVo Model Information"
)
public class StockVo {

    private Long stockId;
    @Schema(
            description = "Stock productQuantity Information"
    )
    @Min(value = 5, message = "Quantity must be greater than 6")
    @Max(value = 10,message = "Quantity must be less or equal to 10")
    private Integer productQuantity;
    @Schema(
            description = "Stock productCode Information"
    )
    @NotNull(message = "Product code must not be null")
    private Long productCode;
    @Schema(
            description = "Stock location Information"
    )
    @NotBlank(message="Location must not be blank")
    private String location;
    @Schema(
            description = "Stock zipCode Information"
    )
    @NotBlank(message="Zip Code must not be blank")
    // @Pattern(regexp="(^$|[0-9]{5})",message = "Zip Code must be 6 digits")
    @Size(min = 6,max = 6,message = "Zip Code must be 6 digits")
    private String zipCode;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private LocalDateTime updatedAt;
    @JsonIgnore
    private String updatedBy;

}

package com.codemayur.transactions_routine.core.dto;

import com.codemayur.transactions_routine.core.bo.AccountBo;
import com.codemayur.transactions_routine.core.bo.OperationTypeBo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionRequestDto {

    @JsonProperty("account")
    private AccountBo account;
    @JsonProperty("operation_type")
    private OperationTypeBo operationType;
    @JsonProperty("amount")
    private BigDecimal amount;

}

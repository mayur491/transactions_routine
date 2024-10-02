package com.codemayur.transactions_routine.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponseDto {

    @JsonProperty("account_id")
    Long accountId;
    @JsonProperty("document_number")
    String documentNumber;

    @JsonProperty("error_code")
    String errorCode;
    @JsonProperty("error_message")
    String errorMessage;

}

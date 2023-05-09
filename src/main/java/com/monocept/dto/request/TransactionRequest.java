package com.monocept.dto.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long accountId;
    private Float amount;
}

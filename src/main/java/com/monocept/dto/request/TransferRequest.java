package com.monocept.dto.request;

import lombok.Data;

@Data
public class TransferRequest {
    private Long senderAccountId;
    private Long receiverAccountId;
    private Float amount;
}

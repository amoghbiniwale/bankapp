package com.monocept.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionDetail {
	Long tId;
    Float amount;
    LocalDateTime createdOn;
    String transactionType;
    String status;
    Long senderAccNO;
    Long reciverAccNO;
}

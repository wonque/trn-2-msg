package com.tietoevry.trn2msg.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.tietoevry.trn2msg.utils.ObfuscationUtil;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonRootName(value = "root")
public class OutputMessage {

    private final String TRANSACTION_INFO_TEMPLATE = "%s with card %s on %s, amount %s %s.";
    private List<String> transactionInfo;
    private Map<String, String> totalInfo;

    public OutputMessage() {
        this.totalInfo = new HashMap<>();
        this.transactionInfo = new ArrayList<>();
    }

    public void appendTransactionInfo(Transaction transaction) {

        this.transactionInfo.add(String.format(
                TRANSACTION_INFO_TEMPLATE, transaction.getTransactionType().getFormattedType(),
                ObfuscationUtil.obfuscate(transaction.getAccNumber()),
                transaction.getTransactionTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:ss")),
                transaction.getAmount().setScale(2, RoundingMode.HALF_UP),
                transaction.getCurrency().getAlphaCode()
        ));
    }

    public void putTotalInfo(String key, String value) {
        this.totalInfo.put(key, value);
    }

    @JsonProperty("msg-list")
    public List<String> getTransactionInfo() {
        return transactionInfo;
    }

    @JsonProperty("totals")
    public Map<String, String> getTotalInfo() {
        return totalInfo;
    }
}

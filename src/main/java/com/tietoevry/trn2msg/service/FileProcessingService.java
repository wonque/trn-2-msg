package com.tietoevry.trn2msg.service;

import com.tietoevry.trn2msg.exception.TransactionParsingException;
import com.tietoevry.trn2msg.model.OutputMessage;
import com.tietoevry.trn2msg.model.Transaction;
import com.tietoevry.trn2msg.utils.OutputWriterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class FileProcessingService {

    private static final Logger log = LoggerFactory.getLogger(FileProcessingService.class);
    private InputParsingService inputParsingService;

    public FileProcessingService(InputParsingService inputParsingService) {
        this.inputParsingService = inputParsingService;
    }

    public void processTransactionFile(String inputFile, String outputFile) {
        log.debug("Start of file conversion");
        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFile))) {
            log.debug(String.format("Reading %s file", inputFile));
            String line;
            long cnt = 0;
            BigDecimal sum = BigDecimal.ZERO;
            OutputMessage message = new OutputMessage();
            while ((line = br.readLine()) != null) {
                Optional<Transaction> t = getTransaction(line);
                if (t.isPresent()) {
                    cnt++;
                    sum = sum.add(t.get().getAmount());
                    message.appendTransactionInfo(t.get());
                }
            }
            message.putTotalInfo("cnt", String.valueOf(cnt));
            message.putTotalInfo("sum", sum.toString());
            message.putTotalInfo("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")));
            OutputWriterUtil.INSTANCE.writeOutput(message, outputFile);
        } catch (IOException ex) {
            log.error(String.format("Unable to read file %s. Stacktrace: ", inputFile), ex);
        }
    }

    private Optional<Transaction> getTransaction(String input) {
        try {
            return inputParsingService.getTransactionObject(input);
        } catch (TransactionParsingException ex) {
            log.error(
                    String.format("TransactionFileProcessingService - parsing error occurred: %s", ex));
            return Optional.empty();
        }
    }
}

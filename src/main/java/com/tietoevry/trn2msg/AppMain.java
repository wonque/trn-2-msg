package com.tietoevry.trn2msg;

import com.tietoevry.trn2msg.service.FileProcessingService;
import com.tietoevry.trn2msg.service.InputParsingService;
import com.tietoevry.trn2msg.service.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppMain {

    private static final Logger log = LoggerFactory.getLogger(AppMain.class);

    public static void main(String[] args) {
        if (args.length == 0) {
            log.error("No input file provided! At least input file must be provided!");
        } else {
            InputValidator validator = new InputValidator();
            InputParsingService parsingService = new InputParsingService(validator);
            FileProcessingService processingService =
                    new FileProcessingService(parsingService);
            if (args.length == 1 && !args[0].isBlank()) {
                processingService.processTransactionFile(args[0], null);
            } else if (args.length >= 2) {
                processingService.processTransactionFile(args[0], args[1]);
            }
            log.debug("End of conversion");
        }
    }
}

package com.tietoevry.trn2msg.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tietoevry.trn2msg.model.OutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public enum OutputWriterUtil {
    INSTANCE;

    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private Logger log = LoggerFactory.getLogger(OutputWriterUtil.class);

    public void writeOutput(OutputMessage message, String outputFile) {
        if (Objects.isNull(message)) {
            log.warn("OutputWriterUtil - output message object is null!");
        } else {
            if (Objects.isNull(outputFile) || outputFile.isBlank()) {
                writeToConsole(message);
            } else {
                writeToFileOrToConsole(message, outputFile);
            }
        }
    }

    private void writeToConsole(OutputMessage message) {
        try {
            System.out.println(ow.writeValueAsString(message));
        } catch (JsonProcessingException ex) {
            log.error("OutputWriterUtil - unable to process output message with ObjectMapper. Exception: ", ex);
        }
    }

    private void writeToFileOrToConsole(OutputMessage message, String file) {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(file))) {
            bw.write(ow.writeValueAsString(message));
            log.debug("OutputWriterUtil - successfully appended output message to file!");
        } catch (IOException ex) {
            log.error(
                    String.format("OutputWriterUtil - failed to write to file with path %s", file), ex);
            writeToConsole(message);
        }
    }
}
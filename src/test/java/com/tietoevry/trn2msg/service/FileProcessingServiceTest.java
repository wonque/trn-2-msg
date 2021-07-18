package com.tietoevry.trn2msg.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Path;

/**
 * Not much assertions however it allows to check some logs =)
 * */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileProcessingServiceTest {

    private InputValidator validator = new InputValidator();
    private InputParsingService parsingService = new InputParsingService(validator);
    private FileProcessingService fileProcessingService = new FileProcessingService(parsingService);
    private Path workingDir;

    @BeforeAll
    public void init() {
        this.workingDir = Path.of("", "src", "test", "resources");
    }

    @Test
    public void testReadTransactionFile() {
        Path file = this.workingDir.resolve("tietoevry");
        fileProcessingService.processTransactionFile(file.toAbsolutePath().toString(), workingDir.toAbsolutePath() + "/output/outputFile");
    }

    @Test
    public void testReadTransactionFileEmptyFile() {
        Path file = this.workingDir.resolve("someEmptyFile");
        fileProcessingService.processTransactionFile(file.toAbsolutePath().toString(), null);
    }
}
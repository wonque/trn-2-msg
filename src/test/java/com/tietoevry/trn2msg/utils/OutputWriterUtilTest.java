package com.tietoevry.trn2msg.utils;

import com.tietoevry.trn2msg.model.OutputMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.file.Path;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OutputWriterUtilTest {

    private Path workingDir;

    @BeforeAll
    public void init() {
        this.workingDir = Path.of("", "src", "test", "resources", "output");
    }

    @Test
    public void testWriteOutputToConsole() {
        OutputMessage message = new OutputMessage();
        message.putTotalInfo("some", "total");
        message.putTotalInfo("any", "one");

        OutputWriterUtil.INSTANCE.writeOutput(message, null);

        OutputWriterUtil.INSTANCE.writeOutput(message, workingDir.toAbsolutePath() + "/outputFile");
    }
}
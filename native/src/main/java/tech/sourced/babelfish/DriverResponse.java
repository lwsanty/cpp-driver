package tech.sourced.babelfish;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.*;
import java.util.ArrayList;


public class DriverResponse {
    static class ResponseSendException extends IOException {
        ResponseSendException(Throwable e) {
            super(e);
        }
    }

    public String driver = "1.0.0";
    public String language = "C++";
    public String languageVersion = "14";
    public String status = "ok";
    public ArrayList<String> errors = new ArrayList<String>(0);
    @JsonProperty("ast")

    private TranslationUnit translationUnit;
    private IExchangeFormatWritter formatWritter;

    DriverResponse(IExchangeFormatWritter mapper) {
        this.formatWritter = mapper;
    }

    DriverResponse(String driver, String language, String languageVersion, IExchangeFormatWritter mapper) {
        this.driver = driver;
        this.language = language;
        this.languageVersion = languageVersion;
        this.formatWritter = mapper;
    }

    void parseCode(EclipseCPPParser parser, String source) {
        translationUnit = parser.parseCPP(source);
    }

    // Note: since we're using the System.out output stream with Jackson, output will
    // start to be written before this call so its not a deterministic "send everything".
    // The reason to not use a ByteArrayOutputStream and send everything in one go is that
    // sometimes memory can grow too much with some files.
    void send() throws ResponseSendException {
        // FIXME: this includes the errors in the already started document
        try {
            formatWritter.writeValue(this);
            OutputStream byteOut = formatWritter.getOutputStream();
            byteOut.flush();
            System.out.write('\n');
        } catch (IOException e) {
            throw new DriverResponse.ResponseSendException(e);
        }
    }

    void sendError(Exception e, String errorString)
            throws IOException {

        translationUnit = null;
        errors.add(e.getClass().getCanonicalName());
        errors.add(errorString + e.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        errors.add(sw.toString());
        status = "fatal";
        send();
    }
}

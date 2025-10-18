package com.app.rest;


import java.util.Date;


public class Response {

    private static final String NO_MESSAGE = "";
    private static final String NO_CONTENT = "No content";

    private final String _message;
    private final Object _content;

    private final Date _timestamp;

    public Response(Object content, String message, Date timeStamp) {
        _content = (content == null ? NO_CONTENT : content);
        _message = (message == null ? NO_MESSAGE : message);
        _timestamp = (timeStamp == null ? new Date() : timeStamp);
    }

    public Response(Object content, String message) {
        _content = (content == null ? NO_CONTENT : content);
        _message = (message == null ? NO_MESSAGE : message);
        _timestamp = new Date();
    }

    public Response(Object content) {
        _content = (content == null ? NO_CONTENT : content);
        _message = NO_MESSAGE;
        _timestamp = new Date();
    }

    public Response(String message) {
        _content = NO_CONTENT;
        _message = (message == null ? NO_MESSAGE : message);
        _timestamp = new Date();
    }

    public Response() {
        _content = NO_CONTENT;
        _message = NO_MESSAGE;
        _timestamp = new Date();
    }

    public String getMessage() {
        return _message;
    }

    public Object getContent() {
        return _content;
    }

    public Date getTimestamp() {
        return _timestamp;
    }

}

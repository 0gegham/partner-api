package com.sparkers.partner.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
        super(msg);
        log.error(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
        log.error(msg + ". Cause: " + cause);
    }
}

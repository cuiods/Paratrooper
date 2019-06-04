package edu.tsinghua.paratrooper.web.handler;

import lombok.Data;

/**
 * Describes argument invalid error
 * @author cuiods
 */
@Data
class ArgInvalidResult {
    private String field;
    private Object rejectedValue;
    private String defaultMessage;
}


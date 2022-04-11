package com.marionete.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage {

    private Date timestamp;
    private String errorMessage;
    private String errorDescription;
    private String errorStatus;

}

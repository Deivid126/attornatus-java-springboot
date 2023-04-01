package com.attornatus.demo.exceptions;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageExceptionHandler {
    private Date timestemp;
    private Integer status;
    private String msg;
}

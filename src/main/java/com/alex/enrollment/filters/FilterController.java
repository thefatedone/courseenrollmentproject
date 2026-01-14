package com.alex.enrollment.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/testing")
public class FilterController {

    @GetMapping("/test-mdc")
    public String testMDC() {
        log.info("Inside Controller");
        log.info("Request ID: " + MDC.get("requestId"));
        return "OK";
    }
}
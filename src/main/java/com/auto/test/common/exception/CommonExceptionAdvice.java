//package com.auto.test.common.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@ControllerAdvice
//@ResponseBody
//public class CommonExceptionAdvice {
//
//  @Autowired
//  private MessageSource messageSource;
//
//  @ResponseStatus(HttpStatus.OK)
//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//    logger.error("参数验证失败:{}", e.getMessage());
//    BindingResult result = e.getBindingResult();
//    List<FieldError> errorList = e.getBindingResult().getFieldErrors();
//    List<String> errorMessages = errorList.stream().map(x->{
//      String itemMessage= messageSource.getMessage(x.getDefaultMessage(), null, x.getDefaultMessage(), LocaleContextHolder.getLocale());
//      return String.format("%s", itemMessage);
//    }).collect(Collectors.toList());
//    return BaseResponse.errorResponse(errorMessages.toString());
//  }
//}
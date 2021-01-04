//package com.auto.test.utils.apitest;
//
//import com.auto.test.model.bo.TApiResult;
//import com.auto.test.model.dto.TApi;
//import com.auto.test.model.po.*;
//import com.auto.test.utils.BeanUtils;
//import com.auto.test.utils.MyStringUtils;
//import io.restassured.RestAssured;
//import io.restassured.config.EncoderConfig;
//import io.restassured.config.HttpClientConfig;
//import io.restassured.http.Header;
//import io.restassured.path.json.JsonPath;
//import io.restassured.path.xml.XmlPath;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//import lombok.extern.slf4j.Slf4j;
//
//import java.net.ConnectException;
//import java.net.URLDecoder;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import static io.restassured.RestAssured.given;
//import static io.restassured.config.JsonConfig.jsonConfig;
//import static io.restassured.config.SSLConfig.sslConfig;
//import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
//
//@Slf4j
//public class RequestExecutor {
//    private TApi tApi;
//    private com.auto.test.model.bo.TApiResult TApiResult;
//    private RequestSpecification requestSpecification;
//    Map<String, Object> gVars = new ConcurrentHashMap<>();
//    Map<String, Object> caseVars = new ConcurrentHashMap<>();
//
//    public RequestExecutor(TApi tApi, Map<String, Object> gVars, Map<String, Object> caseVars) {
//        this.tApi = tApi;
//        TApiResult = new TApiResult();
//        requestSpecification = given();
//        this.gVars = gVars;
//        this.caseVars = caseVars;
//    }
//
//    //trust all hosts regardless if the SSL certificate is invalid
//    private void trustAllHosts() {
//        RestAssured.config = RestAssured.config().sslConfig(sslConfig().relaxedHTTPSValidation());
//        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
//        Map<String, Object> httpClientParams = new HashMap<String, Object>();
//        httpClientParams.put("http.connection.timeout", 60000);
//        httpClientParams.put("http.socket.timeout", 60000);
//        httpClientParams.put("http.connection.manager.timeout", 60000);
//        RestAssured.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().addParams(httpClientParams));
//        RestAssured.config = RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));
//        requestSpecification.config(RestAssured.config);
//    }
//
//    private void applyHeaders() {
//        List<com.auto.test.model.po.Header> headers = tApi.getReqHeader();
//        for (com.auto.test.model.po.Header header : headers) {
//            header.setValue(MyStringUtils.replaceKeyFromMap(header.getValue(), gVars, caseVars));
//            if (header.getKey().equalsIgnoreCase("cookie")) {
//                String[] cookies = header.getValue().split(";");
//                for (String s : cookies) {
//                    String[] c = header.getValue().split("=");
//                    requestSpecification.cookie(c[0], c[1]);
//                }
//            } else {
//                requestSpecification.header(new Header(header.getKey(), header.getValue()));
//            }
//        }
//        TApiResult.setReqHeaders(headers);
//    }
//
//    private void applyQueryParameters() {
//        List<Query> reqQuerys = tApi.getReqQuery();
//        try {
//            for (Query reqQuery : reqQuerys) {
//                reqQuery.setValue(MyStringUtils.replaceKeyFromMap(reqQuery.getValue(), gVars, caseVars));
//                requestSpecification.queryParam(reqQuery.getKey(), URLDecoder.decode(reqQuery.getValue()));
//            }
//            TApiResult.setReqQuery(tApi.getReqQuery());
//        } catch (Exception e) {
//            TApiResult.setException("解析parameters失败：" + e.getMessage());
//        }
//    }
//
//    private void applyFormParam() {
//        try {
//            List<BodyData> reqBodyDatas = tApi.getReqBodyData();
//            for (BodyData reqBodyData : reqBodyDatas) {
//                reqBodyData.setValue(MyStringUtils.replaceKeyFromMap(reqBodyData.getValue(), gVars, caseVars));
//                requestSpecification.formParam(reqBodyData.getKey(), reqBodyData.getValue());
//            }
//            TApiResult.setReqBodyData(tApi.getReqBodyData());
//        } catch (Exception e) {
//            TApiResult.setException("解析表单数据失败：" + e.getMessage());
//        }
//    }
//
//    private void applyRawParam() {
//        tApi.setReqBodyJson(MyStringUtils.replaceKeyFromMap(tApi.getReqBodyJson(), gVars, caseVars));
//        TApiResult.setReqBodyJson(tApi.getReqBodyJson());
//        requestSpecification = requestSpecification.body(tApi.getReqBodyJson());
//    }
//
//    public TApiResult executeHttpRequest() {
//        trustAllHosts();
//        applyHeaders();
//        applyQueryParameters();
//        tApi.setDomain(MyStringUtils.replaceKeyFromMap(tApi.getDomain(), gVars, caseVars));
//        TApiResult.setReqMethod(tApi.getMethod());
//        if (tApi.getMethod().equalsIgnoreCase("post")) {
//            TApiResult.setReqBodyType(tApi.getReqBodyType());
//            if (tApi.getReqBodyType().equals("form")) {
//                applyFormParam();
//            } else if (tApi.getReqBodyType().equals("raw")) {
//                applyRawParam();
//            }
//        }
//        Response response = null;
//        String url = tApi.getDomain() + tApi.getPath();
//        if (url.indexOf("?") != -1) {
//            url = url.substring(0, url.indexOf("?"));
//        }
//        TApiResult.setReqUrl(url);
//        try {
//            switch (tApi.getMethod().toUpperCase()) {
//                case "GET":
//                    response = requestSpecification.when().get(url);
//                    break;
//                case "POST":
//                    response = requestSpecification.when().post(url);
//                    break;
//                case "PATCH":
//                    response = requestSpecification.when().patch(url);
//                    break;
//                case "DELETE":
//                    response = requestSpecification.when().delete(url);
//                    break;
//                case "PUT":
//                    response = requestSpecification.when().put(url);
//                    break;
//                case "OPTIONS":
//                    response = requestSpecification.when().options(url);
//                    break;
//                case "HEAD":
//                    response = requestSpecification.when().head(url);
//                    break;
//                default:
//                    TApiResult.setException(String.format("不支持这个请求 %s.", url));
//            }
//        } catch (Exception e) {
//            TApiResult.setResultType(-1);
//            if (e instanceof UnknownHostException) {
//                TApiResult.setException("请求异常,URL[" + e.getMessage() + "]无法连接");
//            } else if (e instanceof ConnectException) {
//                TApiResult.setException("请求异常,URL[" + tApi.getDomain() + "]无法连接");
//            } else {
//                TApiResult.setException("请求异常：" + e.getMessage());
//            }
//            e.printStackTrace();
//            return TApiResult;
//        }
//        TApiResult.setRspStatusCode(response.getStatusCode());
//        TApiResult.setRspTime(response.getTime());
//        TApiResult.setRspBodySize(response.getBody().asByteArray().length);
//        List<com.auto.test.model.po.Header> listHeader = new ArrayList<>();
//        response.getHeaders().asList().forEach(x -> {
//            com.auto.test.model.po.Header header = new com.auto.test.model.po.Header();
//            header.setKey(x.getName());
//            header.setValue(x.getValue());
//            listHeader.add(header);
//        });
//        TApiResult.setRspHeaders(listHeader);
//        TApiResult.setRspBody(response.getBody().prettyPrint());
//        TApiResult.setRspBodyType(StringUtilHelper.checkStringFormat(TApiResult.getRspBody()));
//        Boolean handResult = handleAssert(); //断言
//        handleExtract(); //提取参数
//        if (handResult) {
//            //响应断言成功
//            TApiResult.setResultType(1);
//        } else {
//            //断言失败
//            TApiResult.setResultType(0);
//        }
//        return TApiResult;
//    }
//
//
//    //提取参数
//    private void handleExtract() {
//        List<ExtractResult> extracts = new ArrayList();
//        List<Extract> reqExtract = tApi.getReqExtract();
//        for (Extract extract : reqExtract) {
//            String extractExpress = extract.getExtractExpress() == null ? "" : extract.getExtractExpress();
//            ExtractResult extractResult = new ExtractResult();
//            BeanUtils.copyBeanProp(extractResult, extract);
//            switch (extract.getDataSource()) {
//                case "bodyJson":
//                    if (TApiResult.getRspBodyType().equals("json")) {
//                        JsonPath json = new JsonPath(TApiResult.getRspBody());
//                        Object value = json.get(extractExpress);
//                        String realType = ApiUtil.getObjRealType(value);
//                        if (realType.equals("null")) {
//                            extractResult.setRealType(realType);
//                            extractResult.setRealValue("");
//                        } else {
//                            extractResult.setRealType(realType);
//                            extractResult.setRealValue(value.toString());
//                        }
//                    } else {
//                        extractResult.setRealType("null");
//                        extractResult.setRealValue("");
//                    }
//                    break;
//                case "bodyXml":
//                    if (TApiResult.getRspBodyType().equals("bodyXml")) {
//                        XmlPath xmlPath = new XmlPath(TApiResult.getRspBody());
//                        Object value = xmlPath.get(extractExpress);
//                        String realType = ApiUtil.getObjRealType(value);
//                        if (realType.equals("null")) {
//                            extractResult.setRealType("null");
//                            extractResult.setRealValue("");
//                        } else {
//                            extractResult.setRealType(realType);
//                            extractResult.setRealValue(value.toString());
//                        }
//                    } else {
//                        extractResult.setRealType("null");
//                        extractResult.setRealValue("");
//                    }
//                    break;
//                case "bodyReg":
//                    String value = MyStringUtils.extractValue(TApiResult.getRspBody(), extractExpress, 1);
//                    if (value == null) {
//                        extractResult.setRealType("null");
//                        extractResult.setRealValue("");
//                    } else {
//                        extractResult.setRealType("string");
//                        extractResult.setRealValue(value);
//                    }
//                    break;
//                case "code":
//                    String realType = ApiUtil.getObjRealType(TApiResult.getRspStatusCode());
//                    if (realType.equals("null")) {
//                        extractResult.setRealType("null");
//                        extractResult.setRealValue("");
//                    } else {
//                        extractResult.setRealType(realType);
//                        extractResult.setRealValue(String.valueOf(TApiResult.getRspStatusCode()));
//                    }
//                    break;
//                case "header":
//                    Header header = TApiResult.getRspHeaders().stream().filter(x -> x.getKey().equals(extractExpress)).findFirst().orElse(null);
//                    if (header == null) {
//                        extractResult.setRealType("null");
//                    } else {
//                        extractResult.setRealType("string");
//                    }
//                    extractResult.setRealValue(header.getValue());
//                    break;
//                default:
//            }
//            caseVars.put(extractResult.getVarName(), extractResult.getRealValue());
//            extracts.add(extractResult);
//        }
//        TApiResult.setRspExtracts(extracts);
//    }
//
//    //批量断言
//    private Boolean handleAssert() {
//        boolean flag = true;
//        List<AssertResult> assertResults = new ArrayList();
//        List<Assert> reqAssert = tApi.getReqAssert();
//        for (Assert iassert : reqAssert) {
//            AssertResult assertResult = assertion(iassert);
//            if (!assertResult.getResult()) {
//                flag = false;
//            }
//            assertResults.add(assertResult);
//        }
//        TApiResult.setRspAsserts(assertResults);
//        return flag;
//    }
//
//
//    //单个断言
//    private AssertResult assertion(Assert assertion) {
//        //处理接口变量类型的参数
//        if (assertion.getExtractType().equals("apiVar")) {
//            String expectValue = assertion.getExpectValue();
//            if (expectValue == null) {
//                assertion.setExtractType("null");
//                assertion.setExpectValue("");
//            } else {
//                Object o = caseVars.get(expectValue);
//                String objRealType = ApiUtil.getObjRealType(o);
//                assertion.setExtractType(objRealType);
//                assertion.setExpectValue(o.toString());
//            }
//        }
//        AssertResult assertResult = new AssertResult();
//        BeanUtils.copyBeanProp(assertResult, assertion);
//        String extractExpress = assertion.getExtractExpress() == null ? "" : assertion.getExtractExpress();
//        switch (assertion.getDataSource()) {
//            case "bodyJson":
//                if (TApiResult.getRspBodyType().equals("json")) {
//                    JsonPath json = new JsonPath(TApiResult.getRspBody());
//                    Object value = json.get(extractExpress);
//                    String realType = ApiUtil.getObjRealType(value);
//                    if (realType.equals("null")) {
//                        assertResult.setRealType(realType);
//                        assertResult.setRealValue("");
//                    } else {
//                        assertResult.setRealType(realType);
//                        assertResult.setRealValue(value.toString());
//                    }
//                } else {
//                    assertResult.setRealType("null");
//                    assertResult.setRealValue("");
//                }
//                break;
//            case "bodyXml":
//                if (TApiResult.getRspBodyType().equals("bodyXml")) {
//                    XmlPath xmlPath = new XmlPath(TApiResult.getRspBody());
//                    Object value = xmlPath.get(extractExpress);
//                    String realType = ApiUtil.getObjRealType(value);
//                    if (realType.equals("null")) {
//                        assertResult.setRealType(realType);
//                        assertResult.setRealValue("");
//                    } else {
//                        assertResult.setRealType(realType);
//                        assertResult.setRealValue(value.toString());
//                    }
//                } else {
//                    assertResult.setRealType("null");
//                    assertResult.setRealValue("");
//                }
//                break;
//            case "bodyReg":
//                String value = MyStringUtils.extractValue(TApiResult.getRspBody(), assertion.getExtractExpress(), 1);
//                if (value == null) {
//                    assertResult.setRealType("null");
//                    assertResult.setRealValue("");
//                } else {
//                    assertResult.setRealType("string");
//                    assertResult.setRealValue(value);
//                }
//                break;
//            case "code":
//                String realType = ApiUtil.getObjRealType(TApiResult.getRspStatusCode());
//                if (realType.equals("null")) {
//                    assertResult.setRealType("null");
//                    assertResult.setRealValue("");
//                } else {
//                    assertResult.setRealType(realType);
//                    assertResult.setRealValue(String.valueOf(TApiResult.getRspStatusCode()));
//                }
//                break;
//            case "header":
//                Header header = TApiResult.getRspHeaders().stream().filter(x -> x.getKey().equals(extractExpress)).findFirst().orElse(null);
//                if (header == null) {
//                    assertResult.setRealType("null");
//                } else {
//                    assertResult.setRealType("string");
//                }
//                assertResult.setRealValue(header.getValue());
//                break;
//            default:
//        }
//        boolean assertionResult = ApiUtil.getAssertionResult(assertion, assertResult.getRealType(), assertResult.getRealValue());
//        assertResult.setResult(assertionResult);
//        return assertResult;
//    }
//}

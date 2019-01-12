package com.test.res.controller;


import com.test.res.datasource.entity.TestEntity;
import com.test.res.exceptions.TestException;
import com.test.res.service.TestService;
import com.test.res.util.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 9/1/2019.
 */
@RestController
@RequestMapping("/testApi")
public class TestController {
    private static final Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @Value("${token.test}")
    private String tokenTEST;

    @RequestMapping(
            value = "/retrieveBalance",
            method = RequestMethod.POST)
    public Map<String, Object> retrieveBalance(@RequestBody Map<String, Object> payload, HttpServletRequest request)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("result", false);
        String ipAddr = request.getRemoteAddr();
        //do some IP filter if you want
        Timestamp nowTs = new Timestamp(new Date().getTime());
//        messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_IN, payload.toString());
        if(!payload.get("token").equals(tokenTEST)){
            result.put("errorCode", ErrorCode.INVALID_PARAM+":token");
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }

        if (
                        payload.get("UserID") == null//this need to ask andrew to send encrypted userID
                ) {
            result.put("errorCode", ErrorCode.INVALID_PARAM+":UserID");
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }

        try {
            int userId = Integer.parseInt((String) payload.get("UserID"));

            TestEntity entity = testService.getTestEntityById(userId);



            result.put("userId", entity.getUserId());
            result.put("amount", entity.getAmount());

            result.put("result", true);

        }catch (TestException ex) {
            ex.printStackTrace();
            logger.error(ex.getLocalizedMessage());
            result.put("errorCode", ex.getErrorCode());//here get the error code from service layer level

//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }
        catch (DataAccessException ex) {
            ex.printStackTrace();
            logger.error(ex.getLocalizedMessage());
            result.put("errorCode", ErrorCode.INVALID_PARAM);
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("errorCode", ErrorCode.INVALID_PARAM);
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }


//        System.out.println(payload);
//        messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
        return result;
    }


    @RequestMapping(
            value = "/changeBalance",
            method = RequestMethod.POST)
    public Map<String, Object> changeBalance(@RequestBody Map<String, Object> payload, HttpServletRequest request)
            throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("result", false);
        String ipAddr = request.getRemoteAddr();
        //do some IP filter if you want
        Timestamp nowTs = new Timestamp(new Date().getTime());
//        messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_IN, payload.toString());
        if(!payload.get("token").equals(tokenTEST)){
            result.put("errorCode", ErrorCode.INVALID_PARAM+":token");
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }

        if (
                payload.get("UserID") == null//this need to ask andrew to send encrypted userID
                ) {
            result.put("errorCode", ErrorCode.INVALID_PARAM+":UserID");
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }


        if (
                payload.get("changeAmount") == null//this need to ask andrew to send encrypted userID
                ) {
            result.put("errorCode", ErrorCode.INVALID_PARAM+":changeAmount");
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }

        try {
            int userId = Integer.parseInt((String) payload.get("UserID"));
            BigDecimal changeAmount = new BigDecimal((String) payload.get("changeAmount"));

            Map<String, Object> resultMap = testService.changeTestAmountValue(userId, changeAmount);



            return resultMap;

//            result.put("result", true);

        }catch (TestException ex) {
            ex.printStackTrace();
            logger.error(ex.getLocalizedMessage());
            result.put("errorCode", ex.getErrorCode());//here get the error code from service layer level
            result.put("errorMessage", "what ever error message match with this code");
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }
        catch (DataAccessException ex) {
            ex.printStackTrace();
            logger.error(ex.getLocalizedMessage());
            result.put("errorCode", ErrorCode.INVALID_PARAM);
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            result.put("errorCode", ErrorCode.INVALID_PARAM);
//            messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
            return result;
        }


//        System.out.println(payload);
//        messageService.addCRMMessageHistory(ipAddr, nowTs, Constants.MESSAGE_TYPE_OUT, result.toString());
//        return result;
    }
}

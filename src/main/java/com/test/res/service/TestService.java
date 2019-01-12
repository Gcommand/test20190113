package com.test.res.service;

import com.test.res.datasource.entity.TestEntity;
import com.test.res.datasource.repository.TestEntityRepository;
import com.test.res.exceptions.TestException;
import com.test.res.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 9/1/2019.
 */
@Service
public class TestService {

    @Autowired
    TestEntityRepository testEntityRepository;

    @Transactional
    public TestEntity getTestEntityById(int userId){
        try {
            TestEntity result = testEntityRepository.getOne(userId);
            return result;

        }catch (DataAccessException ex){
            ex.printStackTrace();
            throw new TestException(ErrorCode.SOME_OTHER_ERROR_CODE, "SOME ERROR CODE of Data Access in Service Layer", ex);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new TestException(ErrorCode.SOME_OTHER_ERROR_CODE, "SOME other ERROR CODE...", ex);
        }

    }

    @Transactional
    public Map<String, Object> changeTestAmountValue(int userId, BigDecimal changeAmount){
        try{
            //add or minus value to the test amount and return result map
            Map<String, Object> resultMap = new HashMap<>();
            TestEntity entity = getTestEntityById(userId);
            if(entity==null){
                throw new TestException(ErrorCode.SOME_OTHER_ERROR_CODE,"SOME ERROR CODE of user not found in Service Layer", null);
            }
            entity.setAmount(entity.getAmount().add(changeAmount));

            testEntityRepository.save(entity);

            resultMap.put("result", true);
            resultMap.put("userId", userId);
            resultMap.put("newAmount", entity.getAmount());

            return resultMap;
        } catch (DataAccessException ex){
            ex.printStackTrace();
            throw new TestException(ErrorCode.SOME_OTHER_ERROR_CODE, "SOME ERROR CODE of Data Access in Service Layer", ex);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new TestException(ErrorCode.SOME_OTHER_ERROR_CODE, "SOME other ERROR CODE...", ex);
        }
    }
}

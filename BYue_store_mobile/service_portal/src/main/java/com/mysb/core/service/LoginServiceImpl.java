package com.mysb.core.service;

import com.mysb.core.dao.customer.CustomerDao;
import com.mysb.core.dao.token.TokenDao;
import com.mysb.core.interfac.LoginFeignClient;
import com.mysb.core.pojo.customer.Customer;
import com.mysb.core.pojo.customer.CustomerQuery;
import com.mysb.core.pojo.entry.Response;
import com.mysb.core.pojo.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class LoginServiceImpl implements LoginFeignClient {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Customer findUserByUsername(String username) {
        CustomerQuery query = new CustomerQuery();
        CustomerQuery.Criteria criteria = query.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<Customer> customers = customerDao.selectByExample(query);
        if(customers.size() > 0){
            return customers.get(0);
        }
        return null;
    }

    @Override
    public void saveToken(String token, String name) {
        Token tokenSave = new Token();
        tokenSave.setSellerId(name);
        tokenSave.setToken(token);
        tokenDao.insertSelective(tokenSave);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }



}

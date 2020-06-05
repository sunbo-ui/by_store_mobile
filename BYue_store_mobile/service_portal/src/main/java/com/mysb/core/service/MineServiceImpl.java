package com.mysb.core.service;

import com.mysb.core.dao.customer.CustomerDao;
import com.mysb.core.dao.token.TokenDao;
import com.mysb.core.interfac.MineFeignClient;
import com.mysb.core.pojo.customer.Customer;
import com.mysb.core.pojo.customer.CustomerQuery;
import com.mysb.core.pojo.log.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MineServiceImpl implements MineFeignClient{
    @Autowired
    private CustomerDao customerDao;

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
    public PayLog findOrderByUsername(String username) {
        return (PayLog)redisTemplate.boundHashOps("payLog").get(username);
    }
}

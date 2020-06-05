package com.mysb.core.service;

import com.mysb.core.dao.address.AddressDao;
import com.mysb.core.interfac.AddressFeignClient;
import com.mysb.core.pojo.address.Address;
import com.mysb.core.pojo.address.AddressQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Transactional
@RestController
public class AddressServiceImpl implements AddressFeignClient {

    @Autowired
    private AddressDao addressDao;

    public List<Address> findAddressByUserId(String userId) {
        if (userId == null) {
            return null;
        } else {
            AddressQuery addressQuery = new AddressQuery();
            AddressQuery.Criteria criteria = addressQuery.createCriteria();
            criteria.andUserIdEqualTo(userId);
            return addressDao.selectByExample(addressQuery);
        }
    }

    @Override
    public void saveAddress(String username, Address address) {
        address.setUserId(username);
        address.setCreateDate(new Date());
        AddressQuery addressQuery = new AddressQuery();
        AddressQuery.Criteria criteria = addressQuery.createCriteria();
        criteria.andUserIdEqualTo(address.getUserId());
        List<Address> addresses = addressDao.selectByExample(addressQuery);
        if(address.getIsDefault().equals("1")){
            for (Address address1 : addresses) {
                if(address1.getId() == address.getId()){
                    continue;
                }
                address1.setIsDefault("0");
                addressDao.updateByPrimaryKeySelective(address1);
            }
        }
        addressDao.insertSelective(address);
    }

    @Override
    public void editAddress(Address address) {
        address.setCreateDate(new Date());
        AddressQuery addressQuery = new AddressQuery();
        AddressQuery.Criteria criteria = addressQuery.createCriteria();
        criteria.andUserIdEqualTo(address.getUserId());
        List<Address> addresses = addressDao.selectByExample(addressQuery);
        if(address.getIsDefault().equals("1")){
            for (Address address1 : addresses) {
                if(address1.getId() == address.getId()){
                    continue;
                }
                address1.setIsDefault("0");
                addressDao.updateByPrimaryKeySelective(address1);
            }
        }
        addressDao.updateByPrimaryKeySelective(address);
    }

    @Override
    public Address findOneAddress(Long id) {
        return addressDao.selectByPrimaryKey(id);
    }

    @Override
    public void removeAddress(Long id) {
        addressDao.deleteByPrimaryKey(id);
    }

    @Override
    public void updateDefault(Long id,String username) {
        Address address = addressDao.selectByPrimaryKey(id);
        address.setIsDefault("1");
        AddressQuery addressQuery = new AddressQuery();
        AddressQuery.Criteria criteria = addressQuery.createCriteria();
        criteria.andUserIdEqualTo(username);
        List<Address> addresses = addressDao.selectByExample(addressQuery);
            for (Address address1 : addresses) {
                if(address1.getId() == address.getId()){
                    continue;
                }
                address1.setIsDefault("0");
                addressDao.updateByPrimaryKeySelective(address1);
            }
        addressDao.updateByPrimaryKeySelective(address);
    }
}

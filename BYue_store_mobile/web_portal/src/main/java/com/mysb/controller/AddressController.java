package com.mysb.controller;

import com.mysb.core.interfac.AddressFeignClient;
import com.mysb.core.pojo.address.Address;
import com.mysb.core.pojo.entry.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressFeignClient addressFeignClient;

    @RequestMapping("/findAddressByUserId")
    public List<Address> findAddressByUserId(String username) {
        return addressFeignClient.findAddressByUserId(username);
    }

    @RequestMapping("/saveAddress")
    public Result saveAddress(String username, @RequestBody Address address) {
        try {
            addressFeignClient.saveAddress(username, address);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/findOneAddress")
    public Address findOneAddress(Long id) {
        return addressFeignClient.findOneAddress(id);
    }

    @RequestMapping("/editAddress")
    public Result editAddress(@RequestBody Address address) {
        try {
            addressFeignClient.editAddress(address);
            return new Result(true, "更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }
    @RequestMapping("/removeAddress")
    public Result removeAddress(Long id) {
        try {
            addressFeignClient.removeAddress(id);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }
    @RequestMapping("/updateDefault")
    public Result updateDefault(Long id,String username) {
        try {
            addressFeignClient.updateDefault(id,username);
            return new Result(true, "更新成功");
        } catch (Exception e) {
            return new Result(false, "更新失败");
        }
    }
}

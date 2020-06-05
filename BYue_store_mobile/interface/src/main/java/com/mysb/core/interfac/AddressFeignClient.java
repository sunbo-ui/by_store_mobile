package com.mysb.core.interfac;

import com.mysb.core.fallback.AddressFeignClientFallBack;
import com.mysb.core.pojo.address.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-portal",fallbackFactory = AddressFeignClientFallBack.class)
public interface AddressFeignClient {
    @RequestMapping("/address/findAddressByUserId")
    public List<Address> findAddressByUserId(@RequestParam("userId") String userId);

    @RequestMapping("/saveAddress")

    void saveAddress(@RequestParam("username")String username, @SpringQueryMap Address address);
    @RequestMapping("/editAddress")
    void editAddress( @SpringQueryMap Address address);

    @RequestMapping("/findOneAddress")
    Address findOneAddress(@RequestParam("id") Long id);

    @RequestMapping("/removeAddress")
    void removeAddress(@RequestParam("id") Long id);

    @RequestMapping("/updateDefault")
    void updateDefault(@RequestParam("id") Long id, @RequestParam("username")String username);
}

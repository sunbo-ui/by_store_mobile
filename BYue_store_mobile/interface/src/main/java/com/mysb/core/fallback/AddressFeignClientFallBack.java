package com.mysb.core.fallback;

import com.mysb.core.interfac.AddressFeignClient;
import com.mysb.core.pojo.address.Address;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressFeignClientFallBack implements FallbackFactory<AddressFeignClient> {

    @Override
    public AddressFeignClient create(Throwable e) {
        return new AddressFeignClient() {

            @Override
            public List<Address> findAddressByUserId(String userId) {
                System.out.println(e);
                return null;
            }

            @Override
            public void saveAddress(String username, Address address) {
            }

            @Override
            public void editAddress(Address address) {

            }

            @Override
            public Address findOneAddress(Long id) {
                return null;
            }

            @Override
            public void removeAddress(Long id) {

            }

            @Override
            public void updateDefault(Long id, String username) {

            }
        };

    }
}

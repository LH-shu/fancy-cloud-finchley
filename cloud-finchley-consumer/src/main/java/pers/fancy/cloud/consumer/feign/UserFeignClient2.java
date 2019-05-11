package pers.fancy.cloud.consumer.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pers.fancy.cloud.consumer.entity.User;


@FeignClient(name = "cloud-finchley-provider", fallback = FeignClientFallback.class)
public interface UserFeignClient2 {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    User findById(@PathVariable("id") Long id);
}


@Component
class FeignClientFallback implements UserFeignClient2 {
    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(-1L);
        user.setUsername("默认用户");
        return user;
    }
}
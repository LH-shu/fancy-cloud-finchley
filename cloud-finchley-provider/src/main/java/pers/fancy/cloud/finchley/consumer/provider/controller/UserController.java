//package pers.fancy.cloud.finchley.consumer.provider.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import pers.fancy.cloud.finchley.consumer.provider.entity.User;
//import pers.fancy.cloud.finchley.consumer.provider.repository.UserRepository;
//
//import java.util.Optional;
//
//@RestController
//public class UserController {
//  @Autowired
//  private UserRepository userRepository;
//
//  @GetMapping("/{id}")
//  public Optional<User> findById(@PathVariable Long id) {
//    return this.userRepository.findById(id);
//  }
//}

package pers.fancy.cloud.finchley.consumer.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pers.fancy.cloud.finchley.consumer.provider.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

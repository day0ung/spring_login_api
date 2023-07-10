package com.made.api.repository;

import com.made.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


//@Repository라는 어노테이션이 없어도 Ioc가능 (상속받았기때문에)
public interface UserRepository extends JpaRepository<User, Long> {

}

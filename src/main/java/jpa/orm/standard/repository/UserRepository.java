package jpa.orm.standard.repository;

import jpa.orm.standard.domain.batchsize.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

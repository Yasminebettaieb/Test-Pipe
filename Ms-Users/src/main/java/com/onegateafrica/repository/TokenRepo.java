package com.onegateafrica.repository;

import com.onegateafrica.entity.ConfirmationToken;
import com.onegateafrica.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken findConfirmationTokenByUser(Users user);
}

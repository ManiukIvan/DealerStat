package com.dealerstat.repositories;

import com.dealerstat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    @Query(nativeQuery = true,
            value = "SELECT sum(appraisal)/count(appraisal) "
                    + "FROM comment"
                    + " WHERE comment.user_id = :userId AND comment.approved = true")
    Double getUserRatingByAppraisalOfApprovedComments(@Param("userId") Long userId);

    @Query(nativeQuery = true,
            value = "SELECT user.id,user.first_name,user.last_name,user.password,user.email,user.created_date,user.role "
                    + "FROM dealerstat.user "
                    + "JOIN dealerstat.comment "
                    + "where user.id = comment.user_id and comment.approved = true "
                    + "Group BY user_id Order BY Sum(comment.appraisal)/Count(comment.appraisal) DESC")
    List<User> findAllSortedByRating();

    Optional<User> getUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("update User user set user.password = :password where user.email = :email")
    void changeUserPasswordForUserWithEmail(@Param("email") String email, @Param("password") String password);
}

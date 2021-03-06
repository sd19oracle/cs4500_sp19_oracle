package edu.neu.cs4500.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs4500.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query(value="SELECT user FROM User user")
	public List<User> findAllUsers();
	@Query(value="SELECT user FROM User user WHERE user.id=:id")
	public User findUserById(@Param("id") Integer id);
	@Query(value="SELECT user FROM User user WHERE user.email=:email")
	public User findByEmail(@Param("email") String email);
	@Query(value="SELECT user FROM User user WHERE user.email=:email AND user.password=:password")
	public Optional<User> matchCredentials(@Param("email") String email, @Param("password") String password);
	@Query(value="SELECT * from users where users.role = 'provider' and (users.email LIKE CONCAT('%',:email,'%') " +
					"or users.first_name LIKE CONCAT('%',:email,'%') or users.last_name LIKE CONCAT('%',:email,'%'))", nativeQuery = true)
	public List<User> findAllProvidersNameMatch(@Param("email") String email);
	@Query(value="SELECT * from users where users.role = 'provider'", nativeQuery = true)
	public List<User> findAllProviders();
}

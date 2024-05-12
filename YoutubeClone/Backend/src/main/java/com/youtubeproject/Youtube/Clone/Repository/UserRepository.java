package com.youtubeproject.Youtube.Clone.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.youtubeproject.Youtube.Clone.Model.User;

/**
 * Spring Data MongoDB repository for {@link User}.
 * <p>
 * This interface handles the data access layer of the application concerning user objects. It provides
 * automated CRUD operations by extending {@link MongoRepository}. It also includes a custom
 * query method for finding a user by their OAuth2 id.
 * </p>
 */
public interface UserRepository extends MongoRepository<User, String> {
	/**
     * Retrieves a user by their OAuth2 id called sub.
     *
     * @param sub The OAuth2 id as a {@link String} which is unique for each user.
     * @return An {@link Optional} containing the {@link User} if found, or empty if no user is found with the given sub.
     */
	Optional<User> findBySub(String sub);
}

package com.gfa.wc.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.gfa.wc.models.entities.Gambler;
import com.gfa.wc.repositories.GamblerRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GamblerService {

  private GamblerRepository gamblerRepository;

  public GamblerService(GamblerRepository gamblerRepository) {
    this.gamblerRepository = gamblerRepository;
  }

  public Gambler save(Gambler user) throws IllegalArgumentException {
    validateUser(user);
    String hashedPassword = hashPassword(user.getPassword());
    user.setPassword(hashedPassword);
    return gamblerRepository.save(user);
  }

  public void login(Gambler loginUser) throws NoSuchElementException, IllegalArgumentException {
    if (loginUser == null || loginUser.getUsername() == null || loginUser.getPassword() == null) {
      throw new IllegalArgumentException("Invalid login data.");
    }
    Optional<Gambler> gambler = gamblerRepository.findByUsername(loginUser.getUsername());
    if (!gambler.isPresent()) throw new NoSuchElementException("No such user.");
    if (!isValidPassword(gambler.get(), loginUser.getPassword())) throw new IllegalArgumentException("Invalid password.");
  }

  private String hashPassword(String password) {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray());
  }

  private boolean isValidPassword(Gambler gambler, String password) {
    return BCrypt.verifyer().verify(password.toCharArray(), gambler.getPassword()).verified;
  }

  private void validateUser(Gambler user) throws IllegalArgumentException {
    if (user == null) throw new IllegalArgumentException("Invalid user.");
    if (user.getUsername() == null
            || user.getUsername().trim().isEmpty()
            || user.getUsername().contains(" ")
            || gamblerRepository.findByUsername(user.getUsername()).isPresent()
    ) {
      throw new IllegalArgumentException("Invalid username.");
    }
    if (user.getName() == null || user.getName().trim().isEmpty()) throw new IllegalArgumentException("Invalid name.");
    if (user.getPassword() == null || user.getPassword().trim().length() < 6) throw new IllegalArgumentException("Invalid password.");
  }

}

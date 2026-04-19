package com.henryqi.voicematching.repository;

import com.henryqi.voicematching.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}

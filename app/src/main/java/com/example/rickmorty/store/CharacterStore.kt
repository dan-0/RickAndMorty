package com.example.rickmorty.store

import com.apollographql.apollo3.api.Optional
import com.example.rickmorty.persistence.entity.DbCharacter
import org.mobilenativefoundation.store.store5.Store

class CharacterStore(
  store: Store<String, List<DbCharacter>>
) : Store<String, List<DbCharacter>> by store

class CharacterInfoStore(
  store: Store<String, Optional<DbCharacter>>
) : Store<String, Optional<DbCharacter>> by store
package com.example.rickmorty.store

import com.example.rickmorty.data.character.DbCharacter
import org.mobilenativefoundation.store.store5.Store

class CharacterStore(
  store: Store<String, List<DbCharacter>>
) : Store<String, List<DbCharacter>> by store
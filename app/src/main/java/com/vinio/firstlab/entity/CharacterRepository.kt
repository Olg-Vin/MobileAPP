package com.vinio.firstlab.entity

import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val characterDao: CharacterDao) {
    suspend fun insertCharacter(character: CharacterEntity): Long {
        return characterDao.insertCharacter(character)
    }
    suspend fun insertCharacters(characters: List<CharacterEntity>) {
        characterDao.insertCharacters(characters)
    }
    suspend fun updateCharacter(character: CharacterEntity) {
        characterDao.updateCharacter(character)
    }
    suspend fun deleteCharacter(character: CharacterEntity) {
        characterDao.deleteCharacter(character)
    }
    suspend fun getCharacterById(id: Int): CharacterEntity? {
        return characterDao.getCharacterById(id)
    }
    suspend fun getCharacterByName(name: String): CharacterEntity? {
        return characterDao.getCharacterByName(name)
    }
    suspend fun getAllCharacters(): List<CharacterEntity> {
        return characterDao.getAllCharacters()
    }

    fun getAllCharactersFlow(): Flow<List<CharacterEntity>> {
        return characterDao.getAllCharactersFlow()
    }

    suspend fun deleteAllCharacters() {
        characterDao.deleteAllCharacters()
    }
}

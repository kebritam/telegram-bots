// SPDX-License-Identifier: MIT

package com.teimour.whisper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author kebritam
 * Project echo-bot
 */

public class UserDataAccessMap implements UserDataAccess {

    private final Map<Long, UUID> idMap;

    public UserDataAccessMap() {
        this.idMap = new HashMap<>();
    }

    @Override
    public boolean containsKey(Long telegramId) {
        return idMap.containsKey(telegramId);
    }

    @Override
    public UUID getUuid(Long telegramId) {
        return idMap.get(telegramId);
    }

    @Override
    public Optional<UUID> getUuidOrElse(Long telegramId) {
        return Optional.ofNullable(getUuid(telegramId));
    }

    @Override
    public Long getTelegramId(UUID uuid) {
        return idMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(uuid))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow()
                ;
    }

    @Override
    public void save(Long telegramId, UUID uuId) {
        idMap.put(telegramId, uuId);
    }
}

// SPDX-License-Identifier: MIT

package com.teimour.whisper;

import java.util.Optional;
import java.util.UUID;

/**
 * @author kebritam
 * Project echo-bot
 */

public interface UserDataAccess {

    boolean containsKey(Long telegramId);

    UUID getUuid(Long telegramId);

    Optional<UUID> getUuidOrElse(Long telegramId);

    Long getTelegramId(UUID uuid);

    void save(Long telegramId, UUID uuId);
}

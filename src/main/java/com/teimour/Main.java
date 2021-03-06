// SPDX-License-Identifier: MIT

package com.teimour;

import com.teimour.insult.Insult;
import com.teimour.insult.InsultDataApi;
import com.teimour.whisper.UserDataAccessMap;
import com.teimour.whisper.Whisper;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author kebritam
 * Project echo-bot
 */

public class Main {

    public static void main(String[] args) {
        try {
            TelegramBotsApi bots = new TelegramBotsApi(DefaultBotSession.class);

            bots.registerBot(new Whisper(new UserDataAccessMap()));
            bots.registerBot(new Insult(new InsultDataApi()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

// SPDX-License-Identifier: MIT

package com.teimour.insult;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;

/**
 * @author kebritam
 * Project echo-bot
 */

public class Insult extends AbilityBot {

    private final static String BOT_TOKEN = "";
    private final static String BOT_USERNAME = "";
    private static final int CREATOR_ID = 0;

    private final InsultData insultData;

    public Insult(InsultData insultData) {
        super(BOT_TOKEN, BOT_USERNAME);
        this.insultData = insultData;
    }

    @Override
    public long creatorId() {
        return CREATOR_ID;
    }

    public Ability start() {
        return Ability.builder()
                .name("start")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .input(0)
                .info("gives basic information about bot.")
                .action(ctx -> silent.send("you can get your joy line by entering /joy", ctx.chatId()))
                .build();
    }

    public Ability newInsult() {
        return Ability.builder()
                .name("joy")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .input(0)
                .info("gets insult message")
                .action(ctx -> silent.send(insultData.getInsult(), ctx.chatId()))
                .build();
    }
}

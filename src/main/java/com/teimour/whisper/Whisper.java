// SPDX-License-Identifier: MIT

package com.teimour.whisper;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;
import java.util.UUID;


/**
 * @author kebritam
 * Project echo-bot
 */

public class Whisper extends AbilityBot {

    private static final String BOT_TOKEN = "";
    private static final String BOT_USERNAME = "";
    private static final int CREATOR_ID = 0;

    private static final String BOT_USERID_NAME = "id: ";

    private final UserDataAccess userDataAccess;

    public Whisper(UserDataAccess userDataAccess) {
        super(BOT_TOKEN, BOT_USERNAME);
        this.userDataAccess = userDataAccess;
    }

    @Override
    public long creatorId() {
        return CREATOR_ID;
    }

    public Ability start() {
        return Ability.builder()
                .name("start")
                .info("sends start message.")
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    silent.send(generateStartMessage(ctx), ctx.chatId());
                })
                .build();
    }

    private String generateStartMessage(MessageContext ctx) {
        String addition = "";
        if (contextHasArgument(ctx)) {
            addition = "\n\nReply this message and Enter your anonymous message to public link.\n"+ BOT_USERID_NAME + ctx.firstArg();
        }
        return "This is an anonymous message bot.\nYou can get your public link wit /my_link" + addition;
    }

    private boolean contextHasArgument(MessageContext ctx) {
        return ctx.arguments().length > 0;
    }

    public Ability sendMessage() {
        return Ability.builder()
                .name(DEFAULT)
                .info("sends message to link")
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    Message message = ctx.update().getMessage();
                    if (message.isReply()) {
                        String repliedMessage = message.getReplyToMessage().getText();
                        if (repliedMessage.contains(BOT_USERID_NAME)) {
                            String uuid = repliedMessage.substring(repliedMessage.indexOf(BOT_USERID_NAME) + BOT_USERID_NAME.length());
                            Long telegramUserId = userDataAccess.getTelegramId(UUID.fromString(uuid));
                            silent.send(ctx.update().getMessage().getText(), telegramUserId);
                        }
                    }
                })
                .build();
    }

    public Ability getLink() {
        return Ability.builder()
                .name("my_link")
                .info("creates public link to bot.")
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> {
                    UUID uuid = calculateLinkUuid(ctx.chatId());
                    silent.send("t.me/" + BOT_USERNAME + "?start=" + uuid, ctx.chatId());
                })
                .build();
    }

    private UUID calculateLinkUuid(Long userTelegramId) {
        UUID linkUuid;
        Optional<UUID> optionalUuid = userDataAccess.getUuidOrElse(userTelegramId);
        if (optionalUuid.isPresent()) {
            linkUuid = optionalUuid.get();
        } else {
            linkUuid = UUID.randomUUID();
            userDataAccess.save(userTelegramId, linkUuid);
        }
        return linkUuid;
    }
}

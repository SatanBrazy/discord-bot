package me.comu.exeter.events;

import me.comu.exeter.commands.economy.EconomyManager;
import me.comu.exeter.util.ChatTrackingManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class CreditOnMessageEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        double d = Math.random();
        int amount = new Random().nextInt(31);
        if (d > 0.97 && amount != 0)
        {
            event.getChannel().sendMessage(String.format("%s got lucky and received `%s` credits!", event.getAuthor().getName(), amount).replaceAll("@everyone","everyone").replaceAll("@here","here")).queue();
            if (EconomyManager.verifyUser(event.getAuthor().getId()))
                EconomyManager.getUsers().put(event.getAuthor().getId(), 0);
            EconomyManager.setBalance(event.getAuthor().getId(), EconomyManager.getBalance(event.getAuthor().getId()) + amount);
        }
        if (ChatTrackingManager.verifyChatUser(Objects.requireNonNull(event.getMember()).getId()))
        {
            ChatTrackingManager.setChatCredits(event.getMember().getId(), 0);
        }
        ChatTrackingManager.setChatCredits(event.getMember().getId(), ChatTrackingManager.getChatCredits(event.getMember().getId()) + 1);

    }
}

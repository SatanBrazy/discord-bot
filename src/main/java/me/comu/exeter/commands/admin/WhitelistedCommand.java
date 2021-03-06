package me.comu.exeter.commands.admin;

import me.comu.exeter.core.Core;
import me.comu.exeter.interfaces.ICommand;
import me.comu.exeter.util.ChatTrackingManager;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WhitelistedCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR) && event.getMember().getIdLong() != Core.OWNERID) {
            event.getChannel().sendMessage("You don't have permission to see the whitelisted users").queue();
            return;
        }
        if (WhitelistCommand.getWhitelistedIDs().isEmpty()) {
            event.getChannel().sendMessage("null").queue();
            return;
        }
        if (event.getAuthor().getIdLong() == Core.OWNERID && !args.isEmpty() && args.get(0).equals("-g")) {
            StringBuilder globalStringBuffer = new StringBuilder();
            int counter = 0;
            for (String x : WhitelistCommand.getWhitelistedIDs().keySet()) {
                User user = event.getJDA().getUserById(x);
                try {
                    String name = Objects.requireNonNull(user).getName() + "#" + user.getDiscriminator() + String.format(" (%s)", Objects.requireNonNull(event.getJDA().getGuildById(WhitelistCommand.getWhitelistedIDs().get(x))).getName());
                    globalStringBuffer.append(" + ").append(name).append("\n");
                    counter++;
                } catch (NullPointerException ex)
                {
                    event.getChannel().sendMessage("The whitelist config contained an invalid user and was automatically resolved. (" + x + ")").queue();
                    WhitelistCommand.getWhitelistedIDs().remove(x);
                }
            }
            event.getChannel().sendMessage(EmbedUtils.embedMessage("**" + counter + " Whitelisted Users: (GLOBAL)**\n" + globalStringBuffer.toString()).build()).queue();
            WhitelistedJSONHandler.saveWhitelistConfig();
            return;
        }
        StringBuilder stringBuffer = new StringBuilder();
        int counter2 = 0;
        for (String x : WhitelistCommand.getWhitelistedIDs().keySet()) {
            if (WhitelistCommand.getWhitelistedIDs().get(x).equals(event.getGuild().getId())) {
                User user = event.getJDA().getUserById(x);
                try {
                    String name = Objects.requireNonNull(user).getName() + "#" + user.getDiscriminator();
                    stringBuffer.append(" + ").append(name).append("\n");
                    counter2++;
                } catch (NullPointerException ex) {
                    event.getChannel().sendMessage("The whitelist config contained an invalid user and was automatically resolved. (" + x + ")").queue();
                    WhitelistCommand.getWhitelistedIDs().remove(x);

                }
            }
        }

        event.getChannel().sendMessage(EmbedUtils.embedMessage("**" + counter2 + " Whitelisted Users: (LOCAL)**\n" + stringBuffer.toString()).build()).queue();
        WhitelistedJSONHandler.saveWhitelistConfig();

    }

    @Override
    public String getHelp() {
        return "See all the users on the whitelist\n`" + Core.PREFIX + getInvoke() + "`\nAliases: `" + Arrays.deepToString(getAlias()) + "`";
    }

    @Override
    public String getInvoke() {
        return "whitelisted";
    }

    @Override
    public String[] getAlias() {
        return new String[]{"seewhitelist", "arwhitelisted", "whitelistlist", "trustlist", "wld"};
    }

   @Override
    public Category getCategory() {
        return Category.ADMIN;
    }
}

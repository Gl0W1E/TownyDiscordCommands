package net.euromc.townydiscordcommands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class NationCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (!event.getName().equals("nation")) {
            return;
        }

        Nation n = TownyAPI.getInstance().getNation(event.getInteraction().getOption("name").getAsString());
        if (n == null) {
            EmbedBuilder embFailNat = new EmbedBuilder();
            embFailNat.setDescription("This nation does not exist!");
            embFailNat.setColor(Color.RED);
            event.replyEmbeds(embFailNat.build()).queue();
            return;
        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.GREEN);
        emb.setThumbnail(Main.instance.getConfig().getString("nation-image"));
        emb.setTitle(n.getName());
        emb.setDescription("**Board:** " + n.getBoard());
        emb.addField("Leader:", n.getKing().getName(), true);
        emb.addField("Capital:", n.getCapital().getName(), true);
        emb.addField("Town Count:", String.valueOf(n.getNumTowns()), true);

        event.replyEmbeds(emb.build()).queue();
    }
}
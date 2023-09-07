package net.euromc.townydiscordcommands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.object.Resident;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class ResidentCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("resident")) {
            return;
        }

        Resident r = TownyAPI.getInstance().getResident(event.getInteraction().getOption("name").getAsString());

        if (r == null) {
            EmbedBuilder embFailRes = new EmbedBuilder();
            embFailRes.setDescription("This resident does not exist!");
            embFailRes.setColor(Color.RED);
            event.replyEmbeds(embFailRes.build()).queue();
            return;
        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setTitle(r.getName());
        emb.setDescription("**Activity:** " + (r.isOnline() ? "ONLINE" : "OFFLINE"));
        emb.setThumbnail("https://cravatar.eu/helmhead/" + r.getName() + "/600.png");
        emb.setColor(Color.GREEN);
        emb.addField("Registered:", "<t:" + r.getRegistered()/1000 + ":f>", true);
        if (r.hasTown()) {
            emb.addField("Town:" , r.getTownOrNull().getName(), true);
        }
        if (r.hasNation()) {
            emb.addField("Nation: " , r.getNationOrNull().getName(), true);
        }
        if (TownyEconomyHandler.isActive()) {
            emb.addField("Balance:", String.valueOf(r.getAccountOrNull().getHoldingBalance()), true);
        }

        event.replyEmbeds(emb.build()).queue();

    }
}

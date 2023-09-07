package net.euromc.townydiscordcommands;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Town;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class TownCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.getName().equals("town")) {
            return;
        }

        Town t = TownyAPI.getInstance().getTown(event.getInteraction().getOption("name").getAsString());

        if (t == null) {

            EmbedBuilder embFailTown = new EmbedBuilder();
            embFailTown.setDescription("This town does not exist!");
            embFailTown.setColor(Color.RED);
            event.replyEmbeds(embFailTown.build()).queue();
            return;
        }

        EmbedBuilder emb = new EmbedBuilder();
        emb.setColor(Color.GREEN);
        emb.setTitle(t.getName());
        emb.setDescription("**Board:** " + t.getBoard());
        emb.addField("Mayor:", t.getMayor().getName(), true);
        if (t.hasNation()) {
            emb.addField("Nation:", t.getNationOrNull().getName(), true);
        }
        if (TownyEconomyHandler.isActive()) {
            emb.addField("Bank:", String.valueOf(t.getAccount().getHoldingBalance()), true);
        }
        emb.addField("PVP:", t.isPVP() ? "ON" : "OFF", true);
        emb.addField("Fire:", t.isFire() ? "ON" : "OFF", true);
        emb.addField("Explosions:", t.isExplosion() ? "ON" : "OFF", true);
        emb.addField("Mob Spawns:", t.hasMobs() ? "ON" : "OFF", true);
        emb.addField("Resident Count:" , String.valueOf(t.getNumResidents()), true);
        emb.setThumbnail(Main.instance.getConfig().getString("town-image"));

        event.replyEmbeds(emb.build()).queue();
        // success

    }
}

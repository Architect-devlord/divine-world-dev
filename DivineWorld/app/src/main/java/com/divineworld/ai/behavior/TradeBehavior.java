// upgrade this class so that the bot can dynamically adjust trade offers based on inventory and market conditions
// This will involve checking the bot's inventory and the current market prices for items
package com.divineworld.ai.behavior;

import com.divineworld.npc.DWBot;
import com.divineworld.trade.TradeOffer;
import org.bukkit.Material;

public class TradeBehavior {
    private final DWBot bot;

    public TradeBehavior(DWBot bot) {
        this.bot = bot;
    }

    public void tick() {
        
        TradeOffer offer = new TradeOffer(Material.POTATO, 5, Material.WHEAT, 4);
        boolean fair = bot.getTradeEngine().isFair(offer);
        
        if (fair) {
            bot.getTradeEngine().learn(offer, true);
            bot.getMemory().remember("TradeSuccess", offer.toString(), 0.5);
        } else {
            bot.getTradeEngine().learn(offer, false);
            bot.getMemory().remember("TradeRejected", offer.toString(), -0.3);
        }
    }
}

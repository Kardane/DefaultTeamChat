package org.karn.defaultteamchat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class DefaultTeamChat implements ModInitializer {
    public static final GameRules.Key<GameRules.BooleanRule> DEFAULT_TEAMCHAT = GameRuleRegistry.register("setTeamChatDefault", GameRules.Category.CHAT, GameRuleFactory.createBooleanRule(false));
    @Override
    public void onInitialize() {
        System.out.println("DefaultTeamChat online!");
    }
}

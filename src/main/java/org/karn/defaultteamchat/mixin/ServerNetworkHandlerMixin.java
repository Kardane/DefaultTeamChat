package org.karn.defaultteamchat.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.karn.defaultteamchat.DefaultTeamChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method="handleDecoratedMessage", at = @At(value = "HEAD"), cancellable = true)
    private void onHandleDecoratedMessage(SignedMessage message, CallbackInfo ci) {
        boolean teamchat = player.getWorld().getGameRules().getBoolean(DefaultTeamChat.DEFAULT_TEAMCHAT);
        String content = message.getSignedContent();

        if(player.getScoreboardTeam() == null)
            return;

        if(content.startsWith("!"))
            return;

        if(teamchat) {
            Team team = player.getScoreboardTeam();
            MinecraftServer server = player.getServer();
            team.getPlayerList().forEach(playerName -> {
                System.out.println(playerName);
                PlayerEntity player = server.getPlayerManager().getPlayer(playerName);
                player.sendMessage(Text.empty().append(Text.literal("[Team] ").formatted(Formatting.AQUA)).append(player.getDisplayName()).append(Text.literal(" > ").formatted(Formatting.GRAY)).append(message.getContent()));
            });
            ci.cancel();
        } else {
            return;
        }
    }
}

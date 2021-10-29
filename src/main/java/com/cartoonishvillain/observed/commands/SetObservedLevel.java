package com.cartoonishvillain.observed.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

import static com.cartoonishvillain.observed.components.ComponentStarter.OBSERVELEVEL;

public class SetObservedLevel {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("observed").then(Commands.literal("setobservedlevel")
                .requires(cs -> {return cs.hasPermission(2);})
                .then(Commands.argument("target", GameProfileArgument.gameProfile()).then(Commands.argument("level", FloatArgumentType.floatArg(0, 100)).executes(context -> {
                    return setHauntChance(context.getSource(), GameProfileArgument.getGameProfiles(context, "target"), FloatArgumentType.getFloat(context, "level"));
                })))

        ));
    }


    private static int setHauntChance(CommandSourceStack source, Collection<GameProfile> profiles, float level){
        for(GameProfile gameProfile : profiles){
            ServerPlayer serverPlayerEntity = source.getServer().getPlayerList().getPlayer(gameProfile.getId());
            OBSERVELEVEL.get(serverPlayerEntity).setObserveLevel(level);
        }
        source.sendSuccess(new TranslatableComponent("command.observed.success", level), false);
        return 0;
    }
}

package com.ritsukage.simple_aether_cloud.damage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class RedCloudDamageSource extends DamageSource {
    public RedCloudDamageSource(Level level) {
        super(level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                .getHolderOrThrow(net.minecraft.world.damagesource.DamageTypes.PLAYER_ATTACK));
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity entity) {
        return Component.translatable("death.attack.red_cloud", entity.getDisplayName());
    }
}
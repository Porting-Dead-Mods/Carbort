package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.registries.CBDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DivisionSigilItem extends Item {
    public DivisionSigilItem(Properties properties) {
        super(properties.component(CBDataComponents.ACTIVE, false).durability(256));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (stack.get(CBDataComponents.ACTIVE) || !player.isShiftKeyDown() || player.getMainHandItem() != stack) {
            return InteractionResult.FAIL;
        }

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (!level.getBlockState(pos).is(Blocks.ENCHANTING_TABLE) || !isRedstoneWired(level, pos)) {
            return InteractionResult.PASS;
        }

        Optional<LivingEntity> optionalEntity = level.getEntities(null, new AABB(pos.above())).stream()
                .filter(entity -> entity instanceof LivingEntity)
                .map(entity -> (LivingEntity) entity)
                .findFirst();

        if (optionalEntity.isPresent() && level.isNight()) {
            LivingEntity livingEntity = optionalEntity.get();
            Vec3 entityPos = livingEntity.position();

            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
            if (lightning != null) {
                lightning.moveTo(entityPos);
                level.addFreshEntity(lightning);

                Holder<DamageType> lightningDamageType = level.registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(DamageTypes.LIGHTNING_BOLT);

                livingEntity.hurt(new DamageSource(lightningDamageType), livingEntity.getMaxHealth() * 2);
                stack.set(CBDataComponents.ACTIVE, true);
                return InteractionResult.SUCCESS;
            }
        }

        return super.useOn(context);
    }

    private boolean isRedstoneWired(Level level, BlockPos pos) {
        BlockPos[] offsets = { pos.north(), pos.south(), pos.east(), pos.west(),
                pos.north().east(), pos.north().west(), pos.south().east(), pos.south().west() };
        return Arrays.stream(offsets)
                .allMatch(offset -> level.getBlockState(offset).is(Blocks.REDSTONE_WIRE));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.get(CBDataComponents.ACTIVE);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Status: " + (stack.get(CBDataComponents.ACTIVE) ? "Active" : "Inactive")).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}

package net.descriptivismo.spelunky2mod.block.entity.client;

import net.descriptivismo.spelunky2mod.Spelunky2Mod;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {

    public static final ModelLayerLocation SNAKE_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Spelunky2Mod.MODID, "snake_layer"),
            "main"
    );

    public static final ModelLayerLocation BOMB_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(Spelunky2Mod.MODID, "bomb_layer"),
            "main"
    );

}

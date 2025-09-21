package com.talhanation.smallships.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.talhanation.smallships.Main;
import com.talhanation.smallships.client.model.ModelCog;
import com.talhanation.smallships.client.model.ModelCogSail;
import com.talhanation.smallships.entities.CogEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class RenderEntityCog extends AbstractShipRenderer<CogEntity> {
    private static final ResourceLocation[] COG_TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/oak_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/spruce_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/birch_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/jungle_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/acacia_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/cog/dark_oak_cog.png"),
/*
            //BOP//
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_cherry_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_dead_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_fir_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_hellbark_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_jacaranda_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_magic_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_mahogany_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_palm_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_redwood_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_umbran_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/bop/cog/bop_willow_cog.png"),

            //LOTR//
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_apple_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_aspen_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_beech_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_cedar_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_charred_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_cherry_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_cypress_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_fir_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_green_oak_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_holly_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_lairelosse_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_larch_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_lebethron_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_mallorn_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_maple_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_mirk_oak_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_pear_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_pine_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/lotr/cog/lotr_rotten_cog.png"),

            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/cog/envi_cherry_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/cog/envi_wisteria_cog.png"),
            new ResourceLocation(Main.MOD_ID,"textures/entity/mod/envi/cog/envi_willow_cog.png")
*/
    };

    public RenderEntityCog(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelCog(), ModelCogSail::new);
        this.shadowRadius = 1.5F;
    }

    @Override
    protected double getRenderYOffset(CogEntity entity) {
        return 0.4D;
    }

    @Override
    protected float getModelScale(CogEntity entity) {
        return 1.3F;
    }

    @Override
    protected Vector3d getModelTranslation(CogEntity entity) {
        return new Vector3d(0.0D, -1.8D, -1.0D);
    }

    @Override
    protected float getModelYawOffset(CogEntity entity) {
        return -90F;
    }

    @Override
    protected void renderAdditionalParts(CogEntity entity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        entity.renderCannon(-0.65D, 0.03D, 0F, matrixStack, buffer, packedLight, partialTicks);
    }

    @Override
    public ResourceLocation getTextureLocation(CogEntity entity) {
        return COG_TEXTURES[entity.getWoodType().ordinal()];
    }

}

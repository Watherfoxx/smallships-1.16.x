package com.talhanation.smallships.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.talhanation.smallships.entities.AbstractBannerUser;
import com.talhanation.smallships.entities.GalleyEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class ModelGalley extends EntityModel<AbstractBannerUser> {
    private static final float HULL_Y_ROT = ((float) Math.PI / 2F);
    private static final float PADDLE_MIN_X_ROT = -1.0471976F;
    private static final float PADDLE_MAX_X_ROT = -0.2617994F;
    private static final float PADDLE_MIN_Y_ROT = -0.7853982F;
    private static final float PADDLE_MAX_Y_ROT = 0.7853982F;

    private final ModelRenderer root;
    private final ModelRenderer galley;
    private final ModelRenderer steer;
    private final ModelRenderer[] chests;
    private final ModelRenderer[] leftPaddles;
    private final ModelRenderer[] rightPaddles;

    public ModelGalley() {
        this.texWidth = 128;
        this.texHeight = 64;

        this.root = new ModelRenderer(this);
        this.root.setPos(0.0F, 24.0F, 0.0F);

        this.galley = new ModelRenderer(this);
        this.galley.setPos(0.0F, 0.0F, 0.0F);
        this.root.addChild(this.galley);

        ModelRenderer deck = new ModelRenderer(this);
        deck.setPos(14.0F, 0.0F, 0.0F);
        this.galley.addChild(deck);

        ModelRenderer cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-14.0F, 0.0F, 0.0F);
        deck.addChild(cube_r1);
        setRotationAngle(cube_r1, 1.5708F, 0.0F, 0.0F);
        cube_r1.texOffs(28, 0).addBox(-42.0F, -13.0F, 2.0F, 14.0F, 13.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(28, 0).addBox(-42.0F, 0.0F, 2.0F, 14.0F, 13.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(33, 0).addBox(-53.0F, 0.0F, 4.0F, 8.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(33, 0).addBox(-53.0F, -6.0F, 4.0F, 8.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(33, 0).addBox(-64.0F, -3.5F, 4.0F, 11.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(15, 0).addBox(38.0F, -13.0F, 2.0F, 13.0F, 13.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(15, 0).addBox(38.0F, 0.0F, 2.0F, 13.0F, 13.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(18, 0).addBox(28.0F, -16.0F, 2.0F, 10.0F, 16.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(18, 0).addBox(28.0F, 0.0F, 2.0F, 10.0F, 16.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(0, 0).addBox(0.0F, 0.0F, 2.0F, 28.0F, 16.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(0, 0).addBox(0.0F, -16.0F, 2.0F, 28.0F, 16.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(0, 0).addBox(-28.0F, 0.0F, 2.0F, 28.0F, 16.0F, 3.0F, 0.0F, false);
        cube_r1.texOffs(0, 0).addBox(-28.0F, -16.0F, 2.0F, 28.0F, 16.0F, 3.0F, 0.0F, false);

        ModelRenderer bottom = new ModelRenderer(this);
        bottom.setPos(0.0F, 0.0F, 0.0F);
        this.galley.addChild(bottom);

        ModelRenderer cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.0F, 0.0F, 0.0F);
        bottom.addChild(cube_r2);
        cube_r2.texOffs(8, 2).addBox(-42.0F, 3.0F, -4.0F, 14.0F, 7.0F, 8.0F, 0.0F, false);

        ModelRenderer cube_r5_r1 = new ModelRenderer(this);
        cube_r5_r1.setPos(-42.0F, 5.5F, 0.0F);
        cube_r2.addChild(cube_r5_r1);
        setRotationAngle(cube_r5_r1, 0.0F, 0.0F, 1.5708F);
        cube_r5_r1.texOffs(4, 1).addBox(-11.6F, 0.8F, -3.0F, 10.0F, 7.0F, 6.0F, 0.0F, false);

        ModelRenderer cube_r4_r1 = new ModelRenderer(this);
        cube_r4_r1.setPos(-42.0F, 5.5F, 0.0F);
        cube_r2.addChild(cube_r4_r1);
        setRotationAngle(cube_r4_r1, 0.0F, 0.0F, 0.6545F);
        cube_r4_r1.texOffs(4, 1).addBox(-7.2F, -3.5F, -3.5F, 10.0F, 7.0F, 7.0F, 0.0F, false);

        ModelRenderer cube_r3 = new ModelRenderer(this);
        cube_r3.setPos(0.0F, 0.0F, 0.0F);
        bottom.addChild(cube_r3);
        setRotationAngle(cube_r3, 1.5708F, 0.0F, 0.0F);
        cube_r3.texOffs(0, 0).addBox(32.0F, -4.0F, -10.0F, 15.0F, 8.0F, 7.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(16.0F, -4.0F, -10.0F, 16.0F, 8.0F, 7.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-6.0F, -4.0F, -10.0F, 22.0F, 8.0F, 7.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-28.0F, -4.0F, -10.0F, 22.0F, 8.0F, 7.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-28.0F, 2.0F, -3.0F, 22.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-28.0F, -12.0F, -3.0F, 22.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-42.0F, -12.0F, -3.0F, 14.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-42.0F, 2.0F, -3.0F, 14.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-6.0F, -12.0F, -3.0F, 24.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(34.0F, -12.0F, -3.0F, 17.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(-6.0F, 2.0F, -3.0F, 24.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(34.0F, 2.0F, -3.0F, 17.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(18.0F, 2.0F, -3.0F, 16.0F, 10.0F, 5.0F, 0.0F, false);
        cube_r3.texOffs(0, 0).addBox(18.0F, -12.0F, -3.0F, 16.0F, 10.0F, 5.0F, 0.0F, false);

        ModelRenderer sides = new ModelRenderer(this);
        sides.setPos(0.0F, 0.0F, 0.0F);
        this.galley.addChild(sides);
        sides.texOffs(8, 36).addBox(-43.0F, -11.0F, -16.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(-43.0F, -11.0F, 13.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(37.0F, -11.0F, -16.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(37.0F, -17.0F, -16.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(13.0F, -11.0F, -19.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(-2.0F, -11.0F, -19.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(-28.0F, -11.0F, -19.0F, 11.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(-17.0F, -11.0F, -19.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(37.0F, -11.0F, 13.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(28.0F, -11.0F, 16.0F, 9.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(28.0F, -11.0F, -19.0F, 9.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(37.0F, -17.0F, 13.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(13.0F, -11.0F, 16.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(-2.0F, -11.0F, 16.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(-17.0F, -11.0F, 16.0F, 15.0F, 6.0F, 3.0F, 0.0F, false);
        sides.texOffs(8, 36).addBox(-28.0F, -11.0F, 16.0F, 11.0F, 6.0F, 3.0F, 0.0F, false);

        ModelRenderer cube_r4 = new ModelRenderer(this);
        cube_r4.setPos(35.0F, -8.0F, -9.5F);
        sides.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 1.5708F, 0.0F);

        ModelRenderer cube_r5 = new ModelRenderer(this);
        cube_r5.setPos(-20.5F, -8.0F, -3.5F);
        sides.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, -1.5708F, 0.0F);
        cube_r5.texOffs(8, 36).addBox(3.5001F, -3.0F, 21.5F, 13.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(-9.4999F, -3.0F, 21.5F, 13.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(3.5001F, 3.0F, 21.5F, 13.0F, 3.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(-9.4999F, 3.0F, 21.5F, 13.0F, 3.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(-8.4999F, 6.0F, 21.5F, 11.0F, 3.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(3.5001F, 6.0F, 21.5F, 12.0F, 3.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(-9.5002F, -3.0F, -74.5F, 13.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(-9.5002F, -9.0F, -74.5F, 13.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(3.4998F, -3.0F, -74.5F, 13.0F, 6.0F, 3.0F, 0.0F, false);
        cube_r5.texOffs(8, 36).addBox(3.4998F, -9.0F, -74.5F, 13.0F, 6.0F, 3.0F, 0.0F, false);

        ModelRenderer mast_1 = new ModelRenderer(this);
        mast_1.setPos(14.0F, -5.0F, -1.0F);
        this.galley.addChild(mast_1);
        mast_1.texOffs(8, 0).addBox(-13.0F, -15.0F, -0.5F, 3.0F, 15.0F, 3.0F, 0.0F, false);
        mast_1.texOffs(8, 0).addBox(-13.0F, -30.0F, -0.5F, 3.0F, 15.0F, 3.0F, 0.0F, false);
        mast_1.texOffs(8, 0).addBox(-13.0F, -45.0F, -0.5F, 3.0F, 15.0F, 3.0F, 0.0F, false);
        mast_1.texOffs(8, 0).addBox(-13.0F, -60.0F, -0.5F, 3.0F, 15.0F, 3.0F, 0.0F, false);
        mast_1.texOffs(8, 0).addBox(-13.0F, -75.0F, -0.5F, 3.0F, 15.0F, 3.0F, 0.0F, false);

        ModelRenderer mast_2 = new ModelRenderer(this);
        mast_2.setPos(-11.0F, -65.5F, 3.4F);
        mast_1.addChild(mast_2);
        setRotationAngle(mast_2, 0.0F, 0.0F, 0.7418F);
        mast_2.texOffs(8, 0).addBox(1.2957F, -39.0539F, -0.9F, 2.0F, 15.0F, 4.0F, 0.0F, false);
        mast_2.texOffs(8, 0).addBox(1.2957F, -52.0539F, -0.9F, 2.0F, 13.0F, 4.0F, 0.0F, false);
        mast_2.texOffs(8, 0).addBox(1.2957F, -24.0539F, -0.9F, 2.0F, 15.0F, 4.0F, 0.0F, false);
        mast_2.texOffs(8, 0).addBox(1.2957F, -9.0539F, -0.9F, 2.0F, 15.0F, 4.0F, 0.0F, false);
        mast_2.texOffs(8, 0).addBox(1.2957F, 5.9461F, -0.9F, 2.0F, 15.0F, 4.0F, 0.0F, false);
        mast_2.texOffs(8, 0).addBox(1.2957F, 20.9461F, -0.9F, 2.0F, 15.0F, 4.0F, 0.0F, false);
        mast_2.texOffs(8, 0).addBox(1.2957F, 35.9461F, -0.9F, 2.0F, 15.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r6 = new ModelRenderer(this);
        cube_r6.setPos(29.0F, 0.5F, 1.0F);
        mast_1.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.5672F);
        cube_r6.texOffs(8, 0).addBox(6.6905F, -13.6357F, -2.5F, 4.0F, 24.0F, 5.0F, 0.0F, false);
        cube_r6.texOffs(8, 0).addBox(7.4905F, -23.3357F, -1.0F, 2.0F, 10.0F, 2.0F, 0.0F, false);

        ModelRenderer bannerStick = new ModelRenderer(this);
        bannerStick.setPos(-14.0F, 4.0F, 1.0F);
        mast_1.addChild(bannerStick);
        bannerStick.texOffs(8, 0).addBox(2.0F, -94.0F, -0.5F, 1.0F, 15.0F, 1.0F, 0.0F, false);

        ModelRenderer chest_1 = new ModelRenderer(this);
        chest_1.setPos(1.0F, 0.0F, 1.0F);
        this.galley.addChild(chest_1);
        chest_1.texOffs(96, 38).addBox(33.0F, -13.0F, -13.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        ModelRenderer cube_r7 = new ModelRenderer(this);
        cube_r7.setPos(27.75F, -11.75F, -8.25F);
        chest_1.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, 1.5708F, 0.0F);
        cube_r7.texOffs(30, 55).addBox(-2.75F, -4.25F, 6.75F, 4.0F, 3.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r8 = new ModelRenderer(this);
        cube_r8.setPos(19.0F, -15.5F, -18.0F);
        chest_1.addChild(cube_r8);
        setRotationAngle(cube_r8, 1.5708F, -1.5708F, 0.0F);
        cube_r8.texOffs(30, 55).addBox(15.0001F, -24.5001F, -10.0001F, 4.0F, 5.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r9 = new ModelRenderer(this);
        cube_r9.setPos(31.0F, 9.0F, -75.0F);
        chest_1.addChild(cube_r9);
        setRotationAngle(cube_r9, 0.0F, -1.5708F, 0.0F);
        cube_r9.texOffs(30, 55).addBox(72.1001F, -19.0F, -17.4001F, 4.0F, 5.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r10 = new ModelRenderer(this);
        cube_r10.setPos(22.0F, -9.0F, -1.0F);
        chest_1.addChild(cube_r10);
        setRotationAngle(cube_r10, 0.0F, 3.1416F, 0.0F);
        cube_r10.texOffs(96, 38).addBox(-28.0F, -4.0F, 3.5F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        ModelRenderer chest_4 = new ModelRenderer(this);
        chest_4.setPos(42.0F, -11.5F, 9.0F);
        this.galley.addChild(chest_4);

        ModelRenderer cube_r11 = new ModelRenderer(this);
        cube_r11.setPos(0.0F, 0.0F, 0.0F);
        chest_4.addChild(cube_r11);
        setRotationAngle(cube_r11, 0.0F, 1.5708F, 0.0F);
        cube_r11.texOffs(64, 29).addBox(-3.0F, -1.5F, -4.0F, 6.0F, 5.0F, 8.0F, 0.0F, false);

        ModelRenderer cube_r12 = new ModelRenderer(this);
        cube_r12.setPos(3.0F, 20.5F, -42.0F);
        chest_4.addChild(cube_r12);
        setRotationAngle(cube_r12, 0.0F, -1.5708F, 0.0F);
        cube_r12.texOffs(30, 55).addBox(39.0F, -22.0F, -5.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r13 = new ModelRenderer(this);
        cube_r13.setPos(0.0F, 20.5F, -42.0F);
        chest_4.addChild(cube_r13);
        setRotationAngle(cube_r13, 0.0F, -1.5708F, 0.0F);
        cube_r13.texOffs(50, 47).addBox(38.0F, -17.0F, -9.25F, 7.0F, 3.0F, 13.0F, 0.0F, false);

        ModelRenderer chest_2 = new ModelRenderer(this);
        chest_2.setPos(-55.0F, -15.5F, 30.0F);
        this.galley.addChild(chest_2);

        ModelRenderer cube_r14 = new ModelRenderer(this);
        cube_r14.setPos(0.0F, 0.0F, 0.0F);
        chest_2.addChild(cube_r14);
        setRotationAngle(cube_r14, 1.5708F, -1.5708F, 0.0F);
        cube_r14.texOffs(30, 55).addBox(-21.0001F, -24.4999F, -9.9999F, 4.0F, 5.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r15 = new ModelRenderer(this);
        cube_r15.setPos(12.0F, 24.5F, -57.0F);
        chest_2.addChild(cube_r15);
        setRotationAngle(cube_r15, 0.0F, -1.5708F, 0.0F);
        cube_r15.texOffs(30, 55).addBox(30.9999F, -19.0F, -14.9999F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        cube_r15.texOffs(30, 55).addBox(32.0F, -25.0F, -8.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);

        ModelRenderer cube_r16 = new ModelRenderer(this);
        cube_r16.setPos(3.0F, 6.5F, -16.0F);
        chest_2.addChild(cube_r16);
        setRotationAngle(cube_r16, 0.0F, 3.1416F, 0.0F);
        cube_r16.texOffs(96, 38).addBox(-19.0F, -4.0F, 3.5F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        ModelRenderer chest_3 = new ModelRenderer(this);
        chest_3.setPos(37.3333F, -8.0F, -11.1667F);
        this.galley.addChild(chest_3);

        ModelRenderer cube_r17 = new ModelRenderer(this);
        cube_r17.setPos(-20.3333F, 17.0F, -33.8333F);
        chest_3.addChild(cube_r17);
        setRotationAngle(cube_r17, 0.0F, -1.5708F, 0.0F);
        cube_r17.texOffs(30, 55).addBox(33.0F, -19.0F, 53.0F, 4.0F, 5.0F, 4.0F, 0.0F, false);
        cube_r17.texOffs(31, 56).addBox(33.0F, -19.0F, 49.0F, 4.0F, 5.0F, 3.0F, 0.0F, false);

        ModelRenderer cube_r18 = new ModelRenderer(this);
        cube_r18.setPos(-29.3333F, -1.0F, 7.1667F);
        chest_3.addChild(cube_r18);
        setRotationAngle(cube_r18, 0.0F, 1.5708F, 0.0F);
        cube_r18.texOffs(96, 38).addBox(-4.0F, -4.0F, -49.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.steer = new ModelRenderer(this);
        this.steer.setPos(38.0F, 3.25F, 0.0F);
        this.galley.addChild(this.steer);
        this.steer.texOffs(4, 1).addBox(13.0F, 2.75F, -1.0F, 4.0F, 8.0F, 2.0F, 0.0F, false);
        this.steer.texOffs(4, 1).addBox(9.0F, -0.25F, -1.0F, 4.0F, 11.0F, 2.0F, 0.0F, false);

        ModelRenderer row_L_4 = new ModelRenderer(this);
        row_L_4.setPos(-12.5F, -12.0F, -18.0F);
        this.galley.addChild(row_L_4);
        setRotationAngle(row_L_4, 0.2618F, 0.0F, 0.0F);
        row_L_4.texOffs(33, 3).addBox(-2.5F, -0.5F, -28.0F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r19 = new ModelRenderer(this);
        cube_r19.setPos(-4.5F, 9.0F, -23.0F);
        row_L_4.addChild(cube_r19);
        setRotationAngle(cube_r19, 1.5708F, 0.0F, 0.0F);
        cube_r19.texOffs(9, 0).addBox(4.0F, 18.5F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r19.texOffs(9, 0).addBox(4.0F, 1.5F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        ModelRenderer row_L_3 = new ModelRenderer(this);
        row_L_3.setPos(1.5F, -12.0F, -18.0F);
        this.galley.addChild(row_L_3);
        setRotationAngle(row_L_3, 0.2618F, 0.0F, 0.0F);
        row_L_3.texOffs(33, 3).addBox(-2.5F, -0.5F, -28.0F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r20 = new ModelRenderer(this);
        cube_r20.setPos(-4.5F, 9.0F, -23.0F);
        row_L_3.addChild(cube_r20);
        setRotationAngle(cube_r20, 1.5708F, 0.0F, 0.0F);
        cube_r20.texOffs(9, 0).addBox(4.0F, 18.5F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r20.texOffs(9, 0).addBox(4.0F, 1.5F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        ModelRenderer row_L_2 = new ModelRenderer(this);
        row_L_2.setPos(16.5F, -12.0F, -18.0F);
        this.galley.addChild(row_L_2);
        setRotationAngle(row_L_2, 0.2618F, 0.0F, 0.0F);
        row_L_2.texOffs(33, 3).addBox(-2.5F, -0.5F, -28.0F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r21 = new ModelRenderer(this);
        cube_r21.setPos(-4.5F, 9.0F, -23.0F);
        row_L_2.addChild(cube_r21);
        setRotationAngle(cube_r21, 1.5708F, 0.0F, 0.0F);
        cube_r21.texOffs(9, 0).addBox(4.0F, 19.0F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r21.texOffs(9, 0).addBox(4.0F, 2.0F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        ModelRenderer row_L_1 = new ModelRenderer(this);
        row_L_1.setPos(31.5F, -12.0F, -18.0F);
        this.galley.addChild(row_L_1);
        setRotationAngle(row_L_1, 0.2618F, 0.0F, 0.0F);
        row_L_1.texOffs(33, 3).addBox(-2.5F, -0.5F, -28.0F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r22 = new ModelRenderer(this);
        cube_r22.setPos(-4.5F, 9.0F, -23.0F);
        row_L_1.addChild(cube_r22);
        setRotationAngle(cube_r22, 1.5708F, 0.0F, 0.0F);
        cube_r22.texOffs(9, 0).addBox(4.0F, 18.5F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r22.texOffs(9, 0).addBox(4.0F, 1.5F, 8.0F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        ModelRenderer row_R_1 = new ModelRenderer(this);
        row_R_1.setPos(31.5F, -11.0F, 18.0F);
        this.galley.addChild(row_R_1);
        setRotationAngle(row_R_1, -0.2618F, 0.0F, 0.0F);
        row_R_1.texOffs(33, 3).addBox(-2.5F, -0.2059F, 11.1704F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r23 = new ModelRenderer(this);
        cube_r23.setPos(-2.5F, 8.0F, -16.0F);
        row_R_1.addChild(cube_r23);
        setRotationAngle(cube_r23, 1.5708F, 0.0F, 0.0F);
        cube_r23.texOffs(9, 0).addBox(2.0F, 13.6704F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r23.texOffs(9, 0).addBox(2.0F, -3.3296F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        ModelRenderer row_R_2 = new ModelRenderer(this);
        row_R_2.setPos(16.5F, -11.0F, 18.0F);
        this.galley.addChild(row_R_2);
        setRotationAngle(row_R_2, -0.2618F, 0.0F, 0.0F);
        row_R_2.texOffs(33, 3).addBox(-2.5F, -0.2059F, 11.1704F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r24 = new ModelRenderer(this);
        cube_r24.setPos(-4.5F, 8.0F, -16.0F);
        row_R_2.addChild(cube_r24);
        setRotationAngle(cube_r24, 1.5708F, 0.0F, 0.0F);
        cube_r24.texOffs(9, 0).addBox(4.0F, 13.6704F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r24.texOffs(9, 0).addBox(4.0F, -3.3296F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        ModelRenderer row_R_3 = new ModelRenderer(this);
        row_R_3.setPos(1.5F, -11.0F, 18.0F);
        this.galley.addChild(row_R_3);
        setRotationAngle(row_R_3, -0.2618F, 0.0F, 0.0F);
        row_R_3.texOffs(33, 3).addBox(-2.5F, -0.2059F, 11.1704F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r25 = new ModelRenderer(this);
        cube_r25.setPos(-4.5F, 8.0F, -16.0F);
        row_R_3.addChild(cube_r25);
        setRotationAngle(cube_r25, 1.5708F, 0.0F, 0.0F);
        cube_r25.texOffs(9, 0).addBox(4.0F, 13.6704F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r25.texOffs(9, 0).addBox(4.0F, -3.3296F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        ModelRenderer row_R_4 = new ModelRenderer(this);
        row_R_4.setPos(-12.5F, -11.0F, 18.0F);
        this.galley.addChild(row_R_4);
        setRotationAngle(row_R_4, -0.2618F, 0.0F, 0.0F);
        row_R_4.texOffs(33, 3).addBox(-2.5F, -0.2059F, 11.1704F, 5.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer cube_r26 = new ModelRenderer(this);
        cube_r26.setPos(-4.5F, 8.0F, -16.0F);
        row_R_4.addChild(cube_r26);
        setRotationAngle(cube_r26, 1.5708F, 0.0F, 0.0F);
        cube_r26.texOffs(9, 0).addBox(4.0F, 13.6704F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);
        cube_r26.texOffs(9, 0).addBox(4.0F, -3.3296F, 6.7059F, 1.0F, 17.0F, 2.0F, 0.0F, false);

        this.chests = new ModelRenderer[]{chest_1, chest_2, chest_3, chest_4};
        this.leftPaddles = new ModelRenderer[]{row_L_1, row_L_2, row_L_3, row_L_4};
        this.rightPaddles = new ModelRenderer[]{row_R_1, row_R_2, row_R_3, row_R_4};
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(AbstractBannerUser entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!(entity instanceof GalleyEntity)) {
            return;
        }

        GalleyEntity galleyEntity = (GalleyEntity) entity;
        this.root.xRot = 0.0F;
        this.root.yRot = HULL_Y_ROT;
        this.root.zRot = 0.0F;

        int cargo = galleyEntity.getCargo();
        for (int i = 0; i < this.chests.length; ++i) {
            this.chests[i].visible = cargo > i;
        }

        animatePaddles(galleyEntity, 0, limbSwing, this.leftPaddles, false);
        animatePaddles(galleyEntity, 1, limbSwing, this.rightPaddles, true);
    }

    private void animatePaddles(GalleyEntity galleyEntity, int side, float limbSwing, ModelRenderer[] paddles, boolean rightSide) {
        float rowingTime = galleyEntity.getRowingTime(side, limbSwing);
        float steerValue = -galleyEntity.getRotSpeed();
        for (ModelRenderer paddle : paddles) {
            setPaddleRotation(paddle, rowingTime, steerValue, rightSide);
        }
    }

    private static void setPaddleRotation(ModelRenderer paddle, float rowingTime, float steer, boolean rightSide) {
        float xRot = MathHelper.clamp((MathHelper.sin(-rowingTime) + 1.0F) / 2.0F, PADDLE_MIN_X_ROT, PADDLE_MAX_X_ROT);
        float yawOffset = MathHelper.clamp((MathHelper.sin(-rowingTime + 1.0F) + 1.0F) / 2.0F, PADDLE_MIN_Y_ROT, PADDLE_MAX_Y_ROT);
        if (rightSide) {
            paddle.xRot = xRot;
            paddle.yRot = (float) Math.PI - yawOffset + steer;
        } else {
            paddle.xRot = xRot;
            paddle.yRot = yawOffset - steer;
        }
    }

    private static void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

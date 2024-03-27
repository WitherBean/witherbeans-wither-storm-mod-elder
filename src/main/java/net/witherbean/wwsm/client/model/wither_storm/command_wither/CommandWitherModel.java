package net.witherbean.wwsm.client.model.wither_storm.command_wither;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.witherbean.wwsm.animation.definitions.WitherStormAnimationDefinitions;
import net.witherbean.wwsm.entity.wither_storm.main.WitherStorm;

public class CommandWitherModel<T extends WitherStorm> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "witherstorm0_0model"), "main");

    private static final String CENTER_HEAD = "center_head";
    private static final String RIGHT_HEAD = "right_head";
    private static final String LEFT_HEAD = "left_head";

    private final ModelPart centerHead;
    private final ModelPart ws_head;
    private final ModelPart rightHead;
    private final ModelPart leftHead;
    private final ModelPart tail;
    private final ModelPart all;

    public CommandWitherModel(ModelPart root) {
        this.all = root.getChild("all");
        this.tail = root.getChild("all").getChild("body1").getChild("body2").getChild("body3");
        this.centerHead = root.getChild("all").getChild("body1").getChild("head1");
        this.leftHead = root.getChild("all").getChild("body1").getChild("head3");
        this.rightHead = root.getChild("all").getChild("body1").getChild("head2");
        this.ws_head = root.getChild("all").getChild("body1").getChild("head1").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, -8.0F));

        PartDefinition body1 = all.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(0, 45).addBox(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 8.0F));

        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(0, 51).addBox(0.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-4.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-4.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-4.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 6.9F, -0.5F));

        PartDefinition body10_r1 = body2.addOrReplaceChild("body10_r1", CubeListBuilder.create().texOffs(24, 51).addBox(-9.0F, -15.6F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 52).addBox(-9.0F, -13.1F, -3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -10.6F, -3.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -10.6F, 2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(25, 52).addBox(-9.0F, -13.1F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -15.6F, 2.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -15.6F, 4.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -13.1F, 4.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -10.6F, 4.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -10.6F, -5.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -13.1F, -5.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(-9.0F, -15.6F, -5.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 17.1F, 0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition command_block = body2.addOrReplaceChild("command_block", CubeListBuilder.create().texOffs(0, -16).addBox(8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, -16).addBox(-8.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4.1F, -7.5F));

        PartDefinition head4_r1 = command_block.addOrReplaceChild("head4_r1", CubeListBuilder.create().texOffs(0, -16).addBox(8.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, -16).addBox(-8.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition head5_r1 = command_block.addOrReplaceChild("head5_r1", CubeListBuilder.create().texOffs(0, -16).addBox(-8.0F, -8.0F, -16.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, -16).addBox(8.0F, -8.0F, -16.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition mass_cb = command_block.addOrReplaceChild("mass_cb", CubeListBuilder.create(), PartPose.offset(4.0F, 28.0F, 4.0F));

        PartDefinition growth_cb = mass_cb.addOrReplaceChild("growth_cb", CubeListBuilder.create().texOffs(83, 10).addBox(4.0F, -38.5F, -15.0F, 5.0F, 17.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(0.5F, -37.5F, -13.0F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(0.5F, -39.5F, -9.5F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(0.5F, -31.5F, -16.5F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(9.0F, -33.0F, -7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(9.0F, -30.0F, -11.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = growth_cb.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(83, 10).addBox(-2.5F, -4.0F, -8.0F, 5.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -30.0384F, -7.5158F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r2 = growth_cb.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(84, 11).addBox(-3.5F, -2.5F, -7.5F, 7.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -28.0F, -4.5F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r3 = growth_cb.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(95, 22).addBox(-3.0F, -7.0F, 3.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(-3.0F, -2.0F, -6.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -30.0F, -8.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r4 = growth_cb.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(95, 22).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -30.0F, -8.0F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r5 = growth_cb.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(92, 19).addBox(-4.5F, -6.0F, -1.0F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -25.5F, -13.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition growth_cb2 = mass_cb.addOrReplaceChild("growth_cb2", CubeListBuilder.create().texOffs(83, 10).addBox(-3.1351F, -9.1703F, -7.4689F, 5.0F, 17.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(-6.6351F, -8.1703F, -5.4689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(-6.6351F, -10.1703F, -1.9689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(-6.6351F, -2.1703F, -8.9689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(1.8649F, -3.6703F, 0.5311F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(1.8649F, -0.6703F, -3.4689F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.3649F, -34.8297F, -6.0311F, 0.0F, 0.0F, -1.5272F));

        PartDefinition cube_r6 = growth_cb2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(83, 10).addBox(-2.5F, -4.0F, -8.0F, 5.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8649F, -0.7087F, 0.0153F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r7 = growth_cb2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(84, 11).addBox(-3.5F, -2.5F, -7.5F, 7.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8649F, 1.3297F, 3.0311F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r8 = growth_cb2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(95, 22).addBox(-3.0F, -7.0F, 3.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(-3.0F, -2.0F, -6.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8649F, -0.6703F, -0.4689F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r9 = growth_cb2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(95, 22).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8649F, -0.6703F, -0.4689F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r10 = growth_cb2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(92, 19).addBox(-4.5F, -6.0F, -1.0F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.1351F, 3.8297F, -5.4689F, 0.0F, 0.0F, 0.3491F));

        PartDefinition growth_cb3 = mass_cb.addOrReplaceChild("growth_cb3", CubeListBuilder.create().texOffs(83, 10).addBox(-3.1351F, -9.1703F, -7.4689F, 5.0F, 17.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(-6.6351F, -8.1703F, -5.4689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(-6.6351F, -10.1703F, -1.9689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(-6.6351F, -2.1703F, -8.9689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(1.8649F, -3.6703F, 0.5311F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(1.8649F, -0.6703F, -3.4689F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.8649F, -27.3297F, -5.5311F, 0.0F, 0.0F, 3.098F));

        PartDefinition cube_r11 = growth_cb3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(83, 10).addBox(-2.5F, -4.0F, -8.0F, 5.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8649F, -0.7087F, 0.0153F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r12 = growth_cb3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(84, 11).addBox(-3.5F, -2.5F, -7.5F, 7.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8649F, 1.3297F, 3.0311F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r13 = growth_cb3.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(95, 22).addBox(-3.0F, -7.0F, 3.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(95, 22).addBox(-3.0F, -2.0F, -6.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8649F, -0.6703F, -0.4689F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r14 = growth_cb3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(95, 22).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8649F, -0.6703F, -0.4689F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r15 = growth_cb3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(92, 19).addBox(-4.5F, -6.0F, -1.0F, 5.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.1351F, 3.8297F, -5.4689F, 0.0F, 0.0F, 0.3491F));

        PartDefinition growth_cb4 = mass_cb.addOrReplaceChild("growth_cb4", CubeListBuilder.create().texOffs(83, 10).addBox(-3.1351F, -9.1703F, -7.4689F, 5.0F, 17.0F, 16.0F, new CubeDeformation(1.0F))
                .texOffs(92, 19).addBox(-6.6351F, -8.1703F, -5.4689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(1.0F))
                .texOffs(92, 19).addBox(-6.6351F, -10.1703F, -1.9689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(1.0F))
                .texOffs(92, 19).addBox(-6.6351F, -2.1703F, -8.9689F, 5.0F, 12.0F, 7.0F, new CubeDeformation(1.0F))
                .texOffs(95, 22).addBox(1.8649F, -3.6703F, 0.5311F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F))
                .texOffs(95, 22).addBox(1.8649F, -0.6703F, -3.4689F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-4.8649F, -22.3297F, -3.0311F, -2.1817F, 0.0F, -1.6144F));

        PartDefinition cube_r16 = growth_cb4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(83, 10).addBox(-2.5F, -4.0F, -8.0F, 5.0F, 8.0F, 16.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(0.8649F, -0.7087F, 0.0153F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r17 = growth_cb4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(84, 11).addBox(-3.5F, -2.5F, -7.5F, 7.0F, 7.0F, 15.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(1.8649F, 1.3297F, 3.0311F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r18 = growth_cb4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(95, 22).addBox(-3.0F, -7.0F, 3.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F))
                .texOffs(95, 22).addBox(-3.0F, -2.0F, -6.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(3.8649F, -0.6703F, -0.4689F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r19 = growth_cb4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(95, 22).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(3.8649F, -0.6703F, -0.4689F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r20 = growth_cb4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(92, 19).addBox(-4.5F, -6.0F, -1.0F, 5.0F, 12.0F, 7.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-5.1351F, 3.8297F, -5.4689F, 0.0F, 0.0F, 0.3491F));

        PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create().texOffs(12, 51).addBox(0.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

        PartDefinition mass = body2.addOrReplaceChild("mass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition chunk = mass.addOrReplaceChild("chunk", CubeListBuilder.create().texOffs(79, 19).addBox(-6.0F, -5.0F, 3.0F, 9.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 6).addBox(-7.0F, -6.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(113, -1).addBox(-5.0F, -4.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-1.5F, -6.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 2.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition chunk2 = mass.addOrReplaceChild("chunk2", CubeListBuilder.create().texOffs(79, 19).addBox(-6.0F, -5.0F, 3.0F, 9.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 6).addBox(-7.0F, -6.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(113, -1).addBox(-5.0F, -4.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-1.5F, -6.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 2.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -2.5F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition chunk3 = mass.addOrReplaceChild("chunk3", CubeListBuilder.create().texOffs(79, 19).addBox(-6.0F, -5.0F, 3.0F, 9.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 6).addBox(-7.0F, -6.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(113, -1).addBox(-5.0F, -4.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-1.5F, -6.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 2.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -7.5F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition chunk4 = mass.addOrReplaceChild("chunk4", CubeListBuilder.create().texOffs(79, 19).addBox(-6.0F, -5.0F, 3.0F, 9.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 6).addBox(-7.0F, -6.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(113, -1).addBox(-5.0F, -4.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-1.5F, -6.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 2.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.5F, 0.0F, 0.0F, 0.0F, 3.098F));

        PartDefinition chunk5 = mass.addOrReplaceChild("chunk5", CubeListBuilder.create().texOffs(79, 19).addBox(-6.0F, -5.0F, 3.0F, 9.0F, 8.0F, 2.0F, new CubeDeformation(1.0F))
                .texOffs(112, 6).addBox(-7.0F, -6.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F))
                .texOffs(113, -1).addBox(-5.0F, -4.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(1.0F))
                .texOffs(91, 3).addBox(-1.5F, -6.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(1.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(1.0F))
                .texOffs(91, 3).addBox(-4.5F, -2.5F, 2.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(1.0F)), PartPose.offsetAndRotation(-5.5F, 1.5F, 0.5F, 0.0F, 0.0F, -2.6616F));

        PartDefinition huge_mass = body2.addOrReplaceChild("huge_mass", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition glob = huge_mass.addOrReplaceChild("glob", CubeListBuilder.create().texOffs(80, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.5F, -6.0F, 3.5F, 0.48F, 0.0F, 0.7854F));

        PartDefinition glob2 = huge_mass.addOrReplaceChild("glob2", CubeListBuilder.create().texOffs(80, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -11.0F, 3.5F, -0.7596F, -0.5865F, 1.2693F));

        PartDefinition glob3 = huge_mass.addOrReplaceChild("glob3", CubeListBuilder.create().texOffs(80, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -11.0F, 3.5F, -0.2221F, -0.7705F, 2.5733F));

        PartDefinition glob4 = huge_mass.addOrReplaceChild("glob4", CubeListBuilder.create().texOffs(80, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 8.5F, 0.9957F, -0.4121F, -2.9187F));

        PartDefinition glob5 = huge_mass.addOrReplaceChild("glob5", CubeListBuilder.create().texOffs(80, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, -6.5F, -6.5F, 1.5124F, 0.7583F, 2.5347F));

        PartDefinition glob6 = huge_mass.addOrReplaceChild("glob6", CubeListBuilder.create().texOffs(80, 0).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -6.5F, -6.5F, 0.0379F, 0.9247F, 1.7785F));

        PartDefinition head3 = body1.addOrReplaceChild("head3", CubeListBuilder.create().texOffs(32, 29).addBox(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 4.0F, 0.0F));

        PartDefinition head1 = body1.addOrReplaceChild("head1", CubeListBuilder.create().texOffs(0, 29).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = head1.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 5.0F));

        PartDefinition top = head.addOrReplaceChild("top", CubeListBuilder.create().texOffs(84, 6).addBox(-4.9423F, -4.6346F, -11.8462F, 10.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(4.0577F, 0.3654F, -1.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(4.0577F, 0.3654F, -3.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(4.0577F, 0.3654F, -5.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(4.0577F, 0.3654F, -7.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(4.0577F, 0.3654F, -9.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(4.0577F, 0.3654F, -11.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(2.0577F, 0.3654F, -12.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(1.0577F, 0.3654F, -13.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-0.9423F, 0.3654F, -13.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-2.9423F, 0.3654F, -11.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-4.9423F, 0.3654F, -11.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-4.9423F, 0.3654F, -9.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-4.9423F, 0.3654F, -7.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-4.9423F, 0.3654F, -5.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-4.9423F, 0.3654F, -3.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-4.9423F, 0.3654F, -1.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(104, 62).addBox(-2.9423F, 0.3654F, -13.8462F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(112, 60).addBox(-2.9423F, -4.6346F, -13.8462F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(112, 61).addBox(-2.9423F, -0.6346F, -13.8462F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(120, 19).addBox(1.0577F, -2.6346F, -13.8462F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 33).addBox(-0.9423F, -2.6346F, -13.8462F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(120, 60).addBox(-2.9423F, -2.6346F, -13.8462F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(79, 1).addBox(-2.9423F, -6.6346F, -10.8462F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(111, 6).addBox(3.0577F, -6.6346F, -5.8462F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(112, 30).addBox(-4.9423F, -6.6346F, -5.8462F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0577F, 1.6346F, -0.1538F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(79, 0).addBox(-4.9F, -0.15F, -11.95F, 10.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(4.1F, -1.15F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(4.1F, -1.15F, -2.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(4.1F, -1.15F, -4.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(4.1F, -1.15F, -6.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(4.1F, -1.15F, -8.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(4.1F, -1.15F, -10.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(2.1F, -1.15F, -11.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(2.1F, -1.15F, -13.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(0.1F, -1.15F, -13.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-1.9F, -1.15F, -13.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-2.9F, -1.15F, -12.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-3.9F, -1.15F, -11.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-4.9F, -1.15F, -10.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-4.9F, -1.15F, -8.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-4.9F, -1.15F, -6.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-4.9F, -1.15F, -4.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-4.9F, -1.15F, -2.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(70, 62).addBox(-4.9F, -1.15F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(112, 60).addBox(-2.9F, -0.15F, -13.95F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, 3.15F, -0.05F));

        PartDefinition head2 = body1.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(32, 29).addBox(-4.0F, -4.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 4.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.centerHead.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.centerHead.xRot = netHeadYaw * ((float)Math.PI / 180F);
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(((WitherStorm) entity).idleAnimationState, WitherStormAnimationDefinitions.WS_0, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase0, WitherStormAnimationDefinitions.WS_0, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase1, WitherStormAnimationDefinitions.WS_1, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase2, WitherStormAnimationDefinitions.WS_2, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase3, WitherStormAnimationDefinitions.WS_3, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase4, WitherStormAnimationDefinitions.WS_4, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase5, WitherStormAnimationDefinitions.WS_5, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase6, WitherStormAnimationDefinitions.WS_6, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase7, WitherStormAnimationDefinitions.WS_7, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase8, WitherStormAnimationDefinitions.WS_8, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase9, WitherStormAnimationDefinitions.WS_9, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase10, WitherStormAnimationDefinitions.WS_10, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase11, WitherStormAnimationDefinitions.WS_11, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).phase12, WitherStormAnimationDefinitions.WS_12, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).evolving1, WitherStormAnimationDefinitions.WS_CONVERSION1, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).evolving2, WitherStormAnimationDefinitions.WS_CONVERSION2, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).evolving3, WitherStormAnimationDefinitions.WS_CONVERSION3, ageInTicks, 1f);
        this.animate(((WitherStorm) entity).evolving4, WitherStormAnimationDefinitions.WS_CONVERSION4, ageInTicks, 1f);

    }
    public void prepareMobModel(T p_104095_, float p_104096_, float p_104097_, float p_104098_) {
        setupHeadRotation(p_104095_, this.rightHead, 0);
        setupHeadRotation(p_104095_, this.leftHead, 1);
    }

    private static <T extends WitherStorm> void setupHeadRotation(T p_171072_, ModelPart p_171073_, int p_171074_) {
        p_171073_.yRot = (p_171072_.getHeadYRot(p_171074_) - p_171072_.yBodyRot) * ((float)Math.PI / 180F);
        p_171073_.xRot = p_171072_.getHeadXRot(p_171074_) * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return all;
    }
}
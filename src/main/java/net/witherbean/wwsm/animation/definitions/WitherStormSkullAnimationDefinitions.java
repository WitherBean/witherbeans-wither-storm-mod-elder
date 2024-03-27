package net.witherbean.wwsm.animation.definitions;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class WitherStormSkullAnimationDefinitions {
    public static final AnimationDefinition SKULL_IDLE = AnimationDefinition.Builder.withLength(2f).looping()
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 2f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -7.5f, 7f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.posVec(0f, -2.5f, -0.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 3.5f, 7f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 2f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(30f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5f, KeyframeAnimations.degreeVec(45f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(-27.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(1.5f, 1.5f, 1.5f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("fire",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 360f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("fire2",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -27.5f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("fire2",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.7f, 0.7f, 1.2f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("fire3",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -75f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("fire3",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.5f, 0.5f, 2f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("fire4",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 37.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, -37.5f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("fire4",
                    new AnimationChannel(AnimationChannel.Targets.SCALE,
                            new Keyframe(0f, KeyframeAnimations.scaleVec(0.3f, 0.3f, 3f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}

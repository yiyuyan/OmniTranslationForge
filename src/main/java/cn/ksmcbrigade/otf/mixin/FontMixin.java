package cn.ksmcbrigade.otf.mixin;

import cn.ksmcbrigade.otf.OmniTranslationForge;
import cn.ksmcbrigade.otf.Utils;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.language.FormattedBidiReorder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Mth;
import net.minecraft.util.StringDecomposer;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.StringEncoderComparator;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.class)
public abstract class FontMixin {

    @Shadow @Final private StringSplitter splitter;

    @Shadow public abstract String bidirectionalShaping(String p_92802_);

    @Shadow protected abstract float renderText(String p_273765_, float p_273532_, float p_272783_, int p_273217_, boolean p_273583_, Matrix4f p_272734_, MultiBufferSource p_272595_, Font.DisplayMode p_273610_, int p_273727_, int p_273199_);

    @Shadow @Final private static Vector3f SHADOW_OFFSET;

    @Shadow
    private static int adjustColor(int p_92720_) {
        return 0;
    }

    @Inject(method = "width(Ljava/lang/String;)I",at = @At("RETURN"),cancellable = true)
    public void width(String p_92896_, CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(Mth.ceil(this.splitter.stringWidth(Utils.getKey(p_92896_))));
    }

    @Inject(method = "drawInternal(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I",at = @At("HEAD"),cancellable = true)
    public void draw(String text, float x, float y, int color, boolean p_272778_, Matrix4f p_272662_, MultiBufferSource p_273012_, Font.DisplayMode p_273381_, int p_272855_, int p_272745_, boolean p_272785_, CallbackInfoReturnable<Integer> cir) {
        if (Utils.getConfig("saveStringList").equals("true")) {

            OmniTranslationForge.saveStringList.add(text);

        }

        String string = Utils.getKey(text);


        if (p_272785_) {
            string = this.bidirectionalShaping(string);
        }

        color = adjustColor(color);

        Matrix4f matrix4f = new Matrix4f(p_272662_);

        if (p_272778_) {
            this.renderText(string, x, y, color, true, p_272662_, p_273012_, p_273381_, p_272855_, p_272745_);
            matrix4f.translate(SHADOW_OFFSET);
        }

        x = this.renderText(string, x, y, color, false, matrix4f,  p_273012_, p_273381_, p_272855_, p_272745_);

        cir.setReturnValue((int) x + (p_272778_ ? 1 : 0));
        cir.cancel();
    }
}

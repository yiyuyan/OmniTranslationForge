package cn.ksmcbrigade.otf.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static cn.ksmcbrigade.otf.OmniTranslationForge.to;

@Mixin(Component.class)
public interface ComponentMixin {

    /**
     * @author KSmc_brigade
     * @reason RE
     */
    @Overwrite
    static MutableComponent literal(String p_237114_) {
        return MutableComponent.create(new LiteralContents(to(p_237114_)));
    }

    /**
     * @author KSmc_brigade
     * @reason RE
     */
    @Overwrite
    static MutableComponent translatable(String p_237116_) {
        return MutableComponent.create(new TranslatableContents(to(p_237116_), null, TranslatableContents.NO_ARGS));
    }
}

package cn.ksmcbrigade.otf.mixin;

import cn.ksmcbrigade.otf.OmniTranslationForge;
import cn.ksmcbrigade.otf.Utils;
import com.google.gson.JsonArray;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow @Final public File gameDirectory;

    @Inject(method = "stop",at = @At("HEAD"))
    public void saveConfig(CallbackInfo ci) throws IOException {
        new File(gameDirectory + "/OmniTranslationForge").mkdir();

        File file = new File(this.gameDirectory + "/OmniTranslationForge/saveStringList.json");


        if (!file.exists()) {
            file.createNewFile();
        }

        JsonArray saveStringList = new JsonArray();

        for(String key:OmniTranslationForge.saveStringList){
            saveStringList.add(key);
        }

        Utils.write(file, saveStringList.toString());
    }
}

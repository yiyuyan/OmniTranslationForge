package cn.ksmcbrigade.otf;

import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod(OmniTranslationForge.MODID)
public class OmniTranslationForge {

    public static final String MODID = "otf";

    public static Set<String> saveStringList = new HashSet<>();

    public OmniTranslationForge() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static String to(String c){
        String text = I18n.get(c);
        if (Utils.getConfig("saveStringList").equals("true")) {

            OmniTranslationForge.saveStringList.add(text);

        }
        return Utils.getKey(text);
    }
}

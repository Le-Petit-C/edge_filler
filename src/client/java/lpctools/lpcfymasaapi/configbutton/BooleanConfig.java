package lpctools.lpcfymasaapi.configbutton;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import lpctools.lpcfymasaapi.LPCConfigList;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

public class BooleanConfig extends LPCConfig<ConfigBoolean> implements BooleanSupplier, BooleanConsumer {
    public final boolean defaultBoolean;
    public BooleanConfig(LPCConfigList list, String name, boolean defaultBoolean){
        super(list, name, false);
        this.defaultBoolean = defaultBoolean;
    }
    public BooleanConfig(LPCConfigList list, String name, boolean defaultBoolean, IValueRefreshCallback callback){
        this(list, name, defaultBoolean);
        setCallback(callback);
    }
    @Override public boolean getAsBoolean() {
        return getInstance() != null ?  getInstance().getBooleanValue() : defaultBoolean ;
    }
    @Override public void accept(boolean b) {
        getConfig().setBooleanValue(b);
    }

    @Override @NotNull protected ConfigBoolean createInstance(){
        ConfigBoolean config = new ConfigBoolean(nameKey, defaultBoolean);
        config.apply(list.getFullTranslationKey());
        config.setValueChangeCallback(new LPCConfigCallback<>(this));
        return config;
    }
}

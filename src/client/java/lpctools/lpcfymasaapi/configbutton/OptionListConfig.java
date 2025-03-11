package lpctools.lpcfymasaapi.configbutton;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.util.StringUtils;
import lpctools.lpcfymasaapi.LPCConfigList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class OptionListConfig<T> extends LPCConfig<ConfigOptionList> {
    public OptionListConfig(@NotNull LPCConfigList list, @NotNull String name) {
        this(list, name, null);
    }
    public OptionListConfig(@NotNull LPCConfigList list, @NotNull String name, @Nullable IValueRefreshCallback callback) {
        super(list, name, false);
        setCallback(callback);
    }
    //构造后应立即调用至少一次addOption
    public void addOption(@NotNull String translationKey, @Nullable T userData){
        options.add(new OptionData<>(options, translationKey, userData, options.size()));
    }
    @SuppressWarnings("unchecked")
    public T getCurrentUserdata(){
        ConfigOptionList instance = getInstance();
        if(instance == null) return options.getFirst().userData;
        return ((OptionData<T>)instance.getOptionListValue()).userData;
    }
    @Override protected @NotNull ConfigOptionList createInstance() {
        return new ConfigOptionList(name, options.isEmpty() ? null : options.getFirst());
    }
    @NotNull private final ArrayList<@NotNull OptionData<T>> options = new ArrayList<>();
    private record OptionData<T>(@NotNull ArrayList<OptionData<T>> options, @NotNull String translationKey, @Nullable T userData, int index) implements IConfigOptionListEntry{
        @Override public String getStringValue() {
            return translationKey;
        }
        @Override public String getDisplayName() {
            return StringUtils.translate(translationKey);
        }
        @Override public IConfigOptionListEntry cycle(boolean forward) {
            int n = index;
            if(forward){if(++n >= options.size()) n = 0;}
            else if(--n < 0) n = options.size() - 1;
            return options.get(n);
        }

        //感觉这是一个“半static”方法
        @Override public IConfigOptionListEntry fromString(String value) {
            for(OptionData<T> option : options)
                if(option.getStringValue().equals(value))
                    return option;
            return null;
        }
    }
}
